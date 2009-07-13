/**
 * $Id$
 */
package org.cggh.chassis.gwt.play.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.PreElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
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
public class StudyServiceExerciser implements EntryPoint {

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {
		
		Document doc = Document.get();
		
		final InputElement servicePathInputElement = InputElement.as(doc.getElementById("servicePathInput"));
		final InputElement collectionPathInputElement = InputElement.as(doc.getElementById("collectionPathInput"));
		final InputElement collectionTitleInputElement = InputElement.as(doc.getElementById("collectionTitleInput"));
		ButtonElement getStudiesButtonElement = ButtonElement.as(doc.getElementById("getStudiesButton"));
		ButtonElement createCollectionButtonElement = ButtonElement.as(doc.getElementById("createCollectionButton"));

		Button getStudiesButton = Button.wrap(getStudiesButtonElement);
		Button createCollectionButton = Button.wrap(createCollectionButtonElement);

		Element responseOutputElement = Document.get().getElementById("responseOutput");
		final PreElement responseOutputPreElement = PreElement.as(responseOutputElement);
		
		getStudiesButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				
				String servicePath = servicePathInputElement.getValue();
				String collectionPath = collectionPathInputElement.getValue();
				getStudies(servicePath, collectionPath, responseOutputPreElement);

			}

		});
		
		createCollectionButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {

				String servicePath = servicePathInputElement.getValue();
				String collectionPath = collectionPathInputElement.getValue();
				String collectionTitle = collectionTitleInputElement.getValue();
				createCollection(servicePath, collectionPath, collectionTitle);
				
			}
			
		});
		
//		RootPanel appContainer = RootPanel.get("appContainer");
//		
//		VerticalPanel v = new VerticalPanel();
//		appContainer.add(v);
//		
//		HorizontalPanel hp1 = new HorizontalPanel();
//		Label l1 = new Label("study service path:");
//		hp1.add(l1);
//		TextBox servicePathTextBox = new TextBox();
//		hp1.add(servicePathTextBox);
//		v.add(hp1);
//		
//		HorizontalPanel hp2 = new HorizontalPanel();
//		Label l2 = new Label("study collection relative path:");
//		hp1.add(l2);
//		TextBox collectionPathTextBox = new TextBox();
//		hp1.add(collectionPathTextBox);
//		v.add(hp2);
//
////		v.add(new HTML("<hr/>"));
//		
//		HorizontalPanel hp3 = new HorizontalPanel();
//		Label l3 = new Label("retrieve collection feed:");
//		hp3.add(l3);
//		Button getFeedButton = new Button("go");
//		hp3.add(getFeedButton);
//		v.add(hp3);
//
////		v.add(new HTML("<hr/>"));
//
//		VerticalPanel vp4 = new VerticalPanel();
//		Label l41 = new Label("create new collection");
//		vp4.add(l41);
//		HorizontalPanel hp41 = new HorizontalPanel();
//		Label l42 = new Label("title:");
//		TextBox collectionTitleTextBox = new TextBox();
//		Button createCollectionButton = new Button("go");
//		hp41.add(l42);
//		hp41.add(collectionTitleTextBox);
//		hp41.add(createCollectionButton);
//		vp4.add(hp41);
//		v.add(vp4);

	}

	protected void getStudies(String servicePath, String collectionPath, final PreElement output) {
		Window.alert("getStudies "+servicePath+" "+collectionPath);
		
		String url = "http://"+Window.Location.getHost()+servicePath+collectionPath;
		Window.alert(url);
		
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(url));
		
		RequestCallback callback = new RequestCallback() {

			public void onError(Request request, Throwable exception) {
				Window.alert("onError: "+exception.getLocalizedMessage());
			}

			public void onResponseReceived(Request request, Response response) {
				output.setInnerText(response.getHeadersAsString()+"\n\n"+response.getText()/*.replaceAll("<", "&lt;").replaceAll(">", "&gt;")*/);
			}
			
		};
		
		builder.setCallback(callback);

		try {
			builder.send();
		} catch (RequestException ex) {
			Window.alert("request exception: "+ex.getLocalizedMessage());
			ex.printStackTrace();
		}
		
	}
	
	/**
	 * TODO document me
	 * 
	 * @param servicePath
	 * @param collectionPath
	 * @param collectionTitle
	 */
	protected void createCollection(String servicePath, String collectionPath,
			String collectionTitle) {
		// TODO Auto-generated method stub
		Window.alert("createCollection "+servicePath+" "+collectionPath+" "+collectionTitle);
	}

}
