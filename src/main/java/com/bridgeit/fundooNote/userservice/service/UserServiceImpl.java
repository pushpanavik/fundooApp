package com.bridgeit.fundooNote.userservice.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgeit.fundooNote.configuration.MessageSender;
import com.bridgeit.fundooNote.exceptionservice.EmailIdNotPresentException;
import com.bridgeit.fundooNote.exceptionservice.TokenNotFound;
import com.bridgeit.fundooNote.noteservice.model.Note;
import com.bridgeit.fundooNote.userservice.dao.IUserDao;
import com.bridgeit.fundooNote.userservice.dao.RedisDao;
import com.bridgeit.fundooNote.userservice.model.EmailDto;
import com.bridgeit.fundooNote.userservice.model.ResetPasswordDto;
import com.bridgeit.fundooNote.userservice.model.User;
import com.bridgeit.fundooNote.userservice.model.UserDto;
import com.bridgeit.fundooNote.userservice.model.Validation;
import com.bridgeit.fundooNote.utilservice.GenerateToken;
import com.bridgeit.fundooNote.utilservice.VerifyJwtToken;

@Service
public class UserServiceImpl implements IUserService {

	private static Logger logger=LoggerFactory.getLogger(UserServiceImpl.class);
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
			System.out.println(user.getPassword());
			String generataHash = encoder.encode(user.getPassword());
			
			System.out.println(user.getPassword());
			user.setPassword(generataHash);
			int id = userDao.addUser(user);
			
			String token = GenerateToken.generateToken(id);
			System.out.println("my Token.... " + token);

			int id1 = VerifyJwtToken.getId(token);
			
			System.out.println("My id via JWT token..." + id1);

	
			String url=req.getRequestURL().toString().substring(0, req.getRequestURL().lastIndexOf("/"))
			+ "/tokenvalue/" + token ;
			System.out.println("This is required url" +url);
			
			emailDto.setMailto(user.getEmailId());
			emailDto.setSubject("click the link to activate your acount");
			emailDto.setUrl(url);
		
			messageSender.sendMessage(emailDto);
			System.out.println("Email Send Successfully");
			redisCache.saveToken((Integer.toString(id1)),token);
			return getDetails;
		}

		return null;
	}

	@Transactional
	public String validateUser(User user) {

		System.out.println("goes inside validation method");
			System.out.println(user.getEmailId());
		User user2 = userDao.getUserByEmaiId(user.getEmailId());
		if (user2 == null) {
			System.out.println("user 2 NullPointer Exception ");
		} else {
			System.out.println("comes again in validation method to check password and encrypted password");

			logger.info("plain text" + user.getPassword());
			logger.info("encrypted text" + user2.getPassword());
			
			String generatedhash=encoder.encode(user.getPassword());
			System.out.println("generatedhash for user is" +generatedhash );
			System.out.println("db passwrd " +user2.getPassword());
						
			if (BCrypt.checkpw(user.getPassword(),user2.getPassword())) {

				if(true)
				{
					user.setEnabled(true);
					String tokenGenerated = GenerateToken.generateUserToken(user2.getFirstname(), user2.getLastname(), user2.getUserId(), user2.getEmailId());

					logger.info("token successfully generated" + tokenGenerated);
									
					return tokenGenerated;
				}
				
			} else {
				logger.info("token and actual password does  not match");
			}
		}
		return null;
	}
	
	@Transactional
	public boolean isEmailIdPresent(String emailId) {

		List<User> userlist = userDao.checkEmailId(emailId);
		System.out.println("FEnd comes in the dao ");
		if (userlist.size() != 0) {
			System.out.println("returns true from dao");
			
			return true;
			
		}else {
			try {
				throw new EmailIdNotPresentException("emailid does not exist");
			} catch (EmailIdNotPresentException e) {
				
				logger.info("email id not  exist");
			}
			
		return false;
		
		}
	}
	@Transactional
	public boolean forgotPassword(User user, HttpServletRequest request) {

		
		User userInformation = userDao.getUserByEmaiId(user.getEmailId());
		System.out.println(user.getPassword());
		
		if(userInformation!= null) {

			
			String token = GenerateToken.generateToken(userInformation.getUserId());
			
			int id1 = VerifyJwtToken.getId(token);
		String url=request.getRequestURL().toString().substring(0, request.getRequestURL().lastIndexOf("/")-3)+"ser/resetPwd"+"/"+token;
			System.out.println(url);
					
			System.out.println("url inside forgot "+url);
			
			emailDto.setMailto(user.getEmailId());
			emailDto.setSubject("click on given link to reset your password ");
			emailDto.setUrl(url );
		
			messageSender.sendMessage(emailDto);
			logger.info("Email Send Successfully");
			
			redisCache.saveToken((Integer.toString(id1)),token);
			
			return true;
		}
		return false;
	}
	
	@Transactional
	public void resetPassword( String newPassword, String token,ResetPasswordDto reset) {
		
		int id = VerifyJwtToken.getId(token);
		String  getredisToken=redisCache.getToken((Integer.toString(id)));
		if(getredisToken.equals(token)) {
			
		User user1 = userDao.getUserById(id);
		String nPassword = reset.getNewpassword();
		String hashCodePassword = encoder.encode(nPassword);
		reset.setNewpassword(hashCodePassword);
		
		userDao.updateRecord(user1);
		logger.info("password reset successfully");
		
		
	}
	}

	@Transactional
	@Override
	public void activateUser(String token) {
		int id=VerifyJwtToken.getId(token);
		String  getredisToken=redisCache.getToken((Integer.toString(id)));
		if(getredisToken.equals(token)) {
			
		User user=userDao.getUserById(id);
		System.out.println(user);
		System.out.println("inside ctivate usder");
			user.setEnabled(true);
			userDao.updateRecord(user);	
		}
		
	}
	
	@Transactional
	public String VerifyLogin(String email,String password) {
		User user=userDao.getUserByEmaiId(email);
		String newtoken=" ";
		if(userDao==null) {
			throw new EmailIdNotPresentException("this email id is not registered");
		}
		else if(BCrypt.checkpw(password, user.getPassword()) && user.isEnabled())
		{
			return newtoken=GenerateToken.generateUserToken(user.getFirstname(), user.getLastname(), user.getUserId(), user.getEmailId());
			
		}
		return newtoken;
	}

	
	@Transactional
	@Override
	 public User displayUserDetails(String token) {
		int id=VerifyJwtToken.getId(token);
		 return userDao.getUserById(id);
		
	}

	@Transactional
	@Override
	public void updateUserDetails(User user, String token) {
		int getId=VerifyJwtToken.getId(token);
		
		
		if(getId==user.getUserId())
		{
			User user2= userDao.updateUserById(getId);
			user2.setProfilepicImage(user.getProfilepicImage());
			
		}
		
	}
	

	@Transactional
	@Override
	public List<User> displayAllUser() {
		List<User> listofUsers=userDao.getallUsers();
		
		return listofUsers;
		
	}
	
	
}
