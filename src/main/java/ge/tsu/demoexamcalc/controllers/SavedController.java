package ge.tsu.demoexamcalc.controllers;

import ge.tsu.demoexamcalc.App;
import ge.tsu.demoexamcalc.database.repository.SavedResultsRepository;
import ge.tsu.demoexamcalc.models.SavedResult;
import ge.tsu.demoexamcalc.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SavedController implements Initializable {
    private static final Logger log = LoggerFactory.getLogger(App.class);

    private List<SavedResult> savedResults = new ArrayList<>();
    private static User user;
    private final SavedResultsRepository savedResultsRepository = new SavedResultsRepository();
    private Alert alertAbout;

    @FXML
    protected VBox wrapper;
    @FXML
    protected Menu profile;

    public static void setUser(User user1) {
        user = user1;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        profile.setText("User : " + user.getuName());
        loadResults();
    }

    private void removeItem(String id) throws SQLException, IOException {
        savedResultsRepository.deleteByID(Integer.valueOf(id));
        HBox hBox = new HBox();
        int i = 0;
        for (SavedResult savedResult : savedResults) {
            if(savedResult.getID().equals(Integer.valueOf(id))){
             hBox = createHBox(savedResult, i);
             break;
            }
            i++;
        }
        wrapper.getChildren().remove(hBox);
        App.setRoot("Saved");
    }

    private void loadResults() {
        try {
            alertAbout = MenuController.setAlertAbout();
            savedResults = savedResultsRepository.selectAllByUser(user);
            if (savedResults.isEmpty()){
                return;
            }
            int i = 1;
            for (SavedResult savedResult : savedResults) {
                HBox hBox = createHBox(savedResult, i);
                wrapper.getChildren().add(hBox);
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HBox createHBox(SavedResult savedResult, int i) {
        HBox hBox = new HBox();
        hBox.setPrefHeight(25.0);
        hBox.setPrefWidth(200.0);
        List<Object> data = savedResult.getAll();
        VBox vBoxNum = new VBox();
        vBoxNum.setAlignment(Pos.TOP_CENTER);
        vBoxNum.setPrefWidth(25.0);
        vBoxNum.getChildren().add(new Label(String.valueOf(i)));
        hBox.getChildren().add(vBoxNum);
        for (int j = 0; j < data.size(); j++) {
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.TOP_CENTER);
            vBox.setPrefWidth(105.0);
            Label label = new Label();
            label.setText(String.valueOf(data.get(j)));
            vBox.getChildren().add(label);
            hBox.getChildren().add(vBox);
        }
        Button removeButton = new Button("Remove");
        removeButton.setPrefHeight(17);
        removeButton.setMaxHeight(17);
        removeButton.setMinHeight(17);
        removeButton.setStyle("-fx-font-size:10");
        removeButton.setId(String.valueOf(savedResult.getID()));
        removeButton.setOnAction(e -> {
            try {
                removeItem(removeButton.getId());
            } catch (SQLException | IOException ex) {
                ex.printStackTrace();
            }
        });
        VBox vboxBtn = new VBox();
        vboxBtn.setAlignment(Pos.TOP_CENTER);
        vboxBtn.setPrefWidth(105.0);
        vboxBtn.getChildren().add(removeButton);
        hBox.getChildren().add(vboxBtn);
        return hBox;
    }

    public void backToCalculator(ActionEvent event) throws IOException {
        App.setRoot("BasePoints");
    }

    public void logOut(ActionEvent event) throws IOException {
        log.info(user.getuName() + " logged out");
        App.setRoot("LogIn");
    }

    public void showAbout(ActionEvent event) {
        alertAbout.showAndWait();
    }

}
