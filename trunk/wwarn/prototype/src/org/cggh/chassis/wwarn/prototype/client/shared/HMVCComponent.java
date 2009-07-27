/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.shared;

import java.util.ArrayList;
import java.util.List;

/**
 * @author aliman
 *
 */
public abstract class HMVCComponent {

	
	
	public static final String TOKENSEPARATOR = "/";
	
	
	
	
	private List<HMVCComponent> children = new ArrayList<HMVCComponent>();
	private HMVCComponent parent = null;

	

	public String getAbsoluteHistoryTokenBase() {
		String stateToken = "";
		if (this.parent != null) {
			stateToken = this.parent.getAbsoluteHistoryTokenBase();
		}
		stateToken += this.getRelativeHistoryTokenBase();
		return stateToken;
	}
	
	
	
	public int getDepth() {
		if (this.parent == null) {
			return 0;
		}
		else {
			return this.parent.getDepth() + 1;
		}
	}
	
	
	
	public abstract String getRelativeHistoryTokenBase();



	public List<HMVCComponent> getChildren() {
		List<HMVCComponent> copy = new ArrayList<HMVCComponent>();
		copy.addAll(this.children);
		return copy;
	}

	
	
	public void addChild(HMVCComponent child) {
		child.setParent(this);
		this.children.add(child);
	}

	
	
	public void clearChildren() {
		for (HMVCComponent child : this.children) {
			child.destroy();
		}
		this.children.clear();
	}

	
	
	public void removeChild(HMVCComponent child) {
		this.children.remove(child);
		child.destroy();
	}
	
	
	
	public void setParent(HMVCComponent parent) {
		this.parent = parent;
	}

	
	
	public void destroy() {
		for (HMVCComponent child : this.children) {
			child.destroy();
		}
	}	

	
	
}
