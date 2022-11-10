package hk.ust.comp3021.gui.component.control;

import hk.ust.comp3021.actions.Action;
import hk.ust.comp3021.actions.Move;
import hk.ust.comp3021.actions.Undo;
import hk.ust.comp3021.entities.Player;
import hk.ust.comp3021.game.InputEngine;
import hk.ust.comp3021.gui.App;
import hk.ust.comp3021.gui.scene.game.ExitEvent;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Control logic for a {@link ControlPanel}.
 * ControlPanelController serves as {@link InputEngine} for the game.
 * It caches users input (move actions) and provides them to the {@link hk.ust.comp3021.gui.scene.game.GUISokobanGame}.
 */
public class ControlPanelController implements Initializable, InputEngine {
    @FXML
    private FlowPane playerControls;
    private int[] ids= {0,0,0,0};
    public static GridPane grid;


    /**
     * Fetch the next action made by users.
     * All the actions performed by users should be cached in this class and returned by this method.
     *
     * @return The next action made by users.
     */
    @Override
    public @NotNull Action fetchAction() {
        // TODO

        final Action[] action = new Action[1];
        playerControls.addEventHandler(ExitEvent.EVENT_TYPE, e -> {
            action[0] = new Undo(ids[0]);

        });
        grid.addEventHandler(MoveEvent.UP_EVENT_TYPE,e -> {
            action[0] = new Move.Up(e.getPlayerId());
        });
        grid.addEventHandler(MoveEvent.DOWN_EVENT_TYPE,e -> {
            action[0] = new Move.Down(e.getPlayerId());
        });
        grid.addEventHandler(MoveEvent.LEFT_EVENT_TYPE,e -> {
            action[0] = new Move.Left(e.getPlayerId());
        });
        grid.addEventHandler(MoveEvent.RIGHT_EVENT_TYPE,e -> {
            action[0] = new Move.Right(e.getPlayerId());
        });

//        t.start();
//        try {
//            t.join();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        try {
            while (action[0]==null)
                Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return action[0];

        //throw new NotImplementedException();
    }

    /**
     * Initialize the controller as you need.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
        ids= App.ids;
        grid = new GridPane();

        for (int i = 0; i < App.playercount; i++) {
            MovementButtonGroup m;
            try {
                m = new MovementButtonGroup();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            m.getController().setPlayer(new Player(ids[i]));
            m.getController().setPlayerImage(Objects.requireNonNull(getClass().getClassLoader().getResource(
                    String.format("components/img/player-%s.png",i))));
            playerControls.getChildren().add(m.getController().getPlayerControl());
        }
    }

    /**
     * Event handler for the undo button.
     * Cache the undo action and return it when {@link #fetchAction()} is called.
     *
     * @param event Event data related to clicking the button.
     */
    public void onUndo(ActionEvent event) {
        // TODO
        Event e = new ExitEvent();
        Event.fireEvent(playerControls,e);
    }

    /**
     * Adds a player to the control player.
     * Should add a new movement button group for the player.
     *
     * @param player         The player.
     * @param playerImageUrl The URL to the profile image of the player
     */
    public void addPlayer(Player player, URL playerImageUrl) {
        // TODO

    }
//    class A extends Thread {
//        public Action a;
//        @Override
//        public void run() {
//            try {
//                while (a==null) {
//                    if (a!=null)this.interrupt();
//                    Thread.sleep(500);
//                }
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//        }
//        public void setA(Action aa) {
//            a=aa;
//        }
//    }

}
