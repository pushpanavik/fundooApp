//package com.bridgeit.fundooNote.collaborator.service;
//
//import javax.transaction.Transactional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.bridgeit.fundooNote.collaborator.dao.ICollaboratorDao;
//import com.bridgeit.fundooNote.collaborator.model.Collaborator;
//import com.bridgeit.fundooNote.noteservice.dao.INoteDao;
//import com.bridgeit.fundooNote.userservice.dao.IUserDao;
//import com.bridgeit.fundooNote.userservice.model.User;
//import com.bridgeit.fundooNote.utilservice.VerifyJwtToken;
//
//
//@Service
//public class CollaboratorServiceImpl implements ICollaboratorService{
//
//	@Autowired
//	private ICollaboratorDao collaboratordao;
//		
//	@Autowired
//	private IUserDao userdao;
//
//	@Transactional
//	@Override
//	public int addCollaborator(Collaborator collaborator, String token) {
//		int getId=VerifyJwtToken.getId(token);
//		User user = userdao.getUserById(getId);
//		collaborator.set
//		return collaboratordao.addCollaborator(collaborator);
//	}
//
//	@Transactional
//	@Override
//	public List<Collaborator> getAllCollaborators(String token) {
//		int id = Jwt.parseJWT(token);
//		User user = userdao.getUserById(id);
//
//		List<Collaborator> listofCollaborators = collaboratordao.getallCollaborators(user);
//		return listofCollaborators;
//	}
//	
//
//	@Transactional
//	@Override
//	public void deleteCollaborator(int collaboratorid, String token) {
//		System.out.println("id coll:"+collaboratorid);
//		int id = Jwt.parseJWT(token);	
//		System.out.println("UserId:" + id);
//	
//		 Collaborator collaborator=collaboratordao.getCollaboratorById(collaboratorid);
//		 System.out.println("Collaborator:"+collaborator);
//		 System.out.println("Collaborator: "+collaborator.getUser());
//		 int userid = collaborator.getUser().getId();
//		
//		if (userid == id)
//		{
//			
//			collaboratordao.delete(collaborator);;
//		
//		}
//		
//	}
//
//	@Transactional
//	@Override
//	public boolean update(Collaborator collaborator, String token, int collaboratorid) {
//		int id = Jwt.parseJWT(token);
//		User user = userdao.getUserById(id);
//		Collaborator collaborator1= collaboratordao.getCollaboratorById(id);
//		collaborator1.setEmail(collaborator.getEmail());;
//		if(user.getId()!= collaborator1.getUser().getId()) {
//			return false;	
//		}
//		
//		System.out.println("Pre collaborator:::"+collaborator1.getEmail());
//		collaboratordao.update(collaborator1);
//		return true;
//	}