package com.xpo.fullscreen;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;

public class BASE extends Activity {
	private boolean keyPressed = false;
	private boolean SCREEN_ON = true;
	private boolean mLoaded = false;
	public static String SVOICE_PACKAGE = "vom.vlingo.midas";
	public static String SVOICE_ACTIVITY = "vom.vlingo.midas.gui.ConversationActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		keyPressed = true;

		return true;

	}

	@Override
	public boolean onKeyLongPress(int keyCode, KeyEvent event) {

		keyPressed = true;

		return true;

	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {

		keyPressed = false;

		return true;

	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);

		if (!hasFocus) {
			windowCloseHandler.postDelayed(windowCloserRunnable, 0);
		}
	}

	private void toggleRecents() {
		Intent closeRecents = new Intent(
				"com.android.systemui.recent.action.TOGGLE_RECENTS");
		closeRecents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
		ComponentName recents = new ComponentName("com.android.systemui",
				"com.android.systemui.recent.RecentsActivity");
		closeRecents.setComponent(recents);
		this.startActivity(closeRecents);
	}

	private void toggleSVoice(){
		
		Intent closeRecents = new Intent(
				"com.vlingo.client.app.action.APPLICATION_STATE_CHANGED");
		closeRecents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
		ComponentName recents = new ComponentName(SVOICE_PACKAGE,
				SVOICE_ACTIVITY);
		closeRecents.setComponent(recents);
		this.startActivity(closeRecents);
	}
	
	private Handler windowCloseHandler = new Handler();
	private Runnable windowCloserRunnable = new Runnable() {
		@Override
		public void run() {
			ActivityManager am = (ActivityManager) getApplicationContext()
					.getSystemService(Context.ACTIVITY_SERVICE);
		
			
			ComponentName cn1 = am.getRunningTasks(1).get(0).baseActivity;
			

			if ((cn1 != null)
					&& (cn1.getClassName().equals(
							"com.android.systemui.recent.RecentsActivity"))) {
				toggleRecents();
			}else if((cn1 != null) && (cn1.getClassName().equals(
					SVOICE_ACTIVITY))){
				toggleSVoice(); 
			}else{				
				Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
				getApplicationContext().sendBroadcast(it); 
			}
		}
	};
}
