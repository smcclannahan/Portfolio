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

import game.entities.Player;
import game.scene.CharacterScreen;
import game.scene.GameScreen;
import game.scene.Leaderboard;
import game.scene.MainOption;
import game.scene.SingleOrMultiScreen;

public class MainMenuButtons {
    private MyGdxGame game;
    private Stage stage;
    private Viewport gameViewport;

    private ImageButton play_button;
    private ImageButton highscore_button;
    private ImageButton option_button;
    private ImageButton quit_button;
    private ImageButton music_button;
    private TextButton character_page;

    private Player current_player;
    private Skin mySkin;

    public MainMenuButtons(MyGdxGame game, Player current_player){
        this.game = game;
        this.current_player = current_player;
        gameViewport =  new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());

        stage = new Stage (gameViewport, game.getBatch());

        Gdx.input.setInputProcessor(stage);//making the stage actually clickable
        mySkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        createButtons();
        addActionListeners();

        stage.addActor(play_button);
        stage.addActor(highscore_button);
        stage.addActor(option_button);
        stage.addActor(quit_button);
        stage.addActor(music_button);
        stage.addActor(character_page);

    }

    void createButtons(){
        play_button = new ImageButton(new SpriteDrawable(new Sprite(new Texture("play4.png"))));
        highscore_button = new ImageButton(new SpriteDrawable(new Sprite(new Texture("score2.png"))));
        option_button = new ImageButton(new SpriteDrawable(new Sprite(new Texture("option2.png"))));
        quit_button = new ImageButton(new SpriteDrawable(new Sprite(new Texture("quit2.png"))));
        music_button = new ImageButton(new SpriteDrawable(new Sprite(new Texture("music2.png"))));

        play_button.setPosition(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f + 140 , Align.center);
        //play_button.setSize(400, 400);
        highscore_button.setPosition(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f - 120, Align.center);
        option_button.setPosition(Gdx.graphics.getWidth() -20, Gdx.graphics.getHeight(), Align.topRight);
        quit_button.setPosition(20, Gdx.graphics.getHeight() - 40, Align.topLeft);
        music_button.setPosition(Gdx.graphics.getWidth() -13, 13, Align.bottomRight);

        character_page = new TextButton("CHARACTERS", mySkin, "normal");
        character_page.setPosition(Gdx.graphics.getWidth()/2f-170,Gdx.graphics.getHeight()/2f-40);
        character_page.setSize(370, 60);
        character_page.setScale(2,2);

    }

    void addActionListeners(){
        play_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                    game.setScreen(new SingleOrMultiScreen(game, current_player));
            }
        });
        option_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainOption(game, current_player));
            }
        });
        highscore_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new Leaderboard(game, current_player));
            }
        });
        music_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
        character_page.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new CharacterScreen(game, current_player));
            }
        });
        quit_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.exit(0);
            }
        });

    }

    public Stage getStage(){
        return this.stage;
    }
}
