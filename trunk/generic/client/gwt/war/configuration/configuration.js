if (typeof CHASSIS == "undefined" || !CHASSIS) {
	CHASSIS = new Object();
}

// configure service URLs

CHASSIS.client = "/chassis-generic-client-gwt";
CHASSIS.userService = "/chassis-generic-service-user";
CHASSIS.existService = "/chassis-generic-service-exist";
CHASSIS.uploadService = "/chassis-generic-service-upload";

CHASSIS.userDetailsServiceEndpointURL = CHASSIS.userService + "/gwtrpc/userdetails";

CHASSIS.studyFeedURL = CHASSIS.existService + "/atom/edit/studies";
CHASSIS.studyQueryServiceURL = CHASSIS.existService + "/query/studies.xql";

CHASSIS.studyQuestionnaireURL = CHASSIS.client + "/questionnaire/study-questionnaire.xml";

CHASSIS.submissionFeedURL = CHASSIS.existService + "/atom/edit/submissions";
CHASSIS.submissionQueryServiceURL = CHASSIS.existService + "/query/submissions.xql";

CHASSIS.dataFileFeedURL = CHASSIS.existService + "/atom/edit/datafiles";
CHASSIS.dataFileQueryServiceURL = CHASSIS.existService + "/query/datafiles.xql";

CHASSIS.mediaFeedURL = CHASSIS.existService + "/atom/edit/media";

CHASSIS.sandboxFeedURL = CHASSIS.existService + "/atom/edit/sandbox";

CHASSIS.newDataFileServiceURL = CHASSIS.uploadService + "/newdatafile";
CHASSIS.uploadDataFileRevisionServiceURL = CHASSIS.uploadService + "/revisedatafile";

// CHASSIS.dataFileUploadServiceURL = CHASSIS.uploadService + "/datafileupload";

// configure role name prefix

CHASSIS.userChassisRolesPrefix = "ROLE_CHASSIS_";

// configure permission suffix to chassis role mappings

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
	   "id":"moduleA",
	   "label": {
	   		"en": "Module A"
   		}
   },
   {
	   "id": "moduleB",
	   "label": {
	   		"en": "Module B"
   		}
   },
   {
	   "id": "moduleC",
	   "label": {
	   		"en": "Module C"
   		}
   }
];