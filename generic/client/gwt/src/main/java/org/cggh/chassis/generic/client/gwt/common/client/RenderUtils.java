/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.common.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.async.client.Functional;
import org.cggh.chassis.generic.atom.client.AtomEntry;
import org.cggh.chassis.generic.atomext.shared.ChassisConstants;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author aliman
 *
 */
public class RenderUtils {

	
	
	
	/**
	 * Render a flex table from a list of widget arrays.
	 * 
	 * @param in the list of widget arrays to put into a flex table
	 * 
	 * @return a flex table
	 */
	public static FlexTable renderFlexTable(List<Widget[]> in) {
		FlexTable out = new FlexTable();
		for (int r=0; r<in.size(); r++) {
			Widget[] row = in.get(r);
			for (int c=0; c<row.length; c++) {
				Widget w = row[c];
				out.setWidget(r, c, w);
			}
		}
		return out;
	}
	
	
	
	
	/**
	 * Render a collection of objects as a list of widget arrays for use as rows
	 * in a table, using the given row generator function.
	 *  
	 * @param <T> the type of object to generate rows from
	 * @param in the input collection of objects to generate rows from
	 * @param rowGenerator the function to generate a row from each object in the input collection
	 * @return a list of widget arrays for use as rows in a table
	 */
	public static <T> List<Widget[]> renderAsRows(Collection<T> in, Function<T, Widget[]> rowGenerator) {
		List<Widget[]> out = new ArrayList<Widget[]>();
		Functional.map(in, out, rowGenerator);
		return out;
	}
	
	
	
	
	/**
	 * Render an array of labels using an array of strings. Labels will not be inline.
	 * 
	 * @param in the array of strings to use as label text
	 * @return an array of labels with label text from input strings
	 */
	public static Label[] renderLabels(String[] in) {
		return RenderUtils.renderLabels(in, null, false);
	}
	
	
	
	
	/**
	 * Render an array of labels using an array of strings.
	 * 
	 * @param in the array of strings to use as label text
	 * @param inline if true, labels will be inline
	 * @return an array of labels with label text from input strings
	 */
	public static Label[] renderLabels(String[] in, boolean inline) {
		return RenderUtils.renderLabels(in, null, inline);
	}
	
	
	
	
	/**
	 * Render an array of labels using an array of strings. Labels will not be inline.
	 * 
	 * @param in the array of strings to use as label text
	 * @param styleName the style name (i.e., CSS class name) to add to each label
	 * @return an array of labels with label text from input strings
	 */
	public static Label[] renderLabels(String[] in, String styleName) {
		return RenderUtils.renderLabels(in, styleName, false);
	}

	
	
	
	
	/**
	 * Render an array of labels using an array of strings.
	 * 
	 * @param in the array of strings to use as label text
	 * @param styleName the style name (i.e., CSS class name) to add to each label
	 * @param inline if true, labels will be inline
	 * @return an array of labels with label text from input strings
	 */
	public static Label[] renderLabels(String[] in, String styleName, boolean inline) {
		Label[] out = new Label[in.length];
		for (int i=0; i<in.length; i++) {
			Label l;
			if (inline) l = new InlineLabel(in[i]);
			else l = new Label(in[i]);
			if (styleName != null) l.addStyleName(styleName);
			out[i] = l;
		}
		return out;
	}
	
	
	
	
	/**
	 * Add the style name to all widgets in the input collection.
	 * 
	 * @param in the collection of widgets to add the style name to
	 * @param styleName the style name (i.e., CSS class name) to add
	 */
	public static void addStyleName(Collection<Widget> in, String styleName) {
		for (Widget w : in) {
			w.addStyleName(styleName);
		}
	}

	
	
	
	/**
	 * Add the style name to all widgets in the input array.
	 * 
	 * @param in the array of widgets to add the style name to
	 * @param styleName the style name (i.e., CSS class name) to add
	 */
	public static void addStyleName(Widget[] in, String styleName) {
		for (Widget w : in) {
			w.addStyleName(styleName);
		}
	}

	
	
	
	/**
	 * Truncate a string to a given cutoff length, appending "..." if truncated.
	 * 
	 * @param in the string to truncate if too long
	 * @param cutoff the number of characters to truncate to
	 * @return the string truncated if too long, otherwise the string unchanged
	 */
	public static String truncate(String in, int cutoff) {
		if (in.length() > cutoff) {
			in = in.substring(0, cutoff) + "...";
		}
		return in;
	}
	
	
	
	
	
	/**
	 * Render a string from a collection of authors by concatenating their emails.
	 * 
	 * @param authors the authors to render
	 * @param delimiter the delimiter to use to separate each author in the collection
	 * @return a string concatenation of the authors' emails
	 */
	public static String renderAtomAuthorsAsDelimitedEmailString(Collection<org.cggh.chassis.generic.atom.client.AtomAuthor> authors, String delimiter) {
		return RenderUtils.concatenate(RenderUtils.getEmails(authors), delimiter);
	}
	
	

	
	/**
	 * Render a string from a collection of authors by concatenating their emails,
	 * using commas as the delimiter.
	 * 
	 * @param authors the authors to render
	 * @return a string of the authors' emails separated by commas
	 */
	public static String renderAtomAuthorsAsCommaDelimitedEmailString(Collection<org.cggh.chassis.generic.atom.client.AtomAuthor> authors) {
		return RenderUtils.renderAtomAuthorsAsDelimitedEmailString(authors, ", ");
	}
	
	
	
	
	
	
	/**
	 * Render a collection of authors as a label, where the label text is a comma
	 * delimited list of author emails.
	 * 
	 * @param authors the authors to render
	 * @param inline if true, return an inline label, otherwise a normal label
	 * @return a label
	 */
	public static Label renderAtomAuthorsAsLabel(Collection<org.cggh.chassis.generic.atom.client.AtomAuthor> authors, boolean inline) {
		String text = (authors == null) ? "" : RenderUtils.renderAtomAuthorsAsCommaDelimitedEmailString(authors);
		if (inline) return new InlineLabel(text);
		else return new Label(text);
	}
	
	
	
	public static Label renderAtomAuthorsAsLabel(AtomEntry entry, boolean inline) {
		return RenderUtils.renderAtomAuthorsAsLabel(entry.getAuthors(), inline);
	}
	
	
	
	
	/**
	 * Render a collection of module ids as a string concatenation of the module
	 * labels.
	 * 
	 * @param ids the ids of the modules to render
	 * @param idsToLabels a map of ids to labels
	 * @param delimiter the delimiter to use
	 * @return a string concatenation of the module labels
	 */
	public static String renderModulesAsDelimitedString(Collection<String> ids, Map<String,String> idsToLabels, String delimiter) {
		Collection<String> labels = new ArrayList<String>();
		if (ids != null && idsToLabels != null) {
			Functional.map(ids, labels, idsToLabels);
		}
		return RenderUtils.concatenate(labels, delimiter);
	}
	

	
	
	/**
	 * Render a collection of module ids as a string concatenation of the module
	 * labels, using ", " as the delimiter.
	 * 
	 * @param ids the ids of the modules to render
	 * @param idsToLabels a map of ids to labels
	 * @return a string concatenation of the module labels
	 */
	public static String renderModulesAsCommaDelimitedString(Collection<String> ids, Map<String,String> idsToLabels) {
		return RenderUtils.renderModulesAsDelimitedString(ids, idsToLabels, ", ");
	}

	
	
	
	
	/**
	 * Render a collection of module ids as a label where the text is a string
	 * concatenation of the module labels.
	 * 
	 * @param ids the ids of the modules to render
	 * @param idsToLabels a map of ids to labels
	 * @param inline if true, return an inline label, otherwise return a normal label
	 * @return
	 */
	public static Label renderModulesAsLabel(Collection<String> ids, Map<String,String> idsToLabels, boolean inline) {
		String text = (ids == null || idsToLabels == null) ? "" : RenderUtils.renderModulesAsCommaDelimitedString(ids, idsToLabels);
		if (inline) return new InlineLabel(text);
		else return new Label(text);
	}
	

	
	
	/**
	 * Concatenate a collection of strings using the given delimiter.
	 * 
	 * @param in the collection of strings to concatenate
	 * @param delimiter the delimiter to separate each string in the input collection
	 * @return a string concatenation of the input collection
	 */
	public static String concatenate(Collection<String> in, String delimiter) {
		String out = "";
		for (Iterator<String> it = in.iterator(); it.hasNext(); ) {
			out += it.next();
			if (it.hasNext()) {
				out += delimiter;
			}
		}
		return out;
	}
	
	
	
	
	/**
	 * Get a list of emails for the given atom authors.
	 * 
	 * @param in the list of authors to get emails from
	 * @return a list of emails as strings
	 */
	public static List<String> getEmails(Collection<org.cggh.chassis.generic.atom.client.AtomAuthor> in) {
		List<String> out = new ArrayList<String>();
		if (in != null) {
			for (org.cggh.chassis.generic.atom.client.AtomAuthor a : in) {
				String email = a.getEmail();
				if (email != null) out.add(email);
			}
		}
		return out;
	}
	
	
	
	
	/**
	 * Render an anchor with the given text to use as an action.
	 * 
	 * @param text the anchor text
	 * @param h a click handler to add to the anchor
	 * @return an anchor with the given text and the given click handler added
	 */
	public static Anchor renderActionAsAnchor(String text, ClickHandler h) {
		Anchor out = RenderUtils.renderActionAsAnchor(text);
		out.addClickHandler(h);
		return out;
	}
	
	
	
	
	/**
	 * Render an anchor with the given text to use as an action.
	 * 
	 * @param text the anchor text
	 * @return an anchor with the given text
	 */
	public static Anchor renderActionAsAnchor(String text) {
		Anchor out = new Anchor();
		out.setText(text);
		out.addStyleName(CommonStyles.COMMON_ACTION);
		return out;
	}
	
	
	
	
	
	/**
	 * Render a default actions panel using the given array of widgets as actions.
	 * 
	 * @param actions the array of widgets to use as actions
	 * @return a panel with the default heading and style name set
	 */
	public static FlowPanel renderActionsPanel(Widget[] actions) {
		FlowPanel out = new FlowPanel();
		out.addStyleName(CommonStyles.COMMON_ACTIONS);
		out.add(new HTML("<h3>Actions</h3>")); // TODO use I18N resources
		for (Widget w : actions) {
			out.add(w);
		}
		return out;
	}
	
	
	
	
	/**
	 * Render a default property panel, using the given property name and the
	 * given widget as the value holder, adding the given style name to the
	 * value holder if not null.
	 * 
	 * @param propertyName the property name
	 * @param valueHolder the widget that will hold the property value
	 * @param valueHolderStyleName a custom style name to add to the value holder
	 * @return
	 */
	public static FlowPanel renderPropertyPanel(String propertyName, Widget valueHolder, String valueHolderStyleName) {
		FlowPanel out = new FlowPanel();
		out.add(new InlineLabel(propertyName + ": ")); 
		valueHolder.addStyleName(CommonStyles.COMMON_ANSWER);
		if (valueHolderStyleName != null) valueHolder.addStyleName(valueHolderStyleName);
		out.add(valueHolder);
		out.addStyleName(CommonStyles.COMMON_QUESTION);
		return out;
	}




	/**
	 * Render a default title property panel, using the
	 * given widget as the value holder, adding the given style name to the
	 * value holder if not null.
	 * 
	 * @param propertyName the property name
	 * @param valueHolder the widget that will hold the property value
	 * @param valueHolderStyleName a custom style name to add to the value holder
	 * @return
	 */
	public static FlowPanel renderTitlePropertyPanel(Widget valueHolder, String valueHolderStyleName) {
		return RenderUtils.renderPropertyPanel("Title", valueHolder, valueHolderStyleName); // TODO use I18N resources
	}
	
	
	
	
	/**
	 * Render a default summary property panel, using the
	 * given widget as the value holder, adding the given style name to the
	 * value holder if not null.
	 * 
	 * @param propertyName the property name
	 * @param valueHolder the widget that will hold the property value
	 * @param valueHolderStyleName a custom style name to add to the value holder
	 * @return
	 */
	public static FlowPanel renderSummaryPropertyPanel(Widget valueHolder, String valueHolderStyleName) {
		return RenderUtils.renderPropertyPanel("Summary", valueHolder, valueHolderStyleName); // TODO use I18N resources
	}




	/**
	 * Render a default modules property panel, using the
	 * given widget as the value holder, adding the given style name to the
	 * value holder if not null.
	 * 
	 * @param propertyName the property name
	 * @param valueHolder the widget that will hold the property value
	 * @param valueHolderStyleName a custom style name to add to the value holder
	 * @return
	 */
	public static FlowPanel renderModulesPropertyPanel(Widget valueHolder, String valueHolderStyleName) {
		return RenderUtils.renderPropertyPanel("Modules", valueHolder, valueHolderStyleName); // TODO use I18N resources
	}
	
	
	

	/**
	 * Render a default owners property panel, using the
	 * given widget as the value holder, adding the given style name to the
	 * value holder if not null.
	 * 
	 * @param propertyName the property name
	 * @param valueHolder the widget that will hold the property value
	 * @param valueHolderStyleName a custom style name to add to the value holder
	 * @return
	 */
	public static FlowPanel renderOwnersPropertyPanel(Widget valueHolder, String valueHolderStyleName) {
		return RenderUtils.renderPropertyPanel("Owners", valueHolder, valueHolderStyleName); // TODO use I18N resources
	}
	
	
	

	/**
	 * Render a default created property panel, using the
	 * given widget as the value holder, adding the given style name to the
	 * value holder if not null.
	 * 
	 * @param propertyName the property name
	 * @param valueHolder the widget that will hold the property value
	 * @param valueHolderStyleName a custom style name to add to the value holder
	 * @return
	 */
	public static FlowPanel renderCreatedPropertyPanel(Widget valueHolder, String valueHolderStyleName) {
		return RenderUtils.renderPropertyPanel("Created", valueHolder, valueHolderStyleName); // TODO use I18N resources
	}
	

	
	
	
	/**
	 * Render a default updated property panel, using the
	 * given widget as the value holder, adding the given style name to the
	 * value holder if not null.
	 * 
	 * @param propertyName the property name
	 * @param valueHolder the widget that will hold the property value
	 * @param valueHolderStyleName a custom style name to add to the value holder
	 * @return
	 */
	public static FlowPanel renderUpdatedPropertyPanel(Widget valueHolder, String valueHolderStyleName) {
		return RenderUtils.renderPropertyPanel("Updated", valueHolder, valueHolderStyleName); // TODO use I18N resources
	}
	
	
	

	/**
	 * Render a default id property panel, using the
	 * given widget as the value holder, adding the given style name to the
	 * value holder if not null.
	 * 
	 * @param propertyName the property name
	 * @param valueHolder the widget that will hold the property value
	 * @param valueHolderStyleName a custom style name to add to the value holder
	 * @return
	 */
	public static FlowPanel renderIdPropertyPanel(Widget valueHolder, String valueHolderStyleName) {
		return RenderUtils.renderPropertyPanel("Chassis ID", valueHolder, valueHolderStyleName); // TODO use I18N resources
	}
	
	
	
	
	
	public static Panel renderTitleQuestion(String questionLabelText) {
		
		FlowPanel titleQuestionPanel = new FlowPanel();
		titleQuestionPanel.addStyleName(CommonStyles.COMMON_QUESTION);
		
		TextBox titleInput = new TextBox();
		titleInput.setName(ChassisConstants.FIELD_TITLE);
		InlineLabel titleLabel = new InlineLabel(questionLabelText); 
		titleQuestionPanel.add(titleLabel);
		titleQuestionPanel.add(titleInput);
		
		return titleQuestionPanel;
	}




	/**
	 * @param string
	 * @return
	 */
	public static Panel renderSummaryQuestion(String questionLabelText) {

		FlowPanel summaryQuestionPanel = new FlowPanel();
		summaryQuestionPanel.addStyleName(CommonStyles.COMMON_QUESTION);

		TextArea summaryInput = new TextArea();
		summaryInput.setName(ChassisConstants.FIELD_SUMMARY);
		Label summaryLabel = new Label(questionLabelText);
		summaryQuestionPanel.add(summaryLabel);
		summaryQuestionPanel.add(summaryInput);
		
		return summaryQuestionPanel;
	}
	
	
	
	
	public static Hidden renderHiddenAuthorEmail() {
		Hidden h = new Hidden();
		h.setName(ChassisConstants.FIELD_AUTHOREMAIL);
		h.setValue(ChassisUser.getCurrentUserEmail());
		return h;
	}
	
	
	
	
	public static Panel renderFileInputQuestion(String questionLabelText, String inputName) {

		FlowPanel fileQuestionPanel = new FlowPanel();
		fileQuestionPanel.addStyleName(CommonStyles.COMMON_QUESTION);
		
		InlineLabel fileBrowserLabel = new InlineLabel(questionLabelText);
		fileQuestionPanel.add(fileBrowserLabel);

		FileUpload fileInput = new FileUpload();
		fileInput.setName(inputName);
		fileQuestionPanel.add(fileInput);
		
		return fileQuestionPanel;
	}
}
