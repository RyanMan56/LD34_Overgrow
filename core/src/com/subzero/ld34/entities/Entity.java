package com.subzero.ld34.entities;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.subzero.ld34.images.ImageProvider;

public class Entity {
	protected AssetManager assetManager;
	protected TextureRegion textureRegion;
	protected TextureRegion[] animatedTextures;
	protected float period = 1 / 5f;
	protected float elapsedTime = 0;
	protected Animation animation;
	protected Sprite sprite;
	protected float health = 100;
	protected float x, y;
	protected float dx, dy, baseDy;
	protected Rectangle bounds;
	protected Rectangle blossomBounds;
	protected float blossomScale;
	protected boolean eaten = false;
	protected float leftBorder, rightBorder;
	protected Random rand;
	protected ImageProvider imageProvider;
	protected float growthScale = 1000;
	
	public void update() {
		if(health == 0){
			health = 100;
			eaten = false;
			x = rand.nextInt((int)(rightBorder-leftBorder-bounds.width))+leftBorder;
			y = rand.nextInt(501)+imageProvider.getScreenHeight()+1;
		}
		
		if(y < 0){
			x = rand.nextInt((int)(rightBorder-leftBorder-bounds.width))+leftBorder;
			y = rand.nextInt(501)+imageProvider.getScreenHeight()+1;
		}
		
		y += dy;
		bounds.x = x;
		bounds.y = y;
		sprite.setX(x);
		sprite.setY(y);
	}

	public void render(SpriteBatch batch) {
		if (health > 0) {
			elapsedTime += Gdx.graphics.getDeltaTime();
			sprite.setRegion(animation.getKeyFrame(elapsedTime, true));
		}
		sprite.draw(batch);
	}

	public void kill() {
		eaten = true;
		if (health > 0) {
			if (blossomBounds.x + 35*blossomScale < x)
				x -= 7;
			else
				x += 7;
			if (blossomBounds.y + 25*blossomScale < y)
				y -= 5;
			else
				y += 5;
		}
			if ((x < blossomBounds.x + 40*blossomScale) && ((x > blossomBounds.x + 20*blossomScale)) && (y < blossomBounds.y + 30*blossomScale) && (y > blossomBounds.y + 10*blossomScale)) {
				health = 0;
		}
	}

	public boolean isEaten(){
		return eaten;
	}
	public boolean isAlive() {
		return health > 0;
	}
	
	public float getHealth(){
		return health;
	}
	public float getGrowthScale(){
		return growthScale;
	}
	public Rectangle getBounds() {
		return bounds;
	}
	
	public void setDx(float dx){
		this.dx = dx;
	}
	public void setDy(float dy){
		this.dy = dy;
	}
	public void setBorders(float leftBorder, float rightBorder){
		this.leftBorder = leftBorder;
		this.rightBorder = rightBorder;
	} 
	public void setBlossomBoundsAndScale(Rectangle blossomBounds, float scale) {
		this.blossomBounds = blossomBounds;
		this.blossomScale = scale;
	}

}
