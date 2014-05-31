package com.aneebo.storyidea.desktop.swarm;

import com.aneebo.storyidea.circles.SwarmUserCore;
import com.aneebo.storyidea.swarm.SwarmI;
import com.badlogic.gdx.utils.Array;

public class SwarmDesktop implements SwarmI {

	private boolean loggedIn;
	private Array<SwarmUserCore> friends;
	
	public SwarmDesktop() {
		loggedIn = false;
		friends = new Array<SwarmUserCore>(false, 2, SwarmUserCore.class);
		SwarmUserCore f1 = new SwarmUserCore(0, 1, "Kevin");
		SwarmUserCore f2 = new SwarmUserCore(0, 2, "Eric");
		friends.add(f1);
		friends.add(f2);
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
		return friends;
	}
	
	

}
