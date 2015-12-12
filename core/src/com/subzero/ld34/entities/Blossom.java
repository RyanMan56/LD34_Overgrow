package com.subzero.ld34.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

public class Blossom {
	private Circle[] bodyBounds = new Circle[10];
	private Circle headBounds;
	private float dx = 5, dy = -4;
	private int highestPoint = bodyBounds.length - 1;
	private Texture head, petals;
	private Texture body[] = new Texture[10];
	private float leftBorder, rightBorder;

	public Blossom(AssetManager assetManager) {
		for (int i = 0; i < bodyBounds.length; i++) {
			bodyBounds[i] = new Circle(400, i * 20 - 25, 25);
			body[i] = assetManager.get("Body.png", Texture.class);
		}
		head = assetManager.get("Blossom.png", Texture.class);
		headBounds = new Circle(bodyBounds[highestPoint].x, bodyBounds[bodyBounds.length - 1].y, 50);
		petals = assetManager.get("Petals.png", Texture.class);
	}

	public void update() {
		for (int i = 0; i < bodyBounds.length; i++) {
			bodyBounds[i].y += dy;
			if (bodyBounds[i].y + bodyBounds[i].radius < 0) {
				bodyBounds[i].y = bodyBounds[highestPoint].y += 20;
				bodyBounds[i].x = bodyBounds[highestPoint].x;
				highestPoint = i;
			}
		}
		headBounds.x = bodyBounds[highestPoint].x;

		if (Gdx.input.isKeyPressed(Keys.A)) {
			if (headBounds.x-headBounds.radius >= leftBorder)
				bodyBounds[highestPoint].x -= dx;
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			if (headBounds.x+headBounds.radius <= rightBorder)
				bodyBounds[highestPoint].x += dx;
		}
	}

	public void render(SpriteBatch batch) {
		for(int i = 0; i < body.length; i++)
			batch.draw(body[i], bodyBounds[i].x-bodyBounds[i].radius-25, bodyBounds[i].y-bodyBounds[i].radius-25);
		batch.draw(head, headBounds.x - headBounds.radius, headBounds.y - headBounds.radius);
		batch.draw(petals, headBounds.x - headBounds.radius - 45, headBounds.y - headBounds.radius - 15);
	}

	public void renderShape(ShapeRenderer shapeRenderer) {
//		for (int i = 0; i < body.length; i++) {
//			shapeRenderer.circle(body[i].x, body[i].y, body[i].radius);
//		}
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
