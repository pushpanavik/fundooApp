package com.bridgeit.model;

public class Response {
private String message;
private int status;
public int getStatus() {
	return status;
}
public void setStatus(int status) {
	this.status = status;
}
public String getMsg() {
	return message;
}
public void setMsg(String msg) {
	this.message = msg;
}
 
public Response() {
	
}
}
