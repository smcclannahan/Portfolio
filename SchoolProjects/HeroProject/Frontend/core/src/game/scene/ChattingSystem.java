package game.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import buttons.ChattingSystemButtons;
import buttons.MultiplayerButtons;
import game.entities.Player;

public class ChattingSystem implements Screen {
    private MyGdxGame game;
    private OrthographicCamera mainCamera;
    private Viewport gameViewport;

    private Texture background;

    private Player current_player;
    WebSocketClient cc;
    public ChattingSystemButtons buttons;
    GameScreen gameScreen;

    //Lobby Screentest

    /**
     * MultiplayerScreen constructor
     * @param game
     *  Represents the current game session
     * @param current_player
     * Represents the player playing the game
     */
    public ChattingSystem(MyGdxGame game, Player current_player, WebSocketClient cc, GameScreen gameScreen){
        this.game = game;
        this.current_player =current_player;
        this.cc = cc;
        this.gameScreen =gameScreen;
        background = new Texture("message-center.jpg");

        mainCamera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        mainCamera.position.set(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f,0);

        gameViewport =  new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
                mainCamera);
        this.current_player=current_player;
//        buttons = new ChattingSystemButtons(game, gameScreen);
//        //chattingSystem();
//        addMessagingListeners();
    }
    @Override
    public void show() {

    }
    /**
     * Renders the players and multi player actors
     * @param delta
     * delta time variable
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();
        game.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        game.getBatch().end();

        //game.getBatch().setProjectionMatrix(multiplayerButtons.getStage().getCamera().combined);
        //multiplayerButtons.getStage().draw();

        game.getBatch().setProjectionMatrix(buttons.getStage().getCamera().combined);
        buttons.getStage().draw();
        buttons.getStage().act();




    }

    public void chattingSystem(){
        buttons = new ChattingSystemButtons(game, gameScreen);
        addMessagingListeners();

    }

    void addMessagingListeners() {
        buttons.send_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //game.setScreen( new GameScreen(game) );
                try {
                    String my_message = current_player.userName+": "+buttons.message_field.getText();
                    cc.send("msg: "+my_message);
                    System.out.println(my_message);
                    buttons.chat_label.setText(buttons.chat_label.getText() + my_message+"\n");
                    buttons.message_field.setText("");
                }
                catch (Exception e)
                {
                    System.out.println("ExceptionSendMessage:"+ e.getMessage());
                }

                //System.out.println("Test: " + test);
            }

            ;
        });
    }

    /**
     * Dispose all the objects that are disposable
     */
    @Override
    public void dispose() {
        background.dispose();
        game.getBatch().dispose();
        //multiplayerButtons.getStage().dispose();
        buttons.getStage().dispose();
    }
    
    @Override
    public void resize(int width, int height) {
        buttons.getStage().getViewport().update(width, height, true);
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
