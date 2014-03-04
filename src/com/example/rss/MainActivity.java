package com.example.rss;

import java.util.ArrayList;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;

public class MainActivity extends Activity {

	public ArrayList < Video > Array_Video = new ArrayList < Video > ();
	private Video_adapter adapter;
	private int startIndex = 1;
	private String URL = "http://gdata.youtube.com/feeds/api/users/willyrex/uploads?max-results=50&start-index=" + String.valueOf(startIndex) + "&alt=rss";

	@
	Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fillVideos();
		//initListView();
	}

	private void initListView() {
		ListView list = (ListView) findViewById(R.id.videos_listview);
		adapter = new Video_adapter(this, Array_Video);
		list.setAdapter(adapter);
	}

	private void fillVideos() {
		if (isOnline()) {
			startIndex = 10;
			new DownloadVideos(MainActivity.this, URL).execute();
		} else {
			Log.i("isOnline", String.valueOf(isOnline()));
		}
	}

	private boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getApplication()
			.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected()) {
			return true;
		}
		return false;
	}

	public class DownloadVideos extends AsyncTask < String, Void, Boolean > {

		private String feedUrl;
		private Context ctx;
		ProgressDialog dialog;

		public DownloadVideos(Context c, String url) {
			this.ctx = c;
			this.feedUrl = url;
			dialog = new ProgressDialog(c);
		}

		@
		Override
		protected void onPreExecute() {
			dialog.setCancelable(false);
			dialog.setMessage("Loading");
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setProgress(1);
			dialog.setMax(100);
			dialog.show();
		}

		@
		Override
		protected Boolean doInBackground(final String...args) {
			XMLParser parser = new XMLParser(feedUrl, getBaseContext());
			Array_Video = parser.parse();
			return false;
		}

		@
		Override
		protected void onPostExecute(Boolean success) {
			dialog.cancel();
			initListView();
		}

	}
}