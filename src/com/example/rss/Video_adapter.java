package com.example.rss;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Video_adapter extends ArrayAdapter<Object> {
	
	Context context;
	private ArrayList<Video> videos;
	
	public Video_adapter(Context context, ArrayList<Video> videos){
		super(context, R.layout.item_videos);
		this.context = context;
		this.videos = videos;
	}
	
	@Override
	public int getCount(){
		return videos.size();
	}
	
	private static class PlaceHolder{
		TextView title;
		
		public static PlaceHolder generate(View convertView){
			PlaceHolder placeHolder = new PlaceHolder();
			placeHolder.title = (TextView) convertView.findViewById(R.id.noticia_textview_title);
			
			return placeHolder;
		}
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		PlaceHolder placeHolder;
		if(convertView == null){
			convertView = View.inflate(context, R.layout.item_videos, null);
			placeHolder = PlaceHolder.generate(convertView);
			convertView.setTag(placeHolder);
		}else{
			placeHolder = (PlaceHolder) convertView.getTag();
		}
		
		placeHolder.title.setText(videos.get(position).getTitle());
		return (convertView);
		
	}

}
