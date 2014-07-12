package com.aneebo.storyidea.android;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aneebo.storyidea.StoryIdea;
import com.aneebo.storyidea.android.social.SocialAndroid;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.swarmconnect.Swarm;

@SuppressLint("ShowToast")
public class AndroidLauncher extends AndroidApplication {
	
	public static final int MY_APP_D = 11620;
	public static final String SWARM_ID = "4e7484e2903dfa74fd2de6d0cca9bfd7";
	public static final String API_KEY = "9fc34c63050554fc82647d8191af84ca14b64c74e4fdc15762afa56659f94600";
	public static final String PVT_KEY = "60d1338661c40f61fb4bd543e5df40fd6d0b5447d80b42bcc05c89600220b714";
	public static final String GOOGLE_PROJECT_ID = "948480805556";
	
	private static final String AD_ID = "ca-app-pub-5851825395455390/3467746466";
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	
	public Toast toast;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useWakelock = true;
		config.useCompass = false;
		config.useAccelerometer = false;

        // Create the layout
        RelativeLayout layout = new RelativeLayout(this);

        // Do the stuff that initialize() would do for you
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        //Check that Google Play Services is working
        checkPlayService();
        // Create the libgdx View
        View gameView = initializeForView(new StoryIdea(SocialAndroid.getInstance(this))); 
        
        // Create AdView
//        AdView adView = new AdView(this);
//        adView.setAdSize(AdSize.BANNER);
//        adView.setAdUnitId(AD_ID);
//        
//        final TelephonyManager tm =(TelephonyManager)getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
//        String deviceid = tm.getDeviceId();
//        
//        AdRequest adRequest = new AdRequest.Builder()
//        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//        .addTestDevice(deviceid)
//        .build();
//        
//        adView.loadAd(adRequest);
        
        // Add Libgdx View
        layout.addView(gameView);
        
        // Add the AdMob view
//        RelativeLayout.LayoutParams adParams = 
//                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 
//                                RelativeLayout.LayoutParams.WRAP_CONTENT);
//        adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//        adParams.addRule(RelativeLayout.CENTER_IN_PARENT);
//
//        layout.addView(adView, adParams);
        
        // Hook it all up
        setContentView(layout);
        
		//Android workaround for softkeyboard
		AndroidBug5497Workaround.assistActivity(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(Swarm.isEnabled()) Swarm.setActive(this);
		checkPlayService();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if(Swarm.isEnabled()) Swarm.setInactive(this);
		checkPlayService();
	}
	
	private void checkPlayService() {
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
	    if (resultCode != ConnectionResult.SUCCESS) {
	        if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
	            GooglePlayServicesUtil.getErrorDialog(resultCode, this,
	                    PLAY_SERVICES_RESOLUTION_REQUEST).show();
	        } else {
	            finish();
	        }
	    }
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		//TODO: Implement dialog box for accepting circle request
	}
}
