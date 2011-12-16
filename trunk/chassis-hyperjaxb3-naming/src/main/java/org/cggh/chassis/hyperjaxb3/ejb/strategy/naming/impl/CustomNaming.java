package org.cggh.chassis.hyperjaxb3.ejb.strategy.naming.impl;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.commons.lang.Validate;
import org.jvnet.hyperjaxb3.ejb.strategy.naming.impl.DefaultNaming;

/** Do not uppercase and use a limit of 60 characters. */
public class CustomNaming extends DefaultNaming {

  private Map<String, String> nameKeyMap = new TreeMap<String, String>();
  private Map<String, String> keyNameMap = new TreeMap<String, String>();

  @Override
  public void afterPropertiesSet() throws Exception {

    final Set<Entry<Object, Object>> entries = getReservedNames().entrySet();
    for (final Entry<Object, Object> entry : entries) {
      final Object entryKey = entry.getKey();
      if (entryKey != null) {
        final String key = entryKey.toString();
        final Object entryValue = entry.getValue();
        final String value = entryValue == null
                || "".equals(entryValue.toString().trim()) ? key + "_"
                : entryValue.toString();
        nameKeyMap.put(key, value);
        keyNameMap.put(value, key);
      }
    }
  }

  @Override
  public String getName(final String draftName) {
    Validate.notNull(draftName, "Name must not be null.");
    final String name = draftName.replace('$', '_');
    if (nameKeyMap.containsKey(name)) {
      return nameKeyMap.get(name);
    } else if (name.length() >= getMaxIdentifierLength()) {
      for (int i = 0;; i++) {
        final String suffix = Integer.toString(i);
        final String prefix = name.substring(0,
                getMaxIdentifierLength() - suffix.length() - 1);
        final String identifier = prefix + "_" + suffix;
        if (!keyNameMap.containsKey(identifier)) {
          nameKeyMap.put(name, identifier);
          keyNameMap.put(identifier, name);
          return identifier;
        }
      }
      
      // Was contains, reported at http://java.net/jira/browse/HYPERJAXB3-153 
    } else if (getReservedNames().containsKey(name.toUpperCase())) {
      return name + "_";
    } else {
      return name;
    }
  }

  @Override
  public int getMaxIdentifierLength() {
    return 60;
  }

}
