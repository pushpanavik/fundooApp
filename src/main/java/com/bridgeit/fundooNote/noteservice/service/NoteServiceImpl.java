package com.bridgeit.fundooNote.noteservice.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgeit.fundooNote.exceptionservice.NoteWiththatIdNotFoundException;
import com.bridgeit.fundooNote.exceptionservice.TokenNotFound;
import com.bridgeit.fundooNote.labelservice.dao.ILabelDao;
import com.bridgeit.fundooNote.labelservice.model.Label;
import com.bridgeit.fundooNote.noteservice.dao.INoteDao;
import com.bridgeit.fundooNote.noteservice.model.Note;
import com.bridgeit.fundooNote.noteservice.model.UrlData;
import com.bridgeit.fundooNote.userservice.dao.IUserDao;
import com.bridgeit.fundooNote.userservice.model.User;
import com.bridgeit.fundooNote.utilservice.GenerateToken;
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
	
	int getId=VerifyJwtToken.getId(token);
	User user=	noteDao.getUserById(getId);
	
	Set<UrlData> listofurlinfo = urlinfo(note.getDescription());
	

	System.out.println("user information from add note method" +user.getUserId());
	
	if (!listofurlinfo.isEmpty()) 
	{
		System.out.println("r1...");
		note.setUrls(listofurlinfo);
		for(UrlData list :listofurlinfo) {
			System.out.println(list.getId() +"  "+list.getDescription()+ "  "+ list.getImageUrl()+ "  "+ list.getTitle());
	
		}
		System.out.println("r2");
	}
	note.setUser(user);

	return noteDao.addNote(note,user);
	}


	private Set<UrlData> urlinfo(String description) {
		String urlPattern = "^((((https?|ftps?|gopher|telnet|nntp)://)|(mailto:|news:))(%[0-9A-Fa-f]{2}|[-()_.!~*';/?:@&=+$,A-Za-z0-9])+)([).!';/?:,][[:blank:]])?$";
		 description = description.replaceAll("(\r\n | \n)", "\\s");
			String[] descriptionArray = description.split("\\s+");

			UrlData urlinfo = null;
			Pattern p = Pattern.compile(urlPattern);
			
			Set<String> listofurl = new HashSet<String>();
			Set<UrlData> listofurlinfo = new HashSet<UrlData>();
			
			for(int i=0;i<descriptionArray.length;i++) {
				if(p.matcher(descriptionArray[i]).matches()) {
					listofurl.add(descriptionArray[i]);
				}	
			}
			
			for(String url :listofurl) {
				if (url != null) {
					Document doc;
					try {
						doc = Jsoup.connect(url).get();
					
						String urlTitle = doc.title();

						String urlDescription = url.split("://")[1].split("/")[0];
						String urlImage = doc.select("meta[property=og:image]").first().attr("content");
						if(urlImage==null ||urlImage!=null) {
						urlinfo = new UrlData();
						urlinfo.setUrl(url);
						urlinfo.setImageUrl(urlImage);
						urlinfo.setTitle(urlTitle);
						urlinfo.setDescription(urlDescription);
						
						//noteDao.updateUrlData(urlinfo);
						listofurlinfo.add(urlinfo);
						
						for(int i=0;i<listofurlinfo.size();i++)
						{
							System.out.println("Link Details : "+listofurlinfo.iterator().hasNext());
						}
						
						}
					}
				 catch (IOException e) {
						e.printStackTrace();
					}

				}
								
			}
		 
			return listofurlinfo;
	}




	@Transactional
	@Override
	public void updateNode(Note note,String token) {
		

		int getId=note.getId();
		System.out.println("note id-------------->" +getId);
		Note note2=noteDao.getNoteById(getId);
		
		int userid=VerifyJwtToken.getId(token);
		System.out.println("user id--------------------->" +userid);
		
		Set<UrlData> listofurlinfo = urlinfo(note.getDescription());
		if (!listofurlinfo.isEmpty()) {
			note.setUrls(listofurlinfo);
			for(UrlData list :listofurlinfo) {
				System.out.println(list);
			}
		}
		boolean status=noteDao.isNotewiththatIdExist(getId);
		if(status==true) 
		{
				if(note2.getUser().getUserId()==userid) 
				{
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
					
					noteDao.updateNode(note2);

			}
			else
			{
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

		User user = userDao.getUserById(userid);
		
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
	

	@Override
	@Transactional
	public List<User> getAllCollaboratedUsers(int id) {
		
		Note note = noteDao.getNoteById(id);
		return note.getCollaboratedUser();

	}

	@Transactional
	@Override
	public List<Note> getAllCollaboratedNotes(String token) { 
		
		int id = VerifyJwtToken.getId(token);
		User user2=noteDao.getUserById(id);
		
		List<Note> listofCollaboratedNotes = user2.getCollaboratorNotes();
	
		for(Note note3: listofCollaboratedNotes) {
			System.out.println(note3.getCollaboratedUser());
		}
		return listofCollaboratedNotes;
	}
	
	@Transactional
	@Override
	public void deleteTrashNotes() {
		noteDao.deleteTrashedNote();
		
	}

	@Transactional
	@Override
	public boolean checkIfEmailIdOnNoteAlreadyExist(int userid) {
	User user=noteDao.getUserById(userid);
	if(user==null) {
		return false;
	}
	else {
		return true;
	}
	}


	@Override
	public void removeUrl(String token, Note note, int id) {
		int userId;
		try {
			userId =VerifyJwtToken.getId(token);
			Note updatingnote = noteDao.getNoteById(note.getId());

			if (updatingnote.getUser().getUserId() == userId) {
				UrlData url = noteDao.getByUrlId(id);

				if (updatingnote.getUrls().contains(url)) {
					updatingnote.getUrls().remove(url);
					boolean status = noteDao.deleteUrl(url.getId());
					if (status) {
						noteDao.updateNode(updatingnote);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}

	
	

}