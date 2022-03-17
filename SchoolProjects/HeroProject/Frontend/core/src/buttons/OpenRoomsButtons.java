package buttons;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import game.entities.Player;
import game.scene.GameScreen;
import game.scene.MainMenu;
import game.scene.MultiplayerScreen;


public class OpenRoomsButtons {
    private MyGdxGame game;
    private Stage stage;
    private Viewport gameViewport;

    public List<String> openLobbyList;
    public ScrollPane lobbyScroll;

    public Player current_player;

    private Table openLobbies;
    public Skin skin;
    private ImageButton back_button;

    public Label roomName_label1, roomSize_lbl1;
    public TextButton connect_button1;

    public String id_room;
    public Net.HttpRequest httpRequest;
    private Net.HttpResponseListener listener;
    private Map<Integer, String> room_data = new HashMap<Integer, String>();

    public OpenRoomsButtons(MyGdxGame game, Player current_player){
        this.game = game;
        this.current_player =current_player;
        gameViewport =  new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage (gameViewport, game.getBatch());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        addActionAndListener();
        stage.addActor(back_button);

    }

    public void handleHttpRequest() {
        String s = "{\"hostname\":\""+current_player.getUsername()+"\"}";
        System.out.println("body: "+s);
        httpRequest = new Net.HttpRequest(Net.HttpMethods.PUT);
        httpRequest.setUrl("http://coms-309-sd-2.misc.iastate.edu:8080/joingame");
        httpRequest.setHeader("Content-Type", "application/json");
        httpRequest.setContent(s);
        listener = new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                try {
                    final int statusCode = httpResponse.getStatus().getStatusCode();
                    if (statusCode == 200) {
                        String response = httpResponse.getResultAsString();
                        JsonReader reader = new JsonReader();
                        System.out.println("response: " + response);
                        JsonValue res = reader.parse(response);
                        for (JsonValue json : res) {
                            room_data.put(json.get("id").asInt(), json.get("hostname").asString());
                        }
                        openLobby(room_data);

                    } else {
                        System.out.println("Status code: " + statusCode);
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }

            }

            public void failed(Throwable t) {
                System.out.println("HTTP request failed! " + t.getMessage());
            }

            @Override
            public void cancelled() {
                System.out.println("Cancelled");
            }
        };

        Gdx.net.sendHttpRequest(httpRequest, listener);
        System.out.println("room data: " + room_data);

    }
    public void openLobby(Map<Integer, String> room_data){
        Table table = new Table();
        table.setFillParent(true);
        String lbl = "Room N°";
//        Class currentClass = getClass();
//        Field[] fields = currentClass.getFields();
//        for (Field f : fields) {
//            System.out.println("name: "+f.getName());
//        }
        for (final Map.Entry<Integer,String> entry : room_data.entrySet()) {
            roomName_label1 = new Label(lbl + entry.getKey(), skin);
            roomSize_lbl1 = new Label("1/2", skin);
            connect_button1 = new TextButton("Connect", skin, "normal");

            roomName_label1.setWrap(true);
            roomName_label1.setFontScale(3, 3);
            table.add(roomName_label1).width(400).colspan(1);

            roomSize_lbl1.setWrap(true);
            roomSize_lbl1.setFontScale(3, 3);
            table.add(roomSize_lbl1).width(400).colspan(2);

            table.add(connect_button1).width(400).colspan(3);
            connect_button1.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("room pressed: " + entry.getKey());
                    game.setScreen(new GameScreen(game, current_player, entry.getKey()));
                }
            });

            table.row().space(20, 0, 20, 0);
        }

        table.setVisible(true);


        openLobbies = table;
        if(room_data.size()==1){
            openLobbies.setPosition(Gdx.graphics.getWidth() / 4 - 500, Gdx.graphics.getHeight()/4);
        }else {
            openLobbies.setPosition(Gdx.graphics.getWidth() / 4 - 500, Gdx.graphics.getHeight() / 4 - (30 * room_data.size()));
        }
        stage.addActor(openLobbies);
    }
    void addActionAndListener(){
        back_button = new ImageButton(new SpriteDrawable(new Sprite( new Texture("back_button.png"))));
        back_button.setPosition(20, Gdx.graphics.getHeight() - 40, Align.topLeft);

        back_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MultiplayerScreen(game, current_player));
            }
        });
    }
    public Stage getStage(){
        return this.stage;
    }


}
