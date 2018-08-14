package com.bridgeit.fundooNote.collaborator.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.bridgeit.fundooNote.noteservice.model.Note;
import com.bridgeit.fundooNote.userservice.model.User;

@Entity
public class Collaborator {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String collaboratorId;
	
	@OneToMany
	@JoinColumn(name="ownerId")
	private User ownerId;
	
	@OneToMany
	@JoinColumn(name="shareUserId")
	private User shareUserId;
	
	@OneToMany
	@JoinColumn(name="noteId")
	private Note noteId;
	
	public String getCollaboratorId() {
		return collaboratorId;
	}
	public void setCollaboratorId(String collaboratorId) {
		this.collaboratorId = collaboratorId;
	}
	public User getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(User ownerId) {
		this.ownerId = ownerId;
	}
	public User getShareId() {
		return shareUserId;
	}
	public void setShareId(User shareUserId) {
		this.shareUserId = shareUserId;
	}
	public Note getNoteId() {
		return noteId;
	}
	public void setNoteId(Note noteId) {
		this.noteId = noteId;
	}
	
	
	

}
