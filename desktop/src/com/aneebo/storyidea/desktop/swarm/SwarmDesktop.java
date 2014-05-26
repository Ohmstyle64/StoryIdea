package com.aneebo.storyidea.desktop.swarm;

import com.aneebo.storyidea.swarm.SwarmI;
import com.aneebo.storyidea.swarm.SwarmUserCore;
import com.badlogic.gdx.utils.Array;

public class SwarmDesktop implements SwarmI {

	boolean loggedIn;
	
	public SwarmDesktop() {
		loggedIn = false;
	}
	
	@Override
	public void login() {
		loggedIn = true;
		System.out.println("Desktop: Login");
	}

	@Override
	public boolean sendMessage(String str) {
		System.out.println("Desktop: "+str);
		return false;
	}

	@Override
	public void logout() {
		loggedIn = true;
		System.out.println("Desktop: Logout");
	}

	@Override
	public boolean isLoggedIn() {
		return loggedIn;
	}

	@Override
	public Array<SwarmUserCore> getFriends() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
