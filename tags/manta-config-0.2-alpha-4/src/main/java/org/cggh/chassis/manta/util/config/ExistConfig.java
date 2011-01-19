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

	private String contentSuffix = "/content";
	private String contentURL = null;
	
	private String selfSuffix = "/content";
	private String selfURL = null;
	private String editSuffix = "/content";
	private String editURL = null;
	private String editMediaSuffix = "/content";
	private String editMediaURL = null;
	
	private String historySuffix = "/history";
	private String historyURL = null;
	private String securitySuffix = "/security";
	private String securityURL = null;
	private String userNameAttributeName = "user-name";
	private String userRolesAttributeName = "user-roles";
	private String mediaStorageDir = "/data/atombeat/media";
	private String mediaStorageMode = "FILE"; // or "DIR"
	
	private String makeURL(String url, String suffix) {
		String ret;
		if (url == null) {
			ret = serviceBaseURL + suffix;
		} else {
			ret = url;
		}
		return ret;
	}
	/**
	 * @deprecated - no longer used from atombeat 0.2-alpha-4 onwards
	 * @return
	 */
	public String getContentURL() {
		return makeURL(contentURL, contentSuffix);
	}
	/**
	 * @deprecated - no longer used from atombeat 0.2-alpha-4 onwards
	 * @return
	 */
	public void setContentURL(String contentURL) {
		this.contentURL = contentURL;
	}
	public String getSelfURL() {
		return makeURL(selfURL, selfSuffix);
	}
	public void setSelfURL(String contentURL) {
		this.selfURL = contentURL;
	}
	public String getEditURL() {
		return makeURL(editURL, editSuffix);
	}
	public void setEditURL(String contentURL) {
		this.editURL = contentURL;
	}
	public String getEditMediaURL() {
		return makeURL(editMediaURL, editMediaSuffix);
	}
	public void setEditMediaURL(String contentURL) {
		this.editMediaURL = contentURL;
	}
	public String getHistoryURL() {
		return makeURL(historyURL, historySuffix);
	}
	public void setHistoryURL(String historyURL) {
		this.historyURL = historyURL;
	}
	public String getSecurityURL() {
		return makeURL(securityURL, securitySuffix);
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
	
	public String getContentSuffix() {
		return contentSuffix;
	}
	public void setContentSuffix(String contentSuffix) {
		this.contentSuffix = contentSuffix;
	}
	public String getSelfSuffix() {
		return selfSuffix;
	}
	public void setSelfSuffix(String selfSuffix) {
		this.selfSuffix = selfSuffix;
	}
	public String getEditSuffix() {
		return editSuffix;
	}
	public void setEditSuffix(String editSuffix) {
		this.editSuffix = editSuffix;
	}
	public String getEditMediaSuffix() {
		return editMediaSuffix;
	}
	public void setEditMediaSuffix(String editMediaSuffix) {
		this.editMediaSuffix = editMediaSuffix;
	}
	public String getHistorySuffix() {
		return historySuffix;
	}
	public void setHistorySuffix(String historySuffix) {
		this.historySuffix = historySuffix;
	}
	public String getSecuritySuffix() {
		return securitySuffix;
	}
	public void setSecuritySuffix(String securitySuffix) {
		this.securitySuffix = securitySuffix;
	}
}
