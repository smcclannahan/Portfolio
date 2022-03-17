package tests.ScreenTests;

import com.mygdx.game.MyGdxGame;

import org.junit.Before;
import org.junit.Test;

import buttons.LoginButton;
import game.entities.Player;
import game.scene.GameScreen;
import game.scene.LoginScreen;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class GameScreenTest {

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

    }

    @Test
    public void test_game_should_start_at_desired_x_position() {
        login_screen_test.loginButton.current_player.setPosition(100,100);
        float x = login_screen_test.loginButton.current_player.getPlayerX();
        float expectedX = 100;
        assertTrue("Game should start at desired x",expectedX==x);
    }

    @Test
    public void test_game_should_start_at_desired_y_position() {
        login_screen_test.loginButton.current_player.setPosition(100,100);
        float y = login_screen_test.loginButton.current_player.getPlayerY();
        float expectedY = 100;
        assertTrue("Game should start at desired y",expectedY==y);
    }
    @Test
    public void test_game_should_have_player_faced_direction_UP() {
        login_screen_test.loginButton.current_player.setPosition(100,100);
        login_screen_test.loginButton.current_player.setDirection(Player.Direction.UP);
        Player.Direction dir = login_screen_test.loginButton.current_player.direction;
        Player.Direction expectedDir = Player.Direction.UP;
        assertTrue("game should have player faced direction up",dir==expectedDir);
    }
    @Test
    public void test_game_should_have_player_faced_direction_RIGHT() {
        login_screen_test.loginButton.current_player.setPosition(100,100);
        login_screen_test.loginButton.current_player.setDirection(Player.Direction.RIGHT);
        Player.Direction dir = login_screen_test.loginButton.current_player.direction;
        Player.Direction expectedDir = Player.Direction.RIGHT;
        assertTrue("game should have player faced direction right",dir==expectedDir);
    }
    @Test
    public void test_game_should_have_player_faced_direction_DOWN() {
        login_screen_test.loginButton.current_player.setPosition(100,100);
        login_screen_test.loginButton.current_player.setDirection(Player.Direction.DOWN);
        Player.Direction dir = login_screen_test.loginButton.current_player.direction;
        Player.Direction expectedDir = Player.Direction.DOWN;
        assertTrue("game should have player faced direction down",dir==expectedDir);
    }
    @Test
    public void test_game_should_have_player_faced_direction_LEFT() {
        login_screen_test.loginButton.current_player.setPosition(100,100);
        login_screen_test.loginButton.current_player.setDirection(Player.Direction.LEFT);
        Player.Direction dir = login_screen_test.loginButton.current_player.direction;
        Player.Direction expectedDir = Player.Direction.LEFT;
        assertTrue("game should have player faced direction left",dir==expectedDir);
    }
}
