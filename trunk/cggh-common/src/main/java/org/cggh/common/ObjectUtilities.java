package org.cggh.common;

public class ObjectUtilities {

	public static boolean validString(String xmlString) {
		boolean ret = true;
		if (xmlString == null || xmlString.length() == 0) {
			ret = false;
		}
		return ret;
	}

}
