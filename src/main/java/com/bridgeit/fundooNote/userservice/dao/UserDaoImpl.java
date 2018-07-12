package com.bridgeit.fundooNote.userservice.dao;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgeit.fundooNote.userservice.model.User;



@Repository
public class UserDaoImpl implements IUserDao {
	
	public UserDaoImpl() {
	}

	@Autowired
	private SessionFactory sessionFactory;
	
	public int addUser(User user) {
		Session session=sessionFactory.getCurrentSession();
	    int id3=(Integer)session.save(user);
		
		return id3;
	}

	@SuppressWarnings("deprecation")
	public User validateUser(User user) {
		
		Session session=sessionFactory.getCurrentSession();
		Criteria crite=session.createCriteria(User.class);
		Criterion email=Restrictions.eq("emailId", user.getEmailId());
		Criterion password=Restrictions.eq("password", user.getPassword());
		Criterion bothArePresent=Restrictions.and(email,password);
		crite.add(bothArePresent);
		
		User reg=(User) crite.uniqueResult();
		//System.out.println(reg.getUsername()+ " "+ reg.getEmail()+ " "+ reg.getPassword());
		
		return reg;
	}

	@SuppressWarnings("unchecked")
	public List<User> checkEmailId(String Email) {
		
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("emailId", Email));
		List<User> userList = criteria.list();
		return userList;
	}

	@Override
	public User getUserByEmaiId(String email) {
		
		
		System.out.println("goes inside getuser by email id");
		
		Session session=sessionFactory.getCurrentSession();
		@SuppressWarnings("deprecation")
		Criteria criteria=session.createCriteria(User.class).add(Restrictions.eq("emailId",email));
		User userobj=(User)criteria.uniqueResult();
				return userobj;
	}

	@Override
	public User getUserById(int id) {
		Session session=(Session) sessionFactory.getCurrentSession();
		User user=session.get(User.class,id);
		return user;	
	}

	@Override
	public User updateRecord(User user) {
		Session session=sessionFactory.getCurrentSession();
		session.update(user);
		return user;
	}
	
	
}