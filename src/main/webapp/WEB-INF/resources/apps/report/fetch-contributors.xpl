<p:config xmlns:p="http://www.orbeon.com/oxf/pipeline"
    xmlns:sql="http://orbeon.org/oxf/xml/sql"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:oxf="http://www.orbeon.com/oxf/processors"
    xmlns:xi="http://www.w3.org/2001/XInclude"
    xmlns:atom="http://www.w3.org/2005/Atom" xmlns:atombeat="http://purl.org/atombeat/xmlns">
    
    <p:param type="input" name="instance"/>
    <p:param type="output" name="data"/>
    
    <p:processor name="oxf:sql">
        <p:input name="data" href="#instance"/>
        <p:input name="config">
            <sql:config>
                <contributors>
                    <sql:connection>
                        <sql:datasource>wwarn_drupal</sql:datasource>
                        <sql:execute>
                            <sql:query debug="user query">
                                select users.uid,name,mail, first.value as first_name, last.value as last_name, inst.value as inst_name from users
                                LEFT JOIN profile_values first ON users.uid = first.uid and first.fid = 1
                                LEFT JOIN profile_values last ON users.uid = last.uid and last.fid = 2
                                LEFT JOIN profile_values inst ON users.uid = inst.uid and inst.fid = 3
                            </sql:query>
                            <sql:result-set>
                                <sql:row-iterator>
                                    <contributor>
                                        <uid><sql:get-column-value type="xs:int" column="uid"/></uid>
                                        <name><sql:get-column-value type="xs:string" column="name"/></name>
                                        <mail><sql:get-column-value type="xs:string" column="mail"/></mail>
                                        <first-name><sql:get-column-value type="xs:string" column="first_name"/></first-name>
                                        <last-name><sql:get-column-value type="xs:string" column="last_name"/></last-name>
                                        <inst-name><sql:get-column-value type="xs:string" column="inst_name"/></inst-name>
                                    </contributor>
                                </sql:row-iterator>
                            </sql:result-set>
                        </sql:execute>
                    </sql:connection>
                </contributors>
            </sql:config>
        </p:input>
        <p:output name="data" ref="data"/>
    </p:processor>
    
</p:config>
