package com.bridgeit.fundooNote.userservice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.fundooNote.userservice.model.User;
import com.bridgeit.fundooNote.userservice.service.IUserService;
import com.bridgeit.fundooNote.utilservice.EmailAlreadyExistException;
import com.bridgeit.fundooNote.utilservice.Response;

@RestController
public class UserController {
	
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
					return new ResponseEntity<>("Registration failed",HttpStatus.CONFLICT);
				}
				else {
					return new  ResponseEntity<>("User Registered Successfully",HttpStatus.OK);
				}
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ResponseEntity<?> loginUser(@RequestBody User user,HttpServletRequest request, HttpServletResponse response ){		
		System.out.println("comes under login method");
		Response res=new Response();
		user.setEnabled(false);
		String token=userservice.validateUser(user);
		
		if(token!=null)
		{
			System.out.println("logged In successsfully");
			System.out.println("your token generated is" +token);
			
			response.setHeader("Authorization", token);
			response.setStatus(200);
			res.setMsg("login Successful");	
			return new ResponseEntity<String>("Login succesful",HttpStatus.OK);
		}
		
		
		return new ResponseEntity<String>("Invalid username or password",HttpStatus.NOT_FOUND);
		
	}
	
	@RequestMapping(value="/tokenvalue/{token:.+}",method=RequestMethod.GET)
	public ResponseEntity<?> token(@PathVariable("token") String token){
		System.out.println("user clicks the link");
		userservice.activateUser(token);
		return new ResponseEntity<>(token,HttpStatus.OK);
	}
	
	@RequestMapping(value="/forgotPassword" ,method=RequestMethod.POST)
	public ResponseEntity<?>forgotPassword(@RequestBody User user,HttpServletRequest request,String token,String newPassword)
	{
		if(userservice.isEmailIdPresent(user.getEmailId()))
		{
				System.out.println("email already exist");
				boolean status=userservice.forgotPassword(user, request);
			if(status ==true)
			{
				return new ResponseEntity<>(HttpStatus.ACCEPTED);
			}	
		}
		return new ResponseEntity<>(HttpStatus.OK);		
	}
	
	@RequestMapping(value="/resetPassword/{token:.+}",method=RequestMethod.GET)
	public  ResponseEntity<?> resetPassword(@PathVariable("token") String token,HttpServletRequest request,@RequestBody User user){
		String newPassword = user.getPassword();
		userservice.resetPassword(request,newPassword,token);
		return new ResponseEntity<>(token,HttpStatus.CREATED);
	}
}
