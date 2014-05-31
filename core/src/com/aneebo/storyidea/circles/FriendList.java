package com.aneebo.storyidea.circles;

import com.badlogic.gdx.utils.Array;

public class FriendList {
	private Array<SwarmUserCore> friends;
	private Array<String> userNames;
	
	public FriendList(Array<SwarmUserCore> friends) {
		this.friends = friends;
		setUserNames();
	}

	private  void setUserNames() {
		userNames = new Array<String>(false, friends.size, String.class);
		for(int i = 0; i < friends.size; i++)
			userNames.items[i] = friends.items[i].getUsername();
	}
	
	public Array<SwarmUserCore> getFriends() {
		return friends;
	}

	public void setFriends(Array<SwarmUserCore> friends) {
		this.friends = friends;
		setUserNames();
	}

	public Array<String> getUserNames() {
		return userNames;
	}
}
