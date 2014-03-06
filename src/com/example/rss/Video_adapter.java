package com.example.rss;

import java.util.ArrayList;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.Volley;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Video_adapter extends ArrayAdapter < Object > {

	Context context;
	private ArrayList < Video > videos;
	RequestQueue rqstQueue;
	static ImageLoader imageloader;
	ImageContainer[] imagecontainer;

	public Video_adapter(Context context, ArrayList < Video > videos) {
		super(context, R.layout.item_videos);
		this.context = context;
		this.videos = videos;
		rqstQueue = Volley.newRequestQueue(context);
		imageloader = new ImageLoader(rqstQueue, new BitmapCache(100));
				
	}

	@
	Override
	public int getCount() {
		return videos.size();
	}

	private static class PlaceHolder {
		TextView title;
		ImageView imageView;
		public static PlaceHolder generate(View convertView) {
			PlaceHolder placeHolder = new PlaceHolder();
			placeHolder.title = (TextView) convertView.findViewById(R.id.video_textview_title);
			placeHolder.imageView = (ImageView) convertView.findViewById(R.id.video_imageView);
			
			return placeHolder;
		}
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		PlaceHolder placeHolder;
		String imageUrl = videos.get(position).getImage();
		
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_videos, null);
			placeHolder = PlaceHolder.generate(convertView);
			convertView.setTag(placeHolder);
			
		} else {
			placeHolder = (PlaceHolder) convertView.getTag();
			
		}
		
		imageloader.get(imageUrl, ImageLoader.getImageListener(placeHolder.imageView, R.drawable.smosh, R.drawable.smosh));
		placeHolder.title.setText(videos.get(position).getTitle());

		return (convertView);

	}

}