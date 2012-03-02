select * from ( 
SELECT p.StudyId, c.sid, p.StudyStatus, p.StudyInfoStatus FROM `chassisPruned`.`v_ClinicalStudies` as p
  LEFT JOIN 
     (SELECT distinct sid  FROM `curated_files`.`clinical_WWARNSet_imported`) as c on p.StudyId = c.sid
) as r 
where r.sid is null
;

