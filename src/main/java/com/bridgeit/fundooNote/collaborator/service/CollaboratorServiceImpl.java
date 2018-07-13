//package com.bridgeit.fundooNote.collaborator.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.bridgeit.fundooNote.collaborator.dao.ICollaboratorDao;
//import com.bridgeit.fundooNote.collaborator.model.Collaborator;
//import com.bridgeit.fundooNote.noteservice.dao.INoteDao;
//import com.bridgeit.fundooNote.userservice.dao.IUserDao;
//import com.bridgeit.fundooNote.userservice.model.User;
//
//public class CollaboratorServiceImpl implements ICollaboratorService {
//
//	@Autowired
//	IUserDao userDao;
//	
//	@Autowired
//	INoteDao noteDao;
//	
//	@Autowired
//	ICollaboratorDao collaboratorDao;
//	
//	@Override
//	public void addCollaborator(Collaborator collaborator,int id) {
//		
//		User user = userDao.getUserByEmaiId(collaborator.getShareId().getEmailId());
//		collaborator.setShareId(user);
//	}
//
//}
