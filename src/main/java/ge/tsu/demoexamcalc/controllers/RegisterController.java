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

public class RegisterController {
    private static final Logger log = LoggerFactory.getLogger(App.class);

    @FXML
    protected TextField fName;
    @FXML
    protected TextField lName;
    @FXML
    protected TextField email;
    @FXML
    protected TextField uName;
    @FXML
    protected PasswordField pwd;
    @FXML
    protected PasswordField confirmPwd;
    @FXML
    protected Label warning;

    public void onRegisterClick(ActionEvent event) throws SQLException, NoSuchFieldException, IllegalAccessException, IOException {
        if (checkNull(fName.getText()) ||
                checkNull(lName.getText()) ||
                checkNull(uName.getText()) ||
                checkNull(email.getText()) ||
                checkNull(pwd.getText()) ||
                checkNull(confirmPwd.getText())) {
            warning.setText("Please fill every field");
            return;
        }
        if (checkUserName(uName.getText())){
            warning.setText("Username is already in use");
            return;
        }
        if (checkEmail(email.getText())){
            warning.setText("Please provide valid Email");
            return;
        }
        if (checkpwd(pwd.getText())){
            warning.setText("Password should be at least 8 characters");
            return;
        }
        if (!pwd.getText().equals(confirmPwd.getText())){
            warning.setText("Passwords did not match");
            return;
        }
        UsersRepository usersRepository = new UsersRepository();
        User user = new User();
        user.setfName(fName.getText());
        user.setlName(lName.getText());
        user.setuName(uName.getText());
        user.setEmail(email.getText());
        user.setPwd(pwd.getText());
        usersRepository.insertInto(user);
        log.info("New user registered");
        App.setRoot("LogIn");
    }

    private boolean checkpwd(String pwd) {
        return pwd.length() < 8;
    }

    private boolean checkEmail(String email) {
        return !email.contains("@") || !email.contains(".");
    }

    private boolean checkUserName(String uname) throws SQLException {
        UsersRepository usersRepository = new UsersRepository();
        return usersRepository.findByUname(uname) != null;
    }

    protected boolean checkNull(Object data){
        return data == "";
    }
}
