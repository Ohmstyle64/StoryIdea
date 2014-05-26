package com.aneebo.storyidea.desktop.swarm;

import com.aneebo.storyidea.swarm.SwarmI;

public class SwarmDesktop implements SwarmI {

	@Override
	public void login() {
		// TODO Auto-generated method stub
		System.out.println("Desktop: Login");
	}

	@Override
	public boolean sendMessage(String str) {
		System.out.println("Desktop: "+str);
		return false;
	}

}
