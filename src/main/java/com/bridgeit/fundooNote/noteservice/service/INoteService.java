package com.bridgeit.fundooNote.noteservice.service;

import java.util.List;

import com.bridgeit.fundooNote.noteservice.model.Note;

public interface INoteService {

	public long addNote(Note note,String token );
	public void deleteNode(long id);
	public void updateNode(Note note,String token);
	public List<Note> displayAllNote(String token);
}
