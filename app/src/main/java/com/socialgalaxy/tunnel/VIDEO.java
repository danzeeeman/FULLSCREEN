package com.socialgalaxy.tunnel;

import com.socialgalaxy.tunnel.R;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.VideoView;

public class VIDEO extends BASE {
	private VideoView mVideoView;
	private boolean mVolume;
	private boolean mPrepared;
	private int mPos;
	private AudioManager mAudioManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video);
		mVideoView = (VideoView) findViewById(R.id.videoView1);
		String uriPath = getString(R.string.video_file);
		// String uriPath =
		// "android.resource://com.xpo.fullscreen/raw/ai_weiwei";
		// String uriPath =
		// "android.resource://com.xpo.fullscreen/raw/ai_weiwei";
		// String uriPath =
		// "android.resource://com.xpo.fullscreen/raw/ai_weiwei";
		// String uriPath =
		// "android.resource://com.xpo.fullscreen/raw/ai_weiwei";
		// String uriPath =
		// "android.resource://com.xpo.fullscreen/raw/ai_weiwei";
		// String uriPath =
		// "android.resource://com.xpo.fullscreen/raw/ai_weiwei";
		Uri uri = Uri.parse(uriPath);
		mVideoView.setVideoURI(uri);
		mVideoView.requestFocus();

		try{
			mVolume = savedInstanceState.getBoolean("MyBoolean");
			mPos = savedInstanceState.getInt("MyInt");
		}catch (Exception e){
			mVolume = false;
			mPos = 0;
		}
		
		mVideoView.setOnPreparedListener(new OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				mp.setScreenOnWhilePlaying(false);
				mVideoView.start();
				mVideoView.seekTo(mPos);
				mPrepared = true;
			}
		});
		mVideoView.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				mVideoView.seekTo(0);
				mVideoView.start();
			}
		});

		mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (mVolume) {
			int max = mAudioManager
					.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, max, 0);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mVolume) {
			mVolume = !mVolume;
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
		} else {
			mVolume = !mVolume;
			int max = mAudioManager
					.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, max, 0);
		}
		return true;
	}

	@Override
	public void onPause() {
		super.onPause();
		mVideoView.pause();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mVideoView.resume();
		mVideoView.seekTo(mPos);
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		// Save UI state changes to the savedInstanceState.
		// This bundle will be passed to onCreate if the process is
		// killed and restarted.
		savedInstanceState.putBoolean("MyBoolean", mVolume);
		savedInstanceState.putInt("MyInt", mVideoView.getCurrentPosition());
		mPos = mVideoView.getCurrentPosition();
	}
}
