package com.subzero.ld34.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Citizen {
	private AssetManager assetManager;
	private TextureRegion textureRegion;
	private TextureRegion[] animatedTextures;
	private float period = 1/5f;
	private float elapsedTime = 0;
	private Animation animation;
	Sprite sprite;
	private float health = 100;
	
	public Citizen(AssetManager assetManager){
		this.assetManager = assetManager;
		textureRegion = new TextureRegion(assetManager.get("Citizens.png", Texture.class));
		animatedTextures = textureRegion.split(26, 28)[0];
		animation = new Animation(period, animatedTextures);
		animation.setPlayMode(PlayMode.LOOP_PINGPONG);
		sprite = new Sprite(animation.getKeyFrame(elapsedTime, true));
		sprite.setX(400);
		sprite.setY(300);
	}
	
	public void render(SpriteBatch batch){
		if(health > 0){
			elapsedTime += Gdx.graphics.getDeltaTime();
			sprite.setRegion(animation.getKeyFrame(elapsedTime, true));
		}
		sprite.draw(batch);
	}
}
