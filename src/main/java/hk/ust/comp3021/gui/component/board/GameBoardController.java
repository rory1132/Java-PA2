package hk.ust.comp3021.gui.component.board;

import hk.ust.comp3021.entities.Box;
import hk.ust.comp3021.entities.Entity;
import hk.ust.comp3021.entities.Player;
import hk.ust.comp3021.entities.Wall;
import hk.ust.comp3021.game.GameState;
import hk.ust.comp3021.game.Position;
import hk.ust.comp3021.game.RenderingEngine;
import hk.ust.comp3021.gui.utils.Message;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Control logic for a {@link GameBoard}.
 * <p>
 * GameBoardController serves the {@link RenderingEngine} which draws the current game map.
 */
public class GameBoardController implements RenderingEngine, Initializable {
    @FXML
    private GridPane map;

    @FXML
    private Label undoQuota;

    public static int playercount=0;

    public int[] ids= {0,0,0,0};




    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Draw the game map in the {@link #map} GridPane.
     *
     * @param state The current game state.
     */
    @Override
    public void render(@NotNull GameState state) {
        // TODO
        Platform.runLater(() -> {
            for (int i = 0; i < state.getMapMaxHeight(); i++) {
                for (int j = 0; j < state.getMapMaxWidth(); j++) {
                    if (state.getEntity(new Position(j, i)) instanceof Player) {
                        ids[playercount] = ((Player) state.getEntity(new Position(j,i))).getId();
                        playercount++;
                    }
                }
            }
            for (int i = 0; i < state.getMapMaxHeight(); i++) {
                for (int j = 0; j < state.getMapMaxWidth(); j++) {
                    Entity en = state.getEntity(new Position(j, i));
                    switch (en) {
                        case Wall wall:
                            try {
                                Cell cell = new Cell();
                                //cell.getController().initialPos(j, i);
                                cell.getController().setImage(Objects.requireNonNull(getClass().getClassLoader().getResource(
                                        "components/img/wall.png")));
                                map.add(cell, j, i);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case Player player:
                            try {
                                Cell cell = new Cell();
                                //cell.getController().initialPos(j, i);
                                if (((Player) en).getId() == ids[0]) {
                                    cell.getController().setImage(Objects.requireNonNull(getClass().getClassLoader().getResource(
                                            "components/img/player-0.png")));
                                } else if (((Player) en).getId() == ids[1]) {
                                    cell.getController().setImage(Objects.requireNonNull(getClass().getClassLoader().getResource(
                                            "components/img/player-1.png")));
                                } else if (((Player) en).getId() == ids[2]) {
                                    cell.getController().setImage(Objects.requireNonNull(getClass().getClassLoader().getResource(
                                            "components/img/player-2.png")));
                                } else if (((Player) en).getId() == ids[3]) {
                                    cell.getController().setImage(Objects.requireNonNull(getClass().getClassLoader().getResource(
                                            "components/img/player-3.png")));
                                }
                                map.add(cell, j, i);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case Box box:
                            try {
                                Cell cell = new Cell();
                                //cell.getController().initialPos(j, i);
                                if (((Box) en).getPlayerId() == ids[0]) {
                                    cell.getController().setImage(Objects.requireNonNull(getClass().getClassLoader().getResource(
                                            "components/img/box-0.png")));
                                } else if (((Box) en).getPlayerId() == ids[1]) {
                                    cell.getController().setImage(Objects.requireNonNull(getClass().getClassLoader().getResource(
                                            "components/img/box-1.png")));
                                } else if (((Box) en).getPlayerId() == ids[2]) {
                                    cell.getController().setImage(Objects.requireNonNull(getClass().getClassLoader().getResource(
                                            "components/img/box-2.png")));
                                } else if (((Box) en).getPlayerId() == ids[3]) {
                                    cell.getController().setImage(Objects.requireNonNull(getClass().getClassLoader().getResource(
                                            "components/img/box-3.png")));
                                }
                                map.add(cell, j, i);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case null:
                        default:
                            try {
                                Cell cell = new Cell();
                                //cell.getController().initialPos(j, i);
                                if (state.getDestinations().contains(new Position(j, i))) {
                                    cell.getController().setImage(Objects.requireNonNull(getClass().getClassLoader().getResource(
                                            "components/img/destination.png")));
                                } else
                                    cell.getController().setImage(Objects.requireNonNull(getClass().getClassLoader().getResource(
                                            "components/img/empty.png")));
                                map.add(cell, j, i);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                    }


                }
            }
            if (state.getUndoQuota().isPresent()) undoQuota.setText("Undo Quota: "+ state.getUndoQuota().get());
            map.add(undoQuota,0,state.getMapMaxHeight()+1,state.getMapMaxWidth(),1);
        });
    }


    /**
     * Display a message via a dialog.
     *
     * @param content The message
     */
    @Override
    public void message(@NotNull String content) {
        Platform.runLater(() -> Message.info("Sokoban", content));
    }
}
