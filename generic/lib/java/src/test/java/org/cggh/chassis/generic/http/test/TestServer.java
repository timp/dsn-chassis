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

			System.err.println(headerLine);
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
					System.err.println("filename:" + fileName);
					int pairCount = 0;
					String token = s.nextToken();
					String name = "";
					while (!token.equals("HTTP/1.1")) {
						if (pairCount == 0 ){ 
							name = token;
							pairCount = 1;
						} else { 
							pairCount = 0;
							parameters.put(name, token);
							System.err.println("Putting " + name + ":" + token);
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
				String serverLine = "Server: Simple Java Http Server";
				String statusLine = null;
				String contentTypeLine = null;
				String pageBody = null;
				String contentLengthLine = "error";
				if (fileExists) {
					statusLine = statusLine(parameters, "200" , "OK");
					contentTypeLine = "Content-type: " + contentType(fileName) + CRLF;
					contentLengthLine = "Content-Length: "
						+ (new Integer(fis.available())).toString() + CRLF;
				} else {
					if (TestServer.run) { 
						statusLine = statusLine(parameters, "404" , "Not Found");
						contentTypeLine = "Content-type: text/html";
						pageBody = "<HTML>"
								+ "<HEAD><TITLE>" + statusLine(parameters, "404" , "Not Found") + "</TITLE></HEAD>"
								+ "<BODY>" 
								+ statusLine(parameters, "404" , "Not Found") 
								+ "<br/>usage:http://yourHostName:port/"
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
		bufferedReader.close();
		socket.close();
	}
	private String statusLine(HashMap<String, String> parameters,
			String status, String message) {
		return "HTTP/1.0 " + status(parameters, status) + " " + message(parameters, message) + CRLF;
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


	private void output(String line) throws IOException { 
		output.write(line.getBytes());
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
