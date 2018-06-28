package com.bridgeit.util;

import com.bridgeit.model.Note;

public class ValidateNote {

	public static boolean validateNote(Note note)
	{
		if(note.getTitle()==null || note.getTitle().trim()==" " ) {
			if(note.getColor()==null || note.getCreatedAt()==null ) {
				if(note.getDescription()==null || note.getDescription().trim()==" ") {
					if(note.getUpdatedAt()==null || note.getImage()==null) {
						return false;
					}
				}
			}
		}
		return true;
		
	}
}
