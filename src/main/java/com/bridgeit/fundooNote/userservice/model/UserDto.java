package com.bridgeit.fundooNote.userservice.model;

public class UserDto {

	private int userId;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	private String firstname;
	private String lastname;
	private String emailId;
	private String image;
	private String token;
	
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
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
	
	
	public UserDto() {	
	}
	public UserDto(User obj ) {
	
		this.setFirstname(obj.getFirstname());
		this.setLastname(obj.getLastname());
		this.setEmailId(obj.getEmailId());
		this.setImage(obj.getProfilepicImage());
	}
	@Override
	public String toString() {
		return "UserDto [User Id=" +userId +", firstname=" + firstname + ", lastname=" + lastname + ", emailId=" + emailId + "], image=" +image + "]";
	}
	
	
}
