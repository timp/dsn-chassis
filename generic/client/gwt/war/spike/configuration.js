CHASSIS = new Object();

// configure service URLs

CHASSIS.userDetailsServiceEndpointURL = "/chassis-generic-service-user/gwtrpc/userdetails";

CHASSIS.studyFeedURL = "/chassis-generic-service-exist/atom/edit/studies";
CHASSIS.studyQueryServiceURL = "/chassis-generic-service-exist/query/studies.xql";

CHASSIS.submissionFeedURL = "/chassis-generic-service-exist/atom/edit/submissions";
CHASSIS.submissionQueryServiceURL = "/chassis-generic-service-exist/query/submissions.xql";

CHASSIS.dataFileFeedURL = "/chassis-generic-service-exist/atom/edit/datafiles";

// configure role name prefix
CHASSIS.userChassisRolesPrefix = "ROLE_CHASSIS_";

// configure modules
CHASSIS.modules = [
                   {
                	   "id":"clinical",
                	   "label":{
                	   		"en":"Clinical"
                   		}
                   },
                   {
                	   "id":"invitro",
                	   "label":{
                	   		"en":"In Vitro"
                   		}
                   },
                   {
                	   "id":"molecular",
                	   "label":{
                	   		"en":"Molecular"
                   		}
                   },
                   {
                	   "id":"pharmacology",
                	   "label":{
                	   		"en":"Pharmacology"
                   		}
                   }
];