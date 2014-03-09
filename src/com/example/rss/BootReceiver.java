package com.example.rss;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
        Intent startServiceIntent = new Intent(context, DownloadService.class);
        context.startService(startServiceIntent);
    }
}