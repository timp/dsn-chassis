package org.cggh.chassis.rest.dao;

/**
 * @author timp
 * @since 2011-11-03
 */
public class NotFoundException extends Exception {

  public NotFoundException() {
  }

  public NotFoundException(String id) {
    super("No entry found with id '" + id + "'.");
  }

  public NotFoundException(Throwable cause) {
    super(cause);
  }

  public NotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

}
