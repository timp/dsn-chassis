

package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.widget.client.ObservableProperty;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;

import com.google.gwt.xml.client.Element;

/**
 * @author timp
 */
public class CuratorHomeWidgetModel {

	public final ObservableProperty<String> studyUrl = new ObservableProperty<String>();
	public final ObservableProperty<Element> studyEntryElement = new ObservableProperty<Element>();
	public final ObservableProperty<Status> status = new ObservableProperty<Status>();
	public final ObservableProperty<String> message = new ObservableProperty<String>();

}
