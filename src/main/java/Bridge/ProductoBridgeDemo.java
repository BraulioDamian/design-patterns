package Bridge;

import DBObjetos.Producto;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductoBridgeDemo {
    private static final Logger LOGGER = Logger.getLogger(ProductoBridgeDemo.class.getName());

    public static void main(String[] args) {
        try {
            // Demostración con Oracle
            System.out.println("===== ORACLE DATABASE OPERATIONS =====");
            ProductoPersistencia oraclePersistencia = new ProductoOraclePersistencia();
            ProductoBridge oracleBridge = new ProductoBridge(oraclePersistencia);

            // Crear producto para Oracle
            Producto productoOracle = new Producto.ProductoBuilder()
                    .nombre("Tablet Ejemplo Oracle")
                    .descripcion("Tablet de prueba para base de datos Oracle")
                    .areaID(1)
                    .precio(300.0)
                    .unidadesDisponibles(50)
                    .nivelReorden(10)
                    .fechaCaducidad(LocalDate.now().plusYears(1))
                    .codigoBarras("ORACLE123456")
                    .tamañoNeto("10 pulgadas")
                    .marca("OracleTech")
                    .contenido("Tablet de alta resolución")
                    .build();

            // Guardar producto en Oracle con manejo de errores
            try {
                boolean guardadoOracle = oracleBridge.guardarProducto(productoOracle);
                System.out.println("Producto guardado en Oracle: " + guardadoOracle);
                System.out.println("ID de producto en Oracle: " + productoOracle.getProductoID());
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error al guardar producto en Oracle", e);
                System.err.println("Detalles del error: " + e.getMessage());
            }

            // Listar productos de Oracle
            List<Producto> productosOracle = oracleBridge.obtenerTodosProductos();
            System.out.println("\nProductos en Oracle:");
            productosOracle.forEach(p -> System.out.println(
                    p.getProductoID() + ": " +
                            p.getNombre() + " - " +
                            p.getPrecio()
            ));

            // Demostración con MySQL
            System.out.println("\n===== MYSQL DATABASE OPERATIONS =====");
            ProductoPersistencia mysqlPersistencia = new ProductoMySQLPersistencia();
            ProductoBridge mysqlBridge = new ProductoBridge(mysqlPersistencia);

            // Crear producto para MySQL (similar al de Oracle)
            Producto productoMySQL = new Producto.ProductoBuilder()
                    .nombre("Tablet Ejemplo MySQL")
                    .descripcion("Tablet de prueba para base de datos MySQL")
                    .areaID(1)
                    .precio(300.0)
                    .unidadesDisponibles(50)
                    .nivelReorden(10)
                    .fechaCaducidad(LocalDate.now().plusYears(1))
                    .codigoBarras("MYSQL123456")
                    .tamañoNeto("10 pulgadas")
                    .marca("MySQLTech")
                    .contenido("Tablet de alta resolución")
                    .build();

            // Guardar producto en MySQL con manejo de errores
            try {
                boolean guardadoMySQL = mysqlBridge.guardarProducto(productoMySQL);
                System.out.println("Producto guardado en MySQL: " + guardadoMySQL);
                System.out.println("ID de producto en MySQL: " + productoMySQL.getProductoID());
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error al guardar producto en MySQL", e);
                System.err.println("Detalles del error: " + e.getMessage());
            }

            // Listar productos de MySQL
            List<Producto> productosMySQL = mysqlBridge.obtenerTodosProductos();
            System.out.println("\nProductos en MySQL:");
            productosMySQL.forEach(p -> System.out.println(
                    p.getProductoID() + ": " +
                            p.getNombre() + " - " +
                            p.getPrecio()
            ));

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error general en la aplicación", e);
            e.printStackTrace();
        }
    }
}