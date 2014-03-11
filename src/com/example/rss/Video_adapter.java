package com.example.rss;

import java.util.ArrayList;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Video_adapter extends ArrayAdapter < Object > {

	Context context;
	private ArrayList < Video > videos;
	static RequestQueue rqstQueue;
	static ImageLoader imageloader;	

	public Video_adapter(Context context, ArrayList < Video > videos) {
		super(context, R.layout.item_videos);
		this.context = context;
		this.videos = videos;
		rqstQueue = Volley.newRequestQueue(context);
		imageloader = new ImageLoader(rqstQueue, new BitmapCache(40));	
		}

	@
	Override
	public int getCount() {
		return videos.size();
	}

	private static class PlaceHolder {
		TextView title, duration;
		NetworkImageView imageView;
		ImageView icon;
		
		public static PlaceHolder generate(View convertView) {
			PlaceHolder placeHolder = new PlaceHolder();
			placeHolder.title = (TextView) convertView.findViewById(R.id.video_textview_title);
			placeHolder.imageView = (NetworkImageView) convertView.findViewById(R.id.video_imageView);
			placeHolder.icon = (ImageView) convertView.findViewById(R.id.play_icon);
			placeHolder.duration = (TextView) convertView.findViewById(R.id.video_textview_duration);			
			return placeHolder;
		}
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		PlaceHolder placeHolder;
		String imageUrl = videos.get(position).getImageUrl();
		Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/sintony.otf");
		
		if (convertView == null) {
			convertView = View.inflate(context, isTablet(context) ? R.layout.item_videos_tablet :R.layout.item_videos, null);
			placeHolder = PlaceHolder.generate(convertView);
			convertView.setTag(placeHolder);			
		} else {
			placeHolder = (PlaceHolder) convertView.getTag();
			
		}
		
		 placeHolder.duration.setText(videos.get(position).getDuration());
		 placeHolder.duration.setTypeface(font);
		 placeHolder.imageView.setDefaultImageResId(R.drawable.smosh);
         placeHolder.imageView.setImageUrl(imageUrl, imageloader);
         placeHolder.icon.setImageResource(R.drawable.ic_action_play_over_vide);
		 //imageloader.get(imageUrl, ImageLoader.getImageListener(placeHolder.imageView, R.drawable.smosh, R.drawable.smosh));
         placeHolder.title.setTypeface(font);
		 placeHolder.title.setText(videos.get(position).getTitle());

		return (convertView);

	}

	public static boolean isTablet(Context context) {
    return (context.getResources().getConfiguration().screenLayout
            & Configuration.SCREENLAYOUT_SIZE_MASK)
            >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}

}