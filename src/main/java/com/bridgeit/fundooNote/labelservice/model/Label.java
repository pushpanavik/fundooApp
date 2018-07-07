package com.bridgeit.fundooNote.labelservice.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;

import com.bridgeit.fundooNote.noteservice.model.Note;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table
public class Label {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column
	private int labelId;
	
	@Column
	private String name;

	public int getLabelId() {
		return labelId;
	}

	@OneToMany(fetch = FetchType.LAZY)
	@NotFound
	@JsonView
	@JsonManagedReference
	private List<Note> notes=new ArrayList<>();

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public void setLabelId(int labelId) {
		this.labelId = labelId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}