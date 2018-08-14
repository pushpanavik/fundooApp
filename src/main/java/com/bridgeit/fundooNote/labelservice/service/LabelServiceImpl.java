package com.bridgeit.fundooNote.labelservice.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgeit.fundooNote.exceptionservice.TokenNotFound;
import com.bridgeit.fundooNote.labelservice.dao.ILabelDao;
import com.bridgeit.fundooNote.labelservice.model.Label;
import com.bridgeit.fundooNote.noteservice.model.Note;
import com.bridgeit.fundooNote.userservice.dao.IUserDao;
import com.bridgeit.fundooNote.userservice.model.User;
import com.bridgeit.fundooNote.utilservice.VerifyJwtToken;


@Service
public class LabelServiceImpl implements ILabel {

	@Autowired
	private ILabelDao labelDao;

	@Autowired
	private IUserDao userDao;

	@Transactional
	@Override
	public void updateLabel(Label label, String token) {
		int getId=VerifyJwtToken.getId(token);
		
		User user=userDao.getUserById(getId);
		Label label2=labelDao.getLabelById(label.getLabelId());
		label2.setName(label.getName());
		
		System.out.println("user id      : "+getId); 
		System.out.println("user info inside label     " +label.getUserDetails());
		System.out.println("user inside label id :          "+label.getUserDetails().getUserId());
		if(getId==label.getUserDetails().getUserId() && label.getUserDetails().getUserId()!=0)
		{
			labelDao.updateLabel(label2);
		}
	}



	@Transactional
	@Override
	public long addLabel(Label label, String token) {
		
		System.out.println("note token is" +token );
		
		int getId=VerifyJwtToken.getId(token);
		User user=	labelDao.getUserById(getId);
		
		label.setUserDetails(user);
		return labelDao.addLabel(label, user);
	}


	@Transactional
	@Override
	public void deleteLabel(int id) {
		labelDao.deleteLabel(id);
		
	}

	@Transactional
	@Override
	public List<Label> displayAllLabel(String token) {
		if(token==null) {
			try {
						throw new TokenNotFound("token not found exception");
			}
			catch(TokenNotFound t)
			{
				t.printStackTrace();
			}
			}
			else {
			User user=userDao.getUserById(VerifyJwtToken.getId(token));
			
			System.out.println("user "+user.getUserId());
			
		return	labelDao.getAllLabel(user.getUserId());
			}
			return null;
		
	}


	@Transactional
	@Override
	public List<Note> getlabelNotes(int id, String token) {
		
		Label labels=labelDao.getLabelById(id);
		List<Note> labelNote=labels.getNotes();
		return labelNote;
	}

	
	

}
