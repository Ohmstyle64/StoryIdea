package com.aneebo.storyidea.android;

import android.os.Bundle;

import com.aneebo.storyidea.StoryIdea;
import com.aneebo.storyidea.android.social.SocialAndroid;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.swarmconnect.Swarm;

public class AndroidLauncher extends AndroidApplication {
	
	
	public static final int MY_APP_D = 11620;
	public static final String SWARM_ID = "4e7484e2903dfa74fd2de6d0cca9bfd7";
	public static final String API_KEY = "12df9009336e851d29ee089ad26cbb12520f36738ccd4be1aedd54d7e04d06d9";
	public static final String PVT_KEY = "9cc0f85506c3eb27cbbb0e855d5a877c342f9449e4768cc5ab77ebf8ee873af7";
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new StoryIdea(new SocialAndroid(this)), config);
		
		//Android workaround for softkeyboard
		AndroidBug5497Workaround.assistActivity(this);
		
		Swarm.preload(this, MY_APP_D, SWARM_ID);
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(Swarm.isEnabled()) Swarm.setActive(this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if(Swarm.isEnabled()) Swarm.setInactive(this);
	}
	
}
