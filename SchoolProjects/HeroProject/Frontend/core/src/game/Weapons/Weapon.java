package game.Weapons;

public class Weapon {

    public int power;
    public int hitRate;
    public int range;
    public int price;

    public boolean equipped;


    String name;

    /**
     * Weapon to be used by the player and enemies
     * @param pow - attack power of weapon
     * @param hit - base hit rate of weapon
     * @param weaponRange - range of weapon, usually one or two at most
     * @param buyPrice - how much the weapon costs in the shop
     * @param weaponName - Name of weapon
     */
    public Weapon(int pow, int hit, int weaponRange, int buyPrice, String weaponName){
        power = pow;
        hitRate = hit;
        range = weaponRange;
        price = buyPrice;

        name = weaponName;
    }

    /**
     * This weapon becomes the active weapon
     */
    public void equip(){equipped = true;}

    /**
     * get method for weapon base power
     * @return - power of weapon
     */
    public int getPower(){
        return power;
    }

    /**
     * gets hit rate for weapon
     * @return hit-rate for weapon
     */
    public int getHitRate(){return hitRate;}

    /**
     * gets weapon range
     * @return weapon range
     */
    public int getRange(){return range;}

    /**
     * gets price to buy weapon
     * @return price of weapon
     */
    public int getPrice(){return price;}

    /**
     * gets price of weapon if player sells it
     * @return
     */
    public int getSellPrice(){return price/2;}

    /**
     * checks if the weapon is equipped
     * @return true if the weapon ie equipped, false otherwise
     */
    public boolean isEquipped(){return equipped;}

    /**
     * gets name of weapon
     * @return name of weapon
     */
    public String getName(){
        return name;
    }
}
