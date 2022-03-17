package buttons;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

import game.entities.AxeEnemy;
import game.entities.Player;
import game.Weapons.*;
import game.scene.BattleScreen;
import game.scene.GameScreen;

public class ShopButtons {

    private MyGdxGame game;
    private Stage stage;
    private Viewport gameViewport;


    public ImageButton buyButton, sellButton, equipButton, back_button;
    private Skin skin;

    Weapon testWeapon;
    Item testItem;

    public Player current_player;//public for testing

    public ShopButtons(MyGdxGame game, Player current_player){
        this.game = game;
        this.current_player = current_player;
        gameViewport =  new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());

        stage = new Stage (gameViewport, game.getBatch());

        Gdx.input.setInputProcessor(stage);//making the stage actually clickable
        createButtons();
        addActionListeners();

        testWeapon = new Weapon(5,100,1,20,"sword");
        testItem = new Item(10);

        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        ItemsAvailable();
        stage.addActor(buyButton);
        stage.addActor(sellButton);
        stage.addActor(equipButton);
        stage.addActor(back_button);

    }
    void createButtons(){
        buyButton = new ImageButton(new SpriteDrawable(new Sprite(new Texture("BuyButton.png"))));
        buyButton.setPosition(0,0, Align.bottomLeft);

        sellButton = new ImageButton(new SpriteDrawable(new Sprite(new Texture("SellButton.png"))));
        sellButton.setPosition(Gdx.graphics.getWidth()/2, 0, Align.bottom);

        equipButton = new ImageButton(new SpriteDrawable(new Sprite(new Texture("EquipButton.png"))));
        equipButton.setPosition(Gdx.graphics.getWidth(),0, Align.bottomRight);

        back_button = new ImageButton(new SpriteDrawable(new Sprite( new Texture("back_button.png"))));
        back_button.setPosition(20, Gdx.graphics.getHeight() - 40, Align.topLeft);
    }
    public void addActionListeners(){
        buyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                current_player.subtractCoins(testWeapon.getPrice());
                current_player.addWeapon(testWeapon);
            }
        });
        sellButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                current_player.updateCoins(testWeapon.getSellPrice());
                //current_player.dropWeapon(); to update
            }
        });
        equipButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //To implement

            }
        });
        back_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
               // game.setScreen(new BattleScreen(game,current_player, current_player.get));
            }
        });

    }
    public Label itemLabel, priceLabel;

    public Label itemLabel2, priceLabel2;

    public Table ItemsAvailable(){
        Table table = new Table();
        table.setFillParent(true);

        itemLabel = new Label("Iron Sword", skin);
        priceLabel = new Label("20 Coins", skin);

        itemLabel.setWrap(true);
        itemLabel.setFontScale(3,3);
        table.add(itemLabel).width(200).colspan(1);

        priceLabel.setWrap(true);
        priceLabel.setFontScale(3,3);
        table.add(priceLabel).width(200).colspan(2);


        table.row().space(20,0,20,0);

        itemLabel2 = new Label("Potion", skin);
        priceLabel2 = new Label("10 coins", skin);

        itemLabel2.setWrap(true);
        itemLabel2.setFontScale(3,3);
        table.add(itemLabel2).width(200).colspan(1);

        priceLabel2.setWrap(true);
        priceLabel2.setFontScale(3,3);
        table.add(priceLabel2).width(200).colspan(2);


        table.setVisible(true);

        return table;
    }
    public Stage getStage(){
        return this.stage;
    }

    public void buyButtonTest(Weapon weapon){ // made to test functions of the buy button
        current_player.subtractCoins(weapon.getPrice());
        current_player.addWeapon(weapon);
    }
    public void sellButtonTest(){ //Made to test functions of the sell button
        current_player.updateCoins(testWeapon.getSellPrice());
    }
}