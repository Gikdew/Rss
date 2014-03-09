package com.example.rss;

import java.net.URL;
import java.util.ArrayList;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class DownloadService extends Service {
	
	String youtubeUser="smosh";		 
	public ArrayList < Video > Array_Video = new ArrayList < Video > ();
    private static final String DEBUG_TAG = "TutListDownloaderService";
    private DownloaderTask tutorialDownloader;
 
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    	Log.i("Service", "Started");
        new DownloaderTask().execute();
        return START_STICKY;
    }
 
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
 
    private class DownloaderTask extends AsyncTask<URL, Void, Boolean> {
 
        private static final String DEBUG_TAG = "TutListDownloaderService$DownloaderTask";
 
        @Override
        protected Boolean doInBackground(URL... params) {
        	XMLParser parser = new XMLParser(generateUrl(1, 10), DownloadService.this);
			Array_Video = parser.parse();
			Log.i("Service", Array_Video.get(0).getDate().toString());
			return false;
        }
        
        @Override
        protected void onPostExecute(Boolean result) {
        	new Handler().postDelayed(new Runnable() {
                public void run() {
                    new DownloaderTask().execute();
                }
            }, 1000);
        }

		public String generateUrl(int startPos, int pageNumber) {
		return "http://gdata.youtube.com/feeds/api/users/"+ youtubeUser + 
				"/uploads?max-results=" + String.valueOf(pageNumber) + 
				"&start-index=" + String.valueOf(startPos) + 
				"&alt=rss";
	}
        
    }
 
}