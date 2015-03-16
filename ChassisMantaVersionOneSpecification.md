

# Introduction #

This document is a specification of the first production version of the web-based system for contribution (submission) of data to WWARN, codename Chassis Manta Version 1.0.

Note that this version is supposed to be a minimal first version for development and release as soon as possible.

You can click on any of the images below to navigate to a larger version of the image.


---

# Roles #

  * Contributor - can register studies and submit files
  * Personal Data Reviewer - checks all submitted files for personal data that should be removed
  * Personal Data Remover - as personal data reviewer, but can also upload files that are derived from submitted files but with personal data removed
  * Curator - works on submitted data


---

# Required Functionality (**Must** Be Implemented) #

## First-Time Contributor ##

**_A contributor comes to the WWARN website and contributes some data for the first time._**

The user first comes to the wwarn.org home page.

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598897630/' title='IMAG0022 by londonbonsaipurple, on Flickr'><img src='http://farm2.static.flickr.com/1049/4598897630_48912ce01b.jpg' alt='IMAG0022' width='500' height='299' /></a>

On the home page is a link to the "contribute" section of the web site. The user navigates to the "contribute" page of the web site.

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598279859/' title='IMAG0023 by londonbonsaipurple, on Flickr'><img src='http://farm2.static.flickr.com/1061/4598279859_f9403def64.jpg' alt='IMAG0023' width='500' height='299' /></a>

On this page, amongst other general information about contributing to WWARN, is a link to the user registration page. The user navigates to the user registration page of the web site.

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598280915/' title='IMAG0025 by londonbonsaipurple, on Flickr'><img src='http://farm4.static.flickr.com/3334/4598280915_be20c64462.jpg' alt='IMAG0025' width='500' height='299' /></a>

Once they have filled in the user registration form and clicked "confirm", an email is sent to them containing a temporary password and a link to the login page. The user navigates to the login page and logs in.

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598898654/' title='IMAG0024 by londonbonsaipurple, on Flickr'><img src='http://farm2.static.flickr.com/1184/4598898654_1dd9ba167d.jpg' alt='IMAG0024' width='500' height='299' /></a>

Note that all new users will automatically be given the _contributor_ role, i.e., once the user has registered, they can immediately begin contributing data.

Once the user is logged in, they navigate to their contributor home page, which is part of Chassis. The page contains a link to register a new study, and list of the user's registered studies, which in this case will be empty.

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598899534/' title='IMAG0026 by londonbonsaipurple, on Flickr'><img src='http://farm5.static.flickr.com/4051/4598899534_8891692438.jpg' alt='IMAG0026' width='500' height='299' /></a>

The user clicks the "register study" link to begin the study registration wizard.

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598281599/' title='IMAG0027 by londonbonsaipurple, on Flickr'><img src='http://farm5.static.flickr.com/4025/4598281599_68f5bfe5eb.jpg' alt='IMAG0027' width='500' height='299' /></a>

The user enters a title for the study, reads the terms of submission, clicks accept, then clicks "next".

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598281975/' title='IMAG0028 by londonbonsaipurple, on Flickr'><img src='http://farm2.static.flickr.com/1071/4598281975_6a4bcca952.jpg' alt='IMAG0028' width='500' height='299' /></a>

The user enters the email address of anyone else who they want to be able to access the study and modify it or submit new files, then clicks "next".

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598282391/' title='IMAG0029 by londonbonsaipurple, on Flickr'><img src='http://farm5.static.flickr.com/4064/4598282391_4eef2c79bf.jpg' alt='IMAG0029' width='500' height='299' /></a>

The user enters details of the people who should be acknowledged for the study, and clicks "next".

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598282747/' title='IMAG0030 by londonbonsaipurple, on Flickr'><img src='http://farm2.static.flickr.com/1319/4598282747_ba7d2d8c07.jpg' alt='IMAG0030' width='500' height='299' /></a>

The user uploads files they want to submit, and clicks "next".

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598901534/' title='IMAG0031 by londonbonsaipurple, on Flickr'><img src='http://farm2.static.flickr.com/1249/4598901534_790438f3de.jpg' alt='IMAG0031' width='500' height='299' /></a>

The user selects whether the study is published or not. If published, the user enters a web link for each publication, then clicks "next".

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598901994/' title='IMAG0032 by londonbonsaipurple, on Flickr'><img src='http://farm5.static.flickr.com/4017/4598901994_7d5f5b8aab.jpg' alt='IMAG0032' width='500' height='299' /></a>

The user reviews all of the information they have entered in the previous steps. If they are happy with the information, they click "confirm". The study is then permanently registered in the Chassis database, and the user is taken to a confirmation page.

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598284233/' title='IMAG0033 by londonbonsaipurple, on Flickr'><img src='http://farm2.static.flickr.com/1049/4598284233_63404c0d3a.jpg' alt='IMAG0033' width='500' height='299' /></a>

The confirmation page contains the study questionnaire. Here the user can edit the study questionnaire. If the user clicks "save changes" the modifications they have made to the study questionnaire will be saved, and they will remain on the confirmation page to make more modifications if they want.

## Contributor Returns to Submit More Files ##

**_A contributor comes back to the WWARN website to contribute some more files for a study they previously registered._**

The user comes back to the wwarn.org web site home page.

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598284709/' title='IMAG0034 by londonbonsaipurple, on Flickr'><img src='http://farm5.static.flickr.com/4047/4598284709_8accae460e.jpg' alt='IMAG0034' width='500' height='299' /></a>

The web site home page has a direct link to the contributor home page within Chassis. The user clicks this link, but access requires log in.

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598898654/' title='IMAG0024 by londonbonsaipurple, on Flickr'><img src='http://farm2.static.flickr.com/1184/4598898654_1dd9ba167d.jpg' alt='IMAG0024' width='500' height='299' /></a>

Once logged in, the user is taken to the contributor home page, which shows a list of studies previously registered.

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598285509/' title='IMAG0036 by londonbonsaipurple, on Flickr'><img src='http://farm2.static.flickr.com/1183/4598285509_f8af5bd83b.jpg' alt='IMAG0036' width='500' height='299' /></a>

The user clicks on the study they want to submit more files for. The user is taken to the "study dashboard" for that study.

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598286043/' title='IMAG0037 by londonbonsaipurple, on Flickr'><img src='http://farm4.static.flickr.com/3394/4598286043_3d34c0d50e_b.jpg' alt='IMAG0037' width='1024' height='613' /></a>

The study dashboard has several tabs: summary, permissions, acknowledgments, files, publications, and study info.

The user clicks on the "files" tab.

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598286581/' title='IMAG0038 by londonbonsaipurple, on Flickr'><img src='http://farm2.static.flickr.com/1417/4598286581_08b4abf056_b.jpg' alt='IMAG0038' width='1024' height='613' /></a>

The "files" tab has a form for submitting more files. Below that is a list of files already submitted. The user selects a file to upload in the form, then clicks "confirm". The file is then uploaded, and the page is refreshed, to show the newly submitted file in the list of submitted files.

## Contributor Returns to Modify Study Info, Permissions, Acknowledgments, or Publications ##

**_A contributor comes back to the WWARN website to modify the study questionnaire, permissions, acknowledgments or publications for a study they previously registered._**

As above, but the user clicks on one of the other tabs in the study dashboard, e.g., "study info".

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598287155/' title='IMAG0039 by londonbonsaipurple, on Flickr'><img src='http://farm5.static.flickr.com/4033/4598287155_b3f384d87a_b.jpg' alt='IMAG0039' width='1024' height='613' /></a>

The study info tab contains the study questionnaire. The user can edit the study questionnaire, then click "save changes", which will save their changes in the Chassis database, and will leave the user on the same page to continue editing.

Similarly, the user could click on the "permissions" tab, make changes, and save them.

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598905874/' title='IMAG0040 by londonbonsaipurple, on Flickr'><img src='http://farm4.static.flickr.com/3299/4598905874_a17b279b57_b.jpg' alt='IMAG0040' width='1024' height='613' /></a>

The same applies to the acknowledgments and publications tabs.

## Contributor Loses Internet Connection ##

**_A contributor loses internet connection part-way through registering a new study._**

A contributor begins the study registration process, and gets to step 5.

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598901534/' title='IMAG0031 by londonbonsaipurple, on Flickr'><img src='http://farm2.static.flickr.com/1249/4598901534_790438f3de.jpg' alt='IMAG0031' width='500' height='299' /></a>

While the user is filling out the form, their internet connection fails. The user then clicks "next", but nothing happens, because the connection is down.

When the connection returns, the user visits the contributor home page.

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598288001/' title='IMAG0041 by londonbonsaipurple, on Flickr'><img src='http://farm2.static.flickr.com/1167/4598288001_ef63aab8f0_b.jpg' alt='IMAG0041' width='1024' height='613' /></a>

On the home page will be a list of "unfinished drafts" which were automatically saved while the user was filling out the wizard.

The user clicks on the draft they were working on when their connection failed, and is returned to step 5 in the wizard, with all previous steps containing the information they entered before the connection failed.

Note that a draft will be automatically saved each time the user clicks "next" or "previous". So in this case, the user will not have lost information from steps 1-4, but will have lost information from step 5.

## Contributor Breaks During Study Registration ##

**_A contributor decides to break off part-way through registering a new study and resume later._**

A contributor begins registering a new study, and gets to step 3.

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598282391/' title='IMAG0029 by londonbonsaipurple, on Flickr'><img src='http://farm5.static.flickr.com/4064/4598282391_4eef2c79bf_b.jpg' alt='IMAG0029' width='1024' height='613' /></a>

On this page (and on all other steps in the wizard), below the "next" and "previous" buttons, is a "save as draft" button. The user clicks this button, then shuts down their computer to go and do some other work.

The user returns later to finish the study registration process. The user goes to the contributor home page.

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598288001/' title='IMAG0041 by londonbonsaipurple, on Flickr'><img src='http://farm2.static.flickr.com/1167/4598288001_ef63aab8f0_b.jpg' alt='IMAG0041' width='1024' height='613' /></a>

On the home page will be a list of "unfinished drafts" which were saved when the user clicked the "save as draft" button.

The user clicks on the draft they were working on previously, and is returned to step 3 in the wizard, with all steps containing the information as it was when they clicked "save as draft".

## One Study, Multiple Contributors ##

**_A contributor registers a study, then asks a colleague to submit the files for that study._**

Bill registers a study, adding Ben's email address in the permissions step.

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598281975/' title='IMAG0028 by londonbonsaipurple, on Flickr'><img src='http://farm2.static.flickr.com/1071/4598281975_6a4bcca952.jpg' alt='IMAG0028' width='500' height='299' /></a>

Ben then logs in and goes to the contributor home page.

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598285509/' title='IMAG0036 by londonbonsaipurple, on Flickr'><img src='http://farm2.static.flickr.com/1183/4598285509_f8af5bd83b_b.jpg' alt='IMAG0036' width='1024' height='613' /></a>

Ben will see a list of studies for which he has permissions, which will include the study that Bill registered.

## Submit Multiple Studies with Similar Study Info ##

**_A contributor registers multiple studies, each of which has very similar answers for the study questionnaire._**

The "study info" tab in the study dashboard will include a control which allows the user to select a different study, then copy all the answers from that study's questionnaire to the current study's questionnaire.

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598287155/' title='IMAG0039 by londonbonsaipurple, on Flickr'><img src='http://farm5.static.flickr.com/4033/4598287155_b3f384d87a_b.jpg' alt='IMAG0039' width='1024' height='613' /></a>

Note that copying the answers from another study will replace any answers previously entered for the current study, however, the changes will not be made permanent until the user clicks "save changes".

## Proxy Contribution ##

**_A contributor gives a member of WWARN a USB stick containing data files and asks them to submit them on their behalf._**

We propose to build no special interface for this. This could be done with the pages above, for example, as follows.

A researcher, Bill, meets Patrice and gives him some data on a USB stick.

Patrice goes to the user registration page, and fills out Bill's details.

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598280915/' title='IMAG0025 by londonbonsaipurple, on Flickr'><img src='http://farm4.static.flickr.com/3334/4598280915_be20c64462_b.jpg' alt='IMAG0025' width='1024' height='613' /></a>

Patrice then clicks "confirm", which sends a confirmation email to Bill. Patrice then sits with Bill in front of a computer and helps Bill to log in ...

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598898654/' title='IMAG0024 by londonbonsaipurple, on Flickr'><img src='http://farm2.static.flickr.com/1184/4598898654_1dd9ba167d.jpg' alt='IMAG0024' width='500' height='299' /></a>

...begin the study registration wizard...

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598899534/' title='IMAG0026 by londonbonsaipurple, on Flickr'><img src='http://farm5.static.flickr.com/4051/4598899534_8891692438.jpg' alt='IMAG0026' width='500' height='299' /></a>

...fill in step 1...

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598281599/' title='IMAG0027 by londonbonsaipurple, on Flickr'><img src='http://farm5.static.flickr.com/4025/4598281599_68f5bfe5eb.jpg' alt='IMAG0027' width='500' height='299' /></a>

...and fill in step 2, giving Patrice permissions...

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598281975/' title='IMAG0028 by londonbonsaipurple, on Flickr'><img src='http://farm2.static.flickr.com/1071/4598281975_6a4bcca952.jpg' alt='IMAG0028' width='500' height='299' /></a>

...then skipping to the end...

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598901994/' title='IMAG0032 by londonbonsaipurple, on Flickr'><img src='http://farm5.static.flickr.com/4017/4598901994_7d5f5b8aab.jpg' alt='IMAG0032' width='500' height='299' /></a>

...and clicking "confirm".

Patrice can then log in and act as a contributor with permissions on Bill's study, going to the contributor home page...

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598285509/' title='IMAG0036 by londonbonsaipurple, on Flickr'><img src='http://farm2.static.flickr.com/1183/4598285509_f8af5bd83b.jpg' alt='IMAG0036' width='500' height='299' /></a>

...selecting Bill's study, navigating to the "files" tab on the study dashboard, uploading the files Bill gave him, and adding any other information...

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598286581/' title='IMAG0038 by londonbonsaipurple, on Flickr'><img src='http://farm2.static.flickr.com/1417/4598286581_08b4abf056_b.jpg' alt='IMAG0038' width='1024' height='613' /></a>

## Personal Data Review ##

**_All submitted files must be reviewed to see if they contain personal data that should be removed prior to allowing WWARN curators to download the file._**

A person or persons is nominated by WWARN to act as personal data reviewer for submitted files. WWARN notifies informatics of who this person is, and informatics will add that person to the list of people authorised to access the personal data pages in Chassis.

The personal data reviewer can then log in...

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598898654/' title='IMAG0024 by londonbonsaipurple, on Flickr'><img src='http://farm2.static.flickr.com/1184/4598898654_1dd9ba167d.jpg' alt='IMAG0024' width='500' height='299' /></a>

...and navigate to the personal data home page...

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598906816/' title='IMAG0042 by londonbonsaipurple, on Flickr'><img src='http://farm5.static.flickr.com/4006/4598906816_10c25e1f48_b.jpg' alt='IMAG0042' width='1024' height='613' /></a>

This page shows a list of newly submitted files that need to be reviewed. The user selects one of the files to review.

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598907032/' title='IMAG0043 by londonbonsaipurple, on Flickr'><img src='http://farm5.static.flickr.com/4033/4598907032_c8b812d476_b.jpg' alt='IMAG0043' width='1024' height='613' /></a>

The user then downloads the file, inspects it, and either selects "file contains personal data that needs to be removed" or "file can proceed to curation", and clicks "submit review".

## Personal Data Removal ##

**_If a submitted file is found to contain personal data that should be removed prior to curation, an personal data cleaner can download the file, and upload another file with the personal data removed._**

A person or persons is nominated by WWARN to act as personal data **remover** for submitted files. WWARN notifies informatics of who this person is, and informatics will add that person to the list of people authorised to access the personal data pages in Chassis.

The personal data remover can then log in...

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598898654/' title='IMAG0024 by londonbonsaipurple, on Flickr'><img src='http://farm2.static.flickr.com/1184/4598898654_1dd9ba167d.jpg' alt='IMAG0024' width='500' height='299' /></a>

...and navigate to the personal data home page...

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598906816/' title='IMAG0042 by londonbonsaipurple, on Flickr'><img src='http://farm5.static.flickr.com/4006/4598906816_10c25e1f48_b.jpg' alt='IMAG0042' width='1024' height='613' /></a>

This page shows a list of newly submitted files that need to be reviewed. The page also shows a list of files that have been reviewed and found to contain personal data that needs to be removed prior to curation. The user selects one of these files with personal data.

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598289207/' title='IMAG0044 by londonbonsaipurple, on Flickr'><img src='http://farm4.static.flickr.com/3222/4598289207_720686f8d6_b.jpg' alt='IMAG0044' width='1024' height='613' /></a>

The user can then download the original file, and upload a new file with personal data removed.

## Contributor Cannot Download Submitted Files Unless Reviewed for Personal Data ##

**_A contributor should not be allowed to download any submitted files for a study unless the file has been reviewed and found not contain personal data that needs to be removed._**

In the "files" tab in the study dashboard, for submitted files, the "download" link will not be present unless the file has been reviewed for personal data and approved.

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598286581/' title='IMAG0038 by londonbonsaipurple, on Flickr'><img src='http://farm2.static.flickr.com/1417/4598286581_08b4abf056_b.jpg' alt='IMAG0038' width='1024' height='613' /></a>

## Curator Find Registered Studies ##

**_A curator looks up a list of all registered studies, to find a study to begin work on._**

A person or persons is nominated by WWARN to act as curator. WWARN notifies informatics of who this person is, and informatics will add that person to the list of people authorised to access the curator pages in Chassis.

The curator can then log in...

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598898654/' title='IMAG0024 by londonbonsaipurple, on Flickr'><img src='http://farm2.static.flickr.com/1184/4598898654_1dd9ba167d.jpg' alt='IMAG0024' width='500' height='299' /></a>

...and go to the curator home page...

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598907716/' title='IMAG0045 by londonbonsaipurple, on Flickr'><img src='http://farm5.static.flickr.com/4015/4598907716_6849141a2d_b.jpg' alt='IMAG0045' width='1024' height='613' /></a>

...which will have a list of **all** registered studies.

## Curator Downloads Submitted Files ##

**_A curator chooses a study to work on, then looks up the the list of submitted files for that study, and downloads a file to begin working on._**

A curator logs in and goes to the curator home page...

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598907716/' title='IMAG0045 by londonbonsaipurple, on Flickr'><img src='http://farm5.static.flickr.com/4015/4598907716_6849141a2d_b.jpg' alt='IMAG0045' width='1024' height='613' /></a>

...then chooses a study to work on. The curator clicks on the chosen study, which takes the curator to the study dashboard for that study...

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598907994/' title='IMAG0046 by londonbonsaipurple, on Flickr'><img src='http://farm2.static.flickr.com/1275/4598907994_f3874aab7b_b.jpg' alt='IMAG0046' width='1024' height='613' /></a>

The study dashboard for the curator is similar to the study dashboard for the contributor, except that the permissions, acknowledgments and publications tabs are read-only (i.e., the curator cannot make changes), and the files tab does not have an upload form (i.e., the curator cannot submit files).

## Curator Cannot Download Submitted Files Unless Reviewed for Personal Data ##

**_A curator should not be allowed to download any submitted files for a study unless the file has been reviewed and found not contain personal data that needs to be removed._**

As for the submitter, in the "files" tab in the curator's study dashboard, for submitted files, the "download" link will not be present unless the file has been reviewed for personal data and approved.

## Curator Downloads Files With Personal Data Removed ##

**_A curator downloads a file that was uploaded by the personal data cleaner for a given study._**

In the "files" tab in the curator's study dashboard, the curator can see a list of files submitted by the contributors, and can also see a separate list of files uploaded by the personal data remover, if there are any such files.

## Curator Modifies Study Info ##

**_A curator fills out some information in the study questionnaire for a given study._**

A curator logs in and goes to the curator home page...

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598907716/' title='IMAG0045 by londonbonsaipurple, on Flickr'><img src='http://farm5.static.flickr.com/4015/4598907716_6849141a2d_b.jpg' alt='IMAG0045' width='1024' height='613' /></a>

...then chooses a study to work on. The curator clicks on the chosen study, which takes the curator to the study dashboard for that study...

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598907994/' title='IMAG0046 by londonbonsaipurple, on Flickr'><img src='http://farm2.static.flickr.com/1275/4598907994_f3874aab7b_b.jpg' alt='IMAG0046' width='1024' height='613' /></a>

The curator clicks on the "study info" tab, which is the same as the "study info" tab in the contributor's study dashboard...

<a href='http://www.flickr.com/photos/londonbonsaipurple/4598287155/' title='IMAG0039 by londonbonsaipurple, on Flickr'><img src='http://farm5.static.flickr.com/4033/4598287155_b3f384d87a_b.jpg' alt='IMAG0039' width='1024' height='613' /></a>

I.e., the curator can make changes and save them.



---

# Postponed Functionality (Can Live Without in This Version) #

  * emails to acknowledgments
  * how does curator know who is primary point of contact in multi-contributor study?
  * curator uploads curated files