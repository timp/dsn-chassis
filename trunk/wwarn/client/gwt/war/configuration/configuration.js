if (typeof CHASSIS == "undefined" || !CHASSIS) {
	CHASSIS = new Object();
}

// configure network name

CHASSIS.networkName = "WWARN";

// configure service URLs

CHASSIS.client = "/chassis-wwarn-client-gwt";
CHASSIS.userService = "/chassis-generic-service-user";
CHASSIS.existService = "/chassis-generic-service-exist";
CHASSIS.uploadService = "/chassis-generic-service-upload";

CHASSIS.userDetailsServiceEndpointUrl = CHASSIS.userService + "/gwtrpc/userdetails";

CHASSIS.studyCollectionUrl = CHASSIS.existService + "/atom/edit/studies";
CHASSIS.studyQueryServiceUrl = CHASSIS.existService + "/query/studies.xql";

CHASSIS.studyQuestionnaireUrl = CHASSIS.client + "/questionnaire/study-questionnaire.xml";

CHASSIS.submissionCollectionUrl = CHASSIS.existService + "/atom/edit/submissions";
CHASSIS.submissionQueryServiceUrl = CHASSIS.existService + "/query/submissions.xql";

CHASSIS.dataFileCollectionUrl = CHASSIS.existService + "/atom/edit/datafiles";
CHASSIS.dataFileQueryServiceUrl = CHASSIS.existService + "/query/datafiles.xql";

CHASSIS.datasetCollectionUrl = CHASSIS.existService + "/atom/edit/datasets";
CHASSIS.datasetQueryServiceUrl = CHASSIS.existService + "/query/datasets.xql";

CHASSIS.mediaCollectionUrl = CHASSIS.existService + "/atom/edit/media";

CHASSIS.sandboxCollectionUrl = CHASSIS.existService + "/atom/edit/sandbox";

CHASSIS.newDataFileServiceUrl = CHASSIS.uploadService + "/newdatafile";
CHASSIS.uploadDataFileRevisionServiceUrl = CHASSIS.uploadService + "/revisedatafile";

//configure role name prefix

CHASSIS.userChassisRolesPrefix = "ROLE_CHASSIS_";

//configure permission suffix to chassis role mappings

CHASSIS.chassisRoleCoordinator = {
	"permissionSuffix": "COORDINATOR",
	"label": {
		"en": "Coordinator"
	}
};

CHASSIS.chassisRoleCurator = {
	"permissionSuffix": "CURATOR",
	"label": {
		"en": "Curator"
	}
};

CHASSIS.chassisRoleGatekeeper = {
	"permissionSuffix": "GATEKEEPER",
	"label": {
		"en": "Gate Keeper"
	}
};

CHASSIS.chassisRoleSubmitter = {
	"permissionSuffix": "SUBMITTER",
	"label": {
		"en": "Submitter"
	}
};

CHASSIS.chassisRoleUser = {
	"permissionSuffix": "USER",
	"label": {
		"en": "User"
	}
};

CHASSIS.chassisRoleAdministrator = {
	"permissionSuffix": "ADMINISTRATOR",
	"label": {
		"en": "Administrator"
	}
};


// configure modules
CHASSIS.modules = [
   {
	   "id": "clinical",
	   "label": {
	   		"en": "Clinical"
   		}
   },
   {
	   "id": "molecular",
	   "label": {
	   		"en": "Molecular"
   		}
   },
   {
	   "id": "invitro",
	   "label": {
	   		"en": "In Vitro"
   		}
   },
   {
	   "id": "pharmacology",
	   "label": {
	   		"en": "Pharmacology"
		}
   }
];