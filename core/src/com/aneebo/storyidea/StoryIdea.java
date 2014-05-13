package com.aneebo.storyidea;

import com.aneebo.storyidea.screens.Splash;
import com.badlogic.gdx.Game;

public class StoryIdea extends Game {
	
	public static final String TITLE = "Story Idea";
	public static final String VERSION = "0.0.0";
	
	@Override
	public void create () {
		//Start the app by loading the Splash Screen.
		setScreen(new Splash());
	}
}
