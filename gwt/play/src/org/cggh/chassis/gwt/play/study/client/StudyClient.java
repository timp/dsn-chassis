/**
 * $Id$
 */
package org.cggh.chassis.gwt.play.study.client;

import org.cggh.chassis.gwt.lib.atom.client.format.AtomEntry;
import org.cggh.chassis.gwt.lib.atom.client.format.AtomFeed;
import org.cggh.chassis.gwt.lib.atom.client.format.AtomFormatException;
import org.cggh.chassis.gwt.lib.atom.client.format.AtomLink;
import org.cggh.chassis.gwt.lib.atom.client.protocol.GetFeedCallback;
import org.cggh.chassis.gwt.lib.atom.client.protocol.GetFeedRequestBuilder;
import org.cggh.chassis.gwt.lib.atom.client.protocol.PostEntryCallback;
import org.cggh.chassis.gwt.lib.atom.client.protocol.PostEntryRequestBuilder;
import org.cggh.chassis.gwt.lib.study.client.format.StudyEntry;
import org.cggh.chassis.gwt.lib.study.client.format.StudyFactory;
import org.cggh.chassis.gwt.lib.study.client.format.StudyFeed;
import org.cggh.chassis.gwt.lib.study.client.format.StudyFormatException;
import org.wwarn.chassis.gwt.lib.study.client.WwarnStudyEntry;
import org.wwarn.chassis.gwt.lib.study.client.WwarnStudyFactory;
import org.wwarn.chassis.gwt.lib.study.client.WwarnStudyFeed;

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
public class StudyClient implements EntryPoint {

	private Controller controller;
	private Renderer renderer;
//	private StudyFeed feed;
//	private StudyEntry entry;
	private WwarnStudyFeed feed;
	private WwarnStudyEntry entry;
	private InputElement inputElementCollectionURL;
	private ButtonElement buttonElementGetFeed;
	private DivElement divElementFeedContainer;
	private Button buttonGetFeed;
	private InputElement inputElementTitle;
	private TextAreaElement textareaElementSummary;
	private ButtonElement buttonElementPostEntry;
	private DivElement divElementEntryContainer;
	private Button buttonPostEntry;
	private InputElement inputElementSampleSize;

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
		inputElementSampleSize = InputElement.as(doc.getElementById("inputSampleSize"));
		textareaElementSummary = TextAreaElement.as(doc.getElementById("textareaSummary"));
		buttonElementPostEntry = ButtonElement.as(doc.getElementById("buttonPostEntry"));
		divElementEntryContainer = DivElement.as(doc.getElementById("divEntryContainer"));
		
		buttonPostEntry = Button.wrap(buttonElementPostEntry);
		buttonPostEntry.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				String collectionURL = inputElementCollectionURL.getValue();
				String title = inputElementTitle.getValue();
				String summary = textareaElementSummary.getValue();
				int sampleSize = Integer.parseInt(inputElementSampleSize.getValue());
				controller.postEntry(collectionURL, title, summary, sampleSize);
			}
			
		});
	}
	
	class Controller {
		
		public void getFeed(String collectionURL) {

//			StudyFactory factory = new StudyFactory();
			WwarnStudyFactory factory = new WwarnStudyFactory();

			GetFeedRequestBuilder builder = new GetFeedRequestBuilder(URL.encode(collectionURL), factory);

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
//					feed = StudyFeed.as(newFeed);
					feed = WwarnStudyFeed.as(newFeed);
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
		public void postEntry(String collectionURL, String title, String summary, int sampleSize) {
			
//			StudyFactory factory = new StudyFactory();
			WwarnStudyFactory factory = new WwarnStudyFactory();
			
			PostEntryRequestBuilder builder = new PostEntryRequestBuilder(URL.encode(collectionURL), factory);
			
			try {

//				StudyEntry newEntry = new StudyEntry();
				WwarnStudyEntry newEntry = new WwarnStudyEntry();
				newEntry.setTitle(title);
				newEntry.setSummary(summary);
				newEntry.setSampleSize(42);
				
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
//						entry = StudyEntry.as(returnedEntry);
						entry = WwarnStudyEntry.as(returnedEntry);
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
					html += renderEntry(StudyEntry.as(entry));
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

		public String renderEntry(StudyEntry entry) {
			String html = "";
			html += "<h2>"+entry.getTitle()+"</h2>";
			html += "<p><strong>summary:</strong> "+entry.getSummary()+"</p>";
			try {
				html += "<p><strong>sample size:</strong> "+entry.getSampleSize()+"</p>";
			} catch (StudyFormatException ex) {
				Window.alert("error getting sample size: "+ex.getLocalizedMessage());
			}
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
