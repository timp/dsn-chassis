**This page is deprecated and replaced by MantaSecurityModel. This page is maintained for historical value and reference.**


---

# Introduction #

As detailed in [Issue 61](http://code.google.com/p/dsn-chassis/issues/detail/id60)
the WWARN system must be secured so that only allowed data accesses are possible.

This document describes in natural language the allowed data accesses and
methods of implementing the required restrictions.



The access controls are of three types, which can be illustrated as stories:
  * Role based access to entities: Submitters cannot review submissions
  * Content Type restrictions : Only meta data may be updated, no one may modify an uploaded file
  * Field matching restrictions: Submitters may only alter the studies they have submitted

# Role based access #

Access control is composed of two elements: authentication and authorisation.
Authentication determines the identity of the user, authorisation determines what the user may do.

For safety and ease of comprehension actions must be explicitly allowed, otherwise they are disallowed.

## User Roles ##

| **Role** | **Capabilities** | **Description** |
|:---------|:-----------------|:----------------|
| Surfer | None | Anonymous web user |
| Submitter | Creation of data, upload of files | Default role for registered users|
| Anonymizer | Download of files, Upload of transformed files |A specialist Curator who reduces identifying data|
| Curator | Download of files, Upload of transformed files  | Allowed to modify all files|
| Administrator | Deletion of files, data and users | Super user |

<br />


## Access Operations ##


The Data Operations we are concerned with are
<font color='green'>Read</font>, <font color='red'>Create</font>, <font color='red'>Update</font> or <font color='red'>Delete</font> on individual, specified,
entities (collection members).

In addition a user may <font color='green'>List</font> a collection or selection from it.

| | Surfer | Submitter | Anonymizer | Curator | Administrator |
|:|:-------|:----------|:-----------|:--------|:--------------|
| Study | <font color='blue'>Null</font> | <font color='green'>RL</font><font color='red'>CU</font> |<font color='green'>RL</font> |<font color='green'>RL</font>| <font color='green'>RL</font><font color='red'>UD</font> |
| Submission | <font color='blue'>Null</font> | <font color='green'>RL</font><font color='red'>C</font> |<font color='green'>RL</font> |<font color='green'>RL</font>| <font color='green'>RL</font><font color='red'>UD</font> |
| Review | <font color='blue'>Null</font> | <font color='blue'>Null</font> |<font color='green'>RL</font><font color='red'>C</font> |<font color='green'>RL</font><font color='red'>C</font>| <font color='green'>RL</font><font color='red'>UD</font> |
| Derivation | <font color='blue'>Null</font> | <font color='blue'>Null</font> |<font color='green'>RL</font><font color='red'>C</font> |<font color='green'>RL</font><font color='red'>C</font>| <font color='green'>RL</font><font color='red'>UD</font> |
| Media | <font color='blue'>Null</font> | <font color='green'>RL</font><font color='red'>C</font> |<font color='green'>RL</font><font color='red'>C</font> |<font color='green'>RL</font><font color='red'>C</font>| <font color='green'>RL</font><font color='red'>UD</font> |


This space is the Cartesian Product of the three lists: Entities, Roles and Operations.
As formulated this gives a 5 x 5 x 5 cube: containing 255 nodes.
To bring this space down we can ignore the Surfer. We could note that the Anonymizer and Curator roles have exactly the same accesses, however they might diverge at a later date.
It is a feature of the Atom specification that an entity and feed url cannot be distinguished,
So we are forced to treat treat the Read Entity and List Collection operations as one operation. This reduces our cube to 4 x 4 x 5 : 80 nodes to consider.






<br />
# Content Type Restrictions #

We need to ensure that uploaded files cannot be modified.
The process of Curation produces a Derivation.
Derivations take as input one or more uploaded files and output one or more uploaded files.
If a single file is uploaded twice it will still be known as two separate files within Chassis.

The underlying data store (eXist) distinguishes between files which it is concerned with by the file's type: it is only interested in files with a content type of <tt>application/atom+xml</tt> (see [2 ](http://exist.sourceforge.net/atompub.html))

The content type of an Atom PUT request can be determined either from the request content-type property or as a hack by matching the url pattern.

We only allow PUT requests with Content-Type equal to <tt>application/atom+xml</tt>.



# Field Matching Restrictions #

The only field level restriction that we currently require is the ability to ensure that the current authenticated user is a member of the author list for the entity.




# Abstract Access Rules #

We can cast the rules given in the table above into a format which defines the data access operations we allow. We do not need to consider what we disallow, as **everything that is not permitted is forbidden**.
These rules take the form of a triple of Role, Access Operation and collection.
Note that the query and retrieve operations are filtered by whether the entry author list contains the user's id.

  * allow ( submitter, retrieve member, studies )
  * allow ( submitter, query members, studies )
  * allow ( submitter, create member, studies )
  * allow ( submitter, update member, studies )

  * allow ( anonymizer, retrieve member, studies )
  * allow ( anonymizer, query members, studies )

  * allow ( curator, retrieve member, studies )
  * allow ( curator, query members, studies )

  * allow ( administrator, retrieve member, studies )
  * allow ( administrator, query members, studies )
  * allow ( administrator, update member, studies )
  * allow ( administrator, delete member, studies )


  * allow ( submitter, retrieve member, submission )
  * allow ( submitter, query members, submission )
  * allow ( submitter, create member, submission )
  * allow ( submitter, update member, submission )

  * allow ( anonymizer, retrieve member, submission )
  * allow ( anonymizer, query members, submission )

  * allow ( curator, retrieve member, submission )
  * allow ( curator, query members, submission )

  * allow ( administrator, retrieve member, submission )
  * allow ( administrator, query members, submission )
  * allow ( administrator, update member, submission )
  * allow ( administrator, delete member, submission )




  * allow ( anonymizer, retrieve member, review )
  * allow ( anonymizer, query members, review )
  * allow ( anonymizer, create members, review )

  * allow ( curator, retrieve member, review )
  * allow ( curator, query members, review )
  * allow ( curator, create members, review )

  * allow ( administrator, retrieve member, review )
  * allow ( administrator, query members, review )
  * allow ( administrator, update member, review )
  * allow ( administrator, delete member, review )




  * allow ( anonymizer, retrieve member, derivation )
  * allow ( anonymizer, query members, derivation )
  * allow ( anonymizer, create members, derivation )

  * allow ( curator, retrieve member, derivation )
  * allow ( curator, query members, derivation )
  * allow ( curator, create members, derivation )

  * allow ( administrator, retrieve member, derivation )
  * allow ( administrator, query members, derivation )
  * allow ( administrator, update member, derivation )
  * allow ( administrator, delete member, derivation )



  * allow ( submitter, retrieve member, Media )
  * allow ( submitter, query members, Media )
  * allow ( submitter, create member, Media )

  * allow ( anonymizer, retrieve member, Media )
  * allow ( anonymizer, query members, Media )

  * allow ( curator, retrieve member, Media )
  * allow ( curator, query members, Media )

  * allow ( administrator, retrieve member, Media )
  * allow ( administrator, query members, Media )
  * allow ( administrator, update member, Media )
  * allow ( administrator, delete member, Media )



# Concrete Access Rules #

The rules above can be translated into concrete Http methods and requests.

  * allow ( submitter, GET,  /atom/edit/studies/id )
  * allow ( submitter, GET,  /submitter/query/studies )
  * allow ( submitter, POST, /atom/edit/studies )
  * allow ( submitter, PUT,  /atom/edit/studies/id )

  * allow ( anonymizer, GET, /atom/edit/studies/id )
  * allow ( anonymizer, GET, /anonymizer/query/studies )

  * allow ( curator, GET, /atom/edit/studies/id )
  * allow ( curator, GET, /curator/query/studies )

  * allow ( administrator, GET,    /administartor/query/studies )
  * allow ( administrator, GET,    /atom/edit/studies/id )
  * allow ( administrator, PUT,    /atom/edit/studies/id )
  * allow ( administrator, DELETE, /atom/edit/studies/id )


  * allow ( submitter, POST, /atom/edit/submissions )
  * allow ( submitter, GET,  /submitter/query/submissions )
  * allow ( submitter, GET,  /atom/edit/submissions/id )
  * allow ( submitter, PUT,  /atom/edit/submissions/id )

  * allow ( anonymizer, GET, /anonymizer/query/submissions )
  * allow ( anonymizer, GET, /atom/edit/submissions/id )

  * allow ( curator, GET, /anonymizer/query/submissions )
  * allow ( curator, GET, /atom/edit/submissions/id )

  * allow ( administrator, GET,    /administrator/query/submissions )
  * allow ( administrator, GET,    /atom/edit/submissions/id )
  * allow ( administrator, PUT,    /atom/edit/submissions/id )
  * allow ( administrator, DELETE, /atom/edit/submissions/id )




  * allow ( anonymizer, GET,  /atom/edit/reviews/id )
  * allow ( anonymizer, GET,  /anonymizer/query/reviews )
  * allow ( anonymizer, POST, /atom/edit/reviews )

  * allow ( curator, GET,  /atom/edit/reviews/id )
  * allow ( curator, GET,  /curator/query/reviews )
  * allow ( curator, POST, /atom/edit/reviews )

  * allow ( administrator, GET,    /atom/edit/reviews/id )
  * allow ( administrator, GET,    /administrator/query/reviews )
  * allow ( administrator, PUT,    /atom/edit/reviews/id )
  * allow ( administrator, DELETE, /atom/edit/reviews/id )




  * allow ( anonymizer, GET,  /atom/edit/derivations/id )
  * allow ( anonymizer, GET,  /anonymiser/query/derivations )
  * allow ( anonymizer, POST, /atom/edit/derivations )

  * allow ( curator, GET,  /atom/edit/derivations/id )
  * allow ( curator, GET,  /curator/query/derivations )

  * allow ( administrator, GET,    /atom/edit/derivations/id )
  * allow ( administrator, GET,    /administrator/query/derivations )
  * allow ( administrator, PUT,    /atom/edit/derivations/id )
  * allow ( administrator, DELETE, /atom/edit/derivations/id )



  * allow ( submitter, GET,  /atom/edit/media/id )
  * allow ( submitter, GET,  /submitter/query/media )
  * allow ( submitter, POST, /atom/edit/media )

  * allow ( anonymizer, GET, /atom/edit/media/id )
  * allow ( anonymizer, GET, /anonymizer/query/media )

  * allow ( curator, GET, /atom/edit/media/id )
  * allow ( curator, GET, /curator/query/media )

  * allow ( administrator, GET,    /atom/edit/media/id )
  * allow ( administrator, GET,    /administrator/query/media )
  * allow ( administrator, PUT, /atom/edit/media/id )
  * allow ( administrator, DELETE, /atom/edit/media/id )



# Implementation Strategies #

The rules above are of the following shapes

  * allow( role, GET, atom entity url)
  * allow( role, GET, atom collection url)
  * allow( role, POST, atom collection url)
  * allow( role, PUT, atom entity url)
  * allow( role, DELETE, atom entity url)


  * allow( role, GET, query entity url)
  * allow( role, GET, query collection url)

which can all be accomodated with the standard spring security declarations
<pre>
<intercept-url<br>
pattern="/atom/edit/studies"<br>
method="POST"<br>
access="ROLE_SUBMITTER"/ ><br>
</pre>

Note that there is no need to further protect the query urls as the
value of the current user is included in the query:

  * allow ( GET, /submitter/query/studies, (submitter) )
  * allow ( GET, /anonymizer/query/studies, (anonymizer) )
  * allow ( GET, /curator/query/studies, (curator) )
  * allow ( GET, /administrator/query/studies, (administrator) )

  * allow ( GET, /submitter/query/submissions, (submitter) )
  * allow ( GET, /anonymizer/query/submissions, (anonymizer) )
  * allow ( GET, /anonymizer/query/submissions, (curator) )
  * allow ( GET, /administrator/query/submissions, (administrator) )

  * allow ( GET, /anonymizer/query/reviews, (anonymizer))
  * allow ( GET, /curator/query/reviews, (curator) )
  * allow ( GET, /administrator/query/reviews (administrator) )

  * allow ( GET, /anonymiser/query/derivations, (anonymizer) )
  * allow ( GET, /curator/query/derivations, (curator) )
  * allow ( GET, /administrator/query/derivations, (administrator) )

  * allow ( GET, /submitter/query/media, (submitter) )
  * allow ( GET, /anonymizer/query/media, (anonymizer) )
  * allow ( GET, /curator/query/media, (curator) )
  * allow ( GET, /administrator/query/media, (administrator) )


There is no effective method of distinguishing an atom feed url from an entry url.
Sorting by url gives us:

  * allow ( /atom/edit/studies, POST, submitter )
  * allow ( /atom/edit/studies, GET,  submitter )
  * allow ( /atom/edit/studies, GET,  anonymizer )
  * allow ( /atom/edit/studies, GET,  curator )
  * allow ( /atom/edit/studies, GET,  administrator )
  * allow ( /atom/edit/studies, PUT,  submitter )
  * allow ( /atom/edit/studies, PUT, administrator )
  * allow ( /atom/edit/studies, DELETE, administrator )

  * allow ( /atom/edit/submissions, POST, submitter )
  * allow ( /atom/edit/submissions, GET, submitter )
  * allow ( /atom/edit/submissions, GET, anonymizer )
  * allow ( /atom/edit/submissions, GET, curator )
  * allow ( /atom/edit/submissions, GET, administrator,  )
  * allow ( /atom/edit/submissions, PUT, submitter )
  * allow ( /atom/edit/submissions, PUT, administrator )
  * allow ( /atom/edit/submissions, DELETE, administrator )

  * allow ( /atom/edit/reviews, POST, anonymizer )
  * allow ( /atom/edit/reviews, POST, curator )
  * allow ( /atom/edit/reviews, GET, anonymizer )
  * allow ( /atom/edit/reviews, GET, curator )
  * allow ( /atom/edit/reviews, GET, administrator )
  * allow ( /atom/edit/reviews, PUT, anonymizer )
  * allow ( /atom/edit/reviews, PUT, curator )
  * allow ( /atom/edit/reviews, PUT, administrator )
  * allow ( /atom/edit/reviews, DELETE, administrator )

  * allow ( /atom/edit/derivations, POST, curator )
  * allow ( /atom/edit/derivations, POST, anonymizer )
  * allow ( /atom/edit/derivations, GET, anonymizer )
  * allow ( /atom/edit/derivations, GET, curator )
  * allow ( /atom/edit/derivations, GET, administrator )
  * allow ( /atom/edit/derivations, PUT, anonymizer )
  * allow ( /atom/edit/derivations, PUT, curator )
  * allow ( /atom/edit/derivations, PUT, administrator )
  * allow ( /atom/edit/derivations, DELETE, administrator )

  * allow ( /atom/edit/media, POST, submitter )
  * allow ( /atom/edit/media, POST, anonymizer )
  * allow ( /atom/edit/media, POST, curator )
  * allow ( /atom/edit/media, GET, submitter )
  * allow ( /atom/edit/media, GET, anonymizer )
  * allow ( /atom/edit/media, GET, curator )
  * allow ( /atom/edit/media, GET, administrator )
  * allow ( /atom/edit/media, PUT, submitter )
  * allow ( /atom/edit/media, PUT, anonymizer )
  * allow ( /atom/edit/media, PUT, curator )
  * allow ( /atom/edit/media, PUT, administrator )
  * allow ( /atom/edit/media, DELETE, administrator )

The access attribute in the Spring configuration file is a list of roles so we can cast the
remaining rules into the same form as the Spring configuration:

  * allow ( /atom/edit/studies, POST, ( submitter)
  * allow ( /atom/edit/studies, GET, ( submitter, anonymizer, curator, administrator ) )
  * allow ( /atom/edit/studies, PUT, ( submitter, administrator ) )
  * allow ( /atom/edit/studies, DELETE, (administrator) )

  * allow ( /atom/edit/submissions, POST, ( submitter) )
  * allow ( /atom/edit/submissions, GET, ( submitter, anonymizer, curator, administrator)  )
  * allow ( /atom/edit/submissions, PUT, ( submitter, administrator ) )
  * allow ( /atom/edit/submissions, DELETE, ( administrator ) )

  * allow ( /atom/edit/reviews, POST, (anonymizer, curator) )
  * allow ( /atom/edit/reviews, GET, (anonymizer, curator, administrator ) )
  * allow ( /atom/edit/reviews, PUT, (anonymizer, curator, administrator ) )
  * allow ( /atom/edit/reviews, DELETE, ( administrator ) )

  * allow ( /atom/edit/derivations, POST, ( curator, anonymizer ) )
  * allow ( /atom/edit/derivations, GET, (anonymizer, curator, administrator ) )
  * allow ( /atom/edit/derivations, PUT, (anonymizer, curator, administrator ) )
  * allow ( /atom/edit/derivations, DELETE, administrator )

  * allow ( /atom/edit/media, POST, ( submitter, anonymizer, curator ) )
  * allow ( /atom/edit/media, GET, (submitter, anonymizer, curator, administrator ) )
  * allow ( /atom/edit/media, PUT, (submitter, anonymizer, curator, administrator ) )
  * allow ( /atom/edit/media, DELETE, (administrator) )


## Allowing update of atom only ##

```
public void doHttpFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

  if (request.getMethod().equals("PUT"))
    if (!request.getContentType().equals("application/atom+xml"))
      response.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, 
                         "Only Atom entries may be updated");
}
```

## References ##
  1. [Spring authorization without authentication](http://stackoverflow.com/questions/1322135/spring-security-authorization-without-authentication)
  1. [Exist: Atom Protocol Support](http://exist.sourceforge.net/atompub.html)



# Appendix #
## Chassis Atom Entries = ##

All Chassis Atom Entries have the following fields:
Id, Title, Summary, Author list, Date published, Date last updated.

| **Name** | **Additional Fields** | **Links**|
|:---------|:----------------------|:---------|
| Study | Module | Edit |
| Submission  | <font color='blue'>None</font> | Study, Media+ |
| Review | Type, Outcome | Media+ (in)|
| Derivation | <font color='blue'>None</font> |  Media+ (in), Media+ (out)|
| Media  | <font color='blue'>None</font> | Edit, Content.src |

<br />


# Chassis Collection Query Filters #

All of the above Entries are aggregated into Collections (Atom Feeds).
The raw collections can only be accessed by **Administrators**.

All other access to the collections is through queries which
are filtered by the current user and her roles.

<br />