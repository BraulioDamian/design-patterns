package Bridge;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion_Oracle {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:CFE"; // Adjust as needed
    private static final String USUARIO = "C##admin"; // Replace with your Oracle username
    private static final String CONTRASENA = "123456"; // Replace with your Oracle password
    private static final Logger LOGGER = Logger.getLogger(Conexion_Oracle.class.getName());

    static {
        try {
            // Load Oracle driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Could not load Oracle driver", e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Connection getConexion() throws SQLException {
        try {
            Connection conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            System.out.println("Database connection successful!");
            return conexion;
        } catch (SQLException e) {
            System.err.println("Connection Error Details:");
            System.err.println("URL: " + URL);
            System.err.println("Username: " + USUARIO);
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("SQL State: " + e.getSQLState());
            LOGGER.log(Level.SEVERE, "Database connection failed", e);
            throw e;
        }
    }

    public static void main(String[] args) {
        try {
            // Test connection
            Connection conexion = Conexion_Oracle.getConexion();
            System.out.println("Connection Details:");
            System.out.println("Catalog: " + conexion.getCatalog());
            System.out.println("Auto Commit: " + conexion.getAutoCommit());
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}