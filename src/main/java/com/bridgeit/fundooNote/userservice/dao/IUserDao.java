package com.bridgeit.fundooNote.userservice.dao;

import java.util.List;

import com.bridgeit.fundooNote.userservice.model.User;

public interface IUserDao {

	public int addUser(User user);
	public User validateUser(User user);
	public User getUserByEmaiId(String email);
	List<User> checkEmailId(String emailId);
}
