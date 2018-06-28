package com.bridgeit.fundooNote.noteservice.service;

import java.util.List;

import com.bridgeit.fundooNote.noteservice.model.Note;
import com.bridgeit.fundooNote.noteservice.model.NoteDto;

public interface INoteService {

	public long addNote(Note note );
	public void deleteNode(long id);
	public void updateNode(NoteDto note,long id);
	public List<Note> displayAllNote(Note note);
}
