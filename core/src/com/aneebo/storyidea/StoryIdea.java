package com.aneebo.storyidea;

import com.aneebo.storyidea.screens.Splash;
import com.aneebo.storyidea.swarm.SwarmI;
import com.badlogic.gdx.Game;

public class StoryIdea extends Game {
	
	public static final String TITLE = "Story Idea";
	public static final String VERSION = "0.0.0";
	public static int REFRESH_TIME = 15;
	
	public static SwarmI swarm;
	
	public StoryIdea(SwarmI swarm) {
		StoryIdea.swarm = swarm;
	}
	
	@Override
	public void create () {
		//Start the app by loading the Splash Screen.
		setScreen(new Splash());
	}
}
