/**
 * $Id$
 */
package org.cggh.chassis.gwt.play.client;

import org.cggh.chassis.gwt.lib.atom.client.format.AtomFormatException;
import org.cggh.chassis.gwt.lib.study.client.format.StudyFeed;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class StudyTester implements EntryPoint {

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {

		StudyFeed feed = null;
		try {
			feed = new StudyFeed();
		} catch (AtomFormatException ex1) {
			// TODO Auto-generated catch block
			ex1.printStackTrace();
		}
		Window.alert(feed.toString());
		
		feed.setTitle("foo studies from gwt");
		Window.alert(feed.toString());

		String url = "http://localhost/eXist-1.2.6-rev9165/atom/edit/studiesfromgwt";
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, URL.encode(url));
		builder.setRequestData(feed.toString());
		builder.setHeader("Content-Type", "application/atom+xml;charset=utf-8");
		builder.setHeader("Content-Length", Integer.toString(feed.toString().length()));
		
		RequestCallback callback = new RequestCallback() {

			public void onError(Request request, Throwable exception) {
				Window.alert("onError: "+exception.getLocalizedMessage());
			}

			public void onResponseReceived(Request request, Response response) {
				Window.alert("onResponseReceived: "+response.getStatusCode()+" "+response.getStatusText()+"\n\n"+response.getHeadersAsString()+"\n\n"+response.getText());
			}
			
		};
		
		builder.setCallback(callback);
		
		try {
			builder.send();
		} catch (RequestException ex) {
			Window.alert("request exception: "+ex.getLocalizedMessage());
			ex.printStackTrace();
		}
		
		builder = new RequestBuilder(RequestBuilder.GET, URL.encode(url));
		
		callback = new RequestCallback() {

			public void onError(Request request, Throwable exception) {
				Window.alert("onError: "+exception.getLocalizedMessage());
			}

			public void onResponseReceived(Request request, Response response) {
				Window.alert("onResponseReceived: "+response.getStatusCode()+" "+response.getStatusText()+"\n\n"+response.getHeadersAsString()+"\n\n"+response.getText());
				
				StudyFeed feed = null;
				try {
					feed = new StudyFeed(response.getText());
				} catch (AtomFormatException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
				Window.alert("title: "+feed.getTitle()+"\nid: "+feed.getId()+"\nupdated: "+feed.getUpdated());
			}
			
		};

		try {
			builder.send();
		} catch (RequestException ex) {
			Window.alert("request exception: "+ex.getLocalizedMessage());
			ex.printStackTrace();
		}
				
	}

}
