-- Note this does not take account of status in chassis


SELECT mode, count(mode) AS modeCount FROM 
(
 SELECT sid, (0+lastday) AS mode FROM
 (
  SELECT sid, lastday, count(lastday) as lastdayCount 
  FROM `curated_files`.`clinical_WWARNSet_imported` 
  GROUP BY sid, lastday  ORDER BY sid, lastdayCount desc 
 ) studyCounts GROUP BY sid ORDER BY mode
) modeCounts GROUP BY mode ORDER BY mode

