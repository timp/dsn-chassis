/**
 * 
 */
package spike.xqs.example.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.xquestion.client.XQuestionnaire;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.PreElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author aliman
 *
 */
public class SpikeXqsExampleEntryPoint implements EntryPoint {

	private Log log = LogFactory.getLog(this.getClass());
	private XQuestionnaire questionnaire;

	
	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {
		log.enter("onModuleLoad");
		
		Button loadButton = new Button();
		loadButton.setText("load");
		loadButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				loadQuestionnaire();
			}
		});
		RootPanel.get("loader").add(loadButton);

		Button loadDataButton = new Button();
		loadDataButton.setText("load");
		loadDataButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				loadData();
			}
		});
		RootPanel.get("loaddata").add(loadDataButton);

		Button loadDataReadOnlyButton = new Button();
		loadDataReadOnlyButton.setText("load (read only)");
		loadDataReadOnlyButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				loadDataReadOnly();
			}
		});
		RootPanel.get("loaddata").add(loadDataReadOnlyButton);

		Button getDataButton = new Button();
		getDataButton.setText("get form data");
		getDataButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				String data = questionnaire.getModel().getElement().toString();
				PreElement dataElement = PreElement.as(RootPanel.get("data").getElement());
				dataElement.setInnerText(data);
			}
			
		});
		RootPanel.get("controls").add(getDataButton);
		
		

		loadQuestionnaire();
		
		log.leave();
	}

	
	
	private void loadQuestionnaire() {

		String qloc = InputElement.as(DOM.getElementById("qloc")).getValue();
		Deferred<XQuestionnaire> d = XQuestionnaire.load(qloc);
		
		d.addCallback(new Function<XQuestionnaire,XQuestionnaire>() {

			public XQuestionnaire apply(XQuestionnaire in) {
				RootPanel qp = RootPanel.get("questionnaire");
				qp.clear();
				qp.add(in);
				in.init();
				questionnaire = in;
				return in;
			}
			
		});
		
		d.addErrback(new Function<Throwable, Throwable>() {

			public Throwable apply(Throwable in) {
				Window.alert("an error has occurred: "+in.getLocalizedMessage());
				log.debug("errback", in);
				return in;
			}

		});

	}



	private void loadData() {

		String dloc = InputElement.as(DOM.getElementById("dloc")).getValue();

		Deferred<Void> d = XQuestionnaire.loadData(questionnaire, dloc);

		d.addErrback(new Function<Throwable, Throwable>() {

			public Throwable apply(Throwable in) {
				Window.alert("an error has occurred: "+in.getLocalizedMessage());
				log.debug("errback", in);
				return in;
			}

		});

	}

	
	
	private void loadDataReadOnly() {

		String dloc = InputElement.as(DOM.getElementById("dloc")).getValue();

		Deferred<Void> d = XQuestionnaire.loadDataReadOnly(questionnaire, dloc);

		d.addErrback(new Function<Throwable, Throwable>() {

			public Throwable apply(Throwable in) {
				Window.alert("an error has occurred: "+in.getLocalizedMessage());
				log.debug("errback", in);
				return in;
			}

		});

	}

}
