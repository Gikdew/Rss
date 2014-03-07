package com.example.rss;

import java.util.Date;

import android.graphics.Bitmap;

public class Video {
	
	private String title;
	private String link;
	private String imageUrl;
	private Date date;
	private Bitmap image;
	private String duration;
	
	//Set Methods
	public void setTitle(String title){
		this.title = title;		
	}	
	public void setLink(String link){
		this.link = link;
	}
	public void setImageUrl(String image){
		this.imageUrl = image;
	}
	public void setDate(Date date){
		this.date = date;
	}
	public void setImage(Bitmap image){
		this.image = image;
	}
	public void setDuration(int duration){
		//FORMATEO DE LA DURACION
		
		String seconds1;
		int minutes = (duration % 3600) / 60;
		int seconds = duration % 60;		
		
		if(seconds < 10) seconds1 = "0" + seconds;
		else seconds1 = "" + seconds;
		
		this.duration = minutes + ":" + seconds1;
	}
	
	//Get Methods
	public String getTitle(){
		return this.title;
	}
	public String getLink(){
		return this.link;
	}
	public String getImageUrl(){
		return this.imageUrl;
	}
	public Date getDate(){
		return this.date;
	}
	public Bitmap getImage(){
		return this.image;
	}
	public String getDuration(){
		return this.duration;
	}
}
