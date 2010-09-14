package spike.xfui.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class TestUi extends Composite {

	private static TestUiUiBinder uiBinder = GWT.create(TestUiUiBinder.class);

	interface TestUiUiBinder extends UiBinder<Widget, TestUi> {}
	
	@UiField Question q1;
	@UiField Button q1b;

	public TestUi() {
		initWidget(uiBinder.createAndBindUi(this));
		q1.bind();
	}

	@UiHandler("q1b")
	public void onClick(ClickEvent e) {
		Window.alert(q1.getModel().toString());
	}

}
