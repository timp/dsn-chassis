# DataWiki #

## Ideas / Wishlist ##

Not all of these are necessarily a good idea. In no particular order...

### Editing; Validation; Constraints ###

  * duplicate a row, i.e., create a new row by copying an existing row

  * auto-incrementing columns?

  * UUID-like generated ID column (not editable for obvious reasons)?

  * constrain a column to contain unique values (i.e., no duplicates)

  * express logical constraints across multiple colums, e.g., birth date must be before admission date **--done in alimanfoo branch using pseudo-xpath expression**

  * get specific validation error messages, e.g., "not a valid integer", instead of nonspecific "the data are not valid"

  * get a validation report for a whole table, i.e., a summary of all validation problems

  * constrain decimal precision

  * add other datatypes, e.g., URI, email, float, double, boolean, password, timestamp, date, blob/file, ...

  * add a reference datatype: reference to a column

  * ...

### Import/Export ###

  * create a new table by importing a CSV file with configurable parser (i.e., can configure cell & row delimiters etc.)

  * create a new table by importing an Excel file

  * create a new table by importing a stata file

  * create a new table by importing an spss file

  * export a table to XML (trivial)

  * export a table to CSV

  * export a table to Excel

  * export a table to stata file

  * export a table to spss file

### Versioning ###

  * view a diff between two specified revisions

  * export a diff or change set between two specified revisions

  * revert to specified version

#### Support for data dependency? ####

  1. Joe imports Table A1 and defines Managed List B1 (used by Table A1).
  1. Sue uses Table A1 to make View C1.
  1. Fred uses View C1 for Analysis X1 or import into System Y1.
  1. Bob updates Managed List B1, creating Managed List B2.
  1. Joe corrects bad data in Table A1, creating Table A2.
  1. Analysis X1 and System Y1 are still using View C1, based on Table A1 and Managed List B1. (Perhaps this could be a reference signature on the export?)
  1. To update System Y1, Fred might need to wait for Sue to create View C2.

Perhaps a policy to never Move, Remove, Rename or Change **any** data resource and only allow Addition or Derivation, that might help to maintain a kind of referential integrity for "wild" resources and allow provenance tracking.


### Controlled Value Lists; Referencing Tables ###

  * provide a means to manage controlled value lists (i.e., controlled vocabularies) independently from a single table, then use the list in any number of tables

  * N.B. one way to achieve this would be to simply allow the use of one table as a value list for a column in another table

  * referential integrity and foreign keys??? effectively the same as the idea above; could use autocomplete to deal with selecting item from large tables instead of select1

### Permissions & Authorization ###

  * provide permissions management utility for tables **--done in alimanfoo branch, implemented simple three-level model of permissions for each table, admins, editors and viewers**

### Viewing; Searching and Query ###

  * show/hide specific columns

  * deal with tables with many columns

  * allow custom queries to filter rows of a table, e.g., show rows where age is greater than 4 and sex is male

### Views; Mapping and Transformation ###

  * allow definition of views of a table, i.e., effectively a transformation of a table from one set of columns to another, evaluated dynamically

  * allow materialisation of a view as a new table, i.e., definition of a transformation from one set of columns to another, and store output of transformation, with derivation, and allow further editing or other work on the new table

  * allow in situ transformation/replacement of a single column of a table, e.g., transform all ages from years to months

  * define a transformation from two or more input tables to two or more output tables

### Merging ###

  * merging of two tables, joined on a specified column, then present a report on conflicts and guide user in resolving conflicts; storing output as a new table, with derivation

  * merging in the case of longitudinal data...?

  * merging and location data with missingness...?

### Grouping ###

  * grouping of two or more tables into database/dataset/...

  * maybe "links" in nested "folders", e.g. Folder A contains a link to Table B, and Folder C contains Folder D, which also contains a link to Table B, and Table E.

### Misc ###

  * defining pivot tables

  * performing simple statistical tests

  * inspirations from Google Fusion Tables, http://tables.googlelabs.com/Home
  * simple charts and maps, visualizations, http://tables.googlelabs.com/public/tour/tour1.html
  * also performs basic merge, http://tables.googlelabs.com/DataSource?dsrcid=130641

...