package com.aneebo.storyidea.screens;

import static com.aneebo.storyidea.StoryIdea.social;

import com.aneebo.storyidea.StoryIdea;
import com.aneebo.storyidea.circles.CircleList;
import com.aneebo.storyidea.circles.UserCircle;
import com.aneebo.storyidea.users.SocialUser;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

public class MainMenu implements Screen {

	private Texture background;
	private SpriteBatch batch;
	private Stage stage;
	private Table table;
	private Skin skin;
	private TextureAtlas atlas;
	
	private List<String> circleList;
	
	
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
		social.initiate();
		
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
		
		final TextButton playButton = new TextButton("Play", skin);
		playButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((StoryIdea)Gdx.app.getApplicationListener()).setScreen(new SendStory());
			}
		});
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
		final TextButton addFriendsButton = new TextButton("Create Circles", skin);
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
		
		circleList = new List<String>(skin);
		circleList.setItems(getCircleList().getDisplayInfo());
		ScrollPane scrollPane = new ScrollPane(circleList, skin);
		
		
		background = new Texture("img/sampleSplash.jpg");
		table.add(new Label("Circle list", skin)).colspan(3).expandX().spaceBottom(50).row();
		table.add(scrollPane).uniformX().expandX().expandY().fillY().center().spaceBottom(20).row();
		table.add(playButton).uniformX().spaceBottom(20).row();
		table.add(addFriendsButton).uniformX().spaceBottom(20).row();
		table.add(logButton).uniformX().spaceBottom(20).row();
		table.add(exitButton).uniformX();
		
		stage.addActor(table);
		
	}	
	
	private CircleList getCircleList() {
		//TODO: Add Cloud storage retreival to get list!
//		social.getCloudCircles();
		
		//Create array list to hold users
		Array<SocialUser> su1 = new Array<SocialUser>(false, 2, SocialUser.class);
		su1.items[0] = new SocialUser(0, 123, "Kevin");
		su1.items[1] = new SocialUser(2, 234, "Eric");
		
		//Create user circle to hold the users
		UserCircle uc1 = new UserCircle(su1, 4);
		
		//Create array to hold a list of user circles
		Array<UserCircle> uca1 = new Array<UserCircle>(false, 1, UserCircle.class);
		uca1.add(uc1);
		
		//Create a circle list
		CircleList cl1 = new CircleList(uca1);
		return cl1;
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
