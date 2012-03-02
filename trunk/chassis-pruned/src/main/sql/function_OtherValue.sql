
DROP FUNCTION IF EXISTS `otherValue`;

CREATE FUNCTION `otherValue`( col varchar(255), otherCol varchar(255)) RETURNS varchar(255) CHARSET utf8
    DETERMINISTIC
BEGIN
    DECLARE ret varchar(255);
    select CASE col
        WHEN  'other' THEN  otherCol
        ELSE  
         col 
        END INTO ret;
        RETURN ret;
        
END
;
