package com.bridgeit.fundooNote.noteservice.dao;



import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgeit.fundooNote.noteservice.model.Note;
import com.bridgeit.fundooNote.userservice.model.User;


@Repository
public class NoteDaoImpl implements INoteDao {
	
	@Autowired
	private SessionFactory factory;
	
	@Override

	public long addNote(Note note) {
		
			Session getSession=(Session) factory.getCurrentSession();
			note.setColor("white");
			note.setCreatedAt(new Date(System.currentTimeMillis()));
			note.setUpdatedAt(new Date(System.currentTimeMillis()));
			getSession.save(note);
			return note.getId();
	}

	@Override
	public User getUserById(int userId) {
		Session session = factory.getCurrentSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(User.class).add(Restrictions.eq("userId", userId));
		return (User) criteria.uniqueResult();
	}

	@Override
	public void updateNode(Note note,String token) {
		Session session=factory.getCurrentSession();
		note.setLastupdatedAt(new Date(System.currentTimeMillis()));
		session.saveOrUpdate(note);		
	}

	@Override
	public void deleteNode(long id) {
		Session session=factory.getCurrentSession();
		Note note2=session.byId(Note.class).load(id);
		session.delete(note2);	
	}

	@Override
	public List<Note> displayAllNote(String token){
		
		Session session = factory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Note> noteList = session.createQuery("from Note").list();
		for(Note p : noteList){
			System.out.println("noteList :" +p);
		}
		return noteList;
	}

	@Override
	public Note getNoteById(long noteId) {
		
		Session session = factory.getCurrentSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Note.class).add(Restrictions.eq("id", noteId));
		return (Note) criteria.uniqueResult();
	}
	      
	}

