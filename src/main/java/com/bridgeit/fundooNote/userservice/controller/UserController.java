package com.bridgeit.fundooNote.userservice.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.fundooNote.exceptionservice.EmailAlreadyExistException;
import com.bridgeit.fundooNote.exceptionservice.EmailIdNotPresentException;
import com.bridgeit.fundooNote.userservice.model.ResetPasswordDto;
import com.bridgeit.fundooNote.userservice.model.User;
import com.bridgeit.fundooNote.userservice.model.UserDto;
import com.bridgeit.fundooNote.userservice.service.IUserService;
import com.bridgeit.fundooNote.utilservice.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@PropertySource(value = { "classpath:application.properties" })
@Api
@RestController
public class UserController {
 
	@Value("${login}")
	String login;
	@Value("${resetPassword}")
	String resetPassword;
	@Value("${dashboard}")
	String dashboard;
	private static Logger logger=LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private IUserService userservice;
   
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
		
	@ApiOperation(value="register new user")
	@RequestMapping(value="user/registerUser", method=RequestMethod.POST)
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
					UserDto user2=new UserDto();
					user2.setFirstname(user.getFirstname());
					user2.setLastname(user.getLastname());
					user2.setEmailId(user.getEmailId());
					user2.setUserId(user.getUserId());
					user2.setImage(user.getProfilepicImage());
									
					return new  ResponseEntity<>(new Response("User successfully resgistered" +user2,100),HttpStatus.CREATED);
				}
	}
	
	@ApiOperation(value="login User")
	@RequestMapping(value="/user/login", method=RequestMethod.POST)
	public ResponseEntity<?> loginUser(@RequestBody User user,HttpServletRequest request,HttpServletResponse response){		
		System.out.println("comes under login method");
		
		user.setEnabled(false);
		
		
		if(userservice.isEmailIdPresent(user.getEmailId())) {
		
			String token=userservice.validateUser(user);
			
				if(token!=null)
				{
					logger.info("logged In successsfully");
					logger.info("your token generated is" +token);
												
					return new ResponseEntity<>(new Response(token, 200),HttpStatus.ACCEPTED);
				}
		}
		else
		{
			return new ResponseEntity<>(new Response("Invalid username or password",-101),HttpStatus.NOT_FOUND);
		}
		return null;
		
		
	}
	
	@ApiOperation(value="get token ")
	@RequestMapping(value="/user/tokenvalue/{token:.+}",method=RequestMethod.GET)
	public ResponseEntity<?> token(@PathVariable("token") String token,HttpServletResponse response,HttpServletRequest req){
		System.out.println("user clicks the link");
		userservice.activateUser(token);
		String url=req.getRequestURL().toString().substring(0, req.getRequestURL().lastIndexOf("/")-15);
		System.out.println("get token url"+url);
		
		try {
			response.sendRedirect(login);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(token,HttpStatus.FOUND);
	}
	
	@RequestMapping(value="/user/resetPwd/{token:.+}",method=RequestMethod.GET)
	public ResponseEntity<?> redirectTo(@PathVariable("token") String token,HttpServletResponse response,HttpServletRequest request){
		String url=request.getRequestURL().toString().substring(0, request.getRequestURL().lastIndexOf("/")-14);
		System.out.println(url);
		try {
			
			response.sendRedirect(resetPassword+ "/?token=" +token );
			//System.out.println(resetPassword+ "/?token=" +token);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return new ResponseEntity<>(token,HttpStatus.ACCEPTED);
		
	}
	
	@RequestMapping(value="/user/loginRedirect", method=RequestMethod.GET)
	public ResponseEntity<?> loginRedirect(HttpServletResponse response){
		try {
			response.sendRedirect(dashboard);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return new ResponseEntity<>("redirecting to login",HttpStatus.CONTINUE);
	}
	
	@ApiOperation(value="forgot password")
	@RequestMapping(value="/user/forgotPassword" ,method=RequestMethod.POST)
	public ResponseEntity<?>forgotPassword(@RequestBody User user,HttpServletRequest request,String token)
	{
		System.out.println("frontend comes under backend");
		if(userservice.isEmailIdPresent(user.getEmailId()))	{
			if(userservice!=null)
			logger.info("email already exist");
			System.out.println("then FE goes inside forgot method");
				boolean status=userservice.forgotPassword(user, request);
				System.out.println("after entering under backend check for email id ");
			if(status==true)
			{
				logger.info("ok confirmation done,continue to reset password");
				return new ResponseEntity<>(HttpStatus.ACCEPTED);
			}
			else
			{
				try {
					throw new EmailIdNotPresentException("Email id not present");
				}catch(EmailIdNotPresentException e) {
					logger.info("email not present exception");
				}
			}
		}
		return new ResponseEntity<>(HttpStatus.OK);		
	}
	
	
	
	@ApiOperation(value="reset password")
	@RequestMapping(value="user/resetPassword", method=RequestMethod.POST)
	public  ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDto reset,HttpServletResponse response,@RequestHeader("token")String token){
		System.out.println("user clicks the link for reset password");
		
		String newPassword = reset.getNewpassword();
		userservice.resetPassword(newPassword,token,reset);
				
		return new ResponseEntity<>("token is send",HttpStatus.CREATED);
	}
	
	
	@ApiOperation(value = "retrieve user info ")
	@GetMapping("getUser")
	public ResponseEntity<?> displayUser(@RequestHeader("token")String token)
	{ 
	
		
		User  userdetail = userservice.displayUserDetails(token);
					System.out.println("from backend==========>"+userdetail.getProfilepicImage());
		
		return new ResponseEntity<>( userdetail,HttpStatus.OK);
		
	}
	@ApiOperation(value="retrieve list of users")
	@GetMapping("getAllUsers")
	public ResponseEntity<?> getAllUsers(){
		List<User> user = userservice.displayAllUser();
		
		for(User user2 : user) {
			System.out.println("user info  "+user2);
			
			System.out.println("from backend of user info==========>"+user2);
		}
		return new ResponseEntity<>( user,HttpStatus.OK);
	}
	@ApiOperation(value = "update user ")
	@RequestMapping(value="updateUser", method=RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@RequestBody User user,@RequestHeader("token")String token){	
		userservice.updateUserDetails(user,token);
				return new ResponseEntity<>(new Response("user successfully updated",200),HttpStatus.ACCEPTED);
		
	}
}
