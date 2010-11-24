package org.cggh.chassis.manta.util.config;
/**
 * Java bean to contain Atombeat eXist configuration variables
 * 
 * @author iwright
 *
 */
public class ExistConfig {

	private String username = "admin";
	private String password = "";
	private String serviceBaseURL = "http://localhost:8080/repository/service";
	private String contentURL = serviceBaseURL + "/content";
	private String historyURL = serviceBaseURL + "/history";
	private String securityURL = serviceBaseURL + "/security";
	private String userNameAttributeName = "user-name";
	private String userRolesAttributeName = "user-roles";
	private String mediaStorageDir = "/data/atombeat/media";
	private String mediaStorageMode = "FILE"; // or "DIR"
	
	public String getContentURL() {
		return contentURL;
	}
	public void setContentURL(String contentURL) {
		this.contentURL = contentURL;
	}
	public String getHistoryURL() {
		return historyURL;
	}
	public void setHistoryURL(String historyURL) {
		this.historyURL = historyURL;
	}
	public String getSecurityURL() {
		return securityURL;
	}
	public void setSecurityURL(String securityURL) {
		this.securityURL = securityURL;
	}
	public String getUserNameAttributeName() {
		return userNameAttributeName;
	}
	public void setUserNameAttributeName(String userNameAttributeName) {
		this.userNameAttributeName = userNameAttributeName;
	}
	public String getUserRolesAttributeName() {
		return userRolesAttributeName;
	}
	public void setUserRolesAttributeName(String userRolesAttributeName) {
		this.userRolesAttributeName = userRolesAttributeName;
	}
	public String getMediaStorageDir() {
		return mediaStorageDir;
	}
	public void setMediaStorageDir(String mediaStorageDir) {
		this.mediaStorageDir = mediaStorageDir;
	}
	public String getMediaStorageMode() {
		return mediaStorageMode;
	}
	public void setMediaStorageMode(String mediaStorageMode) {
		this.mediaStorageMode = mediaStorageMode;
	}
	public String getServiceBaseURL() {
		return serviceBaseURL;
	}
	public void setServiceBaseURL(String serviceBaseURL) {
		this.serviceBaseURL = serviceBaseURL;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
