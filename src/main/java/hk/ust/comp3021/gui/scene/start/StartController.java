package hk.ust.comp3021.gui.scene.start;


import hk.ust.comp3021.gui.component.maplist.MapEvent;
import hk.ust.comp3021.gui.component.maplist.MapList;
import hk.ust.comp3021.gui.component.maplist.MapModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

/**
 * Control logic for {@link  StartScene}.
 */
public class StartController implements Initializable {
    @FXML
    private MapList mapList;

    @FXML
    private Button deleteButton;

    @FXML
    private Button openButton;

    /** mapModel items
     */
    public static ObservableList<MapModel> items;




    /**
     * Initialize the controller.
     * Load the built-in maps to {@link this#mapList}.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        deleteButton.disableProperty().bind(mapList.getSelectionModel().selectedItemProperty().isNull());
        openButton.disableProperty().bind(mapList.getSelectionModel().selectedItemProperty().isNull());

        // TODO
        MapModel ma;
        MapModel mb;

        URI a = null; try {
            a = getClass().getClassLoader().getResource("map00.map").toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }catch (NullPointerException ignored) {}

        try {
            //mapList.getItems().add(MapModel.load(a.toURL()));
            ma = MapModel.load(a.toURL());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        URI b; try {
            b = getClass().getClassLoader().getResource("map01.map").toURI();

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        try {
            //mapList.getItems().add(MapModel.load(b.toURL()));
            mb = MapModel.load(b.toURL());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        items =FXCollections.observableArrayList (ma,mb);
        mapList.setItems(items);
    }

    /**
     * Event handler for the open button.
     * Display a file chooser, load the selected map and add to {@link this#mapList}.
     * If the map is invalid or cannot be loaded, display an error message.
     *
     * @param event Event data related to clicking the button.
     */
    @FXML
    private void onLoadMapBtnClicked(ActionEvent event) {
        // TODO
        event.getEventType();
        FileChooser fileChooser = new FileChooser();

        // set title
        fileChooser.setTitle("Select File");

        // set initial File
        fileChooser.setInitialDirectory(new File(
                "D:\\mingc\\code\\java\\pa2copy\\COMP3021-F22-PA-Student-Version-PA2\\src\\main\\resources\\"));
        File file = fileChooser.showOpenDialog(null);
//        if (file == null) throw new RuntimeException("No file given");
//        else if (!file.getPath().substring(file.getPath().length()-4).equals(".map")) {
//            throw("Invalid file");
//        }
        MapModel m;
        if (file ==null) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("No file given");
            errorAlert.showAndWait();
            return;
        }
        try {

            m = MapModel.load(file.toURI().toURL());

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("Invalid file");
            errorAlert.showAndWait();
            //throw new IllegalArgumentException("Invalid file");
            return;
        }


        for (int i = 0; i < items.size(); i++) {
            if (file.getPath().equals(items.get(i).file().toString())) {
                items.remove(i);
                break;
            }
        }
        items.add(m);
        items.sort(Comparator.comparing(MapModel::loadAt));
        mapList.setItems(items);


    }

    /**
     * Handle the event when the delete button is clicked.
     * Delete the selected map from the {@link this#mapList}.
     */
    @FXML
    public void onDeleteMapBtnClicked() {
        // TODO
        ObservableList<MapModel> selected;
        selected = mapList.getSelectionModel().getSelectedItems();
        MapModel m = selected.get(0);
        items.remove(m);
        items.sort(Comparator.comparing(MapModel::loadAt));
        mapList.setItems(items);
        mapList.getSelectionModel().select(null);
    }

    /**
     * Handle the event when the map open button is clicked.
     * Retrieve the selected map from the {@link this#mapList}.
     * Fire an {@link MapEvent} so that the {@link hk.ust.comp3021.gui.App} can handle it and switch to the game scene.
     */
    @FXML
    public void onOpenMapBtnClicked() {
        // TODO
        ObservableList<MapModel> selected;
        selected = mapList.getSelectionModel().getSelectedItems();
        MapModel m = selected.get(0);

        Event e = new MapEvent(MapEvent.OPEN_MAP_EVENT_TYPE,m);
        Event.fireEvent(openButton,e);

    }

    /**
     * Handle the event when a file is dragged over.
     *
     * @param event The drag event.
     * @see <a href="https://docs.oracle.com/javafx/2/drag_drop/jfxpub-drag_drop.htm">JavaFX Drag and Drop</a>
     */
    @FXML
    public void onDragOver(DragEvent event) {
        // TODO
        if (event.getGestureSource() != mapList
                && event.getDragboard().hasFiles()) {
            /* allow for both copying and moving, whatever user chooses */
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    /**
     * Handle the event when the map file is dragged to this app.
     * <p>
     * Multiple files should be supported.
     * Display error message if some dragged files are invalid.
     * All valid maps should be loaded.
     *
     * @param dragEvent The drag event.
     * @see <a href="https://docs.oracle.com/javafx/2/drag_drop/jfxpub-drag_drop.htm">JavaFX Drag and Drop</a>
     */
    @FXML
    public void onDragDropped(DragEvent dragEvent) {
        // TODO
        Dragboard db = dragEvent.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            for (int i = 0; i < db.getFiles().size(); i++) {

                MapModel m;
                File file = db.getFiles().get(i);
                try {

                    m = MapModel.load(file.toURI().toURL());

                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (IllegalArgumentException e) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("Input not valid");
                    errorAlert.setContentText("Invalid file");
                    errorAlert.showAndWait();
                    throw new IllegalArgumentException("Invalid file");

                }


                for (int j = 0; j < items.size(); j++) {
                    if (file.getPath().equals(items.get(j).file().toString())) {
                        items.remove(j);
                        break;
                    }
                }
                items.add(m);
                items.sort(Comparator.comparing(MapModel::loadAt));
                mapList.setItems(items);
            }
            success = true;
        }
        /* let the source know whether the string was successfully
         * transferred and used */
        dragEvent.setDropCompleted(success);

        dragEvent.consume();
    }
    /** getter of openButton
     * @return openButton
     */
    public Button getOpenButton() {
        return openButton;
    }

}
