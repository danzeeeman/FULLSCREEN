package com.socialgalaxy.tunnel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.content.Context;
import android.widget.Toast;
import android.webkit.PermissionRequest;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.net.http.SslError;
import android.webkit.ConsoleMessage;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.GregorianCalendar;

@SuppressLint({"SetJavaScriptEnabled", "NewApi"})
public class WEB extends BASE {

	private WebView mWebView;
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	private GoogleApiClient client;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fullscreen);
		mWebView = (WebView) findViewById(R.id.webView1);
		mWebView.setOnLongClickListener(new View.OnLongClickListener() {
			int count;
			long lastPress = 0;
			long firstPress = 0;

			@Override
			public boolean onLongClick(View view) {
				Log.i("WEB", "onLongClick");
				if (lastPress == 0) {
					lastPress = System.currentTimeMillis();
				}
				if (lastPress - System.currentTimeMillis() < 2000 && lastPress != 0) {
					String htmlString = getString(R.string.html_file);
					if (htmlString.contains("https://") || htmlString.contains("http://")) {
						loadRemoteHtmlPage(htmlString);
					} else {
						loadLocalHtmlPage(getHtmlFromAsset());
					}
				}
				lastPress = System.currentTimeMillis();
				return true;
			}
		});
		// mWebView.loadUrl("http://hotairballoon-on-whitesmoke.com/html5/index.html");
		String htmlString = getString(R.string.html_file);
		if (htmlString.contains("https://") || htmlString.contains("http://")) {
			loadRemoteHtmlPage(htmlString);
		} else {
			loadLocalHtmlPage(getHtmlFromAsset());
		}
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// mWebView.loadUrl("http://hotairballoon-on-whitesmoke.com/html5/index.html");
		String htmlString = getString(R.string.html_file);
		if (htmlString.contains("https://") || htmlString.contains("http://")) {
			loadRemoteHtmlPage(htmlString);
		} else {
			loadLocalHtmlPage(getHtmlFromAsset());
		}
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

	@Override
	public boolean onKeyLongPress(int keyCode, KeyEvent event) {
		Log.i("WEB", "onKeyLongPress");
		String htmlString = getString(R.string.html_file);
		if (htmlString.contains("https://") || htmlString.contains("http://")) {
			loadRemoteHtmlPage(htmlString);
		} else {
			loadLocalHtmlPage(getHtmlFromAsset());
		}

		return true;

	}

	private void loadRemoteHtmlPage(String uri) {
        final String url = uri;
		if (uri != null) {

			// Settings
			WebSettings webSettings = mWebView.getSettings();
			webSettings.setJavaScriptEnabled(true);
			webSettings.setAllowFileAccessFromFileURLs(true);
			webSettings.setAllowUniversalAccessFromFileURLs(true);

			int currentapiVersion = Build.VERSION.SDK_INT;
			if (currentapiVersion >= Build.VERSION_CODES.JELLY_BEAN) {
				webSettings.setAllowFileAccessFromFileURLs(true);
				webSettings.setAllowUniversalAccessFromFileURLs(true);
			}

			webSettings.setAllowFileAccess(true);
			webSettings.setAllowContentAccess(true);


			// Add JS interface to allow calls from webview to Android
			// code. See below for WebAppInterface class implementation
			mWebView.addJavascriptInterface(new WebAppInterface(this), "Android");

			// Set a web view client and a chrome client
			mWebView.setWebViewClient(new WebViewClient() {
				@Override
				public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
					handler.proceed();
				}

				@Override
				public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
					Log.i("DEBUG DEBUG DEBUG", String.valueOf(errorCode));
					view.loadUrl(url);
				}
			});

			mWebView.setWebChromeClient(new WebChromeClient() {
				// Need to accept permissions to use the camera and audio
				@Override
				public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
					if (consoleMessage.messageLevel() == ConsoleMessage.MessageLevel.ERROR) {
						mWebView.loadUrl(url);
						Log.e("WebChromeClient", "ConsoleMessage ERROR");
					}
					return super.onConsoleMessage(consoleMessage);
				}

				@Override
				public void onPermissionRequest(final PermissionRequest request) {
					WEB.this.runOnUiThread(new Runnable() {
						//                        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
						@Override
						public void run() {
							request.grant(request.getResources());
						}
					});
				}
			});
			webSettings.setBuiltInZoomControls(false);
			mWebView.requestFocusFromTouch();
			mWebView.loadUrl(uri);
		} else
			Log.i("ERROROROOROROROR", "Cannot Find Web");
	}

	private void loadLocalHtmlPage(String htmlString) {
		if (htmlString != null) {
			WebSettings webSettings = mWebView.getSettings();
			webSettings.setJavaScriptEnabled(true);
			webSettings.setBuiltInZoomControls(false);
			mWebView.requestFocusFromTouch();
			mWebView.setWebViewClient(new WebViewClient());
			mWebView.setWebChromeClient(new WebChromeClient());
			mWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
			mWebView.loadDataWithBaseURL("file:///android_asset/320/", htmlString, "text/html", "UTF-8", null);

		} else {
			Log.i("ERROROROOROROROR", "Cannot Find HTML");
		}
	}

	@Override
	public void onStart() {
		super.onStart();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client.connect();
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"WEB Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app deep link URI is correct.
				Uri.parse("android-app://com.socialgalaxy.tunnel/http/host/path")
		);
		AppIndex.AppIndexApi.start(client, viewAction);
	}

	@Override
	public void onStop() {
		super.onStop();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"WEB Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app deep link URI is correct.
				Uri.parse("android-app://com.socialgalaxy.tunnel/http/host/path")
		);
		AppIndex.AppIndexApi.end(client, viewAction);
		client.disconnect();
	}

	// Interface b/w JS and Android code
	private class WebAppInterface {
		Context mContext;

		WebAppInterface(Context c) {
			mContext = c;
		}

		// This function can be called in our JS script now
		@JavascriptInterface
		public void showToast(String toast) {
			Toast.makeText(mContext, toast, Toast.LENGTH_LONG).show();
		}

		@JavascriptInterface
		public void showSettings(){
			Log.i("showSettings", "showSettings");
			Intent settingsIntent = new Intent(Settings.ACTION_SETTINGS);
			mContext.startActivity(settingsIntent);
			android.os.Process.killProcess(android.os.Process.myPid());
		}
	}
}

