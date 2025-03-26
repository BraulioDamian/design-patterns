package DBObjetos;

import ConexionDB.Conexion_DB;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductoDAO {
    private static final Logger LOGGER = Logger.getLogger(ProductoDAO.class.getName());

    // Método para obtener un producto por ID
    public Producto obtenerProductoPorId(int productoId) {
        String query = "SELECT p.*, a.Nombre AS NombreArea " +
                "FROM productos p " +
                "LEFT JOIN area a ON p.AreaID = a.AreaID " +
                "WHERE p.ProductoID = ?";

        try (Connection conexion = Conexion_DB.getConexion();
             PreparedStatement stmt = conexion.prepareStatement(query)) {

            stmt.setInt(1, productoId);

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
            LOGGER.log(Level.SEVERE, "Error al obtener producto por ID", e);
        }
        return null;
    }

    // Método para clonar un producto
    public Producto clonarProducto(int productoId) {
        Producto productoOriginal = obtenerProductoPorId(productoId);

        if (productoOriginal == null) {
            LOGGER.warning("No se encontró el producto para clonar");
            return null;
        }

        // Crear un nuevo producto con los mismos atributos, pero con ID 0
        return new Producto.ProductoBuilder()
                .productoID(0)  // Nuevo ID será generado por auto-incremento
                .nombre(productoOriginal.getNombre() + " (Copia)")
                .descripcion(productoOriginal.getDescripcion())
                .areaID(productoOriginal.getAreaID())
                .precio(productoOriginal.getPrecio())
                .unidadesDisponibles(0)  // Iniciar con 0 unidades
                .nivelReorden(productoOriginal.getNivelReorden())
                .fechaCaducidad(LocalDate.now().plusYears(1))  // Nueva fecha de caducidad
                .codigoBarras(generarNuevoCodigoBarras(productoOriginal.getCodigoBarras()))
                .tamañoNeto(productoOriginal.getTamañoNeto())
                .marca(productoOriginal.getMarca())
                .contenido(productoOriginal.getContenido())
                .nombreArea(productoOriginal.getNombreArea())
                .build();
    }

    // Método para guardar un nuevo producto (clon)
    public Producto guardarProductoClonado(Producto productoClon) {
        String query = "INSERT INTO productos " +
                "(Nombre, Descripcion, AreaID, Precio, UnidadesDisponibles, " +
                "NivelReorden, FechaCaducidad, CodigoBarras, TamañoNeto, Marca, Contenido) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexion = Conexion_DB.getConexion();
             PreparedStatement stmt = conexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, productoClon.getNombre());
            stmt.setString(2, productoClon.getDescripcion());
            stmt.setInt(3, productoClon.getAreaID());
            stmt.setDouble(4, productoClon.getPrecio());
            stmt.setInt(5, productoClon.getUnidadesDisponibles());
            stmt.setInt(6, productoClon.getNivelReorden());
            stmt.setDate(7, productoClon.getFechaCaducidad() != null
                    ? Date.valueOf(productoClon.getFechaCaducidad())
                    : null);
            stmt.setString(8, productoClon.getCodigoBarras());
            stmt.setString(9, productoClon.getTamañoNeto());
            stmt.setString(10, productoClon.getMarca());
            stmt.setString(11, productoClon.getContenido());

            stmt.executeUpdate();

            // Obtener el ID generado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int nuevoId = generatedKeys.getInt(1);
                    productoClon.setProductoID(nuevoId);
                }
            }

            return productoClon;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al guardar producto clonado", e);
            return null;
        }
    }

    // Método para obtener todos los productos
    public List<Producto> obtenerTodosProductos() {
        List<Producto> productos = new ArrayList<>();
        String query = "SELECT p.*, a.Nombre AS NombreArea " +
                "FROM productos p " +
                "LEFT JOIN area a ON p.AreaID = a.AreaID";

        try (Connection conexion = Conexion_DB.getConexion();
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
            LOGGER.log(Level.SEVERE, "Error al obtener todos los productos", e);
        }

        return productos;
    }

    // Método para generar un nuevo código de barras
    private String generarNuevoCodigoBarras(String codigoOriginal) {
        // Lógica para generar un nuevo código de barras único
        return codigoOriginal + "-COPIA-" + System.currentTimeMillis();
    }

    // Método main de demostración
    public static void main(String[] args) {
        ProductoDAO productoDAO = new ProductoDAO();

        try {
            // 1. Listar todos los productos existentes
            System.out.println("--- Productos Existentes ---");
            List<Producto> productosExistentes = productoDAO.obtenerTodosProductos();
            productosExistentes.forEach(System.out::println);

            // 2. Clonar un producto específico (por ejemplo, el primer producto de la lista)
            if (!productosExistentes.isEmpty()) {
                int productoAClonarId = productosExistentes.get(0).getProductoID();

                System.out.println("\n--- Clonando Producto ---");
                Producto productoOriginal = productoDAO.obtenerProductoPorId(productoAClonarId);
                System.out.println("Producto Original: " + productoOriginal);

                // Crear un clon del producto
                Producto productoClon = productoDAO.clonarProducto(productoAClonarId);
                System.out.println("Producto Clon (antes de guardar): " + productoClon);

                // Guardar el producto clonado en la base de datos
                Producto productoGuardado = productoDAO.guardarProductoClonado(productoClon);
                System.out.println("Producto Clon (guardado): " + productoGuardado);
            } else {
                System.out.println("No hay productos para clonar.");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error en la demostración del Prototype", e);
        }
    }
}