/**
 * 
 */
package org.cggh.chassis.generic.widget.client;

import com.google.gwt.user.client.ui.HTML;

/**
 * @author aliman
 *
 */
public class HtmlElements {


	public static HTML em(String content) {
		return new HTML("<em>"+content+"</em>");
	}

	public static HTML strong(String content) {
		return new HTML("<strong>"+content+"</strong>");
	}

	public static HTML p(String content) {
		return new HTML("<p>"+content+"</p>");
	}

	public static HTML h1(String titleContent) {
		return new HTML("<h1>"+titleContent+"</h1>");
	}

	public static HTML h2(String titleContent) {
		return new HTML("<h2>"+titleContent+"</h2>");
	}

	public static HTML h3(String titleContent) {
		return new HTML("<h3>"+titleContent+"</h3>");
	}

	public static HTML h4(String titleContent) {
		return new HTML("<h4>"+titleContent+"</h4>");
	}

	public static HTML h5(String titleContent) {
		return new HTML("<h5>"+titleContent+"</h5>");
	}

}
