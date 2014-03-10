package com.example.rss;

import java.util.ArrayList;

import com.android.volley.toolbox.Volley;
import com.appnext.appnextsdk.Appnext;

import android.R.string;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	public ArrayList < Video > Array_Video = new ArrayList < Video > ();
	public ArrayList < Video > Array_1 = new ArrayList < Video > ();
	private Video_adapter adapter;
	private String URL; 
	
	Boolean firstTimeAsync = true;
	Button btnLoadMore;
	ListView lv;
	ProgressBar pgBar;
	MenuItem refreshMenuItem, settingsMenuItem;	
	Boolean flag_loading;

	ConfigClass c = new ConfigClass();
	String youtubeUser = c.youtubeUser;
	int perPage = c.perPage;
	int startIndex = c.startIndex;
	boolean appnext = c.appnextEnabled;
	String appnextKey = c.APPNEXT_KEY;
	int adsEvery = c.adsEveryXClicks;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		
		supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_main);
		
		Intent i= new Intent(MainActivity.this, DownloadService.class);
		MainActivity.this.startService(i);
		
		//Shared Preferences for Ads
		final SharedPreferences ads = getSharedPreferences("Ads", Context.MODE_PRIVATE);
        final Editor editor = ads.edit();
        editor.putInt("counter", 0);
	   	editor.commit();
        
        if(appnext){
        	Appnext appnext; 
        	appnext = new Appnext(this); 
        	appnext.setAppID(appnextKey); // Set your AppID 
        	appnext.showBubble(); // show the interstitial 
        }        

		URL = c.generateUrl(startIndex, perPage);		

		lv = (ListView) findViewById(R.id.videos_listview);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		  @Override
		  public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			  
			   	editor.putInt("counter", ads.getInt("counter", 0)+1);
			   	editor.commit();			  
			   	if(appnext && ads.getInt("counter", 0) == adsEvery){
			   		
			   		editor.putInt("counter", 0);
				   	editor.commit();	
				   	
			   		Intent intent = new Intent(MainActivity.this, mainToVideo.class);
				    String video = Array_Video.get(position).getLink();
				    intent.putExtra("videoURL", video);
				    //Toast.makeText(MainActivity.this, video, Toast.LENGTH_SHORT).show();
				    startActivity(intent);
				    
			   	}else{
			   		Intent intent = new Intent(MainActivity.this, VideoActivity.class);
				    String video = Array_Video.get(position).getLink();
				    intent.putExtra("videoURL", video);
				    //Toast.makeText(MainActivity.this, video, Toast.LENGTH_SHORT).show();
				    startActivity(intent);
			   	}
			   	Log.i("Counter", String.valueOf(ads.getInt("counter", 0)));
			  	
		   
		  }
		});
		
		flag_loading = false;
		lv.setOnScrollListener(new OnScrollListener() {
	        public void onScrollStateChanged(AbsListView view, int scrollState) {
	        	
	        }
	        public void onScroll(AbsListView view, int firstVisibleItem,
	                int visibleItemCount, int totalItemCount) {
	            if(firstVisibleItem+visibleItemCount == totalItemCount - 1 && totalItemCount!=0)
	            {
	            	SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	                if(flag_loading == false && preferences.getBoolean("scroll", true))
	                {
	                    flag_loading = true;
	                    btnLoadMore.performClick();
	                }
	            }
	        }
	    });
		
		btnLoadMore = new Button(this);
		btnLoadMore.setText("Load More");
		btnLoadMore.setTextColor(Color.WHITE);
		btnLoadMore.setVisibility(View.GONE);
		lv.addFooterView(btnLoadMore);
		btnLoadMore.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				lv.removeFooterView(btnLoadMore);
				startIndex += perPage;
				if(isOnline()){
					new DownloadVideos(MainActivity.this, c.generateUrl(startIndex, perPage)).execute();					
				}else{
					Toast.makeText(MainActivity.this, "Internet Connection Problem. Retry.", Toast.LENGTH_SHORT).show();
				}
				
			}
		});

		fillVideos();		
		initListView();
		
	}

	private void initListView() {
		adapter = new Video_adapter(this, Array_Video);
		lv.setAdapter(adapter);		

	}

	private void fillVideos() {
		if(isOnline()) {
			startIndex = 1;
			new DownloadVideos(MainActivity.this, c.generateUrl(startIndex, perPage)).execute();
		} else {
			//Log.i("isOnline", String.valueOf(isOnline()));
			Toast.makeText(MainActivity.this, "Internet Connection Problem. Retry.", Toast.LENGTH_SHORT).show();
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
		Exception error;
		
		public DownloadVideos(Context c, String url) {
			this.ctx = c;
			this.feedUrl = url;
			dialog = new ProgressDialog(c);
		}

		@Override
		protected void onPreExecute() {
			flag_loading = true;
			if(firstTimeAsync) {
				dialog.setCancelable(false);
				dialog.setMessage("Loading...");
				dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				dialog.setProgress(1);
				dialog.setMax(100);
				dialog.show();
			} else {
				pgBar = (ProgressBar) layoutInflater.inflate(R.layout.progress_bar, null);
				pgBar.setIndeterminate(true);
				//lv.removeFooterView(btnLoadMore);
				lv.addFooterView(pgBar);
				//lv.setSelection(lv.getCount()-1);
			}
		}

		@Override
		protected Boolean doInBackground(final String...args) {
			try {
				if(!c.playlistMode){
					XMLParser parser = new XMLParser(feedUrl, getBaseContext());
					Array_1 = parser.parse();
				}else{
					XMLParserList parser = new XMLParserList(feedUrl, getBaseContext());
					Array_1 = parser.parse();
				}				
				
				Array_Video.addAll(Array_1);
	             return true;
	        } catch (Exception e) {	            
	        	return false;
	        } 
							
			
		}

		@Override
		protected void onPostExecute(Boolean success) {		
			flag_loading = false;
			if (success) {
			 	adapter.notifyDataSetChanged();
				btnLoadMore.setVisibility(View.VISIBLE);						
				if(firstTimeAsync) {
					dialog.cancel();
					firstTimeAsync = false;
					//Hide the btnloadmore, if you dont get enough results!
					if(Array_Video.size()<perPage){
						lv.removeFooterView(btnLoadMore);
					}else{
						lv.addFooterView(btnLoadMore);
					}
				} else {
					////Log.i("Array",String.valueOf(Array_Video.size()));
					lv.removeFooterView(pgBar);
					if(Array_1.size() == 0){
						lv.removeFooterView(btnLoadMore);
					}	
					if(Array_1.size()<perPage){
						lv.removeFooterView(btnLoadMore);
					}else{
						lv.addFooterView(btnLoadMore);
					}
					//Hide the btnloadmore, if you dont get enough results!
					
						
				}   
	         } else {
	            if (error != null) {
	                Toast.makeText(ctx, error.getMessage(),
	                        Toast.LENGTH_SHORT).show();
	            }
	        

			}		 
			
		}

	}
	

	public class RefreshButtonAsync extends AsyncTask<String, Void, Boolean>{
		private String feedUrl;
		private Context ctx;
		
		public RefreshButtonAsync(Context c, String url) {
			this.ctx = c;
			this.feedUrl = url;				
		}
		
		@Override
		protected void onPreExecute() {
			startIndex = 1;			
			//lv.removeFooterView(btnLoadMore);
			//setSupportProgressBarIndeterminateVisibility(true);			
			MenuItemCompat.setActionView(refreshMenuItem, R.layout.action_progress_bar);			 
            MenuItemCompat.expandActionView(refreshMenuItem);	
			
		}
		
		@Override
		protected Boolean doInBackground(String... params) {
			try {				
				if(!c.playlistMode){
					XMLParser parser = new XMLParser(feedUrl, getBaseContext());
					Array_Video = parser.parse();
				}else{
					XMLParserList parser = new XMLParserList(feedUrl, getBaseContext());
					Array_Video = parser.parse();
				}		
	             return true;
	        } catch (Exception e) {	            
	        	return false;
	        } 			
		}	
		
		@Override
		protected void onPostExecute(Boolean result) {
			initListView();		
			lv.setSelection(0);
			btnLoadMore.setVisibility(View.VISIBLE);
			/*
			if(Array_Video.size()<perPage){
				lv.removeFooterView(btnLoadMore);
			}else{
				lv.addFooterView(btnLoadMore);
			}
			*/
			//setSupportProgressBarIndeterminateVisibility(false);
			MenuItemCompat.collapseActionView(refreshMenuItem);
            MenuItemCompat.setActionView(refreshMenuItem, null);
        }

	}


	//MENU
	@
	Override

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
				
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			// action with ID action_refresh was selected
		case R.id.action_refresh:
			
			if(isOnline()) {
				if(!flag_loading){
					
				refreshMenuItem = item;
				new RefreshButtonAsync(MainActivity.this, c.generateUrl(1, perPage)).execute();
				}else{
					Toast.makeText(MainActivity.this, "Wait. Downloading videos...", Toast.LENGTH_SHORT).show();
				}
				
			} else {
				//Log.i("isOnline", String.valueOf(isOnline()));
				//Toast.makeText(MainActivity.this, "Internet Connection Problem. Retry.", Toast.LENGTH_SHORT).show();
				lv.addFooterView(btnLoadMore);
			}
			break;
			// action with ID action_settings was selected
		case R.id.action_settings:
			//Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show();
			Intent intent2 = new Intent(this, Preferences.class);		    
		    startActivity(intent2);
			break;			
		case R.id.action_contact:
			Intent intent = new Intent(this, Contacto.class);		    
		    startActivity(intent);
		default:
			break;
		}

		return true;
	}
}