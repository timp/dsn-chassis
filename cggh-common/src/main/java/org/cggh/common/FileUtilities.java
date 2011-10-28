package org.cggh.common;

import java.io.File;
import java.net.URL;

/**
 *  A class containing various useful methods relating to files.
 *
 */
public class FileUtilities {

	/**
	 * This method looks for a resource using the getResource() method on
	 * various classes and class loaders.
	 * <br/>
	 * An Example use of this method:
	 *  <code>
	 *	URL url = FileUtilities.findResource(propfile,getClass());
	 *	if (url != null) {
	 *		f = new File(url.getFile());
	 *	}
	 *	if (f.exists()) {
	 *		is = new FileInputStream(f);
	 *		props.load(is);
	 *		is.close();
	 *	}
	 * </code>
	 *
	 * Can return a null if incorrect paramaters passed.
	 * 
	 *@param  name - the name of the resource to find
	 *@param  clazz - the class that is trying to find the resource
	 *@return the url of the resource file found
	 */
	public static URL findResource(String name, Class<?> clazz) {
		ClassLoader cl = null;
		URL url = null;

		// No resource name passed return null URL
		if (!ObjectUtilities.validString(name)) {
			return url;
		}

		// No clazz passed return null URL
		if (clazz == null) {
			return url;
		}

		url = clazz.getResource(name);
		if (url == null) {
			try {
				cl = clazz.getClassLoader();
				if (cl != null) {
					url = cl.getResource(name);
				}
			} catch (Exception e) {
				//Ignored
			}
		}

		if (url == null) {
			try {
				cl = Thread.currentThread().getContextClassLoader();
				if (cl != null) {
					url = cl.getResource(name);
				}
			} catch (Exception e) {
				//Ignored
			}
		}
		if (url == null) {
			try {
				cl = Thread.currentThread().getClass().getClassLoader();
				if (cl != null) {
					url = cl.getResource(name);
				}
			} catch (Exception e) {
				//Ignored
			}
		}
		return (url);
	}

	/**
	 * A utility method to return a URL corresponding to a file.
	 * <br/>
	 * The file is looked for in the following order:
	 * <br/>
	 * A plain file name
	 * <br/>
	 * A resource {@see #findResource(String,Class)}
	 * <br/>
	 * A URL.
	 * Can return a null if incorrect paramaters passed.
	 *@param  name - the name of the file to find
	 *@param  clazz - the class that is trying to find the file
	 *@return a URL pointing to the file, or null if nothing can be found
	*/
	public static URL findFileAsURL(String name, Class<?> clazz) {
		URL url = null;

		// No filename passed return null URL
		if (!ObjectUtilities.validString(name)) {
			return url;
		}

		// No clazz passed return null URL
		if (clazz == null) {
			return url;
		}
		
		// Now go on and look for the file
		File file = new File(name);

		// If the name isn't a file then look for it as a resource
		if (!file.exists() && clazz != null) {
			url = FileUtilities.findResource(name, clazz);
			if (url != null) {
				file = new File(url.getFile());
			}
		}

		try {
			//If the name isn't a file or a resource try as a URL
			if (!file.exists()) {
				url = new URL(name);
			} else {
				if (url == null) {
					url = new URL("file:///" + file.getAbsolutePath());
				}
			}
		} catch (java.net.MalformedURLException mue) {
			//Ignored
		}

		return (url);
	}
}
/*****************************************************************************
 *
 *
 *                         ELSEVIER - SCIENCE
 *
 *                            CONFIDENTIAL
 *
 *  This document is the property of Elsevier Science (ES),
 *  and its contents are proprietary to ES.   Reproduction in any form by
 *  anyone of the materials contained  herein  without  the  permission  of
 *  ES is prohibited.  Finders are  asked  to  return  this  document  to
 *  the following Elsevier Science location.
 *
 *      Elsevier Science
 *      655 Avenues of the Americas
 *      New York, NY 10010-5107
 *
 *  Copyright (c) 2002 by Elsevier Science, A member of the Reed Elsevier plc
 *  group.
 *
 *  All Rights Reserved.
 *
 *****************************************************************************/