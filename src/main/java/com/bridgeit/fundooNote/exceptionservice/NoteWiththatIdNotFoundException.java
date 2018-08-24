package com.bridgeit.fundooNote.exceptionservice;

public class NoteWiththatIdNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	public NoteWiththatIdNotFoundException (String message)
	{
		super(message);
		
	}
}
