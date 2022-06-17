package ge.tsu.demoexamcalc.database.connection;

import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;
import java.util.Properties;

public class Database {
    private static final Connection INSTANCE = createConnection();
    private Database() {}

    private static Connection createConnection() {
        try {
            // Load DB properties
            Properties props = new Properties();
            try (var inputStream = Database.class.getResourceAsStream("/db.properties")) {
                props.load(inputStream);
            }
            JdbcDataSource dataSource = new JdbcDataSource();
            dataSource.setURL(props.getProperty("db.url"));
            dataSource.setUser(props.getProperty("db.username"));
            dataSource.setPassword(props.getProperty("db.password"));
            return dataSource.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return INSTANCE;
    }
}
