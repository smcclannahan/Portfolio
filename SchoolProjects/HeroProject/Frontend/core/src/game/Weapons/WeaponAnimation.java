package game.Weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;

import game.entities.Player;

public class WeaponAnimation extends Sprite {
    private MyGdxGame game;
    private Player current_player;
    private Weapon weapon;

    public WeaponAnimation(MyGdxGame game, Player currentPlayer, Weapon weapon, String name){
        super(new Texture("weapons/" + name + ".png"));
        this.game = game;
        this.current_player = currentPlayer;
        setPosition(current_player.getX(),current_player.getPlayerY());
        this.weapon = weapon;
    }
    /**
     * renders weapon on screen
     * @param batch
     */
    public void render(SpriteBatch batch){
        batch.begin();
        batch.draw(this, getX(), getY());
        batch.end();
    }

    /**
     * Moves or sets the player
     */
    public void updateWeaponPosition(){

//        setPosition(body.getPosition().x,body.getPosition().y);
        setPosition(getX(),current_player.getPlayerY());
    }

    /**
     * Moves weapon animation on the X-axis
     * @param x - how fast the player moves.
     */
    public void moveWeaponAnimation(float x ){
        float p = getX();
        p += 470*x;
        setX(p);
    }




}
