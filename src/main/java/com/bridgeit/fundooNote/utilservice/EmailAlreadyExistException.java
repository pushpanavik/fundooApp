package com.bridgeit.fundooNote.utilservice;

public class EmailAlreadyExistException  extends Exception{
	
	
	private static final long serialVersionUID = 1L;

	public EmailAlreadyExistException(String msg)
	{
		super("Email Already exist");
	}
}