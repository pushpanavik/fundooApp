package com.bridgeit.fundooNote.labelservice.dao;

import com.bridgeit.fundooNote.labelservice.model.Label;
import com.bridgeit.fundooNote.labelservice.model.LabelDto;

public interface ILabelDao {

	public long addLabel(Label label);
	public void deleteLabel(long id);
	public void updateLabel(LabelDto labelDto ,long id);
}
