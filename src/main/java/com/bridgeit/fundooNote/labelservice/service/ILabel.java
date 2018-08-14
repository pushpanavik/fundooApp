package com.bridgeit.fundooNote.labelservice.service;

import java.util.List;
import com.bridgeit.fundooNote.labelservice.model.Label;
import com.bridgeit.fundooNote.noteservice.model.Note;


public interface ILabel {

	public long addLabel(Label label,String token);
	public void deleteLabel(int id);
	public void updateLabel(Label label,String token);
	public List<Label> displayAllLabel(String token);
	public List<Note> getlabelNotes(int id, String token);
}
