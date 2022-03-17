package buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

import game.entities.Player;

public class DialogCollision {
    private MyGdxGame game;
    private Stage stage;
    public Viewport gameViewport;

    public Skin mySkin;
    public Player current_player;
    private Table dialogTable;

    public DialogCollision (MyGdxGame game){
        this.game = game;
        gameViewport =  new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage (gameViewport, game.getBatch());

        Gdx.input.setInputProcessor(stage);//making the stage actually clickable
        mySkin = new Skin(Gdx.files.internal("skin/shadeui/uiskin.json"));
        buildDialogRoomTable();
        dialogTable = buildDialogRoomTable();
        stage.addActor(dialogTable);

        //buttonsListeners();

    }
    public TextButton yes_button;
    public TextButton no_button;
    public Label dialog_label;
    public ScrollPane scroll;

    private Table buildDialogRoomTable(){
        Table table = new Table();
        table.setFillParent(true);

        dialog_label = new Label("Would you like to battle?", mySkin);
        dialog_label.setWrap(true);
        dialog_label.setAlignment(Align.center);
        dialog_label.setFontScale(3,3);

        scroll = new ScrollPane(dialog_label, mySkin);
        scroll.setFadeScrollBars(false);

        table.add(scroll).width(300).height(200).colspan(2);

        table.row();
        yes_button = new TextButton("YES", mySkin);
        table.add(yes_button).width(100).colspan(1);

        no_button = new TextButton("NO", mySkin);
        //no_button.setPosition(100,100);//back_button.setPosition(20, Gdx.graphics.getHeight() - 40, Align.topLeft);
        table.add(no_button).width(100).colspan(1);

        table.setVisible(true);


//        send_button.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//
//                String text = message_field.getText();
//
//                if(!text.isEmpty()){
//                    socket.emit("user_message", username + ": " + text + "\n");
//                    message_field.setText("");
//                }
//
//            }
//        });

        return table;
    }

    void buttonsListeners(){

//        yes_button.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                //game.setScreen( new GameScreen(game) );
//                System.out.println("dialog yes");
//            }
//        });
//        no_button.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                //game.setScreen( new GameScreen(game) );
//                System.out.println("dialog yes");
//            }
//        });

    }

    public Stage getStage(){
        return this.stage;
    }

}
