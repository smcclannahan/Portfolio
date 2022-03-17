package game.Weapons;

public class Item {

    int price;
    public Item(int buyPrice){
        price = buyPrice;
    }
    public int getPrice(){
        return price;
    }
    public int getSellPrice(){
        return price/2;
    }

}
