package com.bridgeit.fundooNote.collaboratorservice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.fundooNote.collaboratorservice.model.Collaborator;
import com.bridgeit.fundooNote.collaboratorservice.service.ICollaborator;
import com.bridgeit.fundooNote.labelservice.model.Label;
import com.bridgeit.fundooNote.utilservice.Response;

@RestController
public class CollaboratorController {

	@Autowired
	private ICollaborator collaboratorservice;
	
	@RequestMapping(value="/addCollaborator" ,method = RequestMethod.POST)
	  public ResponseEntity<?> createlabel(@RequestBody Collaborator collaborator,HttpServletRequest req,@RequestHeader("token") String token)
	  {
		System.out.println("inside backend");
		  
		  int status = collaboratorservice.addCollaborator(collaborator, token);
			if (status > 0) {
				Response res=new Response();
				res.setMsg("Collaborator added Successfully");
				res.setStatus(200);
				return new ResponseEntity<>( res,HttpStatus.CREATED);

			}
			return new ResponseEntity<String>( "Collaborator cannot be added",HttpStatus.NOT_FOUND); 
	  }
	
	@RequestMapping(value="/getallCollaborators" ,method = RequestMethod.GET)
	  public ResponseEntity<List<Collaborator>> getAllCollaborators(HttpServletRequest req,@RequestHeader("token") String token)
	  {
		List<Collaborator> list=collaboratorservice.getAllCollaborators(token);  
		 return new ResponseEntity<List<Collaborator>>( list,HttpStatus.CREATED); 
		  
	  }
	
	 @RequestMapping(value="/deleteCollaborator/{id}",method=RequestMethod.POST)
	  public ResponseEntity<?> deleteNote(@PathVariable("id") int id,Label label,HttpServletRequest req,@RequestHeader("token") String token)
	  {
		  System.out.println("Going into the Controller...");
		  
		  collaboratorservice.deleteCollaborator(id, token);;
		  Response res = new Response(token,id);
			res.setMsg("Note deleteion is Done");
			res.setStatus(200);
		return new ResponseEntity<Response>(res,HttpStatus.CREATED);
	  }  
	
	

}
