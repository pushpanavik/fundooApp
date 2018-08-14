package com.bridgeit.fundooNote.noteservice.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgeit.fundooNote.exceptionservice.TokenNotFound;
import com.bridgeit.fundooNote.labelservice.dao.ILabelDao;
import com.bridgeit.fundooNote.labelservice.dao.LabelDaoImpl;
import com.bridgeit.fundooNote.labelservice.model.Label;
import com.bridgeit.fundooNote.noteservice.dao.INoteDao;
import com.bridgeit.fundooNote.noteservice.model.Note;
import com.bridgeit.fundooNote.userservice.dao.IUserDao;
import com.bridgeit.fundooNote.userservice.model.User;
import com.bridgeit.fundooNote.utilservice.VerifyJwtToken;


@Service
@PropertySource(value = { "classpath:image.properties" })
public class NoteServiceImpl implements INoteService {

	@Autowired
	private INoteDao noteDao;
	
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private ILabelDao labelDao;
	
	@Value("${image.path}")
	private String path;

	@Value("${response.path}")
	private String responsePath;
	
	@Transactional
	public long addNote(Note note,String token) {
	
		System.out.println("note token is" +token );
		
	int getId=VerifyJwtToken.getId(token);
	User user=	noteDao.getUserById(getId);
	
	note.setUser(user);
	System.out.println("user information from add note method" +user.getUserId());
//	noteDao.addNote(note,user);
	return noteDao.addNote(note,user);
	}

	@Transactional
	@Override
	public void updateNode(Note note,String token) {
		
		int getId=VerifyJwtToken.getId(token);
				
		System.out.println("user id      : "+getId); 
		System.out.println("user info inside note " +note.getReminderDate());
		System.out.println("user note id : "+note.getId());
		if(getId==note.getUser().getUserId())
		{
			userDao.updateUserById(getId);
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
		
		System.out.println(VerifyJwtToken.getId(token));
		
	return	noteDao.displayAllNote(user);
		}
		return null;
	}

	@Transactional
	@Override
	public void noteLabel(int noteid, int labelid) {
		
		System.out.println("noteid under noteLabel"+ noteid);
		System.out.println("labelid under noteLabel"+ labelid);
				
		Label labels=labelDao.getLabelById(labelid);
		Note note=noteDao.getNoteById(noteid);
		
		note.getListOfLabels().add(labels);
		labels.getNotes().add(note);
		
		labelDao.updateLabel(labels);
		noteDao.updateNode(note);
		
	}

	@Transactional
	@Override
	public boolean deleteLabel(int noteid, int labelid) {
		
		System.out.println("noteid under deleteLabel"+ noteid);
		System.out.println("labelid under deleteLabel"+ labelid);
		
		Note note=noteDao.getNoteById(noteid);
			
		Label label=labelDao.getLabelById(labelid);
		
		if(note==null && label==null) {
			
			return false;
			
		}
		else {
		note.getListOfLabels().remove(label);
		label.getNotes().remove(note);
		
		
		labelDao.updateLabel(label);
		noteDao.updateNode(note);
		return true;
		}
	}


	@Override
	public String storeServerSideImage(MultipartFile file) {
		System.out.println("file inside serverside image....=>" +file);

		try {

			byte[] bytes = file.getBytes();
			System.out.println("path : " + path + File.separator + file.getOriginalFilename());

			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(path + File.separator + file.getOriginalFilename()));
			stream.write(bytes);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return responsePath+file.getOriginalFilename();
	}

	@Override
	public byte[] toGetImage(String name) 
	{
		File serverFile = new File(path+File.separator+name);
		
		if (serverFile.exists()) {
            
		 System.out.println("r1");
		try {
		 return Files.readAllBytes(serverFile.toPath());
			
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

}