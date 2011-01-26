xquery version "1.0";

module namespace security-config = "http://purl.org/atombeat/xquery/security-config";

declare namespace atombeat = "http://purl.org/atombeat/xmlns" ;


import module namespace util = "http://exist-db.org/xquery/util" ;

import module namespace xutil = "http://purl.org/atombeat/xquery/xutil" at "../lib/xutil.xqm" ;
import module namespace config = "http://purl.org/atombeat/xquery/config" at "config.xqm" ;



(:
 : Enable or disable the ACL-based security system.
 :)
declare variable $security-config:enable-security := true() ;



(:
 : The default security decision which will be applied if no ACL rules match 
 : a request. Either "DENY" or "ALLOW".
 :)
declare variable $security-config:default-decision := "DENY" ;



(:
 : The order in which to process the relevant access control lists for
 : any given operation. E.g., if "WORKSPACE" comes before "COLLECTION" then 
 : ACEs in the workspace ACL will take precedence over ACEs in the collection
 : ACLs.
 :)
declare variable $security-config:priority := ( "WORKSPACE" , "COLLECTION" , "RESOURCE") ;
(: declare variable $security-config:priority := ( "RESOURCE" , "COLLECTION" , "WORKSPACE") ; :)




(:
 : A default workspace ACL, customise for your environment.
 :)
declare variable $security-config:default-workspace-security-descriptor := 
    <atombeat:security-descriptor>
        <atombeat:acl>
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_ADMINISTRATOR</atombeat:recipient>
                <atombeat:permission>CREATE_COLLECTION</atombeat:permission>
            </atombeat:ace>
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_ADMINISTRATOR</atombeat:recipient>
                <atombeat:permission>UPDATE_COLLECTION</atombeat:permission>
            </atombeat:ace>
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_ADMINISTRATOR</atombeat:recipient>
                <atombeat:permission>LIST_COLLECTION</atombeat:permission>
            </atombeat:ace>
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_ADMINISTRATOR</atombeat:recipient>
                <atombeat:permission>RETRIEVE_MEMBER</atombeat:permission>
            </atombeat:ace>
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_ADMINISTRATOR</atombeat:recipient>
                <atombeat:permission>UPDATE_MEMBER</atombeat:permission>
            </atombeat:ace>
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_ADMINISTRATOR</atombeat:recipient>
                <atombeat:permission>DELETE_MEMBER</atombeat:permission>
            </atombeat:ace>
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_ADMINISTRATOR</atombeat:recipient>
                <atombeat:permission>RETRIEVE_MEDIA</atombeat:permission>
            </atombeat:ace>
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_ADMINISTRATOR</atombeat:recipient>
                <atombeat:permission>DELETE_MEDIA</atombeat:permission>
            </atombeat:ace>
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_ADMINISTRATOR</atombeat:recipient>
                <atombeat:permission>RETRIEVE_WORKSPACE_ACL</atombeat:permission>
            </atombeat:ace>
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_ADMINISTRATOR</atombeat:recipient>
                <atombeat:permission>RETRIEVE_COLLECTION_ACL</atombeat:permission>
            </atombeat:ace>
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_ADMINISTRATOR</atombeat:recipient>
                <atombeat:permission>RETRIEVE_MEMBER_ACL</atombeat:permission>
            </atombeat:ace>
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_ADMINISTRATOR</atombeat:recipient>
                <atombeat:permission>RETRIEVE_MEDIA_ACL</atombeat:permission>
            </atombeat:ace>
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_ADMINISTRATOR</atombeat:recipient>
                <atombeat:permission>UPDATE_WORKSPACE_ACL</atombeat:permission>
            </atombeat:ace>
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_ADMINISTRATOR</atombeat:recipient>
                <atombeat:permission>UPDATE_COLLECTION_ACL</atombeat:permission>
            </atombeat:ace>
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_ADMINISTRATOR</atombeat:recipient>
                <atombeat:permission>UPDATE_MEMBER_ACL</atombeat:permission>
            </atombeat:ace>
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_ADMINISTRATOR</atombeat:recipient>
                <atombeat:permission>UPDATE_MEDIA_ACL</atombeat:permission>
            </atombeat:ace>
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_ADMINISTRATOR</atombeat:recipient>
                <atombeat:permission>RETRIEVE_HISTORY</atombeat:permission>
            </atombeat:ace>
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_ADMINISTRATOR</atombeat:recipient>
                <atombeat:permission>RETRIEVE_REVISION</atombeat:permission>
            </atombeat:ace>
        </atombeat:acl>
    </atombeat:security-descriptor>
;


declare function security-config:get-group-uri($request-path-info as xs:string) as xs:string
{
(: pick off study ID :)
    
    let $study-id := text:groups( $request-path-info , "^.*/([^/]+)$" )[2]
    
    (: construct study URI to reference group :)
    
    let $group-uri := concat( $config:self-link-uri-base , "/groups/" , $study-id )

    return $group-uri
};

(:
 : A function to generate default collection security descriptor for any new
 : collection created via HTTP, customise for your environment.
 :)
declare function security-config:default-collection-security-descriptor(
    $request-path-info as xs:string ,
    $user as xs:string?
) as element(atombeat:security-descriptor)
{ 
    let $group-uri := security-config:get-group-uri($request-path-info)
    
    let $ret := if ( $request-path-info = "/studies" )

    then $security-config:studies-collection-security-descriptor

    else if ( $request-path-info = "/study-info" )

    then $security-config:study-info-collection-security-descriptor

    else if ( $request-path-info = "/drafts" )

    then $security-config:drafts-collection-security-descriptor
    
    else if ( $request-path-info = "/media/submitted" )
    
    then $security-config:all-submitted-media-collection-security-descriptor
    
    else if ( matches( $request-path-info , "^/media/submitted/[^/]+$" ) )
    
    then security-config:submitted-media-collection-security-descriptor( $group-uri )

    else if ( $request-path-info = "/media/curated" )
    
    then $security-config:all-curated-media-collection-security-descriptor
    
    else if ( matches( $request-path-info , "^/media/curated/[^/]+$" ) )
    
    then security-config:curated-media-collection-security-descriptor( $group-uri )

    else if ( $request-path-info = "/derivations" )
    
    then $security-config:all-derivations-collection-security-descriptor
    
    else if ( matches( $request-path-info , "^/derivations/[^/]+$" ) )
    
    then security-config:derivations-collection-security-descriptor( $group-uri )

    else if ( $request-path-info = "/reviews/personal-data" )
    
    then $security-config:all-personal-data-reviews-collection-security-descriptor
    
    else if ( matches( $request-path-info , "^/reviews/personal-data/[^/]+$" ) )
    
    then security-config:personal-data-reviews-collection-security-descriptor( $group-uri )

    else 
    
        <atombeat:security-descriptor>
            <atombeat:acl/>
        </atombeat:security-descriptor>

    return $ret
};




declare variable $security-config:studies-collection-security-descriptor :=

    <atombeat:security-descriptor>
    
        <atombeat:acl>
    
            <!--
                Contributors can list the collection and create members.
            -->     
    
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_CONTRIBUTOR</atombeat:recipient>
                <atombeat:permission>LIST_COLLECTION</atombeat:permission>
            </atombeat:ace>
    
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_CONTRIBUTOR</atombeat:recipient>
                <atombeat:permission>CREATE_MEMBER</atombeat:permission>
            </atombeat:ace>
            
            
            
            <!-- Because study administrators can UPDATE_MEMBER_ACL, make sure they can't
            give themselves permission to do things we don't want them to do -->
            
            <!-- TODO: This will become unnecessary in v.1.1.1 -->
            <atombeat:ace>
                <atombeat:type>DENY</atombeat:type>
                <atombeat:recipient type="user">*</atombeat:recipient>
                <atombeat:permission>DELETE_MEMBER</atombeat:permission>
            </atombeat:ace>
            
            <!--
                Personal data reviewers can list the collection and can retrieve any member or security descriptor.
            -->
    
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_PERSONAL_DATA_REVIEWER</atombeat:recipient>
                <atombeat:permission>LIST_COLLECTION</atombeat:permission>
            </atombeat:ace>
    
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_PERSONAL_DATA_REVIEWER</atombeat:recipient>
                <atombeat:permission>RETRIEVE_MEMBER</atombeat:permission>
            </atombeat:ace>

            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_PERSONAL_DATA_REVIEWER</atombeat:recipient>
                <atombeat:permission>RETRIEVE_MEMBER_ACL</atombeat:permission>
            </atombeat:ace>
    
            <!--
                Curators can list the collection, and can retrieve any member, and can retrieve and update any security descriptor.
            -->
    
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                <atombeat:permission>LIST_COLLECTION</atombeat:permission>
            </atombeat:ace>
    
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                <atombeat:permission>RETRIEVE_MEMBER</atombeat:permission>
            </atombeat:ace>
    
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                <atombeat:permission>RETRIEVE_HISTORY</atombeat:permission>
            </atombeat:ace>
    
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                <atombeat:permission>RETRIEVE_REVISION</atombeat:permission>
            </atombeat:ace>

            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                <atombeat:permission>RETRIEVE_MEMBER_ACL</atombeat:permission>
            </atombeat:ace>
            
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                <atombeat:permission>UPDATE_MEMBER_ACL</atombeat:permission>
            </atombeat:ace>

        </atombeat:acl>
    
    </atombeat:security-descriptor>
;

declare variable $security-config:groups-collection-security-descriptor :=

    <atombeat:security-descriptor>
    
        <atombeat:acl>
    
            <!--
                Contributors can list the collection and create members.
            -->     
    
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_CONTRIBUTOR</atombeat:recipient>
                <atombeat:permission>LIST_COLLECTION</atombeat:permission>
            </atombeat:ace>
    
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_CONTRIBUTOR</atombeat:recipient>
                <atombeat:permission>CREATE_MEMBER</atombeat:permission>
            </atombeat:ace>
            
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_CONTRIBUTOR</atombeat:recipient>
                <atombeat:permission>UPDATE_MEMBER</atombeat:permission>
            </atombeat:ace>
            
            <!--
                Personal data reviewers can list the collection and can retrieve any member or security descriptor.
            -->
    
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_PERSONAL_DATA_REVIEWER</atombeat:recipient>
                <atombeat:permission>LIST_COLLECTION</atombeat:permission>
            </atombeat:ace>
    
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_PERSONAL_DATA_REVIEWER</atombeat:recipient>
                <atombeat:permission>RETRIEVE_MEMBER</atombeat:permission>
            </atombeat:ace>

            <!--
                Curators can list the collection, and can retrieve any member, and can retrieve and update any security descriptor.
            -->
    
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                <atombeat:permission>LIST_COLLECTION</atombeat:permission>
            </atombeat:ace>
    
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                <atombeat:permission>RETRIEVE_MEMBER</atombeat:permission>
            </atombeat:ace>

            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                <atombeat:permission>UPDATE_MEMBER</atombeat:permission>
            </atombeat:ace>

            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                <atombeat:permission>RETRIEVE_HISTORY</atombeat:permission>
            </atombeat:ace>
    
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                <atombeat:permission>RETRIEVE_REVISION</atombeat:permission>
            </atombeat:ace>

            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                <atombeat:permission>RETRIEVE_MEMBER_ACL</atombeat:permission>
            </atombeat:ace>
            
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                <atombeat:permission>UPDATE_MEMBER_ACL</atombeat:permission>
            </atombeat:ace>

        </atombeat:acl>
    
    </atombeat:security-descriptor>
;


declare variable $security-config:study-info-collection-security-descriptor :=

    <atombeat:security-descriptor>
    
        <atombeat:acl>
    
            <!--
                Contributors can list the collection. (Don't allow create, will manage using manta plug-in.)
            -->     
    
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_CONTRIBUTOR</atombeat:recipient>
                <atombeat:permission>LIST_COLLECTION</atombeat:permission>
            </atombeat:ace>
            
            <!--
                Curators can list the collection, and can retrieve any member, and retrieve and update any member ACL.
                Control of curators' permission to update is set on an individual member level.
            -->
    
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                <atombeat:permission>LIST_COLLECTION</atombeat:permission>
            </atombeat:ace>
    
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                <atombeat:permission>RETRIEVE_MEMBER</atombeat:permission>
            </atombeat:ace>

            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                <atombeat:permission>RETRIEVE_HISTORY</atombeat:permission>
            </atombeat:ace>
    
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                <atombeat:permission>RETRIEVE_REVISION</atombeat:permission>
            </atombeat:ace>
            
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                <atombeat:permission>RETRIEVE_MEMBER_ACL</atombeat:permission>
            </atombeat:ace>

            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                <atombeat:permission>UPDATE_MEMBER_ACL</atombeat:permission>
            </atombeat:ace>

        </atombeat:acl>
    
    </atombeat:security-descriptor>
;




declare variable $security-config:drafts-collection-security-descriptor :=
    <atombeat:security-descriptor>
        <atombeat:acl>
        
            <!--  
            Contributors can create entries and can list the collection,
            but can only retrieve entries they have created.
            They can also delete entries that they have created.
            -->
            
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_CONTRIBUTOR</atombeat:recipient>
                <atombeat:permission>CREATE_MEMBER</atombeat:permission>
            </atombeat:ace>
            
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_CONTRIBUTOR</atombeat:recipient>
                <atombeat:permission>LIST_COLLECTION</atombeat:permission>
            </atombeat:ace>
            
        </atombeat:acl>
    </atombeat:security-descriptor>
;




declare variable $security-config:all-submitted-media-collection-security-descriptor :=
    <atombeat:security-descriptor>
        <atombeat:acl>
        
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                <atombeat:permission>LIST_COLLECTION</atombeat:permission>
            </atombeat:ace>
            
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_PERSONAL_DATA_REVIEWER</atombeat:recipient>
                <atombeat:permission>LIST_COLLECTION</atombeat:permission>
            </atombeat:ace>
            
        </atombeat:acl>
    </atombeat:security-descriptor>
;




declare variable $security-config:all-personal-data-reviews-collection-security-descriptor :=
    <atombeat:security-descriptor>
        <atombeat:acl>
        
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_PERSONAL_DATA_REVIEWER</atombeat:recipient>
                <atombeat:permission>LIST_COLLECTION</atombeat:permission>
            </atombeat:ace>
            
        </atombeat:acl>
    </atombeat:security-descriptor>
;




declare variable $security-config:all-curated-media-collection-security-descriptor :=
    <atombeat:security-descriptor>
        <atombeat:acl>
        
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                <atombeat:permission>LIST_COLLECTION</atombeat:permission>
            </atombeat:ace>
            
        </atombeat:acl>
    </atombeat:security-descriptor>
;




declare variable $security-config:all-derivations-collection-security-descriptor :=
    <atombeat:security-descriptor>
        <atombeat:acl>
        
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                <atombeat:permission>LIST_COLLECTION</atombeat:permission>
            </atombeat:ace>
            
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_PERSONAL_DATA_REVIEWER</atombeat:recipient>
                <atombeat:permission>LIST_COLLECTION</atombeat:permission>
            </atombeat:ace>
            
        </atombeat:acl>
    </atombeat:security-descriptor>
;





declare function security-config:submitted-media-collection-security-descriptor(
    $group-uri as xs:string
) as element(atombeat:security-descriptor)
{
        <atombeat:security-descriptor>
        
                <atombeat:groups>
                        <atombeat:group id="GROUP_ADMINISTRATORS" src="{$group-uri}"/>
                </atombeat:groups>
        
                <atombeat:acl>
        
                        <!--
                                The study's administrators can create media resources in the study's submitted media collection, and can list the collection and retrieve and update members (but cannot update media ever, and cannot retrieve media until allowed by personal data reviewer).
                        -->
        
                        <atombeat:ace>
                                <atombeat:type>ALLOW</atombeat:type>
                                <atombeat:recipient type="group">GROUP_ADMINISTRATORS</atombeat:recipient>
                                <atombeat:permission>LIST_COLLECTION</atombeat:permission>
                        </atombeat:ace>
        
                        <atombeat:ace>
                                <atombeat:type>ALLOW</atombeat:type>
                                <atombeat:recipient type="group">GROUP_ADMINISTRATORS</atombeat:recipient>
                                <atombeat:permission>CREATE_MEDIA</atombeat:permission>
                        </atombeat:ace>
        
                        <atombeat:ace>
                                <atombeat:type>ALLOW</atombeat:type>
                                <atombeat:recipient type="group">GROUP_ADMINISTRATORS</atombeat:recipient>
                                <atombeat:permission>RETRIEVE_MEMBER</atombeat:permission>
                        </atombeat:ace>
        
                        <atombeat:ace>
                                <atombeat:type>ALLOW</atombeat:type>
                                <atombeat:recipient type="group">GROUP_ADMINISTRATORS</atombeat:recipient>
                                <atombeat:permission>UPDATE_MEMBER</atombeat:permission>
                        </atombeat:ace>
        
                        <atombeat:ace>
                                <atombeat:type>ALLOW</atombeat:type>
                                <atombeat:recipient type="group">GROUP_ADMINISTRATORS</atombeat:recipient>
                                <atombeat:permission>MULTI_CREATE</atombeat:permission>
                        </atombeat:ace>
        
                        <!--
                                Curators can list the collection, and can retrieve any member, and can retrieve and update any ACL.
                        -->
        
                        <atombeat:ace>
                                <atombeat:type>ALLOW</atombeat:type>
                                <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                                <atombeat:permission>LIST_COLLECTION</atombeat:permission>
                        </atombeat:ace>
        
                        <atombeat:ace>
                                <atombeat:type>ALLOW</atombeat:type>
                                <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                                <atombeat:permission>RETRIEVE_MEMBER</atombeat:permission>
                        </atombeat:ace>
        
                        <atombeat:ace>
                                <atombeat:type>ALLOW</atombeat:type>
                                <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                                <atombeat:permission>DELETE_MEDIA</atombeat:permission>
                        </atombeat:ace>
                        
                        <atombeat:ace>
                            <atombeat:type>ALLOW</atombeat:type>
                            <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                            <atombeat:permission>RETRIEVE_COLLECTION_ACL</atombeat:permission>
                        </atombeat:ace>
                        
                        <atombeat:ace>
                            <atombeat:type>ALLOW</atombeat:type>
                            <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                            <atombeat:permission>UPDATE_COLLECTION_ACL</atombeat:permission>
                        </atombeat:ace>
                        
                        <!--
                                Personal data reviewers can list the collection, and can retrieve any member or media resource. They can also modify ACLs.
                        -->
        
                        <atombeat:ace>
                                <atombeat:type>ALLOW</atombeat:type>
                                <atombeat:recipient type="role">ROLE_CHASSIS_PERSONAL_DATA_REVIEWER</atombeat:recipient>
                                <atombeat:permission>LIST_COLLECTION</atombeat:permission>
                        </atombeat:ace>
        
                        <atombeat:ace>
                                <atombeat:type>ALLOW</atombeat:type>
                                <atombeat:recipient type="role">ROLE_CHASSIS_PERSONAL_DATA_REVIEWER</atombeat:recipient>
                                <atombeat:permission>RETRIEVE_MEMBER</atombeat:permission>
                        </atombeat:ace>
        
                        <atombeat:ace>
                                <atombeat:type>ALLOW</atombeat:type>
                                <atombeat:recipient type="role">ROLE_CHASSIS_PERSONAL_DATA_REVIEWER</atombeat:recipient>
                                <atombeat:permission>RETRIEVE_MEDIA</atombeat:permission>
                        </atombeat:ace>

                </atombeat:acl>
        
        </atombeat:security-descriptor>    

};



declare function security-config:draft-media-collection-security-descriptor(
    $request-path-info as xs:string ,
    $user as xs:string
) as element(atombeat:security-descriptor)
{

        <atombeat:security-descriptor>
        
                <atombeat:acl>
        
                        <atombeat:ace>
                                <atombeat:type>ALLOW</atombeat:type>
                                <atombeat:recipient type="user">{$user}</atombeat:recipient>
                                <atombeat:permission>LIST_COLLECTION</atombeat:permission>
                        </atombeat:ace>
        
                        <atombeat:ace>
                                <atombeat:type>ALLOW</atombeat:type>
                                <atombeat:recipient type="user">{$user}</atombeat:recipient>
                                <atombeat:permission>CREATE_MEDIA</atombeat:permission>
                        </atombeat:ace>
        
                        <atombeat:ace>
                                <atombeat:type>ALLOW</atombeat:type>
                                <atombeat:recipient type="user">{$user}</atombeat:recipient>
                                <atombeat:permission>RETRIEVE_MEDIA</atombeat:permission>
                        </atombeat:ace>
        
                        <atombeat:ace>
                                <atombeat:type>ALLOW</atombeat:type>
                                <atombeat:recipient type="user">{$user}</atombeat:recipient>
                                <atombeat:permission>DELETE_MEDIA</atombeat:permission>
                        </atombeat:ace>
        
                        <atombeat:ace>
                                <atombeat:type>ALLOW</atombeat:type>
                                <atombeat:recipient type="user">{$user}</atombeat:recipient>
                                <atombeat:permission>RETRIEVE_MEMBER</atombeat:permission>
                        </atombeat:ace>
        
                        <atombeat:ace>
                                <atombeat:type>ALLOW</atombeat:type>
                                <atombeat:recipient type="user">{$user}</atombeat:recipient>
                                <atombeat:permission>UPDATE_MEMBER</atombeat:permission>
                        </atombeat:ace>
        
                </atombeat:acl>
        
        </atombeat:security-descriptor>    
    
};



declare function security-config:curated-media-collection-security-descriptor(
    $group-uri as xs:string
) as element(atombeat:security-descriptor)
{
 
        <atombeat:security-descriptor>
        
            <atombeat:groups>
                    <atombeat:group id="GROUP_ADMINISTRATORS" src="{$group-uri}"/>
            </atombeat:groups>
            
            <atombeat:acl>
    
                <!--
                        Curators can create media resources, list the collection, retrieve media resources, and retrieve and update members, and retrieve and update collection ACLs.
                -->

                <atombeat:ace>
                        <atombeat:type>ALLOW</atombeat:type>
                        <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                        <atombeat:permission>CREATE_MEDIA</atombeat:permission>
                </atombeat:ace>

                <atombeat:ace>
                        <atombeat:type>ALLOW</atombeat:type>
                        <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                        <atombeat:permission>LIST_COLLECTION</atombeat:permission>
                </atombeat:ace>

                <atombeat:ace>
                        <atombeat:type>ALLOW</atombeat:type>
                        <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                        <atombeat:permission>RETRIEVE_MEMBER</atombeat:permission>
                </atombeat:ace>

                <atombeat:ace>
                        <atombeat:type>ALLOW</atombeat:type>
                        <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                        <atombeat:permission>UPDATE_MEMBER</atombeat:permission>
                </atombeat:ace>

                <atombeat:ace>
                        <atombeat:type>ALLOW</atombeat:type>
                        <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                        <atombeat:permission>RETRIEVE_MEDIA</atombeat:permission>
                </atombeat:ace>

                <atombeat:ace>
                    <atombeat:type>ALLOW</atombeat:type>
                    <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                    <atombeat:permission>RETRIEVE_COLLECTION_ACL</atombeat:permission>
                </atombeat:ace>
                
                <atombeat:ace>
                    <atombeat:type>ALLOW</atombeat:type>
                    <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                    <atombeat:permission>UPDATE_COLLECTION_ACL</atombeat:permission>
                </atombeat:ace>

                <!-- N.B. by default, study administrators cannot see curated media -->
                
            </atombeat:acl>
    
        </atombeat:security-descriptor> 
};



declare function security-config:derivations-collection-security-descriptor(
    $group-uri as xs:string
) as element(atombeat:security-descriptor)
{
        <atombeat:security-descriptor>
        
                <atombeat:groups>
                        <atombeat:group id="GROUP_ADMINISTRATORS" src="{$group-uri}"/>
                </atombeat:groups>
        
                <atombeat:acl>
        
                        <!--
                                Curators can create members, list the collection, and retrieve members.
                        -->
        
                        <atombeat:ace>
                                <atombeat:type>ALLOW</atombeat:type>
                                <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                                <atombeat:permission>CREATE_MEMBER</atombeat:permission>
                        </atombeat:ace>
        
                        <atombeat:ace>
                                <atombeat:type>ALLOW</atombeat:type>
                                <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                                <atombeat:permission>LIST_COLLECTION</atombeat:permission>
                        </atombeat:ace>
        
                        <atombeat:ace>
                                <atombeat:type>ALLOW</atombeat:type>
                                <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                                <atombeat:permission>RETRIEVE_MEMBER</atombeat:permission>
                        </atombeat:ace>
        
                        <!--
                                Personal data reviewers can list the collection, and retrieve members.
                        -->
        
                        <atombeat:ace>
                                <atombeat:type>ALLOW</atombeat:type>
                                <atombeat:recipient type="role">ROLE_CHASSIS_PERSONAL_DATA_REVIEWER</atombeat:recipient>
                                <atombeat:permission>LIST_COLLECTION</atombeat:permission>
                        </atombeat:ace>
        
                        <atombeat:ace>
                                <atombeat:type>ALLOW</atombeat:type>
                                <atombeat:recipient type="role">ROLE_CHASSIS_PERSONAL_DATA_REVIEWER</atombeat:recipient>
                                <atombeat:permission>RETRIEVE_MEMBER</atombeat:permission>
                        </atombeat:ace>
        
                        <!-- N.B. by default, study administrators cannot see derivations -->
        
                </atombeat:acl>
        
        </atombeat:security-descriptor>

};



declare function security-config:personal-data-reviews-collection-security-descriptor(
    $group-uri as xs:string
) as element(atombeat:security-descriptor)
{
        <atombeat:security-descriptor>
        
            <atombeat:groups>
                    <atombeat:group id="GROUP_ADMINISTRATORS" src="{$group-uri}"/>
            </atombeat:groups>
    
            <atombeat:acl>
    
                <!--
                        Personal data reviewers can list the collection and retrieve any member. Only personal data reviewers can create members.
                -->

                <atombeat:ace>
                        <atombeat:type>ALLOW</atombeat:type>
                        <atombeat:recipient type="role">ROLE_CHASSIS_PERSONAL_DATA_REVIEWER</atombeat:recipient>
                        <atombeat:permission>CREATE_MEMBER</atombeat:permission>
                </atombeat:ace>

                <atombeat:ace>
                        <atombeat:type>ALLOW</atombeat:type>
                        <atombeat:recipient type="role">ROLE_CHASSIS_PERSONAL_DATA_REVIEWER</atombeat:recipient>
                        <atombeat:permission>LIST_COLLECTION</atombeat:permission>
                </atombeat:ace>

                <atombeat:ace>
                        <atombeat:type>ALLOW</atombeat:type>
                        <atombeat:recipient type="role">ROLE_CHASSIS_PERSONAL_DATA_REVIEWER</atombeat:recipient>
                        <atombeat:permission>RETRIEVE_MEMBER</atombeat:permission>
                </atombeat:ace>

                <!-- N.B. by default, study administrators cannot see personal data reviews -->
    
            </atombeat:acl>
        
        </atombeat:security-descriptor>

};


(: TODO fix this :)
declare function security-config:default-member-security-descriptor(
    $request-path-info as xs:string ,
    $user as xs:string
) as element(atombeat:security-descriptor)
{

    if ( $request-path-info = "/studies" )

    then security-config:studies-member-default-security-descriptor( $user )

    else if ( $request-path-info = "/drafts" )

    then security-config:drafts-member-default-security-descriptor( $user )
    
    else 
    
        <atombeat:security-descriptor>
            <atombeat:acl/>
        </atombeat:security-descriptor>

};


(: TODO fix this :)
declare function security-config:default-media-security-descriptor(
    $request-path-info as xs:string ,
    $user as xs:string
) as element(atombeat:security-descriptor)
{

    if ( matches( $request-path-info , "^/media/submitted/[^/]+$" ) )
    
    then security-config:submitted-media-media-security-descriptor( $request-path-info )

    else 
    
        <atombeat:security-descriptor>
            <atombeat:acl/>
        </atombeat:security-descriptor>

};




declare function security-config:studies-member-default-security-descriptor(
    $user as xs:string
) as element(atombeat:security-descriptor)
{
    <atombeat:security-descriptor>
    
		<atombeat:groups>
		
			<atombeat:group id="GROUP_ADMINISTRATORS">
                <atombeat:member>{$user}</atombeat:member>
			</atombeat:group>
			
		</atombeat:groups>
		
        <atombeat:acl>
        
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="group">GROUP_ADMINISTRATORS</atombeat:recipient>
                <atombeat:permission>RETRIEVE_MEMBER</atombeat:permission>
            </atombeat:ace>
            
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="group">GROUP_ADMINISTRATORS</atombeat:recipient>
                <atombeat:permission>UPDATE_MEMBER</atombeat:permission>
            </atombeat:ace>
            
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="group">GROUP_ADMINISTRATORS</atombeat:recipient>
                <atombeat:permission>RETRIEVE_MEMBER_ACL</atombeat:permission>
            </atombeat:ace>
            
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="group">GROUP_ADMINISTRATORS</atombeat:recipient>
                <atombeat:permission>RETRIEVE_HISTORY</atombeat:permission>
            </atombeat:ace>
            
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="group">GROUP_ADMINISTRATORS</atombeat:recipient>
                <atombeat:permission>RETRIEVE_REVISION</atombeat:permission>
            </atombeat:ace>

		</atombeat:acl>
		
	</atombeat:security-descriptor>
};




declare function security-config:drafts-member-default-security-descriptor(
    $user as xs:string
) as element(atombeat:security-descriptor) 
{
    <atombeat:security-descriptor>
    
        <atombeat:acl>
        
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="user">{$user}</atombeat:recipient>
                <atombeat:permission>RETRIEVE_MEMBER</atombeat:permission>
            </atombeat:ace>
            
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="user">{$user}</atombeat:recipient>
                <atombeat:permission>UPDATE_MEMBER</atombeat:permission>
            </atombeat:ace>
            
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="user">{$user}</atombeat:recipient>
                <atombeat:permission>DELETE_MEMBER</atombeat:permission>
            </atombeat:ace>
            
		</atombeat:acl>
		
	</atombeat:security-descriptor>
};





declare function security-config:submitted-media-media-security-descriptor(
    $request-path-info as xs:string
) as element(atombeat:security-descriptor)
{

    (: add the group reference for later convenience :)    
    let $group-uri := security-config:get-group-uri($request-path-info)
    
    return 
    
        <atombeat:security-descriptor>
        
            <atombeat:groups>
                    <atombeat:group id="GROUP_ADMINISTRATORS" src="{$group-uri}"/>
            </atombeat:groups>
    
            <atombeat:acl>
            
                <atombeat:ace>
                        <atombeat:type>ALLOW</atombeat:type>
                        <atombeat:recipient type="role">ROLE_CHASSIS_PERSONAL_DATA_REVIEWER</atombeat:recipient>
                        <atombeat:permission>RETRIEVE_MEDIA_ACL</atombeat:permission>
                </atombeat:ace>

                <atombeat:ace>
                        <atombeat:type>ALLOW</atombeat:type>
                        <atombeat:recipient type="role">ROLE_CHASSIS_PERSONAL_DATA_REVIEWER</atombeat:recipient>
                        <atombeat:permission>UPDATE_MEDIA_ACL</atombeat:permission>
                </atombeat:ace>
    
            </atombeat:acl>
    
        </atombeat:security-descriptor>    
    
};


(: TODO refactor above so don't need to make assumptions about URI structure :)

declare function security-config:study-info-member-security-descriptor(
    $study-uri as xs:string
) as element(atombeat:security-descriptor)
{

    <atombeat:security-descriptor>
    
        <atombeat:groups>
                <atombeat:group id="GROUP_ADMINISTRATORS" src="{$study-uri}"/>
        </atombeat:groups>

        <atombeat:acl>
        
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="group">GROUP_ADMINISTRATORS</atombeat:recipient>
                <atombeat:permission>RETRIEVE_MEMBER</atombeat:permission>
            </atombeat:ace>
            
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="group">GROUP_ADMINISTRATORS</atombeat:recipient>
                <atombeat:permission>UPDATE_MEMBER</atombeat:permission>
            </atombeat:ace>

            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="group">GROUP_ADMINISTRATORS</atombeat:recipient>
                <atombeat:permission>RETRIEVE_HISTORY</atombeat:permission>
            </atombeat:ace>
            
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="group">GROUP_ADMINISTRATORS</atombeat:recipient>
                <atombeat:permission>RETRIEVE_REVISION</atombeat:permission>
            </atombeat:ace>

            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                <atombeat:permission>UPDATE_MEMBER</atombeat:permission>
            </atombeat:ace>

        </atombeat:acl>

    </atombeat:security-descriptor>    
};

declare function security-config:study-member-security-descriptor(
    $study-group-uri as xs:string
) as element(atombeat:security-descriptor)
{
		<atombeat:security-descriptor xmlns:atombeat="http://purl.org/atombeat/xmlns">
			<atombeat:groups>
				<atombeat:group id="GROUP_ADMINISTRATORS" src="{$study-group-uri}"/>
			</atombeat:groups>
			<atombeat:acl>
				<atombeat:ace>
					<atombeat:type>ALLOW</atombeat:type>
					<atombeat:recipient type="group">GROUP_ADMINISTRATORS</atombeat:recipient>
					<atombeat:permission>RETRIEVE_MEMBER</atombeat:permission>
				</atombeat:ace>
				<atombeat:ace>
					<atombeat:type>ALLOW</atombeat:type>
					<atombeat:recipient type="group">GROUP_ADMINISTRATORS</atombeat:recipient>
					<atombeat:permission>UPDATE_MEMBER</atombeat:permission>
				</atombeat:ace>
				<atombeat:ace>
					<atombeat:type>ALLOW</atombeat:type>
					<atombeat:recipient type="group">GROUP_ADMINISTRATORS</atombeat:recipient>
					<atombeat:permission>RETRIEVE_MEMBER_ACL</atombeat:permission>
				</atombeat:ace>
				<atombeat:ace>
					<atombeat:type>ALLOW</atombeat:type>
					<atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
					<atombeat:permission>UPDATE_MEMBER</atombeat:permission>
				</atombeat:ace>
				<atombeat:ace>
					<atombeat:type>ALLOW</atombeat:type>
					<atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
					<atombeat:permission>DELETE_MEMBER</atombeat:permission>
				</atombeat:ace>
			</atombeat:acl>
		</atombeat:security-descriptor>
};