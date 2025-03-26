package Bridge;

import DBObjetos.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

class ProductoOraclePersistencia implements ProductoPersistencia {
    private static final Logger LOGGER = Logger.getLogger(ProductoOraclePersistencia.class.getName());

    @Override
    public boolean guardar(Producto producto) {
        String query = "INSERT INTO productos " +
                "(Nombre, Descripcion, AreaID, Precio, UnidadesDisponibles, " +
                "NivelReorden, FechaCaducidad, CodigoBarras, \"TamañoNeto\", Marca, Contenido) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexion = Conexion_Oracle.getConexion();
             PreparedStatement stmt = conexion.prepareStatement(query, new String[]{"ProductoID"})) {

            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setInt(3, producto.getAreaID());
            stmt.setDouble(4, producto.getPrecio());
            stmt.setInt(5, producto.getUnidadesDisponibles());
            stmt.setInt(6, producto.getNivelReorden());
            stmt.setDate(7, producto.getFechaCaducidad() != null
                    ? Date.valueOf(producto.getFechaCaducidad())
                    : null);
            stmt.setString(8, producto.getCodigoBarras());
            stmt.setString(9, producto.getTamañoNeto());
            stmt.setString(10, producto.getMarca());
            stmt.setString(11, producto.getContenido());

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        // Use getString to avoid conversion issues
                        String generatedIdStr = generatedKeys.getString(1);
                        if (generatedIdStr != null) {
                            int generatedId = Integer.parseInt(generatedIdStr);
                            producto.setProductoID(generatedId);
                            System.out.println("Producto guardado con ID: " + generatedId);
                        }
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al guardar producto en Oracle", e);
            // Detailed error logging
            System.err.println("Error Details:");
            System.err.println("Message: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Producto obtenerPorId(int id) {
        String query = "SELECT p.*, a.Nombre AS NombreArea " +
                "FROM productos p " +
                "LEFT JOIN area a ON p.AreaID = a.AreaID " +
                "WHERE p.ProductoID = ?";

        try (Connection conexion = Conexion_Oracle.getConexion();
             PreparedStatement stmt = conexion.prepareStatement(query)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Producto.ProductoBuilder()
                            .productoID(rs.getInt("ProductoID"))
                            .nombre(rs.getString("Nombre"))
                            .descripcion(rs.getString("Descripcion"))
                            .areaID(rs.getInt("AreaID"))
                            .precio(rs.getDouble("Precio"))
                            .unidadesDisponibles(rs.getInt("UnidadesDisponibles"))
                            .nivelReorden(rs.getInt("NivelReorden"))
                            .fechaCaducidad(rs.getDate("FechaCaducidad") != null
                                    ? rs.getDate("FechaCaducidad").toLocalDate()
                                    : null)
                            .codigoBarras(rs.getString("CodigoBarras"))
                            .tamañoNeto(rs.getString("TamañoNeto"))
                            .marca(rs.getString("Marca"))
                            .contenido(rs.getString("Contenido"))
                            .nombreArea(rs.getString("NombreArea"))
                            .build();
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener producto por ID en Oracle", e);
        }
        return null;
    }

    // Other methods remain the same as in the previous implementation
    @Override
    public List<Producto> obtenerTodos() {
        List<Producto> productos = new ArrayList<>();
        String query = "SELECT p.*, a.Nombre AS NombreArea " +
                "FROM productos p " +
                "LEFT JOIN area a ON p.AreaID = a.AreaID";

        try (Connection conexion = Conexion_Oracle.getConexion();
             Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Producto producto = new Producto.ProductoBuilder()
                        .productoID(rs.getInt("ProductoID"))
                        .nombre(rs.getString("Nombre"))
                        .descripcion(rs.getString("Descripcion"))
                        .areaID(rs.getInt("AreaID"))
                        .precio(rs.getDouble("Precio"))
                        .unidadesDisponibles(rs.getInt("UnidadesDisponibles"))
                        .nivelReorden(rs.getInt("NivelReorden"))
                        .fechaCaducidad(rs.getDate("FechaCaducidad") != null
                                ? rs.getDate("FechaCaducidad").toLocalDate()
                                : null)
                        .codigoBarras(rs.getString("CodigoBarras"))
                        .tamañoNeto(rs.getString("TamañoNeto"))
                        .marca(rs.getString("Marca"))
                        .contenido(rs.getString("Contenido"))
                        .nombreArea(rs.getString("NombreArea"))
                        .build();

                productos.add(producto);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener todos los productos en Oracle", e);
        }

        return productos;
    }

    @Override
    public boolean actualizar(Producto producto) {
        String query = "UPDATE productos SET " +
                "Nombre = ?, Descripcion = ?, AreaID = ?, Precio = ?, " +
                "UnidadesDisponibles = ?, NivelReorden = ?, FechaCaducidad = ?, " +
                "CodigoBarras = ?, \"TamañoNeto\" = ?, Marca = ?, Contenido = ? " +
                "WHERE ProductoID = ?";

        try (Connection conexion = Conexion_Oracle.getConexion();
             PreparedStatement stmt = conexion.prepareStatement(query)) {

            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setInt(3, producto.getAreaID());
            stmt.setDouble(4, producto.getPrecio());
            stmt.setInt(5, producto.getUnidadesDisponibles());
            stmt.setInt(6, producto.getNivelReorden());
            stmt.setDate(7, producto.getFechaCaducidad() != null
                    ? Date.valueOf(producto.getFechaCaducidad())
                    : null);
            stmt.setString(8, producto.getCodigoBarras());
            stmt.setString(9, producto.getTamañoNeto());
            stmt.setString(10, producto.getMarca());
            stmt.setString(11, producto.getContenido());
            stmt.setInt(12, producto.getProductoID());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar producto en Oracle", e);
        }
        return false;
    }

    @Override
    public boolean eliminar(int id) {
        String query = "DELETE FROM productos WHERE ProductoID = ?";
        try (Connection conexion = Conexion_Oracle.getConexion();
             PreparedStatement stmt = conexion.prepareStatement(query)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar producto en Oracle", e);
        }
        return false;
    }
}