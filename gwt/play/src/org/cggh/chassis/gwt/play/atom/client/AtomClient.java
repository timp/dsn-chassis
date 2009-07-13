/**
 * $Id$
 */
package org.cggh.chassis.gwt.play.atom.client;

import org.cggh.chassis.gwt.lib.atom.client.format.AtomEntry;
import org.cggh.chassis.gwt.lib.atom.client.format.AtomFeed;
import org.cggh.chassis.gwt.lib.atom.client.format.AtomFormatException;
import org.cggh.chassis.gwt.lib.atom.client.format.AtomLink;
import org.cggh.chassis.gwt.lib.atom.client.protocol.GetFeedCallback;
import org.cggh.chassis.gwt.lib.atom.client.protocol.GetFeedRequestBuilder;
import org.cggh.chassis.gwt.lib.atom.client.protocol.PostEntryCallback;
import org.cggh.chassis.gwt.lib.atom.client.protocol.PostEntryRequestBuilder;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.TextAreaElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class AtomClient implements EntryPoint {

	private Controller controller;
	private Renderer renderer;
	private AtomFeed feed;
	private AtomEntry entry;
	private InputElement inputElementCollectionURL;
	private ButtonElement buttonElementGetFeed;
	private DivElement divElementFeedContainer;
	private Button buttonGetFeed;
	private InputElement inputElementTitle;
	private TextAreaElement textareaElementSummary;
	private ButtonElement buttonElementPostEntry;
	private DivElement divElementEntryContainer;
	private Button buttonPostEntry;

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {

		controller = new Controller();
		renderer = new Renderer();
		
		Document doc = Document.get();

		inputElementCollectionURL = InputElement.as(doc.getElementById("inputCollectionURL"));
		buttonElementGetFeed = ButtonElement.as(doc.getElementById("buttonGetFeed"));
		divElementFeedContainer = DivElement.as(doc.getElementById("divFeedContainer"));
		
		buttonGetFeed = Button.wrap(buttonElementGetFeed);
		buttonGetFeed.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				String collectionURL = inputElementCollectionURL.getValue();
				controller.getFeed(collectionURL);
			}
			
		});
		
		inputElementTitle = InputElement.as(doc.getElementById("inputTitle"));
		textareaElementSummary = TextAreaElement.as(doc.getElementById("textareaSummary"));
		buttonElementPostEntry = ButtonElement.as(doc.getElementById("buttonPostEntry"));
		divElementEntryContainer = DivElement.as(doc.getElementById("divEntryContainer"));
		
		buttonPostEntry = Button.wrap(buttonElementPostEntry);
		buttonPostEntry.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				String collectionURL = inputElementCollectionURL.getValue();
				String title = inputElementTitle.getValue();
				String summary = textareaElementSummary.getValue();
				controller.postEntry(collectionURL, title, summary);
			}
			
		});
	}
	
	class Controller {
		
		public void getFeed(String collectionURL) {

//			Window.alert("Controller.getFeed :: collectionURL: "+collectionURL);
//			
			GetFeedRequestBuilder builder = new GetFeedRequestBuilder(URL.encode(collectionURL));
			
			GetFeedCallback callback = new GetFeedCallback() {

				public void onError(Request request, Throwable exception) {
					Window.alert("onError(Request, Throwable): "+exception.getLocalizedMessage());
				}
	
				public void onError(Request request, Response response, Throwable exception) {
					Window.alert("onError(Request, Response, Throwable): "+exception.getLocalizedMessage());
				}
	
				public void onFailure(Request request, Response response) {
					Window.alert("onFailure: "+response.getStatusCode()+"\n\n"+response.getText());
				}
	
				public void onSuccess(Request request, Response response, AtomFeed newFeed) {
					feed = newFeed;
					renderer.onFeedChanged();
				}
				
			};
			
			builder.setCallback(callback);
			
			try {
				builder.send();
			} catch (RequestException ex) {
				Window.alert("request exception: "+ex.getLocalizedMessage());
			}

		}

		/**
		 * TODO document me
		 * 
		 * @param title
		 * @param summary
		 */
		public void postEntry(String collectionURL, String title, String summary) {
			
			PostEntryRequestBuilder builder = new PostEntryRequestBuilder(URL.encode(collectionURL));
			
			try {

				AtomEntry newEntry = new AtomEntry();
				newEntry.setTitle(title);
				newEntry.setSummary(summary);
				
				builder.setEntry(newEntry);
				
				PostEntryCallback callback = new PostEntryCallback() {

					public void onError(Request request, Throwable exception) {
						Window.alert("onError(Request, Throwable): "+exception.getLocalizedMessage());
					}

					public void onError(Request request, Response response, Throwable exception) {
						Window.alert("onError(Request, Response, Throwable): "+exception.getLocalizedMessage());
					}

					public void onFailure(Request request, Response response) {
						Window.alert("onFailure: "+response.getStatusCode()+"\n\n"+response.getText());
					}

					public void onSuccess(Request request, Response response, AtomEntry returnedEntry) {
//						Window.alert("onSuccess");
						entry = returnedEntry;
						renderer.onEntryChanged();
					}

				};
				
				builder.setCallback(callback);
				
				builder.send();

			} catch (RequestException ex) {
				Window.alert("request exception: "+ex.getLocalizedMessage());
			} catch (AtomFormatException ex) {
				Window.alert("atom format exception: "+ex.getLocalizedMessage());
			}
		}
		
	}
	
	class Renderer {

		/**
		 * TODO document me
		 * 
		 */
		public void onFeedChanged() {
			
			String html = "";
			
			try {
				for (AtomEntry entry : feed.getEntries()) {
					html += "<hr/>";
					html += renderEntry(entry);
				}
			} catch (AtomFormatException ex) {
				Window.alert("atom format exception: "+ex.getLocalizedMessage());
			}

			divElementFeedContainer.setInnerHTML(html);
			
		}
		
		/**
		 * TODO document me
		 * 
		 */
		public void onEntryChanged() {
			String html = renderEntry(entry);
			divElementEntryContainer.setInnerHTML(html);
		}

		public String renderEntry(AtomEntry entry) {
			String html = "";
			html += "<h2>"+entry.getTitle()+"</h2>";
			html += "<p><strong>summary:</strong> "+entry.getSummary()+"</p>";
			html += "<p><small><strong>id:</strong> "+entry.getId()+"<br/><strong>published:</strong> "+entry.getPublished()+"<br/><strong>updated:</strong> "+entry.getUpdated()+"</small></p>";
			html += "<ul>";
			for (AtomLink link : entry.getLinks()) {
				html += "<li><small><strong>link:</strong>";
				html += "<br/>[href] "+link.getHref();
				html += "<br/>[rel] "+link.getRel();
				html += "<br/>[type] "+link.getType();
				html += "</small></li>";
			}
			html += "</ul>";
			return html;
		}
	}

}
