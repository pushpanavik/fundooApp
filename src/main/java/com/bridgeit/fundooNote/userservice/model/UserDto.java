package com.bridgeit.fundooNote.userservice.model;

public class UserDto {

	private String firstname;
	private String lastname;
	private String emailId;
	private String password;
	private String address;
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public UserDto() {	
	}
	public UserDto(User user ) {
	
		this.firstname = user.getFirstname();
		this.lastname = user.getLastname();
		this.emailId = user.getEmailId();
		this.password = user.getPassword();
		this.address = user.getAddress();
	}
	@Override
	public String toString() {
		return "UserDto [firstname=" + firstname + ", lastname=" + lastname + ", emailId=" + emailId + ", password="
				+ password + ", address=" + address + "]";
	}
	
	
}
