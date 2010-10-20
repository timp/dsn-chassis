xquery version "1.0";

module namespace plugin = "http://purl.org/atombeat/xquery/plugin";


import module namespace logger-plugin = "http://purl.org/atombeat/xquery/logger-plugin" at "../plugins/logger-plugin.xqm" ;
import module namespace security-plugin = "http://purl.org/atombeat/xquery/security-plugin" at "../plugins/security-plugin.xqm" ;
import module namespace link-extensions-plugin = "http://purl.org/atombeat/xquery/link-extensions-plugin" at "../plugins/link-extensions-plugin.xqm" ;
import module namespace link-expansion-plugin = "http://purl.org/atombeat/xquery/link-expansion-plugin" at "../plugins/link-expansion-plugin.xqm" ;
import module namespace history-plugin = "http://purl.org/atombeat/xquery/history-plugin" at "../plugins/history-plugin.xqm" ;
import module namespace manta-plugin = "http://www.cggh.org/2010/chassis/manta/xquery/atombeat-plugin" at "../plugins/manta-plugin.xqm" ;
import module namespace http-headers-plugin = "http://www.cggh.org/2010/chassis/http-headers/xquery/atombeat-plugin" at "../plugins/http-headers-plugin.xqm" ;
import module namespace transform-plugin = "http://www.cggh.org/2010/chassis/transform/xquery/atombeat-plugin" at "../plugins/transform-plugin.xqm" ;

declare function plugin:before() as function* {
	(
        util:function( QName( "http://purl.org/atombeat/xquery/logger-plugin" , "logger-plugin:before" ) , 4 ) ,
        util:function( QName( "http://purl.org/atombeat/xquery/security-plugin" , "security-plugin:before" ) , 4 ) ,
        util:function( QName( "http://purl.org/atombeat/xquery/link-expansion-plugin" , "link-expansion-plugin:before" ) , 4 ) ,  
        util:function( QName( "http://purl.org/atombeat/xquery/link-extensions-plugin" , "link-extensions-plugin:before" ) , 4 ) ,  
        util:function( QName( "http://purl.org/atombeat/xquery/history-plugin" , "history-plugin:before" ) , 4 ) ,
        util:function( QName( "http://www.cggh.org/2010/chassis/transform/xquery/atombeat-plugin" , "transform-plugin:before" ) , 4 ) ,
        util:function( QName( "http://www.cggh.org/2010/chassis/manta/xquery/atombeat-plugin" , "manta-plugin:before" ) , 4 )
  )
};




declare function plugin:after() as function* {
	(
        util:function( QName( "http://purl.org/atombeat/xquery/security-plugin" , "security-plugin:after" ) , 3 ) , (: filter feed first :) 
        util:function( QName( "http://purl.org/atombeat/xquery/history-plugin" , "history-plugin:after" ) , 3 ) , 
        util:function( QName( "http://www.cggh.org/2010/chassis/manta/xquery/atombeat-plugin" , "manta-plugin:after" ) , 3 ) ,
        util:function( QName( "http://www.cggh.org/2010/chassis/http-headers/xquery/atombeat-plugin" , "http-headers-plugin:after" ) , 3 ) ,
        util:function( QName( "http://www.cggh.org/2010/chassis/transform/xquery/atombeat-plugin" , "transform-plugin:after" ) , 3 ) ,
        util:function( QName( "http://purl.org/atombeat/xquery/link-extensions-plugin" , "link-extensions-plugin:after" ) , 3 ) , 
        util:function( QName( "http://purl.org/atombeat/xquery/link-expansion-plugin" , "link-expansion-plugin:after" ) , 3 ) , 
        util:function( QName( "http://purl.org/atombeat/xquery/logger-plugin" , "logger-plugin:after" ) , 3 )
	)
};
