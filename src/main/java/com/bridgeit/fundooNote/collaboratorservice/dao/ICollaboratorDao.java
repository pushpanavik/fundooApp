package com.bridgeit.fundooNote.collaboratorservice.dao;

import java.util.List;

import com.bridgeit.fundooNote.collaboratorservice.model.Collaborator;
import com.bridgeit.fundooNote.userservice.model.User;

public interface ICollaboratorDao {

	int addCollaborator(Collaborator collaborator);

	List<Collaborator> getallCollaborators(User user);

	Collaborator getCollaboratorById(int collaboratorid);

	void delete(int id);
	
	 void updateCollborator(Collaborator collaborator);

	

}
