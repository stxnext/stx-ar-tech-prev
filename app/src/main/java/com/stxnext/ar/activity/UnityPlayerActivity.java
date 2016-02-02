package com.stxnext.ar.activity;

import com.stxnext.ar.R;
import com.unity3d.player.*;
import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;

public class UnityPlayerActivity extends Activity {

	protected UnityPlayer mUnityPlayer; // don't change the name of this variable; referenced from native code
	private Button backButton;
	private FrameLayout unityContainer;

    public static final String AR_OBJECT_INTENT_TAG = "arObjectTag";
    public static final int LOGO_REQUEST = 1;
    public static final int DINOSAUR_REQUEST = 2;
    public static final int CAT_REQUEST = 3;

    private static final String UNITY_OBJECT_NAME = "ARCamera";


	// Setup activity layout
	@Override protected void onCreate (Bundle savedInstanceState)
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		getWindow().setFormat(PixelFormat.RGBX_8888); // <--- This makes xperia play happy

		mUnityPlayer = new UnityPlayer(this);
		setContentView(R.layout.activity_unity_player);
		backButton = (Button) findViewById(R.id.back_button);
		unityContainer = (FrameLayout) findViewById(R.id.unity_container);
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
		unityContainer.addView(mUnityPlayer.getView(), 0, layoutParams);

		mUnityPlayer.requestFocus();
		backButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
//                finish();
			}
		});
		int request = getIntent().getIntExtra(AR_OBJECT_INTENT_TAG, LOGO_REQUEST);
		switch (request) {
			case LOGO_REQUEST:
				mUnityPlayer.UnitySendMessage(UNITY_OBJECT_NAME, "activateLogo", "");
				break;
			case DINOSAUR_REQUEST:
				mUnityPlayer.UnitySendMessage(UNITY_OBJECT_NAME, "activateDinosaur", "");
				break;
			case CAT_REQUEST:
				mUnityPlayer.UnitySendMessage(UNITY_OBJECT_NAME, "activateCat", "");
				break;
		}
	}

	// Quit Unity
	@Override protected void onDestroy ()
	{
		mUnityPlayer.quit();
		super.onDestroy();
	}

	// Pause Unity
	@Override protected void onPause()
	{
		super.onPause();
		mUnityPlayer.pause();
	}

	// Resume Unity
	@Override protected void onResume()
	{
		super.onResume();
		mUnityPlayer.resume();
	}

	// This ensures the layout will be correct.
	@Override public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		mUnityPlayer.configurationChanged(newConfig);
	}

	// Notify Unity of the focus change.
	@Override public void onWindowFocusChanged(boolean hasFocus)
	{
		super.onWindowFocusChanged(hasFocus);
		mUnityPlayer.windowFocusChanged(hasFocus);
	}

	// For some reason the multiple keyevent type is not supported by the ndk.
	// Force event injection by overriding dispatchKeyEvent().
	@Override public boolean dispatchKeyEvent(KeyEvent event)
	{
		if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
			return mUnityPlayer.injectEvent(event);
		return super.dispatchKeyEvent(event);
	}

    /**
     * Called from Unity script (when user clicks device's back button).
     */
    public void onUnityBackPressed() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(this.getClass().getName(), "onUnityBackPressed");
                UnityPlayerActivity.super.onBackPressed();
            }
        });
    }

	// Pass any events not handled by (unfocused) views straight to UnityPlayer
	@Override public boolean onKeyUp(int keyCode, KeyEvent event)     { return mUnityPlayer.injectEvent(event); }
	@Override public boolean onKeyDown(int keyCode, KeyEvent event)   { return mUnityPlayer.injectEvent(event); }
	@Override public boolean onTouchEvent(MotionEvent event)          { return mUnityPlayer.injectEvent(event); }
	/*API12*/ public boolean onGenericMotionEvent(MotionEvent event)  { return mUnityPlayer.injectEvent(event); }
}
