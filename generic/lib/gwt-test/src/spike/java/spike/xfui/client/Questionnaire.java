package spike.xfui.client;

import java.util.Iterator;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class Questionnaire extends Composite implements HasWidgets {

	private FlowPanel canvas;

	public Questionnaire() {
		canvas = new FlowPanel();
		initWidget(canvas);
	}

	public void add(Widget arg0) {
		canvas.add(arg0);
	}

	public void clear() {
		canvas.clear();
	}

	public Iterator<Widget> iterator() {
		return canvas.iterator();
	}

	public boolean remove(Widget arg0) {
		return canvas.remove(arg0);
	}
	
}
