/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.shared;


/**
 * @author aliman
 *
 */
public abstract class HMVCHistoryManager {


	protected HMVCComponent owner;
	protected Logger log;




	public HMVCHistoryManager(HMVCComponent owner) {
		this.owner = owner;
		this.log = new GWTLogger();
	}


	
	
	public HMVCHistoryManager(HMVCComponent owner, Logger log) {
		this.owner = owner;
		this.log = log;
	}

	
	
	

	/*
	 * -------------------------------------------------------------------------
	 * UTILITY METHODS
	 * -------------------------------------------------------------------------
	 */

	
	
	
	public String getLocalStateTokenFromHistoryToken(String historyToken) {
		log.setCurrentMethod("getLocalStateTokenFromHistoryToken");
		log.info("begin: historyToken = "+historyToken);
		
		int depth = this.owner.getDepth();
		String[] tokens = historyToken.split(HMVCComponent.TOKENSEPARATOR);
		
		String ret = "";

		if (tokens.length > depth) {
			ret = tokens[depth];
		}
		
		log.info("return: "+ret);
		return ret;
	}

	
	
	public String getHistoryTokenForNewLocalStateToken(String historyToken, String localStateToken) {
		log.setCurrentMethod("getHistoryTokenForNewLocalStateToken");
		log.info("begin: historyToken = "+historyToken+"; localStateToken = "+localStateToken);

		int depth = this.owner.getDepth();
		String[] tokens = historyToken.split(HMVCComponent.TOKENSEPARATOR);
		
		String newHistoryToken = "";
		for (int i=0; i<depth; i++) {
			if (tokens.length > i) {
				newHistoryToken += tokens[i];
			}
			newHistoryToken += HMVCComponent.TOKENSEPARATOR;
		}
		newHistoryToken += localStateToken;
		
		for (int i=depth+1; i<tokens.length; i++) {
			newHistoryToken += HMVCComponent.TOKENSEPARATOR + tokens[i];
		}
		
		log.info("return: "+newHistoryToken);
		return newHistoryToken;
	}
	
	
	
	

}
