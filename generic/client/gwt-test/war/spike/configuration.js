CHASSIS = new Object();

// configure service URLs

CHASSIS.userDetailsServiceEndpointURL = "/chassis-generic-service-user/gwtrpc/userdetails";

CHASSIS.studyFeedURL = "/chassis-generic-service-exist/atom/edit/studies";
CHASSIS.studyQueryServiceURL = "/chassis-generic-service-exist/query/studies.xql";

CHASSIS.studyQuestionnaireURL = "/chassis-generic-client-gwt/questionnaire/study-questionnaire.xml";

CHASSIS.submissionFeedURL = "/chassis-generic-service-exist/atom/edit/submissions";
CHASSIS.submissionQueryServiceURL = "/chassis-generic-service-exist/query/submissions.xql";

CHASSIS.dataFileFeedURL = "/chassis-generic-service-exist/atom/edit/datafiles";
CHASSIS.dataFileQueryServiceURL = "/chassis-generic-service-exist/query/datafiles.xql";

// configure role name prefix
CHASSIS.userChassisRolesPrefix = "ROLE_CHASSIS_";

// configure permission suffix to chassis role mappings
CHASSIS.chassisRoleCoordinator = {"permissionSuffix":"COORDINATOR",
								  "label":{
											"en":"Coordinator"
										  }
								 };
CHASSIS.chassisRoleCurator = {"permissionSuffix":"CURATOR",
							  "label":{
										"en":"Curator"
									  }
							 };
CHASSIS.chassisRoleGatekeeper = {"permissionSuffix":"GATEKEEPER",
								  "label":{
											"en":"Gate Keeper"
										  }
								 };
CHASSIS.chassisRoleSubmitter = {"permissionSuffix":"SUBMITTER",
								  "label":{
											"en":"Submitter"
										  }
								 };
CHASSIS.chassisRoleUser = {"permissionSuffix":"USER",
							  "label":{
										"en":"User"
									  }
							 };

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