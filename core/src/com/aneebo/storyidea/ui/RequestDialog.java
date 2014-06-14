package com.aneebo.storyidea.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class RequestDialog extends Dialog {

	public RequestDialog(String title, Skin skin) {
		super(title, skin);
	}

	public RequestDialog(String title, WindowStyle windowStyle) {
		super(title, windowStyle);
	}

	public RequestDialog(String title, Skin skin, String windowStyleName) {
		super(title, skin, windowStyleName);
	}

}
