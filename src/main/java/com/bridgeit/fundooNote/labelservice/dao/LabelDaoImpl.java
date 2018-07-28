package com.bridgeit.fundooNote.labelservice.dao;



import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgeit.fundooNote.labelservice.model.Label;
import com.bridgeit.fundooNote.userservice.model.User;

@Repository
public class LabelDaoImpl implements ILabelDao {

	

	@Autowired
	private SessionFactory factory;

	@Override
	public long addLabel(Label label, User user) {
		
		Session getSession=(Session) factory.getCurrentSession();
	
		getSession.save(label);
		return label.getLabelId();
	}

	@Override
	public User getUserById(int userId) {
		
		Session session = factory.getCurrentSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(User.class).add(Restrictions.eq("userId", userId));
		return (User) criteria.uniqueResult();
	}

	@Override
	public void deleteLabel(int id) {
		
		Session session=factory.getCurrentSession();
		Label delLabel=session.byId(Label.class).load(id);
		if(delLabel==null) {
			System.out.println("Label is null");
		}
		session.delete(delLabel);
	}

	@Override
	public void updateLabel(Label label) {
		Session session=factory.getCurrentSession();
		session.update(label);
		
		System.out.println("note sucessfully updated");
		
	}

	@Override
	public List<Label> getAllLabel(int id) {
		
		Session session = factory.getCurrentSession();
		User user1=session.get(User.class, id);
		return user1.getListOfLabels();
	}

	@Override
	public Label getLabelById(int id) {
		Session session=factory.getCurrentSession();
		  return session.get(Label.class, id);
	}
	
	

}
