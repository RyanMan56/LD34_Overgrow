package com.subzero.ld34.entities;

import java.util.Random;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.subzero.ld34.images.ImageProvider;

public class Soldier extends Entity {
	private Rocket rocket;
	private boolean rocketFired = false;
	private double angle;

	public Soldier(AssetManager assetManager, float x, float y, float dy) {
		this.assetManager = assetManager;
		this.x = x;
		this.y = y;
		this.dy = dy;
		growthScale = 0.01f;
		imageProvider = new ImageProvider();
		baseDy = dy;
		rand = new Random();
		textureRegion = new TextureRegion(assetManager.get("Soldiers.png", Texture.class));
		animatedTextures = textureRegion.split(18, 28)[0];
		animation = new Animation(period, animatedTextures);
		animation.setPlayMode(PlayMode.LOOP_PINGPONG);
		sprite = new Sprite(animation.getKeyFrame(elapsedTime, true));
		sprite.setX(x);
		sprite.setY(y);
		bounds = new Rectangle(x, y, 18, 28);
		blossomBounds = new Rectangle();
	}

	public void update() {
		if (health == 0) {
			health = 100;
			eaten = false;
			x = rand.nextInt((int) (rightBorder - leftBorder - bounds.width)) + leftBorder;
			y = rand.nextInt(501) + imageProvider.getScreenHeight() + 1;
			rocketFired = false;
		}

		if (y < 0) {
			x = rand.nextInt((int) (rightBorder - leftBorder - bounds.width)) + leftBorder;
			y = rand.nextInt(501) + imageProvider.getScreenHeight() + 1;
		}

		y += dy;
		bounds.x = x;
		bounds.y = y;
		sprite.setX(x);
		sprite.setY(y);

		if (y > blossomBounds.y)
			if (!rocketFired) {
				assetManager.get("Shoot.wav", Sound.class).play();
				rocket = new Rocket(assetManager, x + bounds.width / 2, y, 0, 0);
				rocket.setDx((blossomBounds.x - rocket.getBounds().x) / 250);
				rocket.setDy((-rocket.getBounds().y - blossomBounds.y) / 250);
				angle = Math.atan2(rocket.getBounds().y - blossomBounds.y, rocket.getBounds().x - blossomBounds.x);
				rocket.setAngle(angle);
				rocketFired = true;
			}
		if (rocketFired) {
			if (!rocket.isFinishedExploding())
				rocket.update();

			if(rocket.isFinishedExploding())
				rocketFired = false;
		}
	}

	public void render(SpriteBatch batch) {
		super.render(batch);
		if (rocketFired && (!rocket.isFinishedExploding()))
			rocket.render(batch);
	}

	public boolean hasFiredRocket() {
		return rocketFired;
	}

	public Rocket getRocket() {
		return rocket;
	}

}
