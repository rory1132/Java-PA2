package hk.ust.comp3021.gui.component.board;

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

        for (int i = 0; i < state.getMapMaxHeight(); i++) {
            for (int j = 0; j < state.getMapMaxWidth(); j++) {
                if (state.getEntity(new Position(i,j))instanceof Wall) {
                    try{
                        map.add(new Cell(),j,i);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
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
