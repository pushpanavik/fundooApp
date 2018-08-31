package com.bridgeit.fundooNote.noteservice.dao;



import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgeit.fundooNote.jsoup.UrlData;
import com.bridgeit.fundooNote.noteservice.model.Note;
import com.bridgeit.fundooNote.userservice.model.User;
import com.bridgeit.fundooNote.utilservice.VerifyJwtToken;


@Repository
public class NoteDaoImpl implements INoteDao {
	
	@Autowired
	private SessionFactory factory;
	
	@Override
	public long addNote(Note note,User user) {
		
			Session getSession=(Session) factory.getCurrentSession();
			note.setCreatedAt(new Date(System.currentTimeMillis()));

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
	public void updateNode(Note note) {
		Session session=factory.getCurrentSession();
		session.update(note);
		
		System.out.println("note sucessfully updated");
	}

	@Override
	public void deleteNode(int id) {
		Session session=factory.getCurrentSession();
		Note note2=session.byId(Note.class).load(id);
		session.delete(note2);	
	}

	@Override
	public List<Note> displayAllNote(User user){
		
		Session session = factory.getCurrentSession();
		return session.get(User.class, user.getUserId()).getNote();	
	}

	@Override
	public Note getNoteById(int noteId) {
		
		Session session = factory.getCurrentSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Note.class).add(Restrictions.eq("id", noteId));
		return (Note) criteria.uniqueResult();
	}

	@Override
	public boolean isNotewiththatIdExist(int getId) {
		Session session = factory.getCurrentSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Note.class);
		criteria.add(Restrictions.eq("id", getId));
		return true;
	}


	@Override
	public void deleteTrashedNote() {
		Date date=new Date(new Date().getTime()-(20*1000));
		Session session=factory.getCurrentSession();
	
		Query query=session.createQuery("delete from Note where trash=true AND lastupdatedAt <:date");
		query.setParameter("date", date);
		int count=query.executeUpdate();
		if(count>0) {
			System.out.println("Number of notes delted " +count);
		}
		
	}

	
	      
	}

