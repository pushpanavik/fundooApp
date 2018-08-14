package com.bridgeit.fundooNote.noteservice.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgeit.fundooNote.noteservice.model.Note;
import com.bridgeit.fundooNote.noteservice.model.resDTO;
import com.bridgeit.fundooNote.noteservice.service.INoteService;
import com.bridgeit.fundooNote.userservice.model.User;
import com.bridgeit.fundooNote.utilservice.Response;
import com.bridgeit.fundooNote.utilservice.ValidateNote;
import com.bridgeit.fundooNote.utilservice.VerifyJwtToken;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Handles requests for the application home page.
 */
@Api
@RestController
public class NoteController {
	
		
	private static final Logger logger = LoggerFactory.getLogger(NoteController.class);
	

	@Autowired
	private INoteService noteService;
	
	
	
	@ApiOperation(value = "add note ")
	@RequestMapping( value="user/addNote", method=RequestMethod.POST)
	public ResponseEntity<?> createNote(@RequestBody  Note note,HttpServletRequest request,@RequestHeader("token") String token ) {
		
	
	boolean noteStatus	=ValidateNote.validateNote(note);
	System.out.println(1);
	if(noteStatus==true)
	{
		System.out.println(5);
		System.out.println("note info from add method call"+note);
		long id=noteService.addNote(note,token);
		if(id!=0)
			
		return new ResponseEntity<>(new Response(token,201), HttpStatus.CREATED);
	}
	else
	{
		System.out.println(6);
		
		return new ResponseEntity<>(new Response("note cannot be added",-5),HttpStatus.NO_CONTENT);
	}
	return null;
	}
	@ApiOperation(value = "update note ")
	@RequestMapping(value="user/updateNote", method=RequestMethod.PUT)
	public ResponseEntity<?> updateNote(@RequestBody Note note,@RequestHeader("token")String token){
			
		System.out.println("token of updateNode is" +token);
		
		System.out.println("color : "+note.getReminderDate());
		
		note.setLastupdatedAt(new Date(System.currentTimeMillis()));
		noteService.updateNode(note,token);
		
		return new ResponseEntity<>(new Response("note successfully updated",200),HttpStatus.ACCEPTED);
		
	}
	@ApiOperation(value = "delete note ")
	@RequestMapping(value="user/deleteNote/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteNote(@PathVariable("id") int id){
		System.out.println("comes under deleted api");
		noteService.deleteNode(id);
		return new ResponseEntity<>(new Response("successfully note updated", 200),HttpStatus.NO_CONTENT);	
	}
	
	@ApiOperation(value = "retrieve all note ")
	@GetMapping("user/displayNote")
	public ResponseEntity<?> ListNote(@RequestHeader("token")String token)
	{ 
		List<Note> note = noteService.displayAllNote(token);
		List<resDTO> notedDtoList = new ArrayList<>();
		for(Note note1 : note) {
			System.out.println("note "+note);
			resDTO obj = new resDTO(note1);
			notedDtoList.add(obj);
			System.out.println("from backend==========>"+notedDtoList);
		}
		return new ResponseEntity<>( notedDtoList,HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "update note and label ")
	@PutMapping("user/updateNoteLabel/{id}/{id1}")
	public ResponseEntity<?> updateNoteLabel(@PathVariable("id")int noteid,@PathVariable("id1")int  labelid){
		System.out.println("label id"+labelid);
		System.out.println("note id:::" +noteid);
		
		noteService.noteLabel(noteid,labelid);
	return new ResponseEntity<>(new Response("label note is updated", 105),HttpStatus.OK);		
	}
	
	@ApiOperation(value="delete Lable inside note")
	@DeleteMapping("user/deleteLabel/{id1}/{id}")
	public ResponseEntity<?> deleteLabel(@PathVariable("id") int labelid, @PathVariable("id1") int noteid){
		if(noteService.deleteLabel(noteid,labelid)) {
			return new ResponseEntity<>(new Response("deleted successfully",120),HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(new Response("false so can't be deleted", -54),HttpStatus.EXPECTATION_FAILED);
	}
	
	
	@RequestMapping(value = "uploadFile", method = RequestMethod.POST)
	public ResponseEntity<?> uploadFile(@RequestBody MultipartFile file) throws IOException 
	{
		System.out.println("inside upload file");
		System.out.println("file is................" +file);
		String name = file.getOriginalFilename();
		System.out.println("file name is..........=> " +name);
	
		if (!file.isEmpty()) 
		{
   		 String path=noteService.storeServerSideImage(file);
   		 System.out.println("path : "+path);
	 	 logger.info("Server File Location with Name=" + path);
      	 return new ResponseEntity<>(new Response(path,100 ),HttpStatus.OK);
		} 
		else 
		{
		 return new ResponseEntity<Response>(new Response("false....You failed to upload " + name + " because the file was empty.",-5),HttpStatus.CONFLICT);
    	}
	}
	
	@RequestMapping(value = "image/{name:.+}", method = RequestMethod.GET)
	public ResponseEntity<?> showFile(@PathVariable("name")String name) 
	{
		System.out.println("comes under getImage");
		byte[] file=noteService.toGetImage(name);	
	
		for(byte str : file)
		{
	     System.out.println("image : "+str);		
		}
	
		System.out.println("file length : "+file.length);
		
		if(file.length==0)
		{
			return new ResponseEntity<Response>(new Response("false.....You failed to get Image" + name + " because the file was empty.",-40),HttpStatus.CONFLICT);	
		}
		
		return new ResponseEntity<>(file,HttpStatus.OK);
	}
	
}
