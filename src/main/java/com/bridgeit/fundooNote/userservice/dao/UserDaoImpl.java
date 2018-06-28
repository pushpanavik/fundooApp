package com.bridgeit.fundooNote.userservice.dao;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgeit.fundooNote.userservice.model.User;



@Repository
public class UserDaoImpl implements IUserDao {
	
	public UserDaoImpl() {
		System.out.println("Dao created");
	}

	
	@Autowired
	private SessionFactory factory;
	
	public int addUser(User user) {
		Session session=factory.openSession();
		Transaction tx=session.beginTransaction();
		int id=(Integer)session.save(user);
		
		tx.commit();
		
		
		return id;
	}

	@SuppressWarnings("deprecation")
	public User validateUser(User user) {
		
		Session session=factory.openSession();
		Criteria crite=session.createCriteria(User.class);
		Criterion email=Restrictions.eq("email", user.getEmail());
		Criterion password=Restrictions.eq("password", user.getPassword());
		Criterion bothArePresent=Restrictions.and(email,password);
		crite.add(bothArePresent);
		
		User reg=(User) crite.uniqueResult();
		//System.out.println(reg.getUsername()+ " "+ reg.getEmail()+ " "+ reg.getPassword());
		
		return reg;
	}

	@SuppressWarnings("unchecked")
	public List<User> checkEmailId(String Email) {
		
		Session session = factory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("email", Email));
		List<User> userList = criteria.list();
		return userList;
	}

	@Override
	public User getUserByEmaiId(String email) {
		
		
		System.out.println("goes inside getuser by email id");
		
		Session session=factory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria=session.createCriteria(User.class).add(Restrictions.eq("email",email));
		User userobj=(User)criteria.uniqueResult();
				return userobj;
	}

	
}