package com.aneebo.storyidea.screens;

import com.aneebo.storyidea.StoryIdea;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SendStory implements Screen {

	private Stage stage;
	private Skin skin;
	private TextureAtlas atlas;
	private Table table;
	private Label textArea;
	private ScrollPane scrollPane;
	private TextField messageText;
	private TextButton sendButton;
	
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
		
		textArea = new Label("Current Story", skin);
		textArea.setWrap(true);
		scrollPane = new ScrollPane(textArea, skin);
		scrollPane.setScrollingDisabled(true, false);
		scrollPane.setFadeScrollBars(false);
		messageText = new TextField("Continue the story", skin);
		sendButton = new TextButton("Send", skin);
		sendButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sendMessageToNextPeer(messageText.getText());
			}
		});

		table.add(scrollPane).colspan(2).fillX().expandY().top().row();
		table.add(messageText).fillX().expandX();
		table.add(sendButton);
		stage.addActor(table);
	}
	
	/**
	 * Send message to next person in circle and send to screen
	 * @param str
	 */
	public void sendMessageToNextPeer(String str) {
		sendMessageToScreen(str);
		sendScrollBarToBottom();
		
		//TODO: Implement a NextPeer connection to the next participant in the tournament
		
		
		//Clear message text
		messageText.setText("");
	}

	/**
	 * Send text to the screen
	 * @param str
	 */
	public void sendMessageToScreen(String str) {
		textArea.setText(textArea.getText().toString()+"\n"+str);
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
		Gdx.app.log(StoryIdea.TITLE, "Play Screen Pause");

	}

	@Override
	public void resume() {
		Gdx.app.log(StoryIdea.TITLE, "Play Screen Resume");

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
	private void sendScrollBarToBottom() {
		scrollPane.validate();
		scrollPane.scrollTo(scrollPane.getMaxX(), -scrollPane.getMaxY(), scrollPane.getWidth(), scrollPane.getHeight());
	}
	
}
