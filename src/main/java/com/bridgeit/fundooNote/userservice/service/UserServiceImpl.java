package com.bridgeit.fundooNote.userservice.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgeit.fundooNote.userservice.configuration.MessageSender;
import com.bridgeit.fundooNote.userservice.dao.IUserDao;
import com.bridgeit.fundooNote.userservice.dao.RedisDao;
import com.bridgeit.fundooNote.userservice.model.EmailDto;
import com.bridgeit.fundooNote.userservice.model.User;
import com.bridgeit.fundooNote.userservice.model.Validation;
import com.bridgeit.fundooNote.utilservice.GenerateToken;
import com.bridgeit.fundooNote.utilservice.VerifyJwtToken;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private MessageSender messageSender;
	
	@Autowired
	private EmailDto emailDto;

	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private RedisDao redisCache;
		
	@Transactional
	public String addUser(User user, HttpServletRequest req) {
		String getDetails = Validation.userValidation(user.getEmailId());

		
		if (getDetails != null) {
			String generataHash = encoder.encode(user.getPassword());
			user.setPassword(generataHash);
			int id = userDao.addUser(user);
			
			String token = GenerateToken.generateToken(id);
			System.out.println("my Token.... " + token);

			int id1 = VerifyJwtToken.getId(token);
			
			System.out.println("My id via JWT token..." + id1);

			String url="http://localhost:8080/fundoo/tokenvalue/"+token;
			emailDto.setMailto(user.getEmailId());
			emailDto.setSubject("click the link to activate your acount");
			emailDto.setUrl(url);
		
			messageSender.sendMessage(emailDto);
			System.out.println("Email Send Successfully");
			redisCache.addToken((Integer.toString(id1)),token);
			return getDetails;
		}

		return null;
	}

	@Transactional
	public String validateUser(User user) {

		System.out.println("goes inside validation method");

		User user2 = userDao.getUserByEmaiId(user.getEmailId());
		if (user2 == null) {
			System.out.println("Email Id not found ");
		} else {
			System.out.println("comes again in validation method to check password and encrypted password");

			System.out.println("plain text" + user.getPassword());
			System.out.println("encrypted text" + user2.getPassword());

			if (BCrypt.checkpw(user.getPassword(),user2.getPassword())) {

				if(true)
				{
					String tokenGenerated = GenerateToken.generateToken(user2.getUserId());

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
	@Transactional
	public boolean forgotPassword(User user, HttpServletRequest req) {

		User userInformation = userDao.getUserByEmaiId(user.getEmailId());
		
		if(userInformation!= null) {

			String token = GenerateToken.generateToken(userInformation.getUserId());
			int id1 = VerifyJwtToken.getId(token);
			
			String url="http://localhost:8080/fundoo/resetPassword/"+token;
					
			emailDto.setMailto(user.getEmailId());
			emailDto.setSubject("click on given link to rest your password ");
			emailDto.setUrl(url);
		
			messageSender.sendMessage(emailDto);
			
			System.out.println("Email Send Successfully");
			redisCache.addToken((Integer.toString(id1)),token);
			
			return true;
		}
		return false;
	}
	
	@Transactional
	public void resetPassword(HttpServletRequest request, String newPassword, String token) {
		
		int id = VerifyJwtToken.getId(token);
		String  getredisToken=redisCache.getToken((Integer.toString(id)));
		if(getredisToken.equals(token)) {
			
		User user = userDao.getUserById(id);
		String nPassword = user.getPassword();
		String hashCodePassword = encoder.encode(nPassword);
		user.setPassword(hashCodePassword);
		
		userDao.updateRecord(user);
		System.out.println("password reset successfully");
	}
	}

	@Transactional
	@Override
	public void activateUser(String token) {
		int id=VerifyJwtToken.getId(token);
		String  getredisToken=redisCache.getToken((Integer.toString(id)));
		if(getredisToken.equals(token)) {
			
		User user=userDao.getUserById(id);
			user.setEnabled(true);
			userDao.updateRecord(user);	
		}
		
	}
}
