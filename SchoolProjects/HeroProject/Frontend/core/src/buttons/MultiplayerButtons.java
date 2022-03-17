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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.Joinable;

import game.entities.Player;
import game.scene.ChattingSystem;
import game.scene.GameScreen;
import game.scene.LobbyScreen;
import game.scene.MainMenu;
import game.scene.MultiplayerScreen;
import game.scene.OpenRoomsScreen;
import game.scene.SingleOrMultiScreen;

public class MultiplayerButtons {

    private MyGdxGame game;
    private Stage stage;
    private Viewport gameViewport;

    private ImageButton back_button, createGameButton, joinGameButton;
    private TextButton singleplayer_button, multiplayer_button;

    private Player current_player;
    public Net.HttpRequest httpRequest;
    private Net.HttpResponseListener listener;
    private Map<Integer, String> room_data = new HashMap<Integer, String>();
    private Label label;
    public int id_room;
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    BitmapFont font;

    public MultiplayerButtons(MyGdxGame game, Player current_player){
        this.game = game;
        this.current_player = current_player;
        gameViewport =  new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());

        stage = new Stage (gameViewport, game.getBatch());

        Gdx.input.setInputProcessor(stage);//making the stage actually clickable
        createButtons();
        addActionListeners();

        stage.addActor(back_button);
        stage.addActor(createGameButton);
        stage.addActor(joinGameButton);
        stage.addActor(label);
    }

    void createButtons(){
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/unbom.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        font = generator.generateFont(parameter);

        back_button = new ImageButton(new SpriteDrawable(new Sprite( new Texture("back_button.png"))));
        back_button.setPosition(20, Gdx.graphics.getHeight() - 40, Align.topLeft);

        createGameButton = new ImageButton(new SpriteDrawable(new Sprite(new Texture("CreateButton.png"))));
        createGameButton.setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2+100,Align.center);

        joinGameButton = new ImageButton(new SpriteDrawable(new Sprite(new Texture("JoinButton.png"))));
        joinGameButton.setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2-150,Align.center);

        label = new Label(" ", new Label.LabelStyle(font, Color.WHITE));
        label.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight() - 120);


    }

    void addActionListeners(){
        back_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new SingleOrMultiScreen(game, current_player));
            }
        });
        createGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //game.setScreen(new LobbyScreen(game, current_player));
                String s = "{\"hostname\":\""+current_player.getUsername()+"\"}";
                httpRequest = new Net.HttpRequest(Net.HttpMethods.PUT);
                httpRequest.setUrl("http://coms-309-sd-2.misc.iastate.edu:8080/hostgame");
                httpRequest.setHeader("Content-Type", "application/json");
                httpRequest.setContent(s);
                listener = new Net.HttpResponseListener() {
                    public void handleHttpResponse(Net.HttpResponse httpResponse) {
                        try {
                            final int statusCode = httpResponse.getStatus().getStatusCode();
                            if (statusCode == 200) {
                                String response = httpResponse.getResultAsString();
                                int id = Integer.parseInt(response);
                                if(id>-1){
                                    id_room = id;
                                    System.out.println("id: "+id_room);
                                    createGameroom(id_room);
                                }
                            } else {
                                System.out.println("Status code: " + statusCode+" "+httpResponse.getResultAsString());
                            }
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }

                    }

                    public void failed(Throwable t) {
                        System.out.println("HTTP request failed! " + t.getMessage());
                    }

                    @Override
                    public void cancelled() {
                        System.out.println("Cancelled");
                    }
                };

                Gdx.net.sendHttpRequest(httpRequest, listener);
                System.out.println("id room: " + id_room);
            }
        });
        joinGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new OpenRoomsScreen(game, current_player));//Seperate screens?
            }
        });

    }

    void createGameroom(int id){
        //label = new Label("Room "+id +" created", new Label.LabelStyle(font, Color.WHITE));
        label.setText("Room "+id +" created");
        label.setPosition(Gdx.graphics.getWidth()/2-120, Gdx.graphics.getHeight() - 120);
        //game.setScreen(new GameScreen(game, current_player));
    }

    public Stage getStage(){
        return this.stage;
    }
}
