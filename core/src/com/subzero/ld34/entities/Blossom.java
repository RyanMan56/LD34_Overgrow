package com.subzero.ld34.entities;

import java.text.DecimalFormat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public class Blossom {
	private AssetManager assetManager;
	private Circle[] bodyBounds = new Circle[11];
	private Circle headBounds;
	private float dx = 5, dy = -4;
	private int highestPoint = bodyBounds.length - 1;
	private Texture head, petals;
	private Texture body[] = new Texture[11];
	private float leftBorder, rightBorder;
	private Rectangle collisionBounds;
	private Rectangle[] damageBounds = new Rectangle[2];
	private boolean eating;
	private float scale = 1;
	private int health = 6;
	private TextureRegion textureRegion;
	private TextureRegion[] animatedTextures;
	private Animation animation;
	private float period = 1 / 5f;
	private float elapsedTime;
	private DecimalFormat df;
    private String formattedScale;


	public Blossom(AssetManager assetManager) {
		this.assetManager = assetManager;
		for (int i = 0; i < bodyBounds.length; i++) {
			bodyBounds[i] = new Circle(400, i * 20 - 50, 25);
			body[i] = assetManager.get("Body.png", Texture.class);
		}
		head = assetManager.get("Blossom.png", Texture.class);
		headBounds = new Circle(bodyBounds[highestPoint].x, bodyBounds[bodyBounds.length - 1].y, 50);
		petals = assetManager.get("Petals.png", Texture.class);
		collisionBounds = new Rectangle(headBounds.x - headBounds.radius, headBounds.y - headBounds.radius, headBounds.radius * 2 * scale, headBounds.radius * 2 * scale);
		damageBounds[0] = new Rectangle(headBounds.x - headBounds.radius + 22, headBounds.y - headBounds.radius, 53* scale, headBounds.radius * 2 * scale);
		damageBounds[1] = new Rectangle(headBounds.x - headBounds.radius, headBounds.y - headBounds.radius+21, headBounds.radius * 2 * scale, 55* scale);

		textureRegion = new TextureRegion(assetManager.get("" + health + "Petals.png", Texture.class));
		animatedTextures = textureRegion.split(190, 149)[0];
		animation = new Animation(period, animatedTextures);
		animation.setPlayMode(PlayMode.LOOP_PINGPONG);
		
		df = new DecimalFormat("#.00");
		formattedScale = df.format(scale);
	}

	public void update() {
		for (int i = 0; i < bodyBounds.length; i++) {
			bodyBounds[i].y += dy;
			if (bodyBounds[i].y + bodyBounds[i].radius < -50 * scale) {
				bodyBounds[i].y = bodyBounds[highestPoint].y += 20;
				bodyBounds[i].x = bodyBounds[highestPoint].x;
				highestPoint = i;
			}
		}
		headBounds.x = bodyBounds[highestPoint].x;
		collisionBounds.x = headBounds.x - headBounds.radius;
		collisionBounds.width = head.getWidth() * scale;
		collisionBounds.height = head.getHeight() * scale;
		damageBounds[0].set(headBounds.x - headBounds.radius + 22, headBounds.y - headBounds.radius, 53* scale, headBounds.radius * 2 * scale);
		damageBounds[1].set(headBounds.x - headBounds.radius, headBounds.y - headBounds.radius+21, headBounds.radius * 2 * scale, 55* scale);

		if ((Gdx.input.isKeyPressed(Keys.A)) || (Gdx.input.isKeyPressed(Keys.LEFT))) {
			if ((headBounds.x - headBounds.radius) * scale >= leftBorder)
				bodyBounds[highestPoint].x -= dx;
		}
		if (Gdx.input.isKeyPressed(Keys.D) || (Gdx.input.isKeyPressed(Keys.RIGHT))) {
			if ((headBounds.x + headBounds.radius * scale) <= rightBorder)
				bodyBounds[highestPoint].x += dx;
		}
		if (eating)
			head = assetManager.get("BlossomEating.png", Texture.class);
		else
			head = assetManager.get("Blossom.png", Texture.class);
		
		formattedScale = df.format(scale);
	}

	public void render(SpriteBatch batch) {
		if (health > 0) {
			//			sprite.setX(headBounds.x - headBounds.radius - 45*scale);
			//			sprite.setY(headBounds.y - headBounds.radius - 15*scale);
			//			sprite.setScale(scale);
			elapsedTime += Gdx.graphics.getDeltaTime();
		}
		for (int i = 0; i < body.length; i++)
			batch.draw(body[i], bodyBounds[i].x - bodyBounds[i].radius - 25, bodyBounds[i].y - bodyBounds[i].radius - 25, headBounds.radius * 2 * scale, headBounds.radius * 2 * scale);
		batch.draw(head, headBounds.x - headBounds.radius, headBounds.y - headBounds.radius, headBounds.radius * 2 * scale, headBounds.radius * 2 * scale);
	}
	
	public void renderPetals(SpriteBatch batch){
		if (health > 0)
			batch.draw(animation.getKeyFrame(elapsedTime), headBounds.x - headBounds.radius - 45 * scale, headBounds.y - headBounds.radius - 21 * scale, petals.getWidth() * scale, petals.getHeight() * scale);
	}

	public void damage() {
		health--;
		if (health > 0) {
			textureRegion = new TextureRegion(assetManager.get("" + health + "Petals.png", Texture.class));
			animatedTextures = textureRegion.split(190, 149)[0];
			animation = new Animation(period, animatedTextures);
			animation.setPlayMode(PlayMode.LOOP_PINGPONG);
		}
		else{
			head = assetManager.get("BlossomDead.png", Texture.class);
		}
	}

	public void grow(float growthScale) {
		assetManager.get("Eat.wav", Sound.class).play();
		scale += growthScale;
	}

	public void setEating(boolean eating) {
		this.eating = eating;
	}
	
	public boolean isAlive(){
		return health > 0;
	}

	public void renderShape(ShapeRenderer shapeRenderer) {
	}

	public String getFormattedScale(){
		return formattedScale;
	}
	
	public float getScale() {
		return scale;
	}

	public Rectangle[] getDamageBounds() {
		return damageBounds;
	}
	public Rectangle getCollisionBounds() {
		return collisionBounds;
	}

	public float getHeadX() {
		return headBounds.x - headBounds.radius;
	}

	public float getHeadY() {
		return headBounds.y - headBounds.radius;
	}

	public float getHeadSize() {
		return headBounds.radius * 2;
	}

	public void setBorders(float leftBorder, float rightBorder) {
		this.leftBorder = leftBorder;
		this.rightBorder = rightBorder;
	}

	public float getDx() {
		return dx;
	}

	public float getDy() {
		return dy;
	}

}
