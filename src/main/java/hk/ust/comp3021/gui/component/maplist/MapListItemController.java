package hk.ust.comp3021.gui.component.maplist;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Control logic for {@link MapListItem}.
 */
public class MapListItemController implements Initializable {
    @FXML
    private GridPane item;

    @FXML
    private Label mapName;

    @FXML
    private Label loadAt;

    @FXML
    private Label mapFilePath;

    private final Property<MapModel> mapModelProperty = new SimpleObjectProperty<>();

    /**
     * Initialize the controller as you need.
     * You should update the displayed information for the list item when the map model changes.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
//        int i=StartController.count;
//        int j = StartController.mnum;


        mapModelProperty.addListener((ChangeListener) (o, oldVal, newVal) -> {
            mapName.setText(mapModelProperty.getValue().name());
            loadAt.setText(mapModelProperty.getValue().loadAt().toString());
            mapFilePath.setText(mapModelProperty.getValue().file().toString());
        });


//            mapName.setText(mapModelProperty.getValue().name());
//            loadAt.setText(mapModelProperty.getValue().loadAt().toString());
//            mapFilePath.setText(mapModelProperty.getValue().file().toString());
//        System.out.print(StartController.count);
//        if (StartController.count!=)
//        StartController.count++;

//        mapModelProperty.addListener(new ChangeListener<MapModel>() {
//            @Override
//            public void changed(ObservableValue<? extends MapModel> observable, MapModel oldValue, MapModel newValue) {
//
//            }
//        });
    }

    /**
     * @return The property for the map model.
     */
    public Property<MapModel> getMapModelProperty() {
        return mapModelProperty;
    }
}
