package org.cggh.chassis.manta.util;

import java.util.Map;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

/**
 * Maps the namespaces to configured prefixes (if any).
 */
public class ConfigurableNamespacePrefixMapperImpl extends NamespacePrefixMapper {

    private Map<String, String> mapping;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.sun.xml.bind.marshaller.NamespacePrefixMapper#getPreferredPrefix(java.lang.
     * String, java.lang.String, boolean)
     */
    @Override
    public String getPreferredPrefix(final String namespaceUri, final String suggestion,
            final boolean requirePrefix) {

        String prefix = null;
        if (namespaceUri == null || namespaceUri.equals("")) {
            prefix = "";
        }
        if (mapping != null)
            for (String uri : mapping.keySet()) {
                if (namespaceUri.equalsIgnoreCase(uri)) {
                    prefix = mapping.get(uri);
                    break;
                }
            }
        if (prefix == null) {
            prefix = suggestion;
        }
        return prefix;
    }

    public final void setMapping(Map<String, String> mapping) {
        this.mapping = mapping;
    }

}