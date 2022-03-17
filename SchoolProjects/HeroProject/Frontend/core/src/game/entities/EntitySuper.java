package game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

public class EntitySuper extends Sprite {

    public Vector2 position, dimensions;

    public String name;

    private int atkDmg;
    private int defense;
    public OrthographicCamera cam;

    private String userName;
    private String passWord;
    private int hp;
    private World world;
    private Animation animation;
    private boolean isWalking;
    private float elapsedTime=0f;
    private Body body;
    private TextureAtlas playerAtlas;

    public EntitySuper(String username, String password){
        userName = username;
        passWord = password;
    }

    /**
     * Constructor for Enemy classes
     * @param x The x location for the sprite to be placed
     * @param y The y location for the sprite to be placed
     * @param width How wide in pixels the image should be
     * @param height How high in pixels the image should be
     * @param aName Name of the entity
     * @param dmg Damage stat value for Enemy
     * @param hp Health stat value for Enemy
     * @param def Defense stat value for Enemy
     */
    public EntitySuper(World world, Texture texture,TextureAtlas playerAtlas, int x, int y, float width, float height, String aName, int dmg, int hp, int def){
        super(texture);
        this.world = world;
        setPosition(x, y);
        createBody();
        this.playerAtlas =playerAtlas;

        atkDmg = dmg;
        defense = def;
        this.hp = hp;

        name =  aName;

        position = new Vector2(x,y);
        dimensions = new Vector2(width, height);

        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

    }

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
    public void updatePositionEnemy(){
        setPosition(body.getPosition().x,body.getPosition().y);

    }
    /**
     * sets the player animation based on the direction chosen using direction variable, assuming
     * player is walking.
     * @param batch
     */
    public void drawEnemyAnimation(SpriteBatch batch){
        //System.out.println("isWalking "+isWalking);
        if(!isWalking) {
            elapsedTime += Gdx.graphics.getDeltaTime();
            //playerAtlas = new TextureAtlas("Player/characters/p1/right_finished/p1_right_animation.atlas");
            animation = new Animation(1f/10f, playerAtlas.getRegions());

            batch.begin();
            //System.out.println("getX(): "+getX());
            //System.out.println("getY(): "+getY());
            TextureRegion region = (TextureRegion) animation.getKeyFrame(elapsedTime, true);
            batch.draw(region, getX(), getY());
            batch.end();
        }
    }

    public void update(){
        setPosition(position.x, position.y);
    }

    public void render(SpriteBatch batch){
        batch.begin();
        batch.draw(this, position.x, position.y);
        batch.end();
    }
    public void setEnemyPosition(int x, int y){
//        position.x =x;
//        position.y = y;
        setPosition(x, y);
    }

    public String getName() {
        return name;
    }

    /**
     * Moves around the entity
     * @param moveRange Distance that the entity is allowed to move in one turn
     *
     */
    public void move(int moveRange) {
        Random rand = new Random();
        int direction = rand.nextInt(4);

        if(direction == 0) {            //moves up
            position.set(position.x, position.y + 5);
        }
        else if (direction == 1) {      //moves right
            position.set(position.x+5, position.y);
        }
        else if (direction == 2) {      //moves down
            position.set(position.x, position.y-5);
        }
        else {                            //moves left
            position.set(position.x-5, position.y);
        }




        /*for (int i = 0; i < moveRange; i++) {
            position.set(Gdx.getX()+1, Gdx.getY());
            sprite.setPosition(position.x+1, position.y);
        }
        for (int i = 0; i < moveRange; i++) {
            position.set(Gdx.getX()-1, Gdx.getY());
            sprite.setPosition(position.x-1, position.y);
        }*/
    }

    public int attack() {
        return atkDmg;
    }
    public int getHp() {
        return hp;
    }
    public void setHp(int attack) {
        this.hp= this.hp-attack;
    }

    public int getDmg() {
        return atkDmg;
    }
    public void setDmg(int dmg) {
        this.atkDmg= this.atkDmg+dmg;
    }

    public int getDefense() {
        return defense;
    }

    public float getEnemyX(){
        return getX();
    }

    public float getEnemyY(){
        return getY();
    }

    /**
     * Gets the username of the player
     * @return string of the username
     *
     */
    public String getUsername(){ return userName; }
    private String getPassword(){ return passWord; }

}
