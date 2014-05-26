package com.aneebo.storyidea.screens;

import static com.aneebo.storyidea.StoryIdea.swarm;
import com.aneebo.storyidea.StoryIdea;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenu implements Screen {

	private Texture background;
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
		batch.draw(background, 0, 0);		
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
		
		atlas = new TextureAtlas("ui/atlas.pack");
		skin = new Skin(Gdx.files.internal("ui/menuSkin.json"),atlas);
		
		table = new Table();
		table.setFillParent(true);
		//TODO: Remove this.
		table.debug();
		
		final TextButton playButton = new TextButton("Play Game", skin);
		playButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((StoryIdea)Gdx.app.getApplicationListener()).setScreen(new SendStory());
			}
		});
		final TextButton logButton = new TextButton("Log In", skin);
		logButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) { //TODO: need to implement this properly
				if(swarm.isLoggedIn()) {
					swarm.logout();
					logButton.setText("Log In");
				}
				else {
					swarm.login();
					logButton.setText("Log Out");
				}
			}
		});
		
		background = new Texture("img/sampleSplash.jpg");
		
		table.add(playButton).center().row();
		table.padBottom(20);
		table.add(logButton).center();
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
		background.dispose();
		skin.dispose();
		batch.dispose();
	}

}
