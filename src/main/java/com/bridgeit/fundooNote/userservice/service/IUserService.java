package com.bridgeit.fundooNote.userservice.service;

import javax.servlet.http.HttpServletRequest;

import com.bridgeit.fundooNote.userservice.model.User;

public interface IUserService {
	public String addUser(User user, HttpServletRequest req);
	public String validateUser(User user);
	
	boolean isEmailIdPresent(String string);
}
