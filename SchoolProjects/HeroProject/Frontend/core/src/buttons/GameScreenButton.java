package buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

import java.util.Random;

import game.entities.Player;
import game.scene.MainMenu;
import game.scene.SingleOrMultiScreen;

public class GameScreenButton {
    private MyGdxGame game;
    private Stage stage;
    private Viewport gameViewport;

    private ImageButton back_button;
    private TextButton end_turn_button, menu_button, end_run;
    public ImageButton up_button, down_button, left_button, right_button, stop_button;
    public ImageButton battle_dialog, Yes, No;
    private Skin mySkin;

    public Player current_player;

    public Net.HttpRequest httpRequest;

    public GameScreenButton (MyGdxGame game, Player current_player){
        this.current_player = current_player;
        this.game = game;
        gameViewport =  new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage (gameViewport, game.getBatch());

        Gdx.input.setInputProcessor(stage);//making the stage actually clickable
        createAndPositionElements();
        stage.addActor(back_button);
        stage.addActor(end_turn_button);
        stage.addActor(menu_button);
        stage.addActor(end_run);
        stage.addActor(left_button);
        stage.addActor(up_button);
        stage.addActor(down_button);
        stage.addActor(right_button);
        stage.addActor(stop_button);


        addActionListeners();



    }

    void createAndPositionElements(){

        back_button = new ImageButton(new SpriteDrawable(new Sprite( new Texture("back_button.png"))));
        back_button.setPosition(20, Gdx.graphics.getHeight() - 40, Align.topLeft);

        mySkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        end_turn_button = new TextButton("End Turn", mySkin, "normal");

        end_turn_button.setSize(250,90);
        end_turn_button.setPosition(Gdx.graphics.getWidth()-280, Gdx.graphics.getHeight()-220);

        menu_button = new TextButton("Menu", mySkin, "normal");
        menu_button.setSize(250,90);
        menu_button.setPosition(Gdx.graphics.getWidth()-280, Gdx.graphics.getHeight()-420);

        end_run = new TextButton("End Run", mySkin, "normal");
        end_run.setSize(250,90);
        end_run.setPosition(Gdx.graphics.getWidth()-280, Gdx.graphics.getHeight()-1020);

        up_button = new ImageButton(new SpriteDrawable(new Sprite( new Texture("Player/Buttons/up.png"))));
        up_button.setSize(100,100);
        up_button.setPosition(Gdx.graphics.getWidth()-200, Gdx.graphics.getHeight()-620);

        right_button = new ImageButton(new SpriteDrawable(new Sprite( new Texture("Player/Buttons/right.png"))));
        right_button.setSize(100,100);
        right_button.setPosition(Gdx.graphics.getWidth()-100, Gdx.graphics.getHeight()-720);

        down_button = new ImageButton(new SpriteDrawable(new Sprite( new Texture("Player/Buttons/down.png"))));
        down_button.setSize(100,100);
        down_button.setPosition(Gdx.graphics.getWidth()-200, Gdx.graphics.getHeight()-820);

        left_button = new ImageButton(new SpriteDrawable(new Sprite( new Texture("Player/Buttons/left.png"))));
        left_button.setSize(100,100);
        left_button.setPosition(Gdx.graphics.getWidth()-300, Gdx.graphics.getHeight()-720);

        stop_button = new ImageButton(new SpriteDrawable(new Sprite( new Texture("Player/Buttons/stop.png"))));
        stop_button.setSize(100,100);
        stop_button.setPosition(Gdx.graphics.getWidth()-200, Gdx.graphics.getHeight()-720);


    }

    public void createAndPositionDialog(){
        battle_dialog = new ImageButton(new SpriteDrawable(new Sprite( new Texture("Player/Buttons/battle_dialog.png"))));
        battle_dialog.setSize(600,300);
        battle_dialog.setPosition(Gdx.graphics.getWidth()/2-390, Gdx.graphics.getHeight()/2-45);

        Yes = new ImageButton(new SpriteDrawable(new Sprite( new Texture("Player/Buttons/dialog_boxYes.png"))));
        Yes.setSize(300,100);
        Yes.setPosition((Gdx.graphics.getWidth()/2)-390, Gdx.graphics.getHeight()/2 - 20);

        No = new ImageButton(new SpriteDrawable(new Sprite( new Texture("Player/Buttons/dialog_boxNo.png"))));
        No.setSize(300,100);
        No.setPosition((Gdx.graphics.getWidth()/2)-90, Gdx.graphics.getHeight()/2 - 20);

        stage.addActor(battle_dialog);
        stage.addActor(Yes);
        stage.addActor(No);
    }
//    void createAndPositionBackElement(){
//        back_button = new ImageButton(new SpriteDrawable(new Sprite( new Texture("back_button.png"))));
//        back_button.setPosition(20, Gdx.graphics.getHeight() - 40, Align.topLeft);
//    }
//    void createAndPositionPlayElements(){
//        left_button = new ImageButton(new SpriteDrawable(new Sprite( new Texture("Player/Buttons/left.png"))));
//        left_button.setSize(30,30);
//        left_button.setPosition(Gdx.graphics.getWidth()-280, Gdx.graphics.getHeight()-820);
//    }

    void addActionListeners(){
        end_turn_button.addListener(new InputListener(){
//            @Override
//            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
//                //outputLabel.setText("Press a Button");
//                System.out.println("end turn button pressed 1");
//            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //outputLabel.setText("Pressed Text Button");
                System.out.println("end turn button pressed 2");
                return true;
            }
        });
        menu_button.addListener(new InputListener(){
//            @Override
//            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
//                //outputLabel.setText("Press a Button");
//                System.out.println("menu_button pressed 1");
//            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //outputLabel.setText("Pressed Text Button");
                System.out.println("menu_button pressed 2");
                return true;
            }
        });
        end_run.addListener(new ChangeListener(){

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Random r = new Random();
                int low = 10;
                int high = 100;
                int id = r.nextInt(high-low) + low;
//                int low2 = 5000;
//                int high2 = 9000;
//                int score = r.nextInt(high-low) + low;
//                current_player.updateScore(score);
                String s = "{\"id\":"+id+",\"name\":\""+current_player.getUsername()+"\",\"score\":"+current_player.getCurrentScore()+"}";
                httpRequest = new Net.HttpRequest(Net.HttpMethods.PUT);
                httpRequest.setUrl("http://coms-309-sd-2.misc.iastate.edu:8080/score");
                httpRequest.setHeader("Content-Type", "application/json");
                httpRequest.setContent(s);
                Net.HttpResponseListener listener = new Net.HttpResponseListener() {
                    public void handleHttpResponse(Net.HttpResponse httpResponse) {
                        final int statusCode = httpResponse.getStatus().getStatusCode();
                        System.out.println("Status code: "+statusCode);
                        System.out.println(httpResponse.getResultAsString());

                    }

                    public void failed(Throwable t) {
                        System.out.println("HTTP request failed! "+ t.getMessage());
                    }

                    @Override
                    public void cancelled() {

                    }
                };

                Gdx.net.sendHttpRequest(httpRequest, listener);
                System.out.println("end run pressed 2");
                game.setScreen(new MainMenu(game, current_player));
            }
        });

        back_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new SingleOrMultiScreen(game, current_player));
            }
        });
    }

    public Stage getStage(){
        return this.stage;
    }

}


