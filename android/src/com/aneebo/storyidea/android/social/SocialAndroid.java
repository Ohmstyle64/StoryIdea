package com.aneebo.storyidea.android.social;

import java.util.List;

import android.app.Activity;

import com.aneebo.storyidea.android.AndroidLauncher;
import com.aneebo.storyidea.android.App42Registration;
import com.aneebo.storyidea.android.social.warp.WarpController;
import com.aneebo.storyidea.social.SocialCodeI;
import com.aneebo.storyidea.users.SocialUser;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.shephertz.app42.paas.sdk.android.App42API;
import com.swarmconnect.Swarm;
import com.swarmconnect.SwarmActiveUser;
import com.swarmconnect.SwarmActiveUser.GotFriendsCB;
import com.swarmconnect.SwarmUser;
import com.swarmconnect.delegates.SwarmLoginListener;

public class SocialAndroid implements SocialCodeI {
	public SocialUser meUser;
	
	private Activity activity;
	private Array<SocialUser> friends;
	private WarpController warpController;
	private App42Registration app42Registration;
	
	private static SocialAndroid instance;

	private SocialAndroid(Activity activity) {
		this.activity = activity;
		friends = new Array<SocialUser>(false,20,SocialUser.class);
	}
	
	public static SocialAndroid getInstance(Activity activity) {
		if(instance==null) {
			instance = new SocialAndroid(activity);
		}
		return instance;
	}
	
	
	@Override
	public void login() {
		if(!Swarm.isLoggedIn())	Swarm.showLogin();
	}

	@Override
	public void sendMessage(String str) {
		warpController.warpClient.sendChat(str);
		
	}

	@Override
	public void logout() {
		if(Swarm.isLoggedIn()) Swarm.logOut();
	}

	@Override
	public boolean isLoggedIn() {
		return Swarm.isLoggedIn();
	}

	@Override
	public Array<SocialUser> getFriends() {
		if(Swarm.isLoggedIn()) {
			
			friends.clear();
			
			Swarm.user.getFriends(new GotFriendsCB() {
				
				@Override
				public void gotFriends(List<SwarmUser> arg0) {
					if(arg0 != null) {
						for(final SwarmUser swarmUser : arg0) {
							SocialUser user = new SocialUser(swarmUser.points, swarmUser.userId, swarmUser.username);
							friends.add(user);
						}
					}
					friends.shrink();
				}
			});
		}
		return friends;
	}
	
	@Override
	public void showDashboard() {
		if(Swarm.isInitialized()) Swarm.showDashboard();
	}
	
	@Override
	public void initiate() {
		if(!Swarm.isInitialized()) {
			Swarm.init(activity, AndroidLauncher.MY_APP_D, AndroidLauncher.SWARM_ID, new SwarmLoginListener() {
				
				@Override
				public void userLoggedOut() {
					warpController.warpClient.disconnect();
					meUser = null;
				}
				
				@Override
				public void userLoggedIn(SwarmActiveUser arg0) {
					meUser = new SocialUser(arg0.points,arg0.userId,arg0.username);
					warpController = WarpController.getInstance(instance);
					warpController.warpClient.connectWithUserName(arg0.username);
					App42API.initialize(activity, AndroidLauncher.API_KEY, AndroidLauncher.PVT_KEY);
					App42API.setLoggedInUser(arg0.username);
					app42Registration = new App42Registration(activity);
				}
				
				@Override
				public void loginStarted() {
					// TODO Auto-generated method stub
				}
				
				@Override
				public void loginCanceled() {
					// TODO Auto-generated method stub
					
				}
			});
		}
	}


	@Override
	public boolean isInitialized() {
		return Swarm.isInitialized();
	}

	@Override
	public String receiveMessage() {
		return warpController.message;
	}
	
	@Override
	public SocialUser getMeUser() {
		return meUser;
	}
	
	public Activity getActivit() {
		return activity;
	}

	@Override
	public void sendCircleRequest() {
		Json json = new Json();
		app42Registration.sendMessage(meUser.getUsername(), "This worked!");
	}

}
