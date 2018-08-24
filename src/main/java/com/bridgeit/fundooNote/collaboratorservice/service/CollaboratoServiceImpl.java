package com.bridgeit.fundooNote.collaboratorservice.service;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgeit.fundooNote.collaboratorservice.dao.ICollaboratorDao;
import com.bridgeit.fundooNote.collaboratorservice.model.Collaborator;
import com.bridgeit.fundooNote.userservice.dao.IUserDao;
import com.bridgeit.fundooNote.userservice.model.User;
import com.bridgeit.fundooNote.utilservice.VerifyJwtToken;

@Service
public class CollaboratoServiceImpl implements ICollaborator {

	@Autowired
	private ICollaboratorDao collaboratorDao;
	
	@Autowired
	private IUserDao userDao;
	
	
	@Override
	public int addCollaborator(Collaborator collaborator, String token) {
		int getId=VerifyJwtToken.getId(token);
		User user =userDao.getUserById(getId);
		collaborator.setUser(user);
		return collaboratorDao.addCollaborator(collaborator);
		
	}

	@Transactional
	@Override
	public List<Collaborator> getAllCollaborators(String token) {
		int id =VerifyJwtToken.getId(token);
		User user = userDao.getUserById(id);
		List<Collaborator> listofCollaborators = collaboratorDao.getallCollaborators(user);
		return listofCollaborators;
	}
	

	@Transactional
	@Override
	public void deleteCollaborator(int collaboratorid, String token) {
		System.out.println("id coll:"+collaboratorid);
		int id = VerifyJwtToken.getId(token);
		System.out.println("UserId:" + id);
	
		 Collaborator collaborator=collaboratorDao.getCollaboratorById(collaboratorid);
		 System.out.println("Collaborator:"+collaborator);
		 System.out.println("Collaborator: "+collaborator.getUser());
		 int userid = collaborator.getUser().getUserId();
		if (userid == id)
		{
			collaboratorDao.delete(id);

		}
}


}
