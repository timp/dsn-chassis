/**
 * 
 */
package org.cggh.chassis.generic.widget.client;

import java.util.HashMap;
import java.util.Map;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

/**
 * @author aliman
 *
 */
public abstract class MapMemory extends WidgetMemory {

	
	
	
	private static final Log log = LogFactory.getLog(MapMemory.class);
	
	
	
	
	public static final String PARAM_DELIMITER = ";";
	public static final String KEYVALUE_DELIMITER = "=";
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.WidgetMemory#createMnemonic()
	 */
	@Override
	public String createMnemonic() {
		log.enter("createMnemonic");
		
		String mnemonic = "";
		Map<String, String> map = this.createMnemonicMap();
		
		for (String key : map.keySet()) {
			
			log.debug("validate key: "+key);
			assert !key.contains(WidgetMemory.PATH_DELIMITER) : key + " contains " + WidgetMemory.PATH_DELIMITER;
			assert !key.contains(PARAM_DELIMITER) : key + " contains " + PARAM_DELIMITER;
			assert !key.contains(KEYVALUE_DELIMITER) : key + " contains " + KEYVALUE_DELIMITER;

			log.debug("key is valid, appending to mnemonic");
			mnemonic += key;

			String value = map.get(key);
			
			if (value != null) {
				
				log.debug("validate value: "+value);
				assert !value.contains(WidgetMemory.PATH_DELIMITER) : value + " contains " + WidgetMemory.PATH_DELIMITER;
				assert !value.contains(PARAM_DELIMITER) : value + " contains " + PARAM_DELIMITER;
				assert !value.contains(KEYVALUE_DELIMITER) : value + " contains " + KEYVALUE_DELIMITER;
			
				log.debug("value is valid, appending to mnemonic");
				mnemonic += KEYVALUE_DELIMITER + value;

			}
			
			mnemonic += PARAM_DELIMITER;
			
		}
		
		log.debug("returning mnemonic: "+mnemonic);

		log.leave();
		return mnemonic;
	}

	
	
	
	
	public abstract Map<String, String> createMnemonicMap();
	
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.WidgetMemory#remember(java.lang.String)
	 */
	@Override
	public Deferred<WidgetMemory> remember(String mnemonic) {
		log.enter("remember");
		
		Map<String, String> map = new HashMap<String, String>();
		
		log.debug("begin parsing mnemonic: "+mnemonic);
		
		String[] params = mnemonic.split(PARAM_DELIMITER);
		
		for (String param : params) {
			
			log.debug("parsing param: "+param);
			
			String[] tokens = param.split(KEYVALUE_DELIMITER);
			
			String key = null;
			String value = null;
			
			if (tokens.length > 0) {
				key = tokens[0];
			}
			
			if (tokens.length > 1) {
				value = tokens[1];
			}
			
			if (key != null) {
				
				log.debug("putting key: "+key+"; value: "+value);
				map.put(key, value);
				
			}
		
		}
		
		log.leave();
		return remember(map);
	}

	
	
	
	public abstract Deferred<WidgetMemory> remember(Map<String, String> mnemonic);
	
	
	
	
	
}
