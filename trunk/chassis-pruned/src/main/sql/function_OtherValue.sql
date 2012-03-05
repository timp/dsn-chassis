-- Beware, we are splitting on semicolons in jdbc executuion, so don't introduce them

DROP FUNCTION IF EXISTS `otherValue`;

CREATE FUNCTION `otherValue`( col varchar(255), otherCol varchar(255)) 
    RETURNS varchar(255) CHARSET utf8 DETERMINISTIC
  RETURN (select CASE col
        WHEN  'other' THEN  
          otherCol
        ELSE  
          col 
        END
        )
;


