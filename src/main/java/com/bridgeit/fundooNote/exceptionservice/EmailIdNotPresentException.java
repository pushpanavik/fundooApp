package com.bridgeit.fundooNote.exceptionservice;

public class EmailIdNotPresentException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public EmailIdNotPresentException (String message)
	{
		super(message);
		
	}
	
}
