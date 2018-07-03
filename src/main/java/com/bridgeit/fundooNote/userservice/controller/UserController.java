package com.bridgeit.fundooNote.userservice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.fundooNote.exceptionservice.EmailAlreadyExistException;
import com.bridgeit.fundooNote.userservice.model.ResetPasswordDto;
import com.bridgeit.fundooNote.userservice.model.User;
import com.bridgeit.fundooNote.userservice.service.IUserService;

@RestController
public class UserController {
	
	private static Logger logger=LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private IUserService userservice;
   
	
	@RequestMapping(value="/registerUser", method=RequestMethod.POST)
	public ResponseEntity<?> registerUser(@RequestBody User user,HttpServletRequest request)throws EmailAlreadyExistException
	{
		System.out.println(user.getEmailId()); 
		if(userservice.isEmailIdPresent(user.getEmailId()))
			{
				 	System.out.println("email already exists");	
				 	return new  ResponseEntity<>("User Already  Registered ",HttpStatus.CONFLICT);
			}
		
				if(userservice.addUser(user,request)==null)
				{
					logger.info("user already exist");
					return new ResponseEntity<>("Registration failed",HttpStatus.CONFLICT);
				}
				else {
					return new  ResponseEntity<>("User Registered Successfully",HttpStatus.OK);
				}
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ResponseEntity<?> loginUser(@RequestBody User user,HttpServletRequest request,HttpServletResponse response ){		
		System.out.println("comes under login method");
		
		user.setEnabled(false);
		
		if(userservice.isEmailIdPresent(user.getEmailId())) {
		
			String token=userservice.validateUser(user);
				if(token!=null)
				{
					logger.info("logged In successsfully");
					logger.info("your token generated is" +token);
					response.setHeader("Authorization", token);
							
					return new ResponseEntity<String>("Login succesful",HttpStatus.OK);
				}
		}
		else
		{
			return new ResponseEntity<String>("Invalid username or password",HttpStatus.NOT_FOUND);
		}
		return null;
		
		
	}
	
	@RequestMapping(value="/tokenvalue/{token:.+}",method=RequestMethod.GET)
	public ResponseEntity<?> token(@PathVariable("token") String token){
		System.out.println("user clicks the link");
		userservice.activateUser(token);
		return new ResponseEntity<>(token,HttpStatus.FOUND);
	}
	
	@RequestMapping(value="/forgotPassword" ,method=RequestMethod.POST)
	public ResponseEntity<?>forgotPassword(@RequestBody User user,HttpServletRequest request,String token,String newPassword)
	{
		if(userservice.isEmailIdPresent(user.getEmailId()))	{
			
			logger.info("email already exist");
				boolean status=userservice.forgotPassword(user, request);
			if(status ==true)
			{
				logger.info("ok confirmation done. continue to reset password");
				return new ResponseEntity<>(HttpStatus.ACCEPTED);
			}	
		}
		return new ResponseEntity<>(HttpStatus.OK);		
	}
	
	@RequestMapping(value="/resetPassword/{token:.+}",method=RequestMethod.POST)
	public  ResponseEntity<?> resetPassword(@PathVariable("token") String token,HttpServletRequest request,@RequestBody ResetPasswordDto reset){
		String newPassword = reset.getPassword();
		userservice.resetPassword(request,newPassword,token,reset);
		return new ResponseEntity<>(token,HttpStatus.CREATED);
	}
}
