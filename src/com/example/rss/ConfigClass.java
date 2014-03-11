package com.example.rss;

public class ConfigClass {
		
	String youtubeUser = "Smosh"; //UCtGG8ucQgEJPeUPhJZ4M4jA
	String playlistID = "PLoXXK_fwj8TXR_ikX1HzbKjaNcmiZhU5E"; 
	Boolean playlistMode = false; //Not imlemented yet (Always false)
	int perPage = 10; //Number of results per page
	int startIndex = 1; //First result to get
	int velocity = 2 ; //Number reccomended between [1,2,3,4] Scroll Velocity
	String DEVELOPER_KEY = "AIzaSyDsbCn-hOTV6yTpSKW_syy78D2rS39yXt8"; //Youtube Developer Key
	
	Boolean admobEnabled = false; //Not Implemented yet
	Boolean appnextEnabled = false;
	String ADMOB_KEY = "";
	String APPNEXT_KEY = "1f619df5-5c55-43ba-932c-9cc601b252c2";
	int adsEveryXClicks = 3; //Shows ad every X videos watched
	
	public String generateUrl(int startPos, int pageNumber) {
		if(!playlistMode){
			return "http://gdata.youtube.com/feeds/api/users/"+ youtubeUser + 
					"/uploads?max-results=" + String.valueOf(pageNumber) + 
					"&start-index=" + String.valueOf(startPos) + 
					"&alt=rss";
		}else{
			return "https://gdata.youtube.com/feeds/api/playlists/"+ playlistID +"?v=2" + 
					"&start-index=" + String.valueOf(startPos) + "&alt=rss" + 
					"&max-results=" + String.valueOf(pageNumber);
		}
		
	}
	
}
