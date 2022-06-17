package ge.tsu.demoexamcalc.database.repository;

import ge.tsu.demoexamcalc.models.SavedResult;
import ge.tsu.demoexamcalc.models.User;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SavedResultsRepository extends AbstractRepository<SavedResult>{
    public SavedResultsRepository() {
        super("RESULTS", SavedResult.class);
    }
    public List<SavedResult> selectAllByUser(User user) throws SQLException, IllegalAccessException {
        List<SavedResult> result = new ArrayList<>();
        String query = "SELECT * FROM " + tableName + " WHERE USER_ID=?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setObject(1,user.getID());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            SavedResult data = new SavedResult();
            for (Field field : dataFields.keySet()) {
                field.set(data, resultSet.getObject(dataFields.get(field), field.getType()));
            }
            result.add(data);
        }
        return result;
    }
}
