package com.bridgeit.fundooNote.exceptionservice;

public class TokenNotFound  extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public TokenNotFound (String message)
	{
		super(message);
		
	}
}
