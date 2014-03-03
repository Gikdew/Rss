package com.example.rss;

public class Video {
	
	private String title;
	private String link;
	private String image;
	
	//Set Methods
	public void setTitle(String title){
		this.title = title;		
	}	
	public void setLink(String link){
		this.link = link;
	}
	public void setImage(String image){
		this.image = image;
	}
	
	//Get Methods
	public String getTitle(){
		return this.title;
	}
	public String getLink(){
		return this.link;
	}
	public String getImage(){
		return this.image;
	}
}
