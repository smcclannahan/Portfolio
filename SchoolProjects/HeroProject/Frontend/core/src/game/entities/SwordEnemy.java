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

public class SwordEnemy extends EntitySuper{

    public Vector2 position, dimensions;
    public Sprite sprite;

    public String name;

    public int atkDmg;
    public int health;
    public int defense;

    /**
     * Constructor for SwordEnemy
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
    public SwordEnemy(World world, int x, int y, float width, float height, String name, int dmg, int hp, int def){

        super(world,
                new Texture("testEnemy.png"),
                new TextureAtlas("Player/characters/p1/right_finished/p1_right_animation.atlas"),
                x,y,width,height,name, dmg, hp, def);

        sprite = new Sprite(new Texture("testEnemy.png")); //replace with actual sprite
        sprite.setSize(dimensions.x, dimensions.y);
    }
    /**
     * Updates the position of the sprite for the enemy
     */
    @Override
    public void update() {
        sprite.setPosition(position.x, position.y);
        //sprite.setPosition(rand.nextFloat(), rand.nextFloat());
    }


    /**
     * renders the sprite of the enemy
     */
    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }


    /**
     * Gets the name of the enemy
     *
     * @return name The name of the enemy
     */
    public String getName() {
        return name;
    }


    /**
     * Moves around the enemy
     *
     * @param range Distance that the enemy is allowed to move
     */
    public void move(int range) {
        super.move(range);
    }


    /**
     * Gets the attack of the enemy
     *
     * @return int of Attack of the current enemy
     */
    public int attack() {
        return super.attack();
    }




    /**
     * Gets the defense of the enemy
     *
     * @return int of Defense of the current enemy
     */
    public int getDefense() {
        return super.getDefense();
    }


    /**
     * Removes the current enemy when health is 0
     */
    public void defeat() {
        if (health == 0) {
        }
    }


}
