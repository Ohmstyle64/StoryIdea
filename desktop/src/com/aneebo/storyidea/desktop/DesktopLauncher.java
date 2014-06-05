package com.aneebo.storyidea.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.aneebo.storyidea.StoryIdea;
import com.aneebo.storyidea.desktop.social.SocialDesktop;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = StoryIdea.TITLE +" v"+ StoryIdea.VERSION;
		config.vSyncEnabled = true;
		config.width = 600;
		config.height = 800;
		new LwjglApplication(new StoryIdea(new SocialDesktop()), config);
	}
}
