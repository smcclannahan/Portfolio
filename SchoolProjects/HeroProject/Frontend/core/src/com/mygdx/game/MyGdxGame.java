package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.scene.GameScreen;
import game.scene.LoginScreen;
import game.scene.MainMenu;

public class MyGdxGame extends Game {
	private SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		//setScreen(new MainMenu(this));
		setScreen(new LoginScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	public SpriteBatch getBatch (){
		return this.batch;
	}

}
