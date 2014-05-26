package com.aneebo.storyidea.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.aneebo.storyidea.StoryIdea;
import com.aneebo.storyidea.android.facebook.FacebookAndroid;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		
		initialize(new StoryIdea(new FacebookAndroid()), config);
		AndroidBug5497Workaround.assistActivity(this);
	}
}
