package Consultas;


import ConexionDB.Conexion_DB;
import DBObjetos.Producto;
import DBObjetos.ProductoDAO;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductoPrototypeDemoMain {
    private static final Logger LOGGER = Logger.getLogger(ProductoPrototypeDemoMain.class.getName());

    public static void main(String[] args) {
        // Instancia del DAO para manejar operaciones de productos
        ProductoDAO productoDAO = new ProductoDAO();

        try {
            // PASO 1: Demostración de Obtención de Productos Existentes
            System.out.println("==== PASO 1: Catálogo de Productos Inicial ====");
            List<Producto> productosOriginales = productoDAO.obtenerTodosProductos();
            productosOriginales.forEach(System.out::println);

            // Verificar que hay productos para clonar
            if (productosOriginales.isEmpty()) {
                System.out.println("No hay productos para realizar la demostración.");
                return;
            }

            // PASO 2: Selección del Producto Prototipo
            Producto productoPrototipo = productosOriginales.get(0);
            System.out.println("\n==== PASO 2: Producto Prototipo Seleccionado ====");
            System.out.println("Producto Original: " + productoPrototipo);

            // PASO 3: Demostración de Clonación
            System.out.println("\n==== PASO 3: Proceso de Clonación ====");

            // Clonar 3 versiones del mismo producto
            Producto clonVersion1 = productoDAO.clonarProducto(productoPrototipo.getProductoID());
            Producto clonVersion2 = productoDAO.clonarProducto(productoPrototipo.getProductoID());
            Producto clonVersion3 = productoDAO.clonarProducto(productoPrototipo.getProductoID());

            // PASO 4: Guardar Clones en Base de Datos
            System.out.println("\n==== PASO 4: Guardando Clones en Base de Datos ====");
            Producto clonGuardado1 = productoDAO.guardarProductoClonado(clonVersion1);
            Producto clonGuardado2 = productoDAO.guardarProductoClonado(clonVersion2);
            Producto clonGuardado3 = productoDAO.guardarProductoClonado(clonVersion3);

            // Verificar los clones guardados
            System.out.println("\nClon 1: " + clonGuardado1);
            System.out.println("Clon 2: " + clonGuardado2);
            System.out.println("Clon 3: " + clonGuardado3);

            // PASO 5: Demostrar Modificaciones Independientes
            System.out.println("\n==== PASO 5: Modificaciones Independientes de Clones ====");

            // Modificar cada clon de manera diferente
            clonGuardado1.setPrecio(clonGuardado1.getPrecio() * 0.9);  // Descuento 10%
            clonGuardado2.setUnidadesDisponibles(50);  // Establecer stock
            clonGuardado3.setDescripcion("Versión mejorada del producto original");

            // PASO 6: Actualización de Clones
            System.out.println("\n==== PASO 6: Actualización de Clones ====");
            // Aquí deberías implementar un método de actualización en tu DAO
            actualizarProducto(clonGuardado1);
            actualizarProducto(clonGuardado2);
            actualizarProducto(clonGuardado3);

            // PASO 7: Verificación Final
            System.out.println("\n==== PASO 7: Verificación de Clones Modificados ====");
            List<Producto> productosActualizados = productoDAO.obtenerTodosProductos();
            productosActualizados.stream()
                    .filter(p -> p.getNombre().contains("(Copia)"))
                    .forEach(System.out::println);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error en la demostración del Prototype", e);
        }
    }

    // Método auxiliar para actualizar productos (deberías implementarlo en tu DAO real)
    private static void actualizarProducto(Producto producto) {
        String query = "UPDATE productos SET " +
                "Precio = ?, UnidadesDisponibles = ?, Descripcion = ? " +
                "WHERE ProductoID = ?";

        try (Connection conexion = Conexion_DB.getConexion();
             PreparedStatement stmt = conexion.prepareStatement(query)) {

            stmt.setDouble(1, producto.getPrecio());
            stmt.setInt(2, producto.getUnidadesDisponibles());
            stmt.setString(3, producto.getDescripcion());
            stmt.setInt(4, producto.getProductoID());

            stmt.executeUpdate();
            System.out.println("Producto actualizado: " + producto.getNombre());

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar producto", e);
        }
    }
}