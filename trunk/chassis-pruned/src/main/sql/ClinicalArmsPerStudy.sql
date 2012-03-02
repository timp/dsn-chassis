-- arms per study


select ac.armCount, count(ac.armCount) from 
( select StudyId, count(StudyId) as armCount 
  from v_ClinicalCompleteStudyRegimens group by StudyId  ) as ac
group by armCount

;
