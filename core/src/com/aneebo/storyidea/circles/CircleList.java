package com.aneebo.storyidea.circles;

import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.utils.Array;

public class CircleList {
	
	private Array<UserCircle> circle;
	private int count;
	
	public CircleList(Array<UserCircle> circle) {
		this.circle = circle;
		count = 0;
	}
	
	public CircleList() {
		this(new Array<UserCircle>());
	}
		
	public void addCircle(UserCircle uc) {
		count++;
		uc.ID = count;
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
			sb.append("Ends: ");
			sb.append(getTime(uc));
			displayInfo.add(sb.toString());
			sb.setLength(0);
		}
		return displayInfo;
	}
	private String getTime(UserCircle uc) {
		long millis = uc.timeRemaining();
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
		return String.format("%02d:%02d:%02d", hours,minutes,seconds);
	}
}
