package com.bridgeit.fundooNote.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.bridgeit.fundooNote.noteservice.service.INoteService;

@Configuration
@EnableScheduling
public class TrashOnNoteDeleter {

	@Autowired
	private INoteService noteService;
	@Scheduled(fixedRate = 1000)
	public void scheduleFixedRateTask() {
	      
	    noteService.deleteTrashNotes();
	}
	
}
