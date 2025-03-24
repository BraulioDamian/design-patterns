package Consultas;

import ConexionDB.Conexion_DB;
import DBObjetos.Area;
import DBObjetos.Producto;
import Composite.ComponenteInventario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AreaDAO {
    private static final Logger LOGGER = Logger.getLogger(AreaDAO.class.getName());

    // Obtener todas las áreas con productos
    public List<ComponenteInventario> getAreasConProductos() {
        Map<Integer, Area> areasMap = new HashMap<>();
        List<ComponenteInventario> areasRaiz = new ArrayList<>();

        Connection connection = null;
        try {
            connection = Conexion_DB.getConexion();

            // Primero, obtener todas las áreas
            String areaQuery = "SELECT AreaID, Nombre, PadreAreaID FROM area";
            try (PreparedStatement ps = connection.prepareStatement(areaQuery);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int areaID = rs.getInt("AreaID");
                    String nombre = rs.getString("Nombre");
                    Integer padreAreaID = rs.getObject("PadreAreaID", Integer.class);

                    Area area = new Area(areaID, nombre, ""); // La descripción no se persiste
                    area.setPadreAreaID(padreAreaID);
                    areasMap.put(areaID, area);
                }
            }

            // Obtener productos para cada área
            String productosQuery = "SELECT * FROM productos";
            try (PreparedStatement ps = connection.prepareStatement(productosQuery);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int productoID = rs.getInt("ProductoID");
                    String nombre = rs.getString("Nombre");
                    String descripcion = rs.getString("Descripcion");
                    int areaID = rs.getInt("AreaID");
                    double precio = rs.getDouble("Precio");
                    int unidadesDisponibles = rs.getInt("UnidadesDisponibles");
                    int nivelReorden = rs.getInt("NivelReorden");
                    LocalDate fechaCaducidad = rs.getDate("FechaCaducidad") != null ?
                            rs.getDate("FechaCaducidad").toLocalDate() : null;
                    String codigoBarras = rs.getString("CodigoBarras");
                    String tamañoNeto = rs.getString("TamañoNeto");
                    String marca = rs.getString("Marca");
                    String contenido = rs.getString("Contenido");

                    // Verificar que el área existe antes de usarla
                    if (areasMap.containsKey(areaID)) {
                        String nombreArea = areasMap.get(areaID).getNombreArea();
                        Producto producto = new Producto(
                                productoID, nombre, descripcion, areaID,
                                precio, unidadesDisponibles, nivelReorden,
                                fechaCaducidad, codigoBarras, tamañoNeto,
                                marca, contenido, nombreArea, 1
                        );
                        areasMap.get(areaID).agregar(producto);
                    }
                }
            }

            // Configurar la jerarquía de áreas (estructura de árbol)
            for (Area area : areasMap.values()) {
                Integer padreID = area.getPadreAreaID();
                if (padreID == null) {
                    areasRaiz.add(area);
                } else if (areasMap.containsKey(padreID)) {
                    areasMap.get(padreID).agregar(area);
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener áreas y productos", e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOGGER.log(Level.WARNING, "Error al cerrar la conexión", e);
                }
            }
        }
        return areasRaiz;
    }

    // Método para insertar un área en la base de datos
    public boolean insertarArea(Area area) {
        boolean insertado = false;
        String query = "INSERT INTO area (Nombre, GananciaPorcentaje, PadreAreaID) VALUES (?, ?, ?)";
        Connection connection = null;
        try {
            connection = Conexion_DB.getConexion();
            try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, area.getNombreArea());
                // Se asigna un valor por defecto para GananciaPorcentaje (puedes modificarlo según tu lógica)
                ps.setDouble(2, 0.0);
                if (area.getPadreAreaID() == null) {
                    ps.setNull(3, java.sql.Types.INTEGER);
                } else {
                    ps.setInt(3, area.getPadreAreaID());
                }
                int filasAfectadas = ps.executeUpdate();
                if (filasAfectadas > 0) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            area.setAreaID(rs.getInt(1));
                        }
                    }
                    insertado = true;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al insertar área", e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOGGER.log(Level.WARNING, "Error al cerrar la conexión", e);
                }
            }
        }
        return insertado;
    }

    // Método para eliminar un área y sus productos asociados usando el nombre del área
    public boolean eliminarAreaPorNombre(String nombreArea) {
        boolean eliminado = false;
        Connection connection = null;

        try {
            connection = Conexion_DB.getConexion();
            connection.setAutoCommit(false);

            // Verificar si el área existe
            int areaID = -1;
            String selectAreaQuery = "SELECT AreaID FROM area WHERE Nombre = ?";
            try (PreparedStatement ps = connection.prepareStatement(selectAreaQuery)) {
                ps.setString(1, nombreArea);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        areaID = rs.getInt("AreaID");
                        System.out.println("Área encontrada con ID: " + areaID);
                    } else {
                        System.out.println("No se encontró el área con el nombre: " + nombreArea);
                        return false;
                    }
                }
            }

            // Verificar si hay productos asociados
            String countProductsQuery = "SELECT COUNT(*) FROM productos WHERE AreaID = ?";
            try (PreparedStatement ps = connection.prepareStatement(countProductsQuery)) {
                ps.setInt(1, areaID);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        System.out.println("Se encontraron " + rs.getInt(1) + " productos en el área.");
                    } else {
                        System.out.println("No hay productos en el área.");
                    }
                }
            }

            // Eliminar productos del área
            String deleteProductsQuery = "DELETE FROM productos WHERE AreaID = ?";
            try (PreparedStatement ps = connection.prepareStatement(deleteProductsQuery)) {
                ps.setInt(1, areaID);
                int productosEliminados = ps.executeUpdate();
                System.out.println("Productos eliminados: " + productosEliminados);
            }

            // Eliminar el área
            String deleteAreaQuery = "DELETE FROM area WHERE AreaID = ?";
            try (PreparedStatement ps = connection.prepareStatement(deleteAreaQuery)) {
                ps.setInt(1, areaID);
                int filasAfectadas = ps.executeUpdate();
                eliminado = filasAfectadas > 0;
                System.out.println("Área eliminada: " + (eliminado ? "Sí" : "No"));
            }

            connection.commit();
        } catch (SQLException e) {
            System.err.println("Error al eliminar el área y sus productos: " + e.getMessage());
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    System.err.println("Error al hacer rollback: " + ex.getMessage());
                }
            }
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        }
        return eliminado;
    }

}
