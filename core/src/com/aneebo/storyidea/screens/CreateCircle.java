package com.aneebo.storyidea.screens;

import static com.aneebo.storyidea.StoryIdea.social;

import com.aneebo.storyidea.circles.UserCircle;
import com.aneebo.storyidea.users.SocialUser;
import com.aneebo.storyidea.users.UserList;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

public class CreateCircle implements Screen {
	
	private Stage stage;
	private Table table;
	private Skin skin;
	private TextureAtlas atlas;
	private UserList userList;
	private List<String> friends;
	private List<String> selected;
	private Slider slider;
	
	@Override
	public void render(float delta) {
		//Clear Screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//Let the stage act
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
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		atlas = new TextureAtlas("ui/defaultskin.atlas");
		skin = new Skin(Gdx.files.internal("ui/defaultskin.json"),atlas);

		table = new Table();
		table.setFillParent(true);
		//TODO: Remove this.
		table.debug();
		
		Label heading = new Label("Create a Circle", skin);
		
		
		//Add a slider for the number story lines
		slider = new Slider(2, 16, 1, false, skin);
		final Label sliderLabel = new Label("Story Lines: "+(int)slider.getVisualValue(), skin);
		slider.addListener(new ClickListener() {
			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				sliderLabel.setText("Story Lines: "+(int)slider.getVisualValue());
			}
		});
		
		getUsernames();
		
		//Create friends list
		friends = new List<String>(skin);
		friends.setFillParent(true);
		friends.setItems(userList.getUserNames());
		ScrollPane scrollFriends = new ScrollPane(friends, skin);
		scrollFriends.setScrollingDisabled(true, false);
		
		//Create selected list
		selected = new List<String>(skin);
		selected.setFillParent(true);
		ScrollPane scrollSelected = new ScrollPane(selected,skin);
		scrollSelected.setScrollingDisabled(true, false);

		//Create button to add friends to selected list
		final TextButton addFriend = new TextButton("\\/", skin);
		//Create button to remove selected friends back to friends list
		final TextButton removeSelected = new TextButton("/\\", skin);
		
		addFriend.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(friends.getSelected() != null) {
					String selectedFriend = friends.getSelected();
					Array<String> items = new Array<String>();
					items.addAll(selected.getItems());
					items.add(selectedFriend);
					selected.setItems(items);
					friends.getItems().removeValue(selectedFriend, false);
					friends.setSelectedIndex(-1);
					
					
					Array<String> names = userList.getUserNames();
					
					Array<String> selectedArray = selected.getItems();
					for(int i = 0; i < selectedArray.size; i++) {
						if(names.contains(selectedArray.get(i), false))
							names.removeValue(selectedArray.get(i), false);
					}
					friends.setItems(names);
				}
				if(friends.getItems().size <=0 || selected.getItems().size >= 8) addFriend.setDisabled(true);
				else removeSelected.setDisabled(false);
			}
		});
		removeSelected.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(selected.getSelected() != null) {
					String selectedFriend = selected.getSelected();
					Array<String> items = new Array<String>();
					items.addAll(friends.getItems());
					items.add(selectedFriend);
					friends.setItems(items);
					selected.getItems().removeValue(selectedFriend, false);
					selected.setSelectedIndex(-1);
					
					Array<String> names = userList.getUserNames();
					
					Array<String> selectedArray = selected.getItems();
					for(int i = 0; i < selectedArray.size; i++) {
						if(names.contains(selectedArray.get(i), false))
							names.removeValue(selectedArray.get(i), false);
					}
					friends.setItems(names);
				}
				if(selected.getItems().size <=0) removeSelected.setDisabled(true);
				else addFriend.setDisabled(false);
			}
		});
		
		final TextButton startCircle = new TextButton("Start Circle", skin);
		startCircle.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//TODO: Add code to start a circle.
				Array<SocialUser> users = userList.getUserSubset(selected.getItems());
				UserList subList = new UserList(users);
				social.createCircleRoom(subList);			
				UserCircle uc = new UserCircle(users, slider.getVisualValue());
				//Returns to menu
				((Game)Gdx.app.getApplicationListener()).setScreen(new WaitRoom(uc));
			}
		});
		
		final TextButton manageFriends = new TextButton("Manage Friends", skin);
		manageFriends.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				social.showDashboard();
			}
		});
		
		final TextButton backButton = new TextButton("Back", skin);
		backButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu());
			}
		});
		
		table.add(heading).colspan(2).spaceBottom(10).row();
		table.add(sliderLabel).spaceBottom(10);
		table.add(slider).expandX().spaceBottom(10).row();
		table.add(new Label("Online Friends:",skin)).row();
		table.add(scrollFriends).colspan(2).center().expand().fillY().spaceBottom(10).row();
		table.add(addFriend).spaceBottom(10).expandX();
		table.add(removeSelected).spaceBottom(10).expandX().row();
		table.add(new Label("Selected:",skin)).row();
		table.add(scrollSelected).colspan(2).center().expand().fillY().spaceBottom(10).row();
		table.add(startCircle).colspan(2).spaceBottom(10).row();
		table.add(manageFriends).colspan(2).spaceBottom(10).row();
		table.add(backButton).colspan(2);
		stage.addActor(table);

	}
	
	private void getUsernames() {
		if(social.isLoggedIn()) {
			userList = new UserList(social.getFriends());
		}
		else
			userList= new UserList(new Array<SocialUser>(false, 16, SocialUser.class));
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
