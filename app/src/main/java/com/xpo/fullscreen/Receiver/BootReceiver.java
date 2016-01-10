package com.xpo.fullscreen.Receiver;

import com.xpo.fullscreen.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
        Intent i = new Intent();
        i.setClassName(context.getString(R.string.autoboot_package), context.getString(R.string.autoboot_activity));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
	}
}

