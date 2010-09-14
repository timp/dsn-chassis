/**
 * 
 */
package org.cggh.chassis.generic.widget.client;

import java.util.Stack;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;

/**
 * @author aliman
 *
 */
public abstract class WidgetMemory {

	

	
	public static final String PATH_DELIMITER = "/";

	
	
	
	
	private Log log = LogFactory.getLog(WidgetMemory.class);
	private WidgetMemory child;
	private WidgetMemory parent;
	
	


	public void setChild(WidgetMemory child) {
		this.child = child;
		if (this.child != null)	this.child.setParent(this);
	}
	

	
	public WidgetMemory getChild() {
		return this.child;
	}
	
	
	
	public boolean hasChild() {
		return (this.child != null);
	}
	
	
	
	private void setParent(WidgetMemory parent) {
		this.parent = parent;
	}




	public void memorise() {
		log.enter("memorise");
		
		if (this.parent == null) {
			String historyToken = this.createHistoryToken();
			History.newItem(historyToken, false); // N.B. no event, otherwise go round in circles
		}
		else {
			this.parent.memorise(); // bubble
		}
		
		log.leave();
	}
	
	
	
	
	
	private String createHistoryToken() {
		log.enter("createHistoryToken");
		
		String mnemonic = this.createMnemonic();
		
		if (mnemonic == null) {
			return ""; // is this sensible?
		}
		
		if (mnemonic.indexOf(PATH_DELIMITER) >= 0) {
			throw new Error("mnemonic must not contain character '"+PATH_DELIMITER+"'");
		}
		
		StringBuffer b = new StringBuffer();

		b.append(mnemonic);

		if (this.child != null) {
			String rest = this.child.createHistoryToken();
			if (rest != null && !rest.equals("")) {
				b.append(PATH_DELIMITER);
				b.append(rest);
			}
		}
		
		log.leave();
		return b.toString();
	}
	
	
	

	public abstract String createMnemonic();
	
	
	
	
	public abstract Deferred<WidgetMemory> remember(String mnemonic);
	
	
	
	
	private void remember(final Stack<String> mnemonics) {
		log.enter("remember(Stack<String>)");
		
		if (!mnemonics.empty()) {

			// remember
			String mnemonic = mnemonics.pop();
			Deferred<WidgetMemory> deferredSelf = this.remember(mnemonic);
			
			// pass on to child if we have one, after remembering is complete (which might involve async actions, hence deferred)
			deferredSelf.addCallback(new Function<WidgetMemory, WidgetMemory>() {

				public WidgetMemory apply(WidgetMemory self) {
					
					WidgetMemory child = self.getChild();

					if (child != null) {
						child.remember(mnemonics);
					}

					return self;
				}
				
			});

		}
		
		log.leave();
	}
	
	
	

	public static class HistoryManager implements ValueChangeHandler<String> {

		
		
		private Log log = LogFactory.getLog(HistoryManager.class);
		private WidgetMemory top;
		
		
		
		public HistoryManager(WidgetMemory top) {
			this.top = top;
		}
		
		
		
		
		/* (non-Javadoc)
		 * @see com.google.gwt.event.logical.shared.ValueChangeHandler#onValueChange(com.google.gwt.event.logical.shared.ValueChangeEvent)
		 */
		public void onValueChange(ValueChangeEvent<String> event) {
			log.enter("onValueChange");
			
			String token = event.getValue();
			
			log.debug("history token: ["+token+"]");
			
			Stack<String> mnemonics = parseHistoryToken(token);
			
			log.debug("call remember on top widget with mnemonics stack ["+mnemonics.size()+"]");
			this.top.remember(mnemonics);
			
			log.leave();
			
		}
		

	
		
	}

	
	
	
	public static Stack<String> parseHistoryToken(String historyToken) {
		
		Stack<String> mnemonics = new Stack<String>();

		String[] tokens = historyToken.split(PATH_DELIMITER); // TODO deal with browser-specific behaviour here for historyToken that is empty string
		
		for (int i=tokens.length-1; i>=0; i--) { // go backwards, Stack is FILO
			mnemonics.push(tokens[i]);
		}
		
		// hack for now
		if (mnemonics.size() == 0) {
			mnemonics.push(""); // workaround different behaviours for historyToken that is empty string
		}
		
		return mnemonics;
	}

	
	

}
