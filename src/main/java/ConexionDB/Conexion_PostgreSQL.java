package ConexionDB;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion_PostgreSQL {
    private static final String URL = "jdbc:postgresql://localhost:5432/dbtienda";
    private static final String USUARIO = "postgres";
    private static final String CONTRASENA = "tu_contraseña";

    private static final Logger LOGGER = Logger.getLogger(Conexion_PostgreSQL.class.getName());

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "No se pudo cargar el driver de PostgreSQL", e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Connection getConexion() throws SQLException {
        int retries = 5;
        while (retries > 0) {
            try {
                return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Reintentando conexión... (" + retries + " intentos restantes)");
                retries--;
                try { Thread.sleep(5000); } catch (InterruptedException ie) { }
            }
        }
        throw new SQLException("No se pudo conectar a la base de datos PostgreSQL");
    }
}
