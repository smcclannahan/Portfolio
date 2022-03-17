package game.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.MyGdxGame;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;


import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import buttons.DialogCollision;
import buttons.GameScreenButton;
import game.Weapons.Axe;
import game.battle.SingleplayerBattle;
import game.entities.AxeEnemy;
import game.entities.EntitySuper;
import game.entities.Player;
//import game.entities.TestEnemy;


public class GameScreen implements Screen {

    private MyGdxGame game;
    //Texture bg;//temporarely, will need to create grid
    public Texture img;
    public SpriteBatch sB;
    public static TiledMap map = new TmxMapLoader().load("maps/TowerFloor1-10.tmx");//test2
    public TiledMapRenderer mapRenderer;

    private Player currentPlayer;
    public AxeEnemy axeEnemy;
    private int num=0;

    //new variable
    boolean playerTurn = true;
    boolean isMultiplayer = false;

    World world;

    private OrthographicCamera camera;
    public GameScreenButton gameScreenButton;
    private Label score_label, coin_label;
    private SingleplayerBattle singleplayerBattle;

    //public Dialog dialog;

    public Skin mySkin;
    //boolean i=true;

    private DialogCollision dialogCollision;
    private BattleScreen b;

    /**
     * Constructor for the Game Screen
     *
     * @param game      Represents the current game session
     * @param curPlayer Represents the player playing the game
     */
    public GameScreen(MyGdxGame game, Player curPlayer) {
        this.game = game;
        this.currentPlayer = curPlayer;
        System.out.println("char: "+this.currentPlayer.getCharacter());
        sB = new SpriteBatch();
        world = new World(new Vector2(0, -9.8f), true);
        mySkin = new Skin(Gdx.files.internal("skin_default/uiskin.json"));
//        sr = new ShapeRenderer();
//        sr.setAutoShapeType(true);
////        System.out.println("Width: "+Gdx.graphics.getWidth());
////        System.out.println("Height: "+Gdx.graphics.getHeight());
//        this.currentPlayer = new Player(100, 100, 100,100,curPlayer.getUsername(),curPlayer.getPassword());
//        enemyTest = new TestEnemy(50,50,100,100, "", 20,20);

        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / 32f);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 5, 5);

        //this.currentPlayer = new Player(100, 100, 320,320,20,10,10,3,curPlayer.getUsername(),curPlayer.getPassword());
        this.currentPlayer = new Player(world,currentPlayer.getUsername(),currentPlayer.getCharacter(), 300, 300, 100, 100, 10, 100, 20);//, curPlayer.getUsername(),curPlayer.getPassword());//feel free to uncomment this after uncomment the second block of code in Player.java
        //enemyTest = new TestEnemy(50,50,320,320, "", 20,20);
        axeEnemy = new AxeEnemy(world,100, 300, 100, 100, "axe boy", 10, 100, 10);
        singleplayerBattle = new SingleplayerBattle(this.currentPlayer, axeEnemy);

        gameScreenButton = new GameScreenButton(game, this.currentPlayer);
        positionElements();
        //System.out.println("Iscollide "+singleplayerBattle.isCollide());

    }
    WebSocketClient cc;
    List<WebSocketClient> socketClients = new ArrayList<WebSocketClient>();
    int room_id;
    String url;
    Player partner;
    ImageButton msg_button;
    ChattingSystem chat;
    /**
     * Constructor for the Game Screen
     *
     * @param game      Represents the current game session
     * @param curPlayer Represents the player playing the game
     */
    public GameScreen(MyGdxGame game, Player curPlayer, int room_id) {
        this.game = game;
        this.currentPlayer = curPlayer;
        this.room_id= room_id;
        this.isMultiplayer= true;
        sB = new SpriteBatch();
        world = new World(new Vector2(0, -9.8f), true);
        mySkin = new Skin(Gdx.files.internal("skin_default/uiskin.json"));
//        sr = new ShapeRenderer();
//        sr.setAutoShapeType(true);
////        System.out.println("Width: "+Gdx.graphics.getWidth());
////        System.out.println("Height: "+Gdx.graphics.getHeight());
//        this.currentPlayer = new Player(100, 100, 100,100,curPlayer.getUsername(),curPlayer.getPassword());
//        enemyTest = new TestEnemy(50,50,100,100, "", 20,20);

        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / 32f);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 5, 5);

        //this.currentPlayer = new Player(100, 100, 320,320,20,10,10,3,curPlayer.getUsername(),curPlayer.getPassword());
        partner = new Player("romain","abc", "p1");
        partner = new Player(world,"romain",partner.getCharacter(), 300, 300, 100, 100, 10, 100, 10);
        this.currentPlayer = new Player(world,currentPlayer.getUsername(),currentPlayer.getCharacter(), 300, 300, 100, 100, 10, 100, 10);//, curPlayer.getUsername(),curPlayer.getPassword());//feel free to uncomment this after uncomment the second block of code in Player.java
        //enemyTest = new TestEnemy(50,50,320,320, "", 20,20);
        axeEnemy = new AxeEnemy(world,100, 300, 100, 100, "axe boy", 10, 100, 10);
        singleplayerBattle = new SingleplayerBattle(this.currentPlayer, axeEnemy);

        gameScreenButton = new GameScreenButton(game, this.currentPlayer);

        positionElements();
        positionSocketElements();
        //System.out.println("Iscollide "+singleplayerBattle.isCollide());
        websocketSetup(this.room_id);
//        addSocketListener();
    }
    boolean isAttacking = false;
    public void websocketSetup(int id){
        //singleplayerBattle.isCollide();
        //System.out.println("Iscollide "+singleplayerBattle.isCollide());
        String url = "ws://coms-309-sd-2.misc.iastate.edu:8080/LobbySocket/"+id+"/"+currentPlayer.getUsername();
        //cc = new WebSocketClient(new URI(“url”));

        try {
            //Log.d("Socket:", "Trying socket");
            System.out.println("Socket: Trying socket");
            cc = new WebSocketClient(new URI(url)) {
                @Override
                public void onMessage(String message) {
                    // msg=message;
                    System.out.println("Socket message: "+message);
                    //buttons.chat_label.setText(buttons.chat_label.getText() +message+"\n");
                    if(message.contains("\"x\"")){
                        JsonReader json = new JsonReader();
                        JsonValue response = json.parse(message);
                        System.out.println("data: "+message);
                        //float d = Float.parseFloat(message.substring(5));
                        //System.out.println("true data: "+d);
                        //System.out.println("data x "+response.getString("x"));
                        float x = Float.parseFloat(response.getString("x"));
                        float y = Float.parseFloat(response.getString("y"));
                        System.out.println("data: "+response.getString("isRight"));
                        boolean isRight = Boolean.parseBoolean(response.getString("isRight", "false"));
                        boolean isLeft = Boolean.parseBoolean(response.getString("isLeft","false"));
                        boolean isUp = Boolean.parseBoolean(response.getString("isUp", "false"));
                        boolean isDown = Boolean.parseBoolean(response.getString("isDown", "false"));
                        boolean isStop = Boolean.parseBoolean(response.getString("isStop", "false"));
                        //partner.setPosition(x,y);

                        if(isRight) {

                            partner.movePlayer(50f, axeEnemy);
                        }
                        if(isLeft) {
                            partner.movePlayer(-50f, axeEnemy);
                        }
                        if(isUp) {
                            partner.movePlayerOnY(50f, axeEnemy);
                        }
                        if(isDown) {
                            partner.movePlayerOnY(-50f, axeEnemy);
                        }
                        if(isStop) {
                            partner.stopWalking();
                        }
                    }
                    if(message.contains("character")){
                        String s = message.substring(11);
                        System.out.println("s: "+s);
                        partner.setCurrentCharacter(s);
                    }
                    if(message.contains("username")){
                        String s = message.substring(10);
                        System.out.println("username: "+s);
                        partner.userName=s;
                    }if(message.contains("attack")){
                        axeEnemy.setHp(partner.getDmg());
                        b.game_status.setColor(Color.RED);
                        b.game_status.setText(axeEnemy.getName().toUpperCase()+" turns !");
                    }
                    if(message.contains("attacking")){
                        partner.setHp(axeEnemy.getDmg());
                        //buttons.attack_button.setDisabled(false);
                        b.game_status.setColor(Color.BLUE);
                        b.game_status.setText(partner.getUsername().toUpperCase() + " & " + partner.getUsername().toUpperCase() + " turns !");
                    }
                    if(message.contains("defend")){
                        partner.setDef(Math.abs(partner.getDef()-axeEnemy.getDmg()));
                        b.game_status.setColor(Color.RED);
                        b.game_status.setText(axeEnemy.getName().toUpperCase()+" turns !");
                    }
                    if(message.contains("defending")){
                        b.game_status.setColor(Color.BLUE);
                        b.game_status.setText(partner.getUsername().toUpperCase() + " & " + partner.getUsername().toUpperCase()+" turns !");
                    }
                    if(message.equalsIgnoreCase("isAttacking")){
                        isAttacking = true;
                        System.out.println("isAttacking");
                    }
                    if(message.equalsIgnoreCase("isNotAttacking")){
                        isAttacking = false;
                        System.out.println("isNotAttacking");
                    }
//                    if (isAttacking) {
//                        weaponAnimation.updateWeaponPosition();
//                        weaponAnimation.moveWeaponAnimation((-1) * delta);
//                    } else {
//                        weaponAnimation.setPosition(current_player.getX(), current_player.getPlayerY());
//                    }
                    if(message.contains("msg")){//for msging system
                        System.out.println("received msg: "+message);
                        chat.buttons.chat_label.setText(chat.buttons.chat_label.getText() +message.substring(5)+"\n");
                    }
                }

                @Override
                public void onOpen(ServerHandshake handshake) {
                    //Log.d("OPEN", "run() returned: " + "is connecting");
                    //buttons.users_list.setItems("connected"+"\n");
                    //partner = new Player(world,"sean", 600, 600, 100, 100, 100, 20, 10);
                    System.out.println(handshake.getHttpStatus()+" is connected successfully");
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("Socket close");
                    //buttons.users_list.setItems("disconnected"+"\n");
                }

                @Override
                public void onError(Exception e)
                {
                    System.out.println("Exception: " + e.toString());

                }
            };

        }
        catch (URISyntaxException e) {
            System.out.println("Exception: "+ e.getMessage().toString());
            e.printStackTrace();
        }
        cc.connect();

        socketClients.add(cc);
        //System.out.println("msg: "+msg);
    }


    @Override
    public void show() {
        //websocketSetup(this.room_id);
    }
    void sendToSocket(WebSocketClient s, String json){
//        msg = "hi it's "+currentPlayer.getUsername();
//        p.send(msg);
//        p.onMessage(msg);
        try {

            //String msg = "{\"x\":\""+currentPlayer.getPlayerX()+"\",\"y\":\""+currentPlayer.getPlayerY()+"\"}";
            System.out.println("json msg: "+json);
            s.send(json);
        }
        catch (Exception e)
        {
            System.out.println("ExceptionSendMessage:"+ e.getMessage());
        }

    }

    /**
     * Renders the players and game actors
     *
     * @param delta delta time variable
     */
    @Override
    public void render(float delta) {
        //update(delta);
        //handleCollision();
        singleplayerBattle.checkCollision();
        update();
        //sendToSocket(cc);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render();
        //axeEnemy.update();
        //currentPlayer.update();
        //currentPlayer.animatePlayer(game.getBatch());
        sB.begin();
        //currentPlayer.render(sB);
        //axeEnemy.render(game.getBatch());
        axeEnemy.drawEnemyAnimation(game.getBatch());
        currentPlayer.drawPlayer(game.getBatch());
        currentPlayer.drawPlayerAnimation(game.getBatch());
        if(partner != null){
            partner.drawPlayer(game.getBatch());
            partner.drawPlayerAnimation(game.getBatch());

        }
        //System.out.println("num: "+num);
        sB.end();
        if(isMultiplayer){
            if(isAttacking){
                System.out.println("trows");
                b.weaponAnimation.setPosition(Gdx.graphics.getWidth()-200, 600);
                b.weaponAnimation.updateWeaponPosition();
                b.weaponAnimation.moveWeaponAnimation((-1) * delta);
            }
        }
        //DISPLAY back all buttons from gameScreen
        game.getBatch().setProjectionMatrix(gameScreenButton.getStage().getCamera().combined);
        gameScreenButton.getStage().draw();


        //Display player
        currentPlayer.updatePositionPlayer();
        if(partner != null){
            partner.updatePositionPlayer();
        }
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);

        if (singleplayerBattle.getIscollide()) {
            currentPlayer.stopWalking();
            if(isMultiplayer){
                b = new BattleScreen(game,currentPlayer,partner, axeEnemy,cc);
                game.setScreen(b);
            }else{
                game.setScreen( new BattleScreen(game,currentPlayer, axeEnemy));
            }

        }
    }

        /**
         * Position game actors in the game stage
         */
        void positionElements(){
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/unbom.ttf"));

//        FreeTypeFontGenerator.FreeTypeFontParameter header_parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
//        header_parameter.size = 50;
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = 30;

            BitmapFont scoreFont = generator.generateFont(parameter);
            BitmapFont coinFont = generator.generateFont(parameter);
            Random r = new Random();
            int low2 = 5000;
            int high2 = 9000;
            int score = r.nextInt(high2 - low2) + low2;
            currentPlayer.updateScore(score);
            score_label = new Label("Score: " + currentPlayer.getCurrentScore(), new Label.LabelStyle(scoreFont, Color.WHITE));
            coin_label = new Label("Coins: " + currentPlayer.getCurrentCoins(), new Label.LabelStyle(coinFont, Color.WHITE));
//        score_label.setPosition(grid.mapWidth+5, grid.mapHeight-50);
//        coin_label.setPosition(grid.mapWidth+150, grid.mapHeight-50);
            score_label.setPosition(Gdx.graphics.getWidth() - 280, Gdx.graphics.getHeight() - 60);
            coin_label.setPosition(Gdx.graphics.getWidth() - 280, Gdx.graphics.getHeight() - 100);


            gameScreenButton.getStage().addActor(score_label);
            gameScreenButton.getStage().addActor(coin_label);

        }

    void positionSocketElements(){

        msg_button = new ImageButton(new SpriteDrawable(new Sprite( new Texture("msg_button.png"))));
        msg_button.setPosition(Gdx.graphics.getWidth() - 400, Gdx.graphics.getHeight() - 130);
        msg_button.setSize(100,100);

        gameScreenButton.getStage().addActor(msg_button);
        msg_button.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("current Screen: "+game.getScreen().toString());
                chat = new ChattingSystem(game, currentPlayer, cc, (GameScreen) game.getScreen());
                chat.chattingSystem();
                game.setScreen(chat);
                //System.out.println("hi from msg button");
            }
        });

    }

        /**
         * Handles game keyboard input
         */
        void handleInput () {
            gameScreenButton.right_button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                   if(isMultiplayer){
                       String msg = "{\"x\":\""+currentPlayer.getPlayerX()+"\",\"y\":\""+currentPlayer.getPlayerY()+"\",\"isRight\":\""+true+"\",\"isStop\":\""+false+"\",\"isUp\":\""+false+"\",\"isDown\":\""+false+"\",\"isLeft\":\""+false+"\"}";
                       //partner.setDirection(Player.Direction.RIGHT);
                       sendToSocket(cc,msg);
                       String msg2 = "character: "+currentPlayer.getCharacter();
                       sendToSocket(cc, msg2);
                       String msg3 = "username: "+currentPlayer.getUsername();
                       sendToSocket(cc, msg3);
                   }
                    currentPlayer.movePlayer(50f, axeEnemy);
                }

                ;
            });
            gameScreenButton.left_button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(isMultiplayer){
                        //String msg = "{\"x\":\""+currentPlayer.getPlayerX()+"\",\"y\":\""+currentPlayer.getPlayerY()+"\",\"isLeft\":\""+true+"\"}";
                        String msg = "{\"x\":\""+currentPlayer.getPlayerX()+"\",\"y\":\""+currentPlayer.getPlayerY()+"\",\"isRight\":\""+false+"\",\"isStop\":\""+false+"\",\"isUp\":\""+false+"\",\"isDown\":\""+false+"\",\"isLeft\":\""+true+"\"}";
                        //partner.setDirection(Player.Direction.LEFT);
                        sendToSocket(cc, msg);
                        String msg2 = "character: "+currentPlayer.getCharacter();
                        sendToSocket(cc, msg2);
                        String msg3 = "username: "+currentPlayer.getUsername();
                        sendToSocket(cc, msg3);
                    }
                    currentPlayer.movePlayer(-50f, axeEnemy);
                }

                ;
            });
            gameScreenButton.up_button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(isMultiplayer){
                        String msg = "{\"x\":\""+currentPlayer.getPlayerX()+"\",\"y\":\""+currentPlayer.getPlayerY()+"\",\"isRight\":\""+false+"\",\"isStop\":\""+false+"\",\"isUp\":\""+true+"\",\"isDown\":\""+false+"\",\"isLeft\":\""+false+"\"}";
                        //partner.setDirection(Player.Direction.UP);
                        sendToSocket(cc, msg);
                        String msg2 = "character: "+currentPlayer.getCharacter();
                        sendToSocket(cc, msg2);
                        String msg3 = "username: "+currentPlayer.getUsername();
                        sendToSocket(cc, msg3);
                    }
                    currentPlayer.movePlayerOnY(50f, axeEnemy);
                }

                ;
            });
            gameScreenButton.down_button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                   if(isMultiplayer){
                       String msg = "{\"x\":\""+currentPlayer.getPlayerX()+"\",\"y\":\""+currentPlayer.getPlayerY()+"\",\"isRight\":\""+false+"\",\"isStop\":\""+false+"\",\"isUp\":\""+false+"\",\"isDown\":\""+true+"\",\"isLeft\":\""+false+"\"}";
                       //partner.setDirection(Player.Direction.DOWN);
                       sendToSocket(cc, msg);
                       String msg2 = "character: "+currentPlayer.getCharacter();
                       sendToSocket(cc, msg2);
                       String msg3 = "username: "+currentPlayer.getUsername();
                       sendToSocket(cc, msg3);
                   }
                    currentPlayer.movePlayerOnY(-50f, axeEnemy);
                }

                ;
            });
            gameScreenButton.stop_button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(isMultiplayer){
                        String msg = "{\"x\":\""+currentPlayer.getPlayerX()+"\",\"y\":\""+currentPlayer.getPlayerY()+"\",\"isRight\":\""+false+"\",\"isStop\":\""+true+"\",\"isUp\":\""+false+"\",\"isDown\":\""+false+"\",\"isLeft\":\""+false+"\"}";
                        sendToSocket(cc, msg);
                        String msg2 = "character: "+currentPlayer.getCharacter();
                        sendToSocket(cc, msg2);
                        String msg3 = "username: "+currentPlayer.getUsername();
                        sendToSocket(cc, msg3);
                    }

                    currentPlayer.stopWalking();
                }

                ;
            });
        }

        /**
         * Updates current player position on map
         */
        void update () {
            handleInput();
        }
        void handleCollision () {
            singleplayerBattle.checkCollision();
            if (singleplayerBattle.getIscollide()) {
                currentPlayer.stopWalking();
                gameScreenButton.createAndPositionDialog();
                gameScreenButton.Yes.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        game.setScreen(new Leaderboard(game, currentPlayer));
                    }
                });

                gameScreenButton.No.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        currentPlayer.movePlayerOnY(50f, axeEnemy);
                        gameScreenButton.getStage().getActors().removeValue(gameScreenButton.battle_dialog, true);
                        gameScreenButton.getStage().getActors().removeValue(gameScreenButton.Yes, true);
                        gameScreenButton.getStage().getActors().removeValue(gameScreenButton.No, true);
                    }
                });
//            if(!i) {
//                System.out.println("i: " + gameScreenButton.getStage().getActors().removeValue(gameScreenButton.battle_dialog, true));
//                gameScreenButton.getStage().getActors().removeValue(gameScreenButton.battle_dialog, true);
//            }
//            singleplayerBattle.setIscollide(false);
//            System.out.println("isCollide: " + singleplayerBattle.getIscollide());


            } else {
                System.out.println("Fasle isCollide: " + singleplayerBattle.getIscollide());
                gameScreenButton.getStage().getActors().removeValue(gameScreenButton.battle_dialog, true);
            }
        }

        @Override
        public void resize(int width, int height) {
        }
        @Override
        public void pause () {
        }

        @Override
        public void resume () {
        }

        @Override
        public void hide () {
        }

        /**
         * Dispose all the objects that are disposable
         */
        @Override
        public void dispose () {
            gameScreenButton.getStage().dispose();
            img.dispose();
            game.getBatch().dispose();
            map.dispose();
            dialogCollision.getStage().dispose();

        }

}