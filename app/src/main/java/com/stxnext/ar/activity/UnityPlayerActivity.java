package com.stxnext.ar.activity;

import com.stxnext.ar.R;
import com.stxnext.ar.adapter.DrawerAdapter;
import com.stxnext.ar.model.DrawerMenuItems;
import com.stxnext.ar.util.Preferences;
import com.unity3d.player.*;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;

public class UnityPlayerActivity extends AppCompatActivity {

	protected UnityPlayer mUnityPlayer; // don't change the name of this variable; referenced from native code
	private Button backButton;
	private FrameLayout unityContainer;
	private Toolbar toolbar;
	private DrawerLayout drawerLayout;
	private ActionBarDrawerToggle drawerToggle;

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
		toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Now it is set in style for toolbar.
//        toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white));
		setSupportActionBar(toolbar);
		getSupportActionBar().setTitle(R.string.app_name);
        configureDrawer();
		unityContainer = (FrameLayout) findViewById(R.id.unity_container);
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
		unityContainer.addView(mUnityPlayer.getView(), 0, layoutParams);
		mUnityPlayer.requestFocus();
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

	private void configureDrawer() {
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		ListView drawerList = (ListView) findViewById(R.id.left_drawer);
        final DrawerAdapter drawerAdapter = new DrawerAdapter(this);
        drawerList.setAdapter(drawerAdapter);
		drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DrawerMenuItems menuItem = drawerAdapter.getItem(position);
                switch(menuItem) {
                    case STX_LOGO:
                        Log.d(UnityPlayerActivity.this.getClass().getName(), "activateLogo request");
                        mUnityPlayer.UnitySendMessage(UNITY_OBJECT_NAME, "activateLogo", "");
                        break;
                    case DINOSAUR:
                        Log.d(UnityPlayerActivity.this.getClass().getName(), "activateDinosaur request");
                        mUnityPlayer.UnitySendMessage(UNITY_OBJECT_NAME, "activateDinosaur", "");
                        break;
                    case CAT:
                        Log.d(UnityPlayerActivity.this.getClass().getName(), "activateCat request");
                        mUnityPlayer.UnitySendMessage(UNITY_OBJECT_NAME, "activateCat", "");
                        break;
                    case TUTORIAL:
                        Log.d(UnityPlayerActivity.this.getClass().getName(), "Run Tutorial");
                        runTutorial();
                        break;
                    case CLOSE:
                        Log.d(UnityPlayerActivity.this.getClass().getName(), "Close application.");
                        finish();
                }

                drawerLayout.closeDrawers();
            }
        });
		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                toolbar.animate().alpha(1).setDuration(150);
//                getSupportActionBar().setTitle(R.string.app_name);
//                drawerToggle.setDrawerIndicatorEnabled(true);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                toolbar.animate().alpha(0).setDuration(150);
//                getSupportActionBar().setTitle("");
//                drawerToggle.setDrawerIndicatorEnabled(false);
            }
        };
		drawerLayout.setDrawerListener(drawerToggle);

	}

    private void runTutorial() {
        Intent tutorialIntent = new Intent(this, TutorialActivity.class);
        startActivityForResult(tutorialIntent, StartActivity.TUTORIAL_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case StartActivity.TUTORIAL_REQUEST:
                if (resultCode == TutorialActivity.RESULT_OK) {
                    Preferences.getInstance(this).setTutorialDone(true);
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return drawerToggle.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawerToggle.syncState();
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

	@Override
	public void onBackPressed() {

		if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
			drawerLayout.closeDrawers();
		} else {
            super.onBackPressed();
        }
	}

	/**
     * Called from Unity script (when user clicks device's back button).
     */
    public void onUnityBackPressed() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(this.getClass().getName(), "onUnityBackPressed");
                UnityPlayerActivity.this.onBackPressed();
            }
        });
    }

	// Pass any events not handled by (unfocused) views straight to UnityPlayer
	@Override public boolean onKeyUp(int keyCode, KeyEvent event)     { return mUnityPlayer.injectEvent(event); }
	@Override public boolean onKeyDown(int keyCode, KeyEvent event)   { return mUnityPlayer.injectEvent(event); }
	@Override public boolean onTouchEvent(MotionEvent event)          { return mUnityPlayer.injectEvent(event); }
	/*API12*/ public boolean onGenericMotionEvent(MotionEvent event)  { return mUnityPlayer.injectEvent(event); }
}
