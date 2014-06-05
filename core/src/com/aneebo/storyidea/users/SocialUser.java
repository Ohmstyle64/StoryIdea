package com.aneebo.storyidea.users;

public class SocialUser {
	private int points, userId;
	private String username;
	
	public SocialUser(int points, int userId, String username) {
		this.points = points;
		this.userId = userId;
		this.username = username;
	}
	
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof SocialUser){
			SocialUser user = (SocialUser)obj;
			return user.username.equals(username);
		}
		return super.equals(obj);
	}
}
