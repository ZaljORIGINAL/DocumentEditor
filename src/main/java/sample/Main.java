package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sample.MainWindow.MainWindow;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        URL pathToMainFormFxml =
                getClass().getResource("/forms/form_main_window.fxml");
        FXMLLoader loader = new FXMLLoader(pathToMainFormFxml);
        loader.setControllerFactory(
                e -> new MainWindow(primaryStage));
        Parent root = loader.load();
        primaryStage.setTitle("Редактор документов");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
