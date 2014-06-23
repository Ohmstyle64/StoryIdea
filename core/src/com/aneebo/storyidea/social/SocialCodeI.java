package com.aneebo.storyidea.social;

import com.aneebo.storyidea.circles.UserCircle;
import com.aneebo.storyidea.users.SocialUser;
import com.badlogic.gdx.utils.Array;

public interface SocialCodeI {
	public void login();
	public void logout();
	public boolean isLoggedIn();
	public boolean isInitialized();
	public void sendMessage(String str);
	public String receiveMessage();
	public Array<SocialUser> getFriends();
	public void initiate();
	public void showDashboard();
	//TODO:Remove
	public SocialUser getMeUser();
	public void sendCircleRequest();
	
	
	
}
