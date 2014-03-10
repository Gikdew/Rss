package com.example.rss;

public class ConfigClass {
	
	
	String youtubeUser = "Willyrex"; //UCtGG8ucQgEJPeUPhJZ4M4jA
	String playlistID = "A84B69B84AF29E41";
	Boolean playlistMode = false; //Not imlemented yet (Always false)
	int perPage = playlistMode ? 25 : 5; //Number of results per page
	int startIndex = 1; //First result to get
	String DEVELOPER_KEY = "AIzaSyDsbCn-hOTV6yTpSKW_syy78D2rS39yXt8";
	
	Boolean admobEnabled = false;
	Boolean appnextEnabled = true;
	String ADMOB_KEY = "";
	String APPNEXT_KEY = "1f619df5-5c55-43ba-932c-9cc601b252c2";
	int adsEveryXClicks = 3;
	
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
