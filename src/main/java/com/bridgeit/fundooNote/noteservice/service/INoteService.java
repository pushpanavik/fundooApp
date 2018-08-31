package com.bridgeit.fundooNote.noteservice.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bridgeit.fundooNote.noteservice.model.Note;
import com.bridgeit.fundooNote.userservice.model.User;

/**
 @author   Pushpa Navik
 @version 1.0
 */
public interface INoteService {

	/**
	 * Save the note in the database
	 * @param note new note
	 * @param token for identifiction of user who has created that note
	 */
	public long addNote(Note note,String token );
	/**
	 * Delete the note from the database
	 * @param id of the  note to be deleted
	 * @param userid of the user
	 */
	public void deleteNode(int id);
	
	/**
	 * update the node in the database
	 * @param extract the id of the user from the note 
	 * @param  extract the id of the user froim token and match it with the user id within the note
	 */
	public void updateNode(Note note,String token);
	
	/**
	 * get all the notes from the database using token 
	 * @param token
	 * @return the list of note
	 */
	public List<Note> displayAllNote(String token);
	/**
	 * add the label on note 
	 * @param using noteid and label id add the note
	 */
	public void noteLabel(int noteid, int labelid);
	/**
	 * delete the label from the note
	 * @param noteid to detemine which label is to be dleted 
	 * @param labelid to detemine which label is to be dleted 
	 * @return true if label is dleted
	 */
	public boolean deleteLabel(int noteid, int labelid);
	
	/**
	 * Store the image file in Multipart file
	 * @param file
	 * @return
	 */
	String storeServerSideImage(MultipartFile file);
	
	/**
	 * Get the image to upload 
	 * @param name
	 * @return
	 */

	byte[] toGetImage(String name);
	 /**
	  * add colloborator on note
	  * @param userid for identification of user
	  * @param noteid by which user will be identified who has created it
	  */
	public void addCollaboratorOnNote(int userid, int noteid);
	/**
	 * to delete the colloborator from  the note
	 * @param userid for identification of user
	 * @param oteid by which user will be identified who has created it
	 * @return
	 */
	public boolean removeCollaboratorOnNote(int userid, int noteid);
	
	/**
	 * to get all colloborated notes using token
	 * @param token
	 * @return
	 */
	public List<Note> getAllCollaboratedNotes(String token);
	
	/**
	 * delete trash after specific interval period of time
	 */
	public void deleteTrashNotes();
	
	/**
	 * Checking if user on note is already exist	
	 * @param userid
	 * @return true if exist
	 */
	public boolean checkIfEmailIdOnNoteAlreadyExist(int userid);
	
	/**
	 * Get All Collaborated User based on userid
	 * @param id
	 * @return
	 */
	public List<User> getAllCollaboratedUsers(int id);
	

	
}
	
