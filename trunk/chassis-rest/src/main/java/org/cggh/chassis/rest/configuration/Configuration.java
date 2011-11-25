package org.cggh.chassis.rest.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author timp
 * @since 2011-11-25
 * 
 * A configuration object which expects to find a properties file 
 * either in $APP_HOME/conf or /etc/$APP_NAME.
 * 
 */
public class Configuration {

  private String homeVariableName;  
  private String configurationDirectoryName; 
  private String propertiesFileName;
  private Properties properties;
  
  public Configuration(String appName) {
    this(appName, null);
  }
  public Configuration(String appName, Properties defaults) {
    super();
    this.homeVariableName = appName.toUpperCase() + "_HOME";
    String envHome = System.getenv(this.homeVariableName);
    if (envHome == null) 
      this.configurationDirectoryName = "/etc/" + appName;
    else 
      this.configurationDirectoryName = envHome + "/conf";
    this.propertiesFileName = this.configurationDirectoryName + "/" + appName + ".properties";
    File propertiesfile = new File(this.propertiesFileName);
    if (propertiesfile.exists())
      this.properties = fromFile(propertiesfile, defaults);
    else
      this.properties = new Properties(defaults);
  }

  public String get(String key) {
    return properties.getProperty(key);
  }
  public String put(String key, String value) {
    return (String) properties.put(key, value);
  }
  
  public String getFileName() { 
    return propertiesFileName;
  }

  /**
   * @param existingFile must exist
   * @param defaults default Properties or null
   */
  public static Properties fromFile(File existingFile, Properties defaults) {
    InputStream data;
    try {
      data = new FileInputStream(existingFile);
    } catch (FileNotFoundException e1) {
      throw new IllegalArgumentException("Path (" + existingFile + ") does not exist");
    }
    Properties them = new Properties();
    try{
      them.load(data);
    } catch (IOException e) {
      throw new RuntimeException("Corrupt properties file `" + existingFile + "': " +
          e.getMessage());
    }

    return them;
  }
}
