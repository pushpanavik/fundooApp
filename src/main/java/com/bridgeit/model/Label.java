package com.bridgeit.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

public class Label {
@Column
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private int labelId;
private String name;

@ManyToOne
private User user;

@ManyToMany
private Set<Note> note;

public int getLabelId() {
	return labelId;
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

public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}

public Set<Note> getNote() {
	return note;
}

public void setNote(Set<Note> note) {
	this.note = note;
}


}
