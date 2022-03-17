package buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

import game.entities.AxeEnemy;
import game.entities.Player;
import game.scene.BattleScreen;
import game.scene.ChattingSystem;
import game.scene.GameScreen;
import game.scene.Leaderboard;
import game.scene.MainMenu;
import game.scene.MainOption;
import game.scene.MultiplayerScreen;
import game.scene.ShopScreen;
import game.scene.SingleplayerScreen;

public class PlayOptionsButtons {

    private MyGdxGame game;
    private Stage stage;
    private Viewport gameViewport;

    private ImageButton back_button;
    private TextButton singleplayer_button, multiplayer_button;

    private Player current_player;
    public Skin mySkin;

    public PlayOptionsButtons(MyGdxGame game, Player current_player){
        this.game = game;
        this.current_player = current_player;
        gameViewport =  new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());

        stage = new Stage (gameViewport, game.getBatch());

        Gdx.input.setInputProcessor(stage);//making the stage actually clickable
        mySkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        createButtons();
        addActionListeners();

        stage.addActor(singleplayer_button);
        stage.addActor(multiplayer_button);
        stage.addActor(back_button);

    }

    void createButtons(){
//        singleplayer_button = new ImageButton(new SpriteDrawable(new Sprite(new Texture("play4.png"))));
//        multiplayer_button = new ImageButton(new SpriteDrawable(new Sprite(new Texture("score2.png"))));

        singleplayer_button = new TextButton("SINGLEPLAYER", mySkin, "normal");
        singleplayer_button.setColor(Color.DARK_GRAY);
        singleplayer_button.setPosition(Gdx.graphics.getWidth()/2f-200, Gdx.graphics.getHeight()/2f+100);
        singleplayer_button.setSize(450, 100);

        multiplayer_button = new TextButton("MULTIPLAYER", mySkin, "normal");
        multiplayer_button.setColor(Color.RED);
        multiplayer_button.setPosition(Gdx.graphics.getWidth()/2f-200, Gdx.graphics.getHeight()/2f-100);
        multiplayer_button.setSize(450, 100);

        back_button = new ImageButton(new SpriteDrawable(new Sprite( new Texture("back_button.png"))));
        back_button.setPosition(20, Gdx.graphics.getHeight() - 40, Align.topLeft);

    }

    void addActionListeners(){
        singleplayer_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game, current_player));
                //game.setScreen(new ShopScreen(game, current_player));
            }
        });
        multiplayer_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //game.setScreen(new ChattingSystem(game, current_player));
                game.setScreen(new MultiplayerScreen(game, current_player));
            }
        });
        back_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenu(game, current_player));
            }
        });
    }

    public Stage getStage(){
        return this.stage;
    }
}
