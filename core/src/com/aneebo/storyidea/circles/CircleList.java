package com.aneebo.storyidea.circles;

import com.badlogic.gdx.utils.Array;

public class CircleList {
	
	private Array<UserCircle> circle;
	
	public CircleList(Array<UserCircle> circle) {
		this.circle = circle;
	}
	
	public Array<Integer> getCircleIds() {
		Array<Integer> ids = new Array<Integer>(false,circle.size, Integer.class);
		for(UserCircle uc : circle) {
			ids.add(uc.ID);
		}
		return ids;
	}
	
	public void addCircle(UserCircle uc) {
		circle.add(uc);
	}
	
	public void removeCircle(UserCircle uc) {
		circle.removeValue(uc, true);
	}

	public Array<UserCircle> getCircle() {
		return circle;
	}

	public Array<String> getDisplayInfo() {
		Array<String> displayInfo = new Array<String>(false, circle.size, String.class);
		StringBuilder sb = new StringBuilder(5);
		for(UserCircle uc : circle) {
			sb.append("Circle: ");
			sb.append(uc.ID);
			sb.append("  ");
			sb.append("Time: ");
			sb.append(uc.timeRemaining());
			displayInfo.add(sb.toString());
			sb.setLength(0);
		}
		return displayInfo;
	}
}
