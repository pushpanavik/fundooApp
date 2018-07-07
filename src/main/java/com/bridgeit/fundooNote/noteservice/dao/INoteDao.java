package com.bridgeit.fundooNote.noteservice.dao;

import java.util.List;

import com.bridgeit.fundooNote.noteservice.model.Note;
import com.bridgeit.fundooNote.userservice.model.User;


public interface INoteDao {
	public long addNote(Note note);
	User getUserById(int userId);
	public void deleteNode(long id);
	public void updateNode(Note notes ,String token);
	public Note getNoteById(long noteId);
	public List<Note> displayAllNote(String token);
}
