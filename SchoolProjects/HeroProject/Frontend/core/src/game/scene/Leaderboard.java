package game.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

import buttons.HighScoreButtons;
import game.entities.Player;

public class Leaderboard implements Screen {

    private MyGdxGame game;
    private OrthographicCamera mainCamera;
    private Viewport gameViewport;

    private Texture background;

    private HighScoreButtons highScoreButtons;
    private Label name_title, score_title, level_title;
    private Label score_label, name_label,level_label;

    public Net.HttpRequest httpRequest;
    private Player current_player;

    /**
     * Leaderboard constructor
     * @param game
     * Represents the current game session
     * @param currentPlayer
     * Represents the player playing the game
     */
    public Leaderboard(MyGdxGame game, Player currentPlayer){
        this.game = game;
        this.current_player = currentPlayer;
        mainCamera = new OrthographicCamera();
        mainCamera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        mainCamera.position.set(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f,0);

        gameViewport =  new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
                mainCamera);
        background = new Texture("chalkboard.jpg");
        highScoreButtons = new HighScoreButtons(game);
        createAndPositionHeaderElements();

        highScoreButtons.getStage().addActor(name_title);
        highScoreButtons.getStage().addActor(score_title);
        highScoreButtons.getStage().addActor(level_title);
    }

    @Override
    public void show() {
        String requestContent = null;

        httpRequest = new Net.HttpRequest(Net.HttpMethods.GET);
        httpRequest.setUrl("http://coms-309-sd-2.misc.iastate.edu:8080/score");
        httpRequest.setHeader("Content-Type", "text/plain");
        httpRequest.setContent(requestContent);
        Net.HttpResponseListener listener = new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                final int statusCode = httpResponse.getStatus().getStatusCode();
                String response = httpResponse.getResultAsString();
                try {
                    JsonReader json = new JsonReader();
                    JsonValue score_response = json.parse(response);
                    createAndPositionListElements(score_response);
                }
                catch (Exception e){
                    System.out.println("Error: " + e.getMessage());
                }

            }

            public void failed(Throwable t) {
                System.out.println("HTTP request failed! "+ t.getMessage());
            }

            @Override
            public void cancelled() {

            }
        };

        Gdx.net.sendHttpRequest(httpRequest, listener);


    }

    /**
     * Position header elements for leaderboard screen
     */
    void createAndPositionHeaderElements(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/chalk.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter header_parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        header_parameter.size = 50;
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;

        BitmapFont usernameTitle_font = generator.generateFont(header_parameter);
        BitmapFont scoreTitle_font = generator.generateFont(header_parameter);
        BitmapFont levelTitle_font = generator.generateFont(header_parameter);

        BitmapFont scoreFont = generator.generateFont(parameter);
        BitmapFont coinFont = generator.generateFont(parameter);
        //LEADERBOAR HEADER
        name_title = new Label("Username", new Label.LabelStyle(usernameTitle_font, Color.WHITE));
        score_title = new Label("Score", new Label.LabelStyle(scoreTitle_font, Color.WHITE));
        level_title = new Label("Level", new Label.LabelStyle(levelTitle_font, Color.WHITE));

        name_title.setPosition(Gdx.graphics.getWidth()/4f, Gdx.graphics.getHeight() - 100);
        score_title.setPosition(2* Gdx.graphics.getWidth()/4f, Gdx.graphics.getHeight() - 100);
        level_title.setPosition(3* Gdx.graphics.getWidth()/4f, Gdx.graphics.getHeight() - 100);

    }

    /**
     * Position leaderboard actors in the leaderboard screen such as playerName, score and level
     * @param score_response
     * Players score data to be display
     */
    void createAndPositionListElements(JsonValue score_response){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/chalk.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;

        BitmapFont scoreFont = generator.generateFont(parameter);
        BitmapFont coinFont = generator.generateFont(parameter);


        //LEADERBOAD DATA LIST
        int space = 2;
        for (JsonValue data: score_response)
        {
            if(space <=9) {
                //PRINT current player score| Later we'll have to check with this.player.username
                if(data.getString("name").equalsIgnoreCase(current_player.userName)){
                    score_label = new Label(data.getString("score"), new Label.LabelStyle(scoreFont, Color.GREEN));
                    name_label = new Label(data.getString("name"), new Label.LabelStyle(coinFont, Color.GREEN));
                    level_label = new Label(data.getString("id"), new Label.LabelStyle(coinFont, Color.GREEN));
                }
                else {
                    score_label = new Label(data.getString("score"), new Label.LabelStyle(scoreFont, Color.WHITE));
                    name_label = new Label(data.getString("name"), new Label.LabelStyle(coinFont, Color.WHITE));
                    level_label = new Label(data.getString("id"), new Label.LabelStyle(coinFont, Color.WHITE));
                }
                name_label.setPosition(Gdx.graphics.getWidth() / 4f, Gdx.graphics.getHeight() - 100 * space);
                score_label.setPosition(2 * Gdx.graphics.getWidth() / 4f, Gdx.graphics.getHeight() - 100 * space);
                level_label.setPosition(3 * Gdx.graphics.getWidth() / 4f, Gdx.graphics.getHeight() - 100 * space);

                highScoreButtons.getStage().addActor(score_label);
                highScoreButtons.getStage().addActor(name_label);
                highScoreButtons.getStage().addActor(level_label);
//            System.out.print(data.getString("name")+" ");
//            System.out.print(data.getString("sore")+" ");
//            System.out.print(data.getString("id")+" ");
//            System.out.println();
            }
            space ++;
        }


    }
    /**
     * Renders the players and leader board actors
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


        game.getBatch().setProjectionMatrix(highScoreButtons.getStage().getCamera().combined);
        highScoreButtons.getStage().draw();

    }

    /**
     * Dispose all the objects that are disposable
     */
    @Override
    public void dispose() {
        game.getBatch().dispose();
        background.dispose();
        highScoreButtons.getStage().dispose();
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
