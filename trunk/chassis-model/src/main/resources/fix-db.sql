ALTER TABLE `chassis_studies`.`acknowledgementsinstitutiona_0` CHANGE COLUMN `DTYPE` `DTYPE` VARCHAR(60) NULL DEFAULT NULL;

ALTER TABLE `chassis_studies`.`thickfilmcalcofparasitemia` CHANGE COLUMN `DTYPE` `DTYPE` VARCHAR(50) NULL DEFAULT NULL;
ALTER TABLE `chassis_studies`.`thinfilmcalcofparasitemia` CHANGE COLUMN `DTYPE` `DTYPE` VARCHAR(50) NULL DEFAULT NULL;

ALTER TABLE `chassis_studies`.`asexualparasitemia` CHANGE COLUMN `DTYPE` `DTYPE` VARCHAR(50) NULL DEFAULT NULL;
ALTER TABLE `chassis_studies`.`geneotypingtodistinguish` CHANGE COLUMN `DTYPE` `DTYPE` VARCHAR(60) NULL DEFAULT NULL;

ALTER TABLE `chassis_studies`.`institutionwebsitesinstituti_0` CHANGE COLUMN `DTYPE` `DTYPE` VARCHAR(50) NULL DEFAULT NULL;
ALTER TABLE `chassis_studies`.`infectioncompestlociopen` CHANGE COLUMN `DTYPE` `DTYPE` VARCHAR(50) NULL DEFAULT NULL;
