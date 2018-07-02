package com.bridgeit.fundooNote.userservice.model;

import java.io.Serializable;


public class EmailDto implements Serializable {

private static final long serialVersionUID = 1L;
private String subject;
private String url;
private String mailto;
public String getSubject() {
	return subject;
}
public void setSubject(String subject) {
	this.subject = subject;
}
public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}
public String getMailto() {
	return mailto;
}
public void setMailto(String mailto) {
	this.mailto = mailto;
}



}
