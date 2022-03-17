package tests.weaponsTest;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import game.Weapons.Weapon;
import game.Weapons.Axe;
import game.Weapons.Sword;
import game.Weapons.Lance;
public class WeaponTesting {

    Weapon weapon;
    Sword sword;
    Axe axe;
    Lance lance;

    @Before
    public void setUpWeapons(){
        weapon = new Weapon(5, 100, 1,10,"Mace");
        sword = new Sword(5,100,1,15, "Iron Sword");
        axe = new Axe(7, 90, 1, 20, "Iron Axe");
        lance = new Lance(6,95,1,25,"Iron Lance");


    }

    @Test
    public void weaponTest(){
        String name = weapon.getName();
        String expected = "Mace";
        assertTrue("Should be named Mace", expected.equalsIgnoreCase(name));

        int power = weapon.getPower();
        int exp = 5;
        assertTrue("Should be 5", exp==power);

        int hitRate = weapon.getHitRate();
        int expHit = 100;
        assertTrue("Should be 100", expHit == hitRate);

        int rangeTest = weapon.getRange();
        int rangeExpect = 1;
        assertTrue("should be 1", rangeExpect==rangeTest);

        int buyTest = weapon.getPrice();
        int expPrice = 10;
        assertTrue("should be 10", expPrice == buyTest);

        int sellTest = weapon.getSellPrice();
        int expSell = 5;
        assertTrue("Should be 5", expSell == sellTest);

        weapon.equip();
        boolean equip = weapon.isEquipped();
        assertTrue("Weapon Should be equipped", equip);
    }
    @Test
    public void SwordTest(){
        String name = sword.getName();
        String expected = "Iron Sword";
        assertTrue("Should be named Iron Sword", expected.equalsIgnoreCase(name));

        int power = sword.getPower();
        int exp = 5;
        assertTrue("Should be 5", exp==power);

        int hitRate = sword.getHitRate();
        int expHit = 100;
        assertTrue("Should be 100", expHit == hitRate);

        int rangeTest = sword.getRange();
        int rangeExpect = 1;
        assertTrue("should be 1", rangeExpect==rangeTest);

        int buyTest = sword.getPrice();
        int expPrice = 15;
        assertTrue("should be 15", expPrice == buyTest);

        int sellTest = sword.getSellPrice();
        int expSell = 7;
        assertTrue("Should be 7", expSell == sellTest);

        sword.equip();
        boolean equip = sword.isEquipped();
        assertTrue("Weapon Should be equipped", equip);
    }
    @Test
    public void AxeTest(){
        String name = axe.getName();
        String expected = "Iron Axe";
        assertTrue("Should be named Iron Axe", expected.equalsIgnoreCase(name));

        int power = axe.getPower();
        int exp = 7;
        assertTrue("Should be 7", exp==power);

        int hitRate = axe.getHitRate();
        int expHit = 90;
        assertTrue("Should be 100", expHit == hitRate);

        int rangeTest = axe.getRange();
        int rangeExpect = 1;
        assertTrue("should be 1", rangeExpect==rangeTest);

        int buyTest = axe.getPrice();
        int expPrice = 20;
        assertTrue("should be 20", expPrice == buyTest);

        int sellTest = axe.getSellPrice();
        int expSell = 10;
        assertTrue("Should be 10", expSell == sellTest);

        axe.equip();
        boolean equip = axe.isEquipped();
        assertTrue("Weapon Should be equipped", equip);
    }
    @Test
    public void LanceTest(){
        String name = lance.getName();
        String expected = "Iron Lance";
        assertTrue("Should be named Iron Lance", expected.equalsIgnoreCase(name));

        int power = lance.getPower();
        int exp = 6;
        assertTrue("Should be 6", exp==power);

        int hitRate = lance.getHitRate();
        int expHit = 95;
        assertTrue("Should be 95", expHit == hitRate);

        int rangeTest = lance.getRange();
        int rangeExpect = 1;
        assertTrue("should be 1", rangeExpect==rangeTest);

        int buyTest = lance.getPrice();
        int expPrice = 25;
        assertTrue("should be 25", expPrice == buyTest);

        int sellTest = lance.getSellPrice();
        int expSell = 12;
        assertTrue("Should be 12", expSell == sellTest);

        lance.equip();
        boolean equip = lance.isEquipped();
        assertTrue("Weapon Should be equipped", equip);
    }


}
