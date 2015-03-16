Present: Alistair, Richard, Meera, Lesley, Val



# Introduction #

Alistair gave a [brief introduction to the meeting](http://dsn-chassis.googlecode.com/svn/trunk/wwarn/docs/content/presentations/20090915-wwarn-chassis-workflow.pdf).

The background to this meeting is the workflow specified in section 2 of [draft 6 of the Chassis/WWARN requirements spec](http://dsn-chassis.googlecode.com/svn/tags/wwarn/docs/draft-6/content/requirements/1.0/index.html)

# Illustration of Workflow #

Alistair illustrated the main features of the proposed Chassis workflow (see image below).

![http://dsn-chassis.googlecode.com/svn/trunk/wwarn/docs/content/presentations/20090915-chassis-workflow.png](http://dsn-chassis.googlecode.com/svn/trunk/wwarn/docs/content/presentations/20090915-chassis-workflow.png)

# Discussion #

## Study Permissions ##

Should the study questionnaire be private? Yes. Or perhaps, the questionnaire could have public and private elements?

Studies, co-submitters vs. co-owners. Do we need two levels of permission for a study, i.e., study owners (can edit SQ and link submissions to study) and study submitters (cannot edit SQ but can link submissions to study)?

What about studies being discoverable, is that needed? For 1.0, OK to force study owner to say, up-front, who co-owners are. I.e., can live without discoverable.

How will we ensure we get feedback from users of the system?

Can any study owner make the decision to make a study public? Yes, but in doing so they must confirm they have agreement from other owners.

Studies will often break down on module lines, i.e., clinical person will fill out clinical part of SQ, same for PK, etc.

We had consensus on a simplified model of study permissions for the Chassis/WWARN 1.0 release. The model is that the submitter who creates a study becomes the owner of the study. The study owner can edit the study questionnaire and can link submissions to the study. The study owner can grant study ownership to other submitters. All owners have the same permissions. Any owner can decide to make the study public, however they must confirm that they have agreement from other owners. There is no need for a second level of permissions, where someone can link submissions to the study but cannot edit the study questionnaire. However, Val noted that there could be potential issues if only have this one permission level.

I.e., if someone wants to link their submissions to a study, they must be one of the study owners. If one of the owners runs amok and starts messing up the SQ, the owners have to sort that out socially.

Do study owners also need ability to grant read-only permissions for a study? What about for submissions?

How will we resolve duplicate study records?

## Studies and Submissions ##

OK to have two level model, studies and submissions, with many-to-many relationship.

It's OK to force all submissions to be linked to at least one study.

Situation where one submission is linked to more than one study, maybe argument for fine grained study permissions?

## Submission Permissions ##

Do we need read-only permissions for submissions, in addition to ownership? Maybe useful if read-only person is doing QC for the submitter. This is not high priority, except after the data has been standardised and available to reporting and visualisation tools, then you might want to allow specific people to view.

## Publishing Data (Open Access) ##

We had consensus on the following model of publication...

Study owner first makes study public. Then, for all submissions linked to the study, submission owners can make those submissions public. Making a study public does **not** automatically make all linked submissions public.

It might also be useful to have notifications where a study or submission has been made public, e.g., between study and submission owners.

N.B. one might, in principle, define three different roles w.r.t. a study ... study owner, study submitter, study editor. There also three different permissions ... (a) permission to edit SQ, (b) permission to link submissions, (c) permission to make study public. owner has (a,b,c), editor has (c), submitter has (b).

## Study Questionnaire ##

Can submitters bypass the questionnaire? I.e., is it OK to allow submitters to start creating submissions and linking to a study, even if no SQ has been filled in for the study? Yes.

If yes, will help to define SQ as we learn more about the info needed.

Harder to start loose then tighten regulations.

Maybe give gentle reminders to complete SQ. A good place for the reminder might be at the end, just before release.

Important to set expectations and boundaries, be as prescriptive as possible, to preserve wwarn's reputation.

Acceptance criteria are a good idea, good to make explicit up front to help set expectations, also good for gatekeeper review.

## Revising Submissions ##

Yes, good to be able to revise data before and after gk review.

## Submitted Data ##

Yes, ok to support data in any format, with no support for previewing data.

## Validation & Transformation Off-Line ##

Yes, ok to have no on-line support for validation and transformation, at least for 1.0.

## Dictionary Permissions ##

Yes, make dictionaries private by default, owners can grant ownership.

## Data Dictionaries ##

Should data dictionaries be a hard requirements, i.e., required to unlock ability to request gk review? No.

Modules should be able to decide whether to require dictionaries, e.g., as part of acceptance criteria.

May also be ok to have partial dictionaries in some cases.

Ok to manage dictionaries as arbitrary files, although wwarn should provide a template and set expectations. (Means we have to extract stats on field name usage etc. manually, at least for 1.0.)

## Gatekeeper Review ##

We had consensus that wwarn should have a single pool of gatekeepers with explicit acceptance criteria, not module-specific. I.e., universal acceptance criteria, across modules.

If triage model, what if a submission takes ages to curate? Need to set expectations.

Important to have transparency, explain not ready for curation yet because ...

Val said, need to prioritise current data, because wwarn is about early warning.

Meera said, yes but for now, prioritise building a database. So maybe different priorities, old data are important.

Clinical gk has priority, because clinical data has the outcome.

Wwarn modules should agree acceptance & prioritisation criteria for release 1.0.

## Curation & Derivation ##

Ok to do off-line curation, upload each revision.

## Release Criteria ##

Finer grained? OK to have coarse grained release criteria for 1.0.

Maybe tack fine-grained quality data (evaluation) onto SQ?

## Curator Collaboration ##

What about splitting a submission, each curator works on their bit in parallel, then merge back together?

Can gk assign more than one curator to a submission?

Working in series versus working in parallel? Start with series, passing the ball. Consider parallel later.

## Other Discussion ##

What about registering prior to submission? Need to build up database of contacts. Expression of interest?

Coordinator role is needed. Also, should capture information on how long things take, so can get management reporting, reporting on curation workload, etc.

What is user journey up to point of submission?

The gk begins the interaction with the submitter.