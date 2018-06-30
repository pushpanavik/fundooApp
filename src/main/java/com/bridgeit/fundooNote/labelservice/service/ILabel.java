package com.bridgeit.fundooNote.labelservice.service;

import java.util.List;

import com.bridgeit.fundooNote.labelservice.model.Label;
import com.bridgeit.fundooNote.labelservice.model.LabelDto;


public interface ILabel {

	public long addLabel(Label label);
	
	public void deleteLabel(long id);
	public void updateLabel(LabelDto note ,long id);
	/*public List<Label> displayAllNote(Label label);*/
}
