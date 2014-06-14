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
	public static final String API_KEY = "9fc34c63050554fc82647d8191af84ca14b64c74e4fdc15762afa56659f94600";
	public static final String PVT_KEY = "60d1338661c40f61fb4bd543e5df40fd6d0b5447d80b42bcc05c89600220b714";
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new StoryIdea(SocialAndroid.getInstance(this)), config);
		
		//Android workaround for softkeyboard
		AndroidBug5497Workaround.assistActivity(this);
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
