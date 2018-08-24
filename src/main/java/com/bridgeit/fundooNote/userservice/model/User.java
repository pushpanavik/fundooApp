package com.bridgeit.fundooNote.userservice.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.bridgeit.fundooNote.labelservice.model.Label;
import com.bridgeit.fundooNote.noteservice.model.Note;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class User {
	

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int userId;
	
	private String firstname;
	private String lastname;
	private String emailId;
	private String password;
	private String address;
	private String profilepicImage;
	
	@ManyToMany
	@LazyCollection(value = LazyCollectionOption.FALSE)
	@JsonIgnore
	@JoinTable(name="collaborator_Note",joinColumns =@JoinColumn( name="USER_Id"),inverseJoinColumns = @JoinColumn( name="Note_Id"))
	private List<Note> collaboratorNotes;
	
	public String getProfilepicImage() {
		return profilepicImage;
	}

	public List<Note> getCollaboratorNotes() {
		return collaboratorNotes;
	}

	public void setCollaboratorNotes(List<Note> collaboratorNotes) {
		this.collaboratorNotes = collaboratorNotes;
	}

	public void setProfilepicImage(String profilepicImage) {
		this.profilepicImage = profilepicImage;
	}

	@NotNull
	private long phoneNumber;
	private boolean enabled;
	
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="user",cascade=CascadeType.PERSIST)
	@JsonIgnore
	private List<Note> note = new ArrayList<Note>();
	
	@JsonIgnore
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="userDetails",cascade=CascadeType.PERSIST)
	private List<Label> listOfLabels = new ArrayList<Label>();
	
	
	public List<Label> getListOfLabels() {
		return listOfLabels;
	}

	public void setListOfLabels(List<Label> listOfLabels) {
		this.listOfLabels = listOfLabels;
	}

	public List<Note> getNote() {
		return note;
	}

	public void setNote(List<Note> note) {
		this.note = note;
	}

	public boolean isEnabled() {
		return enabled;
	}
	
	public User()
	{
		this.enabled=false;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	

}
