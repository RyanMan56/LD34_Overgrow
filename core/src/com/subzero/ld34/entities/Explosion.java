package com.subzero.ld34.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Rectangle;

public class Explosion extends Entity{

	public Explosion(AssetManager assetManager, float x, float y, float dx, float dy){
		this.assetManager = assetManager;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		period = 1/15f;
		textureRegion = new TextureRegion(assetManager.get("Explosions.png", Texture.class));
		animatedTextures = textureRegion.split(17, 17)[0];
		animation = new Animation(period, animatedTextures);
		animation.setPlayMode(PlayMode.NORMAL);
		sprite = new Sprite(animation.getKeyFrame(elapsedTime, true));
		sprite.setX(x);
		sprite.setY(y);
		bounds = new Rectangle(x, y, 18, 28);
		blossomBounds = new Rectangle();
	}
	
	public void update(){
		y += dy;
		bounds.x = x;
		bounds.y = y;
		sprite.setX(x);
		sprite.setY(y);
	}
	
	public void render(SpriteBatch batch) {
		if (health > 0) {
			elapsedTime += Gdx.graphics.getDeltaTime();
			sprite.setRegion(animation.getKeyFrame(elapsedTime, false));
			if(animation.isAnimationFinished(elapsedTime))
				health = 0;
			sprite.draw(batch);
		}
	}
	
	public boolean isFinishedExploding(){
		return animation.isAnimationFinished(elapsedTime);
	}
}
