package spike.xfui.client;

import org.cggh.chassis.generic.log.client.AllenSauerLog;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class TestUiEntryPoint implements EntryPoint {

	static {
		LogFactory.create = AllenSauerLog.create;
	}
	
	public void onModuleLoad() {
		RootPanel.get().add(new TestUi());
	}

}
