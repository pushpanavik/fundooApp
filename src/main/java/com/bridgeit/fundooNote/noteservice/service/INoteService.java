package com.bridgeit.fundooNote.noteservice.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bridgeit.fundooNote.noteservice.model.Note;

public interface INoteService {

	public long addNote(Note note,String token );
	public void deleteNode(int id);
	public void updateNode(Note note,String token);
	public List<Note> displayAllNote(String token);
	public void noteLabel(int noteid, int labelid);
	public boolean deleteLabel(int noteid, int labelid);
	String storeServerSideImage(MultipartFile file);

	byte[] toGetImage(String name);
}
