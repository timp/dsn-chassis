package org.cggh.chassis.rest.dao;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.cggh.casutils.CasProtectedResourceDownloader;
import org.cggh.casutils.CasProtectedResourceDownloaderFactory;
import org.cggh.casutils.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class ChassisDAO {

  private String chassisService;

  private String chassisUser;

  private String chassisPassword;

 
  @Autowired
  public ChassisDAO(@Qualifier("chassisService") String serv, @Qualifier("chassisUser") String user, @Qualifier("chassisPassword") String password) throws MalformedURLException {
    setChassisService(serv);
    setChassisUser(user);
    setChassisPassword(password);
    URL url = new URL(getChassisService());
    String uri = url.getHost();
    if (url.getPort() > 0) {
      uri += ":" + url.getPort();
    }
    String protocol = url.getProtocol() + "://";
    CasProtectedResourceDownloaderFactory.downloaders.put(
            CasProtectedResourceDownloaderFactory.keyFromUrl(getChassisService()),
            new CasProtectedResourceDownloader(protocol, uri,
                    getChassisUser(), getChassisPassword(), System.getProperty("java.io.tmpdir")));
  }

  public File getStudies() throws IOException, NotFoundException {

    File studiesOut = File.createTempFile("studies", ".xml");

    String studyFeedUrl = getChassisService() + "repository/service/content/studies";

    CasProtectedResourceDownloader downloader = CasProtectedResourceDownloaderFactory
            .getCasProtectedResourceDownloader(studyFeedUrl);
    downloader.downloadUrlToFile(studyFeedUrl, studiesOut);

    return (studiesOut);
  }

  public File getLinks() throws IOException, NotFoundException {

    File linksOut = File.createTempFile("links", ".xml");
    String linkedStudiesFeedUrl = getChassisService() + "repository/service/content/link";
    CasProtectedResourceDownloader downloader = CasProtectedResourceDownloaderFactory
            .getCasProtectedResourceDownloader(linkedStudiesFeedUrl);
    downloader.downloadUrlToFile(linkedStudiesFeedUrl, linksOut);
    return (linksOut);

  }

  public String getChassisService() {
    return chassisService;
  }

  public void setChassisService(String chassisService) {
    this.chassisService = chassisService;
  }

  public String getChassisUser() {
    return chassisUser;
  }

  public void setChassisUser(String chassisUser) {
    this.chassisUser = chassisUser;
  }

  public String getChassisPassword() {
    return chassisPassword;
  }

  public void setChassisPassword(String chassisPassword) {
    this.chassisPassword = chassisPassword;
  }

}
