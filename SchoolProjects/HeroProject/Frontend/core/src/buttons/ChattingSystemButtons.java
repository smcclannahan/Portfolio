package buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

import game.entities.Player;
import game.scene.GameScreen;
import game.scene.MainMenu;
import game.scene.MultiplayerScreen;

public class ChattingSystemButtons {

    private MyGdxGame game;
    private Stage stage;
    private Viewport gameViewport;

    public Skin mySkin;
    public Player current_player;
    private Table chatRoomTable;
    private ImageButton back_button;
    GameScreen gameScreen;

    public ChattingSystemButtons (MyGdxGame game, GameScreen gameScreen){
        this.game = game;
        this.gameScreen = gameScreen;
        gameViewport =  new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage (gameViewport, game.getBatch());

        Gdx.input.setInputProcessor(stage);//making the stage actually clickable
        mySkin = new Skin(Gdx.files.internal("skin/shadeui/uiskin.json"));
        createAndPositionElements();
        chatRoomTable = buildChatRoomTable();
        stage.addActor(back_button);
        stage.addActor(chatRoomTable);

        //addActionListeners();

    }
    public TextButton send_button;
    public TextArea message_field;
    public ScrollPane input_scroll;

    public List<String> users_list;
    public ScrollPane chat_scroll;
    public ScrollPane users_scroll;
    public Label chat_label;

    private Table buildChatRoomTable(){
        Table table = new Table();
        table.setFillParent(true);

        chat_label = new Label("", mySkin);
        chat_label.setWrap(true);
        chat_label.setAlignment(Align.topLeft);
        chat_label.setFontScale(3,3);

        chat_scroll = new ScrollPane(chat_label, mySkin);
        chat_scroll.setFadeScrollBars(false);

        table.add(chat_scroll).width(Gdx.graphics.getWidth()/2f).height(Gdx.graphics.getHeight()-300).colspan(2);

        users_list = new List<String>(mySkin, "dimmed");

        users_scroll = new ScrollPane(users_list, mySkin);
        //users_scroll.layout();
        users_scroll.scaleBy(1f,1f);
        users_scroll.setFadeScrollBars(false);

        table.add(users_scroll).width(Gdx.graphics.getWidth()/9f).height(Gdx.graphics.getHeight()/2-300).colspan(2).right();

        message_field = new TextArea("", mySkin);
        message_field.setPrefRows(2);
        input_scroll = new ScrollPane(message_field, mySkin);

        //input_scroll.scaleBy(1.0f);
        input_scroll.scaleBy(1f,1f);
        input_scroll.setFadeScrollBars(false);

        table.row();
        table.add(input_scroll).width(Gdx.graphics.getWidth()/4f).colspan(2).left();

        send_button = new TextButton("SEND", mySkin);
        table.add(send_button).width(100).colspan(2);
        table.setVisible(true);

        return table;
    }

    void createAndPositionElements(){
        back_button = new ImageButton(new SpriteDrawable(new Sprite( new Texture("back_button.png"))));
        back_button.setPosition(20, Gdx.graphics.getHeight() - 40, Align.topLeft);

        back_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.input.setInputProcessor(gameScreen.gameScreenButton.getStage());
                game.setScreen(gameScreen);
            }
        });

    }

    public Stage getStage(){
        return this.stage;
    }

}
