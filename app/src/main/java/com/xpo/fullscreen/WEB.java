package com.xpo.fullscreen;
import com.xpo.fullscreen.R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
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
import android.webkit.JsResult;
@SuppressLint({ "SetJavaScriptEnabled", "NewApi" })
public class WEB extends BASE {

	private WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fullscreen);
		mWebView = (WebView) findViewById(R.id.webView1);


		// mWebView.loadUrl("http://hotairballoon-on-whitesmoke.com/html5/index.html");
        String htmlString = getString(R.string.html_file);
        if(htmlString.contains("https://") || htmlString.contains("http://")){
            loadRemoteHtmlPage(htmlString);
        }else {
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

	private void loadRemoteHtmlPage(String uri) {
		if (uri != null) {

            // Settings
            WebSettings webSettings = mWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setAllowFileAccessFromFileURLs(true);
            webSettings.setAllowUniversalAccessFromFileURLs(true);

            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                webSettings.setAllowFileAccessFromFileURLs(true);
                webSettings.setAllowUniversalAccessFromFileURLs(true);
            }

            webSettings.setAllowFileAccess(true);
            webSettings.setAllowContentAccess(true);


            // Add JS interface to allow calls from webview to Android
            // code. See below for WebAppInterface class implementation
            mWebView.addJavascriptInterface(new WebAppInterface(this), "Android");

            // Set a web view client and a chrome client
            mWebView.setWebViewClient(new WebViewClient(){
				@Override
				public void onReceivedSslError (WebView view, SslErrorHandler handler, SslError error) {
					handler.proceed();
				}
			});

			mWebView.setWebChromeClient(new WebChromeClient() {
				// Need to accept permissions to use the camera and audio
				@Override
				public void onPermissionRequest(final PermissionRequest request) {
					Log.d("WEBBB", "onPermissionRequest");
					WEB.this.runOnUiThread(new Runnable() {
						//                        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
						@Override
						public void run() {
							// Make sure the request is coming from our file
							// Warning: This check may fail for local files
//                            if (request.getOrigin().toString().equals(LOCAL_FILE)) {
							request.grant(request.getResources());
//                            } else {
//                                request.deny();
//                            }
                        }
                    });
                }
            });
            webSettings.setBuiltInZoomControls(false);
            mWebView.requestFocusFromTouch();
            mWebView.loadUrl(uri);
        }
		else
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
            mWebView.loadDataWithBaseURL("file:///android_asset/320/", htmlString, "text/html", "UTF-8", null);

		}else {
			Log.i("ERROROROOROROROR", "Cannot Find HTML");
		}
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
    }
}

