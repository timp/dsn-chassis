/**
 * 
 */
package org.cggh.chassis.generic.widget.client;

import java.util.Stack;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.twisted.client.Function;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;

/**
 * @author aliman
 *
 */
public abstract class MemoryWidget extends ChassisWidget {

	

	
	private static final String delimiter = "/";

	
	
	
	
	private Log log = LogFactory.getLog(MemoryWidget.class);
	private MemoryWidget memoryChild;
	private MemoryWidget memoryParent;
	
	


	public void setMemoryChild(MemoryWidget child) {
		this.memoryChild = child;
		this.memoryChild.setMemoryParent(this);
	}
	

	
	private MemoryWidget getMemoryChild() {
		return this.memoryChild;
	}
	
	
	
	private void setMemoryParent(MemoryWidget parent) {
		this.memoryParent = parent;
	}




	public void memorise() {
		log.enter("memorise");
		
		if (this.memoryParent == null) {
			String historyToken = this.createHistoryToken();
			History.newItem(historyToken, false); // N.B. no event, otherwise go round in circles
		}
		else {
			this.memoryParent.memorise(); // bubble
		}
		
		log.leave();
	}
	
	
	
	
	
	private String createHistoryToken() {
		
		String mnemonic = this.createMnemonic();
		
		if (mnemonic.indexOf(delimiter) >= 0) {
			throw new Error("mnemonic must not contain character '"+delimiter+"'");
		}
		
		StringBuffer b = new StringBuffer();

		b.append(mnemonic);

		if (this.memoryChild != null) {
			String rest = this.memoryChild.createHistoryToken();
			if (rest != null && !rest.equals("")) {
				b.append(delimiter);
				b.append(rest);
			}
		}
		
		return b.toString();
	}
	
	
	

	public abstract String createMnemonic();
	
	
	
	
	public abstract Deferred<MemoryWidget> remember(String mnemonic);
	
	
	
	
	private void remember(final Stack<String> mnemonics) {
		
		if (!mnemonics.empty()) {

			// remember
			String mnemonic = mnemonics.pop();
			Deferred<MemoryWidget> deferredSelf = this.remember(mnemonic);
			
			// pass on to child if we have one, after remembering is complete (which might involve async actions, hence deferred)
			deferredSelf.addCallback(new Function<MemoryWidget, MemoryWidget>() {

				public MemoryWidget apply(MemoryWidget self) {
					
					MemoryWidget child = self.getMemoryChild();

					if (child != null) {
						child.remember(mnemonics);
					}

					return self;
				}
				
			});

		}
		
	}
	
	
	

	public static class HistoryManager implements ValueChangeHandler<String> {

		
		
		private Log log = LogFactory.getLog(HistoryManager.class);
		private MemoryWidget top;
		
		
		
		public HistoryManager(MemoryWidget top) {
			this.top = top;
		}
		
		
		
		
		/* (non-Javadoc)
		 * @see com.google.gwt.event.logical.shared.ValueChangeHandler#onValueChange(com.google.gwt.event.logical.shared.ValueChangeEvent)
		 */
		public void onValueChange(ValueChangeEvent<String> event) {
			log.enter("onValueChange");
			
			Stack<String> mnemonics = parseHistoryToken(event.getValue());
			
			this.top.remember(mnemonics);
			
			log.leave();
			
		}
		

	
		
	}

	
	
	
	public static Stack<String> parseHistoryToken(String historyToken) {
		
		Stack<String> mnemonics = new Stack<String>();

		String[] tokens = historyToken.split(delimiter);
		
		for (int i=tokens.length-1; i>=0; i--) { // go backwards, Stack is FILO
			mnemonics.push(tokens[i]);
		}
		
		return mnemonics;
	}

	
	

}
