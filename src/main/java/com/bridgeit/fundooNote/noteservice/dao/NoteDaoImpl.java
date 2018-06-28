package com.bridgeit.fundooNote.noteservice.dao;



import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgeit.fundooNote.noteservice.model.Note;
import com.bridgeit.fundooNote.noteservice.model.NoteDto;
import com.bridgeit.fundooNote.utilservice.User;

@Repository
public class NoteDaoImpl implements INoteDao {
	
	@Autowired
	private SessionFactory factory;
	
	@Override

	public long addNote(Note note) {
		
			Session getSession=(Session) factory.getCurrentSession();
			Transaction tx=getSession.beginTransaction();
			getSession.save(note);
			tx.commit();
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
	public void updateNode(NoteDto note, long id) {
		Session session=factory.getCurrentSession();
		Transaction tx=session.beginTransaction();
		Note note2=session.byId(Note.class).load(id);
		session.saveOrUpdate(note2);
		tx.commit();			
		
	}

	@Override
	public void deleteNode(long id) {
		Session session=factory.getCurrentSession();
		Transaction tx=session.beginTransaction();
		Note note2=session.byId(Note.class).load(id);
		session.delete(note2);
		tx.commit();
		
	}

	@Override
	public List<Note> displayAllNote(Note note){
		
		Session session = factory.getCurrentSession();
		Transaction tx=session.beginTransaction();
		List<Note> noteList = session.createQuery("from Note").list();
		for(Note p : noteList){
			System.out.println("noteList :" +p);
		}
		return noteList;
	}
	      
	}

