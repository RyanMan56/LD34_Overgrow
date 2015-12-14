package com.subzero.ld34.screens;

import java.text.DecimalFormat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.subzero.ld34.LD34;
import com.subzero.ld34.entities.Blossom;
import com.subzero.ld34.entities.Citizen;
import com.subzero.ld34.entities.Soldier;
import com.subzero.ld34.environments.City;
import com.subzero.ld34.images.ImageProvider;

public class GameScreen implements Screen {
	private LD34 game;
	private AssetManager assetManager;
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private OrthographicCamera camera;
	private ImageProvider imageProvider = new ImageProvider();
	private Viewport viewport;
	private Blossom blossom;
	private City city;
	private Citizen citizen[] = new Citizen[20];
	private Soldier soldier[] = new Soldier[3];
	private BitmapFont font;
	private Label text, gameOver;
	private LabelStyle textStyle;

	public GameScreen(LD34 game, AssetManager assetManager) {
		this.game = game;
		this.assetManager = assetManager;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, imageProvider.getScreenWidth(), imageProvider.getScreenHeight());
		viewport = new FitViewport(imageProvider.getScreenWidth(), imageProvider.getScreenHeight(), camera);
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		blossom = new Blossom(assetManager);
		city = new City(assetManager, blossom.getDx(), blossom.getDy());
		blossom.setBorders(113, 787);
		for (int i = 0; i < citizen.length; i++) {
			citizen[i] = new Citizen(assetManager, 0, 0, blossom.getDy() / 2);
			citizen[i].setBorders(113, 787);
		}
		for (int i = 0; i < soldier.length; i++) {
			soldier[i] = new Soldier(assetManager, 0, 0, blossom.getDy() / 2);
			soldier[i].setBorders(113, 787);
		}
		font = new BitmapFont(Gdx.files.internal("font.fnt"));
		textStyle = new LabelStyle();
		textStyle.font = font;
		text = new Label("", textStyle);
		text.setPosition(300, imageProvider.getScreenHeight() - 50);
		text.setText("Size: ");
		gameOver = new Label("", textStyle);
		gameOver.setPosition(100, 300);
		gameOver.setText("Game Over! Click to restart!");
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();

		if (blossom.isAlive()) {
			blossom.update();
			city.update();
			for (int i = 0; i < citizen.length; i++) {
				citizen[i].update();
				citizen[i].setBlossomBoundsAndScale(blossom.getCollisionBounds(), blossom.getScale());
			}
			for (int i = 0; i < soldier.length; i++) {
				soldier[i].update();
				soldier[i].setBlossomBoundsAndScale(blossom.getCollisionBounds(), blossom.getScale());
			}
			checkCollisions();
		}else{
			city.setDy(0);
			if(Gdx.input.isTouched()){
				game.setScreen(new GameScreen(game, assetManager));
			}
		}

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		city.render(batch);
		blossom.render(batch);
		for (int i = 0; i < citizen.length; i++) {
			if (citizen[i].isAlive())
				citizen[i].render(batch);
		}
		for (int i = 0; i < soldier.length; i++) {
			if (soldier[i].isAlive())
				soldier[i].render(batch);
		}
		blossom.renderPetals(batch);
		text.setText("Size: " + blossom.getFormattedScale() + "m");
		text.draw(batch, 1);
		if(!blossom.isAlive())
			gameOver.draw(batch, 1);
		batch.end();

		//				shapeRenderer.setProjectionMatrix(camera.combined);
		//				shapeRenderer.begin(ShapeType.Line);
		//				shapeRenderer.rect(blossom.getDamageBounds()[0].x, blossom.getDamageBounds()[0].y, blossom.getDamageBounds()[0].width, blossom.getDamageBounds()[0].height);
		//				shapeRenderer.rect(blossom.getDamageBounds()[1].x, blossom.getDamageBounds()[1].y, blossom.getDamageBounds()[1].width, blossom.getDamageBounds()[1].height);
		//				shapeRenderer.end();
	}

	public void checkCollisions() {
		blossom.setEating(false);
		for (int i = 0; i < citizen.length; i++) {
			if (blossom.getCollisionBounds().overlaps(citizen[i].getBounds())) {
				blossom.setEating(true);
				if (!citizen[i].isEaten())
					blossom.grow(citizen[i].getGrowthScale());

				citizen[i].kill();
				blossom.setEating(true);
			}
		}
		for (int i = 0; i < soldier.length; i++) {
			if (soldier[i].hasFiredRocket())
				if (soldier[i].getRocket().isAlive())
					if ((blossom.getDamageBounds()[0].overlaps(soldier[i].getRocket().getBounds())) || (blossom.getDamageBounds()[1].overlaps(soldier[i].getRocket().getBounds()))) {
						soldier[i].getRocket().explode();
						blossom.damage();
					}
			if (blossom.getCollisionBounds().overlaps(soldier[i].getBounds())) {
				blossom.setEating(true);
				if (!soldier[i].isEaten())
					blossom.grow(soldier[i].getGrowthScale());
				soldier[i].kill();
				blossom.setEating(true);
			}
		}

	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
