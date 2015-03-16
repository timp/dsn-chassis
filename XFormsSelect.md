# Introduction #

This page describes how to use XForms select together with an XML Schema to produce select elements with a multi language resources file


# Details #

## Set up the resources files ##
First define your schema with an enumerated list of the values you want for your select.

In our case it's in studyInfoTypes.xsd
```
<?xml version="1.0" encoding="UTF-8"?>
<xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema"
	elementFormDefault="qualified" targetNamespace="http://www.cggh.org/2011/chassis/xmlns"
	xmlns:ch="http://www.cggh.org/2011/chassis/xmlns">

	<xs:simpleType name="administrationRoutes">
		<xs:restriction base="xs:string">
			<xs:enumeration value="" />
			<xs:enumeration value="oral" />
			<xs:enumeration value="intravenous" />
			<xs:enumeration value="intramuscular" />
			<xs:enumeration value="intrarectal" />
		</xs:restriction>
	</xs:simpleType>

<xs:schema>

```

The next thing to do is to define the labels - the structuring of the XML should help you know where the labels are used.

The ref attribute should match a corresponding enumeration value in the XSD

For us it's the file resources.xml

```
<?xml version="1.0" encoding="UTF-8" ?>
<resources>
 <resources xml:lang="en">
  <clinical>
   <drug>
    <adminRoutes>
	<adminRoute ref="" />
	<adminRoute ref="oral">oral</adminRoute>
	<adminRoute ref="intravenous">intravenous</adminRoute>
	<adminRoute ref="intramuscular">intramuscular</adminRoute>
	<adminRoute ref="intrarectal">intrarectal</adminRoute>
    </adminRoutes>
   </drug>
  </clinical>
 </resources>
</resource>
```

## Initialize the model ##
Now to include the resources in your model - note that the xml:lang attribute above allows for easy use of translations

```
<!--
	In your XForms model, load resources.xml in an instance. 
	Make sure to make that instance read-only and cacheable: 
        this way the instance will only be stored once in memory 
	(it will be shared by all the users) and using a more 
         efficient (because read-only) representation in memory.
	
	xxforms:readonly="true" xxforms:cache="true"
	-->
	<xforms:instance id="all-resources"
		src="/apps/common/resources.xml"/>
	<!--
	NOTE: Here we point to a local file with the oxf: protocol.
        This usually yields more performance than using http:, 
        because oxf: will reach a local file on disk. 
        However, in the online version, we use http:, 
        because we want to load the resource from an online server!
	
	Define an instance used to store the current language, 
        e.g. en or fr.
	-->
	<xforms:instance id="language"><language>en</language></xforms:instance>
	<xforms:instance id="iterator"><key></key></xforms:instance>
	<!--
	Define a variable ($resources), which points to <resources>
        for the current language. You will use this variable as a 
        shortcut in your view to point to specific resources.
		-->
	<xxforms:variable name="resources" select="instance('all-resources')/resources[@xml:lang = instance('language')]"/>
	
```

## Show the element ##
Now to include the select - (Note the tagging doesn't match up with the early code snippet)

Usually you will be using xforms:select1 in which case you should not use the @selected attribute or the xforms:action elements (see below)

itemset/@nodeset picks out the enumeration from the xsd file

label/@ref uses the ref attribute to match the text in the resources file to the enumeration value.

```
<xforms:select ref="priorAntimalarials/@selected" 
               appearance="full"
               class="scrollableCheckboxes">
  <xforms:label class="scrollableCheckboxesLabel"> Prior anti-malarials</xforms:label>
	<xforms:itemset model="mod-study-info"
nodeset="instance('study-info-resources')//xs:simpleType[@name='drugMolecules']/xs:restriction/xs:enumeration">
             <xforms:label ref="for $currentItemName in @value return $resources/common/drugs/drugMolecule/name[@ref = $currentItemName]"/>
             <xforms:value ref="@value"/>
        </xforms:itemset>
	<!-- It's possible to just use the ev:event attribute on xforms:insert/delete 
	   this method allows for a little debugging with the message -->
	<xforms:action ev:event="xforms-deselect">
            <xforms:delete context=".." ref="*[. = event('xxforms:item-value')]"/>
            <!-- 
            <xxforms:variable name="deselected" select="event('xxforms:item-value')" />
            <xforms:message level="modal">deSelect:<xforms:output value="$deselected" /></xforms:message>
            -->
        </xforms:action>
        <xforms:action ev:event="xforms-select">
            <xforms:insert context=".." ref="*" origin="xxforms:element('drugTaken',event('xxforms:item-value'))"/>
            <!-- 
            <xxforms:variable name="selected" select="event('xxforms:item-value')" />
            <xforms:message level="modal">Select:<xforms:output value="$selected" /></xforms:message>
             -->
         </xforms:action>
							
</xforms:select>
```


Using the selected attribute and the xforms:insert and delete allows for a more sophisticated mark up when multiple select is used - this is unnecessary but gives a different markup perhaps best explained by example:

```
Default:
<priorAntimalarials>AQ PQP PG</priorAntimalarials>

Using attribute and actions:
<priorAntimalarials selected="AQ PIP PG">
        <drugTaken>AQ</drugTaken>
        <drugTaken>PIP</drugTaken>
        <drugTaken>PG</drugTaken>
</priorAntimalarials>
```