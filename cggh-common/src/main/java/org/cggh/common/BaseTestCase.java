package org.cggh.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.Vector;
import org.apache.log4j.Logger;
import junit.framework.TestCase;


/**
 * All Test Cases should derive from this class.
 * 
 * The environment properties for running a test are put into
 * a file called "environment.properties" in the current working directory.
 * 
 * Optionally, you can set the environment properties to be in another
 * file by setting the "environment.properties" Java property. For example,
 * <br/>
 * java -Denvironment.properties=/home/bob/myprops.properties ...
 * <br/>
 * As a final resort the properties file will be looked for 
 * using the {@see FileUtilities#findResource} method.
 * <br/>
 * This effectively means that the properties files will be 
 * looked for in the same directory as the test class.
 * <br/>
 * The Java property workspace.home will replace the string ${workspace.home}
 * in the environment.properties file. This value defaults to X:\workspace
 * <br/>
 * This class is responsible for setting up the runtime environment.
 * <br/>
 * The class is in main rather than test because
 * it is part of the testing infrastructure rather than an actual test.
 */
public class BaseTestCase extends TestCase {
	/** The name of a system property set to true if the init method has been
	 * called. Is the name of this class.*/
	public static final String TEST_CASE_STRING = BaseTestCase.class.getName();

	/** The default name for the environment properties file: 
	 * environment.properties */
	public static final String DEFAULT_PROPERTIES_FILE_NAME =
		"environment.properties";
	/** The default name for system property to change the 
	 * environment properties file: environment.properties */
	public static final String PROPERTIES_FILE_NAME_PROPERTY =
		"environment.properties";
	
	/** The default path for the workspace X:\workspace */
	public static final String DEFAULT_WORKSPACE =	"X:\\workspace";

	/** The name of the System Property to change the workspace home*/
	public static final String WORKSPACE_PROPERTY = "workspace.home";

	/** Logger used in debug mode to list the properties read*/
	private static Logger c_logger = Logger.getLogger(BaseTestCase.class.getName());

	/**
	 * Initialize the System properties from the environment.properties file
	 */
	private void init() {
		String propfile =
			System.getProperty(
				PROPERTIES_FILE_NAME_PROPERTY,
				DEFAULT_PROPERTIES_FILE_NAME);
		InputStream is = null;
			/** Properties read from a file*/
		Vector loadedProps = new Vector();
		
		if (propfile != null && !propfile.equals("")) {
			try {
				Properties props = new Properties();
				File f = new File(propfile);
				if (!f.exists()) {
					URL url = FileUtilities.findResource(propfile, getClass());
					if (url != null) {
						f = new File(url.getFile());
					}
				}
				if (f.exists()) {
					is = new FileInputStream(f);
				} else {
					if (c_logger.isDebugEnabled()) {
						c_logger.debug("Properties file not found:" + propfile);
					}
					return;
				}
				Enumeration files = loadedProps.elements();
				String abPath = f.getAbsolutePath();
				while (files.hasMoreElements()) {
					String s = (String) files.nextElement();
					if (s.equals(abPath)) {
						return;
					}
				}
				loadedProps.add(abPath);
				if (c_logger.isDebugEnabled()) {
					c_logger.debug("Loading properties from:" 
												+ f.getAbsolutePath());
				}

				props.load(is);
				String workSpaceHome = System.getProperty(WORKSPACE_PROPERTY, 
														DEFAULT_WORKSPACE);
				String wshVar = "${workspace.home}";
				int pos = 0;
				for (Enumeration propsenum = props.propertyNames(); 
				propsenum.hasMoreElements();) {
					String key = (String) propsenum.nextElement();
					String value = props.getProperty(key);

					while ((pos = value.indexOf(wshVar)) >= 0) {
						StringBuffer sb = new StringBuffer(value);
						sb.replace(pos, pos + wshVar.length(), workSpaceHome);
						value = sb.toString();
					}
					if (c_logger.isDebugEnabled()) {
						c_logger.debug("Setting property:" + key + "=" + value);
					}
					System.setProperty(key, value);
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}

		}
		System.setProperty(TEST_CASE_STRING, "true");
	}

	/**
	 * Creates a new test case and initializes any System properties
	 * associated with the test
	 * @param name java.lang.String
	 */
	public BaseTestCase(String name) {
		super(name);
		init();
	}

	/**
	 * Returns a System property
	 * <p>
	 * If the property is not found, then throws an exception.
	 * @param name Specifies the property that is desired.
	 * @return the value of the property
	 * @throws MissingResourceException if the property is not found
	 */
	protected String getSysProperty(String name) 
									throws MissingResourceException {
		String ret = getSysProperty(name, null);
		if (ret == null) {
			throw (
				new MissingResourceException(
					"Could not find the test property:",
					this.getName(),
					name));
		}
		return (ret);
	}

	/**
	 * Returns a System property
	 * <p>
	 * @param name Specifies the property that is desired.
	 * @param defval If the property does not exist, then the default 
	 * is returned.
	 * @return the value of the property or the default value if it does
	 * not exist
	 */
	protected String getSysProperty(String name, String defval) {
		String val = System.getProperty(name, defval);
		return val;
	}
}