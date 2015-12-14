package com.subzero.ld34.environments;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class City {
	private AssetManager assetManager;
	private Texture city[] = new Texture[2];
	private Rectangle cityBounds[] = new Rectangle[2];
	private float dx, dy;

	public City(AssetManager assetManager, float dx, float dy) {
		this.dx = dx;
		this.dy = dy;
		city[0] = assetManager.get("City.png", Texture.class);
		city[1] = assetManager.get("City.png", Texture.class);
		cityBounds[0] = new Rectangle(0, 0, city[0].getWidth(), city[0].getHeight());
		cityBounds[1] = new Rectangle(0, city[1].getHeight(), city[1].getWidth(), city[1].getHeight());
	}

	public void update() {
		for (int i = 0; i < cityBounds.length; i++) {
			if (cityBounds[i].y < -cityBounds[i].height)
				cityBounds[i].y = cityBounds[i].height-4;
		}
	}

	public void render(SpriteBatch batch) {
		batch.draw(city[0], cityBounds[0].x, cityBounds[0].y += dy);
		batch.draw(city[1], cityBounds[1].x, cityBounds[1].y += dy);
	}
	
	public void setDy(float dy){
		this.dy = dy;
	}

}
