package game.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

import buttons.LoginButton;
import game.entities.Player;

public class LoginScreen implements Screen {
    private MyGdxGame game;
    private OrthographicCamera mainCamera;
    private Viewport gameViewport;

    private Texture background;

    public LoginButton loginButton;
    private TextButton login_button;
    private TextButton register_button;
    public Net.HttpRequest httpRequest;
    private Net.HttpResponseListener listener;
    private boolean login=false;
    private boolean register=false;
    private Label usernameLblWarning;
    private Label passwordLblWarning;
    private Label warning;
    private Skin skin;
    private Stage login_stage;

    /**
     * Login screen constructor
     * @param game
     * Represents the current game session
     */
    public LoginScreen(MyGdxGame game){
        this.game = game;
        background = new Texture("game_background.jpg");

        mainCamera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        mainCamera.position.set(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f,0);

        gameViewport =  new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
                mainCamera);
        login_stage = new Stage (gameViewport, game.getBatch());
        loginButton = new LoginButton(this.game);

        createAndPositionHeaderElements();
        loginButton.getStage().addActor(login_button);
        loginButton.getStage().addActor(register_button);
        skin = new Skin(Gdx.files.internal("skin/shadeui/uiskin.json"));


    }

    /**
     * Effectuate login button listeners
     */
    @Override
    public void show() {
    }
    /**
     * Renders the players and login screen actors
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

        //DISPLAY back all buttons from gameScreen
        addActionListeners();
        game.getBatch().setProjectionMatrix(loginButton.getStage().getCamera().combined);
        loginButton.getStage().draw();
        if(login || register){
            game.setScreen(new MainMenu(game, loginButton.current_player));
        }
    }

    /**
     * Position header elements for login screen
     */
    void createAndPositionHeaderElements(){

        login_button = new TextButton("LOGIN", loginButton.mySkin);
        login_button.setPosition(Gdx.graphics.getWidth()/2f +250, Gdx.graphics.getHeight()/2f+100);
        login_button.setSize(200, 60);

        register_button = new TextButton("REGISTER", loginButton.mySkin);
        register_button.setPosition(Gdx.graphics.getWidth()/2f-250, Gdx.graphics.getHeight()/2f+100);
        register_button.setSize(200, 60);
    }

    void addActionListeners(){
        login_button.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                loginButton.current_player = new Player(loginButton.username.getText(), loginButton.password.getText(), "p1");
                if(!loginButton.current_player.getUsername().isEmpty() && !loginButton.current_player.getPassword().isEmpty()) {
                    final String s = "{\"name\":\"" + loginButton.current_player.getUsername() + "\",\"password\":\"" + loginButton.current_player.getPassword() + "\"}";
                    System.out.println(s);
                    httpRequest = new Net.HttpRequest(Net.HttpMethods.PUT);
                    httpRequest.setUrl("http://coms-309-sd-2.misc.iastate.edu:8080/login");
                    httpRequest.setHeader("Content-Type", "application/json");
                    httpRequest.setContent(s);
                    listener = new Net.HttpResponseListener() {
                        public void handleHttpResponse(Net.HttpResponse httpResponse) {
                            try {
                                String response = httpResponse.getResultAsString();
                                final int statusCode = httpResponse.getStatus().getStatusCode();
                                if (statusCode == 200) {
                                    if(response.contains("no such user")){
                                        //not a user
                                        //login_button.getStage().getActors().removeValue(warning, true);
//                                        warning = new Label("No user with this username. Please Try Again!", skin);
//                                        warning.setPosition(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f-20);
//                                        warning.setFontScale(3,3);
//                                        login_stage.addActor(warning);
                                    }else if(response.contains("wrong password")){
                                        //wrong password
                                        //login_button.getStage().getActors().removeValue(warning, true);
//                                        warning = new Label("Wrong password, Please Try Again!", skin);
//                                        warning.setPosition(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f-130);
//                                        warning.setFontScale(3,3);
//                                        login_stage.addActor(warning);
                                    }else if (response.contains("logged in")) {
                                        //System.out.println("Status code: 200 ");
                                        login = true;
                                    }

                                } else {
                                    System.out.println("Status code: " + statusCode);
                                    System.out.println("http: " + httpResponse.getResultAsString());
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
                }
                else if(loginButton.current_player.getUsername().isEmpty() && loginButton.current_player.getPassword().isEmpty()){
                    login_button.getStage().getActors().removeValue(usernameLblWarning, true);
                    login_button.getStage().getActors().removeValue(passwordLblWarning, true);

                    usernameLblWarning = new Label("Please enter username!", skin);
                    usernameLblWarning.setPosition(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f-20);
                    usernameLblWarning.setFontScale(3,3);

                    passwordLblWarning = new Label("Please enter password!", skin);
                    passwordLblWarning.setPosition(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f-130);
                    passwordLblWarning.setFontScale(3,3);

                    login_button.getStage().addActor(usernameLblWarning);
                    login_button.getStage().addActor(passwordLblWarning);

                }
                else if(loginButton.current_player.getUsername().isEmpty() && !loginButton.current_player.getPassword().isEmpty()){
                    login_button.getStage().getActors().removeValue(usernameLblWarning, true);
                    login_button.getStage().getActors().removeValue(passwordLblWarning, true);
                    usernameLblWarning = new Label("Please enter username!", skin);
                    usernameLblWarning.setPosition(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f-20);
                    usernameLblWarning.setFontScale(3,3);

                    login_button.getStage().addActor(usernameLblWarning);

                }
                else if(loginButton.current_player.getPassword().isEmpty() && !loginButton.current_player.getUsername().isEmpty()){
                    login_button.getStage().getActors().removeValue(usernameLblWarning, true);
                    login_button.getStage().getActors().removeValue(passwordLblWarning, true);
                    passwordLblWarning = new Label("Please enter password!", skin);
                    passwordLblWarning.setPosition(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f-130);
                    passwordLblWarning.setFontScale(3,3);
                    login_button.getStage().addActor(passwordLblWarning);

                }

            }
        });

        register_button.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                loginButton.current_player = new Player(loginButton.username.getText(), loginButton.password.getText(), "p1");
                if(!loginButton.current_player.getUsername().isEmpty() && !loginButton.current_player.getPassword().isEmpty()) {
                final String s = "{\"name\":\"" + loginButton.current_player.getUsername() + "\",\"password\":\"" + loginButton.current_player.getPassword() + "\"}";
                System.out.println(s);
                httpRequest = new Net.HttpRequest(Net.HttpMethods.POST);
                httpRequest.setUrl("http://coms-309-sd-2.misc.iastate.edu:8080/newPlayer");
                httpRequest.setHeader("Content-Type", "application/json");
                httpRequest.setContent(s);
                listener = new Net.HttpResponseListener() {
                    public void handleHttpResponse(Net.HttpResponse httpResponse) {
                        try {
                            final int statusCode = httpResponse.getStatus().getStatusCode();
                            String response = httpResponse.getResultAsString();
                            if (statusCode == 200) {
                                if(response.contains("user name is alrady taken")){
                                    //user taken
//                                    login_button.getStage().getActors().removeValue(warning, true);
//                                    warning = new Label("Username already taken. Please try Again!", skin);
//                                    warning.setPosition(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f-20);
//                                    warning.setFontScale(3,3);
//                                    login_button.getStage().addActor(warning);
                                }else if(response.contains("welcome")){
                                    //welcome
                                    register = true;
                                }
                            } else {
                                System.out.println("Register Status code: " + statusCode);
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

                    }
                };

                Gdx.net.sendHttpRequest(httpRequest, listener);
            }

            else if (loginButton.current_player.getUsername().isEmpty() && loginButton.current_player.getPassword().isEmpty()) {
                    login_button.getStage().getActors().removeValue(usernameLblWarning, true);
                    login_button.getStage().getActors().removeValue(passwordLblWarning, true);

                    usernameLblWarning = new Label("Please enter username!", skin);
                    usernameLblWarning.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f - 20);
                    usernameLblWarning.setFontScale(3, 3);

                    passwordLblWarning = new Label("Please enter password!", skin);
                    passwordLblWarning.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f - 130);
                    passwordLblWarning.setFontScale(3, 3);

                    login_button.getStage().addActor(usernameLblWarning);
                    login_button.getStage().addActor(passwordLblWarning);

                } else if (loginButton.current_player.getUsername().isEmpty() && !loginButton.current_player.getPassword().isEmpty()) {
                    login_button.getStage().getActors().removeValue(usernameLblWarning, true);
                    login_button.getStage().getActors().removeValue(passwordLblWarning, true);
                    usernameLblWarning = new Label("Please enter username!", skin);
                    usernameLblWarning.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f - 20);
                    usernameLblWarning.setFontScale(3, 3);

                    login_button.getStage().addActor(usernameLblWarning);

                } else if (loginButton.current_player.getPassword().isEmpty() && !loginButton.current_player.getUsername().isEmpty()) {
                    login_button.getStage().getActors().removeValue(usernameLblWarning, true);
                    login_button.getStage().getActors().removeValue(passwordLblWarning, true);
                    passwordLblWarning = new Label("Please enter password!", skin);
                    passwordLblWarning.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f - 130);
                    passwordLblWarning.setFontScale(3, 3);
                    login_button.getStage().addActor(passwordLblWarning);

                }
            }
        });
    }

    /**
     * Dispose all the objects that are disposable
     */
    @Override
    public void dispose() {
        background.dispose();
        loginButton.getStage().dispose();
        loginButton.mySkin.dispose();
        game.getBatch().dispose();
        login_stage.dispose();

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