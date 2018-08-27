package com.bridgeit.fundooNote.noteservice.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgeit.fundooNote.exceptionservice.NoteWiththatIdNotFoundException;
import com.bridgeit.fundooNote.exceptionservice.TokenNotFound;
import com.bridgeit.fundooNote.jsoup.UrlData;
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
		String contentData=note.getDescription();
		String space=" ";
		String [] splitArray=contentData.split(" ");
	
		for(int i=0;i<1;i++)
		{
			System.out.println(splitArray[i]);
			List<String> list=new ArrayList<>();
			list.add(splitArray[i]);
		}
		System.out.println(space);
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
		
//		int getId=VerifyJwtToken.getId(token);
//				
//		System.out.println("user id      : "+getId); 
//		System.out.println("pin stattus================"+note.isPin());
//		System.out.println("archive status=============="+note.isArchive());
//		System.out.println("trash status============"+note.isTrash());
//		System.out.println("user note id : "+note.getId());
//		if(getId==note.getUser().getUserId())
//		{
//						noteDao.updateNode(note);
//		}
		int getId=note.getId();
		System.out.println("note id-------------->" +getId);
		Note note2=noteDao.getNoteById(getId);
		
		int userid=VerifyJwtToken.getId(token);
		System.out.println("user id--------------------->" +userid);
		
		boolean status=noteDao.isNotewiththatIdExist(getId);
		if(status==true) {
			
			System.out.println("user id in the note------------>"+ note2.getUser().getUserId());
			System.out.println();
			if(note2.getUser().getUserId()==userid) {
				System.out.println();
				note2.setId(note.getId());
				note2.setTitle(note.getTitle());
				note2.setDescription(note.getDescription());
				note2.setImage(note.getImage());
				note2.setArchive(note.isArchive());
				note2.setPin(note.isPin());
				note2.setTrash(note.isTrash());
				note2.setCollaboratedUser(note.getCollaboratedUser());
				note2.setColor(note.getColor());
				note2.setLastupdatedAt(note.getLastupdatedAt());
				note2.setListOfLabels(note.getListOfLabels());
				note2.setCreatedAt(note.getCreatedAt());
				note2.setUser(note.getUser());
				note2.setReminderDate(note.getReminderDate());
				note2.setUrlTitle(note.getUrlTitle());
				note2.setUrlImage(note.getUrlImage());
				note2.setUrlDomain(note.getUrlDomain());
				
			noteDao.updateNode(note2);
			}
			else {
				
				note2.setTitle(note.getTitle());
				note2.setDescription(note.getDescription());
				noteDao.updateNode(note2);
		}
		}
		
		else
		{
			try {
				throw new NoteWiththatIdNotFoundException("note with that id not found");
			}catch(NoteWiththatIdNotFoundException e) {
				e.printStackTrace();
			}
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

	@Transactional
	@Override
	public String storeServerSideImage(MultipartFile file) {
		
		try {

			byte[] bytes = file.getBytes();

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
       
		try {
		 return Files.readAllBytes(serverFile.toPath());
			
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	@Transactional
	@Override
	public void addCollaboratorOnNote(int userid, int noteid) {
		
		Note note = noteDao.getNoteById(noteid);
		User user = noteDao.getUserById(userid);
		
		List<User> collaboratedUser =  note.getCollaboratedUser();
		collaboratedUser.add(user);
		note.setCollaboratedUser(collaboratedUser);
		
		List<Note> collaboratedNotes = user.getCollaboratorNotes();
		collaboratedNotes.add(note);
		user.setCollaboratorNotes(collaboratedNotes);
		
		userDao.updateRecord(user);
		noteDao.updateNode(note);
		
	}

	@Transactional
	@Override
	public boolean removeCollaboratorOnNote(int userid, int noteid) {
		System.out.println("Entering in to the note label service");
		Note note = noteDao.getNoteById(noteid);
		System.out.println("Note in collaborator:"+note);

		User user = userDao.getUserById(userid);
		System.out.println("user in collaborator:"+user);
		
		List<User> collaboratorUser =  note.getCollaboratedUser();
		for(User user2:collaboratorUser) {
			if(userid == user2.getUserId()) {
				collaboratorUser.remove(user2);
				break;
			}
		}
	    note.setCollaboratedUser(collaboratorUser);
	
		List<Note> collaboratedNotes = user.getCollaboratorNotes();
		for(Note note2 :collaboratedNotes) {
			if(noteid == note2.getId()) {
				collaboratedNotes.remove(note2);
				break;
			}
		}
		user.setCollaboratorNotes(collaboratedNotes);
		
		userDao.updateRecord(user);
		noteDao.updateNode(note);
		return true;
	}

	@Transactional
	@Override
	public List<Note> getAllCollaboratedNotes(String token) {
		 
		
		int id = VerifyJwtToken.getId(token);
		System.out.println("tokenid===========>" +id);
		User user2=noteDao.getUserById(id);
		List<Note> listofCollaboratedNotes = user2.getCollaboratorNotes();
		//listofCollaboratedNotes.add(e)
		for(Note note3: listofCollaboratedNotes) {
			System.out.println(note3.getCollaboratedUser());
		}
		return listofCollaboratedNotes;
	}

	

}