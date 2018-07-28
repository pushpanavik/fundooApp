package com.bridgeit.fundooNote.utilservice;

import com.bridgeit.fundooNote.labelservice.model.Label;

public class ValidateLabel {
	public static boolean validateLabel(Label label)
	{
		if(label.getName()==null || label.getName().trim()==" " ) {
			if(label.getNotes()==null) {
				if(label.getUserDetails()==null) {
					return false;
				}
			}
		}
		return true;
}
}
