package buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

import game.entities.Player;
import game.scene.MainMenu;

public class CharacterScreenButtons {
    private MyGdxGame game;
    private Stage stage;
    private Viewport gameViewport;

    private TextButton p1Btn,p2Btn,p3Btn,p4Btn;
    private Label label;
    private ImageButton back_button;


    private Player current_player;
    private Skin mySkin;

    public CharacterScreenButtons(MyGdxGame game, Player current_player){
        this.game = game;
        this.current_player = current_player;
        gameViewport =  new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());

        stage = new Stage (gameViewport, game.getBatch());

        Gdx.input.setInputProcessor(stage);//making the stage actually clickable
        mySkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        createButtons();
        addActionListeners();

        stage.addActor(p1Btn);
        stage.addActor(p2Btn);
        stage.addActor(p3Btn);
        stage.addActor(p4Btn);
        stage.addActor(label);
        stage.addActor(back_button);

    }
    void createButtons(){

        p1Btn = new TextButton("Set P1", mySkin, "normal");
        //p1Btn.setColor(Color.MAROON);
        p1Btn.setPosition(Gdx.graphics.getWidth()/4-200,200);
        p1Btn.setSize(250, 60);
        p1Btn.setScale(2,2);

        p2Btn = new TextButton("Set P2", mySkin, "normal");
        //p2Btn.setColor(Color.MAROON);
        p2Btn.setPosition(2*Gdx.graphics.getWidth()/4-300,200);
        p2Btn.setSize(250, 60);

        p3Btn = new TextButton("Set P3", mySkin, "normal");
        //p3Btn.setColor(Color.MAROON);
        p3Btn.setPosition(3*Gdx.graphics.getWidth()/4-400,200);
        p3Btn.setSize(250, 60);

        p4Btn = new TextButton("Set P4", mySkin, "normal");
        //p4Btn.setColor(Color.MAROON);
        p4Btn.setPosition(Gdx.graphics.getWidth()-500,200);
        p4Btn.setSize(250, 60);

        label = new Label("Current Player: "+current_player.getCharacter().toUpperCase(), mySkin);
        label.setPosition(Gdx.graphics.getWidth()/2f-50,Gdx.graphics.getHeight()-150, Align.center);
        //label.setWrap(true);
        label.setFontScale(3, 3);

        back_button = new ImageButton(new SpriteDrawable(new Sprite( new Texture("back_button.png"))));
        back_button.setPosition(20, Gdx.graphics.getHeight() - 40, Align.topLeft);


    }

    void addActionListeners(){
        p1Btn.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                current_player.setCurrentCharacter("p1");
                label.setText("Player "+current_player.getCharacter().toUpperCase()+" Set!");
            }
        });
        p2Btn.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                current_player.setCurrentCharacter("p2");
                label.setText("Player "+current_player.getCharacter().toUpperCase()+" Set!");
            }
        });
        p3Btn.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                current_player.setCurrentCharacter("p3");
                label.setText("Player "+current_player.getCharacter().toUpperCase()+" Set!");
            }
        });
        p4Btn.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                current_player.setCurrentCharacter("p4");
                label.setText("Player "+current_player.getCharacter().toUpperCase()+" Set!");
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
