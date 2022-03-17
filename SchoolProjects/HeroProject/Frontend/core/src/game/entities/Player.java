
package game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;

import game.Weapons.Axe;
import game.Weapons.Bow;
import game.Weapons.Lance;
import game.Weapons.Sword;
import game.characters.Player_character;
import game.Weapons.Weapon;


public class Player extends Sprite {

    private World world;
    private Body body;


    public Vector2 position, dimensions, touchpoint;
    public Sprite sprite;

    public String userName;
    private String passWord;

    private float current_player_x;
    private float current_player_y;

    public OrthographicCamera cam;
    public int player_current_score;
    private int player_current_coins;

    public int baseAtk;
    public ArrayList<Weapon> weapons;

    private TextureAtlas playerAtlas;
    private TextureAtlas playerAtlas2;
    private Animation animation;
    private float elapsedTime=0f;
    public enum Direction
    {
        UP, RIGHT, DOWN, LEFT;
    }
    public Direction direction;


    private boolean isWalking;
    private int hp;
    private int dmg;
    private int def;
    private String textureStr;

    public boolean swordEq = true;
    public boolean axeEq;
    public boolean lanceEq;
    public boolean bowEq;

    public Sword sword;
    public Axe axe;
    public Lance lance;
    public Bow bow;

    String character;

    /**
     * Player constructor created to
     * facilitate user login
     * @param username
     * Player username
     * @param password
     * Player password
     */
    public Player(String username, String password, String character){
        //super(username,password);
        userName = username;
        passWord = password;
        this.character =character;
        textureStr="Player/characters/"+character+"/down/tile.png";
        weapons  = new ArrayList<>(); // for testing only
        //setIsLoggedIn(false);
    }

    /**
     * Player constructor for rendering and setting start postion
     * @param x - starting x position on map
     * @param y - starting y position on map
     * @param width - size of player sprite
     * @param height - size of player sprite
     * @param dmg - base attack stat
     * @param hp - base hit points
     * @param def - base defense
     */

    public Player(World world, String username,String character, int x, int y, float width, float height, int dmg, int hp, int def){
        super(new Texture("Player/characters/"+character+"/down/tile.png"));
        this.world = world;
        this.character =character;
        setPosition(x, y);
        createBody();
        //playerAtlas = new TextureAtlas("Player/characters/p1/down_finished/p1_down_animation.atlas");
        this.hp = hp;
        this.dmg = dmg;
        baseAtk = dmg;
        this.def = def;


        userName = username;
        current_player_x = x;
        current_player_y =y;
        player_current_score = 0;
        player_current_coins=0;
        position = new Vector2(x, y);
        dimensions = new Vector2(width, height);
        touchpoint = new Vector2(x, y);

        //System.out.println("X: "+x +" Y: "+ y);
        weapons  = new ArrayList<>(6);
        //sprite = new Sprite(new Texture("Player/characters/p1/down/tile000.png"));
        //sprite.setSize(150, 150);

        sword = new Sword(5,100,1,200,"sword");
        axe = new Axe(6,100,1,200,"axe");
        lance = new Lance(4,100,1,200,"lance");
        bow = new Bow(5,100,1,200,"bow");

        //sprite.setOrigin(position.x,position.y);//set's the players starting point


        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        //createAnimation();
    }
    //public boolean getIsLoggedIn(){
    //   return isLoggedIn;
    //}
    //public void setIsLoggedIn(boolean isLoggedIn){
    //    this.isLoggedIn=isLoggedIn;
    //}

    /**
     * sets body for player, and adds physics for player
     */
    void createBody(){
        BodyDef body_def = new BodyDef();
        body_def.type = BodyDef.BodyType.KinematicBody;
        body_def.position.set(getX(), getY());
        body = world.createBody(body_def);
        body.setFixedRotation(true);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth()/2f, getHeight()/2f);
        //shape.setRadius(10);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 0;
        fixtureDef.friction = 0;
        //fixtureDef.restitution = 0;
        fixtureDef.shape = shape;
        //System.out.println("Shape: "+fixtureDef.shape);

        Fixture fixture = body.createFixture(fixtureDef);
        shape.dispose();

    }

    /**
     * Moves or sets the player
     */
    public void updatePositionPlayer(){
        setPosition(body.getPosition().x,body.getPosition().y);

    }

    /**
     * creates the player sprite, and changes sprite with direction
     * @param batch - set of sprites
     */

    /**
     * creates the player sprite, and changes sprite with direction
     * @param batch - set of sprites
     */
    public void drawPlayer(SpriteBatch batch){
        if(isWalking==false) {
            batch.begin();
//            System.out.println("getX(): "+getX());
//            System.out.println("getY(): "+getY());
            //setT
            if(direction == Direction.UP){
                setTexture(new Texture("Player/characters/"+getCharacter()+"/up/tile.png"));
            }
            if(direction == Direction.DOWN){
                setTexture(new Texture("Player/characters/"+getCharacter()+"/down/tile.png"));
            }
            if(direction == Direction.LEFT){
                setTexture(new Texture("Player/characters/"+getCharacter()+"/left/tile.png"));
            }
            if(direction == Direction.RIGHT){
                setTexture(new Texture("Player/characters/"+getCharacter()+"/right/tile.png"));
            }
            //setTexture(new Texture("Player/characters/p1/left/tile012.png"));
            batch.draw(this, getX(), getY());
            batch.end();
        }
    }
    public void setDirection(Direction direction){
        this.direction = direction;
    }

    /**
     * sets the player animation based on the direction chosen using direction variable, assuming
     * player is walking.
     * @param batch
     */
    public void drawPlayerAnimation(SpriteBatch batch){
        //System.out.println("isWalking "+isWalking);
        if(isWalking) {
            //System.out.println("Should be animating");
            elapsedTime += Gdx.graphics.getDeltaTime();
            //Array<TextureAtlas.AtlasRegion> frames = playerAtlas.getRegions();
            //left
            if(body.getLinearVelocity().x<0 ){
                //System.out.print("Left");
                direction = Direction.LEFT;
                playerAtlas2 = new TextureAtlas("Player/characters/"+getCharacter()+"/left_finished/"+getCharacter()+"_left_animation.atlas");
                animation = new Animation(1f/10f, playerAtlas2.getRegions());
            }
            //right
            if(body.getLinearVelocity().x>0){
                direction = Direction.RIGHT;
                playerAtlas2 = new TextureAtlas("Player/characters/"+getCharacter()+"/right_finished/"+getCharacter()+"_right_animation.atlas");
                animation = new Animation(1f/10f, playerAtlas2.getRegions());
            }
            //up
            if(body.getLinearVelocity().y>0){
                direction = Direction.UP;
                playerAtlas = new TextureAtlas("Player/characters/"+getCharacter()+"/up_finished/"+getCharacter()+"_up_animation.atlas");
                animation = new Animation(1f/10f, playerAtlas.getRegions());
            }
            //down
            if(body.getLinearVelocity().y<0){
                direction = Direction.DOWN;
                playerAtlas = new TextureAtlas("Player/characters/"+getCharacter()+"/down_finished/"+getCharacter()+"_down_animation.atlas");
                animation = new Animation(1f/10f, playerAtlas.getRegions());
            }

            batch.begin();
            //System.out.println("getX(): "+getX());
            //System.out.println("getY(): "+getY());
            TextureRegion region = (TextureRegion) animation.getKeyFrame(elapsedTime, true);
            batch.draw(region, getX(), getY());
            batch.end();
        }
    }

    /**
     * sets the player animation based on the direction chosen using direction variable, assuming
     * player is walking.
     * @param batch
     */

    public void drawPlayerAnimationForBattle(SpriteBatch batch){
        elapsedTime += Gdx.graphics.getDeltaTime();
        //System.out.print("Left");
        direction = Direction.LEFT;
        playerAtlas2 = new TextureAtlas("Player/characters/"+getCharacter()+"/left_finished/"+getCharacter()+"_left_animation.atlas");
        animation = new Animation(1f/10f, playerAtlas2.getRegions());

        batch.begin();
        //System.out.println("getX(): "+getX());
        //System.out.println("getY(): "+getY());
        TextureRegion region = (TextureRegion) animation.getKeyFrame(elapsedTime, true);
        batch.draw(region, getX(), getY());
        batch.end();
    }


    /**
     * Moves the player on the X-axis
     * @param x - how fast the player moves.
     */
    public void movePlayer(float x, EntitySuper enemy){
        setIsWalking(true);
        body.setLinearVelocity(x, 0);

    }

    /**
     * moves player on the Y-axis
     * @param y - how fast the player moves
     */
    public void movePlayerOnY(float y, EntitySuper enemy){
        setIsWalking(true);
        body.setLinearVelocity(0, y);
    }

    /**
     * Stops the player
     */
    public void stopWalking(){
        setIsWalking(false);
//        System.out.println("getX(): "+getPlayerX());
//        System.out.println("getY(): "+getPlayerY());
        body.setLinearVelocity(0, 0);
    }

    public float getPlayerX(){
        return getX();
    }

    public float getPlayerY(){
        return getY();
    }

    //@Override
    public void update(){
        int player_x_position = Gdx.input.getX();
        int player_y_position = Gdx.graphics.getHeight()-Gdx.input.getY();
        //int p = Math.abs(this.current_player_x-player_x_position);
        //int p2 = Math.abs(this.current_player_y-player_y_position);

        //sprite = new Sprite(new Texture("Player/characters/p1/down/tile001.png"));
//            System.out.print("y: "+p2+" Left: ");
//            System.out.println(p2>100);
//            if(p>100 || p2>100){
//                //System.out.println("X: " + player_x_position + " Y: " + player_y_position);
//                touchpoint.set(player_x_position, player_y_position);
//                sprite.setPosition(player_x_position, player_y_position);
//                position.set(player_x_position, player_y_position);
//                sprite.setPosition(position.x, position.y);
//                current_player_x = player_x_position;
//                current_player_y = player_y_position;
//            }
    }


    /**
     * renders player on grid
     * @param batch
     */
    public void render(SpriteBatch batch){
//        batch.begin();
//        batch.draw(batch);
//        batch.end();
        batch.begin();
        batch.draw(this, getPlayerX(), getPlayerY());
        batch.end();
    }

    /**
     *  method to get the next tile ahead of the one the player is currently on
     * @param currentTile - the tile the player is currently on.
     * @return
     */
    public TiledMapTile getNextTile(TiledMapTile currentTile){
        if(touchpoint.x > position.x){
            return currentTile;
        }
        return null;
    }


//    @Override
//    public int attack() {
//        return super.attack();
//    }
//
//    public int getHealth() {
//        return super.getHealth();
//    }
//
//    public int getDefense() {
//        return super.getDefense();
//    }

    public int getHp() {
        return hp;
    }
    public void setHp(int attack) {
        hp=hp-attack;
    }

    public int getDmg() {
        return dmg;
    }
    public void setDmg(int dmg) {
        this.dmg=this.dmg+dmg;
    }

    public int getDef() {
        return this.def;
    }
    public void setDef(int def) {
        this.hp=this.hp+def;
    }

    /**
     * Gets the player's current score
     * @return player's current score
     */
    public int getCurrentScore(){
        return player_current_score;
    }
    /**
     * updates the player's score
     * @param points - how many points to increase the player's points by.
     */

    /**
     * updates the player's score
     * @param points - how many points to increase the player's points by.
     */
    public void updateScore(int points){
        player_current_score += points;
    }
    /**
     * Gets the plyaer's current amount of money
     * @return player's current coins
     */

    /**
     * Gets the plyaer's current amount of money
     * @return player's current coins
     */
    public int getCurrentCoins(){
        return player_current_coins;
    }
    /**
     * Updates the amount of coins the player has
     * @param coins - how many coins to add to the player's current total
     */

    /**
     * Updates the amount of coins the player has
     * @param coins - how many coins to add to the player's current total
     */
    public void updateCoins(int coins){
        player_current_coins += coins;
    }
    public void subtractCoins(int coins) {player_current_coins -= coins;}

    public void setCoins(int coins){ player_current_coins = coins;}

    /**
     * gets player's username
     * @return username
     */
    public String getUsername(){ return userName; }
    /**
     * gets player's password
     * @return password
     */
    public String getPassword(){ return passWord; }

    /**
     * sets the isWalking variable to true or false
     * @param isWalking - whether the player is walking or not.
     */
    public void setIsWalking(boolean isWalking){
        this.isWalking = isWalking;
    }
    public void addWeapon(Weapon weapon){
        weapons.add(weapon);
    }
    public ArrayList<Weapon> getWeapons(){
        return weapons;
    }

    public void dropWeapon(int index){
        weapons.remove(index);
    }

    public Weapon getWeapon(int index){
        for(int i = 0;i<weapons.size();++i) {
            if(i == index) {
                return weapons.get(index);
            }
        }
        return null;
    }

    public void setCurrentCharacter(String character){
        this.character = character;
        //this.textureStr = "Player/characters/"+this.character+"/down/tile.png";

    }

    public String getCharacter(){
        return this.character;
    }

    public void equipWeapon(String Weapon){

        if(Weapon.equals("sword")){
            swordEq = true;
            axeEq = false;
            lanceEq = false;
            bowEq = false;

            sword.equip();
            dmg = baseAtk + sword.getPower();
        }
        else if(Weapon.equals("axe")){
            swordEq = false;
            axeEq = true;
            lanceEq = false;
            bowEq = false;

            axe.equip();
            dmg = baseAtk + axe.getPower();

        }
        else if(Weapon.equals("lance")){
            swordEq = false;
            axeEq = false;
            lanceEq = true;
            bowEq = false;

            lance.equip();
            dmg = baseAtk + lance.getPower();
        }
        else if(Weapon.equals("bow")){
            swordEq = false;
            axeEq = false;
            lanceEq = false;
            bowEq = true;

            bow.equip();
            dmg = baseAtk + bow.getPower();
        }
        else{//defaults to sword equip
            swordEq = true;
            axeEq = false;
            lanceEq = false;
            bowEq = false;

            sword.equip();
            dmg = baseAtk + sword.getPower();
        }

    }
    public String getEquippedWeapon(){
        if(swordEq == true){
            return "sword";
        }
        else if(axeEq == true){
            return "axe";
        }
        else if(lanceEq == true){
            return "lance";
        }
        else if(bowEq == true){
            return "bow";
        }
        else{
            return "error";
        }
    }



}


