//package tests.game_tests.entities_tests;
//import static org.junit.Assert.assertTrue;
//import org.junit.Test;
//import org.junit.Before;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import static org.mockito.Mockito.*;
//
//import buttons.LoginButton;
//import game.entities.AxeEnemy;
//import game.entities.BowEnemy;
//import game.entities.LanceEnemy;
//import game.entities.Player;
//import game.entities.SwordEnemy;
//import game.scene.LoginScreen;
//import com.mygdx.game.MyGdxGame;
//
//public class enemy_test {
//    MyGdxGame game;
//    Player playerTest;
//    AxeEnemy axeTest;
//    BowEnemy bowTest;
//    LanceEnemy lanceTest;
//    SwordEnemy swordTest;
//
//    @Before
//    public void setUp(){
//
//        game = new MyGdxGame();
//        axeTest = axeTest
//        axeTest = new AxeEnemy(1,1,100,100, "axe", 10, 150, 25);
//        bowTest = new BowEnemy(1,1,100,100, "bow", 25, 100, 5);
//        lanceTest = new LanceEnemy(1,1,100,100, "lance", 50, 50, 10);
//        swordTest = new SwordEnemy(1,1,100,100, "sword", 20, 125, 20);
//
////        playerTest = new Player("test_name","test_password");
////        login_screen_test = Mockito.mock(LoginScreen.class);
////        login_screen_test.loginButton = Mockito.mock(LoginButton.class);
////        login_screen_test.loginButton.current_player = playerTest;
//
//    }
//
//    @Test
//    public void test_name_axeEnemy() {
//        String name = axeTest.getName();
//        String expected = "axe";
//        assertTrue("should have expected name for axe enemy",expected.equalsIgnoreCase(name));
//    }
//
//    @Test
//    public void test_name_bowEnemy() {
//        String name = bowTest.getName();
//        String expected = "bow";
//        assertTrue("should have expected name for bow enemy",expected.equalsIgnoreCase(name));
//    }
//
//    @Test
//    public void test_name_LanceEnemy() {
//        String name = lanceTest.getName();
//        String expected = "lance";
//        assertTrue("should have expected name for lance enemy",expected.equalsIgnoreCase(name));
//    }
//
//    @Test
//    public void test_name_swordEnemy() {
//        String name = swordTest.getName();
//        String expected = "sword";
//        assertTrue("should have expected name for sword enemy",expected.equalsIgnoreCase(name));
//    }
//
//
//    @Test
//    public void test_attack_axeEnemy() {
//        int dmg = axeTest.attack();
//        int expected = 10;
//        assertTrue("should have expected damage for axe enemy",expected==dmg);
//    }
//
//    @Test
//    public void test_attack_bowEnemy() {
//        int dmg = bowTest.attack();
//        int expected = 25;
//        assertTrue("should have expected damage for bow enemy",expected==dmg);
//    }
//
//    @Test
//    public void test_attack_lanceEnemy() {
//        int dmg = lanceTest.attack();
//        int expected = 50;
//        assertTrue("should have expected damage for lance enemy",expected==dmg);
//    }
//
//    @Test
//    public void test_attack_swordEnemy() {
//        int dmg = axeTest.attack();
//        int expected = 20;
//        assertTrue("should have expected damage for sword enemy",expected==dmg);
//    }
//
//
//    @Test
//    public void test_health_axeEnemy() {
//        int hp = axeTest.getHealth();
//        int expected = 150;
//        assertTrue("should have expected health for axe enemy",expected==hp);
//    }
//
//    @Test
//    public void test_health_bowEnemy() {
//        int hp = bowTest.getHealth();
//        int expected = 100;
//        assertTrue("should have expected health for bow enemy",expected==hp);
//    }
//
//    @Test
//    public void test_health_lanceEnemy() {
//        int hp = lanceTest.getHealth();
//        int expected = 50;
//        assertTrue("should have expected health for lance enemy",expected==hp);
//    }
//
//    @Test
//    public void test_health_swordEnemy() {
//        int hp = axeTest.getHealth();
//        int expected = 125;
//        assertTrue("should have expected health for sword enemy",expected==hp);
//    }
//
//
//    @Test
//    public void test_defense_axeEnemy() {
//        int def = axeTest.getDefense();
//        int expected = 25;
//        assertTrue("should have expected defense for axe enemy",expected==def);
//    }
//
//    @Test
//    public void test_defense_bowEnemy() {
//        int def = bowTest.getDefense();
//        int expected = 5;
//        assertTrue("should have expected defense for bow enemy",expected==def);
//    }
//
//    @Test
//    public void test_defense_lanceEnemy() {
//        int def = lanceTest.getDefense();
//        int expected = 10;
//        assertTrue("should have expected defense for lance enemy",expected==def);
//    }
//
//    @Test
//    public void test_defense_swordEnemy() {
//        int def = swordTest.getDefense();
//        int expected = 20;
//        assertTrue("should have expected defense for sword enemy",expected==def);
//    }
//
//
//
//}
