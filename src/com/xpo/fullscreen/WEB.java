package com.xpo.fullscreen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.xpo.fullscreen.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressLint({ "SetJavaScriptEnabled", "NewApi" })
public class WEB extends BASE {

	private WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fullscreen);
		mWebView = (WebView) findViewById(R.id.webView1);

		WebSettings webSettings = mWebView.getSettings();

		// Enable Javascript for interaction
		webSettings.setJavaScriptEnabled(true);

		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentapiVersion >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
			mWebView.getSettings().setAllowFileAccessFromFileURLs(true); // Maybe
																			// you
																			// don't
																			// need
																			// this
																			// rule
			mWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
		}

		webSettings.setAllowFileAccess(true);
		webSettings.setAllowContentAccess(true);
		mWebView.setWebViewClient(new WebViewClient());
		mWebView.setWebChromeClient(new WebChromeClient());

		// mWebView.loadUrl("http://hotairballoon-on-whitesmoke.com/html5/index.html");
		loadHtmlPage();
	}

	/**
	 * Gets html content from the assets folder.
	 */
	private String getHtmlFromAsset() {
		InputStream is;
		StringBuilder builder = new StringBuilder();
		String htmlString = null;
		try {
			is = getAssets().open(getString(R.string.html_file));
			if (is != null) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}

				htmlString = builder.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return htmlString;
	}

	private void loadHtmlPage() {
		String htmlString = getHtmlFromAsset();
		if (htmlString != null)
			mWebView.loadDataWithBaseURL("file:///android_asset/320/",
					htmlString, "text/html", "UTF-8", null);

		else
			Log.i("ERROROROOROROROR", "Cannot Find HTML");
	}
}
