package ge.tsu.demoexamcalc.database.repository;

import ge.tsu.demoexamcalc.annotation.ColumnName;
import ge.tsu.demoexamcalc.annotation.Identifier;
import ge.tsu.demoexamcalc.database.connection.Database;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AbstractRepository<DATA> {

    protected final Connection conn = Database.getConnection();
    protected final String tableName;
    private final Class<DATA> dataClass;
    private final Constructor<DATA> dataConstructor;
    protected final Map<Field, String> dataFields;

    public AbstractRepository(String tableName, Class<DATA> dataClass) {
        try {
            this.tableName = tableName;
            this.dataClass = dataClass;
            this.dataConstructor = dataClass.getConstructor();
            this.dataFields = new HashMap<>();
            Field[] declaredFields = dataClass.getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);

                String columnName = field.getName();
                if (field.isAnnotationPresent(ColumnName.class)) {
                    columnName = field.getAnnotation(ColumnName.class).value();
                }
                dataFields.put(field, columnName);
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public List<DATA> selectAll() throws SQLException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<DATA> result = new ArrayList<>();
        String query = "SELECT * FROM " + tableName;
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            DATA data = dataConstructor.newInstance();
            for (Field field : dataFields.keySet()) {
                field.set(data, resultSet.getObject(dataFields.get(field), field.getType()));
            }
            result.add(data);
        }
        return result;
    }

    public DATA findByID(Integer id) throws Exception {
        validateNotNull(id);
        DATA result = dataConstructor.newInstance();
        String query = "SELECT * FROM " + tableName +"WHERE ID=?;";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setObject(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            for (Field field : dataFields.keySet()) {
                field.set(result, resultSet.getObject(dataFields.get(field), field.getType()));
            }
        }else {
            throw new Exception("Nothing found with ID = " + id);
        }
        return result;
    }

    public int insertInto (DATA data) throws SQLException, NoSuchFieldException, IllegalAccessException {
        validateNotNull(data);
        String query = createInsertQuery();
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        int columnIndex = 1;
        for (Field field : dataFields.keySet()) {
            if (field.isAnnotationPresent(Identifier.class)) {
                continue;
            }
            preparedStatement.setObject(columnIndex++, field.get(data));
        }
        return preparedStatement.executeUpdate();
    }

    public void deleteByID(Integer id) throws SQLException {
        validateNotNull(id);
        String query = "DELETE FROM " + tableName + " WHERE ID=?;";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setObject(1,id);
        preparedStatement.executeUpdate();
    }

    public int update(DATA data) throws SQLException, IllegalAccessException {
        validateNotNull(data);
        String query = createUpdateQuery();
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        int columnIndex = 1;
        Map.Entry<Field, String> identifierField = null;
        for (Field field : dataFields.keySet()) {
            if (field.isAnnotationPresent(Identifier.class)) {
                identifierField = new AbstractMap.SimpleEntry<>(field, dataFields.get(field));
                continue;
            }
            preparedStatement.setObject(columnIndex++, field.get(data));
        }
        preparedStatement.setObject(columnIndex, identifierField.getKey().get(data));
        return preparedStatement.executeUpdate();
    }

    protected void validateNotNull(Object obj) {
        if(obj == null)
            throw new IllegalArgumentException(String.format("%s can not be null", obj));
    }

    protected String createInsertQuery() throws NoSuchFieldException {
        StringBuilder sb = new StringBuilder("INSERT INTO ").append(tableName);
        StringJoiner stringJoiner1 = new StringJoiner(", ", "(", ")");
        int processedColumnAmount = 0;
        for (Field field : dataFields.keySet()) {
            if (field.isAnnotationPresent(Identifier.class)) {
                continue;
            }
            stringJoiner1.add(dataFields.get(field));
            processedColumnAmount += 1;
        }
        sb.append(stringJoiner1).append(" VALUES");
        StringJoiner stringJoiner2 = new StringJoiner(", ", "(", ")");
        for (int i = 0; i < processedColumnAmount; i++) {
            stringJoiner2.add("?");
        }
        sb.append(stringJoiner2).append(';');
        return sb.toString();
    }

    protected String createUpdateQuery() {
        StringBuilder sb = new StringBuilder("UPDATE ").append(tableName).append(" SET ");
        Map.Entry<Field, String> identifierField = null;
        int index = 0, extraEntry = 0;
        for (Field field : dataFields.keySet()) {
            if (field.isAnnotationPresent(Identifier.class)) {
                identifierField = new AbstractMap.SimpleEntry<>(field, dataFields.get(field));
                extraEntry++;
                continue;
            }
            sb.append(dataFields.get(field)).append("=?");
            if (index < dataFields.size() - extraEntry - 1 && index < dataFields.size() - 2) {
                sb.append(", ");
            }
            index += 1;
        }
        if (identifierField == null) {
            throw new IllegalStateException("No identifier present");
        }
        sb.append(" WHERE ").append(identifierField.getValue()).append("=?").append(';');
        return sb.toString();
    }

}
