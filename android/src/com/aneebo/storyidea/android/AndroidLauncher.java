package com.aneebo.storyidea.android;

import android.os.Bundle;

import com.aneebo.storyidea.StoryIdea;
import com.aneebo.storyidea.android.swarm.SwarmAndroid;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.swarmconnect.Swarm;

public class AndroidLauncher extends AndroidApplication {
	
	
	private static final int MY_APP_D = 11620;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new StoryIdea(new SwarmAndroid()), config);
		
		//Android workaround for softkeyboard
		AndroidBug5497Workaround.assistActivity(this);
		
		//Setup Swarm
		Swarm.setActive(this);	
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Swarm.setActive(this);
		
		Swarm.init(this, MY_APP_D, "4e7484e2903dfa74fd2de6d0cca9bfd7");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		Swarm.setInactive(this);
	}
	
}
