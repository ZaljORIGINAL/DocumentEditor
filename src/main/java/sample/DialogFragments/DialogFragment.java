package sample.DialogFragments;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

public interface DialogFragment extends Initializable {
    FXMLLoader initFragmentView();

    Pane getMainPanel();

    String getPathToFXML();
}
