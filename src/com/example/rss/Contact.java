package com.example.rss;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Contact extends ActionBarActivity {

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

		//FONT    
		final Typeface font = Typeface.createFromAsset(getAssets(), "fonts/sintony.otf");

		final Double ratio_h = (h / 800.000);
		final Double ratio_w = (w / 480.000);

		Double ratio1 = 0.0;
		Double ratio2 = 0.0;

		if(ratio_h < ratio_w) {
			ratio2 = ratio_h;
			ratio1 = ratio_w;
		} else {
			ratio2 = ratio_w;
			ratio1 = ratio_h;
		}

		//final Double ratio = ratio1;
		final Double min_ratio = ratio2;
		////////////////////////////////////////
		////////////////////////////////////////
		////////////////////////////////////////

		RelativeLayout.LayoutParams params;
		RelativeLayout rl = (RelativeLayout) findViewById(R.id.Relativ);

		Button titulo = new Button(this);
		params = new RelativeLayout.LayoutParams((int)(w * 0.8), (int)(h * 0.1));
		params.leftMargin = (int)(w * 0.10);
		params.topMargin = (int)(h * 0.03);
		titulo.getBackground().setAlpha(0);
		titulo.setText(R.string.developer);
		titulo.setTypeface(font);
		titulo.setTextColor(Color.WHITE);
		titulo.setBackgroundResource(R.drawable.button_flat_r);
		titulo.setTextSize((float)(25 * min_ratio));
		titulo.setClickable(false);
		rl.addView(titulo, params);

		final ImageButton foto_twitter = new ImageButton(this);
		params = new RelativeLayout.LayoutParams((int)(w * 0.15), (int)(h * 0.09));
		params.leftMargin = (int)(w * 0.10);
		params.topMargin = (int)(h * 0.28);
		foto_twitter.setBackgroundResource(R.drawable.twitter);
		rl.addView(foto_twitter, params);

		final ImageButton foto_email = new ImageButton(this);
		params = new RelativeLayout.LayoutParams((int)(w * 0.15), (int)(h * 0.09));
		params.leftMargin = (int)(w * 0.10);
		params.topMargin = (int)(h * 0.39);
		foto_email.setBackgroundResource(R.drawable.email);
		rl.addView(foto_email, params);

		final ImageButton foto_facebook = new ImageButton(this);
		params = new RelativeLayout.LayoutParams((int)(w * 0.15), (int)(h * 0.09));
		params.leftMargin = (int)(w * 0.10);
		params.topMargin = (int)(h * 0.51);
		foto_facebook.setBackgroundResource(R.drawable.facebook);
		rl.addView(foto_facebook, params);

		Button foto_mas = new Button(this);
		params = new RelativeLayout.LayoutParams((int)(w * 0.8), (int)(h * 0.09));
		params.leftMargin = (int)(w * 0.1);
		params.topMargin = (int)(h * 0.7);
		foto_mas.setText(getText(R.string.more_apps));
		foto_mas.setTextColor(Color.WHITE);
		foto_mas.setTypeface(font);
		foto_mas.setBackgroundResource(R.drawable.button_flat_c);
		rl.addView(foto_mas, params);

		TextView frase = new TextView(this);
		params = new RelativeLayout.LayoutParams((int)(w * 0.8), (int)(h * 0.1));
		params.leftMargin = (int)(w * 0.1);
		params.topMargin = (int)(h * 0.13);
		frase.setText(R.string.do_you_want);
		frase.setTypeface(font);
		frase.setGravity(Gravity.CENTER);
		frase.setTextSize((float)(16 * min_ratio));
		rl.addView(frase, params);

		TextView twitter2 = new TextView(this);
		params = new RelativeLayout.LayoutParams((int)(w * 0.9), (int)(h * 0.09));
		params.leftMargin = (int)(w * 0.30);
		params.topMargin = (int)(h * 0.28);
		twitter2.setText("@" + getText(R.string.twitter));
		twitter2.setTypeface(font);
		twitter2.setGravity(Gravity.CENTER_VERTICAL);
		twitter2.setTextSize((float)(15 * min_ratio));
		rl.addView(twitter2, params);

		TextView email = new TextView(this);
		params = new RelativeLayout.LayoutParams((int)(w * 0.9), (int)(h * 0.09));
		params.leftMargin = (int)(w * 0.30);
		params.topMargin = (int)(h * 0.39);
		email.setText(R.string.email);
		email.setGravity(Gravity.CENTER_VERTICAL);
		email.setTextSize((float)(15 * min_ratio));
		rl.addView(email, params);

		TextView facebook = new TextView(this);
		params = new RelativeLayout.LayoutParams((int)(w * 0.9), (int)(h * 0.09));
		params.leftMargin = (int)(w * 0.30);
		params.topMargin = (int)(h * 0.51);
		facebook.setText(getText(R.string.developer));
		facebook.setTypeface(font);
		facebook.setGravity(Gravity.CENTER_VERTICAL);
		facebook.setTextSize((float)(15 * min_ratio));
		rl.addView(facebook, params);

		twitter2.setOnClickListener(new View.OnClickListener() {@
			Override
			public void onClick(View v) {
				foto_twitter.performClick();

			}
		});

		facebook.setOnClickListener(new View.OnClickListener() {

			@
			Override
			public void onClick(View v) {
				foto_facebook.performClick();

			}
		});

		email.setOnClickListener(new View.OnClickListener() {

			@
			Override
			public void onClick(View v) {
				foto_email.performClick();

			}
		});
		foto_mas.setOnClickListener(new View.OnClickListener() {

			@
			Override
			public void onClick(View v) {
				Uri uri = Uri.parse("market://search?q=pub:" + getText(R.string.market_dev));
				Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
				try {
					startActivity(myAppLinkToMarket);
				} catch(ActivityNotFoundException e) {
					Toast.makeText(getApplicationContext(), R.string.no_market_problem, Toast.LENGTH_LONG).show();
				}

			}
		});

		foto_twitter.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("http://www.twitter.com/" + getText(R.string.twitter)));
				startActivity(Intent.createChooser(intent, null));

			}
		});

		foto_facebook.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("https://www.facebook.com/" + getText(R.string.facebook_page) + "?fref=ts"));
				startActivity(Intent.createChooser(intent, null));

			}
		});

		foto_email.setOnClickListener(new OnClickListener() {

			@
			Override
			public void onClick(View v) {
				Intent email = new Intent(Intent.ACTION_SEND);
				email.putExtra(Intent.EXTRA_EMAIL, new String[] { (String) getText(R.string.email)});
				email.putExtra(Intent.EXTRA_SUBJECT, getText(R.string.i_want_app));
				email.putExtra(Intent.EXTRA_TEXT, "");
				email.setType("message/rfc822");
				startActivity(Intent.createChooser(email, null));

			}
		});

	}
}