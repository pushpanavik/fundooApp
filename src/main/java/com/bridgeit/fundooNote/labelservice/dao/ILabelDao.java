package com.bridgeit.fundooNote.labelservice.dao;

import java.util.List;

import com.bridgeit.fundooNote.labelservice.model.Label;
import com.bridgeit.fundooNote.userservice.model.User;

public interface ILabelDao {

	public long addLabel(Label label,User user);
	User getUserById(int userId);
	public void deleteLabel(int id);
	public void updateLabel(Label label);
	public List<Label> getAllLabel(int id);
	Label getLabelById(int id);
}
