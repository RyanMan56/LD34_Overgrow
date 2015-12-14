package com.subzero.ld34.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.subzero.ld34.LD34;
import com.subzero.ld34.images.ImageProvider;

public class MenuScreen implements Screen{
	private LD34 game;
	private AssetManager assetManager;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private ImageProvider imageProvider;
	private Viewport viewport;
	private Texture texture;
	
	public MenuScreen(LD34 game, AssetManager assetManager){
		this.game = game;
		this.assetManager = assetManager;
		imageProvider = new ImageProvider();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, imageProvider.getScreenWidth(), imageProvider.getScreenHeight());
		viewport = new FitViewport(imageProvider.getScreenWidth(), imageProvider.getScreenHeight(), camera);
		batch = new SpriteBatch();
		texture = assetManager.get("Title.png", Texture.class);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		batch.begin();
		batch.draw(texture, 0, 0);
		batch.end();
		if(Gdx.input.isTouched()){
			game.setScreen(new GameScreen(game, assetManager));
		}
		
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		
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
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
