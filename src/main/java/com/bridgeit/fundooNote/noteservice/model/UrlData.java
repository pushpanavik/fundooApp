package com.bridgeit.fundooNote.noteservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="url")
public class UrlData {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String title;

	private String imageUrl;


	private String url;
	private String description;
	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public UrlData(String title, String imageUrl) {

		this.title = title;
		this.imageUrl = imageUrl;
		
	}

	public UrlData() {
		
	}

	
	public String getTitle() {
		return title;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	

	public void setTitle(String title) {
		this.title = title;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	


}
