package com.example.rss;

import com.appnext.appnextsdk.Appnext;
import com.appnext.appnextsdk.NoAdsInterface;
import com.appnext.appnextsdk.PopupClosedInterface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Window;

public class mainToVideo extends Activity{
	
	 private String VIDEO = "";
	
	ConfigClass c = new ConfigClass();
	String appnextKey = c.APPNEXT_KEY;
    Boolean appnext = c.appnextEnabled;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_to_video);
		Bundle bundle = getIntent().getExtras();
        VIDEO = bundle.getString("videoURL");
        
		if(appnext){
			Appnext appnext; 
			appnext = new Appnext(this); 
			appnext.setAppID(appnextKey); // Set your AppID 
			appnext.showBubble(); // show the interstitial 			
			appnext.setNoAdsInterface(new NoAdsInterface() {  
				@Override  
				public void noAds() {				
					Intent intent = new Intent(mainToVideo.this, VideoActivity.class);
				    intent.putExtra("videoURL", VIDEO);
				    startActivity(intent);
				    mainToVideo.this.finish();
					}  
				});
			appnext.setPopupClosedCallback(new PopupClosedInterface() { 
				@Override  
				public void popupClosed() {
					Intent intent = new Intent(mainToVideo.this, VideoActivity.class);
				    intent.putExtra("videoURL", VIDEO);
				    startActivity(intent);
				    mainToVideo.this.finish();
					Log.v("appnext", "popup closed");  
					}  
				});
			
		}	        
	}
}
