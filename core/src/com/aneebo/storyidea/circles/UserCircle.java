package com.aneebo.storyidea.circles;

import com.badlogic.gdx.utils.Array;

public class UserCircle {
	private Array<SwarmUserCore> activeUsers;
	private int storyLines;
	public static int ID = 0;
	
	public UserCircle(Array<SwarmUserCore> activeUsers, int storyLines) {
		this.activeUsers = activeUsers;
		this.storyLines = storyLines;
		ID++;
	}
	
	
}
