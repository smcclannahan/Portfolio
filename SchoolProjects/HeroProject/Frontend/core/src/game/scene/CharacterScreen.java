package game.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

import buttons.CharacterScreenButtons;
import game.entities.Player;

public class CharacterScreen implements Screen {

    private MyGdxGame game;
    private OrthographicCamera mainCamera;
    private Viewport gameViewport;

    private Texture background, p1Texture,p2Texture,p3Texture,p4Texture;
    private CharacterScreenButtons buttons;

    private Player current_player;
    /**
     * Character Screen constructor
     * @param game
     * Represents the current game session
     * @param current_player
     * Represents the player playing the game
     *
     */
    public CharacterScreen(MyGdxGame game, Player current_player){
        this.game = game;
        this.current_player = current_player;
        background = new Texture("game_background.jpg");
        p1Texture = new Texture("Player/characters/p1/down/tile.png");
        p2Texture = new Texture("Player/characters/p2/down/tile.png");
        p3Texture = new Texture("Player/characters/p3/down/tile.png");
        p4Texture = new Texture("Player/characters/p4/down/tile.png");

        mainCamera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        mainCamera.position.set(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f,0);

        gameViewport =  new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), mainCamera);

        buttons = new CharacterScreenButtons(game, this.current_player);


    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();

        game.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        game.getBatch().draw(p1Texture, Gdx.graphics.getWidth()/4-200,300,300,600);
        game.getBatch().draw(p2Texture, 2*Gdx.graphics.getWidth()/4-300,300, 300,600);
        game.getBatch().draw(p3Texture, 3*Gdx.graphics.getWidth()/4-400,300, 300,600);
        game.getBatch().draw(p4Texture, Gdx.graphics.getWidth()-500,300, 300,600);

        game.getBatch().end();
        game.getBatch().setProjectionMatrix(buttons.getStage().getCamera().combined);
        buttons.getStage().draw();
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


    /**
     * Dispose all the objects that are disposable
     */
    @Override
    public void dispose() {
        background.dispose();
        game.getBatch().dispose();
        buttons.getStage().dispose();
    }
}
