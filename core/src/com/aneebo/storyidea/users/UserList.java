package com.aneebo.storyidea.users;

import com.badlogic.gdx.utils.Array;

public class UserList {
	
	private Array<SocialUser> userList;
	
	public UserList(Array<SocialUser> userlist) {
		this.userList = userlist;
	}
	
	public Array<String> getUserNames() {
		Array<String> userNameList = new Array<String>(false, userList.size, String.class);
		for(SocialUser user : userList)
			userNameList.add(user.getUsername());
		return userNameList;		
	}
}
