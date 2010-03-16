xquery version "1.0";

module namespace plugin = "http://www.cggh.org/2010/atombeat/xquery/plugin";


import module namespace logger-plugin = "http://www.cggh.org/2010/xquery/atombeat/logger-plugin" at "../plugins/logger-plugin.xqm" ;



declare variable $plugin:before as function* := (
	util:function( QName( "http://www.cggh.org/2010/xquery/atombeat/logger-plugin" , "logger-plugin:before" ) , 3 )
);


declare variable $plugin:after as function* := (
	util:function( QName( "http://www.cggh.org/2010/xquery/atombeat/logger-plugin" , "logger-plugin:after" ) , 4 )
);
