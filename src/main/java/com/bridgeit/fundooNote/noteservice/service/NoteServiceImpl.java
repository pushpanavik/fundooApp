package com.bridgeit.fundooNote.noteservice.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgeit.fundooNote.exceptionservice.TokenNotFound;
import com.bridgeit.fundooNote.noteservice.dao.INoteDao;
import com.bridgeit.fundooNote.noteservice.model.Note;
import com.bridgeit.fundooNote.userservice.dao.IUserDao;
import com.bridgeit.fundooNote.userservice.model.User;
import com.bridgeit.fundooNote.utilservice.VerifyJwtToken;


@Service
public class NoteServiceImpl implements INoteService {

	@Autowired
	private INoteDao noteDao;
	
	@Autowired
	private IUserDao userDao;
	
	@Transactional
	public long addNote(Note note,String token) {
	
		System.out.println("note token is" +token );
		
	int getId=VerifyJwtToken.getId(token);
	User user=	noteDao.getUserById(getId);
	
	note.setUser(user);
//	noteDao.addNote(note,user);
	return noteDao.addNote(note,user);
	}

	@Transactional
	@Override
	public void updateNode(Note note,String token) {
		
		int getId=VerifyJwtToken.getId(token);
				
		System.out.println("user id      : "+getId); 
		System.out.println("user info inside note " +note.getUser());
		System.out.println("user note id : "+note.getUser().getUserId());
		if(getId==note.getUser().getUserId())
		{
			noteDao.updateNode(note);
		}
	}

	@Transactional
	@Override
	public void deleteNode(int id) {
		noteDao.deleteNode(id);
	}

	@Transactional
	@Override
	 public List<Note> displayAllNote(String token) {
		if(token==null) {
		try {
					throw new TokenNotFound("token not found exception");
		}
		catch(TokenNotFound t)
		{
			t.printStackTrace();
		}
		}
		else {
		User user=userDao.getUserById(VerifyJwtToken.getId(token));
		
		System.out.println("user "+user.getUserId());
		
	return	noteDao.displayAllNote(user);
		}
		return null;
	}

	
}