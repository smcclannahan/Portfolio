package com.mygdx.game.TestingStuff;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter{
	Texture img;
	SpriteBatch batch;
	OrthographicCamera camera;
	ShapeRenderer sr;
	//MapGrid1 grid;


	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("TextureTest.jpg");

		camera = new OrthographicCamera();
		camera.setToOrtho(true, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		sr = new ShapeRenderer();
		sr.setAutoShapeType(true);
		//grid = new MapGrid1(16,16);

	}
	@Override
	public void render () {
		camera.update();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 5, 5);
		batch.end();

		sr.begin();
		//grid.render(sr);
		sr.end();


	}
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		sr.dispose();
	}
}