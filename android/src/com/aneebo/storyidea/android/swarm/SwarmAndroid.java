package com.aneebo.storyidea.android.swarm;

import java.util.List;

import com.aneebo.storyidea.StoryIdea;
import com.aneebo.storyidea.swarm.SwarmI;
import com.aneebo.storyidea.swarm.SwarmUserCore;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.swarmconnect.Swarm;
import com.swarmconnect.SwarmActiveUser.GotFriendsCB;
import com.swarmconnect.SwarmUser;

public class SwarmAndroid implements SwarmI {

	private Array<SwarmUserCore> friends;
	
	public SwarmAndroid() {
		friends = new Array<SwarmUserCore>(false, 20, SwarmUserCore.class);
	}
	
	@Override
	public void login() {
		if(!Swarm.isLoggedIn()) {
			Swarm.showLogin();
		}
	}

	@Override
	public boolean sendMessage(String str) {
		Gdx.app.log(StoryIdea.TITLE, "Android: "+str);
		return false;
	}

	@Override
	public void logout() {
		if(Swarm.isLoggedIn()) {
			Swarm.logOut();
		}
	}

	@Override
	public boolean isLoggedIn() {
		return Swarm.isLoggedIn();
	}

	@Override
	public Array<SwarmUserCore> getFriends() {
		//Check if user is logged in
		if(Swarm.isLoggedIn()) {
			//Clear current friends list
			friends.clear();
			//Grab all friends
			Swarm.user.getFriends(new GotFriendsCB() {

				@Override
				public void gotFriends(List<SwarmUser> arg0) {
					//Check if friend list is null
					if(arg0 != null) {
						//Put friends into friends array
						for(SwarmUser swarmUser : arg0) {
							SwarmUserCore swarmUserCore = new SwarmUserCore(swarmUser.points, swarmUser.userId, swarmUser.username);
							friends.add(swarmUserCore);
						}
					}
				}
			});
		}
		
		return friends;
	}

}
