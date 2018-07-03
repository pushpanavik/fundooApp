package com.bridgeit.fundooNote.noteservice.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.fundooNote.noteservice.model.Note;
import com.bridgeit.fundooNote.noteservice.model.NoteDto;
import com.bridgeit.fundooNote.noteservice.service.INoteService;
import com.bridgeit.fundooNote.utilservice.ValidateNote;

/**
 * Handles requests for the application home page.
 */
@RestController
public class NoteController {
	
		
	private static final Logger logger = LoggerFactory.getLogger(NoteController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@Autowired
	private INoteService noteService;
	
	
	@Transactional
	@RequestMapping( value="/addNote", method=RequestMethod.POST)
	public ResponseEntity<?> createNote(@RequestBody  Note note,HttpServletRequest request,@RequestHeader("token")String token ) {
		
	
	boolean noteStatus	=ValidateNote.validateNote(note);
	System.out.println(1);
	if(noteStatus==true)
	{
		System.out.println(5);
		long id=noteService.addNote(note);
		if(id!=0)
			
		
		return new ResponseEntity<String>("Note successfully added", HttpStatus.CREATED);
	}
	else
	{
		System.out.println(6);
		
		return new ResponseEntity<String>("Note is Empty",HttpStatus.NO_CONTENT);
	}
	return null;
	}
	@RequestMapping(value="/update/{id}", method=RequestMethod.PUT)
	public ResponseEntity<?> updateNote(@RequestBody NoteDto note, @PathVariable("id") long id,HttpServletRequest request){
		//int userId=(Integer)request.getAttribute("userId");
		
		noteService.updateNode(note,id);
		
		return new ResponseEntity<String>("Note Succesfully updated",HttpStatus.ACCEPTED);
		
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteNote( @PathVariable("id") long id){
		
		noteService.deleteNode(id);
		
		return new ResponseEntity<String>("Specified note successfully deleted",HttpStatus.NO_CONTENT);	
	}
	
	@GetMapping("/displayNote")
	public ResponseEntity<?> ListNote(Note note)
	{ 
		List<Note> not = noteService.displayAllNote(note);
		return ResponseEntity.ok().body(not);
		
	}
	
}
