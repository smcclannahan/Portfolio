package game.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

import buttons.OpenRoomsButtons;
import game.entities.Player;

public class OpenRoomsScreen implements Screen {
    private MyGdxGame game;
    private OrthographicCamera mainCamera;
    private Viewport gameViewport;

    private Texture background;

    private Player current_player;
    private OpenRoomsButtons buttons;


    public OpenRoomsScreen(MyGdxGame game, Player current_player) {
        this.game = game;
        this.current_player = current_player;

        background = new Texture("game_background.jpg");

        mainCamera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        mainCamera.position.set(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f,0);

        gameViewport =  new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), mainCamera);

        buttons = new OpenRoomsButtons(game, current_player);


    }

    @Override
    public void show() {
        buttons.handleHttpRequest();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();
        game.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        game.getBatch().end();

        game.getBatch().setProjectionMatrix(buttons.getStage().getCamera().combined);
        buttons.getStage().draw();
        buttons.getStage().act();

//        buttons.handleHttpRequest();





    }


    /**
     * Dispose all the objects that are disposable
     */
    @Override
    public void dispose() {
        background.dispose();
        buttons.getStage().dispose();
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
}
