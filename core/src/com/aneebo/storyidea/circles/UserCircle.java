package com.aneebo.storyidea.circles;

import static com.aneebo.storyidea.StoryIdea.social;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import com.aneebo.storyidea.users.SocialUser;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class UserCircle {
	
	public static final String DELIM = "||||";
	
	public static final int DEFAULT_WAIT = 86400000;
	public int ID = 0;
	
	private Date deadline;
	private Array<SocialUser> activeUsers;
	private Array<String> messages;
	private float storyLines;
	private int index;
	private boolean over;
	
	public UserCircle(Array<SocialUser> activeUsers, float storyLines) {
		this(activeUsers,storyLines,DEFAULT_WAIT);
	}
	
	public UserCircle(Array<SocialUser> activeUsers, float storyLines, int waitTime) {
		this.activeUsers = activeUsers;
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
		activeUsers.shuffle();
		this.activeUsers.insert(0, social.getMeUser());
		int start = activeUsers.size;
		activeUsers.ensureCapacity((int) (storyLines)-activeUsers.size+1);
		for(int i = start; i < storyLines; i++) {
			activeUsers.insert(i, activeUsers.items[i-start]);
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
		return activeUsers.items[index];
	}
	
	public SocialUser currentUser() {
		return activeUsers.items[index];
	}
	
	public float getStoryLines() {
		return storyLines;
	}

	public Array<SocialUser> getActiveUsers() {
		return activeUsers;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(ID).append("||||");
		sb.append(deadline.getTime()).append("||||");
		for(SocialUser user : activeUsers)
			sb.append(user.getUsername()).append(",");
		sb.deleteCharAt(sb.lastIndexOf(",")).append("||||");
		sb.append(storyLines).append("||||");
		sb.append(index);
		return sb.toString();
	}
	
}
