package com.aneebo.storyidea.android;

import android.os.Bundle;

import com.aneebo.storyidea.StoryIdea;
import com.aneebo.storyidea.android.social.SocialAndroid;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.swarmconnect.Swarm;

public class AndroidLauncher extends AndroidApplication {
	
	
	public static final int MY_APP_D = 11620;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new StoryIdea(new SocialAndroid(this)), config);
		
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
