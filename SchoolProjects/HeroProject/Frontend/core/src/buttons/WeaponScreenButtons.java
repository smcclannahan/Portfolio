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

import game.entities.AxeEnemy;
import game.entities.Player;
import game.scene.BattleScreen;
import game.scene.MainMenu;

public class WeaponScreenButtons {
    private MyGdxGame game;
    private Stage stage;
    private Viewport gameViewport;

    private TextButton swordbt, axebt, lancebt, bowbt;
    private Label label;
    private ImageButton back_button;
    private BattleScreen bScreen;
    public AxeEnemy enemy;

    private Player current_player;
    private Skin mySkin;

    public WeaponScreenButtons(MyGdxGame game, Player current_player, BattleScreen b){
        this.game = game;
        this.current_player = current_player;
        gameViewport =  new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());

        bScreen = b;
        stage = new Stage (gameViewport, game.getBatch());

        Gdx.input.setInputProcessor(stage);//making the stage actually clickable
        mySkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        createButtons();
        addActionListeners();

        stage.addActor(swordbt);
        stage.addActor(axebt);
        stage.addActor(lancebt);
        stage.addActor(bowbt);
        stage.addActor(label);
        stage.addActor(back_button);


    }
    void createButtons(){
        swordbt = new TextButton("Equip Sword", mySkin, "normal");
        swordbt.setPosition(Gdx.graphics.getWidth()/4-200,200);
        swordbt.setSize(350, 60);
        swordbt.setScale(2,2);

        axebt = new TextButton("Equip Axe", mySkin, "normal");
        axebt.setPosition(2*Gdx.graphics.getWidth()/4-300,200);
        axebt.setSize(300, 60);

        lancebt = new TextButton("Equip Lance", mySkin, "normal");
        lancebt.setPosition(3*Gdx.graphics.getWidth()/4-400,200);
        lancebt.setSize(350, 60);

        bowbt = new TextButton("Equip Bow", mySkin, "normal");
        bowbt.setPosition(Gdx.graphics.getWidth()-500,200);
        bowbt.setSize(300, 60);

        label = new Label("Current Player: "+current_player.getCharacter().toUpperCase(), mySkin);
        label.setPosition(Gdx.graphics.getWidth()/2f-50,Gdx.graphics.getHeight()-150, Align.center);
        label.setFontScale(3, 3);

        back_button = new ImageButton(new SpriteDrawable(new Sprite( new Texture("back_button.png"))));
        back_button.setPosition(20, Gdx.graphics.getHeight() - 40, Align.topLeft);
    }
    void addActionListeners(){
        swordbt.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                current_player.equipWeapon("sword");
                label.setText(current_player.getEquippedWeapon().toUpperCase() + " Equipped!");
            }
        });
        axebt.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                current_player.equipWeapon("axe");
                label.setText(current_player.getEquippedWeapon().toUpperCase()+" Equipped!");
            }
        });
        lancebt.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                current_player.equipWeapon("lance");
                label.setText(current_player.getEquippedWeapon().toUpperCase()+" Equipped!");
            }
        });
        bowbt.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                current_player.equipWeapon("bow");
                label.setText(current_player.getEquippedWeapon().toUpperCase()+" Equipped!");
            }
        });
        back_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new BattleScreen(game, current_player, bScreen.getEnemy()));
            }
        });


    }

    public Stage getStage(){
        return this.stage;
    }
}
