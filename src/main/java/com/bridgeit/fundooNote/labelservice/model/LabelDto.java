package com.bridgeit.fundooNote.labelservice.model;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
public class LabelDto {
	
	private int labelId;
	private String name;
	public int getLabelId() {
		return labelId;
	}
	public void setLabelId(int labelId) {
		this.labelId = labelId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
