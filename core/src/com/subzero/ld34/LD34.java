package com.subzero.ld34;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.subzero.ld34.screens.GameScreen;
import com.subzero.ld34.screens.MenuScreen;

public class LD34 extends Game {
	GameScreen gameScreen;
	MenuScreen menuScreen;
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
		assetManager.load("Citizens2.png", Texture.class);
		assetManager.load("Citizens3.png", Texture.class);
		assetManager.load("Citizens4.png", Texture.class);
		assetManager.load("Citizens5.png", Texture.class);
		assetManager.load("Citizens6.png", Texture.class);
		assetManager.load("Blood.png", Texture.class);
		assetManager.load("BlossomEating.png", Texture.class);
		assetManager.load("Soldiers.png", Texture.class);
		assetManager.load("Rocket.png", Texture.class);
		assetManager.load("Smoke.png", Texture.class);
		assetManager.load("Explosions.png", Texture.class);
		assetManager.load("1Petals.png", Texture.class);
		assetManager.load("2Petals.png", Texture.class);
		assetManager.load("3Petals.png", Texture.class);
		assetManager.load("4Petals.png", Texture.class);
		assetManager.load("5Petals.png", Texture.class);
		assetManager.load("6Petals.png", Texture.class);
		assetManager.load("BlossomDead.png", Texture.class);
		assetManager.load("Title.png", Texture.class);
		
		assetManager.load("Eat.wav", Sound.class);
		assetManager.load("Shoot.wav", Sound.class);
		assetManager.load("Explode.wav", Sound.class);
		
	}

	@Override
	public void render () {
		super.render();
		if(!loaded){
			if(assetManager.update()){
//				gameScreen = new GameScreen(this, assetManager);
				menuScreen = new MenuScreen(this, assetManager);
				setScreen(menuScreen);
				loaded = true;
			}
		}
	}
}
