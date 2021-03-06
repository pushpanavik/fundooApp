package com.bridgeit.fundooNote.noteservice.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgeit.fundooNote.noteservice.dao.INoteDao;
import com.bridgeit.fundooNote.noteservice.model.Note;
import com.bridgeit.fundooNote.noteservice.model.NoteDto;


@Service
public class NoteServiceImpl implements INoteService {

	@Autowired
	private INoteDao noteDao;
	
	@Transactional
	public long addNote(Note note) {
	return noteDao.addNote(note);
	}

	@Override
	public void updateNode(NoteDto note, long id) {
		noteDao.updateNode(note, id);
	}

	@Override
	public void deleteNode(long id) {
		noteDao.deleteNode(id);
	}

	@Override
	 public List<Note> displayAllNote(Note note) {
		
		return noteDao.displayAllNote(note);
	}

	
}