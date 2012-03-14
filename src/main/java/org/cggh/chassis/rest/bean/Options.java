package org.cggh.chassis.rest.bean;

import java.util.Properties;

/**
 * @author timp
 * @since 2011-11-30
 */
public class Options {

  private Properties choices;

  public Properties getChoices() {
    return choices;
  }

  public void setChoices(Properties choices) {
    this.choices = choices;
  }
  
  public String get(String key) { 
    return choices.getProperty(key);
  }
}
