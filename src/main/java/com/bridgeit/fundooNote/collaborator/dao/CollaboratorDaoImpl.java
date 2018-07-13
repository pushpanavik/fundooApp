//package com.bridgeit.fundooNote.collaborator.dao;
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.bridgeit.fundooNote.collaborator.model.Collaborator;
//
//
//public class CollaboratorDaoImpl implements ICollaboratorDao {
//
//	@Autowired
//	SessionFactory sessionFactory;
//	@Override
//	public void addCollaborator(Collaborator collaborator) {
//		Session session = sessionFactory.getCurrentSession();
//		session.save(collaborator);
//		System.out.println("collaborat successfully");
//	}
//
//}
