-- It is assumed that sid/psid is unique
-- Add cunique constraint to table to import to
-- ALTER TABLE `curated_files`.`clinical_WWARNSet_imported` 
-- ADD UNIQUE INDEX `studyPatient_u` (`sid` ASC, `psid` ASC) ;

SELECT count(psid) as pcount FROM `curated_files`.`clinical_WWARNSet_imported` group by sid order by pcount;


