package org.cggh.chassis.manta.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;

public abstract class CASUtil {

	public static String getProxyTicket(HttpServletRequest req,String target) {
		HttpSession httpSess = req.getSession(true);

		// Get CAS information
		Assertion assertion = (Assertion) httpSess
				.getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
		if (assertion == null) {
			return "";
		}
		//String username = assertion.getPrincipal().getName();

		String proxyticket = assertion.getPrincipal().getProxyTicketFor(
				target);

		if (proxyticket == null) {
			return (null);
		}
		return proxyticket;
	}
	
	public static String getUsername(HttpServletRequest req) {
		HttpSession httpSess = req.getSession(true);

		// Get CAS information
		Assertion assertion = (Assertion) httpSess
				.getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
		if (assertion == null) {
			return "";
		}
		String username = assertion.getPrincipal().getName();
		
		return username;
	}
}
