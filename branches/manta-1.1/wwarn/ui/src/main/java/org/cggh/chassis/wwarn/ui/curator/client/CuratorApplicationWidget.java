package org.cggh.chassis.wwarn.ui.curator.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.AtomHelper;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.generic.widget.client.MapMemory;
import org.cggh.chassis.generic.widget.client.MultiWidget;
import org.cggh.chassis.generic.widget.client.WidgetMemory;import org.cggh.chassis.generic.widget.client.WidgetEventChannel;
import org.cggh.chassis.generic.widget.client.WidgetEventHandler;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;

/**
 * BE SURE TO EDIT THE TEMPLATE NOT THE RENDERED RESULT
 *
 * DELETE_TO_MANUALLY_EDIT
 *
 * @author timp
 *
 */
public class CuratorApplicationWidget 
		extends MultiWidget {

	private static final Log log = LogFactory.getLog(CuratorApplicationWidget.class);

	private CuratorHomeWidget curatorHomeWidget;
	private ViewStudyWidget viewStudyWidget;
	private ViewStudyQuestionnaireWidget viewStudyQuestionnaireWidget;
	private EditStudyQuestionnaireWidget editStudyQuestionnaireWidget;
	private ListStudyRevisionsWidget listStudyRevisionsWidget;
	private ViewStudyRevisionWidget viewStudyRevisionWidget;
	private UploadDataFilesWizardWidget uploadDataFilesWizardWidget;



	// CuratorHome
	public final WidgetEventChannel curatorHomeListStudiesViewStudyNavigationEventChannel = new WidgetEventChannel(this);

	// ViewStudy
	public final WidgetEventChannel viewStudyStudyActionsListStudiesNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel viewStudyStudyActionsViewStudyNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel viewStudyStudyActionsViewStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel viewStudyStudyActionsListStudyRevisionsNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel viewStudyStudyActionsEditStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel viewStudyStudyActionsUploadDataFilesWizardNavigationEventChannel = new WidgetEventChannel(this);

	// ViewStudyQuestionnaire
	public final WidgetEventChannel viewStudyQuestionnaireStudyActionsListStudiesNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel viewStudyQuestionnaireStudyActionsViewStudyNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel viewStudyQuestionnaireStudyActionsViewStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel viewStudyQuestionnaireStudyActionsListStudyRevisionsNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel viewStudyQuestionnaireStudyActionsEditStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel viewStudyQuestionnaireStudyActionsUploadDataFilesWizardNavigationEventChannel = new WidgetEventChannel(this);

	// EditStudyQuestionnaire
	public final WidgetEventChannel editStudyQuestionnaireStudyActionsListStudiesNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel editStudyQuestionnaireStudyActionsViewStudyNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel editStudyQuestionnaireStudyActionsViewStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel editStudyQuestionnaireStudyActionsListStudyRevisionsNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel editStudyQuestionnaireStudyActionsEditStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel editStudyQuestionnaireStudyActionsUploadDataFilesWizardNavigationEventChannel = new WidgetEventChannel(this);

	// ListStudyRevisions
	public final WidgetEventChannel listStudyRevisionsStudyActionsListStudiesNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel listStudyRevisionsStudyActionsViewStudyNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel listStudyRevisionsStudyActionsViewStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel listStudyRevisionsStudyActionsListStudyRevisionsNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel listStudyRevisionsStudyActionsEditStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel listStudyRevisionsStudyActionsUploadDataFilesWizardNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel listStudyRevisionsCurrentStudyRevisionViewCurrentStudyNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel listStudyRevisionsPriorStudyRevisionsListViewRevisionNavigationEventChannel = new WidgetEventChannel(this);

	// ViewStudyRevision
	public final WidgetEventChannel viewStudyRevisionStudyRevisionActionsViewStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel viewStudyRevisionStudyRevisionActionsListStudiesNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel viewStudyRevisionStudyRevisionActionsEditStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel viewStudyRevisionStudyRevisionActionsListStudyRevisionsNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel viewStudyRevisionStudyRevisionActionsNextRevisionNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel viewStudyRevisionStudyRevisionActionsPreviousRevisionNavigationEventChannel = new WidgetEventChannel(this);

	// UploadDataFilesWizard
	public final WidgetEventChannel uploadDataFilesWizardUploadCuratedDataFilesProceedFromStep1EventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel uploadDataFilesWizardSelectDerivationFilesProceedFromStep2EventChannel = new WidgetEventChannel(this);



	@Override
	public void refresh() {
		log.enter("refresh");
		this.curatorHomeWidget.refresh();
		log.leave();
	}

	@Override
	public void init() {
		super.init();
	}

	
	@Override
	public void renderMainChildren() {
		log.enter("renderMainChildren");

		this.curatorHomeWidget = new CuratorHomeWidget();
		this.mainChildren.add(this.curatorHomeWidget);

		// Set default child
		this.defaultChild = this.curatorHomeWidget;

		this.viewStudyWidget = new ViewStudyWidget();
		this.mainChildren.add(this.viewStudyWidget);

		this.viewStudyQuestionnaireWidget = new ViewStudyQuestionnaireWidget();
		this.mainChildren.add(this.viewStudyQuestionnaireWidget);

		this.editStudyQuestionnaireWidget = new EditStudyQuestionnaireWidget();
		this.mainChildren.add(this.editStudyQuestionnaireWidget);

		this.listStudyRevisionsWidget = new ListStudyRevisionsWidget();
		this.mainChildren.add(this.listStudyRevisionsWidget);

		this.viewStudyRevisionWidget = new ViewStudyRevisionWidget();
		this.mainChildren.add(this.viewStudyRevisionWidget);

		this.uploadDataFilesWizardWidget = new UploadDataFilesWizardWidget();
		this.mainChildren.add(this.uploadDataFilesWizardWidget);

		log.leave();

	}


	@Override
	public void registerHandlersForChildWidgetEvents() {
		log.enter("registerHandlersForChildWidgetEvents");
		super.registerHandlersForChildWidgetEvents();
		

		// CuratorHome events 
		log.debug("Adding CuratorHome>ListStudies>ViewStudyNavigation event handler");
		this.childWidgetEventHandlerRegistrations.add(
				curatorHomeWidget.listStudiesViewStudyNavigationEventChannel.addHandler(
						new WidgetEventHandler<ViewStudyNavigationEvent>() {
			@Override
			public void onEvent(ViewStudyNavigationEvent e) {
				
				log.enter("onEvent(ViewStudyNavigationEvent)");
				
				String studyUrl =  e.getStudyUrl();
				log.debug("Setting studyUrl to " + studyUrl);

				viewStudyWidget.setStudyUrl(studyUrl);
				
				
				Element studyEntry =  e.getStudyEntry();
				log.debug("Setting studyEntry to " + studyEntry);

				viewStudyWidget.setStudyEntry(studyEntry);
				

				setActiveChild(viewStudyWidget);
				
				log.leave();
			}
		}));



		// ViewStudy events 
		log.debug("Adding ViewStudy>StudyActions>ListStudiesNavigation event handler");
		this.childWidgetEventHandlerRegistrations.add(
				viewStudyWidget.studyActionsListStudiesNavigationEventChannel.addHandler(
						new WidgetEventHandler<ListStudiesNavigationEvent>() {
			@Override
			public void onEvent(ListStudiesNavigationEvent e) {
				
				log.enter("onEvent(ListStudiesNavigationEvent)");

				setActiveChild(curatorHomeWidget);
				
				log.leave();
			}
		}));


		log.debug("Adding ViewStudy>StudyActions>ViewStudyNavigation event handler");
		this.childWidgetEventHandlerRegistrations.add(
				viewStudyWidget.studyActionsViewStudyNavigationEventChannel.addHandler(
						new WidgetEventHandler<ViewStudyNavigationEvent>() {
			@Override
			public void onEvent(ViewStudyNavigationEvent e) {
				
				log.enter("onEvent(ViewStudyNavigationEvent)");
				
				String studyUrl =  e.getStudyUrl();
				log.debug("Setting studyUrl to " + studyUrl);

				viewStudyWidget.setStudyUrl(studyUrl);
				
				
				Element studyEntry =  e.getStudyEntry();
				log.debug("Setting studyEntry to " + studyEntry);

				viewStudyWidget.setStudyEntry(studyEntry);
				

				setActiveChild(viewStudyWidget);
				
				log.leave();
			}
		}));


		log.debug("Adding ViewStudy>StudyActions>ViewStudyQuestionnaireNavigation event handler");
		this.childWidgetEventHandlerRegistrations.add(
				viewStudyWidget.studyActionsViewStudyQuestionnaireNavigationEventChannel.addHandler(
						new WidgetEventHandler<ViewStudyQuestionnaireNavigationEvent>() {
			@Override
			public void onEvent(ViewStudyQuestionnaireNavigationEvent e) {
				
				log.enter("onEvent(ViewStudyQuestionnaireNavigationEvent)");
				
				String studyUrl =  e.getStudyUrl();
				log.debug("Setting studyUrl to " + studyUrl);

				viewStudyQuestionnaireWidget.setStudyUrl(studyUrl);
				
				
				Element studyEntry =  e.getStudyEntry();
				log.debug("Setting studyEntry to " + studyEntry);

				viewStudyQuestionnaireWidget.setStudyEntry(studyEntry);
				

				setActiveChild(viewStudyQuestionnaireWidget);
				
				log.leave();
			}
		}));


		log.debug("Adding ViewStudy>StudyActions>ListStudyRevisionsNavigation event handler");
		this.childWidgetEventHandlerRegistrations.add(
				viewStudyWidget.studyActionsListStudyRevisionsNavigationEventChannel.addHandler(
						new WidgetEventHandler<ListStudyRevisionsNavigationEvent>() {
			@Override
			public void onEvent(ListStudyRevisionsNavigationEvent e) {
				
				log.enter("onEvent(ListStudyRevisionsNavigationEvent)");
				
				String studyUrl =  e.getStudyUrl();
				log.debug("Setting studyUrl to " + studyUrl);

				listStudyRevisionsWidget.setStudyUrl(studyUrl);
				
				
				Element studyEntry =  e.getStudyEntry();
				log.debug("Setting studyEntry to " + studyEntry);

				listStudyRevisionsWidget.setStudyEntry(studyEntry);
				

				setActiveChild(listStudyRevisionsWidget);
				
				log.leave();
			}
		}));


		log.debug("Adding ViewStudy>StudyActions>EditStudyQuestionnaireNavigation event handler");
		this.childWidgetEventHandlerRegistrations.add(
				viewStudyWidget.studyActionsEditStudyQuestionnaireNavigationEventChannel.addHandler(
						new WidgetEventHandler<EditStudyQuestionnaireNavigationEvent>() {
			@Override
			public void onEvent(EditStudyQuestionnaireNavigationEvent e) {
				
				log.enter("onEvent(EditStudyQuestionnaireNavigationEvent)");
				
				String studyUrl =  e.getStudyUrl();
				log.debug("Setting studyUrl to " + studyUrl);

				editStudyQuestionnaireWidget.setStudyUrl(studyUrl);
				
				
				Element studyEntry =  e.getStudyEntry();
				log.debug("Setting studyEntry to " + studyEntry);

				editStudyQuestionnaireWidget.setStudyEntry(studyEntry);
				

				setActiveChild(editStudyQuestionnaireWidget);
				
				log.leave();
			}
		}));


		log.debug("Adding ViewStudy>StudyActions>UploadDataFilesWizardNavigation event handler");
		this.childWidgetEventHandlerRegistrations.add(
				viewStudyWidget.studyActionsUploadDataFilesWizardNavigationEventChannel.addHandler(
						new WidgetEventHandler<UploadDataFilesWizardNavigationEvent>() {
			@Override
			public void onEvent(UploadDataFilesWizardNavigationEvent e) {
				
				log.enter("onEvent(UploadDataFilesWizardNavigationEvent)");
				
				String studyUrl =  e.getStudyUrl();
				log.debug("Setting studyUrl to " + studyUrl);

				uploadDataFilesWizardWidget.studyUrl.set(studyUrl);
				
				
				Element studyEntry =  e.getStudyEntry();
				log.debug("Setting studyEntry to " + studyEntry);

				uploadDataFilesWizardWidget.studyEntry.set(studyEntry);
				

				setActiveChild(uploadDataFilesWizardWidget);
				
				log.leave();
			}
		}));



		// ViewStudyQuestionnaire events 
		log.debug("Adding ViewStudyQuestionnaire>StudyActions>ListStudiesNavigation event handler");
		this.childWidgetEventHandlerRegistrations.add(
				viewStudyQuestionnaireWidget.studyActionsListStudiesNavigationEventChannel.addHandler(
						new WidgetEventHandler<ListStudiesNavigationEvent>() {
			@Override
			public void onEvent(ListStudiesNavigationEvent e) {
				
				log.enter("onEvent(ListStudiesNavigationEvent)");

				setActiveChild(curatorHomeWidget);
				
				log.leave();
			}
		}));


		log.debug("Adding ViewStudyQuestionnaire>StudyActions>ViewStudyNavigation event handler");
		this.childWidgetEventHandlerRegistrations.add(
				viewStudyQuestionnaireWidget.studyActionsViewStudyNavigationEventChannel.addHandler(
						new WidgetEventHandler<ViewStudyNavigationEvent>() {
			@Override
			public void onEvent(ViewStudyNavigationEvent e) {
				
				log.enter("onEvent(ViewStudyNavigationEvent)");
				
				String studyUrl =  e.getStudyUrl();
				log.debug("Setting studyUrl to " + studyUrl);

				viewStudyWidget.setStudyUrl(studyUrl);
				
				
				Element studyEntry =  e.getStudyEntry();
				log.debug("Setting studyEntry to " + studyEntry);

				viewStudyWidget.setStudyEntry(studyEntry);
				

				setActiveChild(viewStudyWidget);
				
				log.leave();
			}
		}));


		log.debug("Adding ViewStudyQuestionnaire>StudyActions>ViewStudyQuestionnaireNavigation event handler");
		this.childWidgetEventHandlerRegistrations.add(
				viewStudyQuestionnaireWidget.studyActionsViewStudyQuestionnaireNavigationEventChannel.addHandler(
						new WidgetEventHandler<ViewStudyQuestionnaireNavigationEvent>() {
			@Override
			public void onEvent(ViewStudyQuestionnaireNavigationEvent e) {
				
				log.enter("onEvent(ViewStudyQuestionnaireNavigationEvent)");
				
				String studyUrl =  e.getStudyUrl();
				log.debug("Setting studyUrl to " + studyUrl);

				viewStudyQuestionnaireWidget.setStudyUrl(studyUrl);
				
				
				Element studyEntry =  e.getStudyEntry();
				log.debug("Setting studyEntry to " + studyEntry);

				viewStudyQuestionnaireWidget.setStudyEntry(studyEntry);
				

				setActiveChild(viewStudyQuestionnaireWidget);
				
				log.leave();
			}
		}));


		log.debug("Adding ViewStudyQuestionnaire>StudyActions>ListStudyRevisionsNavigation event handler");
		this.childWidgetEventHandlerRegistrations.add(
				viewStudyQuestionnaireWidget.studyActionsListStudyRevisionsNavigationEventChannel.addHandler(
						new WidgetEventHandler<ListStudyRevisionsNavigationEvent>() {
			@Override
			public void onEvent(ListStudyRevisionsNavigationEvent e) {
				
				log.enter("onEvent(ListStudyRevisionsNavigationEvent)");
				
				String studyUrl =  e.getStudyUrl();
				log.debug("Setting studyUrl to " + studyUrl);

				listStudyRevisionsWidget.setStudyUrl(studyUrl);
				
				
				Element studyEntry =  e.getStudyEntry();
				log.debug("Setting studyEntry to " + studyEntry);

				listStudyRevisionsWidget.setStudyEntry(studyEntry);
				

				setActiveChild(listStudyRevisionsWidget);
				
				log.leave();
			}
		}));


		log.debug("Adding ViewStudyQuestionnaire>StudyActions>EditStudyQuestionnaireNavigation event handler");
		this.childWidgetEventHandlerRegistrations.add(
				viewStudyQuestionnaireWidget.studyActionsEditStudyQuestionnaireNavigationEventChannel.addHandler(
						new WidgetEventHandler<EditStudyQuestionnaireNavigationEvent>() {
			@Override
			public void onEvent(EditStudyQuestionnaireNavigationEvent e) {
				
				log.enter("onEvent(EditStudyQuestionnaireNavigationEvent)");
				
				String studyUrl =  e.getStudyUrl();
				log.debug("Setting studyUrl to " + studyUrl);

				editStudyQuestionnaireWidget.setStudyUrl(studyUrl);
				
				
				Element studyEntry =  e.getStudyEntry();
				log.debug("Setting studyEntry to " + studyEntry);

				editStudyQuestionnaireWidget.setStudyEntry(studyEntry);
				

				setActiveChild(editStudyQuestionnaireWidget);
				
				log.leave();
			}
		}));


		log.debug("Adding ViewStudyQuestionnaire>StudyActions>UploadDataFilesWizardNavigation event handler");
		this.childWidgetEventHandlerRegistrations.add(
				viewStudyQuestionnaireWidget.studyActionsUploadDataFilesWizardNavigationEventChannel.addHandler(
						new WidgetEventHandler<UploadDataFilesWizardNavigationEvent>() {
			@Override
			public void onEvent(UploadDataFilesWizardNavigationEvent e) {
				
				log.enter("onEvent(UploadDataFilesWizardNavigationEvent)");
				
				String studyUrl =  e.getStudyUrl();
				log.debug("Setting studyUrl to " + studyUrl);

				uploadDataFilesWizardWidget.studyUrl.set(studyUrl);
				
				
				Element studyEntry =  e.getStudyEntry();
				log.debug("Setting studyEntry to " + studyEntry);

				uploadDataFilesWizardWidget.studyEntry.set(studyEntry);
				

				setActiveChild(uploadDataFilesWizardWidget);
				
				log.leave();
			}
		}));



		// EditStudyQuestionnaire events 
		log.debug("Adding EditStudyQuestionnaire>StudyActions>ListStudiesNavigation event handler");
		this.childWidgetEventHandlerRegistrations.add(
				editStudyQuestionnaireWidget.studyActionsListStudiesNavigationEventChannel.addHandler(
						new WidgetEventHandler<ListStudiesNavigationEvent>() {
			@Override
			public void onEvent(ListStudiesNavigationEvent e) {
				
				log.enter("onEvent(ListStudiesNavigationEvent)");

				setActiveChild(curatorHomeWidget);
				
				log.leave();
			}
		}));


		log.debug("Adding EditStudyQuestionnaire>StudyActions>ViewStudyNavigation event handler");
		this.childWidgetEventHandlerRegistrations.add(
				editStudyQuestionnaireWidget.studyActionsViewStudyNavigationEventChannel.addHandler(
						new WidgetEventHandler<ViewStudyNavigationEvent>() {
			@Override
			public void onEvent(ViewStudyNavigationEvent e) {
				
				log.enter("onEvent(ViewStudyNavigationEvent)");
				
				String studyUrl =  e.getStudyUrl();
				log.debug("Setting studyUrl to " + studyUrl);

				viewStudyWidget.setStudyUrl(studyUrl);
				
				
				Element studyEntry =  e.getStudyEntry();
				log.debug("Setting studyEntry to " + studyEntry);

				viewStudyWidget.setStudyEntry(studyEntry);
				

				setActiveChild(viewStudyWidget);
				
				log.leave();
			}
		}));


		log.debug("Adding EditStudyQuestionnaire>StudyActions>ViewStudyQuestionnaireNavigation event handler");
		this.childWidgetEventHandlerRegistrations.add(
				editStudyQuestionnaireWidget.studyActionsViewStudyQuestionnaireNavigationEventChannel.addHandler(
						new WidgetEventHandler<ViewStudyQuestionnaireNavigationEvent>() {
			@Override
			public void onEvent(ViewStudyQuestionnaireNavigationEvent e) {
				
				log.enter("onEvent(ViewStudyQuestionnaireNavigationEvent)");
				
				String studyUrl =  e.getStudyUrl();
				log.debug("Setting studyUrl to " + studyUrl);

				viewStudyQuestionnaireWidget.setStudyUrl(studyUrl);
				
				
				Element studyEntry =  e.getStudyEntry();
				log.debug("Setting studyEntry to " + studyEntry);

				viewStudyQuestionnaireWidget.setStudyEntry(studyEntry);
				

				setActiveChild(viewStudyQuestionnaireWidget);
				
				log.leave();
			}
		}));


		log.debug("Adding EditStudyQuestionnaire>StudyActions>ListStudyRevisionsNavigation event handler");
		this.childWidgetEventHandlerRegistrations.add(
				editStudyQuestionnaireWidget.studyActionsListStudyRevisionsNavigationEventChannel.addHandler(
						new WidgetEventHandler<ListStudyRevisionsNavigationEvent>() {
			@Override
			public void onEvent(ListStudyRevisionsNavigationEvent e) {
				
				log.enter("onEvent(ListStudyRevisionsNavigationEvent)");
				
				String studyUrl =  e.getStudyUrl();
				log.debug("Setting studyUrl to " + studyUrl);

				listStudyRevisionsWidget.setStudyUrl(studyUrl);
				
				
				Element studyEntry =  e.getStudyEntry();
				log.debug("Setting studyEntry to " + studyEntry);

				listStudyRevisionsWidget.setStudyEntry(studyEntry);
				

				setActiveChild(listStudyRevisionsWidget);
				
				log.leave();
			}
		}));


		log.debug("Adding EditStudyQuestionnaire>StudyActions>EditStudyQuestionnaireNavigation event handler");
		this.childWidgetEventHandlerRegistrations.add(
				editStudyQuestionnaireWidget.studyActionsEditStudyQuestionnaireNavigationEventChannel.addHandler(
						new WidgetEventHandler<EditStudyQuestionnaireNavigationEvent>() {
			@Override
			public void onEvent(EditStudyQuestionnaireNavigationEvent e) {
				
				log.enter("onEvent(EditStudyQuestionnaireNavigationEvent)");
				
				String studyUrl =  e.getStudyUrl();
				log.debug("Setting studyUrl to " + studyUrl);

				editStudyQuestionnaireWidget.setStudyUrl(studyUrl);
				
				
				Element studyEntry =  e.getStudyEntry();
				log.debug("Setting studyEntry to " + studyEntry);

				editStudyQuestionnaireWidget.setStudyEntry(studyEntry);
				

				setActiveChild(editStudyQuestionnaireWidget);
				
				log.leave();
			}
		}));


		log.debug("Adding EditStudyQuestionnaire>StudyActions>UploadDataFilesWizardNavigation event handler");
		this.childWidgetEventHandlerRegistrations.add(
				editStudyQuestionnaireWidget.studyActionsUploadDataFilesWizardNavigationEventChannel.addHandler(
						new WidgetEventHandler<UploadDataFilesWizardNavigationEvent>() {
			@Override
			public void onEvent(UploadDataFilesWizardNavigationEvent e) {
				
				log.enter("onEvent(UploadDataFilesWizardNavigationEvent)");
				
				String studyUrl =  e.getStudyUrl();
				log.debug("Setting studyUrl to " + studyUrl);

				uploadDataFilesWizardWidget.studyUrl.set(studyUrl);
				
				
				Element studyEntry =  e.getStudyEntry();
				log.debug("Setting studyEntry to " + studyEntry);

				uploadDataFilesWizardWidget.studyEntry.set(studyEntry);
				

				setActiveChild(uploadDataFilesWizardWidget);
				
				log.leave();
			}
		}));



		// ListStudyRevisions events 
		log.debug("Adding ListStudyRevisions>StudyActions>ListStudiesNavigation event handler");
		this.childWidgetEventHandlerRegistrations.add(
				listStudyRevisionsWidget.studyActionsListStudiesNavigationEventChannel.addHandler(
						new WidgetEventHandler<ListStudiesNavigationEvent>() {
			@Override
			public void onEvent(ListStudiesNavigationEvent e) {
				
				log.enter("onEvent(ListStudiesNavigationEvent)");

				setActiveChild(curatorHomeWidget);
				
				log.leave();
			}
		}));


		log.debug("Adding ListStudyRevisions>StudyActions>ViewStudyNavigation event handler");
		this.childWidgetEventHandlerRegistrations.add(
				listStudyRevisionsWidget.studyActionsViewStudyNavigationEventChannel.addHandler(
						new WidgetEventHandler<ViewStudyNavigationEvent>() {
			@Override
			public void onEvent(ViewStudyNavigationEvent e) {
				
				log.enter("onEvent(ViewStudyNavigationEvent)");
				
				String studyUrl =  e.getStudyUrl();
				log.debug("Setting studyUrl to " + studyUrl);

				viewStudyWidget.setStudyUrl(studyUrl);
				
				
				Element studyEntry =  e.getStudyEntry();
				log.debug("Setting studyEntry to " + studyEntry);

				viewStudyWidget.setStudyEntry(studyEntry);
				

				setActiveChild(viewStudyWidget);
				
				log.leave();
			}
		}));


		log.debug("Adding ListStudyRevisions>StudyActions>ViewStudyQuestionnaireNavigation event handler");
		this.childWidgetEventHandlerRegistrations.add(
				listStudyRevisionsWidget.studyActionsViewStudyQuestionnaireNavigationEventChannel.addHandler(
						new WidgetEventHandler<ViewStudyQuestionnaireNavigationEvent>() {
			@Override
			public void onEvent(ViewStudyQuestionnaireNavigationEvent e) {
				
				log.enter("onEvent(ViewStudyQuestionnaireNavigationEvent)");
				
				String studyUrl =  e.getStudyUrl();
				log.debug("Setting studyUrl to " + studyUrl);

				viewStudyQuestionnaireWidget.setStudyUrl(studyUrl);
				
				
				Element studyEntry =  e.getStudyEntry();
				log.debug("Setting studyEntry to " + studyEntry);

				viewStudyQuestionnaireWidget.setStudyEntry(studyEntry);
				

				setActiveChild(viewStudyQuestionnaireWidget);
				
				log.leave();
			}
		}));


		log.debug("Adding ListStudyRevisions>StudyActions>ListStudyRevisionsNavigation event handler");
		this.childWidgetEventHandlerRegistrations.add(
				listStudyRevisionsWidget.studyActionsListStudyRevisionsNavigationEventChannel.addHandler(
						new WidgetEventHandler<ListStudyRevisionsNavigationEvent>() {
			@Override
			public void onEvent(ListStudyRevisionsNavigationEvent e) {
				
				log.enter("onEvent(ListStudyRevisionsNavigationEvent)");
				
				String studyUrl =  e.getStudyUrl();
				log.debug("Setting studyUrl to " + studyUrl);

				listStudyRevisionsWidget.setStudyUrl(studyUrl);
				
				
				Element studyEntry =  e.getStudyEntry();
				log.debug("Setting studyEntry to " + studyEntry);

				listStudyRevisionsWidget.setStudyEntry(studyEntry);
				

				setActiveChild(listStudyRevisionsWidget);
				
				log.leave();
			}
		}));


		log.debug("Adding ListStudyRevisions>StudyActions>EditStudyQuestionnaireNavigation event handler");
		this.childWidgetEventHandlerRegistrations.add(
				listStudyRevisionsWidget.studyActionsEditStudyQuestionnaireNavigationEventChannel.addHandler(
						new WidgetEventHandler<EditStudyQuestionnaireNavigationEvent>() {
			@Override
			public void onEvent(EditStudyQuestionnaireNavigationEvent e) {
				
				log.enter("onEvent(EditStudyQuestionnaireNavigationEvent)");
				
				String studyUrl =  e.getStudyUrl();
				log.debug("Setting studyUrl to " + studyUrl);

				editStudyQuestionnaireWidget.setStudyUrl(studyUrl);
				
				
				Element studyEntry =  e.getStudyEntry();
				log.debug("Setting studyEntry to " + studyEntry);

				editStudyQuestionnaireWidget.setStudyEntry(studyEntry);
				

				setActiveChild(editStudyQuestionnaireWidget);
				
				log.leave();
			}
		}));


		log.debug("Adding ListStudyRevisions>StudyActions>UploadDataFilesWizardNavigation event handler");
		this.childWidgetEventHandlerRegistrations.add(
				listStudyRevisionsWidget.studyActionsUploadDataFilesWizardNavigationEventChannel.addHandler(
						new WidgetEventHandler<UploadDataFilesWizardNavigationEvent>() {
			@Override
			public void onEvent(UploadDataFilesWizardNavigationEvent e) {
				
				log.enter("onEvent(UploadDataFilesWizardNavigationEvent)");
				
				String studyUrl =  e.getStudyUrl();
				log.debug("Setting studyUrl to " + studyUrl);

				uploadDataFilesWizardWidget.studyUrl.set(studyUrl);
				
				
				Element studyEntry =  e.getStudyEntry();
				log.debug("Setting studyEntry to " + studyEntry);

				uploadDataFilesWizardWidget.studyEntry.set(studyEntry);
				

				setActiveChild(uploadDataFilesWizardWidget);
				
				log.leave();
			}
		}));


		log.debug("Adding ListStudyRevisions>CurrentStudyRevision>ViewCurrentStudyNavigation event handler");
		this.childWidgetEventHandlerRegistrations.add(
				listStudyRevisionsWidget.currentStudyRevisionViewCurrentStudyNavigationEventChannel.addHandler(
						new WidgetEventHandler<ViewCurrentStudyNavigationEvent>() {
			@Override
			public void onEvent(ViewCurrentStudyNavigationEvent e) {
				
				log.enter("onEvent(ViewCurrentStudyNavigationEvent)");
				
				String studyUrl =  e.getStudyUrl();
				log.debug("Setting studyUrl to " + studyUrl);

				viewStudyWidget.setStudyUrl(studyUrl);
				
				
				Element studyEntry =  e.getStudyEntry();
				log.debug("Setting studyEntry to " + studyEntry);

				viewStudyWidget.setStudyEntry(studyEntry);
				

				setActiveChild(viewStudyWidget);
				
				log.leave();
			}
		}));


		log.debug("Adding ListStudyRevisions>PriorStudyRevisionsList>ViewRevisionNavigation event handler");
		this.childWidgetEventHandlerRegistrations.add(
				listStudyRevisionsWidget.priorStudyRevisionsListViewRevisionNavigationEventChannel.addHandler(
						new WidgetEventHandler<ViewRevisionNavigationEvent>() {
			@Override
			public void onEvent(ViewRevisionNavigationEvent e) {
				
				log.enter("onEvent(ViewRevisionNavigationEvent)");
				
				String studyUrl =  e.getStudyUrl();
				log.debug("Setting studyUrl to " + studyUrl);

				viewStudyRevisionWidget.setStudyUrl(studyUrl);
				
				
				Element studyEntry =  e.getStudyEntry();
				log.debug("Setting studyEntry to " + studyEntry);

				viewStudyRevisionWidget.setStudyEntry(studyEntry);
				

				setActiveChild(viewStudyRevisionWidget);
				
				log.leave();
			}
		}));



		// ViewStudyRevision events 
		log.debug("Adding ViewStudyRevision>StudyRevisionActions>ViewStudyQuestionnaireNavigation event handler");
		this.childWidgetEventHandlerRegistrations.add(
				viewStudyRevisionWidget.studyRevisionActionsViewStudyQuestionnaireNavigationEventChannel.addHandler(
						new WidgetEventHandler<ViewStudyQuestionnaireNavigationEvent>() {
			@Override
			public void onEvent(ViewStudyQuestionnaireNavigationEvent e) {
				
				log.enter("onEvent(ViewStudyQuestionnaireNavigationEvent)");
				
				String studyUrl =  e.getStudyUrl();
				log.debug("Setting studyUrl to " + studyUrl);

				viewStudyQuestionnaireWidget.setStudyUrl(studyUrl);
				
				
				Element studyEntry =  e.getStudyEntry();
				log.debug("Setting studyEntry to " + studyEntry);

				viewStudyQuestionnaireWidget.setStudyEntry(studyEntry);
				

				setActiveChild(viewStudyQuestionnaireWidget);
				
				log.leave();
			}
		}));


		log.debug("Adding ViewStudyRevision>StudyRevisionActions>ListStudiesNavigation event handler");
		this.childWidgetEventHandlerRegistrations.add(
				viewStudyRevisionWidget.studyRevisionActionsListStudiesNavigationEventChannel.addHandler(
						new WidgetEventHandler<ListStudiesNavigationEvent>() {
			@Override
			public void onEvent(ListStudiesNavigationEvent e) {
				
				log.enter("onEvent(ListStudiesNavigationEvent)");

				setActiveChild(curatorHomeWidget);
				
				log.leave();
			}
		}));


		log.debug("Adding ViewStudyRevision>StudyRevisionActions>EditStudyQuestionnaireNavigation event handler");
		this.childWidgetEventHandlerRegistrations.add(
				viewStudyRevisionWidget.studyRevisionActionsEditStudyQuestionnaireNavigationEventChannel.addHandler(
						new WidgetEventHandler<EditStudyQuestionnaireNavigationEvent>() {
			@Override
			public void onEvent(EditStudyQuestionnaireNavigationEvent e) {
				
				log.enter("onEvent(EditStudyQuestionnaireNavigationEvent)");
				
				String studyUrl =  e.getStudyUrl();
				log.debug("Setting studyUrl to " + studyUrl);

				editStudyQuestionnaireWidget.setStudyUrl(studyUrl);
				
				
				Element studyEntry =  e.getStudyEntry();
				log.debug("Setting studyEntry to " + studyEntry);

				editStudyQuestionnaireWidget.setStudyEntry(studyEntry);
				

				setActiveChild(editStudyQuestionnaireWidget);
				
				log.leave();
			}
		}));


		log.debug("Adding ViewStudyRevision>StudyRevisionActions>ListStudyRevisionsNavigation event handler");
		this.childWidgetEventHandlerRegistrations.add(
				viewStudyRevisionWidget.studyRevisionActionsListStudyRevisionsNavigationEventChannel.addHandler(
						new WidgetEventHandler<ListStudyRevisionsNavigationEvent>() {
			@Override
			public void onEvent(ListStudyRevisionsNavigationEvent e) {
				
				log.enter("onEvent(ListStudyRevisionsNavigationEvent)");
				
				String studyUrl =  e.getStudyUrl();
				log.debug("Setting studyUrl to " + studyUrl);

				listStudyRevisionsWidget.setStudyUrl(studyUrl);
				
				
				Element studyEntry =  e.getStudyEntry();
				log.debug("Setting studyEntry to " + studyEntry);

				listStudyRevisionsWidget.setStudyEntry(studyEntry);
				

				setActiveChild(listStudyRevisionsWidget);
				
				log.leave();
			}
		}));


		log.debug("Adding ViewStudyRevision>StudyRevisionActions>NextRevisionNavigation event handler");
		this.childWidgetEventHandlerRegistrations.add(
				viewStudyRevisionWidget.studyRevisionActionsNextRevisionNavigationEventChannel.addHandler(
						new WidgetEventHandler<NextRevisionNavigationEvent>() {
			@Override
			public void onEvent(NextRevisionNavigationEvent e) {
				
				log.enter("onEvent(NextRevisionNavigationEvent)");
				
				String studyUrl =  e.getStudyUrl();
				log.debug("Setting studyUrl to " + studyUrl);

				viewStudyRevisionWidget.setStudyUrl(studyUrl);
				
				
				Element studyEntry =  e.getStudyEntry();
				log.debug("Setting studyEntry to " + studyEntry);

				viewStudyRevisionWidget.setStudyEntry(studyEntry);
				

				setActiveChild(viewStudyRevisionWidget);
				
				log.leave();
			}
		}));


		log.debug("Adding ViewStudyRevision>StudyRevisionActions>PreviousRevisionNavigation event handler");
		this.childWidgetEventHandlerRegistrations.add(
				viewStudyRevisionWidget.studyRevisionActionsPreviousRevisionNavigationEventChannel.addHandler(
						new WidgetEventHandler<PreviousRevisionNavigationEvent>() {
			@Override
			public void onEvent(PreviousRevisionNavigationEvent e) {
				
				log.enter("onEvent(PreviousRevisionNavigationEvent)");
				
				String studyUrl =  e.getStudyUrl();
				log.debug("Setting studyUrl to " + studyUrl);

				viewStudyRevisionWidget.setStudyUrl(studyUrl);
				
				
				Element studyEntry =  e.getStudyEntry();
				log.debug("Setting studyEntry to " + studyEntry);

				viewStudyRevisionWidget.setStudyEntry(studyEntry);
				

				setActiveChild(viewStudyRevisionWidget);
				
				log.leave();
			}
		}));


		log.leave();
	}



	@Override
	public void setActiveChild(Widget child, boolean memorise) {
		log.enter("setActiveChild");
		
		super.setActiveChild(child, memorise);
		
		// FIXME 
		// Only Delegating widgets refresh themselves
		if (child == this.curatorHomeWidget) {
			((ChassisWidget)child).refresh();
		}
		if (child == this.uploadDataFilesWizardWidget) {
			((ChassisWidget)child).refresh();
		}
		log.leave();
	}

	

	
}
