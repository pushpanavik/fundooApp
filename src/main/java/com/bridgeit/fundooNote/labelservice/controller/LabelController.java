package com.bridgeit.fundooNote.labelservice.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.fundooNote.labelservice.model.Label;
import com.bridgeit.fundooNote.labelservice.service.ILabel;
import com.bridgeit.fundooNote.utilservice.Response;

@RestController
public class LabelController {
	
	private static Response response;
	
	@Autowired
	private ILabel labelService;

	
	@RequestMapping(value="/addLabel", method=RequestMethod.POST)
	public ResponseEntity<?> addLabel( Label label,HttpServletRequest request)
	{
		labelService.addLabel(label);
		if(labelService !=null)
		{
			response=new Response();
			response.setStatus(100);
			return new ResponseEntity<>("Label created successfully",HttpStatus.CREATED);
		}
		else {
		return new ResponseEntity<>("label cannot be created",HttpStatus.EXPECTATION_FAILED);
		}
	}
	/*@RequestMapping(value="/update/{id}", method=RequestMethod.PUT)
	public ResponseEntity<?> updateLabel(@RequestBody LabelDto labelDto, @PathVariable("id") long id,HttpServletRequest request){
	
		labelService.updateLabel(labelDto, id);
		
		return new ResponseEntity<String>("Label Succesfully updated",HttpStatus.ACCEPTED);
		
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteLabel( @PathVariable("id") long id){
		
		labelService.deleteLabel(id);
		response.setMsg("successfully deleted label");
		return new ResponseEntity<String>("Specified label successfully deleted",HttpStatus.NO_CONTENT);	
	}*/
	
	/*@GetMapping("/displayNote")
	public ResponseEntity<?> ListNote(Note note)
	{ 
		List<Note> not = labelService.displayAllNote(note);
		return ResponseEntity.ok().body(not);
		
	}*/
}
