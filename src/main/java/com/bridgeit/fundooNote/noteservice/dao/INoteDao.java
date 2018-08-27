package com.bridgeit.fundooNote.noteservice.dao;

import java.util.List;

import com.bridgeit.fundooNote.noteservice.model.Note;
import com.bridgeit.fundooNote.userservice.model.User;


public interface INoteDao {
	public long addNote(Note note,User user);
	User getUserById(int userId);
	public void deleteNode(int id);
	public void updateNode(Note notes);
	public Note getNoteById(int i);
	public List<Note> displayAllNote(User user);
	public boolean isNotewiththatIdExist(int getId);
	
	
}
