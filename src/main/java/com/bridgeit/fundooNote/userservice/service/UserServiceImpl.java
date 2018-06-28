package com.bridgeit.fundooNote.userservice.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgeit.fundooNote.userservice.dao.IUserDao;
import com.bridgeit.fundooNote.userservice.model.User;
import com.bridgeit.fundooNote.userservice.model.Validation;
import com.bridgeit.fundooNote.utilservice.GenerateToken;
import com.bridgeit.fundooNote.utilservice.SendingMail;
import com.bridgeit.fundooNote.utilservice.VerifyJwtToken;



@Service

public class UserServiceImpl implements IUserService {

	@Autowired
	IUserDao userDao;

	@Autowired
	private BCryptPasswordEncoder encoder;
	@Transactional
	public String addUser(User user, HttpServletRequest req) {
		String getDetails = Validation.userValidation(user.getEmail());

		if (getDetails != null) {
			String generataHash = encoder.encode(user.getPassword());
			user.setPassword(generataHash);
			int id = userDao.addUser(user);
			

			System.out.println("My  primary id is:" + id);

			String token = GenerateToken.generateToken(id);
			System.out.println("my Token.... " + token);

			int id1 = VerifyJwtToken.getId(token);
			System.out.println("My id via JWT token..." + id1);

			String url ="<a href='http://localhost:8080/token/tokenvalue/"+token+"' ></a>";	
			System.out.println(url);
			System.out.println("emailID.." + user.getEmail());
			String mailTo = user.getEmail();
			
			String subject = "link to activate your account"; 

			SendingMail.sendMail(mailTo, url, subject);
			return getDetails;
		}

		return null;
	}

	@Transactional
	public String validateUser(User user) {

		System.out.println("goes inside validation method");

		User user2 = userDao.getUserByEmaiId(user.getEmail());
		if (user2 == null) {
			System.out.println("Email Id not found ");
		} else {
			System.out.println("comes again in validation method to check password and encrypted password");

			System.out.println("plain text" + user.getPassword());
			System.out.println("encrypted text" + user2.getPassword());

			if (BCrypt.checkpw(user.getPassword(),user2.getPassword())) {

				if(true)
				{
					String tokenGenerated = GenerateToken.generateToken(user2.getId());

					System.out.println("token successfully generated" + tokenGenerated);
					
					user.setEnabled(true);
					return tokenGenerated;
				}
				
			} else {
				System.out.println("token and actual password does  not match");
			}
		}
		return null;
	}

	@Transactional
	public boolean isEmailIdPresent(String emailId) {

		List<User> userlist = userDao.checkEmailId(emailId);
		if (userlist.size() != 0) {
			return true;
		}
		return false;
	}

}
