package buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

import game.entities.Player;
import game.scene.MainMenu;

public class MenuOptionButton {
    private MyGdxGame game;
    private Stage stage;
    private Viewport gameViewport;

    private ImageButton back_button;
    private Player current_player;

    public MenuOptionButton (MyGdxGame game, Player current_player){
        this.game = game;
        this.current_player = current_player;
        gameViewport =  new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage (gameViewport, game.getBatch());

        Gdx.input.setInputProcessor(stage);//making the stage actually clickable

        createAndPositionElements();
        stage.addActor(back_button);

        addActionListeners();

    }

    void createAndPositionElements(){
        back_button = new ImageButton(new SpriteDrawable(new Sprite( new Texture("back_button.png"))));

        back_button.setPosition(20, Gdx.graphics.getHeight() - 40, Align.topLeft);

    }

    void addActionListeners(){
        back_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenu(game, current_player));
            }
        });
    }

    public Stage getStage(){
        return this.stage;
    }

}


