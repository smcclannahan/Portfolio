package game.Weapons;

public class Potion extends Item {
    int health;
    public Potion(int buyPrice, int healedDmg) {
        super(buyPrice);
    }

    @Override
    public int getPrice() {
        return super.getPrice();
    }

    @Override
    public int getSellPrice() {
        return super.getSellPrice();
    }

    public int getHealth() {
        return health;
    }
}
