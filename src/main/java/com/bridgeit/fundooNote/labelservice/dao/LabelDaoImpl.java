package com.bridgeit.fundooNote.labelservice.dao;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgeit.fundooNote.labelservice.model.Label;
import com.bridgeit.fundooNote.labelservice.model.LabelDto;

@Repository
public class LabelDaoImpl implements ILabelDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public long addLabel(Label label) {
		
		Session session=sessionFactory.getCurrentSession();
		session.save(label);
		
		return label.getLabelId();
	}
	@Override
	public void deleteLabel(long id) {
		Session session=sessionFactory.getCurrentSession();
		Label label2=session.byId(Label.class).load(id);
		session.delete(label2);
		
	}
	@Override
	public void updateLabel(LabelDto labelDto, long id) {
		Session session=sessionFactory.getCurrentSession();
		
		Label label2=session.byId(Label.class).load(id);
		session.saveOrUpdate(label2);
			
		
	}
	/*@Override
	public List<Note> displayAllNote(Note note){
		
		Session session = sessionFactory.getCurrentSession();
		Transaction tx=session.beginTransaction();
		List<Note> noteList = session.createQuery("from Note").list();
		for(Note p : noteList){
			System.out.println("noteList :" +p);
		}
		return noteList;
	}*/

}
