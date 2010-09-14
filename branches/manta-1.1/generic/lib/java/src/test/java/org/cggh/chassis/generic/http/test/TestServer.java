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
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Adapted from http://www.java2s.com/Code/Java/Tiny-Application/HttpServer.htm
 * 
 * @author timp
 * @since 2010/03/01
 */
public class TestServer implements Runnable {
	static ServerSocket serverSocket;
    static boolean run = true;
    static String root = "src/test/java";
	public static int PORT = 8089;
    
	public static void main(String args[]) throws Exception {
		new TestServer().run();
	}
	public void run() {
		try {
			if (serverSocket == null)
					serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
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
	
			HttpRequestHandler request;
			try {
				request = new HttpRequestHandler(socket);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			Thread thread = new Thread(request);
			thread.start();
		}
		System.out.println("bye");
	}
	public static void stop() throws Exception { 
		run = false;
		if (TestServer.serverSocket != null)
			TestServer.serverSocket.close();
		if (serverSocket != null)
			serverSocket.close();
	}
}

class HttpRequestHandler implements Runnable {
	
	final static String CRLF = "\r\n";

	Socket socket;
	InputStream input;
	OutputStream output;
	BufferedReader bufferedReader;

	public HttpRequestHandler(Socket socket) throws IOException {
		this.socket = socket;
		this.input = socket.getInputStream();
		this.output = socket.getOutputStream();
		this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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

			HashMap<String, String> parameters = new HashMap<String, String>();

			String headerLine = bufferedReader.readLine();
			if (headerLine.equals(CRLF) || headerLine.equals(""))
				break;

			StringTokenizer s = new StringTokenizer(headerLine, " \t\n\r\f?=&", false);
			String temp = s.nextToken();

			if (temp.equals("GET")) {

				FileInputStream fis = null;
				boolean fileExists = true;

				String fileName = s.nextToken();
				if (fileName.equals("/STOP")) {
					TestServer.run = false;
					fileExists = false;
				} else {
					int pairCount = 0;
					String token = s.nextToken();
					String name = "";
					while (!(token.equals("HTTP/1.1") && !token.equals("HTTP/1.0"))) {
						if (pairCount == 0 ){ 
							name = token;
							pairCount = 1;
						} else { 
							pairCount = 0;
							parameters.put(name, token);
						}
						token = s.nextToken();
					}
					fileName = TestServer.root + fileName;
					try {
						fis = new FileInputStream(fileName);
					} catch (FileNotFoundException e) {
						fileExists = false;
					}
				}
				String serverLine = "Server: TestServer";
				String statusLine = null;
				String contentTypeLine = null;
				String pageBody = null;
				String contentLengthLine = "error";
				if (fileExists) {
					statusLine = statusLine(parameters, "200" , "OK");
					contentTypeLine = "Content-type: " + contentType(fileName);
					contentLengthLine = "Content-Length: "
						+ (new Integer(fis.available())).toString();
				} else {
					if (TestServer.run) { 
						statusLine = statusLine(parameters, "404" , "Not Found");
						contentTypeLine = "Content-type: text/html";
						pageBody = page(statusLine(parameters, "404" , "Not Found"));
						contentLengthLine = "Content-Length: "
							+ (new Integer(pageBody.length())).toString();
					} else { 
						statusLine = statusLine(parameters, "200" , "OK");
						contentTypeLine = "Content-type: text/html";
						pageBody = page("Bye then!");						
						contentLengthLine = "Content-Length: "
							+ (new Integer(pageBody.length())).toString();
					}
				}

				outputLine(statusLine);
				outputLine(serverLine);
				outputLine(contentTypeLine);
				outputLine(contentLengthLine);

				// Send a blank line to indicate the end of the header lines.
				outputLine(CRLF);

				// Send the entity body.
				if (fileExists) {
					sendBytes(fis, output);
					fis.close();
				} else {
					outputLine(pageBody);
				}
			}
		}

		output.close();
		bufferedReader.close();
		socket.close();
	}
	private String page(String text) {
		return 
		      "<HTML>\n"
			+ " <HEAD>\n" 
			+ "  <TITLE>" + text + "</TITLE>\n"
			+ " </HEAD>\n"
			+ "<BODY>\n" 
			+ text 
			+ "\n<hr/>\n"
			+ "TestServer running on port " + TestServer.PORT
			+ "\n</BODY>\n"
			+ "</HTML>\n";
	}

	private String statusLine(HashMap<String, String> parameters,
			String status, String message) {
		return "HTTP/1.0 " + status(parameters, status) + " " + message(parameters, message);
	}

	private String status(HashMap<String, String> parameters, String defaultValue) {
		if(parameters.containsKey("status"))
			return parameters.get("status");
		else 
			return defaultValue;
	}
	private String message(HashMap<String, String> parameters, String defaultValue) {
		if(parameters.containsKey("message"))
			return parameters.get("message");
		else 
			return defaultValue;
	}


	private void outputLine(String line) throws IOException { 
		output.write(line.getBytes());
		output.write(CRLF.getBytes());
		System.out.println(line);
	}

	private static void sendBytes(FileInputStream fis, OutputStream os)
			throws IOException {

		byte[] buffer = new byte[1024];
		int bytes = 0;

		while ((bytes = fis.read(buffer)) != -1) {
			os.write(buffer, 0, bytes);
		}
	}

	private static String contentType(String fileName) {
		if (fileName.endsWith(".htm") || fileName.endsWith(".html")) {
			return "text/html";
		} else if (fileName.endsWith(".txt")) {
			return "text/plain";
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
