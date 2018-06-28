package com.bridgeit.dao;

import java.util.List;

import com.bridgeit.model.Note;
import com.bridgeit.model.NoteDto;
import com.bridgeit.model.User;

public interface INoteDao {
	public long addNote(Note note);
	User getUserById(int userId);
	public void deleteNode(long id);
	public void updateNode(NoteDto note ,long id);
	public List<Note> displayAllNote(Note note);
}
