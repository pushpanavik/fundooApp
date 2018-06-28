package com.bridgeit.service;

import java.util.List;

import com.bridgeit.model.Note;
import com.bridgeit.model.NoteDto;

public interface INoteService {

	public long addNote(Note note );
	public void deleteNode(long id);
	public void updateNode(NoteDto note,long id);
	public List<Note> displayAllNote(Note note);
}
