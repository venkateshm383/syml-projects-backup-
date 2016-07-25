package controllers;

public class ReceivedDocuments {
	
	
	String docuementName;
	String splitedDate;
	String link;
	String recivedDocuments;
	int recived;
	String splitedLink;
	String splitedDocId;
	int download;
	
	public int getDownload() {
		return download;
	}
	public void setDownload(int download) {
		this.download = download;
	}
	public String getSplitedDocId() {
		return splitedDocId;
	}
	public void setSplitedDocId(String splitedDocId) {
		this.splitedDocId = splitedDocId;
	}
	public String getSplitedLink() {
		return splitedLink;
	}
	public void setSplitedLink(String splitedLink) {
		this.splitedLink = splitedLink;
	}
	public int getRecived() {
		return recived;
	}
	public void setRecived(int recived) {
		this.recived = recived;
	}
	public String getRecivedDocuments() {
		return recivedDocuments;
	}
	public void setRecivedDocuments(String recivedDocuments) {
		this.recivedDocuments = recivedDocuments;
	}
	public String getDocumentTypeContent() {
		return documentTypeContent;
	}
	public void setDocumentTypeContent(String documentTypeContent) {
		this.documentTypeContent = documentTypeContent;
	}
	String documentTypeContent;
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getDocuementName() {
		return docuementName;
	}
	public void setDocuementName(String docuementName) {
		this.docuementName = docuementName;
	}
	public String getSplitedDate() {
		return splitedDate;
	}
	public void setSplitedDate(String splitedDate) {
		this.splitedDate = splitedDate;
	}

}
