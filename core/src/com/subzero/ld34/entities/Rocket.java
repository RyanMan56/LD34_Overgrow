package com.subzero.ld34.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Rocket extends Entity {
	//	private Texture[] smoke = new Texture[5];
	//	private Rectangle[] smokeBounds = new Rectangle[10];
	//	private float smokeDistance = 3;
	private Explosion explosion;
	private boolean exploding = false;
	private boolean finishedExploding = false;

	public Rocket(AssetManager assetManager, float x, float y, float dx, float dy) {
		this.assetManager = assetManager;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		textureRegion = new TextureRegion(assetManager.get("Rocket.png", Texture.class));
		animatedTextures = textureRegion.split(11, 9)[0];
		animation = new Animation(period, animatedTextures);
		animation.setPlayMode(PlayMode.LOOP_PINGPONG);
		sprite = new Sprite(animation.getKeyFrame(elapsedTime, true));
		sprite.setX(x);
		sprite.setY(y);
		sprite.setScale(2);
		bounds = new Rectangle(x, y, 33, 9);
		blossomBounds = new Rectangle();
		//		for(int i = 0; i < smoke.length; i++){
		//			smoke[i] = assetManager.get("Smoke.png", Texture.class);
		//			smokeBounds[i] = new Rectangle();
		//		}
	}

	public void update() {
		x += dx;
		y += dy;
		bounds.x = x;
		bounds.y = y;
		sprite.setX(x);
		sprite.setY(y);
		if (exploding && (!finishedExploding)) {
			explosion.update();
		}
		if (health == 0)
			if (explosion.isFinishedExploding())
				finishedExploding = true;

		System.out.println(y);
		if (y < 0)
			finishedExploding = true;
	}

	public void render(SpriteBatch batch) {
		super.render(batch);
		if (exploding && (!finishedExploding)) {
			explosion.render(batch);
		}

		//		batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 0.1f);
		//		batch.draw(smoke[0], x-smokeDistance, y-smokeDistance);
		//		smokeBounds[0].x = x-smokeDistance;
		//		smokeBounds[0].y = y-smokeDistance;
		//		for(int i = 1; i < smoke.length; i++){
		//			batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, (float)0.1-0.001f*i);
		//			batch.draw(smoke[i], smokeBounds[i-1].x-smokeDistance, smokeBounds[i-1].y-smokeDistance);
		//			smokeBounds[i].x = smokeBounds[i-1].x-smokeDistance;
		//			smokeBounds[i].y = smokeBounds[i-1].y-smokeDistance;
		//		}
		//		batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 1f);
	}

	public void explode() {
		assetManager.get("Explode.wav", Sound.class).play();
		health = 0;
		explosion = new Explosion(assetManager, x, y, 0, 0);
		exploding = true;
		x = 1000;
		dx = 0;
		dy = 0;
	}

	public void setAngle(double angle) {
		sprite.setRotation((float) (angle * 180 / Math.PI - 180));
	}

	public boolean isExploding() {
		return exploding;
	}

	public boolean isFinishedExploding() {
		return finishedExploding;
	}

}
