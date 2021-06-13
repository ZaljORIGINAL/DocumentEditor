package sample.DialogFragments;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import sample.Documents.Document;
import sample.Documents.Resource;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShowerDialogFragment implements DialogFragment{

    public AnchorPane mainPane;
    public TextArea infoField;
    private Document document;

    public ShowerDialogFragment(Document document){
        this.document = document;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            document.readFile();

            Resource resource = document.getResource();
            String info = resource.toString();
            infoField.setText(info);
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }

    @Override
    public FXMLLoader initFragmentView() {
        String pathToFXML = getPathToFXML();
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(
                    getClass().getResource(pathToFXML));
            loader.setControllerFactory(
                    c -> this);
            loader.load();
        }catch (Exception e){
            System.out.println(e.getMessage());
            loader = null;
        }

        return loader;
    }

    @Override
    public Pane getMainPanel() {
        return mainPane;
    }

    @Override
    public String getPathToFXML() {
        String pathToFXML = "/fragments/fragment_dialog_document_shower.fxml";
        return pathToFXML;
    }
}
