package spike.fileupload;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

/**
 * Servlet implementation class SimpleFileUploadServlet
 */
public class SimpleFileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SimpleFileUploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		sendError(response, "only POST requests are allowed", HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		
		if (isMultipart) {
			
			try {

				// Create a new file upload handler
				ServletFileUpload upload = new ServletFileUpload();

				// Parse the request
				FileItemIterator iter = upload.getItemIterator(request);

				while (iter.hasNext()) {
				    FileItemStream item = iter.next();
				    String name = item.getFieldName();
				    InputStream stream = item.openStream();
				    if (item.isFormField()) {
				    	
				        System.out.println("Form field " + name + " with value " + Streams.asString(stream) + " detected.");
				        
				    } else {
				    	
				        System.out.println("File field " + name + " with file name " + item.getName() + " detected.");
				        // Process the input stream
				        // ...
				        
				    }
				}

			}
			catch (Throwable t) {

				t.printStackTrace();
				sendError(response, t.getLocalizedMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

			}
			
		}
		else {

			sendError(response, "only multipart content is supported", HttpServletResponse.SC_BAD_REQUEST);

		}
	}

	
	
	protected void sendError(HttpServletResponse response, String message, int code) throws IOException {
		
		response.setStatus(code);
		response.setContentType("text/plain");
		response.getWriter().print(message);
		response.flushBuffer();

	}



}
