xquery version "1.0";

module namespace config-collections = "http://purl.org/atombeat/xquery/config-collections";

declare namespace atom = "http://www.w3.org/2005/Atom" ;
declare namespace atombeat = "http://purl.org/atombeat/xmlns" ;

declare variable $config-collections:collection-spec := 
    <spec>
    
        <collection path-info="/studies">
            <atom:feed
                atombeat:enable-versioning="true"
                atombeat:exclude-entry-content="false"
                atombeat:recursive="false">
                <atom:title type="text">Studies</atom:title>
                <!-- ensure security descriptor link available when listing collection -->
                <atombeat:config-link-expansion>
                    <atombeat:config context="entry-in-feed">
                        <atombeat:param name="match-rels" value="http://purl.org/atombeat/rel/security-descriptor"/>
                    </atombeat:config>
                </atombeat:config-link-expansion>
                <!-- configure atombeat:allow for entry context -->
                <atombeat:config-link-extensions>
                    <atombeat:extension-attribute
                        name="allow"
                        namespace="http://purl.org/atombeat/xmlns">
                        <atombeat:config context="entry">
                            <atombeat:param name="match-rels" value="*"/>
                        </atombeat:config>
                    </atombeat:extension-attribute>
                </atombeat:config-link-extensions>
            </atom:feed>        
        </collection>  
        
        <collection path-info="/groups">
            <atom:feed atombeat:enable-versioning="true"
                atombeat:exclude-entry-content="false"
                atombeat:recursive="false">
                <atom:title type="text">Groups</atom:title>
            </atom:feed>
        </collection>
        
        <collection path-info="/study-info">
            <atom:feed
                atombeat:enable-versioning="true"
                atombeat:exclude-entry-content="false"
                atombeat:recursive="false">
                <atom:title type="text">Study Information</atom:title>
                <!-- configure atombeat:allow for entry context -->
                <atombeat:config-link-extensions>
                    <atombeat:extension-attribute
                        name="allow"
                        namespace="http://purl.org/atombeat/xmlns">
                        <atombeat:config context="entry">
                            <atombeat:param name="match-rels" value="*"/>
                        </atombeat:config>
                    </atombeat:extension-attribute>
                </atombeat:config-link-extensions>
            </atom:feed>
        </collection>   
        
        <collection path-info="/drafts">
            <atom:feed
                atombeat:enable-versioning="true"
                atombeat:exclude-entry-content="true"
                atombeat:recursive="false"
                atombeat:enable-tombstones="true">
                <atom:title type="text">Drafts</atom:title>
                <atombeat:config-tombstones>
                    <atombeat:config>
                        <atombeat:param name="ghost-atom-elements" value="id published updated author title link"/>
                    </atombeat:config>
                </atombeat:config-tombstones>
            </atom:feed>
        </collection>  
        
        <collection path-info="/media/submitted">
            <atom:feed
                atombeat:enable-versioning="false"
                atombeat:exclude-entry-content="false"
                atombeat:recursive="true">
                <atom:title type="text">All Submitted Media</atom:title>
            </atom:feed>
        </collection>  
        
        <collection path-info="/media/curated">
            <atom:feed
                atombeat:enable-versioning="false"
                atombeat:exclude-entry-content="false"
                atombeat:recursive="true">
                <atom:title type="text">All Curated Media</atom:title>
            </atom:feed>
        </collection>  
        
        <collection path-info="/media/draft">
            <atom:feed
                atombeat:enable-versioning="false"
                atombeat:exclude-entry-content="false"
                atombeat:recursive="true">
                <atom:title type="text">All Draft Media</atom:title>
            </atom:feed>
        </collection>  
        
        <collection path-info="/derivations">
            <atom:feed
                atombeat:enable-versioning="false"
                atombeat:exclude-entry-content="false"
                atombeat:recursive="true">
                <atom:title type="text">All Derivations</atom:title>
            </atom:feed>
        </collection>  
        
        <collection path-info="/reviews/personal-data">
            <atom:feed
                atombeat:enable-versioning="false"
                atombeat:exclude-entry-content="false"
                atombeat:recursive="true">
                <atom:title type="text">All Personal Data Reviews</atom:title>
            </atom:feed>
        </collection>  
        
        <collection path-info="/sandbox">
            <atom:feed
                atombeat:enable-versioning="false"
                atombeat:exclude-entry-content="false"
                atombeat:recursive="false">
                <atom:title type="text">Sandbox</atom:title>
            </atom:feed>
        </collection>  
        
    </spec>
;
