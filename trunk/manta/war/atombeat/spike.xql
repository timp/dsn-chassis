declare namespace foo = "http://example.org/foo" ;
declare namespace atom = "http://www.w3.org/2005/Atom" ;

declare function foo:do() {
    doc("/db/atom/content/studies/44UJ.atom")/atom:entry
};

let $_ := response:set-header("Content-Type", "application/xml")
let $f := util:function( QName( "http://example.org/foo" , "foo:do" ) , 0 )
return util:call( $f )