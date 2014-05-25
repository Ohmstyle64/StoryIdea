package com.aneebo.storyidea.screens;

import com.aneebo.storyidea.StoryIdea;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SendStory implements Screen {

	private Stage stage;
	private Skin skin;
	private TextureAtlas atlas;
	private Table table;
	private ScrollPane scrollPane;
	private TextButton sendButton;
	private TextArea storyArea;
	private int messageLength = 0;
	private String messageTyped;
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
		
		Table.drawDebug(stage);
	}


	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
		table.invalidateHierarchy();
	}

	@Override
	public void show() {

		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		atlas = new TextureAtlas("ui/atlas.pack");
		skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), atlas);
		
		table = new Table();
		table.setFillParent(true);
		table.debug();
		
		storyArea = new TextArea("Current Story", skin);
		messageLength = (storyArea.getText() == "" ? 0 : storyArea.getText().length());
		messageTyped = storyArea.getText();
		storyArea.setFillParent(true);
		scrollPane = new ScrollPane(storyArea, skin);
		scrollPane.setScrollingDisabled(true, false);
		scrollPane.setFadeScrollBars(false);
		sendButton = new TextButton("Send", skin);
		sendButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sendMessageToNextPeer(storyArea.getText());
			}
		});
		storyArea.setTextFieldFilter(new TextFieldFilter() {
			
			@Override
			public boolean acceptChar(TextField textField, char c) {
				if(c == 'a') {
					Gdx.app.log(StoryIdea.TITLE, "Cur Length: "+storyArea.getText().length());		
					return false;
				}
				return true;
			}
		});
		storyArea.addListener(new InputListener() {
			@Override
			public boolean keyTyped(InputEvent event, char character) {
				switch(character) {
				case 13 :
					sendMessageToNextPeer(storyArea.getText());
					return true;
				case 8 :
					if(messageLength >= storyArea.getText().length()) {
						storyArea.setText(messageTyped);
						return true;
					}
				}
				return false;
			}
		});
		
		
		table.add(new Label("The Story Continues", skin)).colspan(3).center().row();
		table.add(scrollPane).colspan(3).expandX().fillX().expandY().fillY().top().pad(10).row();
		table.add(sendButton).colspan(3).bottom().center();
		stage.addActor(table);
	}
	
	/**
	 * Send message to next person in circle and send to screen
	 * @param str
	 */
	public void sendMessageToNextPeer(String str) {
//		sendScrollBarToBottom();
		
		//TODO: Implement a NextPeer connection to the next participant in the tournament
		
	}

	/**
	 * Send text to the screen
	 * @param str
	 */
	public void sendMessageToScreen(String str) {
		storyArea.setText(storyArea.getText().toString()+"\n"+str);
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
	}
	/**
	 * Send the scroll bar to the bottom of the screen.
	 * Is called everytime there is text entered to the screen
	 */
//	private void sendScrollBarToBottom() {
//		scrollPane.validate();
//		scrollPane.scrollTo(scrollPane.getMaxX(), -scrollPane.getMaxY(), scrollPane.getWidth(), scrollPane.getHeight());
//	}
	
}
