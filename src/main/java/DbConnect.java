import java.sql.*;

/**
 * Created by hamza on 25/11/16.
 */
public class DbConnect {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/RI1";

    //  Database credentials
    static final String USER = "RI";
    static final String PASS = "RI";

    /**
     * Returns a connection object, connected to the specified credentials
     * @return connection object
     */
    public Connection getConnection() {
        Connection conn = null;
        Statement stmt = null;
        try {
            // Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            return conn;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
