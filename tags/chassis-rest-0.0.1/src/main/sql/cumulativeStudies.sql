SELECT e1.PublishedItem, COUNT(e2.PublishedItem) AS TotalStudies
  FROM Entry e1, Entry e2 
  WHERE e1.PublishedItem >= e2.PublishedItem
  GROUP BY e1.PublishedItem
  ORDER BY e1.PublishedItem 