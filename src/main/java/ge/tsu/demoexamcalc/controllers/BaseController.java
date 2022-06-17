package ge.tsu.demoexamcalc.controllers;

import ge.tsu.demoexamcalc.App;
import ge.tsu.demoexamcalc.models.SavedResult;
import ge.tsu.demoexamcalc.models.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class BaseController implements Initializable {
    private static final Logger log = LoggerFactory.getLogger(App.class);

    private SavedResult savedResult = new SavedResult();
    protected static User user;
    protected Alert alertAbout;

    @FXML
    protected Menu profile;
    @FXML
    protected TextField geo;
    @FXML
    protected TextField foreign;
    @FXML
    protected ChoiceBox choice;
    @FXML
    protected TextField chosenSubject;
    @FXML
    protected Label result;

    public static void setUser(User user1) {
        user = user1;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choice.getItems().add("Math");
        choice.getItems().add("History");
        choice.setValue("Math");

        onlyNumberFields();

        profile.setText("User : " + user.getuName());

        try{
            alertAbout = MenuController.setAlertAbout();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onCalculateClick(ActionEvent event) {
        if (checkNull(geo.getText()) ||
                checkNull(choice.getValue()) ||
                checkNull(foreign.getText()) ||
                checkNull(chosenSubject.getText())){
            result.setText("Please fill every field");
            return;
        }
        getPoints();
        if (checkMaxPoints(savedResult.getGeoP(), savedResult.getfLangP(), savedResult.getThirdSubject(), savedResult.getThirdSubjectP())){
            result.setText("Please provide valid points");
            return;
        }
        result.setText(calculatePoints(savedResult.getGeoP(), savedResult.getfLangP(), savedResult.getThirdSubject(), savedResult.getThirdSubjectP()));
    }


    private String calculatePoints(double geoP, double foreignP, String chosenSub, double chosenSubP) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        double geoScaledP = 15 * ((geoP - 38.99) / 14.77) + 150;
        double foreignScaledP = 15 * ((foreignP - 44.52) / 22.01) + 150;
        double chosenSubScaledP = 0;

        if (chosenSub.equals("Math")) {
            chosenSubScaledP = 15 * ((chosenSubP - 22.18) / 13.49) + 150;
        }
        if (chosenSub.equals("History")){
            chosenSubScaledP = 15 * ((chosenSubP - 32.01) / 14.45) + 150;
        }
        savedResult.setGrant(calculateGrant(geoScaledP, foreignScaledP, chosenSubScaledP, df));

        String resultText = "Georgian scaled point: " + df.format(geoScaledP) + "\n"
                            + "Foreign Language scaled point: " + df.format(foreignScaledP) +"\n"
                            + chosenSub + " scaled point: " + df.format(chosenSubScaledP) + "\n"
                            + "Grant: " + savedResult.getGrant() + "%";
        return resultText;
    }

    private int calculateGrant(double geoScaledP, double foreignScaledP, double chosenSubScaledP, DecimalFormat df) {
        double res = (geoScaledP + foreignScaledP + 1.5 * chosenSubScaledP) * 10;
        savedResult.setCalculated((double) Math.round(res));
        if (savedResult.getCalculated() > 6100.5) {
            return 100;
        }
        if (savedResult.getCalculated() > 5950.5) {
            return 70;
        }
        if (savedResult.getCalculated() > 5690.5) {
            return 50;
        }
        return 0;
    }

    public void onResetClick(ActionEvent event) {
        geo.setText("");
        foreign.setText("");
        chosenSubject.setText("");
        result.setText("");
    }

    public void onScaledClick(ActionEvent event) throws IOException {
        ScaledController.setUser(user);
        App.setRoot("ScaledPoints");
    }

    public void saveResult(ActionEvent event) throws SQLException, NoSuchFieldException, IllegalAccessException {
        MenuController.saveResult(savedResult, user);
    }

    public void logOut(ActionEvent event) throws IOException {
        log.info(user.getuName() + " logged out");
        App.setRoot("LogIn");
    }

    public void savedResults(ActionEvent event) throws IOException {
        SavedController.setUser(user);
        App.setRoot("Saved");
    }

    public void showAbout(ActionEvent event) {
        alertAbout.showAndWait();
    }

    protected boolean checkNull(Object data){
        return data == "";
    }
    private boolean checkMaxPoints(double geoP, double foreignP,String chosenSub, double chosenSubP) {
        if (geoP > 70 || foreignP > 80){
            return true;
        }
        if (chosenSub.equals("Math") && chosenSubP > 51){
            return true;
        }
        return chosenSubP > 60;
    }
    private SavedResult getPoints(){
        savedResult.setGeoP(Double.parseDouble(geo.getText()));
        savedResult.setfLangP(Double.parseDouble(foreign.getText()));
        savedResult.setThirdSubject(String.valueOf(choice.getValue()));
        savedResult.setThirdSubjectP(Double.parseDouble(chosenSubject.getText()));
        return savedResult;
    }

    private void onlyNumberFields() {
        geo.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    geo.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        foreign.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    geo.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        chosenSubject.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    geo.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

}
