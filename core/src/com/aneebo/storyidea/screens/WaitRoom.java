package com.aneebo.storyidea.screens;

import static com.aneebo.storyidea.StoryIdea.social;
import com.aneebo.storyidea.circles.UserCircle;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class WaitRoom implements Screen {
	
	private Image background;
	private Skin skin;
	private TextureAtlas atlas;
	private SpriteBatch batch;
	private Stage stage;
	private Table table;
	private Label chatArea;
	private List<String> chatUsers;

	private String messages;
	private UserCircle uc;
	
	// hmmmmmmmm
	public WaitRoom(UserCircle uc) {
		this.uc = uc;
	}
	
	// Used for joining an already created room
	public WaitRoom() {}

	@Override
	public void render(float delta) {
		//Clear Screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//Draw Background
		batch.begin();
		background.draw(batch, 1);
		batch.end();
		
		
		//Let the stage act
		stage.act(delta);
		stage.draw();
		
		//TODO: Remove this. Draws debug lines
		Table.drawDebug(stage);	
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
		table.invalidateHierarchy();
	}

	@Override
	public void show() {
		//Setup Menu
		batch = new SpriteBatch();
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		atlas = new TextureAtlas("ui/defaultskin.atlas");
		skin = new Skin(Gdx.files.internal("ui/defaultskin.json"),atlas);
		
		table = new Table();
		table.setFillParent(true);
		//TODO: Remove this.
		table.debug();
		
		//Current User Label
		Label currentUser = new Label("Current Author: ", skin);
		
		//Next User Label
		Label nextUser = new Label("Next Author: ", skin);
		
		//Story Lines left Label
		Label storyLinesLeft = new Label("Turns Left: ", skin);
		
		//Timer Label
		Label timeLeft = new Label("Time Left: ", skin);
		
		//List & Scroll of Active Chat Users
		chatUsers = new List<String>(skin);
//		chatUsers.setItems(uc.getUserNames());
		final ScrollPane userScroll = new ScrollPane(chatUsers, skin);
		userScroll.setOverscroll(false, false);
		userScroll.setFadeScrollBars(false);
		
		//Label & Scroll for chat area
		messages = "Chat while you wait!";
		chatArea = new Label(messages, skin);
		chatArea.setWrap(true);
		chatArea.setAlignment(Align.top | Align.left, Align.left);
		final ScrollPane chatScroll = new ScrollPane(chatArea, skin);
		chatScroll.setOverscroll(false, false);
		chatScroll.setFadeScrollBars(false);
		
		//Textfield for chat input
		final TextField chatInput = new TextField("", skin);
		
		//TextButton for sending chat message
		TextButton chatSend = new TextButton("Send", skin);
		chatSend.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				String message = chatInput.getText();
				
				social.sendMessage(message);
				
				sendTextToScreen(social.getMeUser().getUsername()+": "+message);
				chatInput.setText("");
				chatScroll.validate();
				chatScroll.setScrollPercentY(1);
			}
		});
		
		//TextButton for leaving room
		TextButton leaveRoom = new TextButton("Leave", skin);
		leaveRoom.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu());
			}
		});
		
		//Add background
		background = new Image(new Texture("img/sampleSplash.jpg"));
		background.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		Label heading = new Label("Waiting Area!", skin);
		heading.scaleBy(3);
		
		//Set up table
		table.add(heading).center().colspan(2).row();
		table.add(currentUser).left().colspan(2).space(10).row();
		table.add(nextUser).left().colspan(2).space(10).row();
		table.add(storyLinesLeft).left().colspan(2).space(10).row();
		table.add(timeLeft).left().colspan(4).space(10).row();
		table.add(chatScroll).fill().expand().space(10);
		table.add(userScroll).fillY().expandY().space(10).row();
		table.add(chatInput).expandX().fillX().space(10);
		table.add(chatSend).left().space(10).row();
		table.add(leaveRoom).colspan(2).space(10);
		stage.addActor(table);
	}
	
	private void sendTextToScreen(String text) {
		messages += "\n" + text;
		chatArea.setText(messages);
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
		atlas.dispose();
		skin.dispose();
		batch.dispose();
	}
	

}
