/*
 * $Source: /usr/cvsroot/melati/melati/src/test/java/org/melati/servlet/test/MockHttpSession.java,v $
 * $Revision: 1.4 $
 *
 * Copyright (C) 2009 Tim Pizey
 *
 *
 * Contact details for copyright holder:
 *
 *     Tim Pizey <timp At paneris.org>
 *     http://paneris.org/~timp
 */

package org.cggh.chassis.generic.http.test;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

/**
 * @author timp
 * @since  26 Feb 2009
 *
 */
@SuppressWarnings("deprecation")
public class MockHttpSession implements HttpSession {
  
  private Hashtable<String,Object> values;

  /**
   * Constructor.
   */
  public MockHttpSession() {
    values = new Hashtable<String,Object>();
  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpSession#getAttribute(java.lang.String)
   */
  public Object getAttribute(String name) {
    return values.get(name);
    }

  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpSession#getAttributeNames()
   */
  public Enumeration<String> getAttributeNames() {
    throw new RuntimeException("TODO No one else has ever called this method."
        + " Do you really want to start now?");

  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpSession#getCreationTime()
   */
  public long getCreationTime() {
    throw new RuntimeException("TODO No one else has ever called this method."
        + " Do you really want to start now?");

  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpSession#getId()
   */
  public String getId() {
    return "1";
  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpSession#getLastAccessedTime()
   */
  public long getLastAccessedTime() {
    throw new RuntimeException("TODO No one else has ever called this method."
        + " Do you really want to start now?");

  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpSession#getMaxInactiveInterval()
   */
  public int getMaxInactiveInterval() {
    throw new RuntimeException("TODO No one else has ever called this method."
        + " Do you really want to start now?");

  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpSession#getServletContext()
   */
  public ServletContext getServletContext() {
    throw new RuntimeException("TODO No one else has ever called this method."
        + " Do you really want to start now?");

  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpSession#getSessionContext()
   */
  public HttpSessionContext getSessionContext() {
    throw new RuntimeException("TODO No one else has ever called this method."
        + " Do you really want to start now?");
  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpSession#getValue(java.lang.String)
   */
  public Object getValue(String name) {
    throw new RuntimeException("TODO No one else has ever called this method."
        + " Do you really want to start now?");

  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpSession#getValueNames()
   */
  public String[] getValueNames() {
    throw new RuntimeException("TODO No one else has ever called this method."
        + " Do you really want to start now?");

  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpSession#invalidate()
   */
  public void invalidate() {
    throw new RuntimeException("TODO No one else has ever called this method."
        + " Do you really want to start now?");

  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpSession#isNew()
   */
  public boolean isNew() {
    throw new RuntimeException("TODO No one else has ever called this method."
        + " Do you really want to start now?");

  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpSession#putValue(java.lang.String, java.lang.Object)
   */
  public void putValue(String name, Object value) {
    throw new RuntimeException("TODO No one else has ever called this method."
        + " Do you really want to start now?");

  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpSession#removeAttribute(java.lang.String)
   */
  public void removeAttribute(String name) {
    values.remove(name);
  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpSession#removeValue(java.lang.String)
   */
  public void removeValue(String name) {
    throw new RuntimeException("TODO No one else has ever called this method."
        + " Do you really want to start now?");

  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpSession#setAttribute(java.lang.String, java.lang.Object)
   */
  public void setAttribute(String name, Object value) {
    values.put(name, value);
  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpSession#setMaxInactiveInterval(int)
   */
  public void setMaxInactiveInterval(int interval) {
    throw new RuntimeException("TODO No one else has ever called this method."
        + " Do you really want to start now?");

  }

}
