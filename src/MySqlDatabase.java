import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlDatabase {
    public static Connection getDatabaseConnection() throws SQLException {
        Connection databaseConnection = null;
        int databasePort = Integer.parseInt(System.getenv("CS310_PORT"));
        String databaseHost = System.getenv("CS310_HOST");
        String databaseUsername = System.getenv("CS310_USERNAME");
        String databasePassword = System.getenv("CS310_PASSWORD");
        String databaseName = System.getenv("CS310_DATABASE");

        return getDatabaseConnection(databaseUsername, databasePassword, databaseHost, databasePort, databaseName);
    }

    public static Connection getDatabaseConnection(
            String username, String password, String host, int port, String databaseName) throws SQLException{
        String databaseURL = String.format(
                "jdbc:mysql://%s:%s/%s?verifyServerCertificate=false&useSSL=true&serverTimezone=UTC", host, port, databaseName);
        try{
            return DriverManager.getConnection(databaseURL, username, password);
        } catch (SQLException sqlException){
            System.out.println(String.format(
                    "SQLException was thrown while trying to connection to database: %s", databaseURL));
            System.out.println(sqlException.getMessage());
            throw sqlException;
        }
    }
}
