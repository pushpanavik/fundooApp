package com.bridgeit.fundooNote.collaborator.dao;

import java.util.List;

import com.bridgeit.fundooNote.collaborator.model.Collaborator;
import com.bridgeit.fundooNote.userservice.model.User;

public interface ICollaboratorDao {

	public int addCollaborator(Collaborator collaborator);

	public List<Collaborator> getallCollaborators(User user);
	public Collaborator getCollaboratorById(int id);

	public int isUserExist(Collaborator collaborator);

	public void update(Collaborator collaborator);

	public void delete(Collaborator collaborator);

}