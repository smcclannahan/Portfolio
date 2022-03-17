package game.Weapons;

public class Bow extends Weapon {
    public Bow(int pow, int hit, int weaponRange, int buyPrice, String weaponName) {
        super(pow, hit, weaponRange, buyPrice, weaponName);
    }

    @Override
    public void equip() {
        super.equip();
    }

    @Override
    public int getPower() {
        return super.getPower();
    }

    @Override
    public int getHitRate() {
        return super.getHitRate();
    }

    @Override
    public int getRange() {
        return super.getRange();
    }

    @Override
    public int getPrice() {
        return super.getPrice();
    }

    @Override
    public int getSellPrice() {
        return super.getSellPrice();
    }

    @Override
    public boolean isEquipped() {
        return super.isEquipped();
    }

    @Override
    public String getName() {
        return super.getName();
    }
}
