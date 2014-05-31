package com.aneebo.storyidea.swarm;

import com.aneebo.storyidea.circles.SwarmUserCore;
import com.badlogic.gdx.utils.Array;

public interface SwarmI {
	public void login();
	public void logout();
	public boolean isLoggedIn();
	public boolean sendMessage(String str);
	public Array<SwarmUserCore> getFriends();
}
