-- http://charlie.well.ox.ac.uk:8080/posql/selection/?db=wwarn_repository&query=select+%0D%0A+++chassisId%2C+%0D%0A+++guid%2C%0D%0A+++submissionDate%2C%0D%0A+++description%0D%0AFROM+study%0D%0AORDER+BY+submissionDate%0D%0A&Run=Run

use wwarn_repository;
select 
   chassisId, 
   guid,
   submissionDate,
   description
FROM study
ORDER BY submissionDate
