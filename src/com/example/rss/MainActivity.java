package com.example.rss;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	public ArrayList<Video> Array_Video = new ArrayList<Video>();
	private Video_adapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		fillVideos();
		
		initListView();
	}

	private void initListView() {
		ListView list = (ListView) findViewById(R.id.videos_listview);	
		adapter = new Video_adapter(this, Array_Video);
		list.setAdapter(adapter);
	}

	private void fillVideos() {
		for(int i=1;i<500;i++){
			Video vid = new Video();
			vid.setTitle("Video " + String.valueOf(i));
			vid.setLink("http://gikdew.com");
			Array_Video.add(vid);
		}
		
	}
}
