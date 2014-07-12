package com.aneebo.storyidea.screens;

import static com.aneebo.storyidea.StoryIdea.social;

import com.aneebo.storyidea.StoryIdea;
import com.aneebo.storyidea.circles.UserCircle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

public class MainMenu implements Screen {

	private Image background;
	private SpriteBatch batch;
	private Stage stage;
	private Table table;
	private Skin skin;
	private TextureAtlas atlas;
	
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
		social.initiate();
		
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
		
		
		final TextButton logButton = new TextButton("Log Out", skin);
		logButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) { //TODO: need to implement this properly
				if(social.isLoggedIn()) {
					Gdx.app.log(StoryIdea.TITLE, "Log out!");
					social.logout();
					logButton.setText("Log In");
				}
				else {
					Gdx.app.log(StoryIdea.TITLE, "Log in!");
					social.login();
					logButton.setText("Log Out");
				}
			}
		});
		final TextButton addFriendsButton = new TextButton("Add Circles", skin);
		addFriendsButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((StoryIdea)Gdx.app.getApplicationListener()).setScreen(new CreateCircle());
			}
		});
		final TextButton exitButton = new TextButton("Exit", skin);
		exitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		
		background = new Image(new Texture("img/sampleSplash.jpg"));
		background.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		table.add(addFriendsButton).uniformX().spaceBottom(20).row();
		table.add(logButton).uniformX().spaceBottom(20).row();
		table.add(exitButton).uniformX();
		
		stage.addActor(table);
		
	}	
	
	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		stage.dispose();
		atlas.dispose();
		skin.dispose();
		batch.dispose();
	}

}
