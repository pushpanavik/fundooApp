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
import com.bridgeit.fundooNote.userservice.model.EmailDto;
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
	MessageSender messageSender;

	@Autowired
	private BCryptPasswordEncoder encoder;
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

			
			EmailDto emailDto=new EmailDto();
			emailDto.setMailTo(user.getEmailId());
			emailDto.setName(user.getFirstname()+user.getLastname());
			emailDto.setName(token);
			emailDto.setUrl("<a href='http://localhost:8080/token/tokenvalue/' ></a>");
			emailDto.setText("link to activate your account");
			 
			MessageSender.sendMessage(emailDto);
			
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
			String url = req.getRequestURL().toString().substring(0, req.getRequestURL().lastIndexOf("/")) + "/resetPasswordLink/" + token;	
			String mailTo = user.getEmailId();			
			String msg = "click on given link to rest yor password "+url;
			String subject = "reset password link";
			SendingMail.sendMail(mailTo, msg, subject);
			System.out.println("in forgot");

			return true;
		}
		return false;
	}
	
	@Transactional
	public String resetPassword(HttpServletRequest request, String newPassword, String token) {

		int id = VerifyJwtToken.getId(token);
		User user = userDao.getUserById(id);
		String nPassword = user.getPassword();
		String hashCodePassword = encoder.encode(nPassword);
		user.setPassword(hashCodePassword);
		
		userDao.updateRecord(user);
		System.out.println("password reset successfully");
		return null;
	}
}
