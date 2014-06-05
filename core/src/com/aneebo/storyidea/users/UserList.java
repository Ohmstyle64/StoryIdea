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
	
	public Array<SocialUser> getUserSubset(Array<String> usernames) {
		Array<SocialUser> userSubset = new Array<SocialUser>(false, usernames.size, SocialUser.class);
		int lengthUL = userList.size;
		int lengthUN = usernames.size;
		for(int i = 0; i < lengthUL; i++) {
			String username = userList.items[i].getUsername();
			for(int j = 0; j < lengthUN; j++) {
				if(username.equals(usernames.get(j))){
					userSubset.add(userList.items[i]);
					break;
				}
			}
		}
		return userSubset;
	}
}
