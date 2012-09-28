package org.cggh.chassis.manta.util;

/** 
 * Some fields from an AtomEntry;
 * which might be a Study or a curated media entry.
 */
public class AtomEntry {

  // Atom fields
  private String id;
  private String category;
  private String title;
  private String self;
  private String published;

  //manta fields
  private String origin;
	private AtomEntry originStudy;
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
	public AtomEntry getOriginStudy() {
		return originStudy;
	}
	public void setOriginStudy(AtomEntry originStudy) {
		this.originStudy = originStudy;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	/** url of origin study */
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
  /** chassisStudyId */
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }

  /** Atom:Title - may be study title or file name. */
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
}
