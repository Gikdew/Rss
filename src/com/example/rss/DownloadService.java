package com.example.rss;

import java.net.URL;
import java.util.ArrayList;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class DownloadService extends Service {

	public ArrayList < Video > Array_Video = new ArrayList < Video > ();
	Exception error;

	ConfigClass c = new ConfigClass();
	String youtubeUser = c.playlistMode ? "" : c.youtubeUser;

	@
	Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//Log.i("Service", "Started");
		new DownloaderTask().execute();
		return START_STICKY;
	}

	@
	Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private class DownloaderTask extends AsyncTask < URL, Void, Boolean > {
		final SharedPreferences lastVideo = getSharedPreferences("LastVideo", Context.MODE_PRIVATE);
		final Editor editor = lastVideo.edit();

		@
		Override
		protected void onPreExecute() {

			if(!lastVideo.contains("day") || !lastVideo.contains("hour") || !lastVideo.contains("minute") || !lastVideo.contains("lastUrl")) {
				editor.putInt("day", 0);
				editor.commit();

				editor.putInt("hour", 0);
				editor.commit();

				editor.putInt("minute", 0);
				editor.commit();

				editor.putString("lastUrl", "");
				editor.commit();
			}
		}

		@
		Override
		protected Boolean doInBackground(URL...params) {
			try {
				XMLParser parser = new XMLParser(c.generateUrl(1, 1), DownloadService.this);
				Array_Video = parser.parse();
				return true;
			} catch(Exception e) {
				//Log.i("Service", "Catch in Do in background");
				return false;
			}
		}

		@
		Override
		protected void onPostExecute(Boolean result) {
			if(result) {
				//Log.i("Service", Array_Video.get(0).getTitle());
				Video video = Array_Video.get(0);
				if((video.getDate().getDay() != lastVideo.getInt("day", 0)) ||
					(video.getDate().getHours() != lastVideo.getInt("hour", 0)) ||
					(video.getDate().getMinutes() != lastVideo.getInt("minute", 0)) || !video.getLink().equals(lastVideo.getString("lastUrl", ""))) {

					//Log.i("New", "Video");
					editor.putInt("day", video.getDate().getDay());
					editor.commit();
					editor.putInt("minute", video.getDate().getMinutes());
					editor.commit();
					editor.putInt("hour", video.getDate().getHours());
					editor.commit();
					editor.putString("lastUrl", video.getLink());
					editor.commit();

					if(!lastVideo.contains("firstTime")) {
						editor.putBoolean("firstTime", false);
						editor.commit();
					} else {
						SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
						if(preferences.getBoolean("notification", true)) {
							sendNotification();
						}

					}

				}

			} else {
				if(error != null) {
					Toast.makeText(DownloadService.this, error.getMessage(),
						Toast.LENGTH_SHORT).show();
				}
			}
			new Handler().postDelayed(new Runnable() {
				public void run() {
					new DownloaderTask().execute();
				}
			}, 120000);
		}

		public void sendNotification() {
			Intent intent = new Intent(DownloadService.this, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

			// build notification
			// the addAction re-use the same intent to keep the example short
			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(DownloadService.this);

			mBuilder.setContentTitle("New Video!")
				.setContentText(getText(R.string.youtubeUser) + " " + getText(R.string.new_video))
				.setTicker(getText(R.string.youtubeUser) + " " + getText(R.string.new_video))
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentIntent(pIntent)
				.setAutoCancel(true)
				.setDefaults(Notification.DEFAULT_ALL);

			NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

			notificationManager.notify(0, mBuilder.build());

		}
	}

}