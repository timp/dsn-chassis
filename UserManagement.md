# Requirements #

WWARN needs site visitors who are potential data submitters to be able to register as a new user (i.e., create a user account), then log in (authenticate) and proceed to the submitter home page and begin submitting data.

The registration process must present as few delays or obstacles to the potential submitter as possible, so as not to put the busy submitter off.

The self-registration and login process must be exclusively WWARN-branded.

The self-registration process will need to capture some information about the user. Exactly what TBD, but will probably include institution/affiliation and contact details.

The registration process should verify the user's email address, so that WWARN has a verified means of contact, and also so that the email address can be used as part of verifying the user's identity (e.g., as a WHO employee).

Once authenticated, the user should only have to sign-in once across any part of the wwarn.org website, which could involve spanning multiple web applications potentially hosted in separate servlet containers on separate servers.

If a user forgets their username/password, there must be a means of recovering either.

The self-registered user will be able to submit data by default, but it must be possible to revoke that right for spam/junk/bogus submitters.

The self-registration system must not be vulnerable to robots.

# Survey of Existing Solutions #

## CGGH Network Database / Webauth ##

The CGGH network database and webauth system supports single-sign-on and management of user roles.

There is existing code to enable user self-registration, which involves the user providing an email address, and then the system sending a temporary password to that email address, which the user can then use to log in once, but must change that password on first log in.

The system also allows the user to record some basic profile information, which includes ... ? It may not be straightforward to extend the system to capture other user information.

There is not (re)captcha support, but adding that shouldn't be too much work.

There is a web user-interface for administrators to add/remove roles for users.

David B reckons setting up a separate clone of the CGGH network user database and webauth system dedicated to WWARN should take less than a week of work. A clone of the system would enable all registration, login pages etc. to be wwarn-branded.

  * self-registration possible? yes
  * verify email address? yes
  * (re)captcha? no
  * single sign-on? yes
  * management of user roles? yes
  * wwarn branding possible? yes
  * manage user profile data? yes
  * customise user profile data? yes, but not trivial
  * integration with spring security? yes
  * username/password recovery? yes
  * open-source? yes
  * free? yes

Potential vulnerabilities/issues...

The email verification process is potentially insecure as a temporary password is emailed to the user (standard sites will send a generated account confirmation URL).

Any user can trigger a new temporary password to be sent to any other user simply by entering that user's email address. I.e., one user could cause another user's account to become temporarily inactive, if they knew that user's email address.

## OpenSSO ##

https://opensso.dev.java.net/

  * self-registration possible? yes
  * verify email address? no
  * (re)captcha? no
  * single sign-on? yes
  * management of user roles? yes
  * wwarn branding possible? yes
  * manage user profile data? yes
  * customise user profile data? yes
  * integration with spring security? yes
  * username/password recovery? yes
  * open-source? yes
  * free? not sure... (you can download for free, but license terms aren't obvious)

Potential vulnerabilities/issues...

You can enable support for self-registration via the "membership" module, however the email address entered by the user is not verified. Recovery of password is done via setting a security question. Lack of support for email verification seems a killer for OpenSSO.

## Atlassian Crowd ##

http://www.atlassian.com/software/crowd/

  * self-registration possible? no
  * verify email address? not sure
  * (re)captcha? not sure
  * single sign-on? yes
  * management of user roles? yes
  * wwarn branding possible? yes
  * manage user profile data? yes
  * customise user profile data? not sure
  * integration with spring security? yes
  * username/password recovery? not sure
  * open-source? not sure
  * free? no

Lack of support for self-registration rules this out. [It has been discussed](http://jira.atlassian.com/browse/CWD-410?subTaskView=unresolved), but afaik not implemented.

## CoSign ##

http://weblogin.org/

CoSign is a SSO solution similar to webauth, afaik it is designed to integrate with existing user directories, so has no support for self-registration, storing user data etc., out of the box.

## Shibboleth ##

As CoSign, an SSO solution, again no out-of-the-box package to enable user registration etc.

## JanRain OPX:ASP ##

http://www.janrain.com/products/opxasp

  * self-registration possible? yes
  * verify email address? yes
  * (re)captcha? yes
  * single sign-on? yes
  * management of user roles? TODO
  * wwarn branding possible? yes
  * manage user profile data? yes
  * customise user profile data? yes
  * integration with spring security? yes (authentication at least)
  * username/password recovery? yes
  * open-source? no (although key libraries are)
  * free? no

Note that this is a SAAS offering, i.e., a hosted service.

The key question here is whether user roles can be managed, and whether this can be integrated with spring security (or other access control solution). Otherwise, looks great.

The user self-registration process is probably very similar to that provided by [myopenid.com](https://www.myopenid.com/signup), which is hosted by janrain.

## Community ID ##

http://source.keyboard-monkeys.org/projects/show/communityid

  * self-registration possible? yes
  * verify email address? yes
  * (re)captcha? yes
  * single sign-on? yes
  * management of user roles? no
  * wwarn branding possible? yes
  * manage user profile data? yes
  * customise user profile data? not trivial
  * integration with spring security? yes (authentication at least)
  * username/password recovery? yes
  * open-source? yes
  * free? yes

A simple, straightforward system with the necessary self-registration features. However, there is no support for managing custom user roles.

I couldn't get the email sending configuration to work, and there's no documentation on that part of the configuration, but that may just be my lack of experience configuring mail sending on a linux (ubuntu) machine.

Also, when I increased the logging level in the config.php, the initial home page then fails silently with 500. I couldn't figure out how to get any error reporting out of the application.

## OpenASelect ##

http://www.openaselect.org/trac/openaselect/

Not at all obvious how to set up and configure. Have given up for the moment.

## Drupal ##

Can you use drupal's user management features as the central point in a authentication/authorisation/user management setup?

  * self-registration possible? yes
  * verify email address? yes
  * (re)captcha? yes (via captcha module)
  * single sign-on? maybe... (openid? cas? ...)
  * management of user roles? yes
  * wwarn branding possible? yes
  * manage user profile data? TODO (surely)
  * customise user profile data? TODO (surely)
  * integration with spring security? TODO authentication, TODO for roles (probably doable with the right SQL)
  * username/password recovery? yes
  * open-source? yes
  * free? yes

I investigated the use of openid here, using a drupal module that enables your drupal installation to act as an openid provider, then using your drupal openid to log in to java webapps (via spring security configured for authentication using openid). The basic problem here is that you can create a drupal account, and the log in using your username and password, but if you visit a spring protected webapp configured for openid authentication, you have to log in there too. You only have to put in your openid url at the protected webapp, but your logged-in state is specific to that webapp. I.e., you could log out of drupal but still be logged in to the protected webapp. Or you could log out of the webapp but still be logged in to drupal. Not ideal.

Another option might be integration with CAS, ... i.e., use CAS for authentication and SSO, but then use the Drupal user database as the repository of users, groups/roles and passwords... ? This would enable use of drupal as UI for user (self-)registration, and management of user roles, but get single sign-on across drupal and arbitrary java webapps... TODO can this work?

An interesting aside here is that there is a mod\_auth\_mysql module for apache, which enables use of a mysql database as a repository of user and group information. So it might be possible to use apache config directives to limit access to portions of a url space based on group-membership data from a mysql (e.g., drupal) database... ?