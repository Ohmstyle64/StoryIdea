package com.aneebo.storyidea.screens;

import static com.aneebo.storyidea.StoryIdea.social;

import com.aneebo.storyidea.users.SocialUser;
import com.aneebo.storyidea.users.UserList;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

public class CreateCircle implements Screen {
	
	private Stage stage;
	private Table table;
	private Skin skin;
	private TextureAtlas atlas;
	
	private List<String> friends;

	@Override
	public void render(float delta) {
		//Clear Screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//Let the stage act
		stage.act(delta);
		stage.draw();

	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
		table.invalidateHierarchy();

	}

	@Override
	public void show() {
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		atlas = new TextureAtlas("ui/atlas.pack");
		skin = new Skin(Gdx.files.internal("ui/menuSkin.json"),atlas);

		table = new Table();
		table.setFillParent(true);
		//TODO: Remove this.
		table.debug();
		
		Label heading = new Label("Create a Circle", skin);
		
		Array<SocialUser> userList;
		//TODO: Need to wait for friends to load. Need a progress bar if necessary.
		if(social.isLoggedIn()) {
			userList = social.getFriends();
		}
		else
			userList = new Array<SocialUser>();
		
		friends = new List<String>(skin);
		friends.setItems(new UserList(userList).getUserNames());
		
		final TextButton backButton = new TextButton("Back", skin);
		backButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu());
			}
		});
		
		table.add(heading).row();
		table.add(friends).row();
		table.add(backButton);
		
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
	}

}
