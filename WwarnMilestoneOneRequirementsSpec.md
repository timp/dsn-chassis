**Please note, this page is superseded by the [Chassis/WWARN 1.0 User Requirements Specification Draft 6](http://dsn-chassis.googlecode.com/svn/tags/wwarn/docs/draft-6/content/requirements/1.0/index.html). See the ChassisWwarn wiki page for the latest version of the User Requirements Specification. The content below is maintained for historical reference.**


---

# Chassis/WWARN (1.0) Data Management Platform - User Requirements Specification #

This version: **Draft 5** (2009-09-02)

**Changes Since Draft 4** (2009-09-01):

  * Added discussion of options w.r.t. data dictionaries in a single format, or any format.
  * Added note about intended audience to introduction.

([view diff](http://code.google.com/p/dsn-chassis/source/diff?path=/wiki/WwarnMilestoneOneRequirementsSpec.wiki&format=side&r=406&old_path=/wiki/WwarnMilestoneOneRequirementsSpec.wiki&old=402))

**Changes Since Draft 3** (2009-08-12):

  * Changed "first milestone release M1" to "first major release 1.0" to adopt CGGH best practice for release numbering, and allow definition of milestones within releases. Changed all mention of "milestone M1" to "release 1.0".
  * Reworked introduction to clarify scope of Chassis/WWARN and intentions of 1.0 release to provide a bare minimum system as soon as possible.
  * Changed to use "The WWARN Application" instead of "the WWARN database", because of potential confusion over what "the WWARN database" refers to.
  * Added user and administrator roles.
  * Changed terminology used to describe gatekeeper review, to improve clarity, and emphasise distinction between gatekeeper review (acceptance) and curator's approval of a submission for release. Now, a submitter requests a gatekeeper review for a new submission. The outcome of the gatekeeper's review is a decision either to accept the submission, or not to accept the submission.
  * Added points and issues raised in discussion with Philippe and Patrice, 2009-08.
  * Added points and issues raised in design discussions with informatics team, 2009-08.

([view diff](http://code.google.com/p/dsn-chassis/source/diff?path=/wiki/WwarnMilestoneOneRequirementsSpec.wiki&format=side&r=402&old_path=/wiki/WwarnMilestoneOneRequirementsSpec.wiki&old=193))

**Changes Since Second Draft** (2009-08-10):

  * No substantive changes to document content.
  * Added more issues and candidate requirements raised during Chassis design meeting 2009-08-11.

([view diff](http://code.google.com/p/dsn-chassis/source/diff?spec=svn192&r=192&format=side&path=/wiki/WwarnMilestoneOneRequirementsSpec.wiki&old_path=/wiki/WwarnMilestoneOneRequirementsSpec.wiki&old=191))

**Changes Since First Draft** (2009-07-22):

  * No substantive change to document content.
  * Added Candidate Requirements & Open Issues section with requirements, issues & questions raised during initial conversation with Dominic, and during initial design meeting the Oxford IT team.
  * Moved all @@TODO items from main sections to Candidate Requirements and Open Issues section.

([view diff](http://code.google.com/p/dsn-chassis/source/diff?spec=svn190&r=190&format=side&path=/wiki/WwarnMilestoneOneRequirementsSpec.wiki&old_path=/wiki/WwarnMilestoneOneRequirementsSpec.wiki&old=104))


---

**Table of Contents**



---


## Introduction ##

This document is a draft specification of user requirements for the first major release (1.0) of the _Chassis/WWARN_ system for managing the submission, curation and standardisation of data for the World Wide Anti-malarial Resistance Network (WWARN).

### Scope of Chassis/WWARN ###

#### WWARN Data Management ####

The _Chassis/WWARN_ system is intended to support the **data management activities** of the WWARN data-sharing network. These data management activities include the submission of data, curation of submitted data to meet defined quality control and other (e.g., ethical) criteria, and mapping of data onto WWARN standards for data structure and semantics. The end goal of these activities is to produce data that is suitable for ingest into and publication by the WWARN Application.

#### Chassis/WWARN and The WWARN Application ####

Note that the _Chassis/WWARN_ system is considered distinct from _The WWARN Application_. _The WWARN Application_ is a separate system, intended to support the sharing and publication of standardised, quality controlled data, with tools for the analysis and visualisation of those data. This specification assumes that data will be periodically exported from the _Chassis/WWARN_ system and ingested into _The WWARN Application_. Management of this export and ingest process is out of scope for the _Chassis/WWARN_ system, and will be specified elsewhere. Requirements for _The WWARN Application_, including all requirements for data visualisation and analysis tools, are not in scope for this document, and are specified elsewhere.

#### First Major Release (1.0) ####

This document specifies mandatory requirements ("must-haves") for the first major release (1.0) of the _Chassis/WWARN_ system for production use by WWARN. The goal of the 1.0 release of _Chassis/WWARN_ is to provide a system that supports the **bare minimum needed** to enable WWARN to begin accepting and curating data submissions. I.e., a system that is **just sufficient** to enable WWARN to establish key operational procedures and begin basic data management activities, **as soon as possible**. The functionality of the 1.0 release (as specified here) is therefore limited to those features that are deemed absolutely necessary to enable the basic data management operations of WWARN.

Subsequent releases of _Chassis/WWARN_ will then provide additional functionality as needed. Specifying this additional functionality is not in scope for this document, however, some features that might be developed for future releases of _Chassis/WWARN_ are briefly discussed.

The aim for the 1.0 release is to coincide with the first WWARN milestone, which is scheduled for Q1 (Jan) 2010. However, because this document is in flux, and requirements may be added or removed, it is not possible to provide a precise release date at this stage.

#### Limited Release for WHO Data Submission (0.1) ####

Note that a limited functionality release (0.1) of _Chassis/WWARN_, enabling submission of data using the WHO spreadsheet format only, is also planned. The requirements for _Chassis/WWARN (0.1) will be specified in a separate document, however, they are expected to form a subset of the requirements specified here._

### About This Document ###

#### Intended Audience ####

This document is an internal draft, intended for discussion within the WWARN consortium. It is meant to provide a concise description of the functionality provided by Chassis/WWARN 1.0. It is not intended for end-users of the Chassis/WWARN system.

#### Working Draft ####

This document is a working draft, and is intended to act as a focus for discussion within the WWARN consortium. Feedback is very welcome, and may be sent via email to informatics@wwarn.org.

Where there may be several possible choices relating to a particular requirement, some discussion is included in the document below, in sub-sections headed "**Discussion**".

#### Chassis/`*` ####

This user requirements specification is tailored towards the requirements of the WWARN data-sharing network. However, note that the _Chassis/WWARN_ system is intended to be the first instance of a generic platform (_Chassis/`*`_), which can be customised to support the data management activities of other, similar, data-sharing networks. Therefore, this specification aims to describe requirements in a generic way, wherever possible, using terminology that is transferable to other data-sharing networks.

#### Structure of this Document ####

This document is divided into two main parts. The first part describes use cases and functional requirements relating to the submission of new data to WWARN, and the initial review of those submissions by a gatekeeper. The second part describes use cases and functional requirements for the curation and standardisation of submitted data, i.e., all activities necessary to make submitted data suitable for release to _The WWARN Application_.

### Roles ###

The following roles are defined here for users of the _Chassis/WWARN_ system.

  * A **user** is anyone who is authorized to access the _Chassis/WWARN_ system.

  * A **submitter** is a user who can create a data submission. A submitter represents and is assumed to act for the people and/or organisations who have intellectual property rights and copyright over any data they submit.

  * A **gatekeeper** is a user with the authority to accept a data submission. By accepting a submission, the gatekeeper is asserting that the submission is relevant to WWARN, and authorising the expenditure of resources (i.e., time and effort) on curating and standardising the submission.

  * A **curator** is a user who will perform curation activities, which might include (but is not limited to) reviewing submitted data, revising submitted data, ensure data meet quality control criteria and community standards.

  * A **coordinator** is a user who has overall responsibility for the operations of a data-sharing network.

  * An **administrator** is a user with the authority to configure the _Chassis/WWARN_ system.


---

## Part 1 - Data Submission ##


---

### Data Submission Workflow - Key Points ###

The _Chassis/WWARN_ (1.0) system is designed to support a data submission workflow with the following key characteristics.

#### General Points ####

  * Data submission is the process whereby new data enters the Chassis/WWARN system.

  * A submitter initiates the process by creating a **new submission**. The submitter then provides some information about the submission (see below), and uploads a data file containing the initial version of the submitted data.

#### Data Dictionaries ####

  * Submitted data must be linked to a data dictionary, before a submitter can be accepted (see below). A data dictionary defines the structure (i.e., layout, format) and semantics (i.e., meaning) of submitted data.

  * A submitter can create a new data dictionary for each new submission, or re-use an existing data dictionary created by themselves or someone else.

#### Gatekeeper Review ####

  * Once the submitted data has been linked to a data dictionary, the submitter can request **gatekeeper review** of their new submission. The gatekeeper is responsible for performing an initial evaluation of the submitted data and accompanying data dictionary, to determine whether or not resources (i.e., time and effort) should be spent curating the data. The gatekeeper **is not** responsible for making any modifications to the data or approving the data for release to _The WWARN Application_.

  * The outcome of a gatekeeper review is either to accept a submission, or not to accept a submission. If the submission is accepted, then the submission proceeds to curation. Once a gatekeeper has accepted a submission, they can assign a curator to the submission. Responsibility for the submission then passes from the gatekeeper to the assigned curator.

  * Before a submitter has requested gatekeeper review of a submission, they can make revisions to the submitted data. A submitter can also make revisions after a submission is accepted. If a submission is not accepted, a submitter can revise a submission after gatekeeper review, and make a new request for gatekeeper review of the revised submission.

  * There may be more than one gatekeeper. For Chassis/WWARN, it is anticipated that submissions will be assigned to a gatekeeper based on the module(s) to which the submission is linked by its submitter.

  * The criteria that the gatekeeper use to decide whether or not to accept a submission are referred to here as **acceptance criteria**. Each module may have different acceptance criteria. A module may also have no acceptance criteria, i.e., may accept all submissions by default.

  * A gatekeeper may also assign a priority to an accepted submission.

#### Constraints on Submitted Data & Data Dictionaries ####

  * Chassis/WWARN (1.0) does not place any constraints on the nature or format of the data file uploaded as submitted data. The Chassis/WWARN (1.0) system **will not** attempt to parse the submitted data file, and **will not** provide any previews of the submitted data. Later versions of Chassis/WWARN may provide support for parsing and previewing of **some** data files, e.g., where data files conform to a standard data dictionary, or where data files contain a single table of data in a spreadsheet format.

  * A data dictionary defines expectations for the structure and semantics of submitted data. In the simplest case, a data dictionary is a human-readable document, stating that the submitted data are structured as a single table, listing the field (i.e., column) names used in the table, and providing a textual definition for each field name. However, Chassis/WWARN (1.0) does not place any restrictions on the nature or format of the data dictionary file uploaded when a new data dictionary is created or revised. The system **will not** attempt to parse the data dictionary file, nor provide any preview of its contents. Later versions of Chassis/WWARN may constrain the content and layout of data dictionary files, and parse files to store and provide views of data dictionaries. Later versions of the system may also provide online support for creating and editing the contents of data dictionaries, without needing to upload files.

##### Discussion #####

This document tentatively takes the position that, for the first major release of Chassis/WWARN, there will be **no constraints** on the structure or content of data dictionaries. Data dictionaries are simply uploaded as files of any type, with any content. This position was taken because the complete range of possible submitted data structures is not currently known, and therefore it may be useful in the early stages of WWARN to enable some flexibility as the consortium encounters new data and works towards a common approach to data dictionaries.

However, the goal for later releases of Chassis/WWARN is to support a **single type** of data dictionary, with the content of the data dictionary managed through a web interface, rather than through the upload of files. Based on previous experience in CGGH, the likely approach would be that each such data dictionary would define a set of variables (also known as fields or attributes), where each variable has a unique identifier, a human-readable label, a human-readable definition of the variable's meaning, a datatype for the variable, the units of the variable, and any range constraints (e.g., numeric constraints or controlled vocabulary) on the values the variable may take. This would enable further functionality, such as an analysis of the diversity of variable names used across all data submitted, which can support the process of convergence on a common set of data standards across the consortium. It would also be possible in some cases to use the data dictionary to drive automated functionality such as simple validation of datatypes and data ranges. It would also provide a basis for developing functionality to define and execute mappings (i.e., transformations) between data conforming to different data dictionaries, which is planned for future releases of Chassis/WWARN.

Placing no constraints on the content or structure of data dictionaries, in the first major release of Chassis/WWARN, may however have a number of undesirable consequences. Firstly, many submitters may not have prior experience of constructing data dictionaries, and may not know what information to provide or how to provide it. Without clear guidance and examples, both the quality (usefulness), the structure and the content of data dictionaries created by submitters may be highly variable. Whilst prominent guidance and examples may ameliorate some of these issues, there is no guarantee on consistency or convergence towards a common approach to constructing data dictionaries. Secondly, without any constraints on data dictionaries, it will not be possible to reliably parse or analyse all data dictionaries, or to use the data dictionary to support any automated functionality such as simple validation of datatypes and data ranges. Thirdly, once a common approach to data dictionaries is agreed and supported in Chassis/WWARN, there would be a legacy of previously created dictionaries in a variety of formats to migrate, which would require some effort.

Whether or not the first major release of Chassis/WWARN enforces a single approach to data dictionaries, or is more flexible and places no constraints on files uploaded as data dictionaries, is therefore an important open question in this specification.


---

### Data Submission Use Cases ###

The use cases below provide a more detailed illustration of how a user will interact with the Chassis/WWARN system in the most commonly anticipated scenarios. The use cases **are not** intended to cover every possible scenario.

#### Use Case: Submitter Creates a New Study ####

Every submission must be linked to at least one study. Before creating the first submission for a study, a submitter creates a new study, providing at a title and textual summary for the study, and linking the new study to at least one module.

A submitter can then proceed to enter all required metadata for the new study (i.e., fill out the study site questionnaire). However, it is **not** necessary that any further study metadata be entered before a submitter can create new submissions linked to the study, or request gatekeeper review for those submissions.

#### Use Case: Submitter Creates a New Submission With a New Data Dictionary ####

A submitter creates a new submission, provides a title for the submission, links the submission to at least one study and at least one module, and uploads a file containing the initial version of the submitted data.

In this case, no data dictionary is already present in the system that corresponds to the submitted data. The submitter creates a new data dictionary, provides a title for the dictionary, and uploads a file containing the initial version of the data dictionary. The submitter then links the initial version of the submitted data to the initial version of the new data dictionary.

The submitter then makes a request for gatekeeper review of the submission.

#### Use Case: Submitter Creates a New Submission With an Existing Data Dictionary ####

A submitter creates a new submission, provides a title for the submission, links the submission to at least one study and at least one module, and uploads a data file containing the initial version of the submitted data.

In this case, a data dictionary was previously created for a different submission that also applies to the new submission. The submitter links the initial version of the submitted data to the appropriate version of the existing data dictionary.

The submitter then makes a request for gatekeeper review of the submission.

#### Use Case: Submitter Revises a Submission Before Requesting Gatekeeper Review ####

A submitter creates a new submission, with a new data dictionary.

Before the submitter makes a request for gatekeeper review, she realises that some data are missing in the initial version of the submitted data. She downloads the initial version of the submitted data, adds the missing data, and uploads a revised version of the submitted data. She then links the revised version of the submitted data to the initial version of the data dictionary, because neither the structure nor the semantics of the data have changed.

The submitter then makes a request for gatekeeper review of the submission.

#### Use Case: Submitter Revises a Submission and Dictionary Before Requesting Gatekeeper Review ####

A submitter creates a new submission, with a new data dictionary.

Before the submitter makes a request for gatekeeper review, she realises that some fields are missing in the initial version of the submitted data. She downloads the initial version of the submitted data, adds the missing fields and corresponding data, and uploads a new version of the submitted data.

Because the structure of the data has changed, the submitter also uploads a new version of the data dictionary she previously created. The new version includes a definition of each of the new fields. She then links the revised version of the submitted data with the revised version of the data dictionary.

The submitter then makes a request for gatekeeper review of the submission.

#### Use Case: Gatekeeper Reviews and Accepts a Submission ####

A submitter makes a request for gatekeeper review of her new submission. The request contains a link to a specific version of the submitted data, and a specific version of the associated data dictionary, as specified in the request by the submitter.

A gatekeeper is allocated to review the request. She downloads the specified version of the submitted data and the specified version of the data dictionary, reviews them, and decides to accept the submission. The gatekeeper then assigns a curator to the submission, and curation begins.

#### Use Case: Gatekeeper Accepts a Submission After Revision ####

A submitter makes a request for gatekeeper review of their new submission.

A gatekeeper is allocated to review the request. She downloads the specified version of the submitted data and the specified version of the data dictionary, and reviews them.

In this case, the data dictionary is not complete, and the gatekeeper cannot understand the data. The gatekeeper decides not to accept the submission, explaining that the data dictionary is incomplete, and encouraging the submitter to revise the data dictionary and make a new request for gatekeeper review.

The submitter is notified of the outcome of the gatekeeper's review. She then uploads a new version of the data dictionary containing the missing information, removes the link from the initial version of the submitted data to the initial, incomplete, version of the data dictionary, and links the initial version of the submitted data to the revised version of the data dictionary.

The submitter then sends a new request for gatekeeper review of the revised submission.

A gatekeeper is notified of the request for review, downloads the initial version of the submitted data and the revised version of the data dictionary, reviews them, and decides to accept the submission. The gatekeeper assigns a curator, and curation begins.

#### Use Case: Submitted Data is Not Relevant to WWARN ####

A submitter makes a request for gatekeeper review of her new submission.

A gatekeeper is allocated to review the request. She downloads the specified version of the submitted data and the specified version of the data dictionary, and reviews them.

In this case, it is clear that none of the data are relevant to any of WWARN's scientific modules. The gatekeeper decides that the submission will not be accepted, providing an explanation of the reasons why the submission was not accepted. The submitter is notified of the outcome of the gatekeeper's review and their explanation of the outcome. The submitter deletes her submission.

#### Use Case: Coordinator Reviews Submission Activity ####

A coordinator looks through a list of all submissions that are pending gatekeeper review. She notices that one submission has been pending gatekeeper review for more than 2 weeks. She contacts the gatekeeper assigned to the submission and asks that the submission be reviewed urgently.

#### Use Case: Gatekeeper Reviews Submission Activity ####

A gatekeeper looks through a list of all submission requests that have been allocated to her and that have not yet been reviewed. She selects a request from the list and reviews it.

#### Use Case: Submitter Reviews Their Submission Activity ####

A submitter looks through a list of all her submissions that have been reviewed by a gatekeeper and accepted, a list of submissions that have been reviewed and not accepted, a list of all her submissions that are pending gatekeeper review, and all her "draft" submissions for which she has not yet requested gatekeeper review.

From the list of her draft submissions, she selects those that are ready for gatekeeper review, and requests gatekeeper review for them.


---

### Data Submission Functional Requirements ###

#### (Submitter) Managing Studies ####

  * A submitter will be able to create a new study. This involves providing a title and textual summary for the study, and linking the study to at least one module.

  * A submitter will be able to view a list of all studies they have previously created.

  * A submitter will be able to view a list of all studies created by anyone. @@TODO privacy issues

  * Lists of studies can be filtered by module.

#### (Submitter) Entering and Revising Study Metadata ####

  * A submitter will be able to enter metadata about any study they have previously created.

  * A submitter will be able to revise (i.e., update, modify) metadata about any study they have previously created.

N.B. study metadata includes the study site questionnaire (SSQ).

@@TODO specify SSQ fields

#### (Submitter) Creating a New Submission & Uploading Data ####

  * A submitter will be able to create a new submission. This involves providing a title and textual summary for the submission, associating the submission with at least one previously created study, associating the submission with at least one WWARN module, and uploading a single file containing the initial version of the submitted data.

By default, the submitter becomes the owner of any submission they create.

Note that a new submission will be created in a "draft" state, i.e., will not automatically be forwarded to a gatekeeper for review. The submitter must explicitly request gatekeeper review for a new submission, see below.

#### (Submitter) Revising Submitted Data ####

  * A submitter will be able to upload a data file containing a new version (i.e., revision) of the submitted data, for any submission they own.

  * A submitter will be able to view a version history of the submitted data for any submission they own.

#### (Submitter) Downloading Submitted Data ####

  * A submitter will be able to download the latest version of the submitted data, for any submission they own.

  * A submitter will also be able to download any previous version of the submitted data, for any submission they own.

#### (Submitter) Creating a Data Dictionary ####

  * A submitter will be able to create a new data dictionary. This involves providing a title and textual summary for the new data dictionary, and uploading a file containing the initial version of the data dictionary.

By default, a submitter is the owner of any data dictionary they create.

#### (Submitter) Revising a Data Dictionary ####

  * A submitter will be able to upload a file containing a new version of a previously created data dictionary they own.

  * A submitter will be able to view a version history for any data dictionary they own.

#### (Submitter) Downloading a Data Dictionary ####

  * A submitter will be able to download any version of any data dictionary. @@TODO privacy issues

#### (Submitter) Linking Submitted Data With a Data Dictionary ####

  * For any submission they own, a submitter will be able to link a specific version of the submitted data with a specific version of a data dictionary. They do not have to also own the data dictionary to which the link is made.

  * For any submission they own, a submitter will be able to remove the current link between the latest version of the submitted data and the version of the data dictionary to which it is linked, and replace it with a new link to a different version of the same data dictionary, or some version of a different data dictionary.

  * For any submission they own, a submitter will be able to view a history of any changes that have been made to links between the submitted data and data dictionaries.

#### (Submitter) Requesting Gatekeeper Review ####

  * A submitter will be able to request gatekeeper review for any submission they own.

#### (Submitter) Overview of Submission Activity ####

  * A submitter will be able to view a list of all submissions they own. The status of each submission, i.e., either draft (not yet submitted for gatekeeper review), pending gatekeeper review, or gatekeeper reviewed, will be clearly shown. If the submission has been reviewed by a gatekeeper, the outcome of each review (either accepted or not accepted) will also be clearly shown.

  * A submitter will also be able to view a list of only draft submissions, a list of submissions that are pending gatekeeper review, a list of submissions that have been reviewed by a gatekeeper and accepted, and a list of submissions that have been reviewed by a gatekeeper and not accepted.

  * Any of these submission lists can be grouped by study or module.

#### (Gatekeeper) Overview of Submission Activity ####

  * A gatekeeper will be able to view a list of all submissions from any submitter, not including draft submissions. The status of each submission (i.e., either pending gatekeeper review, reviewed and accepted, or reviewed and not accepted) will be clearly shown.

  * A gatekeeper will also be able to view a list of only those submissions that have been reviewed and accepted (not necessarily by themselves), a list of submissions that have been reviewed and not accepted, and a list of submissions that are pending gatekeeper review.

  * Any of these submissions lists can be grouped by submitter, study or module.

#### (Gatekeeper) Downloading Submitted Data and Data Dictionaries ####

  * A gatekeeper will be able to download any version of submitted data for which a request for gatekeeper review has been made. A gatekeeper will also be able to download any version of a data dictionary to which the submitted data is linked.

#### (Gatekeeper) Reviewing Submissions ####

  * A gatekeeper will be able review any any submission for which a request for gatekeeper review has been made, and indicate that the outcome of the review is a decision to accept the submission, or a decision not to accept the submission.

  * A gatekeeper will be able to assign a curator to an accepted submission.

Note that, once a gatekeeper has accepted a submission, that acceptance is assumed to apply, even if the submitter uploads a new revision of the submitted data.

  * If the outcome of a gatekeeper review is a decision not to accept a submission, this also involves providing an explanation of the reasons why the submission has not been accepted, and any further information for the submitter.

#### (Coordinator) Overview of Submission Activity ####

  * A coordinator will be able to view a list of all submissions from any submitter, not including draft submissions. The status of each submission (i.e., either pending gatekeeper review, or reviewed by a gatekeeper) will be clearly shown. Where the submission has been reviewed by a gatekeeper, the outcome of each review (either accepted or not accepted) will also be clearly shown.

  * A coordinator will also be able to view a list of only those submissions that have been reviewed by a gatekeeper and accepted, a list of submissions reviewed by a gatekeeper and not accepted, and a list of submissions that are pending gatekeeper review.

  * Any of these submissions lists can be grouped by submitter, study or module.


---

## Part 2 - Data Curation & Standardisation ##

---

### Data Curation & Standardisation Workflow - Key Points ###

#### General Points ####

  * The curation process takes submitted data as input and produces data that is suitable for release to _The WWARN Application_.

  * Data that is suitable for release to _The WWARN Application_ must conform to one of the standard data dictionaries defined by the curators, and must meet all other release criteria defined by the curators, e.g., ethical or quality control criteria. Therefore, the curation process involves doing whatever is necessary to get the data to the point where it meets all release criteria.

  * The curation process comprises a wide and open-ended range of possible activities. In general, curation activities either make some revision to the submitted data (e.g., adding, removing, or restructuring data), or review the submitted data with respect to some criteria (e.g., ethical criteria, quality control criteria) and assert that the data either do or do not meet those criteria.

#### Transformation and Mapping to Standard Data Dictionaries ####

  * In addition to the non-standard data dictionaries that submitters can create, the Chassis/WWARN system has one or more data dictionaries managed by the curators that are agreed WWARN standard data dictionaries. Submitted data must be transformed into data that conforms with one of these standard data dictionaries before it can be made available for ingest to _The WWARN Application_.

  * A special type of revision activity involves transforming data that is submitted in a non-standard form into something that conforms with one of the standard data dictionaries. Defining such a transformation is referred to here as "mapping". However, note that just because data conforms to the structural and semantic conditions of a standard data dictionary, does not necessarily mean that the data are reliable or make any sense. I.e., quality control is a separate concern from mapping submitted data to standard data dictionaries.

  * The Chassis/WWARN (beta) system does not prescribe the order in which data are mapped to a standard data dictionary, and revised to meet quality control standards and other release criteria. I.e., data may be submitted in a non-standard form, revised to meet quality control standards, and then mapped to a standard data dictionary; or data may be first mapped to a standard data dictionary, and then revised to meet quality control criteria; or revisions may be applied both before and after mapping to a standard data dictionary.

  * In the Chassis/WWARN (beta) system, no support will be provided for transforming data. I.e., it is assumed that all data will be transformed using other systems, e.g., off-line desktop tools. Later versions of Chassis/WWARN may provide online support for defining and executing mappings between data conforming to different data dictionaries.

  * Standard data dictionaries are like any other data dictionaries, in that they define expectations for the structure and semantics of submitted data, except that they are maintained by the curators. In the simplest case, a standard data dictionary is a human readable document, stating that the data should be structured as a single table, and providing a list of field names with a textual definition for each field. However, it is expected that a standard data dictionary will also provide details of the expected datatype for each field (e.g., string, integer, decimal, datetime, etc.), the expected units for each field (e.g., g/dl), and any constraints on the expected value (e.g., numerical range constraints).

  * A special type of review activity involves checking that submitted data conform with respect to a data dictionary, also referred to as validation. The Chassis/WWARN (beta) system will not provide any support for validation. I.e., it is assumed that validation will be performed using other systems, e.g., by manual review, or using off-line desktop tools. Later versions of Chassis/WWARN may provide some online support for validation, where possible.

#### Specification of Release Criteria ####

  * A second special type of review activity involves reviewing submitted data with respect to a specification of **release criteria**. Such a specification states **all** of the criteria that must be met before data may be released to _The WWARN Application_. These criteria might include, but are not limited to, ethical criteria (e.g., data must be anonymous), quality control criteria (e.g., protocols must be approved), and structural criteria (e.g., data must conform with a given standard data dictionary).

  * Note that a specification of release criteria might delegate to other specifications and review mechanisms. For example, a specification of release criteria might state that a submission must meet ethical criteria as stated in some version of an ethical criteria specification. A curator will then approve release if the submission has already passed ethical review by another curator, but will not check the details of the ethical criteria themselves.

  * Specifications of release criteria are versioned, to enable the criteria to be modified without confusing the status of previously released data. This is also intended to enable _The WWARN Application_ to migrate incrementally towards scricter release criteria as these are defined and agreed by the network.

  * Each WWARN module is expected to define its own release criteria.

#### Provenance & Derivation of Data ####

  * A key design goal is that the complete and true **derivation** of any data released to _The WWARN Application_ must be recorded. By derivation, we mean the complete sequence of actions and revisions that led from the data as submitted to the data in its released form, i.e., its provenance.

  * A curator can upload revisions of submitted data. To ensure that the true derivation of any data revision is captured, the curator must explicitly assert the derivation of any new revision they make, i.e., they must select the version of the data (either submitter version or another curator version) that was taken as input to the curator's new revision. This also ensures that curators can go back and do work on versions of the submitted data other than the latest version, and an accurate audit trail of their activities will still be maintained. (I.e., for any one submission, the curators' revisions to the submitted data are assumed to form an arbitrarily branching tree, where the root of each tree is one of the submitter's revisions.)

  * A submitter can upload a revision of submitted data at any time. To keep things simple for a submitter, they do not have to assert the derivation of each new revision. (I.e., for any one submission, the submitter's revisions of the submitted data are assumed to form a single linear version series.)


---

### Data Curation Use Cases ###

The use cases below provide a more detailed illustration of how a user will interact with the Chassis/WWARN system in the most commonly anticipated scenarios. The use cases **are not** intended to cover every possible scenario.

Note that some details of the use cases below are simplified, to avoid distracting from the key features of the use case that are relevant to Chassis/WWARN. For example, the reasons why a curator might need to make revisions to submitted data, and the nature of the revisions they might need to make, will be far more varied and subtle than are suggested below.

#### Notation Conventions ####

In the use cases below, letters A, B, C, etc., are used to help identify different revisions of submitted data, e.g., "the initial revision of the submitted data (A) was revised by the curator to create a new revision (B)". If the letter has the superscript "s" (e.g., A<sup>s</sup>), then the revision is linked to a standard data dictionary. If no superscript is present, the data is linked to a non-standard data dictionary.

#### Use Case: Curator Manages a Standard Data Dictionary ####

A curator creates a new standard data dictionary, provides a title and textual summary for the dictionary, links the dictionary to one or more modules, and uploads a file containing the initial version of the dictionary.

At a later time, the WWARN curators agree to make an update to this standard data dictionary, to incorporate some new fields as standard. The curator uploads a new file containing the revised version of the data dictionary, and enters a log message describing the changes.

#### Use Case: Curator Manages a Specification of Release Criteria ####

A curator creates a new specification of release criteria, provides a title and textual summary for the specification, links the specification to the module to which it applies, and uploads a file containing the initial version of the specification.

At a later time, the WWARN curators agree to make the release criteria more stringent, including new quality control criteria. The curator uploads a new file containing the revised version of the specificiation of release criteria, and enters a log message describing the changes.

The curators review all prior data submissions that were approved with respect to the old version of the release criteria, to determine if they also can be approved with respect to the updated version of the release criteria.

#### Use Case: Standard Submission, Requires No Curation ####

A submitter requests gatekeeper review for a new submission, where the submitted data is already linked by the submitter to a WWARN standard data dictionary. A gatekeeper accepts the submission and assigns a curator.

The assigned curator reviews the initial version of the submitted data (A<sup>s</sup>), and finds that no changes need to be made. She reviews the submitted data with respect to the latest version of the appropriate data release criteria, and asserts that the release criteria have been met.

The submitter sees that her initial version of the submitted data (A<sup>s</sup>) has been approved for release to _The WWARN Application_, and that no other actions were taken.

#### Use Case: Curation of Standard Submission ####

A submitter requests gatekeeper review for a new submission, where the submitted data is already linked to a WWARN standard data dictionary. A gatekeeper accepts the submission and assigns a curator.

The assigned curator downloads the initial version of the submitted data (A<sup>s</sup>), reviews it, and finds that some rows have missing data values. She removes the rows with missing data, uploads a new data file containing the revised data (B<sup>s</sup>), links it to the version of the submitted data from which it was derived (A<sup>s</sup>), and links it to the standard data dictionary. She also enters a log message explaining what changes were made and why.

The assigned curator then reviews the submitted data (B<sup>s</sup>) with respect to the latest version of the appropriate data release criteria, and asserts that the release criteria have been met.

The submitter sees that her submission has been approved for release to _The WWARN Application_, after the assigned curator made a revision (B<sup>s</sup>) to her initial version (A<sup>s</sup>). She downloads the curator's revision (B<sup>s</sup>) to inspect the changes.

#### Use Case: Standard Submission Requires Submitter Revision ####

A submitter requests gatekeeper review for a new submission, where the submitted data is already linked to a WWARN standard data dictionary. A gatekeeper accepts the submission and assigns a curator.

The assigned curator downloads the initial version of the submitted data (A<sup>s</sup>), reviews it, and finds that some rows have data values that appear to be out of range. She decides to contact the submitter, and notify her of the problem.

The submitter realises that some errors were made in the initial version of the submitted data (A<sup>s</sup>). The submitter downloads her initial version of the submitted data (A<sup>s</sup>), fixes the errors, and uploads a new version of the submitted data (B<sup>s</sup>).

The assigned curator then reviews the submitted data (B<sup>s</sup>) with respect to the latest version of the appropriate data release criteria, and asserts that the release criteria have been met.

The submitter sees that her submission has been approved for release to _The WWARN Application_, after she made a revision (B<sup>s</sup>) to her initial version (A<sup>s</sup>).

#### Use Case: Curation of Non-Standard Submission ####

A submitter requests gatekeeper review for a new submission with a new data dictionary. The data dictionary is not one of the WWARN standard data dictionaries. A gatekeeper accepts the submission and assigns a curator.

The assigned curator downloads the initial version of the submitted data (A) and the initial version of the data dictionary. She finds that several rows appear to be missing some data values. She removes the rows with missing data, uploads her revised version of the submitted data (B), links it to the version of the submitted data from which it was derived (A), links it to the non-standard data dictionary, and enters a log message describing the changes she has made and the reasons for making the changes.

The assigned curator then decides to map the submitted data onto the appropriate standard data dictionary. She downloads her curated revision of the submitted data (B), and downloads the latest version of the appropriate standard data dictionary. Using her favourite desktop data transformation tool, she creates a new data file conforming to the standard data dictionary. She then uploads this new data file (C<sup>s</sup>), links it to the version of the submitted data from which it was derived (B), links it to the latest version of the standard data dictionary, and enters a log message describing the transformation she performed.

The assigned curator now believes the data is ready for release to _The WWARN Application_. She reviews her standardised version of the submitted data (C<sup>s</sup>) with respect to the latest version of the WWARN data release criteria, and asserts that the release criteria have been met. The data are then made available for export to _The WWARN Application_.

The submitter sees that her initial version of the submitted data (A) has undergone one curation revision (B, derived from A), one standardisation revision (C<sup>s</sup>, derived from B), and has been approved for release to _The WWARN Application_.

#### Use Case: Non-Standard Submission, Curation and Repeated Standardisation ####

A submitter requests gatekeeper review for a new submission with a new data dictionary. The data dictionary is not one of the WWARN standard data dictionaries. A gatekeeper accepts the submission and assigns a curator.

The assigned curator downloads the initial version of the submitted data (A) and makes a revision (B), without changing the structure of the data.

The assigned curator then decides to map the submitted data onto the appropriate standard data dictionary. She downloads her curated revision of the submitted data (B), and creates a new data file conforming to the appropriate standard data dictionary. She then uploads this new data file (C<sup>s</sup>), links it to the version of the submitted data from which it was derived (B), links it to the latest version of the standard data dictionary, and enters a log message describing the transformation she performed.

The assigned curator now believes the data is ready for release to _The WWARN Application_. She reviews her standardised version of the submitted data (C<sup>s</sup>) with respect to the latest version of the WWARN data release criteria, but finds some problems in the standardised data. She realises that the problems are due to the standardisation transformation she applied. She goes back to the curated, non-standard revision of the submitted data (B), and repeats her transformation, correcting the previous errors. She uploads this new standardised revision of the submitted data (D<sup>s</sup>), links it to the version of the submitted data from which it was derived (B), links it to the latest version of the standard data dictionary, and enters a log message describing the transformation she performed.

The assigned curator reviews her corrected standardised revision of the submitted data (D<sup>s</sup>) with respect to the latest version of the appropriate data release criteria, and asserts that the release criteria have been met.

The submitter sees that her initial version of the submitted data (A) has undergone one curation revision (B, derived from A), one standardisation revision (C<sup>s</sup>, derived from B), a second standardisation revision (D<sup>s</sup>, derived from B), and has been approved for release to _The WWARN Application_.

#### Use Case: Non-Standard Submission, Curation After Standardisation (1) ####

A submitter requests gatekeeper review for a new submission with a new data dictionary. The data dictionary is not one of the WWARN standard data dictionaries. A gatekeeper accepts the submission and assigns a curator.

The assigned curator downloads the initial version of the submitted data (A), reviews it, and decides that there are some problems, but that they will be easier to deal with after the submission has been standardised. She creates a new data file (B<sup>s</sup>) conforming to the appropriate standard data dictionary, uploads it, links it to the version of the submitted data from which it was derived (A), links it to the latest version of the standard data dictionary, and enters a log message describing the transformation she performed.

The assigned curator then creates a new revision (C<sup>s</sup>), derived from her standardised revision (B<sup>s</sup>), fixing the problems she identified earlier. She approves this new version (C<sup>s</sup>) for release.

The submitter sees that her initial version of the submitted data (A) has undergone one standardisation revision (B<sup>s</sup>, derived from A), one curation revision (C<sup>s</sup>, derived from B<sup>s</sup>), and has been approved for release to _The WWARN Application_.

#### Use Case: Non-Standard Submission, Curation After Standardisation (2) ####

A submitter requests gatekeeper review for a new submission with a new data dictionary. The data dictionary is not one of the WWARN standard data dictionaries. A gatekeeper accepts the submission and assigns a curator.

The assigned curator downloads the initial version of the submitted data (A), reviews it, and decides to map the file onto the appropriate standard data dictionary. She creates a new data file (B<sup>s</sup>), uploads it, links it to the version of the submitted data from which it was derived (A), links it to the latest version of the standard data dictionary, and enters a log message describing the transformation she performed.

The assigned curator then reviews her standardised revision (B<sup>s</sup>) and finds some problems. She decides that the problems are best fixed by going back to the initial, non-standard version of the submitted data (A). She creates a new, non-standard revision of the submitted data (C), uploads it, links it to the version of the data from which it was derived (A), links it to the appropriate version of the non-standard data dictionary, and enters a log message.

The assigned curator then maps the new non-standard revision of the submitted data (C) onto the appropriate standard data dictionary. She creates a new data file (D<sup>s</sup>), uploads it, links it to the version of the submitted data from which it was derived (C), links it to the latest version of the standard data dictionary, and enters a log message describing the transformation she performed.

The assigned curator then approves her latest version of the standardised data (D<sup>s</sup>) for release.

The submitter sees that her initial version of the submitted data (A) has undergone one standardisation revision (B<sup>s</sup>, derived from A), one curation revision (C, derived from A), a second standardisation revision (D<sup>s</sup>, derived from C), and has been approved for release to _The WWARN Application_.

#### Use Case: Curator Delegation ####

A submitter requests gatekeeper review for a new submission with a new data dictionary. The data dictionary is not one of the WWARN standard data dictionaries. A gatekeeper accepts the submission and assigns a curator.

The assigned curator delegates the initial review of the submitted data to another curator who specialises in quality aspects of that type of data. The delegate curator downloads the initial version of the submitted data (A), reviews it, decides that some rows do not meet quality control criteria, removes them, and uploads a revised version of the submitted data (B), notifying the assigned curator that their task is complete.

The assigned curator then delegates the mapping of the data onto a standard data dictionary to a different curator, who specialises in transforming data and data standards. The delegate curator downloads the revised version of the submitted data (B), transforms them to conform to the latest version of the appropriate standard data dictionary, and uploads the transformed data (C<sup>s</sup>), notifying the assigned curator that their task is complete.

The assigned curator then delegates the final review of the submission with respect to the appropriate release criteria to third delegate curator. The delegate approves the standardised version of the submitted data (C<sup>s</sup>) for release.

#### Use Case: Coordinator Oversight of Curation and Standardisation Activities ####

A coordinator reviews a list of all submissions for a given module that have been accepted by a gatekeeper, but have not yet been approved for release. She sees which of these have data linked to a standard data dictionary, and which do not. From those that do not, she selects one from the list to investigate in more detail.

For the selected submission, the coordinator reviews a log of all curation and standardisation activities for that submission. She sees that several curation revisions have been made to the submitted data, but that no standardised revisions have yet been made. She contacts the assigned curator to ask for more information.

#### Use Case: Curator Oversight of Curation & Standardisation Activities ####

A curator views a list of all submissions for which she is the assigned curator, and that have not yet been approved for release. From these, she then views those that have not yet been mapped onto a standard data dictionary. She selects a submission from this list, and begins working on it.


---

### Data Curation & Standardisation Functional Requirements ###

#### (Curator) Creating a Standard Data Dictionary ####

  * A curator will be able to create a new standard data dictionary. This involves providing a title and textual summary for the new standard data dictionary, linking the dictionary to at least one module, and uploading a file containing the initial version of the data dictionary.

#### (Curator) Revising a Standard Data Dictionary ####

  * A curator will be able to upload a file containing a revised version of a standard data dictionary.

  * A curator will be able to view a version history for any standard data dictionary.

#### (Curator) Downloading a Standard Data Dictionary ####

  * A curator will be able to download any revision of a standard data dictionary.

#### (Curator) Creating a Specification of Release Criteria ####

  * A curator will be able to create a new specification of release criteria. This involves providing a title and textual summary for the new specification, linking the specification to exactly one module, and uploading a file containing the initial version of the specification.

N.B. there can be only one specification of release criteria per module.

#### (Curator) Revising a Specification of Release Criteria ####

  * A curator will be able to upload a file containing a revised version of a specification of release criteria.

  * A curator will be able to view a version history of any specification of release criteria.

#### (Curator) Downloading a Specification of Release Criteria ####

  * A curator will be able to download any revision of a specification of release criteria.

#### (Curator) Downloading Submitted Data ####

  * A curator will be able to download any version of the submitted data for any submission they are assigned to or have been delegated to work on.

#### (Curator) Revising Submitted Data ####

  * A curator will be able to upload a data file containing a new version of the submitted data, for any submission to which they have been assigned or delegated to work on. The curator must explicitly select which previous version of the submitted data they started work on, i.e., which previous version their new revision is most directly derived from. The curator will also be able to enter a log message describing the changes made.

#### (Curator) Linking Submitted Data With a Data Dictionary ####

  * A curator will be able to link a specific version of the submitted data to a specific version of a data dictionary (either standard or non-standard), for any submission to which they have been assigned or delegated to work.

  * A curator will be able to change the data dictionary link for any submission they are assigned to or delegated to work on.

  * A curator will be able to view a history of changes made to the data dictionary link, for any submission they are assigned to or delegated to work on.

#### (Curator) Viewing Submitted Data Version History ####

  * A curator will be able to view a complete derivation tree of the submitted data, for any submission to which they have been assigned or delegated to work on. It will be clear which versions are linked to a standard data dictionary, and which are not.

#### (Curator) Reviewing Data for Release ####

  * A curator will be able to review submitted data w.r.t. some version of a specification of release criteria, and assert that the data either do or do not meet the specified criteria, for any submission to which they have been assigned.

#### (Curator) Delegation ####

  * An assigned curator will be able to delegate tasks to other curators, for any submission to which they have been assigned.

  * A curator will be able to indicate completion of a delegated tasks.

#### (Curator) Overview of Curation Activity ####

  * A curator will be able to view a list of all submissions that have been accepted but not yet approved for release, and a list of those that have been approved for release, for any version of release criteria, for any submission to which they are assigned. Those for which a revision is available conforming to a standard data dictionary will be clearly marked.

  * These lists can be grouped by module, study or submitter.

#### (Coordinator) Overview of Curation Activity ####

  * A coordinator will be able to view a list of all submissions that have been accepted but not yet approved for release, and a list of those that have been approved for release, for any version of release criteria. Those submissions for which a revision of the submitted data is available conforming to a standard data dictionary will be clearly marked.

  * These lists can be grouped by module, study or submitter.


---

## Candidate Requirements & Open Issues ##

  * Do curators need to be able to create non-standard data dictionaries, in addition to standard data dictionaries?

  * Do we need an additional role, e.g. "analyst", which has read-only permissions for submissions under curation, so e.g., can download data, review it, carry out analyses on it, but cannot make revisions?

  * Does a coordinator need the ability to re-assign the curator for a given submission?

  * Does a gatekeeper need the ability to re-assign the curator for a given submission?

  * Do we need some formal way for a curator to "pass the ball" back to a submitter for a given submission, so it is clear that the submission is awaiting some action by the submitter? (N.B. this is similar in principle to curators delegating tasks.)

  * Should we talk about "approved" data dictionaries, rather than "standard" data dictionaries? Would this be any clearer?

  * Do we need to provide any support for the social process by which a data dictionary becomes approved/standard, e.g., voting or ratification? Or is it OK to give all curators the ability to create approved/standard data dictionaries, and manage the process socially (outsite the system)?

  * Do submitters need the ability to nominate collaborators on a submission, i.e., other users who have same permissions w.r.t. the submission as its initial creator? Or perhaps, collaborators with limited permissions?

  * Do submitters need ability to make some data dictionaries private, i.e., not visible to anyone but themselves?

  * Do submitters need ability to make some studies private, i.e., not visible to anyone but themselves?

  * N.B. by the model above, acceptance of a submission by gatekeeper is "sticky", in the sense that once a submission has been accepted by a gatekeeper, it doesn't matter if the submitter uploads revisions of the submitted data, the acceptance still applies, even if the data has changed completely. On the other hand, denial of a submission request is **not** sticky, but applies only to a specific request. Any number of submission requests can be made for any given submission. Is this model appropriate?

  * Should the system force a submitter to fill out some or all of the SSQ prior to making a submission request to a gatekeeper? Or is it OK to manage gatekeeper requirements for study metadata socially?

  * Do we need some sort of scoping mechanism to determine who can edit a data dictionary, e.g., by module or study?

  * Do gatekeepers need the ability to revoke a prior acceptance of a given submission?

  * Who is responsible for managing and operating the export/ingest of data between chassis/wwarn and _The WWARN Application_? What system will implement that functionality?

  * Will export/ingest of data between chassis/wwarn and _The WWARN Application_ be push or pull?

  * Do submitters need the ability to pre-populate some fields of a study site questionnaire when creating a new study, based on values entered previously for a different study?

  * Do submitters need the ability to create a batch of new studies, rather than one at a time?

  * What happens if a submitter makes a request to a gatekeeper for a given submission, then uploads a revision of the submitted data, then makes a new request to a gatekeeper, before the first request has been fulfilled? This could be confusing/irritating for a gatekeeper if they find two requests pertaining to the same submission. Do submitters need the ability to cancel a pending request? Should there be some rule whereby a submission may have at most one pending request?

  * Who makes sure that there are no duplicate study entries created? What happens if there are? Do we need some function for merging study entries?

  * Having WWARN module heads as gatekeepers may be inappropriate if some are in the field for long periods of time. Gatekeepers need to be fairly responsive.

  * The spec should mention entering log messages any time a revision is made to submitted data, or to a data dictionary.

  * Should we provide online support (e.g., forms) for creating and editing data dictionaries, in a predefined format, and disallow the upload of arbitrary files for data dictionaries? This is the ultimate goal anyway, so if we did this we would avoid having to manage legacy dictionaries when the new feature is rolled out. On the other hand, will it be possible to fit all possible data dictionaries into a single structure?

  * Logging in, and administration of user accounts and user roles is not mentioned.

  * What are the required & optional study metadata, common across modules? What are the required & optional study metadata, for each module?

  * Is it appropriate for any curators to create and revise specifications of release criteria? Or do we need a new role? What about the social process by which a revision to release criteria is accepted, does the system need to provide any support for that social process, e.g., voting or ratification?

  * Is it appropriate for any curators to review and approve submissions for release? Or do we need a specific role?

  * Can curators download revisions of submitted data that were uploaded by the submitter prior to the revision for which gatekeeper acceptance was given? Or should these earlier revisions be private to the submitter?

  * To what extent does the distinction between a submission, and a request for gatekeeper review of a submission, need to be made visible to a submitter? Can this distinction be hidden in some cases?

  * The asymmetry between gatekeeper acceptance of a request (which then applies to the submission as a whole), and gatekeeper denial of a request (which only applies to the version of the submitted data that was reviewed), is possibly counter-intuitive.

  * Should some submitters gain automatic gatekeeper acceptance? E.g., if they have many previously accepted submissions?

  * Should it be possible for more than one person to have the ball at any one time for a given submission, e.g., where the submitted data needs to be broken into parts and each part processed in parallel? I.e., do we need to allow an assigned curator to delegate more than one task at a time for any given submission?

  * Related to the above point, what about situations where data is split apart, revised, then merged back together? I.e., what about versions of the submitted data that are derived from more than one source? N.B., this implies a derivation graph, rather than a derivation tree.

  * What mechanism should we use to capture the derivation of new revisions to submitted data as they are uploaded? Version keys? Explicitly choosing the derivation source? Explicitly creating branches? Formal checkout mechanism?

  * Encourate not creating multiple subs if revising data (???).

  * How will gatekeepers be assigned to review submissions? By module? What if a submission applies to more than one module? Round-robin? Some modules have precedence over others (rock, paper, scissors)? Random?

  * Is it possible to automatically and reliably extract column labels from a spreadsheet?

  * What about privacy for studies and dictionaries? Should it be possible to grant permissions to see details of a study, or to see a dictionary, but otherwise have them private?

  * Public/private dictionaries? If private, what is sharing mechanism?

  * Need to make clear, each module is expected to define acceptance criteria, release criteria?

  * N.B. access to analysis tools, even if submission is not shared, is necessary for WWARN, although possibly not for release 1.0.

  * Need to explain "module" - submitter won't understand.

  * When building a data dictionary, can we provide predefined units and datatypes?

  * N.B. the concept of validation is wider than just dictionaries... ???

  * What about tools for initial visualisation before release approval?

  * Should we constrain all data dictionaries to a single layout? Or can data dictionaries have any layout & content?

  * By requesting gatekeeper review, also agreeing to share data?

  * Should study titles be automatically constructed from other study metadata fields?

  * Study title public, study SSQ private? Who can link submissions to private study? E.g., you can link submission to private study, study owner sees your request, grants you permission to link submission to their study?

  * What if a submission cuts across modules? Need gatekeeper approval from all? Any?

  * N.B. expect submissions for more than one module.

  * Privacy of dictionaries? If private, how share?

  * Do we need a mechanism for defining groups of users? I.e, can someone define a group, and then say anyone in that group has permission to submit to some study? Or view some data dictionary?

  * What about sites vs. studies? Use sites as primary organisational unit, rather than studies? Cf. clinicaltrial.gov.

  * Should coordinator be able to assign review requests to a gatekeeper?

  * Do we need a separate role for approving submissions for release? Release manager? Quality Assurer? Quality Manager?

  * Lock stuff to capture who is working on what?

  * Which curators can work on which submissions?

  * Can a curator completely pass the buck? I.e., totally change the assigned curator?

  * Only one node per submission can be approved for release w.r.t. one version of release criteria?

  * Accept submission & assign curator at same time?

  * Can curators tidy stuff up (dead branches)?

  * What about if submitter creates something derived from a curator's revision? Lose derivation if simplified.

  * Can we drop use cases where submitter revises prior to requesting gatekeeper review?

  * Should curator manage releaes criteria? Or define some other role?

  * Can you assign yourself as curator for a submission?

  * Does task imply permissions? I.e., is a submission locked to the assigned curator?

  * Can you delete previous file revisions? Can you delete whole submissions?

  * Can you overwrite/clear previous mappings to a standard?

  * What about reassigning a curator? Who can do that? Gatekeeper? Coordinator?

  * What about dropping submissions as an entity, only have requests (for gatekeeper review)?

  * Gatekeeper can see other gatekeeper's requests?

  * What about correlation report functionality? E.g. link clinical data to sample labels?

### Architectural Questions & Issues ###

These don't really live in this document, but are included as a temporary placeholder...

  * Is it possible using AtomPub to decouple a media entry from its associated metadata entry?

  * If we are versioning almost everything, this can be demanding in terms of storage space requirements.

  * How do we support versioning in protocols and underlying implementations?

  * How do we maintain integrity of data links if services are loosely coupled? What about transactions that consist of multiple HTTP requests, how do we cope if one request fails? I.e., what about things like roll-back?

  * How do we implement security constraints for underlying services?

