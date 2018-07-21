package com.bridgeit.fundooNote.noteservice.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgeit.fundooNote.noteservice.dao.INoteDao;
import com.bridgeit.fundooNote.noteservice.model.Note;
import com.bridgeit.fundooNote.userservice.model.User;
import com.bridgeit.fundooNote.utilservice.VerifyJwtToken;


@Service
public class NoteServiceImpl implements INoteService {

	@Autowired
	private INoteDao noteDao;
	
	@Transactional
	public long addNote(Note note,String token) {
	
		System.out.println("note token is" +token );
	int getId=VerifyJwtToken.getId(token);
	User user=	noteDao.getUserById(getId);
	note.setUser(user);
	noteDao.addNote(note);
	return noteDao.addNote(note);
	}

	@Transactional
	@Override
	public void updateNode(Note note,String token) {
		
		int getId=VerifyJwtToken.getId(token);
		System.out.println("user" +note.getUser());
		System.out.println("user id" +note.getUser().getUserId());
		if(getId==note.getUser().getUserId())
		{
			noteDao.updateNode(note);
		}
		}

	@Transactional
	@Override
	public void deleteNode(long id) {
		noteDao.deleteNode(id);
	}

	@Transactional
	@Override
	 public List<Note> displayAllNote(String token) {
		
		return noteDao.displayAllNote(token);
	}

	
}