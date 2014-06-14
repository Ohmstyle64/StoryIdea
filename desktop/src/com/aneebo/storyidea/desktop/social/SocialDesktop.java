package com.aneebo.storyidea.desktop.social;

import com.aneebo.storyidea.circles.CircleList;
import com.aneebo.storyidea.circles.UserCircle;
import com.aneebo.storyidea.social.SocialCodeI;
import com.aneebo.storyidea.users.SocialUser;
import com.badlogic.gdx.utils.Array;

public class SocialDesktop implements SocialCodeI {

	private boolean loggedIn;
	private Array<SocialUser> friends;
	private CircleList circleList;
	private boolean initiated = false;
	
	public SocialDesktop() {}
	
	@Override
	public void login() {
		loggedIn = true;
		System.out.println("Desktop: Login");
	}

	@Override
	public void sendMessage(UserCircle uc, String str) {
		System.out.println("Desktop: "+str);
	}

	@Override
	public void logout() {
		loggedIn = false;
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
		if(!initiated) {
			System.out.println("Initiated");
			loggedIn = true;
			friends = new Array<SocialUser>(false, 2, SocialUser.class);
			SocialUser f1 = new SocialUser(0, 1, "Kevin");
			SocialUser f2 = new SocialUser(0, 2, "Eric");
			friends.add(f1);
			friends.add(f2);
			circleList = new CircleList();
			initiated = true;
		}
	}

	@Override
	public void showDashboard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CircleList getCloudCircles() {
		return circleList;
	}

	@Override
	public void saveCircleToCloud(UserCircle circles) {
		circleList.addCircle(circles);
	}

	@Override
	public boolean isInitialized() {
		return initiated;
	}

	@Override
	public String receiveMessage() {
		return null;
	}

	@Override
	public SocialUser getMeUser() {
		return new SocialUser(0,1,"Rudman");
	}

	@Override
	public UserCircle getTempCloudCircle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void requestToStartCircle(UserCircle uc) {
		// TODO Auto-generated method stub
		
	}
	

}
