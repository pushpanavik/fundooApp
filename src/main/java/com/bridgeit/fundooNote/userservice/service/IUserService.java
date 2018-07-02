package com.bridgeit.fundooNote.userservice.service;

import javax.servlet.http.HttpServletRequest;

import com.bridgeit.fundooNote.userservice.model.User;

public interface IUserService {
	public String addUser(User user, HttpServletRequest req);
	public String validateUser(User user);
	public boolean forgotPassword(User user,HttpServletRequest req);
	public void resetPassword(HttpServletRequest req,String token,String newPassword);
	boolean isEmailIdPresent(String string);
	public void activateUser(String token);
}
