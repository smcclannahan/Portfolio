package game.Weapons;

public class Lance extends Weapon {
    /**
     * Lance to be used by the player and enemies, superclass of weapon
     * @param pow - attack power of Lance
     * @param hit - base hit rate of Lance
     * @param weaponRange - range of lance, usually one or two at most
     * @param buyPrice - how much the lance costs in the shop
     * @param weaponName - Name of lance
     */
    public Lance(int pow, int hit, int weaponRange, int buyPrice, String weaponName) {
        super(pow, hit, weaponRange, buyPrice, weaponName);
    }

    /**
     * This weapon becomes the active weapon
     */
    @Override
    public void equip() {
        super.equip();
    }

    /**
     * get method for weapon base power
     * @return - power of weapon
     */
    @Override
    public int getPower() {
        return super.getPower();
    }

    /**
     * gets hit rate for weapon
     * @return hit-rate for weapon
     */
    @Override
    public int getHitRate() {
        return super.getHitRate();
    }

    /**
     * gets weapon range
     * @return weapon range
     */
    @Override
    public int getRange() {
        return super.getRange();
    }

    /**
     * gets price to buy weapon
     * @return price of weapon
     */
    @Override
    public int getPrice() {
        return super.getPrice();
    }

    /**
     * gets price of weapon if player sells it
     * @return
     */
    @Override
    public int getSellPrice() {
        return super.getSellPrice();
    }

    /**
     * checks if the weapon is equipped
     * @return true if the weapon ie equipped, false otherwise
     */
    @Override
    public boolean isEquipped() {
        return super.isEquipped();
    }

    /**
     * gets name of weapon
     * @return name of weapon
     */
    @Override
    public String getName() {
        return super.getName();
    }

}
