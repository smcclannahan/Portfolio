package game.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

import org.java_websocket.client.WebSocketClient;

import buttons.BattleScreenButtons;
import game.Weapons.Axe;
import game.Weapons.Weapon;
import game.Weapons.WeaponAnimation;
import game.entities.AxeEnemy;
import game.entities.Player;

public class BattleScreen implements Screen {
    private MyGdxGame game;
    private OrthographicCamera mainCamera;
    private Viewport gameViewport;

    private Texture background;
    public SpriteBatch sB;

    public Player current_player, partner;
    public AxeEnemy enemy;
    WebSocketClient cc;
    public BattleScreenButtons buttons;
    ProgressBar bar;
    ProgressBar enemy_bar;
    int hp_max;
    public Label game_status;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont font;
    public WeaponAnimation weaponAnimation;
    private Weapon weapon;
    ImageButton equip_button;
    private boolean isPlayerWon;
    public BattleScreen b;
    private boolean isMultiplayer =false;
    //Lobby Screentest

    /**
     * MultiplayerScreen constructor
     *
     * @param game           Represents the current game session
     * @param current_player Represents the player playing the game
     */
    public BattleScreen(MyGdxGame game, Player current_player, AxeEnemy axeEnemy) {

        this.game = game;
        this.enemy = axeEnemy;
        this.current_player = current_player;
        //b = new BattleScreen(game,current_player,enemy);
        this.current_player.setPosition(Gdx.graphics.getWidth() - 200, 400);
        sB = new SpriteBatch();
        background = new Texture("BattleScreen/level1.jpg");

        mainCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        mainCamera.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0);

        gameViewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
                mainCamera);
        this.current_player = current_player;
        buttons = new BattleScreenButtons(game, this.current_player, this.enemy);
        hp_max = 100;
        bar = new ProgressBar(0, hp_max, 10, false, buttons.mySkin);
        bar.setPosition(Gdx.graphics.getWidth() - 80 - 330, Gdx.graphics.getHeight() / 4f - 150);
        bar.setSize(290, bar.getPrefHeight());
        buttons.getStage().addActor(bar);

        enemy_bar = new ProgressBar(0, hp_max, 10, false, buttons.mySkin);
        enemy_bar.setPosition(450, Gdx.graphics.getHeight() / 4f - 150);
        enemy_bar.setSize(290, bar.getPrefHeight());
        buttons.getStage().addActor(enemy_bar);
        buttonsListeners();
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/unbom.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        font = generator.generateFont(parameter);
        game_status = new Label(enemy.getName().toUpperCase() + " VS "
                + current_player.getUsername().toUpperCase(), new Label.LabelStyle(font, Color.RED));
        //addMessagingListeners();

        //TODO - Add some ifs to make the weapon generate into whatever'
        this.setPlayerWeapon(current_player);
    }

    /**
     * MultiplayerScreen constructor
     * @param game
     *  Represents the current game session
     * @param current_player
     * Represents the player playing the game
     */
    public BattleScreen(MyGdxGame game, Player current_player,Player partner, AxeEnemy axeEnemy, WebSocketClient cc){
        this.game = game;
        this.enemy=axeEnemy;
        this.current_player =current_player;
        this.partner = partner;
        this.cc = cc;
        this.current_player.setPosition(Gdx.graphics.getWidth()-200, 400);
        this.partner.setPosition(Gdx.graphics.getWidth()-200, 600);
        isMultiplayer = true;
        sB = new SpriteBatch();
        background = new Texture("BattleScreen/level1.jpg");

        mainCamera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        mainCamera.position.set(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f,0);

        gameViewport =  new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
                mainCamera);
        this.current_player=current_player;
        buttons = new BattleScreenButtons(game, this.current_player, this.enemy);
        buttons.username_label.setText(current_player.getUsername().toUpperCase()+" & "+partner.getUsername().toUpperCase());
        buttons.username_label.setPosition(Gdx.graphics.getWidth()-400,Gdx.graphics.getHeight()/4f-80);
        hp_max=100;
        bar = new ProgressBar(0,hp_max , 10, false, buttons.mySkin);
        bar.setPosition(Gdx.graphics.getWidth()-80-400, Gdx.graphics.getHeight()/4f-150);
        bar.setSize(290, bar.getPrefHeight());
        buttons.getStage().addActor(bar);

        enemy_bar = new ProgressBar(0,100 , 10, false, buttons.mySkin);
        enemy_bar.setPosition(450, Gdx.graphics.getHeight()/4f-150);
        enemy_bar.setSize(290, bar.getPrefHeight());
        buttons.getStage().addActor(enemy_bar);
        buttonsListeners();
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/unbom.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        font = generator.generateFont(parameter);
        game_status = new Label(enemy.getName().toUpperCase()+" VS "
                +this.current_player.getUsername().toUpperCase()+" & "+this.partner.getUsername().toUpperCase(), new Label.LabelStyle(font, Color.RED));
        //addMessagingListeners();
        weapon = new Axe(10, 10, 10, 100, "axe weapon");
        //current_player.addWeapon(weapon);
        //weaponAnimation = new WeaponAnimation(game, current_player, current_player.getWeapon(0),"axe");
        //positionElements();
        this.setPlayerWeapon(current_player);
    }

    @Override
    public void show() {
        game_status.setPosition(Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() - 200);
        game_status.setFontScale(2, 2);
        buttons.getStage().addActor(game_status);
        //weaponAnimation.setPosition(500, 500);

    }

    void positionElements() {

        equip_button = new ImageButton(new SpriteDrawable(new Sprite(new Texture("EquipButton.png"))));
        equip_button.setPosition(Gdx.graphics.getWidth() - 400, Gdx.graphics.getHeight() - 130);
        equip_button.setSize(100, 100);

        buttons.getStage().addActor(equip_button);
        equip_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new WeaponScreen(game, current_player, BattleScreen.this));
                //System.out.println("hi from msg button");
            }
        });

    }

    boolean isAttacking = false;

    /**
     * Renders the players and multi player actors
     *
     * @param delta delta time variable
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            if(!isGameOver()) {
                if (isAttacking) {
                    weaponAnimation.updateWeaponPosition();
                    weaponAnimation.moveWeaponAnimation((-1) * delta);
                } else {
                    weaponAnimation.setPosition(current_player.getX(), current_player.getPlayerY());
                }
            }else{
                buttons.attack_button.setTouchable(Touchable.disabled);
                buttons.defend_button.setTouchable(Touchable.disabled);
            }

        game.getBatch().begin();
        game.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //weaponAnimation.render(game.getBatch());
        game.getBatch().end();

        sB.begin();
        weaponAnimation.render(game.getBatch());
        //weaponAnimation.drawPlayerAnimation(game.getBatch());

        sB.end();

        game.getBatch().setProjectionMatrix(buttons.getStage().getCamera().combined);
        buttons.getStage().draw();
        buttons.getStage().act();
        showHPBar();
        showHPEnemyBar();


    }

    public boolean isGameOver() {
        boolean isOver = false;
        if (current_player.getHp() <= 0 || enemy.getHp() <= 0) {
            isOver = true;
            game_status.setFontScale(3, 3);
            if (current_player.getHp() > enemy.getHp()) {
                game_status.setColor(Color.GREEN);
                isPlayerWon = true;
                game_status.setText("GAME OVER! \n"
                        + current_player.getUsername() + " WINS!");
            } else {
                isPlayerWon = false;
                game_status.setColor(Color.RED);
                game_status.setText("GAME OVER! \n"
                        + enemy.getUsername() + " WINS!");
            }
        }
        return isOver;
    }

    public boolean isPlayerWon() {
        return isPlayerWon;
    }
    void showHPBar(){

        if(isMultiplayer){
            current_player.setDirection(Player.Direction.LEFT);
            partner.setDirection(Player.Direction.LEFT);
            current_player.drawPlayerAnimationForBattle(game.getBatch());
            partner.drawPlayerAnimationForBattle(game.getBatch());
            bar.setValue(current_player.getHp());//combine HP
        }else{
            current_player.setDirection(Player.Direction.LEFT);
            current_player.drawPlayerAnimationForBattle(game.getBatch());
            bar.setValue(current_player.getHp());
        }

        //bar.setAnimateDuration(2);

    }

    void showHPEnemyBar() {
        enemy.setEnemyPosition(100, 400);
        //enemy.render(game.getBatch());
        enemy.drawEnemyAnimation(game.getBatch());

        enemy_bar.setValue(enemy.getHp());
        //enemy_bar.setAnimateDuration(2);

    }

    void buttonsListeners() {
        buttons.attack_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isAttacking = true;
                if(isMultiplayer){
                    sendToSocket(cc,"isAttacking");
                }
                buttons.attack_button.setTouchable(Touchable.disabled);
                buttons.defend_button.setTouchable(Touchable.disabled);
                //weaponAnimation.moveWeaponAnimation((-1)*Gdx.graphics.de);
                Timer.schedule(new Timer.Task(){
                    @Override
                    public void run() {
                        isAttacking=false;
                        sendToSocket(cc,"isNotAttacking");
                        if(isMultiplayer){
//                            String json ="{\"dmg\":\""+current_player.getDmg()+"\"}";

                            String json ="attack";
                            enemy.setHp(current_player.getDmg());
                            game_status.setColor(Color.RED);
                            game_status.setText(enemy.getName().toUpperCase()+" turns !");
                            sendToSocket(cc,json);
                        }else{
                            enemy.setHp(current_player.getDmg());
                            game_status.setColor(Color.RED);
                            game_status.setText(enemy.getName().toUpperCase()+" turns !");
                        }
//                        buttons.attack_button.setTouchable(Touchable.disabled);
//                        buttons.defend_button.setTouchable(Touchable.disabled);
                                   }
                               }
                        , 3        //    (delay)
                );
                Timer.schedule(new Timer.Task(){
                    @Override
                    public void run() {
                        if(isMultiplayer){
                            String json ="attacking";
                            current_player.setHp(enemy.getDmg());
                            //buttons.attack_button.setDisabled(false);
                            buttons.attack_button.setTouchable(Touchable.enabled);
                            buttons.defend_button.setTouchable(Touchable.enabled);
                            game_status.setColor(Color.BLUE);
                            game_status.setText(current_player.getUsername().toUpperCase() + " & " + partner.getUsername().toUpperCase() + " turns !");
                            sendToSocket(cc,json);
                        }else {
                            current_player.setHp(enemy.getDmg());
                            //buttons.attack_button.setDisabled(false);
                            buttons.attack_button.setTouchable(Touchable.enabled);
                            buttons.defend_button.setTouchable(Touchable.enabled);
                            game_status.setColor(Color.BLUE);
                            game_status.setText(current_player.getUsername().toUpperCase() + " & " + partner.getUsername().toUpperCase() + " turns !");
                        }
                                }
                            }
                        , 6        //    (delay)
                );
            }
        });
        buttons.defend_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(isMultiplayer){
                    String json ="defend";
                    current_player.setDef(Math.abs(current_player.getDef()-enemy.getDmg()));
                    game_status.setColor(Color.RED);
                    game_status.setText(enemy.getName().toUpperCase()+" turns !");
                    buttons.defend_button.setTouchable(Touchable.disabled);
                    buttons.attack_button.setTouchable(Touchable.disabled);
                    sendToSocket(cc,json);
                }else{
                    current_player.setDef(Math.abs(current_player.getDef()-enemy.getDmg()));
                    game_status.setColor(Color.RED);
                    game_status.setText(enemy.getName().toUpperCase()+" turns !");
                    buttons.defend_button.setTouchable(Touchable.disabled);
                    buttons.attack_button.setTouchable(Touchable.disabled);
                }
                Timer.schedule(new Timer.Task(){
                                   @Override
                                   public void run() {
                                       if(isMultiplayer){
                                           String json = "defending";
                                           game_status.setColor(Color.BLUE);
                                           game_status.setText(partner.getUsername().toUpperCase() + " & " + partner.getUsername().toUpperCase()+" turns !");
                                           sendToSocket(cc,json);
                                       }else{
                                           buttons.defend_button.setTouchable(Touchable.enabled);
                                           buttons.attack_button.setTouchable(Touchable.enabled);
                                       }
                                   }
                               }
                        , 3        //    (delay)
                );
            }
        });

        buttons.escape_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, current_player));
            }
        });
    }

    public void setPlayerWeapon(Player current_player) {
        if (current_player.getEquippedWeapon() == "sword") {
            weaponAnimation = new WeaponAnimation(game, current_player, current_player.sword, "swordSide");
            positionElements();
        } else if (current_player.getEquippedWeapon() == "axe") {
            weaponAnimation = new WeaponAnimation(game, current_player, current_player.axe, "axe");
            positionElements();
        } else if (current_player.getEquippedWeapon() == "lance") {
            weaponAnimation = new WeaponAnimation(game, current_player, current_player.lance, "lanceSide");
            positionElements();
        } else if (current_player.getEquippedWeapon() == "bow") {
            weaponAnimation = new WeaponAnimation(game, current_player, current_player.bow, "arrow");
            positionElements();
        } else {
            weaponAnimation = new WeaponAnimation(game, current_player, current_player.sword, "swordSide");
            positionElements();
        }
    }


    void addMessagingListeners() {
//        buttons.send_button.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                //game.setScreen( new GameScreen(game) );
//
//            }
//
//            ;
//        });
    }

    @Override
    public void resize(int width, int height) {
        buttons.getStage().getViewport().update(width, height, true);
    }

    public AxeEnemy getEnemy() {
        return this.enemy;
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
    void sendToSocket(WebSocketClient s, String json){
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
     * Dispose all the objects that are disposable
     */
    @Override
    public void dispose() {
        background.dispose();
        game.getBatch().dispose();
        buttons.getStage().dispose();
    }
}
