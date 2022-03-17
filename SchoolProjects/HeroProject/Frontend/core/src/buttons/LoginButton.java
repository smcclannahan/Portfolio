package buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

import game.entities.Player;
import game.scene.GameScreen;
import game.scene.Leaderboard;
import game.scene.MainMenu;

public class LoginButton {
    private MyGdxGame game;
    private Stage stage;
    private Viewport gameViewport;

    public Skin mySkin;
    public TextField username;
    public TextField password;
    public Player current_player;
    private TextButton login_button;
    private TextButton register_button;

    public Net.HttpRequest httpRequest;
    public LoginButton (MyGdxGame game){
        this.game = game;
        gameViewport =  new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage (gameViewport, game.getBatch());

        Gdx.input.setInputProcessor(stage);//making the stage actually clickable
        mySkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        createAndPositionElements();
//        stage.addActor(login_button);
//        stage.addActor(register_button);
        stage.addActor(username );
        stage.addActor(password);

        //addActionListeners();

    }

    void createAndPositionElements(){
        username = new TextField("", mySkin);
        username.setPosition(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f);
        username.setSize(300, 60);

        password = new TextField("", mySkin);
//        password = new TextField("", mySkin, "password");
        password.setPosition(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f-100);
        password.setSize(300, 60);



    }



    public Stage getStage(){
        return this.stage;
    }

}
