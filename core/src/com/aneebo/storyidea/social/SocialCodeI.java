package com.aneebo.storyidea.social;

import com.aneebo.storyidea.circles.CircleList;
import com.aneebo.storyidea.circles.UserCircle;
import com.aneebo.storyidea.users.SocialUser;
import com.badlogic.gdx.utils.Array;

public interface SocialCodeI {
	public void login();
	public void logout();
	public boolean isLoggedIn();
	public boolean isInitialized();
	public void sendMessage(UserCircle uc, String str);
	public String receiveMessage();
	public Array<SocialUser> getFriends();
	public void initiate();
	public void showDashboard();
	public CircleList getCloudCircles();
	//TODO:Remove
	public UserCircle getTempCloudCircle();
	public void saveCircleToCloud(UserCircle circles);
	public SocialUser getMeUser();
	
	
	//TODO:Use this new structure
	public void requestToStartCircle(UserCircle uc);
	
}
