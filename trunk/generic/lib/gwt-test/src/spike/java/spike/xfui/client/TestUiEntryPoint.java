package spike.xfui.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class TestUiEntryPoint implements EntryPoint {

	public void onModuleLoad() {
		RootPanel.get().add(new TestUi("Al"));
	}

}
