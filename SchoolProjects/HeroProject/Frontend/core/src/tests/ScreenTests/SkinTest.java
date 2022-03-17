package tests.ScreenTests;

import com.mygdx.game.MyGdxGame;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import buttons.GameScreenButton;
import buttons.LoginButton;
import game.entities.Player;
import game.scene.GameScreen;
import game.scene.LoginScreen;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class SkinTest {

    MyGdxGame game;

    LoginScreen login_screen_test;
    Player playerTest;
    GameScreen gs;

    @Before
    public void setUpPlayer(){
        playerTest = new Player("test_name","test_password", "p1");
        game = new MyGdxGame();
        login_screen_test = mock(LoginScreen.class);
        login_screen_test.loginButton = mock(LoginButton.class);
        login_screen_test.loginButton.current_player = playerTest;
        //System.out.println("test: "+login_screen_test.loginButton.current_player.getUsername());
        login_screen_test.loginButton.current_player.setPosition(100,100);
        System.out.println("test: "+login_screen_test.loginButton.current_player.getPlayerX());

    }

    @Test
    public void test_game_should_start_with_p1_as_default() {
        String default_skin = login_screen_test.loginButton.current_player.getCharacter();
        String expected = "p1";
        assertTrue("When login game should have p1 as default player",expected.equalsIgnoreCase(default_skin));
    }
    @Test
    public void test_game_should_set_player_skin_to_any_skin() {
        login_screen_test.loginButton.current_player.setCurrentCharacter("p2");
        String skin = login_screen_test.loginButton.current_player.getCharacter();
        String expected = "p2";
        assertTrue("When set a player skin, the skin should be set as current skin",expected.equalsIgnoreCase(skin));
    }
    @Test
    public void test_game_should_set_player_skin_to_any_skin_after_multiple_set() {
        login_screen_test.loginButton.current_player.setCurrentCharacter("p3");
        String skin = login_screen_test.loginButton.current_player.getCharacter();
        String expected = "p3";
        assertTrue("When set a player skin, the skin should be set as current skin",expected.equalsIgnoreCase(skin));

        login_screen_test.loginButton.current_player.setCurrentCharacter("p4");
        String skin2 = login_screen_test.loginButton.current_player.getCharacter();
        String expected2 = "p4";
        assertTrue(" the new skin should be set as current skin after multiple skin set",expected2.equalsIgnoreCase(skin2));

        login_screen_test.loginButton.current_player.setCurrentCharacter("p1");
        String skin3 = login_screen_test.loginButton.current_player.getCharacter();
        String expected3 = "p1";
        assertTrue(" the new skin should be set as current skin after multiple skin set",expected3.equalsIgnoreCase(skin3));
    }
}
