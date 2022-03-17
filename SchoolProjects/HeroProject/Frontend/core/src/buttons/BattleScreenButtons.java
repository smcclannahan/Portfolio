package buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

import java.util.Random;

import game.Weapons.Axe;
import game.entities.AxeEnemy;
import game.entities.Player;
import game.scene.MultiplayerScreen;

public class BattleScreenButtons {

    private MyGdxGame game;
    private Stage stage;
    private Viewport gameViewport;

    public Skin mySkin;
    public Player current_player;
    public AxeEnemy enemy;
    private ImageButton back_button;
    public TextButton attack_button, defend_button, escape_button;
    public Label username_label, hp_label;
    private Label enemy_label, enemy_hp_label;


    public BattleScreenButtons (MyGdxGame game, Player current_player, AxeEnemy axeEnemy){
        this.game = game;
        this.enemy = axeEnemy;
        this.current_player =current_player;
        gameViewport =  new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage (gameViewport, game.getBatch());

        Gdx.input.setInputProcessor(stage);//making the stage actually clickable
        mySkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        createAndPositionElements();
        createInfoBar();
        //stage.addActor(back_button);


        addListener();

    }

    void createAndPositionElements(){
//        back_button = new ImageButton(new SpriteDrawable(new Sprite( new Texture("back_button.png"))));
//        back_button.setPosition(20, Gdx.graphics.getHeight() - 40, Align.topLeft);

        attack_button = new TextButton("ATTACK", mySkin, "normal");
        attack_button.setColor(Color.MAROON);
        attack_button.setPosition(80,Gdx.graphics.getHeight()/4f-80);
        attack_button.setSize(250, 60);
        attack_button.setScale(2,2);

        defend_button = new TextButton("DEFEND", mySkin, "normal");
        defend_button.setColor(Color.MAROON);
        defend_button.setPosition(80,Gdx.graphics.getHeight()/4f-160);
        defend_button.setSize(250, 60);

        escape_button = new TextButton("ESCAPE", mySkin, "normal");
        escape_button.setColor(Color.MAROON);
        escape_button.setPosition(80,Gdx.graphics.getHeight()/4f-240);
        escape_button.setSize(250, 60);

//        login_button = new TextButton("LOGIN", loginButton.mySkin);
//        login_button.setPosition(Gdx.graphics.getWidth()/2f +250, Gdx.graphics.getHeight()/2f+100);
//        login_button.setSize(200, 60);

        stage.addActor(attack_button);
        stage.addActor(defend_button);
        stage.addActor(escape_button);

    }
    void createInfoBar(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/unbom.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        BitmapFont font = generator.generateFont(parameter);

        username_label = new Label(current_player.getUsername().toUpperCase(), new Label.LabelStyle(font, Color.BLACK));
        username_label.setPosition(Gdx.graphics.getWidth()-200,Gdx.graphics.getHeight()/4f-80);

        hp_label = new Label("HP", new Label.LabelStyle(font, Color.BLACK));
        hp_label.setPosition(Gdx.graphics.getWidth()-80,Gdx.graphics.getHeight()/4f-160);

        enemy_label = new Label(enemy.getName().toUpperCase(), new Label.LabelStyle(font, Color.BLACK));
        enemy_label.setPosition(350,Gdx.graphics.getHeight()/4f-80);

        enemy_hp_label = new Label("HP", new Label.LabelStyle(font, Color.BLACK));
        enemy_hp_label.setPosition(350,Gdx.graphics.getHeight()/4f-160);

        stage.addActor(username_label);
        stage.addActor(hp_label);

        stage.addActor(enemy_label);
        stage.addActor(enemy_hp_label);

    }

    void addListener(){
//        back_button.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                game.setScreen(new MultiplayerScreen(game, current_player));
//            }
//        });
    }

    public Stage getStage(){
        return this.stage;
    }
}
