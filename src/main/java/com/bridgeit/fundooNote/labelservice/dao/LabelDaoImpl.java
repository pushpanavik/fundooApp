package com.bridgeit.fundooNote.labelservice.dao;



import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgeit.fundooNote.labelservice.model.Label;
import com.bridgeit.fundooNote.labelservice.model.LabelDto;
import com.bridgeit.fundooNote.noteservice.model.Note;

@Repository
public class LabelDaoImpl implements ILabelDao {

	@Autowired
	private SessionFactory factory;
	@Override
	public long addLabel(Label label) {
		
		Session session=factory.getCurrentSession();
		Transaction tx=session.beginTransaction();
		session.save(label);
		tx.commit();
		return label.getLabelId();
	}
	@Override
	public void deleteLabel(long id) {
		Session session=factory.getCurrentSession();
		Transaction tx=session.beginTransaction();
		Label label2=session.byId(Label.class).load(id);
		session.delete(label2);
		tx.commit();
		
	}
	@Override
	public void updateLabel(LabelDto labelDto, long id) {
		Session session=factory.getCurrentSession();
		Transaction tx=session.beginTransaction();
		Label label2=session.byId(Label.class).load(id);
		session.saveOrUpdate(label2);
		tx.commit();	
		
	}
	/*@Override
	public List<Note> displayAllNote(Note note){
		
		Session session = factory.getCurrentSession();
		Transaction tx=session.beginTransaction();
		List<Note> noteList = session.createQuery("from Note").list();
		for(Note p : noteList){
			System.out.println("noteList :" +p);
		}
		return noteList;
	}*/

}
