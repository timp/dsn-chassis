/**
 * 
 */
package org.cggh.chassis.gwt.lib.ui.fractal.client;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.gwt.lib.log.client.GWTLogger;
import org.cggh.chassis.gwt.lib.log.client.Logger;
import org.cggh.chassis.gwt.lib.twisted.client.Deferred;
import org.cggh.chassis.gwt.lib.twisted.client.Function;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.History;

/**
 * @author aliman
 *
 */
public abstract class FractalUIComponent {

	
	
	
	public static final String KEY = "state";
	public static final String NESTED = "nested";

	protected Logger log;
	
	
	
	protected List<FractalUIComponent> children = new ArrayList<FractalUIComponent>();
	protected FractalUIComponent parent = null;
	protected JSONObject stateKey;

	
	
	public FractalUIComponent() {
		this.log = new GWTLogger();
		this.log.setCurrentClass(FractalUIComponent.class.getName());
	}
	
	public FractalUIComponent(Logger log) {
		this.log = log;
		this.log.setCurrentClass(FractalUIComponent.class.getName());
	}
	
	
	public int getDepth() {
		if (this.parent == null) {
			return 0;
		}
		else {
			return this.parent.getDepth() + 1;
		}
	}
	
	
	
	public List<FractalUIComponent> getChildren() {
		List<FractalUIComponent> copy = new ArrayList<FractalUIComponent>();
		copy.addAll(this.children);
		return copy;
	}

	
	
	public void addChild(FractalUIComponent child) {
		child.setParent(this);
		this.children.add(child);
	}

	
	
	public void clearChildren() {
		for (FractalUIComponent child : this.children) {
			child.destroy();
		}
		this.children.clear();
	}

	
	
	public void removeChild(FractalUIComponent child) {
		child.destroy();
		this.children.remove(child);
	}
	
	
	
	public void setParent(FractalUIComponent parent) {
		this.parent = parent;
	}

	
	
	public void destroy() {
		for (FractalUIComponent child : this.children) {
			child.destroy();
		}
	}



	public void waypoint() {
		log.enter("waypoint");

		this.syncStateKey();
		this.putHistoryItem();
		
		log.leave();
	}
	
	
	
	
	protected void putHistoryItem() {
		log.enter("putHistoryItem");

		if (this.parent == null) {
			log.trace("at top of HMVC hierarchy, put history item");
			JSONObject stateToken = this.getStateToken();
			String historyToken = stateToken.toString();
			log.trace("historyToken: "+historyToken);
			History.newItem(historyToken, false); // N.B. do not fire event!!!
		}
		else {
			log.trace("not top of hierarchy, bubble call to parent");
			this.parent.putHistoryItem();
		}
		
		log.leave();
	}

	
	
	protected JSONObject getStateToken() {
		JSONObject token = new JSONObject();
		
		// set state key
		token.put(KEY, this.stateKey);
		
		// set child tokens
		if (this.children.size() > 0) {
			JSONArray childTokens = new JSONArray();
			for (int i=0; i<this.children.size(); i++) {
				JSONObject childToken = this.children.get(i).getStateToken();
				childTokens.set(i, childToken);
			}
			token.put(NESTED, childTokens);
		}
		
		return token;
	}

	
	
	public void captureHistoryEvent(final JSONValue stateToken) {
		log.enter("captureHistoryEvent");
		log.trace("stateToken: "+stateToken);
		
		this.stateKey = null;
		
		if (stateToken != null && stateToken instanceof JSONObject) {
			JSONObject o = (JSONObject) stateToken;
			if (o.containsKey(KEY)) {
				this.stateKey = (JSONObject) o.get(KEY);
			}
		}
		
		// propagate state to children, which may be deferred
		log.trace("call sync state");
		Deferred def = this.syncState();
		
		log.trace("add callback to handle sync state completion");
		final FractalUIComponent self = this;
		def.addCallback(new Function() {

			public Object apply(Object in) {
				log.trace("propagate history to children");
				self.propagateHistoryEventToChildren(stateToken);
				return null;
			}
			
		});
		
		// TODO errback???

		log.leave();
	}	
	
	
	protected void propagateHistoryEventToChildren(JSONValue stateToken) {
		log.enter("propagateHistoryEventToChildren");
		log.trace("stateToken: "+stateToken);
		
		JSONArray childTokens = null;
		
		if (stateToken != null && stateToken instanceof JSONObject) {
			JSONObject o = (JSONObject) stateToken;
			if (o.containsKey(NESTED)) {
				log.trace("found nested state");
				childTokens = (JSONArray) o.get(NESTED);
			}
		}

		for (int i=0; i<children.size(); i++) {
			JSONValue childToken = null;
			if (childTokens != null && i < childTokens.size()) {
				childToken = childTokens.get(i);
			}
			log.trace("child["+i+"]: propagating token: "+childToken);
			children.get(i).captureHistoryEvent(childToken);
		}

		log.leave();
	}
	
	
	
	protected abstract Deferred syncState();
	protected abstract void syncStateKey();


	


}
