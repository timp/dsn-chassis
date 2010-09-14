/**
 * 
 */
package spike.xfui.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author aliman
 *
 */
public class Input extends FlowPanel {

	private static final String TYPE_STRING = "string";

	private Log log = LogFactory.getLog(Input.class);
	
	private String type;
	private TextBox textBox;

	@Override
	public void onLoad() {
		log.enter("onLoad");
		
		render();
		register();
		
		log.leave();
	}

	
	
	public HandlerManager handlerManager = new HandlerManager(this);
	
	
	
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
		return handlerManager.addHandler(ValueChangeEvent.getType(), handler);
	}
	
	
	
	/**
	 * 
	 */
	private void render() {
		log.enter("render");

		clear();
		
		if (type == null || type.equals(TYPE_STRING)) {
			textBox = new TextBox();
			textBox.addValueChangeHandler(new ValueChangeHandler<String>() {
				
				public void onValueChange(ValueChangeEvent<String> arg0) {
					log.enter("onValueChange");
					
					// bounce
					handlerManager.fireEvent(arg0);
					
					log.leave();
				}
				
			});
			add(textBox);
		}
		
		log.leave();
	}

	
	
	
	/**
	 * 
	 */
	private void register() {
		log.enter("register");
		
		// search upward until find an instanceof Question
		
		Widget parent = getParent();
		
		while ( (parent != null) && !(parent instanceof Question) ) {
			parent = parent.getParent();
		}
		
		assert parent != null;
		
		Question question = (Question) parent;
		question.register(this);
		
		log.leave();
	}

	
	
	
	public void setType(String type) {
		this.type = type;
	}

	
	
	
	public String getType() {
		return type;
	}
	
	
	
}
