package com.bridgeit.fundooNote.labelservice.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgeit.fundooNote.labelservice.dao.ILabelDao;
import com.bridgeit.fundooNote.labelservice.model.Label;
import com.bridgeit.fundooNote.labelservice.model.LabelDto;

@Service
public class LabelServiceImpl implements ILabel {

	@Autowired
	private ILabelDao labelDao;

	@Transactional
	@Override
	public long addLabel(Label label) {
	
		return labelDao.addLabel(label);
	}

	@Override
	public void updateLabel(LabelDto label,long id) {
		
		labelDao.updateLabel(label, id);
	}

	@Override
	public void deleteLabel(long id) {
		labelDao.deleteLabel(id);
		
	}

	
	
	

}
