package com.aneebo.storyidea.screens;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Splash implements Screen {

	private Texture splashTexture;
	private Stage stage;
	private Skin skin;
	private TextureAtlas atlas;
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, false);
	}

	@Override
	public void show() {
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		atlas = new TextureAtlas("ui/atlas.pack");
		skin = new Skin(Gdx.files.internal("ui/menuSkin.json"),atlas);
			
		splashTexture = new Texture("img/splash_01.jpg");
		Image image = new Image(splashTexture);
		image.setFillParent(true);
		Action act = Actions.sequence(Actions.alpha(0),Actions.fadeIn(2),Actions.fadeOut(2), Actions.run(new Runnable() {
			
			@Override
			public void run() {
				((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu());
			}
		}));
		image.addAction(act);
		stage.addActor(image);
		
//		Tween.set(splash, SpriteAccessor.ALPHA).target(0).start(tweenManager);
//		Tween.to(splash, SpriteAccessor.ALPHA, 2).target(1).repeatYoyo(1, 2.0f).setCallback(new TweenCallback() {
//
//			@Override
//			public void onEvent(int arg0, BaseTween<?> arg1) {
//				((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu());
//				
//			}
//		}).start(tweenManager);
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
		splashTexture.dispose();
		skin.dispose();
		atlas.dispose();
		stage.dispose();
	}

}
