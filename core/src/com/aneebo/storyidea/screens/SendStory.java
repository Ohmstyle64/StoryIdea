package com.aneebo.storyidea.screens;
import static com.aneebo.storyidea.StoryIdea.social;

import com.badlogic.gdx.Game;
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
	private String savedText;
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
		
		Table.drawDebug(stage);			//TODO: Remove
	}


	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
		table.invalidateHierarchy();
	}

	@Override
	public void show() {
		//Create Stage and Skin
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		atlas = new TextureAtlas("ui/atlas.pack");
		skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), atlas);
		
		//Create table for layout
		table = new Table();
		table.setFillParent(true);
		table.debug();					//TODO: Remove
		
		//Create rest of GUI
		Label header = new Label("Welcome to the Jungle", skin);
		storyArea = new TextArea("Current Story", skin);
		messageLength = (storyArea.getText() == "" ? 0 : storyArea.getText().length());
		messageTyped = storyArea.getText();
		savedText = messageTyped;
		storyArea.setFillParent(true);
		scrollPane = new ScrollPane(storyArea, skin);
		scrollPane.setScrollingDisabled(true, false);
		scrollPane.setFadeScrollBars(false);
		sendButton = new TextButton("Send", skin);
		//Add listeners
		sendButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sendMessageToSwarm(storyArea.getText());					//TODO: Remove this. Only for desktop
			}
		});
		storyArea.addListener(new InputListener() {
			@Override
			public boolean keyTyped(InputEvent event, char character) {
				switch(character) {
				case 13 :
					sendMessageToSwarm(storyArea.getText());
					return true;
				default:
					if(storyArea.getCursorPosition() < messageLength) 
						storyArea.setText(messageTyped+savedText.substring(messageLength));
					savedText = storyArea.getText();
					return true;
				}
			}
		});
		
		//Set up table
		table.add(header).colspan(3).center().row();
		table.add(scrollPane).colspan(3).expandX().fillX().expandY().fillY().top().pad(10).row();
		table.add(sendButton).colspan(3).bottom().center();
		stage.addActor(table);
	}
	
	/**
	 * Send message to next person in circle and send to screen
	 * @param str
	 */
	public void sendMessageToSwarm(String str) {
		//TODO: Implement a NextPeer connection to the next participant in the tournament
		social.sendMessage(str);
		((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu());
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
		atlas.dispose();
	}
	
}
