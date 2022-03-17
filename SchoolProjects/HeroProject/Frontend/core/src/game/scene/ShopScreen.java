
package game.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

import buttons.ShopButtons;
import game.Weapons.*;
import game.entities.Player;
import buttons.ShopButtons;

import game.entities.Player;

public class ShopScreen implements Screen {
    private MyGdxGame game;
    public Player currentPlayer;
    private OrthographicCamera mainCamera;
    private Viewport gameViewport;

    public ShopButtons shopbuttons;
    private Texture bg;

    /**
     * Shopping screen constructor
     * @param game
     * Represents the current game session
     * @param currentPlayer
     * Represents the player playing the game
     */
    public ShopScreen(MyGdxGame game, Player currentPlayer){
        this.game = game;
        this.currentPlayer = currentPlayer;
        bg = new Texture("ShopScreenTemplate.png");

        mainCamera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        mainCamera.position.set(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f,0);

        gameViewport =  new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
                mainCamera);

        shopbuttons = new ShopButtons(this.game, this.currentPlayer);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();
        game.getBatch().draw(bg, 0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        game.getBatch().end();

        game.getBatch().setProjectionMatrix(shopbuttons.getStage().getCamera().combined);
        shopbuttons.getStage().draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        bg.dispose();
        game.getBatch().dispose();
        shopbuttons.getStage().dispose();
    }
}