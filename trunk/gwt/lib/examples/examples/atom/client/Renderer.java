/**
 * $Id$
 */
package examples.atom.client;

import org.cggh.chassis.gwt.lib.atom.client.format.AtomEntry;
import org.cggh.chassis.gwt.lib.atom.client.format.AtomFeed;
import org.cggh.chassis.gwt.lib.atom.client.format.AtomFormatException;
import org.cggh.chassis.gwt.lib.atom.client.format.AtomLink;

import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.TextAreaElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class Renderer {
	
	private Controller controller;
	private ButtonElement buttonElementGetFeed;
	private DivElement divElementFeedContainer;
	private Button buttonGetFeed;
	private InputElement inputElementCollectionURL;
	private InputElement inputElementTitle;
	private TextAreaElement textareaElementSummary;
	private ButtonElement buttonElementPostEntry;
	private DivElement divElementEntryContainer;
	private Button buttonPostEntry;
	private Element elementMessage;

	public Renderer(Controller controller) {
		this.controller = controller;
		this.initView();
	}
	
	/**
	 * TODO document me
	 * 
	 * @param feed
	 */
	public void onFeedChanged(AtomFeed feed) {
		String html = "";
		
		try {
			for (AtomEntry entry : feed.getEntries()) {
				html += "<hr/>";
				html += this.renderEntry(entry);
			}
		} catch (AtomFormatException ex) {
			Window.alert("atom format exception: "+ex.getLocalizedMessage());
		}

		divElementFeedContainer.setInnerHTML(html);
	}

	/**
	 * TODO document me
	 * 
	 * @param entry
	 * @return
	 */
	public HTML renderEntry(final AtomEntry entry) {
		String html = "<div>";
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
		html += "</ul></div>";
		HTML thing = new HTML(html);
		final Controller control = this.controller;
		thing.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				Window.alert("click on entry: "+entry.getId());
				control.setEdit(entry);
			}});
		
		return thing; 
	}

	/**
	 * TODO document me
	 * 
	 * @param entry
	 */
	public void onSelectedEntryChanged(AtomEntry entry) {
		// TODO Auto-generated method stub
	}

	public void initView() {

		Document doc = Document.get();

		inputElementCollectionURL = InputElement.as(doc.getElementById("inputCollectionURL"));
		buttonElementGetFeed = ButtonElement.as(doc.getElementById("buttonGetFeed"));
		divElementFeedContainer = DivElement.as(doc.getElementById("divFeedContainer"));
		inputElementTitle = InputElement.as(doc.getElementById("inputTitle"));
		textareaElementSummary = TextAreaElement.as(doc.getElementById("textareaSummary"));
		buttonElementPostEntry = ButtonElement.as(doc.getElementById("buttonPostEntry"));
		divElementEntryContainer = DivElement.as(doc.getElementById("divEntryContainer"));
		elementMessage = doc.getElementById("message");
				
		buttonGetFeed = Button.wrap(buttonElementGetFeed);
		ClickHandler buttonGetFeedClickHandler = new GetFeedClickHandler(controller, inputElementCollectionURL);
		buttonGetFeed.addClickHandler(buttonGetFeedClickHandler);
		
		buttonPostEntry = Button.wrap(buttonElementPostEntry);
		ClickHandler buttonPostEntryClickHandler = new PostEntryClickHandler(controller, inputElementCollectionURL, inputElementTitle, textareaElementSummary);
		buttonPostEntry.addClickHandler(buttonPostEntryClickHandler);
	}

	/**
	 * TODO document me
	 * 
	 * @param message
	 */
	public void onMessageChanged(String message) {
		elementMessage.setInnerHTML(message);
	}

}
