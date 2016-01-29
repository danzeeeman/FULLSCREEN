package com.socialgalaxy.tunnel;

import java.io.IOException;
import java.io.InputStream;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

@SuppressLint("NewApi")
public class GIF extends BASE {
	GifImageView mView;
	GifDrawable mGif;
	private RelativeLayout mRelative;
	private int mPos;
	private boolean mCanPause;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mRelative = new RelativeLayout(this);
		RelativeLayout.LayoutParams param  = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		param.setMargins(0, 0, 0, 0);
		mRelative.setLayoutParams(param);
		
		try {
			mGif = new GifDrawable(getAssets(), getString(R.string.gif_file));
			mView = new GifImageView(this);
			// view.setBackground(gifFromAssets);
			
			try{
				mCanPause = savedInstanceState.getBoolean("MyBoolean");
				mPos = savedInstanceState.getInt("MyInt");
			}catch (Exception e){
				mCanPause = mGif.canPause();
				mPos = 0;
			}
			
			mGif.seekTo(mPos);
			mView.setImageDrawable(mGif);
			RelativeLayout.LayoutParams params  = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			param.setMargins(0, 0, 0, 0);
			mRelative.addView(mView, params);
			setContentView(mRelative);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onPause() {
		super.onPause();
		mView.setVisibility(View.GONE);
		mGif.pause();
		mPos = mGif.getCurrentPosition();
	}

	@Override
	public void onResume() {
		super.onResume();
		mView.setVisibility(View.VISIBLE);
		mGif.start();
		mGif.seekTo(mPos);
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		// Save UI state changes to the savedInstanceState.
		// This bundle will be passed to onCreate if the process is
		// killed and restarted.
		savedInstanceState.putBoolean("MyBoolean", mGif.canPause());
		savedInstanceState.putInt("MyInt", mGif.getCurrentPosition());
	}
}