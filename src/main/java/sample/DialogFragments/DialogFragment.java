package sample.DialogFragments;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.io.IOException;

public interface DialogFragment extends Initializable {
    FXMLLoader initFragmentView() throws IOException;

    Pane getMainPanel();

    String getPathToFXML();
}
