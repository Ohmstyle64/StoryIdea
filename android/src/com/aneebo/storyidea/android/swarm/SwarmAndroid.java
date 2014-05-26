package com.aneebo.storyidea.android.swarm;

import com.aneebo.storyidea.StoryIdea;
import com.aneebo.storyidea.swarm.SwarmI;
import com.badlogic.gdx.Gdx;
import com.swarmconnect.Swarm;

public class SwarmAndroid implements SwarmI {

	@Override
	public void login() {
		if(!Swarm.isLoggedIn()) {
			Swarm.showLogin();
		}
	}

	@Override
	public boolean sendMessage(String str) {
		Gdx.app.log(StoryIdea.TITLE, "Android: "+str);
		return false;
	}

	@Override
	public void logout() {
		if(Swarm.isLoggedIn()) {
			Swarm.logOut();
		}
	}

	@Override
	public boolean isLoggedIn() {
		return Swarm.isLoggedIn();
	}

}
