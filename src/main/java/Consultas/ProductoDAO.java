package Consultas;

import ConexionDB.Conexion_DB;
import DBObjetos.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductoDAO {
    private static final Logger LOGGER = Logger.getLogger(ProductoDAO.class.getName());

    // Método para obtener todos los productos
    public List<Producto> getAllProductos() {
        List<Producto> productos = new ArrayList<>();
        String query = "SELECT * FROM productos";
        try (Connection connection = Conexion_DB.getConexion();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int productoID = rs.getInt("ProductoID");
                String nombre = rs.getString("Nombre");
                String descripcion = rs.getString("Descripcion");
                int areaID = rs.getInt("AreaID");
                double precio = rs.getDouble("Precio");
                int unidadesDisponibles = rs.getInt("UnidadesDisponibles");
                int nivelReorden = rs.getInt("NivelReorden");
                LocalDate fechaCaducidad = null;
                if (rs.getDate("FechaCaducidad") != null) {
                    fechaCaducidad = rs.getDate("FechaCaducidad").toLocalDate();
                }
                String codigoBarras = rs.getString("CodigoBarras");
                String tamañoNeto = rs.getString("TamañoNeto");
                String marca = rs.getString("Marca");
                String contenido = rs.getString("Contenido");

                // Si se requiere obtener el nombre del área, se puede realizar otra consulta o pasarlo como parámetro
                Producto producto = new Producto(
                        productoID, nombre, descripcion, areaID, precio,
                        unidadesDisponibles, nivelReorden, fechaCaducidad,
                        codigoBarras, tamañoNeto, marca, contenido, "", 1
                );
                productos.add(producto);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener productos", e);
        }
        return productos;
    }

    // Método para obtener un producto por su ID
    public Producto getProductoById(int id) {
        Producto producto = null;
        String query = "SELECT * FROM productos WHERE ProductoID = ?";
        try (Connection connection = Conexion_DB.getConexion();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int productoID = rs.getInt("ProductoID");
                    String nombre = rs.getString("Nombre");
                    String descripcion = rs.getString("Descripcion");
                    int areaID = rs.getInt("AreaID");
                    double precio = rs.getDouble("Precio");
                    int unidadesDisponibles = rs.getInt("UnidadesDisponibles");
                    int nivelReorden = rs.getInt("NivelReorden");
                    LocalDate fechaCaducidad = null;
                    if (rs.getDate("FechaCaducidad") != null) {
                        fechaCaducidad = rs.getDate("FechaCaducidad").toLocalDate();
                    }
                    String codigoBarras = rs.getString("CodigoBarras");
                    String tamañoNeto = rs.getString("TamañoNeto");
                    String marca = rs.getString("Marca");
                    String contenido = rs.getString("Contenido");

                    producto = new Producto(
                            productoID, nombre, descripcion, areaID, precio,
                            unidadesDisponibles, nivelReorden, fechaCaducidad,
                            codigoBarras, tamañoNeto, marca, contenido, "", 1
                    );
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener producto con ID: " + id, e);
        }
        return producto;
    }

    // Método para insertar un nuevo producto
    public boolean insertarProducto(Producto producto) {
        boolean insercionExitosa = false;
        String query = "INSERT INTO productos (Nombre, Descripcion, AreaID, Precio, UnidadesDisponibles, NivelReorden, FechaCaducidad, CodigoBarras, TamañoNeto, Marca, Contenido) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = Conexion_DB.getConexion();
             PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setInt(3, producto.getAreaID());
            ps.setDouble(4, producto.getPrecio());
            ps.setInt(5, producto.getUnidadesDisponibles());
            ps.setInt(6, producto.getNivelReorden());
            if (producto.getFechaCaducidad() != null) {
                ps.setDate(7, Date.valueOf(producto.getFechaCaducidad()));
            } else {
                ps.setNull(7, Types.DATE);
            }
            ps.setString(8, producto.getCodigoBarras());
            ps.setString(9, producto.getTamañoNeto());
            ps.setString(10, producto.getMarca());
            ps.setString(11, producto.getContenido());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        producto.setProductoID(rs.getInt(1));
                    }
                }
                insercionExitosa = true;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al insertar producto", e);
        }
        return insercionExitosa;
    }

    // Método para actualizar un producto existente
    public boolean actualizarProducto(Producto producto) {
        boolean actualizacionExitosa = false;
        String query = "UPDATE productos SET Nombre = ?, Descripcion = ?, AreaID = ?, Precio = ?, UnidadesDisponibles = ?, " +
                "NivelReorden = ?, FechaCaducidad = ?, CodigoBarras = ?, TamañoNeto = ?, Marca = ?, Contenido = ? " +
                "WHERE ProductoID = ?";
        try (Connection connection = Conexion_DB.getConexion();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setInt(3, producto.getAreaID());
            ps.setDouble(4, producto.getPrecio());
            ps.setInt(5, producto.getUnidadesDisponibles());
            ps.setInt(6, producto.getNivelReorden());
            if (producto.getFechaCaducidad() != null) {
                ps.setDate(7, Date.valueOf(producto.getFechaCaducidad()));
            } else {
                ps.setNull(7, Types.DATE);
            }
            ps.setString(8, producto.getCodigoBarras());
            ps.setString(9, producto.getTamañoNeto());
            ps.setString(10, producto.getMarca());
            ps.setString(11, producto.getContenido());
            ps.setInt(12, producto.getProductoID());

            int filasAfectadas = ps.executeUpdate();
            actualizacionExitosa = filasAfectadas > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar producto", e);
        }
        return actualizacionExitosa;
    }

    // Método para eliminar un producto por su ID
    public boolean eliminarProducto(int id) {
        boolean eliminacionExitosa = false;
        String query = "DELETE FROM productos WHERE ProductoID = ?";
        try (Connection connection = Conexion_DB.getConexion();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            eliminacionExitosa = filasAfectadas > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar producto", e);
        }
        return eliminacionExitosa;
    }
}
