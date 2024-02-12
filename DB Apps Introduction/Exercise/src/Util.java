import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    public static Connection getConnection() throws SQLException {
        Properties properties = new Properties();

        properties.setProperty(Constants.USER_KEY,Constants.USER_VALUE);
        properties.setProperty(Constants.PASSWORD_KEY,Constants.PASSWORD_VALUE);

        return DriverManager
                .getConnection("jdbc:mysql://localhost:3306/minions_db",properties);
    }
}
