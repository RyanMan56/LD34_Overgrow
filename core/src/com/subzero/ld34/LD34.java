package com.subzero.ld34;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.subzero.ld34.screens.GameScreen;

public class LD34 extends Game {
	GameScreen gameScreen;
	AssetManager assetManager;
	private boolean loaded = false;
	
	@Override
	public void create () {
		assetManager = new AssetManager();
		assetManager.load("Blossom.png", Texture.class);
		assetManager.load("Petals.png", Texture.class);
		assetManager.load("City.png", Texture.class);
		assetManager.load("Body.png", Texture.class);
		assetManager.load("Citizens.png", Texture.class);
	}

	@Override
	public void render () {
		super.render();
		if(!loaded){
			if(assetManager.update()){
				gameScreen = new GameScreen(this, assetManager);
				setScreen(gameScreen);
				loaded = true;
			}
		}
	}
}
