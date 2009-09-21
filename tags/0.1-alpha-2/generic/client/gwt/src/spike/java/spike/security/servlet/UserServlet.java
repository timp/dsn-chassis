package spike.security.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String username = null;
		String roles = "";
		
		if (obj instanceof UserDetails) {
			
			UserDetails user = (UserDetails) obj;
			username = user.getUsername();
			GrantedAuthority[] authorities = user.getAuthorities();
			for (int i=0; i<authorities.length; i++) {
				roles += authorities[i].getAuthority() + " ";
			}

		} else {

			username = obj.toString();

		}
		
		response.setContentType("text/plain");
		PrintWriter writer = new PrintWriter(response.getWriter());
		writer.println("username: "+username);
		writer.println("roles: "+roles);
	}

}
