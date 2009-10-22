/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.studyquestionnaire.client;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.format.impl.StudyFactoryImpl;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.AtomServiceImpl;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.twisted.client.Function;
import org.cggh.chassis.generic.xquestion.client.XQuestionnaire;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

/**
 * @author aliman
 *
 */
public class StudyQuestionnaireWidget extends Composite {

	
	
	
	private FlowPanel canvas;
	private FlowPanel questionnairePanel = new FlowPanel();
	private XQuestionnaire questionnaire;
	private Log log = LogFactory.getLog(this.getClass());
	private FlowPanel actionsPanel;
	private StudyEntry studyEntry;
	private AtomService service;
	private Button saveButton;
	private Button editButton;




	public StudyQuestionnaireWidget() {
		log.enter("<constructor>");
		
		this.service = new AtomServiceImpl(new StudyFactoryImpl());
		
		this.canvas = new FlowPanel();
		this.canvas.add(new HTML("<h2>Study Questionnaire</h2>"));
		this.canvas.add(this.questionnairePanel);
		
		this.actionsPanel = new FlowPanel();
		
		this.saveButton = new Button("Update Study Questionnaire");
		this.saveButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				
				String url = Configuration.getStudyFeedURL() + studyEntry.getEditLink().getHref();
				Deferred<AtomEntry> def = service.putEntry(url, studyEntry);

				def.addCallback(new Function<StudyEntry, StudyEntry>() {

					public StudyEntry apply(StudyEntry in) {
						setEntry(in, true);
						return in;
					}
					
				});

				def.addErrback(new Function<Throwable, Throwable>() {

					public Throwable apply(Throwable in) {
						log.error("error saving study questionnaire", in);
						return in;
					}

				});

				// TODO Auto-generated method stub
				
			}
			
		});
		this.actionsPanel.add(this.saveButton);
		
		this.editButton = new Button("Edit Study Questionnaire");
		this.editButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				setEntry(studyEntry, false);
			}
			
		});
		this.actionsPanel.add(this.editButton);
		
		this.canvas.add(this.actionsPanel);
		
		
		Deferred<XQuestionnaire> deferredQuestionnaire = XQuestionnaire.load(Configuration.getStudyQuestionnaireURL());

		deferredQuestionnaire.addCallback(new Function<XQuestionnaire, XQuestionnaire>() {

			private Log log = LogFactory.getLog(this.getClass());
			public XQuestionnaire apply(XQuestionnaire in) {
				log.enter("apply");
				questionnaire = in;
				
				log.debug("adding questionnaire to canvas");
				questionnairePanel.add(questionnaire);

				log.leave();
				return in;
			}
			
		});
		
		deferredQuestionnaire.addErrback(new Function<Throwable, Throwable>() {

			public Throwable apply(Throwable in) {
				log.error("error loading study questionnaire", in);
				return in;
			}

		});
		
		
		this.initWidget(this.canvas);
		
		log.leave();
	}

	
	
	
	/**
	 * @param studyEntry
	 * @param readOnly
	 */
	public void setEntry(StudyEntry studyEntry, boolean readOnly) {
		log.enter("setEntry");
		
		if (readOnly) {
			this.saveButton.setVisible(false);
			this.editButton.setVisible(true);
		}
		else {
			this.editButton.setVisible(false);
			this.saveButton.setVisible(true);
		}
		
		this.studyEntry = studyEntry;
		
		log.debug("initialising questionnaire");
		questionnaire.init(studyEntry.getStudy().getElement(), readOnly);
		
		log.leave();
	}
	
	
	
}
