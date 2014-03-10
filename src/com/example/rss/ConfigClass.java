package com.example.rss;

public class ConfigClass {
	
	String youtubeUser = "willyrex";
	String playlistID = "A84B69B84AF29E41";
	Boolean playlistMode = true; //Not imlemented yet (Always false)
	int perPage = playlistMode ? 25 : 10; //Number of results per page
	int startIndex = 1; //First result to get
	String DEVELOPER_KEY = "AIzaSyDsbCn-hOTV6yTpSKW_syy78D2rS39yXt8";
	
	public String generateUrl(int startPos, int pageNumber) {
		if(!playlistMode){
			return "http://gdata.youtube.com/feeds/api/users/"+ youtubeUser + 
					"/uploads?max-results=" + String.valueOf(pageNumber) + 
					"&start-index=" + String.valueOf(startPos) + 
					"&alt=rss";
		}else{
			return "https://gdata.youtube.com/feeds/api/playlists/"+ playlistID +"?v=2" + 
					"&start-index=" + String.valueOf(startPos);
		}
		
	}
	
}
