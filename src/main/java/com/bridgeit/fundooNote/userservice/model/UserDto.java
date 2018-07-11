package com.bridgeit.fundooNote.userservice.model;

public class UserDto {

private String emailId;
private String password;

public String getEmail() {
	return emailId;
}
public void setEmail(String email) {
	this.emailId = email;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}

}
