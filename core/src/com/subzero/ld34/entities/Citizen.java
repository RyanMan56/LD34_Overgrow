package com.subzero.ld34.entities;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.subzero.ld34.images.ImageProvider;

public class Citizen extends Entity{
	
	public Citizen(AssetManager assetManager, float x, float y, float dy) {
		this.assetManager = assetManager;
		this.x = x;
		this.y = y;
		this.dy = dy;
		growthScale = 0.005f;
		imageProvider = new ImageProvider();
		baseDy = dy;
		rand = new Random();
		int val = rand.nextInt(6) + 1;
		if (val == 1)
			textureRegion = new TextureRegion(assetManager.get("Citizens.png", Texture.class));
		else
			textureRegion = new TextureRegion(assetManager.get("Citizens" + val + ".png", Texture.class));
		animatedTextures = textureRegion.split(26, 28)[0];
		animation = new Animation(period, animatedTextures);
		animation.setPlayMode(PlayMode.LOOP_PINGPONG);
		sprite = new Sprite(animation.getKeyFrame(elapsedTime, true));
		sprite.setX(x);
		sprite.setY(y);
		bounds = new Rectangle(x, y, 26, 28);
		blossomBounds = new Rectangle();
	}
}
