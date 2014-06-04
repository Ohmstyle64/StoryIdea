package com.aneebo.storyidea.desktop.social;

import com.aneebo.storyidea.circles.CircleList;
import com.aneebo.storyidea.social.SocialCodeI;
import com.aneebo.storyidea.users.SocialUser;
import com.badlogic.gdx.utils.Array;

public class SocialDesktop implements SocialCodeI {

	private boolean loggedIn;
	private Array<SocialUser> friends;
	
	public SocialDesktop() {
		loggedIn = false;
		friends = new Array<SocialUser>(false, 2, SocialUser.class);
		SocialUser f1 = new SocialUser(0, 1, "Kevin");
		SocialUser f2 = new SocialUser(0, 2, "Eric");
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
	public Array<SocialUser> getFriends() {
		return friends;
	}
	@Override
	public void initiate() {
		System.out.println("Initiated");
	}

	@Override
	public void showDashboard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CircleList getCloudCircles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveCircleToCloud(CircleList circles) {
		// TODO Auto-generated method stub
		
	}
	

}
