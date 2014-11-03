package com.zxl.demos.webview;

import com.zxl.demos.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
	private static final String TAG = "com.zxl.webview.MainActivity";
//	private static boolean D = ZxlConfig.isDebug();
	private LinearLayout mLayout = null;
	private WebView mWebView = null;

	@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		
		mLayout = (LinearLayout)findViewById(R.id.layout);
		
		mWebView = (WebView)findViewById(R.id.webview);
		//mWebView.addJavascriptInterface(new DemoJavaScriptInterface(), "blankCordova");
		mWebView.setWebChromeClient(new WebChromeClient());
		String url = "file:///android_asset/index.html";
		mWebView.loadUrl(url);
		mWebView.getSettings().setJavaScriptEnabled(true);
		
		mLayout.setOnTouchListener(new OnTouchListener() {
			
			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) { 
	                case MotionEvent.ACTION_DOWN: 
	                case MotionEvent.ACTION_UP: 
	                    if (!v.hasFocus()) { 
	                        v.requestFocus(); 
	                    } 
	                    break; 
	            }
				Log.d(TAG, "onTouch");
	            return false; 
			}
		});
	}

	@Override
	protected void onDestroy() {
		
		super.onDestroy();
	}

	@Override
	protected void onPause() {		
		super.onPause();
	}

	@Override
	protected void onRestart() {		
		super.onRestart();
	}

	@Override
	protected void onResume() {		
		super.onResume();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return super.dispatchTouchEvent(ev);
	}
	
	

	class DemoJavaScriptInterface {

        DemoJavaScriptInterface() {
        }

        /**
         * This is not called on the UI thread. Post a runnable to invoke
         * loadUrl on the UI thread.
         */
        public void startActivity() {
//            mHandler.post(new Runnable() {
//                public void run() {
//                	mAppView.loadUrl("javascript:wave()");
//                }
//            });
        	startBlankCordovaActivity();
        }
    }
    
    private void startBlankCordovaActivity(){		
		Intent intent = new Intent(this, com.zxl.demos.MainActivity.class);
		String url = "http://www.fengbao.com/test/content.html"; 
		intent.putExtra("", url);
		startActivity(intent);	
		
	}
}
