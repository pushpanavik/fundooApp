package com.bridgeit.fundooNote.labelservice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.fundooNote.labelservice.model.Label;
import com.bridgeit.fundooNote.labelservice.model.LabelDto;
import com.bridgeit.fundooNote.labelservice.service.ILabel;
import com.bridgeit.fundooNote.noteservice.model.Note;
import com.bridgeit.fundooNote.utilservice.Response;
import com.bridgeit.fundooNote.utilservice.ValidateLabel;
import com.bridgeit.fundooNote.utilservice.ValidateNote;

@RestController
public class LabelController {
	
	
	@Autowired
	private ILabel labelService;

	
	@RequestMapping(value="/user/addLabel", method=RequestMethod.POST)
	public ResponseEntity<?> addLabel( @RequestBody  Label label,HttpServletRequest request,@RequestHeader("token") String token)
	{
		
		boolean labelStatus	=ValidateLabel.validateLabel(label);
		if(labelStatus==true) {
			labelService.addLabel(label, token);
			if(labelService !=null)
			{
			
			return new ResponseEntity<>("Label created successfully",HttpStatus.CREATED);
			}
		}
		else {
		return new ResponseEntity<>("label cannot be created",HttpStatus.EXPECTATION_FAILED);
		}
		return null;
	}
	@RequestMapping(value="user/update/{id}", method=RequestMethod.PUT)
	public ResponseEntity<?> updateLabel(@RequestBody LabelDto labelDto, @PathVariable("id") long id,HttpServletRequest request){
	
		labelService.updateLabel(labelDto, id);
		
		return new ResponseEntity<String>("Label Succesfully updated",HttpStatus.ACCEPTED);
		
	}
	
	@RequestMapping(value="user/delete/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteLabel( @PathVariable("id") long id){
		
		labelService.deleteLabel(id);
		
		return new ResponseEntity<>("Specified label successfully deleted",HttpStatus.NO_CONTENT);	
	}
	
	@GetMapping("user/displayLabel")
	public ResponseEntity<?> ListLabel(Label label)
	{ 
		List<Label> not = labelService.displayAllLabel(label);
		return ResponseEntity.ok().body(not);
		
	}
}
