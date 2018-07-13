//package com.bridgeit.fundooNote.collaborator.controller;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.bridgeit.fundooNote.collaborator.model.Collaborator;
//import com.bridgeit.fundooNote.collaborator.service.ICollaboratorService;
//import com.bridgeit.fundooNote.utilservice.VerifyJwtToken;
//
//
//@RestController
//public class CollaboratorController {
//	
//	@Autowired
//	private ICollaboratorService collabaratorService;
//
//	@RequestMapping(value="/user/addCollaborator", method=RequestMethod.POST)
//	public ResponseEntity<?> createCollaborator(@RequestBody Collaborator collaborator,HttpServletRequest request){
//		int id=VerifyJwtToken.getId(request.getHeader("Author"));
//		
//		collabaratorService.addCollaborator(collaborator, id);
//		return new ResponseEntity<>("Collaborator created Successfully",HttpStatus.CREATED);
//		
//		
//	}
//}
