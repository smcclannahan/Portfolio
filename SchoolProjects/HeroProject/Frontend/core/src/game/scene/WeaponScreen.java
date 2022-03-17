package game.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

import javax.xml.soap.Text;

import buttons.WeaponScreenButtons;
import game.entities.AxeEnemy;
import game.entities.Player;

public class WeaponScreen implements Screen {
    private Texture bg, swordTex, axeTex, lanceTex, bowTex;

    private OrthographicCamera mainCamera;
    private Viewport gameViewport;

    private MyGdxGame game;
    private Player current_player;


    private WeaponScreenButtons buttons;


    public WeaponScreen(MyGdxGame game, Player current_player, BattleScreen bScreen) {
        this.game = game;
        this.current_player = current_player;

        bg = new Texture("game_background.jpg");

        swordTex = new Texture("weapons/sword.png");
        axeTex = new Texture("weapons/axe.png");
        lanceTex = new Texture("weapons/lance.png");
        bowTex = new Texture("weapons/bow.png");

        mainCamera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        mainCamera.position.set(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f,0);

        gameViewport =  new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), mainCamera);

        buttons = new WeaponScreenButtons(this.game, this.current_player, bScreen);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();

        game.getBatch().draw(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        game.getBatch().draw(swordTex, Gdx.graphics.getWidth()/4-200,300,300,600);
        game.getBatch().draw(axeTex, 2*Gdx.graphics.getWidth()/4-300,300, 300,600);
        game.getBatch().draw(lanceTex, 3*Gdx.graphics.getWidth()/4-400,300, 300,600);
        game.getBatch().draw(bowTex, Gdx.graphics.getWidth()-500,300, 300,600);

        game.getBatch().end();
        game.getBatch().setProjectionMatrix(buttons.getStage().getCamera().combined);
        buttons.getStage().draw();
    }

    /**
     * Dispose all the objects that are disposable
     */
    @Override
    public void dispose() {
        bg.dispose();
        game.getBatch().dispose();
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
