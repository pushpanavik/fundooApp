package com.bridgeit.fundooNote.collaboratorservice.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgeit.fundooNote.collaboratorservice.model.Collaborator;
import com.bridgeit.fundooNote.userservice.model.User;

@Repository
public class CollaboratorDaoImpl implements ICollaboratorDao {

	@Autowired
	private SessionFactory factory;
	
	@Override
	public int addCollaborator(Collaborator collaborator) {
		int data=(Integer) factory.getCurrentSession().save(collaborator);
		return data;
	}

	@Override
	public Collaborator getCollaboratorById(int collaboratorid) {
		Session session=factory.getCurrentSession();
		return session.get(Collaborator.class, collaboratorid);
	}

	@Override
	public void delete(int id) {
		Session session=factory.getCurrentSession();
		Collaborator collaborator2=session.byId(Collaborator.class).load(id);
		session.delete(collaborator2);
		
	}

	@Override
	public List<Collaborator> getallCollaborators(User user) {
		
		Criteria criteria = factory.openSession().createCriteria(Collaborator.class).add(Restrictions.eq("user", user));
		List<Collaborator> list=criteria.list();
		return  list;
		
	}

	@Override
	public void updateCollborator(Collaborator collaborator) {
		Session session=factory.getCurrentSession();
		session.update(collaborator);
	}

}
