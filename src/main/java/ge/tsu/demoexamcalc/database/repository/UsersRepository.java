package ge.tsu.demoexamcalc.database.repository;

import ge.tsu.demoexamcalc.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersRepository extends AbstractRepository <User> {

    public UsersRepository() {
        super("USERS", User.class);
    }
    public User findByUname (String uName) throws SQLException {
        User user = new User();
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM USERS WHERE U_NAME=?");
        preparedStatement.setObject(1, uName);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            user.setID(resultSet.getObject("ID", Integer.class));
            user.setfName(resultSet.getObject("F_NAME", String.class));
            user.setlName(resultSet.getObject("L_NAME", String.class));
            user.setEmail(resultSet.getObject("EMAIL", String.class));
            user.setuName(resultSet.getObject("U_NAME", String.class));
            user.setPwd(resultSet.getObject("PWD", String.class));
            return user;
        }
        return null;
    }
}
