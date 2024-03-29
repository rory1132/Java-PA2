package hk.ust.comp3021.gui;

import hk.ust.comp3021.entities.Player;
import hk.ust.comp3021.game.GameState;
import hk.ust.comp3021.game.Position;
import hk.ust.comp3021.gui.component.maplist.MapEvent;
import hk.ust.comp3021.gui.scene.game.ExitEvent;
import hk.ust.comp3021.gui.scene.game.GameScene;
import hk.ust.comp3021.gui.scene.start.StartScene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * The JavaFX application that launches the game.
 */
public class App extends Application {
    private Stage primaryStage;
    private Scene startScene;
    /**No. of player in the map*/
    public static int playercount=0;
    /**playerIds*/
    public static int[] ids= {0,0,0,0};


    /**
     * Set up the primary stage and show the {@link StartScene}.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages.
     * @throws Exception if something goes wrong.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Sokoban Game - COMP3021 2022Fall");

        // TODO
        var scene = new StartScene();
        this.primaryStage.setScene(scene);

        scene.getController().getOpenButton().addEventHandler(MapEvent.OPEN_MAP_EVENT_TYPE, this::onOpenMap
        );
//        scene.getController().getOpenButton().addEventHandler(MapEvent.OPEN_MAP_EVENT_TYPE,new EventHandler<MapEvent>() {
//                    @Override
//                    public void handle(MapEvent e) {
//                        onOpenMap(e);
//                    }
//                }
//        );
        //scene.getController().getOpenButton().setOnAction(e -> onOpenMap(scene.getController().getOpen()));
        this.startScene=scene;
        this.primaryStage.show();
    }

    /**
     * Event handler for opening a map.
     * Switch to the {@link GameScene} in the {@link this#primaryStage}.
     *
     * @param event The event data related to the map being opened.
     */
    public void onOpenMap(MapEvent event) {
        // TODO
        var state = new GameState(event.getModel().gameMap());
        playercount=0;
        for (int i = 0; i < state.getMapMaxHeight(); i++) {
            for (int j = 0; j < state.getMapMaxWidth(); j++) {
                if (state.getEntity(new Position(j, i)) instanceof Player) {
                    ids[playercount] = ((Player) Objects.requireNonNull(state.getEntity(new Position(j, i)))).getId();
                    playercount++;
                }
            }
        }


        GameScene scene;
        try {
            scene = new GameScene(state);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene.getController().getExitButton().addEventHandler(ExitEvent.EVENT_TYPE, this::onExitGame
        );
        this.primaryStage.setScene(scene);

    }

    /**
     * Event handler for exiting the game.
     * Switch to the {@link StartScene} in the {@link this#primaryStage}.
     *
     * @param event The event data related to exiting the game.
     */
    public void onExitGame(ExitEvent event) {
        // TODO
        this.primaryStage.setScene(startScene);

    }

}
