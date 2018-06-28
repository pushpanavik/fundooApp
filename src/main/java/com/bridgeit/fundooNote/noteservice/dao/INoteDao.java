package com.bridgeit.fundooNote.noteservice.dao;

import java.util.List;

import com.bridgeit.fundooNote.noteservice.model.Note;
import com.bridgeit.fundooNote.noteservice.model.NoteDto;
import com.bridgeit.fundooNote.utilservice.User;

public interface INoteDao {
	public long addNote(Note note);
	User getUserById(int userId);
	public void deleteNode(long id);
	public void updateNode(NoteDto note ,long id);
	public List<Note> displayAllNote(Note note);
}
