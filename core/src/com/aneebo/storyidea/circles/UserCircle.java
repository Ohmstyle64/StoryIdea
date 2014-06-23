package com.aneebo.storyidea.circles;

import static com.aneebo.storyidea.StoryIdea.social;

import java.util.Calendar;
import java.util.Date;

import com.aneebo.storyidea.users.SocialUser;
import com.badlogic.gdx.utils.Array;

public class UserCircle {
	
	
	public static final int DEFAULT_WAIT = 86400000;
	public int ID = 0;
	
	private Date deadline;
	private Array<SocialUser> orderedUsers;
	private Array<SocialUser> users;
	private Array<String> messages;
	private float storyLines;
	private int index;
	private boolean over;
	
	public UserCircle(Array<SocialUser> activeUsers, float storyLines) {
		this(activeUsers,storyLines,DEFAULT_WAIT);
	}
	
	public UserCircle(Array<SocialUser> activeUsers, float storyLines, int waitTime) {
		this.orderedUsers = activeUsers;
		this.users = activeUsers;
		this.storyLines = storyLines;
		
		messages = new Array<String>(true, (int) storyLines, String.class);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MILLISECOND, waitTime);
		deadline = cal.getTime();
		
		this.over = false;
		index = 0;
		
		setupUsers();
	}
	
	public UserCircle() {}
	
	private void setupUsers() {
		orderedUsers.shuffle();
		this.orderedUsers.insert(0, social.getMeUser());
		int start = orderedUsers.size;
		orderedUsers.ensureCapacity((int) (storyLines)-orderedUsers.size+1);
		for(int i = start; i < storyLines; i++) {
			orderedUsers.insert(i, orderedUsers.items[i-start]);
		}
	}
	
	public long timeRemaining() {
		Date today = new Date();
		long diff = deadline.getTime() - today.getTime();
		if(diff < 0 ) {
			diff = 0;
			over = true;
		}
		return diff;
	}
	
	public boolean isOver() {
		return over;
	}
	
	public SocialUser next(String message) {
		messages.items[index] = message;
		index++;
		return orderedUsers.items[index];
	}
	
	public SocialUser currentUser() {
		return orderedUsers.items[index];
	}
	
	public float getStoryLines() {
		return storyLines;
	}

	public Array<SocialUser> getOrderedUsers() {
		return orderedUsers;
	}

	public Array<String> getUserNames() {
		Array<String> usernames = new Array<String>(false, users.size,String.class);
		
		for(SocialUser user : users)
			usernames.add(user.getUsername());
		
		return usernames;
	}
	
	
}
