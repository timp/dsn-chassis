/**
 * $Id$
 */
package examples.atom.client;

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
public class GetFeedClickHandler implements ClickHandler {

	private InputElement inputElementCollectionURL;
	private Controller controller;

	public GetFeedClickHandler(
			Controller controller, 
			InputElement inputElementCollectionURL) {
		this.controller = controller;
		this.inputElementCollectionURL = inputElementCollectionURL;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
	 */
	public void onClick(ClickEvent event) {
		String collectionURL = inputElementCollectionURL.getValue();
		controller.getFeed(collectionURL);
	}

}
