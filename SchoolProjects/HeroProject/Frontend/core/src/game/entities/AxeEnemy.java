package game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

import static com.badlogic.gdx.math.MathUtils.random;

public class AxeEnemy extends EntitySuper {

    public Vector2 position, dimensions;
    public Sprite sprite;

    public String name;

    public int atkDmg;
    public int health;
    public int defense;
    //private World world;
    Random rand = new Random(100);

    /**
     * Constructor for AxeEnemy
     *
     * @param x      The x location for the sprite to be placed
     * @param y      The y location for the sprite to be placed
     * @param width  How wide in pixels the image should be
     * @param height How high in pixels the image should be
     * @param name   Name of the entity
     * @param dmg    Damage stat value for Enemy
     * @param hp     Health stat value for Enemy
     * @param def    Defense stat value for Enemy
     */
    public AxeEnemy(World world, int x, int y, float width, float height, String name, int dmg, int hp, int def){
        super(world,new Texture("testEnemy.png"),
                new TextureAtlas("Player/characters/e1/right_finished/e1_right_animation.atlas"),
                x,y,width,height,name, dmg, hp, def);

        //sprite = new Sprite(new Texture("testEnemy.png")); //replace with actual sprite
        position = new Vector2(x, y);
        dimensions = new Vector2(width, height);
        //sprite.setSize(dimensions.x, dimensions.y);

       // sprite.setSize(dimensions.x, dimensions.y);
    }
    @Override
    public void update(){
        super.update();
        //sprite.setPosition(position.x, position.y);
        //sprite.setPosition(rand.nextFloat(), rand.nextFloat());
    }
    public void render(SpriteBatch batch) {
        //batch.begin();
        //sprite.draw(batch);
        super.render(batch);
        //  batch.end();
    }

    public void drawEnemyAnimation(SpriteBatch batch){
      super.drawEnemyAnimation(batch);
    }
//    public void update() {
//        sprite.setPosition(position.x, position.y);
//        sprite.setPosition(rand.nextFloat(), rand.nextFloat());
//    }


//    /**
//     * renders the sprite of the enemy
//     */
//    public void render(SpriteBatch batch) {
//        sprite.draw(batch);
//    }


    /**
     * Gets the name of the enemy
     *
     * @return name The name of the enemy
     */
    public String getName() {
        return super.getName();
    }
    public int getHp(){
        return super.getHp();
    }
    public void setHp(int attack){
        super.setHp(attack);
    }
    public int getDmg() {
        return super.getDmg();
    }

    public void setDmg(int dmg) {
        super.setDmg(dmg);
    }

    public void move(int range) {
        super.move(range);
    }

    public int attack() {
        return super.attack();
    }


    public int getDefense() {
        return super.getDefense();
    }

    public void defeat(){
        if(health == 0){
            //figure out how to unrender
        }
    }


}
