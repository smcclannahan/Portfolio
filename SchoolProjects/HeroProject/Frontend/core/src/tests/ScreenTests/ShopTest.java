package tests.ScreenTests;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import buttons.ShopButtons;
import game.Weapons.Weapon;
import game.entities.Player;
import game.scene.ShopScreen;

import com.mygdx.game.MyGdxGame;


public class ShopTest {

    MyGdxGame game;
    Player playerTest;
    ShopScreen shop;
    Weapon testWeapon;

    @Before
    public void setUpPlayer(){
        playerTest = new Player("test_name","test_password", "p1");
        game = new MyGdxGame();
        shop = Mockito.mock(ShopScreen.class);
        shop.shopbuttons = Mockito.mock(ShopButtons.class);
        shop.currentPlayer = playerTest;
        shop.shopbuttons.current_player = playerTest;
        playerTest.updateCoins(500);
    }

    @Test
    public void coinTest(){
        int money = playerTest.getCurrentCoins();
        assertTrue("Should be 500", money == 500);
    }
    @Test
    public void buyTest(){
        playerTest.setCoins(500);
        Weapon weaponTest = new Weapon(5,100,1,20,"sword");
        shop.shopbuttons.buyButtonTest(weaponTest);
        //int money = playerTest.getCurrentCoins();
        //assertTrue("should update player coins subtracting 20", money == 480);
        assertTrue("Should add a weapon to the player's inventory in the second position",
                playerTest.getWeapon(0).equals(weaponTest));
    }
    @Test
    public void sellTest(){
        playerTest.setCoins(500);
        shop.shopbuttons.sellButtonTest();
        int money = playerTest.getCurrentCoins();
        assertTrue("selling an item should increase the player's current money by half the weapon's value",money == 510);
    }



}