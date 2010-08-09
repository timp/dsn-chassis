(: 
NOTE this file is NOT filtered by Maven and is overwritten by Maven during a build, 
it is here for the convenience of Eclipse users. 
NOTE ANY EDITS TO THIS FILE MUST BE DUPLICATED IN src/main/webapp/atombeat/config/shared.xqm
:) 
xquery version "1.0";

module namespace config = "http://purl.org/atombeat/xquery/config";

declare namespace atombeat = "http://purl.org/atombeat/xmlns" ;


import module namespace util = "http://exist-db.org/xquery/util" ;

import module namespace xutil = "http://purl.org/atombeat/xquery/xutil" at "../lib/xutil.xqm" ;

(:
declare variable $config:service-url-base as xs:string :=  "http://cloud1.cggh.org/manta/atombeat" ;
:)
declare variable $config:service-url-base as xs:string :=  "http://localhost:8080/manta/atombeat" ;


(:
 : The base URL for the Atom service. This URL will be prepended to all edit
 : and self link href values.
 :)
declare variable $config:content-service-url as xs:string := concat( $config:service-url-base , "/content" ) ;


(:
 : The base URL for the History service. This URL will be prepended to all 
 : history link href values.
 :)
declare variable $config:history-service-url as xs:string := concat( $config:service-url-base , "/history" ) ;
 

declare variable $config:security-service-url as xs:string := concat( $config:service-url-base , "/security" ) ;



(:
 : The name of the request attribute where the currently authenticated user
 : is stored. N.B. it is assumed that authentication will have been carried out
 : prior to the Atom service handling the request, e.g., by a filter, and that
 : the filter will have stored the authenticated user's name (ID) in the given
 : request attribute.
 :)
declare variable $config:user-name-request-attribute-key as xs:string := "user-name" ; 


(:
 : The name of the request attribute where the currently authenticated user's
 : roles are stored. N.B. it is assumed that authentication and retrieval of
 : the authenticated user's roles will have been carried outprior to the Atom 
 : service handling the request, e.g., by a filter, and that the filter will 
 : have stored the authenticated user's roles in the given request attribute.
 : TODO explain expected type of attribute value.
 :)
declare variable $config:user-roles-request-attribute-key as xs:string := "user-roles" ; 


(: TODO doc me :)
declare variable $config:auto-author as xs:boolean := true() ;


(:
 : If usernames should be treated as email addresses, set this to true(). (I.e.,
 : if users are logging in with their email address as their user ID.)
 :)
declare variable $config:user-name-is-email as xs:boolean := true() ;


(:
 : The base collection within which to store Atom collections and resources.
 : All paths will be relative to this base collection path.
 :)
declare variable $config:base-collection-path as xs:string := "/db/atom/content" ;


(:
 : The base collection within which to store access control lists.
 :)
declare variable $config:base-security-collection-path as xs:string := "/db/atom/security" ;


(: 
 : The resource name used to store feed documents in the database.
 :)
declare variable $config:feed-doc-name as xs:string := ".feed" ;


declare function config:generate-identifier(
    $collection-path-info as xs:string
) as xs:string
{

    if ( $collection-path-info = "/studies" )
    
(:    then upper-case( xutil:random-alphanumeric( 4 ) ) :)

    (: this avoids confusion between numbers 0, 1 and letters i, l, o, v, w :)
    
    then upper-case( xutil:random-alphanumeric( 5 , 21 , "0123456789abcdefghijk" , "abcdefghjkmnpqrstuxyz" ) )

    (: TODO check this path is right :)
    
(:    else if ( matches( $collection-path-info , "^/media" ) ) :)
    
(:    then upper-case( xutil:random-alphanumeric( 6 ) ) :)

(:    then upper-case( xutil:random-alphanumeric( 7 , 21 , "0123456789abcdefghijk" , "abcdefghjkmnpqrstuxyz" ) ) :)

    else util:uuid()

};


(:
 : Enable or disable the ACL-based security system.
 :)
declare variable $config:enable-security := true() ;


(:
 : The default security decision which will be applied if no ACL rules match 
 : a request. Either "DENY" or "ALLOW".
 :)
declare variable $config:default-security-decision := "DENY" ;


(:
 : The order in which to process the relevant access control lists for
 : any given operation. E.g., if "WORKSPACE" comes before "COLLECTION" then 
 : ACEs in the workspace ACL will take precedence over ACEs in the collection
 : ACLs.
 :)
declare variable $config:security-priority := ( "WORKSPACE" , "COLLECTION" , "RESOURCE") ;
(: declare variable $config:security-priority := ( "RESOURCE" , "COLLECTION" , "WORKSPACE") ; :)



(:
 : A default workspace ACL, customise for your environment.
 :)
declare variable $config:default-workspace-security-descriptor := 
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
                <atombeat:permission>RETRIEVE_ACL</atombeat:permission>
            </atombeat:ace>
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_ADMINISTRATOR</atombeat:recipient>
                <atombeat:permission>UPDATE_ACL</atombeat:permission>
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



(:
 : A function to generate default collection security descriptor for any new
 : collection created via HTTP, customise for your environment.
 :)
declare function config:default-collection-security-descriptor(
    $request-path-info as xs:string ,
    $user as xs:string?
) as element(atombeat:security-descriptor)
{ 
    if ( $request-path-info = "/studies" )

    then $config:studies-collection-security-descriptor

    else if ( $request-path-info = "/study-info" )

    then $config:study-info-collection-security-descriptor

    else if ( $request-path-info = "/drafts" )

    then $config:drafts-collection-security-descriptor
    
    else if ( $request-path-info = "/media/submitted" )
    
    then $config:all-submitted-media-collection-security-descriptor
    
    else if ( matches( $request-path-info , "^/media/submitted/[^/]+$" ) )
    
    then config:submitted-media-collection-security-descriptor( $request-path-info )

    else if ( $request-path-info = "/media/curated" )
    
    then $config:all-curated-media-collection-security-descriptor
    
    else if ( matches( $request-path-info , "^/media/curated/[^/]+$" ) )
    
    then config:curated-media-collection-security-descriptor( $request-path-info )

    else if ( $request-path-info = "/derivations" )
    
    then $config:all-derivations-collection-security-descriptor
    
    else if ( matches( $request-path-info , "^/derivations/[^/]+$" ) )
    
    then config:derivations-collection-security-descriptor( $request-path-info )

    else if ( $request-path-info = "/reviews/personal-data" )
    
    then $config:all-personal-data-reviews-collection-security-descriptor
    
    else if ( matches( $request-path-info , "^/reviews/personal-data/[^/]+$" ) )
    
    then config:personal-data-reviews-collection-security-descriptor( $request-path-info )

    else 
    
        <atombeat:security-descriptor>
            <atombeat:acl/>
        </atombeat:security-descriptor>

};




declare variable $config:studies-collection-security-descriptor :=

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
            
            
            
            <!-- Because study administrators can UPDATE_ACL, make sure they can't
            give themselves permission to do things we don't want them to do -->
            
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
    
            <!--
                Curators can list the collection, and can retrieve any member, and can retrieve any security descriptor.
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
    
        </atombeat:acl>
    
    </atombeat:security-descriptor>
;




declare variable $config:study-info-collection-security-descriptor :=

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
                Curators can list the collection, and can retrieve and update any member.
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
    

        </atombeat:acl>
    
    </atombeat:security-descriptor>
;




declare variable $config:drafts-collection-security-descriptor :=
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




declare variable $config:all-submitted-media-collection-security-descriptor :=
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




declare variable $config:all-personal-data-reviews-collection-security-descriptor :=
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




declare variable $config:all-curated-media-collection-security-descriptor :=
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




declare variable $config:all-derivations-collection-security-descriptor :=
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





declare function config:submitted-media-collection-security-descriptor(
    $request-path-info as xs:string
) as element(atombeat:security-descriptor)
{

    (: pick off study ID :)
    
    let $study-id := text:groups( $request-path-info , "^.*/([^/]+)$" )[2]
    
    (: construct study URI to reference group :)
    
    let $study-uri := concat( $config:content-service-url , "/studies/" , $study-id , ".atom" )
    
    return 
    
        <atombeat:security-descriptor>
        
                <atombeat:groups>
                        <atombeat:group id="GROUP_ADMINISTRATORS" src="{$study-uri}"/>
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
                                Curators can list the collection, and can retrieve any member.
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



declare function config:draft-media-collection-security-descriptor(
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



declare function config:curated-media-collection-security-descriptor(
    $request-path-info as xs:string
) as element(atombeat:security-descriptor)
{

    (: pick off study ID :)
    
    let $study-id := text:groups( $request-path-info , "^.*/([^/]+)$" )[2]
    
    (: construct study URI to reference group :)
    
    let $study-uri := concat( $config:content-service-url , "/studies/" , $study-id , ".atom" )
    
    return 

        <atombeat:security-descriptor>
        
            <atombeat:groups>
                    <atombeat:group id="GROUP_ADMINISTRATORS" src="{$study-uri}"/>
            </atombeat:groups>
            
            <atombeat:acl>
    
                <!--
                        Curators can create media resources, list the collection, retrieve media resources, and retrieve and update members.
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

                <!-- N.B. by default, study administrators cannot see curated media -->
                
            </atombeat:acl>
    
        </atombeat:security-descriptor> 
};



declare function config:derivations-collection-security-descriptor(
    $request-path-info as xs:string
) as element(atombeat:security-descriptor)
{

    (: pick off study ID :)
    
    let $study-id := text:groups( $request-path-info , "^.*/([^/]+)$" )[2]
    
    (: construct study URI to reference group :)
    
    let $study-uri := concat( $config:content-service-url , "/studies/" , $study-id , ".atom" )
    
    return 

        <atombeat:security-descriptor>
        
                <atombeat:groups>
                        <atombeat:group id="GROUP_ADMINISTRATORS" src="{$study-uri}"/>
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



declare function config:personal-data-reviews-collection-security-descriptor(
    $request-path-info as xs:string
) as element(atombeat:security-descriptor)
{

    (: pick off study ID :)
    
    let $study-id := text:groups( $request-path-info , "^.*/([^/]+)$" )[2]
    
    (: construct study URI to reference group :)
    
    let $study-uri := concat( $config:content-service-url , "/studies/" , $study-id , ".atom" )
    
    return

        <atombeat:security-descriptor>
        
            <atombeat:groups>
                    <atombeat:group id="GROUP_ADMINISTRATORS" src="{$study-uri}"/>
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


(:
 : A function to generate default resource security descriptor for any new
 : collection members or media resources, customise for your environment.
 :)
declare function config:default-resource-security-descriptor(
    $request-path-info as xs:string ,
    $user as xs:string
) as element(atombeat:security-descriptor)
{

    if ( $request-path-info = "/studies" )

    then config:studies-member-default-security-descriptor( $user )

    else if ( $request-path-info = "/drafts" )

    then config:drafts-member-default-security-descriptor( $user )
    
    else if ( matches( $request-path-info , "^/media/submitted/[^/]+$" ) )
    
    then config:submitted-media-member-security-descriptor( $request-path-info )

    else 
    
        <atombeat:security-descriptor>
            <atombeat:acl/>
        </atombeat:security-descriptor>

};




declare function config:studies-member-default-security-descriptor(
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
                <atombeat:permission>RETRIEVE_ACL</atombeat:permission>
            </atombeat:ace>
            
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="group">GROUP_ADMINISTRATORS</atombeat:recipient>
                <atombeat:permission>UPDATE_ACL</atombeat:permission>
            </atombeat:ace>
            
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="group">GROUP_ADMINISTRATORS</atombeat:recipient>
                <atombeat:permission>RETRIEVE_HISTORY</atombeat:permission>
            </atombeat:ace>
            
            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="group">GROUP_ADMINISTRATORS</atombeat:recipient>
                <atombeat:permission>RETRIEVE_MEMBER</atombeat:permission>
            </atombeat:ace>

            <!-- needed here as workaround for http://code.google.com/p/atombeat/issues/detail?id=71 -->

            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                <atombeat:permission>RETRIEVE_ACL</atombeat:permission>
            </atombeat:ace>

            <atombeat:ace>
                <atombeat:type>ALLOW</atombeat:type>
                <atombeat:recipient type="role">ROLE_CHASSIS_PERSONAL_DATA_REVIEWER</atombeat:recipient>
                <atombeat:permission>RETRIEVE_ACL</atombeat:permission>
            </atombeat:ace>
    
		</atombeat:acl>
		
	</atombeat:security-descriptor>
};




declare function config:drafts-member-default-security-descriptor(
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





declare function config:submitted-media-member-security-descriptor(
    $request-path-info as xs:string
) as element(atombeat:security-descriptor)
{

    (: add the group reference for later convenience :)
    
    (: pick off study ID :)
    
    let $study-id := text:groups( $request-path-info , "^.*/([^/]+)$" )[2]
    
    (: construct study URI to reference group :)
    
    let $study-uri := concat( $config:content-service-url , "/studies/" , $study-id , ".atom" )
    
    return 
    
        <atombeat:security-descriptor>
        
            <atombeat:groups>
                    <atombeat:group id="GROUP_ADMINISTRATORS" src="{$study-uri}"/>
            </atombeat:groups>
    
            <atombeat:acl>
            
                <!-- TODO make specific to media -->
                
                <atombeat:ace>
                        <atombeat:type>ALLOW</atombeat:type>
                        <atombeat:recipient type="role">ROLE_CHASSIS_PERSONAL_DATA_REVIEWER</atombeat:recipient>
                        <atombeat:permission>RETRIEVE_ACL</atombeat:permission>
                </atombeat:ace>

                <atombeat:ace>
                        <atombeat:type>ALLOW</atombeat:type>
                        <atombeat:recipient type="role">ROLE_CHASSIS_PERSONAL_DATA_REVIEWER</atombeat:recipient>
                        <atombeat:permission>UPDATE_ACL</atombeat:permission>
                </atombeat:ace>
    
            </atombeat:acl>
    
        </atombeat:security-descriptor>    
    
};


(: TODO refactor above so don't need to make assumptions about URI structure :)

declare function config:study-info-member-security-descriptor(
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
                <atombeat:permission>RETRIEVE_MEMBER</atombeat:permission>
            </atombeat:ace>
            
        </atombeat:acl>

    </atombeat:security-descriptor>    
};