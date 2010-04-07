xquery version "1.0";

module namespace config = "http://www.cggh.org/2010/atombeat/xquery/config";

import module namespace util = "http://exist-db.org/xquery/util" ;

import module namespace xutil = "http://www.cggh.org/2010/atombeat/xquery/xutil" at "../lib/xutil.xqm" ;

(:
 : The base URL for the Atom service. This URL will be prepended to all edit
 : and self link href values.
 :)
declare variable $config:service-url as xs:string := "http://localhost:8081/manta/atombeat/content" ;


(:
 : The base URL for the History service. This URL will be prepended to all 
 : history link href values.
 :)
declare variable $config:history-service-url as xs:string := "http://localhost:8081/manta/atombeat/history" ;
 

declare variable $config:acl-service-url as xs:string := "http://localhost:8081/manta/atombeat/acl" ;


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
declare variable $config:base-acl-collection-path as xs:string := "/db/atom/acl" ;


(: 
 : The resource name used to store feed documents in the database.
 :)
declare variable $config:feed-doc-name as xs:string := ".feed" ;


declare function config:generate-identifier(
    $collection-path-info as xs:string
) as xs:string
{
    if ( $collection-path-info = "/studies" )
    then upper-case( xutil:random-alphanumeric( 4 ) )
    else util:uuid()
};


(:
 : Enable or disable the ACL-based security system.
 :)
declare variable $config:enable-security := true() ;


(:
 : The default security decision which will be applied if no ACL rules match 
 : a request. Either "deny" or "allow".
 :)
declare variable $config:default-decision := "deny" ;


(:
 : The global ACL.
 :)
declare variable $config:default-global-acl := 
    <acl>
        <rules>
            <allow>
                <role>ROLE_CHASSIS_ADMINISTRATOR</role>
                <operation>*</operation>
            </allow>
        </rules>
    </acl>
;


(:
 : The default collection ACLs.
 :)
declare function config:default-collection-acl(
    $request-path-info as xs:string ,
    $user as xs:string?
) as element(acl)
{ 
    if ( $request-path-info = "/studies" )

    then

        <acl>
            <rules>
                <!-- submitters can create studies and can list the collection -->
                <allow>
                    <role>ROLE_CHASSIS_SUBMITTER</role>
                    <operation>create-member</operation>
                </allow>
                <allow>
                    <role>ROLE_CHASSIS_SUBMITTER</role>
                    <operation>list-collection</operation>
                </allow>
            </rules>
        </acl>
        
    else

        (: TODO other collections :)
        <acl>
            <rules>
            </rules>
        </acl>
};


(:
 : The default resource ACLs.
 :)
declare function config:default-resource-acl(
    $request-path-info as xs:string ,
    $user as xs:string
) as element(acl)
{

    if ( starts-with( $request-path-info , "/studies" ) )
    
    then

    	<acl>
    		<groups>
    			<group name="owners">
                    <user>{$user}</user><!-- creating user is automatically an owner -->
    			</group>
    			<group name="submitters">
    			</group>
    		</groups>
    		<rules>
    		    <!-- owners can retrieve, update and change sharing -->
                <allow>
                    <group>owners</group>
                    <operation>retrieve-member</operation>
                </allow>
                <allow>
                    <group>owners</group>
                    <operation>update-member</operation>
                </allow>
                <allow>
                    <group>owners</group>
                    <operation>update-acl</operation>
                </allow>
                <!-- submitters can retrieve and update, but cannot change sharing -->
                <allow>
                    <group>submitters</group>
                    <operation>retrieve-member</operation>
                </allow>
                <allow>
                    <group>submitters</group>
                    <operation>update-member</operation>
                </allow>
    		</rules>
    	</acl>
    	
    else

        (: TODO other collections :)
        <acl>
            <rules>
            </rules>
        </acl>
    

};

