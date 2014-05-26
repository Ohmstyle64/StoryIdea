package com.aneebo.storyidea.Circles;

import com.aneebo.storyidea.swarm.SwarmUserCore;
import com.badlogic.gdx.utils.Array;

public class UserCircle {
	private Array<SwarmUserCore> activeUsers;
	private int storyLines;
	
	public UserCircle(Array<SwarmUserCore> activeUsers, int storyLines) {
		this.activeUsers = activeUsers;
		this.storyLines = storyLines;
	}
}
