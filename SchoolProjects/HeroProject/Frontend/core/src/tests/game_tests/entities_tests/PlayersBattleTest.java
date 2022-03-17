package tests.game_tests.entities_tests;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;

import game.entities.AxeEnemy;
import game.entities.Player;
import game.scene.BattleScreen;

public class PlayersBattleTest {
    AxeEnemy enemy;
    BattleScreen battleScreen;
    Player p;

    @Before
    public void setUpPlayer(){
        battleScreen = Mockito.mock(BattleScreen.class);
        enemy = mock(AxeEnemy.class);
        when(enemy.getHp()).thenAnswer(new Answer<Integer>() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return 100;
            }
        });
        p = mock(Player.class);
        when(p.getHp()).thenAnswer(new Answer<Integer>() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return 100;
            }
        });
        when(p.getDef()).thenAnswer(new Answer<Integer>() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return 20;
            }
        });
        when(p.getDmg()).thenAnswer(new Answer<Integer>() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return 10;
            }
        });
        battleScreen.current_player = p;
        battleScreen.enemy = enemy;
//
        battleScreen.current_player.setHp(10);
    }

    @Test
    public void when_starting_battle_player_should_have_full_hp() {
        assertEquals("when starting battle player should have full hp",100,p.getHp());
    }

    @Test
    public void when_starting_battle_enemy_should_have_full_hp() {
        assertEquals("when starting battle enemy should have full hp",100,enemy.getHp());
    }

    @Test
    public void when_player_attack_enemy_hp_decreases() {
        enemy.setHp(10);
        when(enemy.getHp()).thenAnswer(new Answer<Integer>() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return 90;
            }
        });
        assertEquals("when player attack enemy hp decreases",90,enemy.getHp());
    }

    @Test
    public void when_enemy_attack_player_hp_decreases() {
        p.setHp(10);
        when(p.getHp()).thenAnswer(new Answer<Integer>() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return 90;
            }
        });
        assertEquals("when enemy attack player hp decreases",90,p.getHp());
    }




}
