/**
 * $Id$
 */
package org.cggh.chassis.examples.atom.client;

import org.cggh.chassis.gwt.lib.atom.client.format.AtomEntry;
import org.cggh.chassis.gwt.lib.atom.client.format.AtomFormatException;

import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.TextAreaElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class PostEntryClickHandler implements ClickHandler {

	private InputElement inputElementCollectionURL;
	private InputElement inputElementTitle;
	private TextAreaElement textareaElementSummary;
	private Controller controller;

	public PostEntryClickHandler(
			Controller controller, 
			InputElement inputElementCollectionURL,
			InputElement inputElementTitle,
			TextAreaElement textareaElementSummary) {
		this.controller = controller;
		this.inputElementCollectionURL = inputElementCollectionURL;
		this.inputElementTitle = inputElementTitle;
		this.textareaElementSummary = textareaElementSummary;
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
	 */
	public void onClick(ClickEvent event) {
		String collectionURL = inputElementCollectionURL.getValue();
		String title = inputElementTitle.getValue();
		String summary = textareaElementSummary.getValue();
		AtomEntry entry;
		try {
			entry = new AtomEntry();
			entry.setTitle(title);
			entry.setSummary(summary);
			controller.postEntry(collectionURL, entry);
		} catch (AtomFormatException ex) {
			controller.setMessage("an unexpected error has occurred: "+ex.getLocalizedMessage());
		}
	}

}
