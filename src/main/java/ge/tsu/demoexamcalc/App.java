package ge.tsu.demoexamcalc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class App extends Application {
    private static final Logger log = LoggerFactory.getLogger(App.class);


    private static Scene scene;
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFxml("LogIn").load());
        stage.setTitle("National Exam Calculator");
        stage.getIcons().add(new Image(App.class.getResource("icon.png").toExternalForm()));
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        FXMLLoader fxmlLoader = loadFxml(fxml);
        scene.setRoot(fxmlLoader.load());
    }
    private static FXMLLoader loadFxml(String fxml){
        return new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    }

    public static void main(String[] args) {
        log.info("Application started");
        launch();
        log.info("Application stopped");
    }
}