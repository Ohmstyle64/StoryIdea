package com.aneebo.storyidea.circles;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.aneebo.storyidea.users.SocialUser;
import com.badlogic.gdx.utils.Array;

public class UserCircle {
	
	public static final int DEFAULT_WAIT = 86400000;
	public static int ID = 0;
	
	private Date deadline;
	private Array<SocialUser> activeUsers;
	private int storyLines;
	private boolean over;
	
	public UserCircle(Array<SocialUser> activeUsers, int storyLines) {
		this(activeUsers,storyLines,DEFAULT_WAIT);
	}
	
	public UserCircle(Array<SocialUser> activeUsers, int storyLines, int waitTime) {
		this.activeUsers = activeUsers;
		this.storyLines = storyLines;
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MILLISECOND, waitTime);
		deadline = cal.getTime();
		
		this.over = false;
		
		ID++;
	}
	
	
	public long timeRemaining() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.HOUR_OF_DAY, 5);
		Date today = cal.getTime();
		long diff = deadline.getTime() - today.getTime();
		System.out.println(deadline.getTime());
		System.out.println(today.getTime());
		System.out.println(diff);
		if(diff < 0 ) {
			diff = 0;
			over = true;
		}
		return diff / 1000;
	}
	
	public boolean isOver() {
		return over;
	}
	
	public int getStoryLines() {
		return storyLines;
	}
	
	public int userStoryLine() {
		storyLines --;
		return storyLines;
	}

	public Array<SocialUser> getActiveUsers() {
		return activeUsers;
	}
	
	
}
