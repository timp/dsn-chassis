package org.cggh.chassis.wwarn.ui.submitter.server;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Will connect to a ClamAV Server and request a scan. See
 * http://www.clamav.net/
 * 
 * Install ClamAV on Debian:
 * 
 * apt-get install clamav clamav-daemon clamav-docs libclamunrar6
 * 
 * NOTE: libclamunrar6 is in Debian Karmnic but not Debian jaunty.
 * 
 * Configuration:
 * 
 * dpkg-reconfigure clamav-base
 * 
 * Non-default choices: 
 * <ul>
 * <li>Enable tcp sockets</li>
 * <li>Only listen to 127.0.0.1</li>
 * <li>Do not scan mail</li>
 * </ul>
 * 
 * Based upon code from net.taldius.clamav.impl.NetworkScanner; copyright 2007
 * Jean-François POUX, jfp@jsmtpd.org See http://dev.taldius.net/libclamav/
 * 
 * @author Jean-Francois POUX, jfp@taldius.net
 * @author timp
 * @since 25-Jan-2010
 * 
 */
public class ClamAntiVirusScanner {

	private static final byte[] INIT_COMMAND = { 'S', 'T', 'R', 'E', 'A', 'M', '\n' };

	// Some developers do not have ClamAV installed 
	public static boolean DEVELOPMENT_INSTALLATION = false;

	private int connectionTimeout = 23; // pretty short, but it is local

	private String clamdHost = "localhost";

	private int clamdPort = 3310; // the default

	private static Log log = LogFactory.getLog(ClamAntiVirusScanner.class);

	private Socket protocolSocket = null;

	private Socket dataSocket = null;

	private String message = "";

	int dataPort = -1;

	public ClamAntiVirusScanner() {
	}

	public ClamAntiVirusScanner(String clamdHost) {
		this.clamdHost = clamdHost;
	}

	public ClamAntiVirusScanner(String clamdHost, int clamdPort,
			int connectionTimeout) {
		this.clamdHost = clamdHost;
		this.clamdPort = clamdPort;
		this.connectionTimeout = connectionTimeout;
	}

	/**
	 * Will block until scan is performed.
	 */
	public InputStream performScan(InputStream inputStream)
			throws ScannerException, ContainsVirusException {

		message = "";

		try {
			
			// check if clamd running before consuming stream 
			openProtocolChannel();

			
			
			File f = null;

			f = File.createTempFile("wwarnVirusScanner_"
					+ System.currentTimeMillis(), ".tmp");

			try {
				FileOutputStream output = new FileOutputStream(f);
				try {
					IOUtils.copy(inputStream, output);
				} finally {
					IOUtils.closeQuietly(output);
				}
			} finally {
				IOUtils.closeQuietly(inputStream);
			}
			
			f.deleteOnExit();

			FileInputStream inputStreamToScan = new FileInputStream(f);

			requestScan(inputStreamToScan);

			// clamd writes this if the stream
			// we sent does not contains viruses.
			if (!message.equals("stream: OK"))
				throw new ContainsVirusException("Virus found in " + f.getPath() + "(" + message + ")");

			// Recreate the stream we consumed 
			return new FileInputStream(f);
		} catch (IOException e) {
			log.debug(e);
			throw new ScannerException("Problem with temporary files:", e);
		} finally {
			if (protocolSocket != null) {
				try {
					protocolSocket.close();
				} catch (IOException e) {
					log.debug("Error closing protocol channel", e);
				}
			}
			if (dataSocket != null) {
				try {
					dataSocket.close();
				} catch (IOException e) {
					log.debug("Error closing data channel", e);
				}
			}
		}
	}

	private void requestScan(InputStream inputStream) throws ScannerException {
		byte[] received = new byte[1];
		dataSocket = new Socket();
		SocketAddress sockaddrData = new InetSocketAddress(clamdHost, dataPort);
		try {
			dataSocket.setSoTimeout(connectionTimeout * 1000);
		} catch (SocketException e) {
			throw new ScannerException(
					"Could not set timeout parameter to dataSocket", e);
		}
		try {
			dataSocket.connect(sockaddrData);

			// Write the content to the data stream
			IOUtils.copy(inputStream, dataSocket.getOutputStream());

			// let clamd know it's the end of the stream.
			dataSocket.close();
			inputStream.close();
		} catch (IOException e) {
			throw new ScannerException(
					"Error while initializing clamd data channel", e);
		}

		// Wait for the response on the chat stream.
		while (true) {
			try {
				protocolSocket.getInputStream().read(received);
			} catch (IOException e) {
				e.printStackTrace();
				throw new ScannerException(
						"Error while waiting for clamd response:" + e.getMessage(), e);
			}
			if (received[0] == '\n')
				break;
			message += new String(received);
		}
		log.debug("response: " + message);
	}

	private void openProtocolChannel() throws ScannerException {

		String serverResponse = "";
		protocolSocket = new Socket();
		SocketAddress sockaddr = new InetSocketAddress(clamdHost, clamdPort);
		try {
			protocolSocket.setSoTimeout(connectionTimeout * 1000);
		} catch (SocketException e) {
			throw new ScannerException(
					"Could not set timeout parameter on configurationSocket", e);
		}

		try {
			// First, try to connect to the clam daemon
			protocolSocket.connect(sockaddr);
			// Write the initialisation command
			protocolSocket.getOutputStream().write(INIT_COMMAND);

			// Now, read byte per byte until we find a LF.
			byte[] received = new byte[1];
			while (true) {
				protocolSocket.getInputStream().read(received);
				if (received[0] == '\n')
					break;
				serverResponse += new String(received);
			}
			
		} catch (ConnectException e) {
			throw new ClamAntiVirusNotRunningException(
					"Cannot connect to ClamAV service", e);
		} catch (IOException e) {
			throw new ScannerException(
					"Error while requesting protocol channel", e);
		}
		log.debug("Channel request response: " + serverResponse);

		// In the response value, there's an integer.
		// It's the TCP port that the clamd server has allocated for us for the
		// actual data stream.
		if (serverResponse.contains(" ")) {
			try {
				dataPort = Integer.parseInt(serverResponse.split(" ")[1]);
			} catch (NumberFormatException e) {
				throw new ScannerException(
						"Could not parse the server data port number to connect to, the server's response is: "
						+ serverResponse);
			}
		} else {
			throw new ScannerException(
					"Could not parse the server data port number to connect to, the server's response is: "
					+ serverResponse);
		}
	}


	public String getMessage() {
		return message;
	}


	
	
}
