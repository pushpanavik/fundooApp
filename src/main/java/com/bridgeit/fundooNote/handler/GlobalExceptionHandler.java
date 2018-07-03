package com.bridgeit.fundooNote.handler;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bridgeit.fundooNote.exceptionservice.EmailAlreadyExistException;
import com.bridgeit.fundooNote.exceptionservice.UserNotFoundException;
import com.bridgeit.fundooNote.utilservice.ErrorMessage;

@ControllerAdvice
public class GlobalExceptionHandler {

	private ErrorMessage error;
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(EmailAlreadyExistException.class)
	public ResponseEntity<?> handleEmailException(HttpServletRequest request,Exception e,String email) {
	logger.warn("Email already Exist ");
	error.setErrorstatus(15);
	error.setErrorMsg("");
	return new ResponseEntity<>("Email already exist",HttpStatus.CONFLICT);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<?> handleUserNotFoundException(HttpServletRequest request,Exception e){
		logger.warn("User not found");
		error.setErrorstatus(-5);
		error.setErrorMsg("User not found");
		return new  ResponseEntity<>("User not found Exception",HttpStatus.NOT_FOUND);
	}
}
