xquery version "1.0";

module namespace config-collections = "http://purl.org/atombeat/xquery/config-collections";

declare variable $config-collections:collection-spec := 
    <spec>
        <collection>
            <title>Studies</title>
            <path-info>/studies</path-info>
            <enable-history>true</enable-history>
            <exclude-entry-content>false</exclude-entry-content>
            <expand-security-descriptors>true</expand-security-descriptors>
            <recursive>false</recursive>
        </collection>   
        <collection>
            <title>Study Information</title>
            <path-info>/study-info</path-info>
            <enable-history>true</enable-history>
            <exclude-entry-content>false</exclude-entry-content>
            <expand-security-descriptors>false</expand-security-descriptors>
            <recursive>false</recursive>
        </collection>   
        <collection>
            <title>Drafts</title>
            <path-info>/drafts</path-info>
            <enable-history>true</enable-history>
            <exclude-entry-content>true</exclude-entry-content>
            <expand-security-descriptors>false</expand-security-descriptors>
            <recursive>false</recursive>
        </collection>   
        <collection>
            <title>All Submitted Media</title>
            <path-info>/media/submitted</path-info>
            <enable-history>false</enable-history>
            <exclude-entry-content>false</exclude-entry-content>
            <expand-security-descriptors>false</expand-security-descriptors>
            <recursive>true</recursive>
        </collection>   
        <collection>
            <title>All Curated Media</title>
            <path-info>/media/curated</path-info>
            <enable-history>false</enable-history>
            <exclude-entry-content>false</exclude-entry-content>
            <expand-security-descriptors>false</expand-security-descriptors>
            <recursive>true</recursive>
        </collection>   
        <collection>
            <title>All Draft Media</title>
            <path-info>/media/draft</path-info>
            <enable-history>false</enable-history>
            <exclude-entry-content>false</exclude-entry-content>
            <expand-security-descriptors>false</expand-security-descriptors>
            <recursive>true</recursive>
        </collection>   
        <collection>
            <title>All Derivations</title>
            <path-info>/derivations</path-info>
            <enable-history>false</enable-history>
            <exclude-entry-content>false</exclude-entry-content>
            <expand-security-descriptors>false</expand-security-descriptors>
            <recursive>true</recursive>
        </collection>   
        <collection>
            <title>All Personal Data Reviews</title>
            <path-info>/reviews/personal-data</path-info>
            <enable-history>false</enable-history>
            <exclude-entry-content>false</exclude-entry-content>
            <expand-security-descriptors>false</expand-security-descriptors>
            <recursive>true</recursive>
        </collection>   
        <collection>
            <title>Sandbox</title>
            <path-info>/sandbox</path-info>
            <enable-history>false</enable-history>
            <exclude-entry-content>false</exclude-entry-content>
            <expand-security-descriptors>false</expand-security-descriptors>
            <recursive>false</recursive>
        </collection>   
    </spec>
;
