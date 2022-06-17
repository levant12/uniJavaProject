package ge.tsu.demoexamcalc.controllers;

import ge.tsu.demoexamcalc.App;
import ge.tsu.demoexamcalc.database.repository.SavedResultsRepository;
import ge.tsu.demoexamcalc.models.SavedResult;
import ge.tsu.demoexamcalc.models.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;

public class MenuController {
    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static Alert setAlertAbout() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("About.fxml"));

        Alert alertAbout = new Alert(Alert.AlertType.INFORMATION);
        DialogPane aboutDialogPane = new DialogPane();
        aboutDialogPane.setContent(fxmlLoader.load());
        aboutDialogPane.getButtonTypes().addAll(ButtonType.CLOSE);
        alertAbout.setDialogPane(aboutDialogPane);
        alertAbout.setTitle("About Calculator");
        return alertAbout;
    }

    public static int saveResult(SavedResult savedResult, User user) throws SQLException, NoSuchFieldException, IllegalAccessException {
        savedResult.setUserID(user.getID());
        SavedResultsRepository savedResultsRepository = new SavedResultsRepository();
        int i = savedResultsRepository.insertInto(savedResult);
        log.info("New result saved for user: " + user.getuName());
        return i;
    }

}
