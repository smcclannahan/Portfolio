package tests.game_tests.entities_tests;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import buttons.LoginButton;
import game.entities.Player;
import game.scene.LoginScreen;
import com.mygdx.game.MyGdxGame;

public class player_test {
    MyGdxGame game;

    LoginScreen login_screen_test;
    Player playerTest;

    @Before
    public void setUpPlayer(){
        playerTest = new Player("test_name","test_password", "p1");
        game = new MyGdxGame();
        login_screen_test = Mockito.mock(LoginScreen.class);
        login_screen_test.loginButton = Mockito.mock(LoginButton.class);
        login_screen_test.loginButton.current_player = playerTest;
        //System.out.println("test: "+login_screen_test.loginButton.current_player.getUsername());

    }

    @Test
    public void test_game_should_have_expected_username() {
        String username = login_screen_test.loginButton.current_player.getUsername();
        String expected = "test_name";
        assertTrue("When login game should have the expected username",expected.equalsIgnoreCase(username));
    }
    @Test
    public void test_game_should_have_expected_password() {
        String password = login_screen_test.loginButton.current_player.getPassword();
        String expected = "test_password";
        assertTrue("When login game should have the expected password",expected.equalsIgnoreCase(password));
    }

    @Test
    public void test_player_initial_score_should_be_zero() {
        int score = login_screen_test.loginButton.current_player.getCurrentScore();
        assertTrue("Player initial score should be zero",0==score);
    }

    @Test
    public void test_player_initial_coin_should_be_zero() {
        int coin = login_screen_test.loginButton.current_player.getCurrentCoins();
        assertTrue("Player initial coin should be zero",0==coin);
    }

    @Test
    public void test_game_should_update_score() {
        int expected = 50;
        login_screen_test.loginButton.current_player.updateScore(50);
        int score = login_screen_test.loginButton.current_player.getCurrentScore();
        assertTrue("Game should update current score",score==expected);
        int expected2 = 150;
        login_screen_test.loginButton.current_player.updateScore(100);
        int score2 = login_screen_test.loginButton.current_player.getCurrentScore();
        assertTrue("Player should continually update score",score2==expected2);
    }
    @Test
    public void test_game_should_update_coin() {
        int expected = 50;
        login_screen_test.loginButton.current_player.updateCoins(50);
        int score = login_screen_test.loginButton.current_player.getCurrentCoins();
        assertTrue("Game should update current coin",score==expected);
        int expected2 = 150;
        login_screen_test.loginButton.current_player.updateCoins(100);
        int score2 = login_screen_test.loginButton.current_player.getCurrentCoins();
        assertTrue("Player should continually update coin",score2==expected2);
    }

}
