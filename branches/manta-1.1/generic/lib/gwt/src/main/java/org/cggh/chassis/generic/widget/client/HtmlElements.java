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


	public static HTML emWidget(String content) {
		return new HTML(em(content));
	}
	public static String em(String content) {
		return "<em>"+content+"</em>";
	}

	public static HTML strongWidget(String content) {
		return new HTML(strong(content));
	}
	public static String strong(String content) {
		return "<strong>"+content+"</strong>";
	}

	public static HTML pWidget(String content) {
		return new HTML(p(content));
	}
	public static String p(String content) {
		return "<p>"+content+"</p>";
	}

	public static HTML h1Widget(String titleContent) {
		return new HTML(h1(titleContent));
	}
	public static String h1(String titleContent) {
		return "<h1>"+titleContent+"</h1>";
	}

	public static HTML h2Widget(String titleContent) {
		return new HTML(h2(titleContent));
	}
	public static String h2(String titleContent) {
		return "<h2>"+titleContent+"</h2>";
	}

	public static HTML h3Widget(String titleContent) {
		return new HTML(h3(titleContent));
	}
	public static String h3(String titleContent) {
		return "<h3>"+titleContent+"</h3>";
	}
	
	public static HTML h4Widget(String titleContent) {
		return new HTML(h4(titleContent));
	}
	public static String h4(String titleContent) {
		return "<h4>"+titleContent+"</h4>";
	}
	
	public static HTML h5Widget(String titleContent) {
		return new HTML(h5(titleContent));
	}
	public static String h5(String titleContent) {
		return "<h5>"+titleContent+"</h5>";
	}
	

	public static String li(String listItemContent) { 
		return "<li>"+listItemContent+"</li>";
	}
}
