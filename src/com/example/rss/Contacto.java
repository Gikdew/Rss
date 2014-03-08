package com.example.rss;



import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Contacto extends ActionBarActivity {
	
	
	 protected void onCreate(Bundle savedInstanceState) {
	     super.onCreate(savedInstanceState);
	     ActionBar actionBar = getSupportActionBar();
	     actionBar.setDisplayHomeAsUpEnabled(true);
	        
	        setContentView(R.layout.contacto);
	
	/////////////////////////////////////
	/////////////////////////////////////
	/////////////////////////////////////
	// VARIABLES DE TAMAÑO
	        
	DisplayMetrics metrics = new DisplayMetrics();
	((WindowManager) getBaseContext().getSystemService(Context.WINDOW_SERVICE))
    .getDefaultDisplay().getMetrics(metrics);
    Double h = (double) metrics.heightPixels;
    final Double w = (double) metrics.widthPixels;
    

    
    
    final Double ratio_h =  (h/800.000);
    final Double ratio_w =  (w/480.000);
    
   
    Double ratio1= 0.0;
    Double ratio2= 0.0;
    		
    if(ratio_h<ratio_w){
    	ratio2=ratio_h;
    	ratio1=ratio_w;
    }
    else {
    	ratio2=ratio_w;
    	ratio1=ratio_h;
    }
    
    
   final Double ratio=  ratio1;
   final Double min_ratio=  ratio2;
   ////////////////////////////////////////
   ////////////////////////////////////////
   ////////////////////////////////////////
   
   RelativeLayout.LayoutParams params;
   RelativeLayout rl = (RelativeLayout) findViewById(R.id.Relativ);
   
	   Button titulo = new Button(this);
         params = new RelativeLayout.LayoutParams((int)(w * 0.8), (int)(h * 0.2));
         params.leftMargin = (int)(w * 0.10);
         params.topMargin = (int)(0);
         titulo.setText("APePe Studio");
         titulo.setTextSize((float) (30*min_ratio));
         rl.addView(titulo, params);
         
         
  	   ImageButton foto_twitter = new ImageButton(this);
       params = new RelativeLayout.LayoutParams((int)(w * 0.15), (int)(h * 0.09));
       params.leftMargin = (int)(0);
       params.topMargin = (int)(h*0.38);
       foto_twitter.setBackgroundResource(R.drawable.ic_launcher);
       rl.addView(foto_twitter, params);
       
  	   ImageButton foto_email = new ImageButton(this);
       params = new RelativeLayout.LayoutParams((int)(w * 0.15), (int)(h * 0.09));
       params.leftMargin = (int)(0);
       params.topMargin = (int)(h*0.56);
       foto_email.setBackgroundResource(R.drawable.ic_launcher);
       rl.addView(foto_email, params);
       
       
  	   ImageButton foto_facebook = new ImageButton(this);
       params = new RelativeLayout.LayoutParams((int)(w * 0.15), (int)(h * 0.09));
       params.leftMargin = (int)(0);
       params.topMargin = (int)(h*0.74);
       foto_facebook.setBackgroundResource(R.drawable.ic_launcher);
       rl.addView(foto_facebook, params);
       
 	   ImageButton foto_mas = new ImageButton(this);
       params = new RelativeLayout.LayoutParams((int)(w * 0.15), (int)(h * 0.15));
       params.leftMargin = (int)(w*0.425);
       params.topMargin = (int)(h*0.85);
       foto_mas.setBackgroundResource(R.drawable.ic_launcher);
       rl.addView(foto_mas, params);
         
         
	   TextView frase = new TextView(this);
       params = new RelativeLayout.LayoutParams((int)(w * 0.9), (int)(h * 0.1));
       params.leftMargin = (int)(w * 0.05);
       params.topMargin = (int)(h*0.25);
       frase.setText("¿Do you want an App? Contact us!");
       frase.setTextSize((float) (18.5*min_ratio));
       rl.addView(frase, params);
         
         
        TextView twitter = new TextView(this);
       params = new RelativeLayout.LayoutParams((int)(w * 0.4), (int)(h * 0.05));
       params.leftMargin = (int)(w * 0.16);
       params.topMargin = (int)(h*0.38);
       twitter.setText("Twitter:");
       twitter.setTextSize((float) (15*min_ratio));
       rl.addView(twitter, params);
       
  	   TextView twitter2 = new TextView(this);
       params = new RelativeLayout.LayoutParams((int)(w * 0.4), (int)(h * 0.05));
       params.leftMargin = (int)(w * 0.16);
       params.topMargin = (int)(h*0.43);
       twitter2.setText("@ApepeStudio");
       twitter2.setTextSize((float) (15*min_ratio));
       rl.addView(twitter2, params);
       
  	   TextView email = new TextView(this);
       params = new RelativeLayout.LayoutParams((int)(w * 0.4), (int)(h * 0.05));
       params.leftMargin = (int)(w * 0.16);
       params.topMargin = (int)(h*0.56);
       email.setText("Email:");
       email.setTextSize((float) (15*min_ratio));
       rl.addView(email, params);
         
  	   TextView email2 = new TextView(this);
       params = new RelativeLayout.LayoutParams((int)(w * 0.6), (int)(h * 0.05));
       params.leftMargin = (int)(w * 0.16);
       params.topMargin = (int)(h*0.61);
       email2.setText("correo@ejemplo.com");
       email2.setTextSize((float) (15*min_ratio));
       rl.addView(email2, params);
       
  	   TextView facebook = new TextView(this);
       params = new RelativeLayout.LayoutParams((int)(w * 0.4), (int)(h * 0.05));
       params.leftMargin = (int)(w * 0.16);
       params.topMargin = (int)(h*0.74);
       facebook.setText("Facebook:");
       facebook.setTextSize((float) (15*min_ratio));
       rl.addView(facebook, params);
         
  	   TextView facebook2 = new TextView(this);
       params = new RelativeLayout.LayoutParams((int)(w * 0.6), (int)(h * 0.05));
       params.leftMargin = (int)(w * 0.16);
       params.topMargin = (int)(h*0.79);
       facebook2.setText("Cuenta de Facebook");
       facebook2.setTextSize((float) (15*min_ratio));
       rl.addView(facebook2, params);
       
       
       foto_mas.setOnClickListener(new View.OnClickListener() {
    	   
    	   @Override
    	   public void onClick(View v) {
    	    Uri uri = Uri.parse("market://search?q=pub:Apepe Studio");
    	                Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
    	                try {
    	                    startActivity(myAppLinkToMarket);
    	                } catch (ActivityNotFoundException e) {
    	                    Toast.makeText(getApplicationContext(), "Unable to find Google Play app.", Toast.LENGTH_LONG).show();
    	                }
    	    
    	   }
    	  });
       
       
       
        foto_twitter.setOnClickListener(new OnClickListener(){
         
     	   
     	   public void onClick(View v) {
     			Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("http://www.twitter.com/apepestudio"));
				startActivity(intent);
     	

        }  
     });
        
        foto_facebook.setOnClickListener(new OnClickListener(){
            
      	   
      	   public void onClick(View v) {
      			Intent intent = new Intent(Intent.ACTION_VIEW);
 				intent.setData(Uri.parse("https://www.facebook.com/apepestudio?fref=ts"));
 				startActivity(intent);
      	

         }  
      });
       
       
}
}

