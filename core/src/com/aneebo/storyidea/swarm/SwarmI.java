package com.aneebo.storyidea.swarm;

public interface SwarmI {
	public void login();
	public void logout();
	public boolean isLoggedIn();
	public boolean sendMessage(String str);
}
