package game.scene;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

//import org.java_websocket.client.WebSocketClient;
//import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import buttons.ChattingSystemButtons;
import buttons.LoginButton;
import buttons.MainMenuButtons;
import buttons.MultiplayerButtons;
import buttons.PlayOptionsButtons;
import game.entities.Player;

public class MultiplayerScreen implements Screen {

    private MyGdxGame game;
    private OrthographicCamera mainCamera;
    private Viewport gameViewport;

    private Texture background;

    public MultiplayerButtons multiplayerButtons;
    private Player current_player;

    //Lobby Screen

    /**
     * MultiplayerScreen constructor
     * @param game
     *  Represents the current game session
     * @param current_player
     * Represents the player playing the game
     */
    public MultiplayerScreen(MyGdxGame game, Player current_player){
        this.game = game;
        this.current_player =current_player;
        background = new Texture("game_background.jpg");

        mainCamera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        mainCamera.position.set(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f,0);

        gameViewport =  new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), mainCamera);
        multiplayerButtons = new MultiplayerButtons(this.game, this.current_player);
    }
    @Override
    public void show() {

    }
    /**
     * Renders the players and multi player actors
     * @param delta
     * delta time variable
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();
        game.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        game.getBatch().end();

        game.getBatch().setProjectionMatrix(multiplayerButtons.getStage().getCamera().combined);
        multiplayerButtons.getStage().draw();






    }

    /**
     * Dispose all the objects that are disposable
     */
    @Override
    public void dispose() {
        background.dispose();
        game.getBatch().dispose();
        multiplayerButtons.getStage().dispose();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }
}