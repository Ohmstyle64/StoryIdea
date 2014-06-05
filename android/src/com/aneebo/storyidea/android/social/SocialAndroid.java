package com.aneebo.storyidea.android.social;

import java.util.List;

import android.app.Activity;

import com.aneebo.storyidea.StoryIdea;
import com.aneebo.storyidea.android.AndroidLauncher;
import com.aneebo.storyidea.circles.CircleList;
import com.aneebo.storyidea.circles.UserCircle;
import com.aneebo.storyidea.social.SocialCodeI;
import com.aneebo.storyidea.users.SocialUser;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.swarmconnect.Swarm;
import com.swarmconnect.SwarmActiveUser.GotFriendsCB;
import com.swarmconnect.SwarmUser;

public class SocialAndroid implements SocialCodeI {

	private Activity activity;
	private Array<SocialUser> friends;
	
	public SocialAndroid(Activity activity) {
		this.activity = activity;
		friends = new Array<SocialUser>(false,20,SocialUser.class);
	}
	
	@Override
	public void login() {
		if(!Swarm.isLoggedIn()) Swarm.showLogin();
	}

	@Override
	public boolean sendMessage(String str) {
		Gdx.app.log(StoryIdea.TITLE, "Android: "+str);
		return false;
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
						for(SwarmUser swarmUser : arg0) {
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
			Swarm.init(activity, AndroidLauncher.MY_APP_D, AndroidLauncher.SWARM_ID);
						
			WarpClient.initialize(AndroidLauncher.API_KEY, AndroidLauncher.PVT_KEY);

			
		}
	}

	@Override
	public CircleList getCloudCircles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveCircleToCloud(UserCircle circles) {
		// TODO Auto-generated method stub
		
	}
}
