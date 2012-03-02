-- Not there yet

select sid, count(lastday) as mode from (
SELECT sid, lastday FROM `curated_files`.`clinical_WWARNSet_imported` order by sid, lastday
) basis group by sid, lastDay 

