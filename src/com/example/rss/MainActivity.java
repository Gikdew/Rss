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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

public class MainActivity extends Activity {

	public ArrayList < Video > Array_Video = new ArrayList < Video > ();
	public ArrayList < Video > Array_1 = new ArrayList < Video > ();
	private Video_adapter adapter;
	private int startIndex = 1;
	private String URL;
	int perPage = 10;
	Boolean firstTimeAsync = true;
	Button btnLoadMore;
	ListView lv;
	ProgressBar pgBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		URL = generateUrl(startIndex, perPage);

		lv = (ListView) findViewById(R.id.videos_listview);
		btnLoadMore = new Button(this);
		btnLoadMore.setText("Load More");
		lv.addFooterView(btnLoadMore);

		btnLoadMore.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				lv.removeFooterView(btnLoadMore);
				startIndex += perPage;
				if(isOnline()){
					new DownloadVideos(MainActivity.this, generateUrl(startIndex, perPage)).execute();
				}
				
			}
		});

		fillVideos();
		initListView();
	}

	public String generateUrl(int startPos, int pageNumber) {
		return "http://gdata.youtube.com/feeds/api/users/willyrex/uploads?max-results=" + String.valueOf(pageNumber) + "&start-index=" + String.valueOf(startPos) + "&alt=rss";

	}

	private void initListView() {
		adapter = new Video_adapter(this, Array_Video);
		lv.setAdapter(adapter);
		lv.invalidateViews();

	}

	private void fillVideos() {
		if(isOnline()) {
			startIndex = 1;
			new DownloadVideos(MainActivity.this, generateUrl(startIndex, perPage)).execute();
		} else {
			Log.i("isOnline", String.valueOf(isOnline()));
		}
	}

	private boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getApplication()
			.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if(netInfo != null && netInfo.isConnected()) {
			return true;
		}
		return false;
	}

	public class DownloadVideos extends AsyncTask < String, Void, Boolean > {

		private String feedUrl;
		private Context ctx;
		ProgressDialog dialog;
		LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ProgressBar pgBar;

		public DownloadVideos(Context c, String url) {
			this.ctx = c;
			this.feedUrl = url;
			dialog = new ProgressDialog(c);
		}

		@Override
		protected void onPreExecute() {
			if(firstTimeAsync) {
				dialog.setCancelable(false);
				dialog.setMessage("Loading");
				dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				dialog.setProgress(1);
				dialog.setMax(100);
				dialog.show();
			} else {
				pgBar = (ProgressBar) layoutInflater.inflate(R.layout.progress_bar, null);
				pgBar.setIndeterminate(true);
				lv.addFooterView(pgBar);
				lv.setSelection(lv.getCount());
			}
		}

		@Override
		protected Boolean doInBackground(final String...args) {
			XMLParser parser = new XMLParser(feedUrl, getBaseContext());
			Array_1 = parser.parse();
			Array_Video.addAll(Array_1);
			return false;
		}

		@Override
		protected void onPostExecute(Boolean success) {
			adapter.notifyDataSetChanged();

			//btnLoadMore.setVisibility(View.VISIBLE);

			if(firstTimeAsync) {
				dialog.cancel();
				firstTimeAsync = false;
			} else {
				lv.removeFooterView(pgBar);
				lv.addFooterView(btnLoadMore);
			}
		}

	}
}