package com.bridgeit.fundooNote.noteservice.model;

import java.util.Date;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.bridgeit.fundooNote.userservice.model.User;

public class resDTO {

private int id;
	
	private String title;
	private String description;
	private Date createdAt;
	
	private String color;
	private boolean archive;
	private boolean pin;
	private boolean trash;
	private Date lastupdatedAt;
	@ManyToOne
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="User_id")
	private User user;
	
	public resDTO () {};
	
	public resDTO(Note note1) {
				
		this.id=note1.getId();
		this.title=note1.getTitle();
		this.description=note1.getDescription();
		this.color=note1.getColor();
		this.setCreatedAt(note1.getCreatedAt());
		this.setArchive(note1.archive());
		this.setPin(note1.pin());
		this.setTrash(note1.trash());
		this.setLastupdatedAt(note1.getLastupdatedAt());
		this.setUser(note1.getUser());
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public boolean isArchive() {
		return archive;
	}
	public void setArchive(boolean archive) {
		this.archive = archive;
	}
	public boolean isPin() {
		return pin;
	}
	public void setPin(boolean pin) {
		this.pin = pin;
	}
	public boolean isTrash() {
		return trash;
	}
	public void setTrash(boolean trash) {
		this.trash = trash;
	}
	public Date getLastupdatedAt() {
		return lastupdatedAt;
	}
	public void setLastupdatedAt(Date lastupdatedAt) {
		this.lastupdatedAt = lastupdatedAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
