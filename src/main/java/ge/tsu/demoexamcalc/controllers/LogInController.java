package ge.tsu.demoexamcalc.controllers;

import ge.tsu.demoexamcalc.App;
import ge.tsu.demoexamcalc.database.repository.UsersRepository;
import ge.tsu.demoexamcalc.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;

public class LogInController {
    private static final Logger log = LoggerFactory.getLogger(App.class);
    protected UsersRepository usersRepository = new UsersRepository();

    @FXML
    protected TextField Uname;
    @FXML
    protected PasswordField pwd;
    @FXML
    protected Label warning;

    public void onLogInClick(ActionEvent event) throws SQLException, IOException {
        if (Uname.getText().equals("") || this.pwd.getText().equals("")){
            warning.setText("Please fill every field");
            return;
        }
        String uName = Uname.getText();
        String pwd = this.pwd.getText();
        User user = usersRepository.findByUname(uName);
        if (user == null){
            warning.setText("Wrong username");
            return;
        }
        if (user.checkPwd(pwd)){
            log.info("User logged in");
            BaseController.setUser(user);
            App.setRoot("BasePoints");
        }
        else {
            warning.setText("Wrong password");
        }
    }

    public void onRegisterClick(ActionEvent event) throws IOException {
        App.setRoot("Register");
    }

}