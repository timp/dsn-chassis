<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:atom="http://www.w3.org/2005/Atom">

	<xsl:output method="text" media-type="text/csv" />

	<xsl:variable name="SSQversion" select="'unknown'" />
	<xsl:variable name="xsltversion" select="'0.0.3'" />

	<xsl:variable name="general.site" select="5" />
	<xsl:variable name="general.site.colDef"
		select="'country,region,district,locality,lookupAddress,siteCode,testingDelay,anticoagulant,transportAndStorageTemperature,latitude,longitude,'" />
	<xsl:variable name="general.site.ti.colDef"
		select="'annualParasitologicalIncidence,annualParasitologicalIncidenceYear,parasitePrevalence,parasitePrevalenceYear,transmissionIntensityAgeFrom,transmissionIntensityAgeFromUnits,transmissionIntensityAgeTo,transmissionIntensityAgeToUnits,entomologicalInoculationRate,entomologicalInoculationRateYear,seasonalTransmission,transmissionMonths,'" />
	<xsl:variable name="clinical.regimens" select="3" />
	<xsl:variable name="clinical.regimens.colDef"
		select="'regimenName,regimenSupervision,regimenUrl,'" />
	<xsl:variable name="clinical.regimen.drugs" select="3" />
	<xsl:variable name="clinical.regimen.drugs.colDef"
		select="'name,administrationRoute,manufacturer,batchNumber,expiryDate,drugStorage,drugDosingDeterminant,feeding,fatPerMeal,feedingOther,readministeredOnVomitting,comments,'" />
	<xsl:variable name="clinical.regimen.drug.activeIngredients"
		select="3" />
	<xsl:variable name="clinical.regimen.drug.activeIngredients.colDef"
		select="'activeIngredientName,activeIngredientMgPerDose,'" />
	<xsl:variable name="clinical.regimen.drug.ageDosing"
		select="3" />
	<xsl:variable name="clinical.regimen.drug.ageDosing.colDef"
		select="'day,hour,ageFrom,ageTo,dose,doseUnit,'" />
	<xsl:variable name="clinical.regimen.drug.weightGroupDosing"
		select="3" />
	<xsl:variable name="clinical.regimen.drug.weightGroupDosing.colDef"
		select="'day,hour,wGroupFrom,wGroupTo,dose,doseUnit,'" />
	<xsl:variable name="clinical.regimen.drug.weightDosing"
		select="3" />
	<xsl:variable name="clinical.regimen.drug.weightDosing.colDef"
		select="'day,hour,dose,'" />

	<xsl:variable name="molecular.recrudescenceMarker" select="3" />
	<xsl:variable name="molecular.genotypedMarker" select="3" />
	<xsl:variable name="molecular.resistanceLoci" select="3" />
	<xsl:variable name="molecular.otherLoci" select="3" />

	<xsl:variable name="invitro.antibioticTreatment" select="3" />
	<xsl:variable name="invitro.drugs" select="5" />
	<xsl:variable name="invitro.plateBatches.batch" select="5" />

	<xsl:variable name="pharmacology.samples" select="5" />
	<xsl:variable name="pharmacology.samples.colDef"
		select="'numberPlanned,anticoagulent,centrifugeTime,'" />
	<xsl:variable name="pharmacology.sample.storages" select="3" />
	<xsl:variable name="pharmacology.sample.storages.colDef"
		select="'storageTemperature,storageDuration,storageDurationUnit,'" />
	<xsl:variable name="pharmacology.analytes" select="5" />
	<xsl:variable name="pharmacology.analytes.colDef"
		select="'drugMeasured,lowerLoQ,sampleMatrixType,'" />
	<xsl:variable name="pharmacology.assayReferences" select="5" />
	<xsl:variable name="pharmacology.assayReferences.colDef"
		select="'referenceType,url,doi,note,assayValidated,uploadedUrl,'" />

	<xsl:template match="//atom:id" />
	<xsl:template match="//atom:published" />
	<xsl:template match="//atom:updated" />
	<xsl:template match="//atom:email" />
	<xsl:template match="/atom:entry">
		<xsl:apply-templates select="atom:content" />
	</xsl:template>
	<xsl:template match="atom:content">
		<xsl:text>updated,SSQ-version,xslt-version,</xsl:text>
		<xsl:apply-templates select="study-info">
			<xsl:with-param name="outputType" select="0" />
		</xsl:apply-templates>
		<xsl:text>&#10;</xsl:text>
		<xsl:value-of select="//atom:updated"></xsl:value-of>
		<xsl:text>,</xsl:text>
		<xsl:value-of select="study-info/@profile" />
		<xsl:text>,</xsl:text>
		<xsl:value-of select="$xsltversion" />
		<xsl:text>,</xsl:text>
		<xsl:apply-templates select="study-info">
			<xsl:with-param name="outputType" select="1" />
		</xsl:apply-templates>
	</xsl:template>

	<xsl:template match="study-info">
		<xsl:param name="outputType" />

		<xsl:apply-templates select="start">
			<xsl:with-param name="outputType" select="$outputType" />
		</xsl:apply-templates>
		<xsl:apply-templates select="end">
			<xsl:with-param name="outputType" select="$outputType" />
		</xsl:apply-templates>
		<xsl:apply-templates select="sites/site">
			<xsl:with-param name="outputType" select="$outputType" />
			<xsl:with-param name="total" select="$general.site" />
			<xsl:with-param name="count" select="count(//site) + 1" />
			<xsl:with-param name="colDef" select="$general.site.colDef" />
			<xsl:with-param name="prefix" select="'site'" />
		</xsl:apply-templates>

		<xsl:apply-templates select="modules">
			<xsl:with-param name="outputType" select="$outputType" />
		</xsl:apply-templates>
		<xsl:apply-templates select="pathogens">
			<xsl:with-param name="outputType" select="$outputType" />
		</xsl:apply-templates>
		<xsl:apply-templates select="inclusionExclusionCriteria">
			<xsl:with-param name="outputType" select="$outputType" />
			<xsl:with-param name="prefix" select="''" />
		</xsl:apply-templates>

		<xsl:apply-templates select="clinical|pharmacology">
			<xsl:with-param name="outputType" select="$outputType" />
		</xsl:apply-templates>

	</xsl:template>

	<xsl:template
		match="start|end|modules|pathogens|includeMixedInfections|excludeIfPriorAntimalarials|pregnancy|treatmentReason|otherCriteria|recrudescenceAndReinfection">
		<xsl:param name="outputType" />
		<xsl:choose>
			<xsl:when test="$outputType = 1">
				<xsl:value-of select="normalize-space(.)" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="name(.)" />
			</xsl:otherwise>
		</xsl:choose>
		<xsl:value-of select="','" />
	</xsl:template>

	<xsl:template match="inclusionExclusionCriteria">
		<xsl:param name="outputType" />
		<xsl:apply-templates select="age">
			<xsl:with-param name="outputType" select="$outputType" />
			<xsl:with-param name="prefix" select="''" />
		</xsl:apply-templates>

		<xsl:apply-templates select="parasitaemia">
			<xsl:with-param name="outputType" select="$outputType" />
			<xsl:with-param name="total" select="1" />
			<xsl:with-param name="count" select="1" />
			<xsl:with-param name="colDef" select="''" />
			<xsl:with-param name="prefix" select="''" />
		</xsl:apply-templates>

		<xsl:apply-templates select="includeMixedInfections">
			<xsl:with-param name="outputType" select="$outputType" />
		</xsl:apply-templates>

		<xsl:apply-templates select="excludeIfPriorAntimalarials">
			<xsl:with-param name="outputType" select="$outputType" />
		</xsl:apply-templates>

		<xsl:apply-templates select="priorAntimalarialsExclusion">
			<xsl:with-param name="outputType" select="$outputType" />
			<xsl:with-param name="total" select="1" />
			<xsl:with-param name="count" select="1" />
			<xsl:with-param name="colDef" select="''" />
			<xsl:with-param name="prefix" select="''" />
		</xsl:apply-templates>

		<xsl:apply-templates select="pregnancy">
			<xsl:with-param name="outputType" select="$outputType" />
		</xsl:apply-templates>
		<xsl:apply-templates select="treatmentReason">
			<xsl:with-param name="outputType" select="$outputType" />
		</xsl:apply-templates>
		<xsl:apply-templates select="otherCriteria">
			<xsl:with-param name="outputType" select="$outputType" />
		</xsl:apply-templates>

	</xsl:template>
	<xsl:template match="sites/site">
		<xsl:param name="count" />
		<xsl:param name="outputType" />
		<xsl:param name="colDef" />
		<xsl:param name="total" />
		<xsl:param name="prefix" />

		<xsl:for-each select="*">
			<xsl:if
				test="not((name(.) = 'transmissionIntensity') or name(.) = 'drugs')">
				<xsl:choose>
					<xsl:when test="$outputType = 1">
						<xsl:value-of select="normalize-space(.)" />
						<!-- <xsl:if test="position() != last()"> -->
						<xsl:value-of select="','" />
						<!-- </xsl:if> -->

					</xsl:when>
					<xsl:otherwise>
						<xsl:variable name="name" select="name(.)" />
						<xsl:variable name="parentNum">
							<xsl:choose>
								<xsl:when test="$prefix = ''">
									<xsl:text />
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of
										select="concat($prefix,concat(1 + count(parent::*/preceding-sibling::*),'.'))" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:variable>
						<xsl:value-of select="concat($parentNum,$name)" />
						<!-- <xsl:if test="position() != last()"> -->
						<xsl:value-of select="','" />
						<!-- </xsl:if> -->
					</xsl:otherwise>
				</xsl:choose>
			</xsl:if>
		</xsl:for-each>
		<xsl:apply-templates select="transmissionIntensity">
			<xsl:with-param name="outputType" select="$outputType" />
			<xsl:with-param name="total" select="$general.site" />
			<xsl:with-param name="count" select="count(//site) + 1" />
			<xsl:with-param name="prefix" select="'site'" />
		</xsl:apply-templates>

		<xsl:call-template name="addSites">
			<xsl:with-param name="count" select="$count" />
			<xsl:with-param name="outputType" select="$outputType" />
			<xsl:with-param name="colDef" select="$colDef" />
			<xsl:with-param name="total" select="$total" />
			<xsl:with-param name="prefix" select="$prefix" />
		</xsl:call-template>

	</xsl:template>

	<xsl:template name="addSites">
		<xsl:param name="outputType" />
		<xsl:param name="colDef" />
		<xsl:param name="count" />
		<xsl:param name="total" />
		<xsl:param name="prefix" />

		<xsl:if test="$count &lt;= $total">
			<xsl:call-template name="commaSplit">
				<xsl:with-param name="position" select="1" />
				<xsl:with-param name="count" select="$count" />
				<xsl:with-param name="outputType" select="$outputType" />
				<xsl:with-param name="dataString" select="$colDef" />
				<xsl:with-param name="prefix" select="$prefix" />
			</xsl:call-template>

			<xsl:call-template name="addBlanks">
				<xsl:with-param name="count" select="$count" />
				<xsl:with-param name="outputType" select="$outputType" />
				<xsl:with-param name="colDef" select="$general.site.ti.colDef" />
				<xsl:with-param name="total" select="$count" />
				<xsl:with-param name="prefix" select="$prefix" />
			</xsl:call-template>

			<xsl:call-template name="addSites">
				<xsl:with-param name="count" select="$count + 1" />
				<xsl:with-param name="outputType" select="$outputType" />
				<xsl:with-param name="colDef" select="$colDef" />
				<xsl:with-param name="total" select="$total" />
				<xsl:with-param name="prefix" select="$prefix" />
			</xsl:call-template>
		</xsl:if>
	</xsl:template>

	<xsl:template match="clinical">
		<xsl:param name="outputType" />
		<xsl:apply-templates select="treatment/regimens/regimen">
			<xsl:with-param name="outputType" select="$outputType" />
			<xsl:with-param name="total" select="$clinical.regimens" />
			<xsl:with-param name="count" select="count(//regimen) + 1" />
			<xsl:with-param name="colDef" select="$clinical.regimens.colDef" />
			<xsl:with-param name="prefix" select="'regimen'" />
		</xsl:apply-templates>

		<xsl:apply-templates select="treatment/regimenAllocation">
			<xsl:with-param name="outputType" select="$outputType" />
		</xsl:apply-templates>
		<xsl:apply-templates select="followup">
			<xsl:with-param name="outputType" select="$outputType" />
		</xsl:apply-templates>

		<xsl:apply-templates select="microscopy">
			<xsl:with-param name="outputType" select="$outputType" />
		</xsl:apply-templates>

		<xsl:apply-templates
			select="geneotypingToDistinguishBetweenRecrudescenceAndReinfection">
			<xsl:with-param name="outputType" select="$outputType" />
		</xsl:apply-templates>
		<xsl:apply-templates
			select="geneotypingToDistinguishBetweenRecrudescenceAndReinfection/applicable/markers/recrudescenceMarker">
			<xsl:with-param name="outputType" select="$outputType" />
		</xsl:apply-templates>
		<xsl:apply-templates
			select="geneotypingToDistinguishBetweenRecrudescenceAndReinfection/applicable/analysisProtocol/mixedAllelesOpen">
			<xsl:with-param name="outputType" select="$outputType" />
		</xsl:apply-templates>
		<xsl:apply-templates select="recrudescenceAndReinfection">
			<xsl:with-param name="outputType" select="$outputType" />
		</xsl:apply-templates>
	</xsl:template>

	<xsl:template match="treatment/regimens/regimen">
		<xsl:param name="count" />
		<xsl:param name="outputType" />
		<xsl:param name="colDef" />
		<xsl:param name="total" />
		<xsl:param name="prefix" />
		<xsl:for-each select="*">
			<xsl:if
				test="not((name(.) = 'transmissionIntensity') or name(.) = 'drugs')">
				<xsl:choose>
					<xsl:when test="$outputType = 1">
						<xsl:value-of select="normalize-space(.)" />
						<!-- <xsl:if test="position() != last()"> -->
						<xsl:value-of select="','" />
						<!-- </xsl:if> -->

					</xsl:when>
					<xsl:otherwise>
						<xsl:variable name="name" select="name(.)" />
						<xsl:variable name="parentNum">
							<xsl:choose>
								<xsl:when test="$prefix = ''">
									<xsl:text />
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of
										select="concat($prefix,concat(1 + count(parent::*/preceding-sibling::*),'.'))" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:variable>
						<xsl:value-of select="concat($parentNum,$name)" />
						<!-- <xsl:if test="position() != last()"> -->
						<xsl:value-of select="','" />
						<!-- </xsl:if> -->
					</xsl:otherwise>
				</xsl:choose>
			</xsl:if>
		</xsl:for-each>

		<xsl:apply-templates select="drugs/drug">
			<xsl:with-param name="outputType" select="$outputType" />
			<xsl:with-param name="total" select="$clinical.regimen.drugs" />
			<xsl:with-param name="colDef" select="$clinical.regimen.drugs.colDef" />
			<xsl:with-param name="prefix" select="'drug'" />
		</xsl:apply-templates>
		<xsl:call-template name="addRegimen">
			<xsl:with-param name="count" select="$count" />
			<xsl:with-param name="outputType" select="$outputType" />
			<xsl:with-param name="colDef" select="$colDef" />
			<xsl:with-param name="total" select="$total" />
			<xsl:with-param name="prefix" select="$prefix" />
		</xsl:call-template>

	</xsl:template>

	<xsl:template name="addRegimen">
		<xsl:param name="outputType" />
		<xsl:param name="colDef" />
		<xsl:param name="count" />
		<xsl:param name="total" />
		<xsl:param name="prefix" />

		<xsl:if test="$count &lt;= $total">
			<xsl:call-template name="commaSplit">
				<xsl:with-param name="position" select="1" />
				<xsl:with-param name="count" select="$count" />
				<xsl:with-param name="outputType" select="$outputType" />
				<xsl:with-param name="dataString" select="$colDef" />
				<xsl:with-param name="prefix" select="$prefix" />
			</xsl:call-template>

			<xsl:call-template name="addDrugs">
				<xsl:with-param name="count" select="1" />
				<xsl:with-param name="outputType" select="$outputType" />
				<xsl:with-param name="colDef" select="$clinical.regimen.drugs.colDef" />
				<xsl:with-param name="total" select="$clinical.regimen.drugs" />
				<xsl:with-param name="prefix" select="concat($prefix,$count,'.drug')" />
			</xsl:call-template>

			<xsl:call-template name="addRegimen">
				<xsl:with-param name="count" select="$count + 1" />
				<xsl:with-param name="outputType" select="$outputType" />
				<xsl:with-param name="colDef" select="$colDef" />
				<xsl:with-param name="total" select="$total" />
				<xsl:with-param name="prefix" select="$prefix" />
			</xsl:call-template>
		</xsl:if>
	</xsl:template>


	<xsl:template name="addDrugs">
		<xsl:param name="outputType" />
		<xsl:param name="colDef" />
		<xsl:param name="count" />
		<xsl:param name="total" />
		<xsl:param name="prefix" />

		<xsl:if test="$count &lt;= $total">
			<xsl:call-template name="commaSplit">
				<xsl:with-param name="position" select="1" />
				<xsl:with-param name="count" select="$count" />
				<xsl:with-param name="outputType" select="$outputType" />
				<xsl:with-param name="dataString" select="$colDef" />
				<xsl:with-param name="prefix" select="$prefix" />
			</xsl:call-template>

			<xsl:variable name="newpref" select="concat($prefix,concat($count,'.'))" />

			<xsl:call-template name="addDrugInfo">
				<xsl:with-param name="outputType" select="$outputType" />
				<xsl:with-param name="total"
					select="$clinical.regimen.drug.activeIngredients" />
				<xsl:with-param name="colDef"
					select="$clinical.regimen.drug.activeIngredients.colDef" />
				<xsl:with-param name="prefix"
					select="concat($newpref,'activeIngredient')" />
				<xsl:with-param name="count" select="1" />
			</xsl:call-template>

			<xsl:call-template name="addDrugInfo">
				<xsl:with-param name="outputType" select="$outputType" />
				<xsl:with-param name="total"
					select="$clinical.regimen.drug.ageDosing" />
				<xsl:with-param name="colDef"
					select="$clinical.regimen.drug.ageDosing.colDef" />
				<xsl:with-param name="prefix"
					select="concat($newpref,'ageDosingSchedule')" />
				<xsl:with-param name="count" select="1" />
			</xsl:call-template>

			<xsl:call-template name="addDrugInfo">
				<xsl:with-param name="outputType" select="$outputType" />
				<xsl:with-param name="total"
					select="$clinical.regimen.drug.weightGroupDosing" />
				<xsl:with-param name="colDef"
					select="$clinical.regimen.drug.weightGroupDosing.colDef" />
				<xsl:with-param name="prefix"
					select="concat($newpref,'wGroupDosingSchedule')" />
				<xsl:with-param name="count" select="1" />
			</xsl:call-template>

			<xsl:call-template name="addDrugInfo">
				<xsl:with-param name="outputType" select="$outputType" />
				<xsl:with-param name="total"
					select="$clinical.regimen.drug.weightDosing" />
				<xsl:with-param name="colDef"
					select="$clinical.regimen.drug.weightDosing.colDef" />
				<xsl:with-param name="prefix"
					select="concat($newpref,'weightDosingSchedule')" />
				<xsl:with-param name="count" select="1" />
			</xsl:call-template>
			<xsl:call-template name="addDrugs">
				<xsl:with-param name="count" select="$count + 1" />
				<xsl:with-param name="outputType" select="$outputType" />
				<xsl:with-param name="colDef" select="$colDef" />
				<xsl:with-param name="total" select="$total" />
				<xsl:with-param name="prefix" select="$prefix" />
			</xsl:call-template>
		</xsl:if>
	</xsl:template>


	<xsl:template match="drugs/drug">
		<xsl:param name="outputType" />
		<xsl:param name="colDef" />
		<xsl:param name="total" />
		<xsl:param name="prefix" />

		<xsl:for-each select="*">
			<xsl:if
				test="not(name(.) = 'activeIngredients'  or name(.) = 'ageDosing' or name(.) = 'wGroupDosing' or name(.) = 'weightDosing')">
				<xsl:choose>
					<xsl:when test="$outputType = 1">
						<xsl:value-of select="normalize-space(.)" />
						<xsl:value-of select="','" />
					</xsl:when>
					<xsl:otherwise>
						<xsl:variable name="name" select="name(.)" />
						<xsl:variable name="parentNum">
							<xsl:choose>
								<xsl:when test="$prefix = ''">
									<xsl:text />
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of
										select="concat($prefix,concat(1 + count(parent::*/preceding-sibling::*),'.'))" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:variable>
						<xsl:value-of
							select="concat('regimen',1 + count(parent::node()/parent::node()/parent::*/preceding-sibling::*))" />
						<xsl:value-of select="'.'" />
						<xsl:value-of select="concat($parentNum,$name)" />
						<xsl:value-of select="','" />
					</xsl:otherwise>
				</xsl:choose>
			</xsl:if>
		</xsl:for-each>

		<!-- Define regime and drug -->
		<xsl:variable name="newpref"
			select="concat(concat(concat('regimen',concat(1 + count(parent::node()/parent::node()/parent::*/preceding-sibling::*),'.'), $prefix),1 + count(parent::node()/parent::*/preceding-sibling::*)),'.')" />

		<xsl:apply-templates select="activeIngredients/activeIngredient">
			<xsl:with-param name="outputType" select="$outputType" />
			<xsl:with-param name="prefix" select="$newpref" />
		</xsl:apply-templates>

		<xsl:call-template name="addDrugInfo">
			<xsl:with-param name="outputType" select="$outputType" />
			<xsl:with-param name="total"
				select="$clinical.regimen.drug.activeIngredients" />
			<xsl:with-param name="colDef"
				select="$clinical.regimen.drug.activeIngredients.colDef" />
			<xsl:with-param name="prefix"
				select="concat($newpref,'activeIngredient')" />
			<xsl:with-param name="count"
				select="count(activeIngredients/activeIngredient) + 1" />
		</xsl:call-template>

		<xsl:apply-templates select="ageDosing/ageDosingSchedule">
			<xsl:with-param name="outputType" select="$outputType" />
			<xsl:with-param name="prefix" select="$newpref" />
		</xsl:apply-templates>

		<xsl:call-template name="addDrugInfo">
			<xsl:with-param name="outputType" select="$outputType" />
			<xsl:with-param name="total"
				select="$clinical.regimen.drug.ageDosing" />
			<xsl:with-param name="colDef"
				select="$clinical.regimen.drug.ageDosing.colDef" />
			<xsl:with-param name="prefix"
				select="concat($newpref,'ageDosingSchedule')" />
			<xsl:with-param name="count"
				select="count(ageDosing/ageDosingSchedule) + 1" />
		</xsl:call-template>

		<xsl:apply-templates select="wGroupDosing/wGroupDosingSchedule">
			<xsl:with-param name="outputType" select="$outputType" />
			<xsl:with-param name="prefix" select="$newpref" />
		</xsl:apply-templates>

		<xsl:call-template name="addDrugInfo">
			<xsl:with-param name="outputType" select="$outputType" />
			<xsl:with-param name="total"
				select="$clinical.regimen.drug.weightGroupDosing" />
			<xsl:with-param name="colDef"
				select="$clinical.regimen.drug.weightGroupDosing.colDef" />
			<xsl:with-param name="prefix"
				select="concat($newpref,'wGroupDosingSchedule')" />
			<xsl:with-param name="count"
				select="count(wGroupDosing/wGroupDosingSchedule) + 1" />
		</xsl:call-template>

		<xsl:apply-templates select="weightDosing/weightDosingSchedule">
			<xsl:with-param name="outputType" select="$outputType" />
			<xsl:with-param name="prefix" select="$newpref" />
		</xsl:apply-templates>

		<xsl:call-template name="addDrugInfo">
			<xsl:with-param name="outputType" select="$outputType" />
			<xsl:with-param name="total"
				select="$clinical.regimen.drug.weightDosing" />
			<xsl:with-param name="colDef"
				select="$clinical.regimen.drug.weightDosing.colDef" />
			<xsl:with-param name="prefix"
				select="concat($newpref,'weightDosingSchedule')" />
			<xsl:with-param name="count"
				select="count(weightDosing/weightDosingSchedule) + 1" />
		</xsl:call-template>

		<!-- Add in empty drugs -->

		<xsl:variable name="newpref1"
			select="concat('regimen',concat(1 + count(parent::node()/parent::*/preceding-sibling::*),'.'), $prefix)" />
		<xsl:variable name="newcount" select="count(parent::*/drug) + 1" />
		<xsl:call-template name="addDrugs">
			<xsl:with-param name="outputType" select="$outputType" />
			<xsl:with-param name="total" select="$total" />
			<xsl:with-param name="colDef" select="$colDef" />
			<xsl:with-param name="prefix" select="$newpref1" />
			<xsl:with-param name="count" select="$newcount" />
		</xsl:call-template>

	</xsl:template>

	<xsl:template
		match="activeIngredients/activeIngredient|ageDosing/ageDosingSchedule|wGroupDosing/wGroupDosingSchedule|weightDosing/weightDosingSchedule">
		<xsl:param name="outputType" />
		<xsl:param name="colDef" />
		<xsl:param name="total" />
		<xsl:param name="prefix" />
		<xsl:param name="count" />
		<xsl:variable name="drugnum"
			select="concat('drug',1 + count(parent::node()/parent::*/preceding-sibling::*))" />

		<xsl:for-each select="*">
			<xsl:choose>
				<xsl:when test="$outputType = 1">
					<xsl:value-of select="normalize-space(.)" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$prefix" />
					<xsl:value-of select="name(..)" />
					<xsl:value-of select="1 + count(parent::*/preceding-sibling::*)" />
					<xsl:value-of select="'.'" />
					<xsl:value-of select="name(.)" />
				</xsl:otherwise>
			</xsl:choose>
			<xsl:value-of select="','" />
		</xsl:for-each>

	</xsl:template>

	<xsl:template name="addDrugInfo">
		<xsl:param name="outputType" />
		<xsl:param name="colDef" />
		<xsl:param name="total" />
		<xsl:param name="prefix" />
		<xsl:param name="count" />

		<xsl:call-template name="addBlanks">
			<xsl:with-param name="count" select="$count" />
			<xsl:with-param name="outputType" select="$outputType" />
			<xsl:with-param name="colDef" select="$colDef" />
			<xsl:with-param name="total" select="$total" />
			<xsl:with-param name="prefix" select="$prefix" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template name="commaSplit">
		<xsl:param name="dataString" />
		<xsl:param name="position" />
		<xsl:param name="count" />
		<xsl:param name="outputType" />
		<xsl:param name="prefix" />
		<xsl:choose>
			<xsl:when test="contains($dataString,',')">
				<!-- Select the first value to process -->
				<xsl:choose>
					<xsl:when test="$outputType = 0">

						<xsl:variable name="parentNum">
							<xsl:choose>
								<xsl:when test="$prefix = ''">
									<xsl:text />
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="concat($prefix,concat($count,'.'))" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:variable>

						<xsl:value-of
							select="concat($parentNum,substring-before($dataString,','))" />
					</xsl:when>
				</xsl:choose>
				<xsl:value-of select="','" />
				<!-- Recurse with remainder of string -->
				<xsl:call-template name="commaSplit">
					<xsl:with-param name="dataString"
						select="substring-after($dataString,',')" />
					<xsl:with-param name="position" select="$position + 1" />
					<xsl:with-param name="count" select="$count" />
					<xsl:with-param name="outputType" select="$outputType" />
					<xsl:with-param name="prefix" select="$prefix" />
				</xsl:call-template>
			</xsl:when>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="addBlanks">
		<xsl:param name="count" />
		<xsl:param name="outputType" />
		<xsl:param name="colDef" />
		<xsl:param name="total" />
		<xsl:param name="prefix" />
		<xsl:if test="$count &lt;= $total">
			<xsl:call-template name="commaSplit">
				<xsl:with-param name="position" select="1" />
				<xsl:with-param name="count" select="$count" />
				<xsl:with-param name="outputType" select="$outputType" />
				<xsl:with-param name="dataString" select="$colDef" />
				<xsl:with-param name="prefix" select="$prefix" />
			</xsl:call-template>
			<xsl:call-template name="addBlanks">
				<xsl:with-param name="count" select="$count + 1" />
				<xsl:with-param name="outputType" select="$outputType" />
				<xsl:with-param name="colDef" select="$colDef" />
				<xsl:with-param name="total" select="$total" />
				<xsl:with-param name="prefix" select="$prefix" />
			</xsl:call-template>
		</xsl:if>
	</xsl:template>

	<xsl:template match="transmissionIntensity">
		<xsl:param name="count" />
		<xsl:param name="outputType" />
		<xsl:param name="colDef" />
		<xsl:param name="total" />
		<xsl:param name="prefix" />

		<xsl:for-each select="*">
			<xsl:choose>
				<xsl:when test="$outputType = 1">
					<xsl:value-of select="normalize-space(.)" />
					<!-- <xsl:if test="position() != last()"> -->
					<xsl:value-of select="','" />
					<!-- </xsl:if> -->

				</xsl:when>
				<xsl:otherwise>
					<xsl:variable name="parentNum">
						<xsl:choose>
							<xsl:when test="$prefix = ''">
								<xsl:text />
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of
									select="concat($prefix,concat(1 + count(parent::node()/parent::*/preceding-sibling::*),'.'))" />
							</xsl:otherwise>
						</xsl:choose>
					</xsl:variable>
					<!-- <xsl:variable name="ing" select="concat($parentNum,name(..))"/> 
						<xsl:variable name="nname" select="name(.)"/> <xsl:value-of select="concat(concat($ing,$nname),1 
						+ count(parent::*/preceding-sibling::*))"/> -->
					<xsl:value-of select="concat($parentNum,name(.))" />
					<!-- <xsl:value-of select="concat(name(.),1 + count(parent::node()/parent::*/preceding-sibling::*))"/> -->
					<xsl:value-of select="','" />

				</xsl:otherwise>
			</xsl:choose>

		</xsl:for-each>
	</xsl:template>

	<xsl:template match="age|parasitaemia|priorAntimalarialsExclusion">
		<xsl:param name="count" />
		<xsl:param name="outputType" />
		<xsl:param name="colDef" />
		<xsl:param name="total" />
		<xsl:param name="prefix" />

		<xsl:for-each select="*">
			<xsl:choose>
				<xsl:when test="$outputType = 1">
					<xsl:value-of select="normalize-space(.)" />
					<!-- <xsl:if test="position() != last()"> -->
					<xsl:value-of select="','" />
					<!-- </xsl:if> -->

				</xsl:when>
				<xsl:otherwise>
					<xsl:variable name="parentNum">
						<xsl:choose>
							<xsl:when test="$prefix = ''">
								<xsl:text />
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of
									select="concat($prefix,concat(1 + count(parent::node()/parent::*/preceding-sibling::*),'.'))" />
							</xsl:otherwise>
						</xsl:choose>
					</xsl:variable>
					<!-- <xsl:variable name="ing" select="concat($parentNum,name(..))"/> 
						<xsl:variable name="nname" select="name(.)"/> <xsl:value-of select="concat(concat($ing,$nname),1 
						+ count(parent::*/preceding-sibling::*))"/> -->
					<xsl:value-of select="concat($parentNum,name(.))" />
					<!-- <xsl:value-of select="concat(name(.),1 + count(parent::node()/parent::*/preceding-sibling::*))"/> -->
					<xsl:value-of select="','" />

				</xsl:otherwise>
			</xsl:choose>

		</xsl:for-each>
		<xsl:call-template name="addBlanks">
			<xsl:with-param name="count" select="$count" />
			<xsl:with-param name="outputType" select="$outputType" />
			<xsl:with-param name="colDef" select="$colDef" />
			<xsl:with-param name="total" select="$total" />
			<xsl:with-param name="prefix" select="$prefix" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="microscopy">
		<xsl:param name="outputType" />
		<xsl:for-each select="microscopyStain|microscopyStainOther">
			<xsl:choose>
				<xsl:when test="$outputType = 1">
					<xsl:value-of select="normalize-space(.)" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="name(.)" />
				</xsl:otherwise>
			</xsl:choose>
			<xsl:value-of select="','" />
		</xsl:for-each>

		<xsl:apply-templates select="asexualParasitemia">
			<xsl:with-param name="outputType" select="$outputType" />
		</xsl:apply-templates>
		<xsl:apply-templates select="sexualParasitemia">
			<xsl:with-param name="outputType" select="$outputType" />
		</xsl:apply-templates>
		<xsl:apply-templates select="thickFilmCalculationOfParasitemia">
			<xsl:with-param name="outputType" select="$outputType" />
		</xsl:apply-templates>
		<xsl:apply-templates select="thinFilmCalculationOfParasitemia">
			<xsl:with-param name="outputType" select="$outputType" />
		</xsl:apply-templates>
		<xsl:apply-templates select="qualityControl/internal">
			<xsl:with-param name="outputType" select="$outputType" />
		</xsl:apply-templates>
		<xsl:apply-templates select="qualityControl/external">
			<xsl:with-param name="outputType" select="$outputType" />
		</xsl:apply-templates>
	</xsl:template>

	<xsl:template
		match="qualityControl/internal|qualityControl/external|asexualParasitemia|sexualParasitemia|thickFilmCalculationOfParasitemia|thinFilmCalculationOfParasitemia|haemoglobinRecording">
		<xsl:param name="outputType" />
		<xsl:for-each select="*">
			<xsl:choose>
				<xsl:when test="$outputType = 1">
					<xsl:value-of select="normalize-space(.)" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="concat(name(..),'.',name(.))" />
				</xsl:otherwise>
			</xsl:choose>
			<xsl:value-of select="','" />
		</xsl:for-each>
	</xsl:template>

	<xsl:template
		match="markers/recrudescenceMarker|analysisProtocol/mixedAllelesOpen|treatment/regimenAllocation">
		<xsl:param name="outputType" />
		<xsl:for-each select="*">
			<xsl:choose>
				<xsl:when test="$outputType = 1">
					<xsl:value-of select="normalize-space(.)" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="concat(name(..),'.',name(.))" />
				</xsl:otherwise>
			</xsl:choose>
			<xsl:value-of select="','" />
		</xsl:for-each>
	</xsl:template>

	<xsl:template match="followup">
		<xsl:param name="outputType" />
		<xsl:for-each select="duration|feverMeasurement">
			<xsl:choose>
				<xsl:when test="$outputType = 1">
					<xsl:value-of select="normalize-space(.)" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="concat(name(..),'.',name(.))" />
				</xsl:otherwise>
			</xsl:choose>
			<xsl:value-of select="','" />

		</xsl:for-each>
		<xsl:apply-templates select="haemoglobinRecording">
			<xsl:with-param name="outputType" select="$outputType" />
		</xsl:apply-templates>
	</xsl:template>

	<xsl:template
		match="geneotypingToDistinguishBetweenRecrudescenceAndReinfection">
		<xsl:param name="outputType" />
		<xsl:for-each select="applicability">
			<xsl:choose>
				<xsl:when test="$outputType = 1">
					<xsl:value-of select="normalize-space(.)" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="concat(name(..),'.',name(.))" />
				</xsl:otherwise>
			</xsl:choose>
			<xsl:value-of select="','" />
		</xsl:for-each>
		<xsl:apply-templates select="applicable">
			<xsl:with-param name="outputType" select="$outputType" />
		</xsl:apply-templates>
	</xsl:template>

	<xsl:template match="applicable">
		<xsl:param name="outputType" />
		<xsl:for-each
			select="genotypingLaboratory|markerDiscriminant|markerDiscriminantOther">
			<xsl:choose>
				<xsl:when test="$outputType = 1">
					<xsl:value-of select="normalize-space(.)" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="concat(name(..),'.',name(.))" />
				</xsl:otherwise>
			</xsl:choose>
			<xsl:value-of select="','" />
			<xsl:apply-templates select="markers/recrudescenceMarker">
				<xsl:with-param name="outputType" select="$outputType" />
			</xsl:apply-templates>
			<xsl:apply-templates select="analysisProtocol/mixedAllelesOpen">
				<xsl:with-param name="outputType" select="$outputType" />
			</xsl:apply-templates>
		</xsl:for-each>

	</xsl:template>

	<xsl:template match="pharmacology">
		<xsl:param name="outputType" />
		<xsl:apply-templates select="samples/sample">
			<xsl:with-param name="outputType" select="$outputType" />
			<xsl:with-param name="prefix" select="'sample'" />
		</xsl:apply-templates>
		<xsl:apply-templates select="analytes/analyte">
			<xsl:with-param name="outputType" select="$outputType" />
			<xsl:with-param name="prefix" select="'analyte'" />
		</xsl:apply-templates>
		<xsl:apply-templates select="assayReferences/assayReference">
			<xsl:with-param name="outputType" select="$outputType" />
			<xsl:with-param name="prefix" select="'assayReference'" />
		</xsl:apply-templates>
	</xsl:template>

	<xsl:template match="samples/sample">
		<xsl:param name="outputType" />
		<xsl:param name="colDef" />
		<xsl:param name="total" />
		<xsl:param name="prefix" />

		<xsl:for-each select="*">
			<xsl:if test="not(name(.) = 'storages')">
				<xsl:choose>
					<xsl:when test="$outputType = 1">
						<xsl:value-of select="normalize-space(.)" />
						<xsl:value-of select="','" />
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of
							select="concat($prefix,1 + count(parent::*/preceding-sibling::*))" />
						<xsl:value-of select="'.'" />
						<xsl:value-of select="name(.)" />
						<xsl:value-of select="','" />
					</xsl:otherwise>
				</xsl:choose>
			</xsl:if>
		</xsl:for-each>

		<xsl:apply-templates select="storages/storage">
			<xsl:with-param name="outputType" select="$outputType" />
			<xsl:with-param name="prefix" select="$prefix" />
		</xsl:apply-templates>

		<xsl:call-template name="addBlanks">
			<xsl:with-param name="count" select="count(storages/storage)+1" />
			<xsl:with-param name="outputType" select="$outputType" />
			<xsl:with-param name="colDef"
				select="$pharmacology.sample.storages.colDef" />
			<xsl:with-param name="total" select="$pharmacology.sample.storages" />
			<xsl:with-param name="prefix"
				select="concat($prefix,1 + count(parent::*/preceding-sibling::*),'.storage')" />
		</xsl:call-template>

		<xsl:call-template name="addSample">
			<xsl:with-param name="count" select="count(parent::node()/sample)" />
			<xsl:with-param name="outputType" select="$outputType" />
			<xsl:with-param name="colDef" select="$pharmacology.samples.colDef" />
			<xsl:with-param name="total" select="$pharmacology.samples" />
			<xsl:with-param name="prefix" select="$prefix" />

		</xsl:call-template>

	</xsl:template>

	<xsl:template match="storages/storage">
		<xsl:param name="outputType" />
		<xsl:param name="prefix" />
		<xsl:for-each select="*">

			<xsl:choose>
				<xsl:when test="$outputType = 1">
					<xsl:value-of select="normalize-space(.)" />
					<xsl:value-of select="','" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of
						select="concat($prefix,1 + count(parent::node()/parent::node()/parent::*/preceding-sibling::*))" />
					<xsl:text>.</xsl:text>
					<xsl:value-of
						select="concat('storage',1 + count(parent::*/preceding-sibling::*))" />
					<xsl:value-of select="'.'" />
					<xsl:value-of select="name(.)" />
					<xsl:value-of select="','" />
				</xsl:otherwise>
			</xsl:choose>

		</xsl:for-each>
	</xsl:template>

	<xsl:template name="addSample">
		<xsl:param name="outputType" />
		<xsl:param name="colDef" />
		<xsl:param name="count" />
		<xsl:param name="total" />
		<xsl:param name="prefix" />

		<xsl:if test="$count &lt;= $total">
			<xsl:call-template name="commaSplit">
				<xsl:with-param name="position" select="1" />
				<xsl:with-param name="count" select="$count" />
				<xsl:with-param name="outputType" select="$outputType" />
				<xsl:with-param name="dataString" select="$colDef" />
				<xsl:with-param name="prefix" select="$prefix" />
			</xsl:call-template>

			<xsl:call-template name="addBlanks">
				<xsl:with-param name="count" select="1" />
				<xsl:with-param name="outputType" select="$outputType" />
				<xsl:with-param name="colDef"
					select="$pharmacology.sample.storages.colDef" />
				<xsl:with-param name="total" select="$pharmacology.sample.storages" />
				<xsl:with-param name="prefix"
					select="concat($prefix,$count,'.storage')" />
			</xsl:call-template>

			<xsl:call-template name="addSample">
				<xsl:with-param name="count" select="$count + 1" />
				<xsl:with-param name="outputType" select="$outputType" />
				<xsl:with-param name="colDef" select="$colDef" />
				<xsl:with-param name="total" select="$total" />
				<xsl:with-param name="prefix" select="$prefix" />
			</xsl:call-template>
		</xsl:if>
	</xsl:template>

	<xsl:template match="analytes/analyte">
		<xsl:param name="outputType" />
		<xsl:param name="colDef" />
		<xsl:param name="total" />
		<xsl:param name="prefix" />

		<xsl:for-each select="*">
			<xsl:choose>
				<xsl:when test="$outputType = 1">
					<xsl:value-of select="normalize-space(.)" />
					<xsl:value-of select="','" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of
						select="concat($prefix,1 + count(parent::*/preceding-sibling::*))" />
					<xsl:value-of select="'.'" />
					<xsl:value-of select="name(.)" />
					<xsl:value-of select="','" />
				</xsl:otherwise>
			</xsl:choose>
		</xsl:for-each>
		<xsl:call-template name="addBlanks">
			<xsl:with-param name="count" select="count(parent::node()//analyte)" />
			<xsl:with-param name="outputType" select="$outputType" />
			<xsl:with-param name="colDef" select="$pharmacology.analytes.colDef" />
			<xsl:with-param name="total" select="$pharmacology.analytes" />
			<xsl:with-param name="prefix" select="$prefix" />
		</xsl:call-template>

	</xsl:template>
	<xsl:template match="assayReferences/assayReference">
		<xsl:param name="outputType" />
		<xsl:param name="colDef" />
		<xsl:param name="total" />
		<xsl:param name="prefix" />

		<xsl:for-each select="*">
			<xsl:if test="not(name(.) = 'upload')">
				<xsl:choose>
					<xsl:when test="$outputType = 1">
						<xsl:value-of select="normalize-space(.)" />
						<xsl:value-of select="','" />
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of
							select="concat($prefix,1 + count(parent::*/preceding-sibling::*))" />
						<xsl:value-of select="'.'" />
						<xsl:value-of select="name(.)" />
						<xsl:value-of select="','" />
					</xsl:otherwise>
				</xsl:choose>
			</xsl:if>
		</xsl:for-each>

		<xsl:apply-templates select="upload/uploadedUrl">
			<xsl:with-param name="outputType" select="$outputType" />
			<xsl:with-param name="prefix" select="$prefix" />
		</xsl:apply-templates>

		<xsl:call-template name="addBlanks">
			<xsl:with-param name="count"
				select="count(parent::node()//assayReference)" />
			<xsl:with-param name="outputType" select="$outputType" />
			<xsl:with-param name="colDef"
				select="$pharmacology.assayReferences.colDef" />
			<xsl:with-param name="total" select="$pharmacology.assayReferences" />
			<xsl:with-param name="prefix" select="$prefix" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="upload/uploadedUrl">
		<xsl:param name="outputType" />
		<xsl:param name="colDef" />
		<xsl:param name="total" />
		<xsl:param name="prefix" />


		<xsl:choose>
			<xsl:when test="$outputType = 1">
				<xsl:value-of select="normalize-space(.)" />
				<xsl:value-of select="','" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of
					select="concat($prefix,1 + count(parent::node()/parent::*/preceding-sibling::*))" />
				<xsl:text>.</xsl:text>
				<xsl:value-of select="name(.)" />
				<xsl:value-of select="','" />
			</xsl:otherwise>
		</xsl:choose>

	</xsl:template>
</xsl:stylesheet>