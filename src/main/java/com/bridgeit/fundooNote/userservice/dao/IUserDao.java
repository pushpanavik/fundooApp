package com.bridgeit.fundooNote.userservice.dao;

import java.util.List;

import com.bridgeit.fundooNote.userservice.model.User;

public interface IUserDao {

	public int addUser(User user);
	public User validateUser(User user);
	public User getUserByEmaiId(String email);
	public User getUserById(int id);
	List<User> checkEmailId(String emailId);
	public User updateRecord(User user);
	public void updateUserById(int id);
	public List<User> displayAllUser();
}
