package com.bridgeit.fundooNote.userservice.model;

public class EmailDto {
private String name;
private String mailTo;
private String token;
private String url;
private String text;

public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}
public String getText() {
	return text;
}
public void setText(String text) {
	this.text = text;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getMailTo() {
	return mailTo;
}
public void setMailTo(String mailTo) {
	this.mailTo = mailTo;
}
public String getToken() {
	return token;
}
public void setToken(String token) {
	this.token = token;
}



}
