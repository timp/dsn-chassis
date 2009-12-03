/**
 * 
 */
package spike.widget.data.management.client;

import org.cggh.chassis.generic.client.gwt.common.client.ChassisUser;
import org.cggh.chassis.generic.client.gwt.widget.data.client.DataManagementWidget;
import org.cggh.chassis.generic.log.client.AllenSauerLog;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.user.transfer.UserDetailsTO;
import org.cggh.chassis.generic.widget.client.WidgetMemory;
import org.cggh.chassis.generic.widget.client.WidgetMemory.HistoryManager;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author aliman
 *
 */
public class SpikeDataManagementWidgetEntryPoint implements EntryPoint {

	
	
	
	static {
		LogFactory.create = AllenSauerLog.create;
		LogFactory.hide("*");
		LogFactory.show("org.cggh.chassis.generic.widget.*");
		LogFactory.show("org.cggh.chassis.generic.client.gwt.widget.data.*");
	}
	
	
	private Log log = LogFactory
			.getLog(SpikeDataManagementWidgetEntryPoint.class);

	
	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {
		log.enter("onModuleLoad");

		UserDetailsTO user = new UserDetailsTO();
		user.setId("alice@example.com");
		ChassisUser.setCurrentUser(user);

		Panel root = RootPanel.get("holdall");
		
		MenuBar menu = new MenuBar();
		root.add(menu);
		
		DataManagementWidget w = new DataManagementWidget();
		root.add(w);

		menu.addItem("data", w.getMenu());
		
		HistoryManager hm = new WidgetMemory.HistoryManager(w.getMemory());
		History.addValueChangeHandler(hm);
		History.fireCurrentHistoryState();

		log.leave();
	}

}
