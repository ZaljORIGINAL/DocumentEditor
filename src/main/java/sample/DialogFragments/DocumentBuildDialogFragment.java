package sample.DialogFragments;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import sample.Commands.Actions.DocumentCreated;

import java.io.File;

public abstract class DocumentBuildDialogFragment implements DialogFragment {
    public Pane mainPane;
    public Button pathSelectButton;
    protected String pathToDir;
    protected Stage primaryStage;
    protected PathSelected callBack;

    public DocumentBuildDialogFragment(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    @Override
    public FXMLLoader initFragmentView(){
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
    public Pane getMainPanel(){
        return mainPane;
    }

    public void setCallBack(PathSelected callBack){
        this.callBack = callBack;
    }

    public void clickSelectPath(ActionEvent actionEvent){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDir = directoryChooser.showDialog(primaryStage);
        if (selectedDir != null){
            String path = selectedDir.getPath();
            setPathToDir(path);
        }
    }

    public abstract void clickPositiveButton();

    public void clickNegativeButton(ActionEvent actionEvent){
    }

    public abstract DocumentCreated buildDocument();

    private void setPathToDir(String path){
        pathToDir = path;
        callBack.notif();
    }
}