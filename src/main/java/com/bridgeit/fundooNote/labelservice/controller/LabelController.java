package com.bridgeit.fundooNote.labelservice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class LabelController {
	
	
	@Autowired
	private ILabel labelService;

	@ApiOperation(value = "add Label ")
	@RequestMapping(value="/user/addLabel", method=RequestMethod.POST)
	public ResponseEntity<?> addLabel( @RequestBody  Label label,HttpServletRequest request,@RequestHeader("token") String token)
	{
		
		boolean labelStatus	=ValidateLabel.validateLabel(label);
		if(labelStatus==true) {
			labelService.addLabel(label, token);
			if(labelService !=null)
			{
			
			return new ResponseEntity<>(new Response("Label created successfully",200),HttpStatus.CREATED);
			}
		}
		else {
		return new ResponseEntity<>(new Response("label cannot be created",-200),HttpStatus.EXPECTATION_FAILED);
		}
		return null;
	}
	
	@ApiOperation(value = "update label ")
	@RequestMapping(value="user/updateLabel", method=RequestMethod.PUT)
	public ResponseEntity<?> updateLabel(@RequestBody Label label,@RequestHeader("token")String token){
	
		  System.out.println("Updating User label : " + label.getName());
		labelService.updateLabel(label,token);
		
		return new ResponseEntity<>(new Response("Label Succesfully updated",5),HttpStatus.ACCEPTED);
		
	}
	
	@ApiOperation(value = "delete label ")
	@RequestMapping(value="user/delete/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteLabel( @PathVariable("id") int id){
		
		labelService.deleteLabel(id);
		
		return new ResponseEntity<>(new Response("Specified label successfully deleted",201),HttpStatus.NO_CONTENT);	
	}
	
	@ApiOperation(value = "retrieve all label ")
	@GetMapping("user/displayLabel")
	public ResponseEntity<?> ListLabel(@RequestHeader("token")String token)
	{ 
		List<Label> not = labelService.displayAllLabel(token);
		return ResponseEntity.ok().body(not);
		
	}
	@ApiOperation(value="label and note id")
	@PostMapping("user/labelNote/{id}/{id1}")
	public ResponseEntity<?> LabelWithNote(@PathVariable("id1")int id1,@PathVariable("id")int id){
		
		//labelService.checkNoteLabelId(id1,id);
		return new ResponseEntity<>(new Response("label and note id",100),HttpStatus.OK);
	}
	
	@ApiOperation(value=" labelNote id")
	@RequestMapping(value="user/listlabelNote/{id}", method=RequestMethod.GET)
	public ResponseEntity<List<Note>> listAllLabelNote(@RequestHeader("token") String token,@PathVariable("id")int id, Label label,HttpServletRequest req){
		
		System.out.println("Inside label note controller");
		List<Note> notes=labelService.getlabelNotes(id,token);
				
		return new ResponseEntity<>(notes,HttpStatus.OK);
	}
}
