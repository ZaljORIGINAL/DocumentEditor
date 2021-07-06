package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sample.MainWindow.MainWindow;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.logging.LogManager;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Загрузка параметрво логирования
        /*FIXME НЕзнаю как обработать ошибку при отсутвии фалйа настроек логирование...
        *  Куда выдать эту ошибку? Если путь для записи логов в в ФАЙЛЕ КОТОРЫЙ НЕ УДАЛОСЬ НАЙТИ
        *  Другие ошибки так же не исправими. Кроме как в момент исполнения создать
        *  экстренный фаил настроек и передать его.*/
        ApplicationContext context = new AnnotationConfigApplicationContext();
        FileInputStream file = new FileInputStream(
                context.getResource("logger.properties").getFile());
        LogManager.getLogManager().readConfiguration(file);

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
