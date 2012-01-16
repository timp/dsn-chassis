package org.cggh.chassis.manta.http.servlet;

import org.w3c.dom.Document;

public class UploadResponse {

	private int status;
	private Document document;
	
	private int updateStatus;
	private Document updateDocument;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Document getDocument() {
		return document;
	}
	public void setDocument(Document document) {
		this.document = document;
	}
	public int getUpdateStatus() {
		return updateStatus;
	}
	public void setUpdateStatus(int updateStatus) {
		this.updateStatus = updateStatus;
	}
	public Document getUpdateDocument() {
		return updateDocument;
	}
	public void setUpdateDocument(Document updateDocument) {
		this.updateDocument = updateDocument;
	}
	
}
