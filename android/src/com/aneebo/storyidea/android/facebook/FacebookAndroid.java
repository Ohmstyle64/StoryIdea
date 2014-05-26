package com.aneebo.storyidea.android.facebook;

import com.aneebo.storyidea.StoryIdea;
import com.aneebo.storyidea.facebook.FacebookI;
import com.badlogic.gdx.Gdx;

public class FacebookAndroid implements FacebookI {

	@Override
	public void login() {
		Gdx.app.log(StoryIdea.TITLE, "Android: login");
	}

	@Override
	public boolean sendMessage(String str) {
		Gdx.app.log(StoryIdea.TITLE, "Android: "+str);
		return false;
	}

}
