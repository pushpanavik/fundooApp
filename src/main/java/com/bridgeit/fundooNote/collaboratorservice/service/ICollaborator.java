package com.bridgeit.fundooNote.collaboratorservice.service;

import java.util.List;
import com.bridgeit.fundooNote.collaboratorservice.model.Collaborator;

public interface ICollaborator {

	public int addCollaborator(Collaborator collaborator, String token);

	List<Collaborator> getAllCollaborators(String token);

	void deleteCollaborator(int collaboratorid, String token);

	
	
	

}
