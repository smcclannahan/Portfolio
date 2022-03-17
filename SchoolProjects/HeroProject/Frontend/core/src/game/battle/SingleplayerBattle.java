package game.battle;

import game.entities.EntitySuper;
import game.entities.Player;

public class SingleplayerBattle {

    private Player player;
    private EntitySuper enemy;
    private boolean is_collide;

    public SingleplayerBattle(Player player, EntitySuper enemy){
        this.player = player;
        this.enemy = enemy;
    }

    public void checkCollision(){
        float x_range = Math.abs(player.getPlayerX()-enemy.position.x);
        float y_range = Math.abs(player.getPlayerY()-enemy.position.y);
//
        //System.out.println("here: ");
//        System.out.println("enemy width: "+enemy.dimensions.x);
//        System.out.println("enemy height: "+enemy.dimensions.y);
//        System.out.println("enemy x: "+enemy.position.x);
//        System.out.println("enemy y: "+enemy.position.y);

        if(x_range<=enemy.dimensions.x && y_range<=enemy.dimensions.y){
            //is_collide = true;
            setIscollide(true);
        }else {
            //is_collide = false;
            setIscollide(false);
        }
        //return is_collide;
    }

    public void setIscollide(boolean is_collide){
        this.is_collide = is_collide;
    }
    public boolean getIscollide(){
        return this.is_collide;
    }


}
