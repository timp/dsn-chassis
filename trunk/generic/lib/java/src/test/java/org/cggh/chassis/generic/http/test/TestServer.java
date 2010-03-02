package org.cggh.chassis.generic.http.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 * @author timp
 * 
 */
public class TestServer {
	static ServerSocket serverSocket;
    static boolean run = true;
    static String root = "src/test/java/org/cggh/chassis/generic/http/test";
    
	public static void main(String args[]) throws Exception {
		int port;
		try {
			port = Integer.parseInt(args[0]);
		} catch (Exception e) {
			port = 1500;
		}
		run(port);
	}
	public static void run(int port) throws Exception {
		serverSocket = new ServerSocket(port);
		System.out.println("TestServer running on port "
					+ serverSocket.getLocalPort());

		while (run) {
			Socket socket = null;
			try { 
				socket= serverSocket.accept();
			} catch (IOException e) { 
				break;
			}
			System.out.println("New connection accepted "
					+ socket.getInetAddress() + ":" + socket.getPort());
	
			HttpRequestHandler request = new HttpRequestHandler(socket);
			Thread thread = new Thread(request);
			thread.start();
		}
	}
	public static void stop() throws Exception { 
		run = false;
	}
}

class HttpRequestHandler implements Runnable {
	
	final static String CRLF = "\r\n";

	Socket socket;
	InputStream input;
	OutputStream output;
	BufferedReader br;

	public HttpRequestHandler(Socket socket) throws Exception {
		this.socket = socket;
		this.input = socket.getInputStream();
		this.output = socket.getOutputStream();
		this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	public void run() {
		try {
			processRequest();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void processRequest() throws Exception {
		while (true) {

			String headerLine = br.readLine();
			if (headerLine.equals(CRLF) || headerLine.equals(""))
				break;

			StringTokenizer s = new StringTokenizer(headerLine);
			String temp = s.nextToken();

			if (temp.equals("GET")) {

				FileInputStream fis = null;
				boolean fileExists = true;

				String fileName = s.nextToken();
				if (fileName.equals("/STOP")) {
					TestServer.stop();
					fileExists = false;
				} else {
					fileName = TestServer.root + fileName;
					try {
						fis = new FileInputStream(fileName);
					} catch (FileNotFoundException e) {
						fileExists = false;
					}
				}
				String serverLine = "Server: Simple Java Http Server";
				String statusLine = null;
				String contentTypeLine = null;
				String pageBody = null;
				String contentLengthLine = "error";
				if (fileExists) {
					statusLine = "HTTP/1.0 200 OK" + CRLF;
					contentTypeLine = "Content-type: " + contentType(fileName)
							+ CRLF;
					contentLengthLine = "Content-Length: "
						+ (new Integer(fis.available())).toString() + CRLF;
				} else {
					if (TestServer.run) { 
						statusLine = "HTTP/1.0 404 Not Found" + CRLF;
						contentTypeLine = "Content-type: text/html";
						pageBody = "<HTML>"
								+ "<HEAD><TITLE>404 Not Found</TITLE></HEAD>"
								+ "<BODY>404 Not Found"
								+ "<br>usage:http://yourHostName:port/"
								+ "fileName.html</BODY></HTML>";
						contentLengthLine = "Content-Length: "
							+ (new Integer(pageBody.length())).toString() + CRLF;
					} else { 
						statusLine = "HTTP/1.0 200 OK" + CRLF;
						contentTypeLine = "Content-type: text/html";
						pageBody = "<HTML>"
								+ "<HEAD><TITLE>Shutdown</TITLE></HEAD>"
								+ "<BODY>Bye then!</BODY></HTML>";						
						contentLengthLine = "Content-Length: "
							+ (new Integer(pageBody.length())).toString() + CRLF;
					}
				}

				output(statusLine);
				output(serverLine);
				output(contentTypeLine);
				output(contentLengthLine);

				// Send a blank line to indicate the end of the header lines.
				output(CRLF);

				// Send the entity body.
				if (fileExists) {
					sendBytes(fis, output);
					fis.close();
				} else {
					output(pageBody);
				}
			}
		}

		output.close();
		br.close();
		socket.close();
		if (!TestServer.run) {
			TestServer.serverSocket.close();
		}
	}
	private void output(String line) throws IOException { 
		output.write(line.getBytes());
		System.out.println(line);
	}

	private static void sendBytes(FileInputStream fis, OutputStream os)
			throws Exception {

		byte[] buffer = new byte[1024];
		int bytes = 0;

		while ((bytes = fis.read(buffer)) != -1) {
			os.write(buffer, 0, bytes);
		}
	}

	private static String contentType(String fileName) {
		if (fileName.endsWith(".htm") || fileName.endsWith(".html")
				|| fileName.endsWith(".txt")) {
			return "text/html";
		} else if (fileName.endsWith(".atom")) {
			return "application/atom+xml";
		} else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
			return "image/jpeg";
		} else if (fileName.endsWith(".gif")) {
			return "image/gif";
		} else {
			return "application/octet-stream";
		}
	}

}
