package com.example.rss;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{
	
	
    static private String VIDEO = "";
    YouTubePlayerView youTubeView;
    
	ConfigClass c = new ConfigClass();
	String DEVELOPER_KEY = c.DEVELOPER_KEY;
	int adsEvery = c.adsEveryXClicks;
	
    
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.video_layout);
		Bundle bundle = getIntent().getExtras();
        VIDEO = bundle.getString("videoURL");
		youTubeView = (YouTubePlayerView)	findViewById(R.id.player);
		youTubeView.initialize(DEVELOPER_KEY, this);
		
	}

	@Override
	public void onInitializationFailure(Provider arg0,
			YouTubeInitializationResult arg1) {
		  Toast.makeText(this, "Oh no! "+arg1.toString(),Toast.LENGTH_LONG).show();
		
	}

	@Override
	public void onInitializationSuccess(Provider arg0, YouTubePlayer player,
			boolean arg2) {		
		player.setFullscreen(true);
		player.loadVideo(VIDEO);		
	}
}
