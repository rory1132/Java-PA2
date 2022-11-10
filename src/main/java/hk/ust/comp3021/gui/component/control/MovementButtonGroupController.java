package hk.ust.comp3021.gui.component.control;

import hk.ust.comp3021.entities.Player;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Control logic for {@link MovementButtonGroup}.
 */
public class MovementButtonGroupController implements Initializable {
    @FXML
    private GridPane playerControl;

    @FXML
    private ImageView playerImage;

    private Player player = null;

    /**
     * Sets the player controller by the button group.
     *
     * @param player The player.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * The URL to the profile image of the player.
     *
     * @param url The URL.
     */
    public void setPlayerImage(URL url) {
        // TODO
        Image i;
        try {
            i = new Image(url.openStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        playerImage.setImage(i);
    }

    @FXML
    private void moveUp() {
        // TODO
        Event e = new MoveEvent(MoveEvent.UP_EVENT_TYPE,player.getId());
        Event.fireEvent(ControlPanelController.grid,e);
    }

    @FXML
    private void moveDown() {
        // TODO
        Event e = new MoveEvent(MoveEvent.DOWN_EVENT_TYPE,player.getId());
        Event.fireEvent(ControlPanelController.grid,e);
    }

    @FXML
    private void moveLeft() {
        // TODO
        Event e = new MoveEvent(MoveEvent.LEFT_EVENT_TYPE,player.getId());
        Event.fireEvent(ControlPanelController.grid,e);
    }

    @FXML
    private void moveRight() {
        // TODO
        Event e = new MoveEvent(MoveEvent.RIGHT_EVENT_TYPE,player.getId());
        Event.fireEvent(ControlPanelController.grid,e);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
    }
    /**Get player control grid pane
     * @return playControl*/
    public GridPane getPlayerControl() {
        return playerControl;
    }
}
