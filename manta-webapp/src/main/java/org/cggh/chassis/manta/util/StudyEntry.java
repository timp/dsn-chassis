package org.cggh.chassis.manta.util;

public class StudyEntry {

	private String self;
	private String published;
	private String id;
	private String origin;
	private StudyEntry originStudy;
	private String display;
	private String modules;
	
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public String getModules() {
		return modules;
	}
	public void setModules(String modules) {
		this.modules = modules;
	}
	public StudyEntry getOriginStudy() {
		return originStudy;
	}
	public void setOriginStudy(StudyEntry originStudy) {
		this.originStudy = originStudy;
	}
	private String category;
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getSelf() {
		return self;
	}
	public void setSelf(String self) {
		this.self = self;
	}
	public String getPublished() {
		return published;
	}
	public void setPublished(String published) {
		this.published = published;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
