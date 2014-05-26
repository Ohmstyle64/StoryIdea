package com.aneebo.storyidea;

import com.aneebo.storyidea.facebook.FacebookI;
import com.aneebo.storyidea.screens.SendStory;
import com.badlogic.gdx.Game;

public class StoryIdea extends Game {
	
	public static final String TITLE = "Story Idea";
	public static final String VERSION = "0.0.0";
	
	public static FacebookI fb;
	
	public StoryIdea(FacebookI fb) {
		StoryIdea.fb = fb;
	}
	
	@Override
	public void create () {
		//Start the app by loading the Splash Screen.
		setScreen(new SendStory());
	}
}
