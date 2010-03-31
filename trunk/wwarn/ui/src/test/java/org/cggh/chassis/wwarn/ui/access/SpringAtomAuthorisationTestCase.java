package org.cggh.chassis.wwarn.ui.access;

import javax.servlet.http.HttpServletResponse;

/**
 * @author timp
 * @since 5 Mar 2010
 */
public class SpringAtomAuthorisationTestCase extends AtomAuthorisationTestCase {
    //<user name="submitter@example.org" password="bar" authorities="ROLE_CHASSIS_SUBMITTER" />      
    //<user name="anonymizer@example.org" password="bar" authorities="ROLE_CHASSIS_ANONYMIZER" />
    //<user name="curator@example.org" password="bar" authorities="ROLE_CHASSIS_CURATOR" />      
    //<user name="administrator@example.org" password="bar" authorities="ROLE_CHASSIS_ADMINISTRATOR" />

   	final static String SUBMITTER ="submitter@example.org";      
   	final static String ANONYMIZER ="anonymizer@example.org";
   	final static String CURATOR ="curator@example.org";      
   	final static String ADMINISTRATOR = "administrator@example.org";
   	
   	final static String[] homeDirs = new String[] {"submitter", "anonymizer", "curator", "administrator"};  
   	final static String[] users = new String[] {SUBMITTER, ANONYMIZER, CURATOR, ADMINISTRATOR};
 /*  	
    <intercept-url pattern="/submitter/**" access="ROLE_CHASSIS_SUBMITTER" />
    <intercept-url pattern="/anonymizer/**" access="ROLE_CHASSIS_ANONYMIZER" />
    <intercept-url pattern="/curator/**" access="ROLE_CHASSIS_CURATOR" />
    <intercept-url pattern="/administrator/**" access="ROLE_CHASSIS_ADMINISTRATOR" />

	<intercept-url pattern="/atombeat/content/studies" method="POST" access="ROLE_CHASSIS_SUBMITTER" />
	<intercept-url pattern="/atombeat/content/studies/**" method="GET" access="ROLE_CHASSIS_SUBMITTER, ROLE_CHASSIS_ANONYMIZER, ROLE_CHASSIS_CURATOR, ROLE_CHASSIS_ADMINISTRATOR" />
	<intercept-url pattern="/atombeat/content/studies/**" method="PUT" access="ROLE_CHASSIS_SUBMITTER, ROLE_CHASSIS_ADMINISTRATOR" />
	<intercept-url pattern="/atombeat/content/studies/**" method="DELETE" access="ROLE_CHASSIS_ADMINISTRATOR" />

	<intercept-url pattern="/atombeat/content/submissions" method="POST" access="ROLE_CHASSIS_SUBMITTER" />
	<intercept-url pattern="/atombeat/content/submissions/**" method="GET" access="ROLE_CHASSIS_SUBMITTER, ROLE_CHASSIS_ANONYMIZER, ROLE_CHASSIS_CURATOR, ROLE_CHASSIS_ADMINISTRATOR" />
	<intercept-url pattern="/atombeat/content/submissions/**" method="PUT" access="ROLE_CHASSIS_SUBMITTER, ROLE_CHASSIS_ADMINISTRATOR" />
	<intercept-url pattern="/atombeat/content/submissions/**" method="DELETE" access="ROLE_CHASSIS_ADMINISTRATOR" />

	<intercept-url pattern="/atombeat/content/reviews" method="POST" access="ROLE_CHASSIS_ANONYMIZER, ROLE_CHASSIS_CURATOR" />
	<intercept-url pattern="/atombeat/content/reviews/**" method="GET" access="ROLE_CHASSIS_ANONYMIZER, ROLE_CHASSIS_CURATOR, ROLE_CHASSIS_ADMINISTRATOR" />
	<intercept-url pattern="/atombeat/content/reviews/**" method="PUT" access="ROLE_CHASSIS_ANONYMIZER, ROLE_CHASSIS_CURATOR, ROLE_CHASSIS_ADMINISTRATOR" />
	<intercept-url pattern="/atombeat/content/reviews/**" method="DELETE" access="ROLE_CHASSIS_ADMINISTRATOR" />

	<intercept-url pattern="/atombeat/content/derivations" method="POST" access="ROLE_CHASSIS_CURATOR, ROLE_CHASSIS_ANONYMIZER" />
	<intercept-url pattern="/atombeat/content/derivations/**" method="GET" access="ROLE_CHASSIS_ANONYMIZER, ROLE_CHASSIS_CURATOR, ROLE_CHASSIS_ADMINISTRATOR" />
	<intercept-url pattern="/atombeat/content/derivations/**" method="PUT" access="ROLE_CHASSIS_ANONYMIZER, ROLE_CHASSIS_CURATOR, ROLE_CHASSIS_ADMINISTRATOR" />
	<intercept-url pattern="/atombeat/content/derivations/**" method="DELETE" access="ROLE_CHASSIS_ADMINISTRATOR" />

	<intercept-url pattern="/atombeat/content/media" method="POST" access="ROLE_CHASSIS_SUBMITTER, ROLE_CHASSIS_ANONYMIZER, ROLE_CHASSIS_CURATOR" />
	<intercept-url pattern="/atombeat/content/media/**" method="GET" access="ROLE_CHASSIS_SUBMITTER, ROLE_CHASSIS_ANONYMIZER, ROLE_CHASSIS_CURATOR, ROLE_CHASSIS_ADMINISTRATOR" />
	<intercept-url pattern="/atombeat/content/media/**" method="PUT" access="ROLE_CHASSIS_SUBMITTER, ROLE_CHASSIS_ANONYMIZER, ROLE_CHASSIS_CURATOR, ROLE_CHASSIS_ADMINISTRATOR" />
	<intercept-url pattern="/atombeat/content/media/**" method="DELETE" access="ROLE_CHASSIS_ADMINISTRATOR" />

*/
	public SpringAtomAuthorisationTestCase() {
		super();
	}
   	public void testHome() throws Exception { 
   		for (String dir : homeDirs)
   			assertEquals(dir, HttpServletResponse.SC_OK, 
   					getResourceAs("/"+dir,"text/html", ALICE, PASSWORD));
   	}
   	
   	public void testOnlyOwnHome() throws Exception { 
   		for (String dir : homeDirs)
   			for (String user : users) {
   				if (user.indexOf(dir) != -1) 
   	   				assertEquals(dir + "/" + user, HttpServletResponse.SC_OK, 
   	   						getResourceAs("/"+dir,"text/html", user, PASSWORD));
   				else
   	   				assertEquals(dir + "/" + user, HttpServletResponse.SC_FORBIDDEN,
   	   						getResourceAs("/"+dir,"text/html", user, PASSWORD));
   					
   			}
   	}
   	
   	public void testGet() throws Exception { 
		for (String user : users) {
				assertEquals(user, HttpServletResponse.SC_OK, 
   						getResourceAs("/atombeat/content/studies","application/atom+xml", user, PASSWORD));
				assertEquals(user, HttpServletResponse.SC_OK, 
   						getResourceAs("/atombeat/content/submissions","application/atom+xml", user, PASSWORD));
				if (user.contains("submitter")) {
					assertEquals(user, HttpServletResponse.SC_FORBIDDEN, 
	   						getResourceAs("/atombeat/content/reviews","application/atom+xml", user, PASSWORD));
					assertEquals(user, HttpServletResponse.SC_FORBIDDEN, 
	   						getResourceAs("/atombeat/content/derivations","application/atom+xml", user, PASSWORD));
				} else {
					assertEquals(user, HttpServletResponse.SC_OK, 
	   						getResourceAs("/atombeat/content/derivations","application/atom+xml", user, PASSWORD));
				}
				assertEquals(user, HttpServletResponse.SC_OK, 
   						getResourceAs("/atombeat/content/media","application/atom+xml", user, PASSWORD));
		}
   	}

   	
   	public void testDelete() throws Exception { 
		for (String user : users) {
 			int expected = HttpServletResponse.SC_FORBIDDEN;
			assertEquals(user, expected, 
  						accessResourceAs("DELETE", "/atombeat/content/studies","application/atom+xml", user, PASSWORD));
			assertEquals(user, expected, 
   						accessResourceAs("DELETE", "/atombeat/content/submissions","application/atom+xml", user, PASSWORD));
			assertEquals(user, expected, 
   						accessResourceAs("DELETE", "/atombeat/content/reviews","application/atom+xml", user, PASSWORD));
			assertEquals(user, expected, 
   						accessResourceAs("DELETE", "/atombeat/content/derivations","application/atom+xml", user, PASSWORD));
			assertEquals(user, expected, 
   						accessResourceAs("DELETE", "/atombeat/content/media","application/atom+xml", user, PASSWORD));
		}
   	}
   	
    // BAD_REQUEST means we got through
   	public void testPut() throws Exception { 
		for (String user : users) {
 			int expected = HttpServletResponse.SC_FORBIDDEN;
			if (user.contains("submitter") || user.contains("administrator")) {
				expected = HttpServletResponse.SC_BAD_REQUEST;
			}
			assertEquals(user, expected, 
  						accessResourceAs("PUT", "/atombeat/content/studies", "application/atom+xml", user, PASSWORD));

			assertEquals(user, expected, 
   						accessResourceAs("PUT", "/atombeat/content/submissions", "application/atom+xml", user, PASSWORD));
			if (user.contains("anonymizer") || user.contains("curator")) {
				expected = HttpServletResponse.SC_BAD_REQUEST;
			}
			assertEquals(user, expected, 
						accessResourceAs("PUT", "/atombeat/content/media", "application/atom+xml", user, PASSWORD));
			if (user.contains("submitter")) {
				expected = HttpServletResponse.SC_FORBIDDEN;
			}
			assertEquals(user, expected, 
   						accessResourceAs("PUT", "/atombeat/content/reviews", "application/atom+xml", user, PASSWORD));
			assertEquals(user, expected, 
   						accessResourceAs("PUT", "/atombeat/content/derivations", "application/atom+xml", user, PASSWORD));
		}
   	}
   	
   	// 500 means we got through
   	public void testPost() throws Exception { 
		for (String user : users) {
 			int expected = 500;
			if (! user.contains("submitter")) {
				expected = HttpServletResponse.SC_FORBIDDEN;
			}
			assertEquals(user, expected, 
  						accessResourceAs("POST", "/atombeat/content/studies", "application/atom+xml", user, PASSWORD));

			assertEquals(user, expected, 
   						accessResourceAs("POST", "/atombeat/content/submissions", "application/atom+xml", user, PASSWORD));
			if (user.contains("anonymizer") || user.contains("curator")) {
				expected = 500;
			}
			assertEquals(user, expected, 
						accessResourceAs("POST", "/atombeat/content/media", "application/atom+xml", user, PASSWORD));
			if (user.contains("submitter")) {
				expected = HttpServletResponse.SC_FORBIDDEN;
			}
			assertEquals(user, expected, 
   						accessResourceAs("POST", "/atombeat/content/reviews", "application/atom+xml", user, PASSWORD));
			assertEquals(user, expected, 
   						accessResourceAs("POST", "/atombeat/content/derivations", "application/atom+xml", user, PASSWORD));
		}
   	}
   	
}
