package Composite;

import Consultas.AreaDAO;
import Consultas.ProductoDAO;
import DBObjetos.Area;
import DBObjetos.Producto;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        // Instanciar DAOs
        AreaDAO areaDAO = new AreaDAO();
        ProductoDAO productoDAO = new ProductoDAO();

        // PARTE 1: Crear un área nueva
        System.out.println("\n=== CREANDO NUEVA ÁREA DE PRUEBA ===");
        Area nuevaArea = new Area(0, "Área de Prueba", "Área creada para prueba de eliminación en cascada");
        if (areaDAO.insertarArea(nuevaArea)) {
            System.out.println("Área insertada con ID: " + nuevaArea.getAreaID());
        } else {
            System.out.println("Error al insertar el área.");
            return; // Si falla la inserción del área, terminamos
        }

        // PARTE 2: Agregar productos al área creada
        System.out.println("\n=== AGREGANDO PRODUCTOS AL ÁREA ===");

        // Producto 1
        Producto producto1 = new Producto(
                0,
                "Producto Prueba 1",
                "Producto para probar eliminación en cascada",
                nuevaArea.getAreaID(), // Asignamos el área recién creada
                89.99,
                15,
                3,
                LocalDate.now().plusMonths(3),
                "1111222233",
                "100g",
                "Marca Prueba",
                "Contenido de prueba",
                nuevaArea.getNombreArea(),
                1
        );

        if (productoDAO.insertarProducto(producto1)) {
            System.out.println("Producto 1 insertado con ID: " + producto1.getProductoID());
        } else {
            System.out.println("Error al insertar el producto 1.");
        }

        // Producto 2
        Producto producto2 = new Producto(
                0,
                "Producto Prueba 2",
                "Otro producto para probar eliminación en cascada",
                nuevaArea.getAreaID(), // Asignamos el área recién creada
                129.50,
                8,
                2,
                LocalDate.now().plusMonths(6),
                "4444555566",
                "250g",
                "Otra Marca",
                "Otro contenido de prueba",
                nuevaArea.getNombreArea(),
                1
        );

        if (productoDAO.insertarProducto(producto2)) {
            System.out.println("Producto 2 insertado con ID: " + producto2.getProductoID());
        } else {
            System.out.println("Error al insertar el producto 2.");
        }

        // PARTE 3: Mostrar estructura actual del inventario
        System.out.println("\n=== ESTRUCTURA ACTUAL DEL INVENTARIO ===");
        List<ComponenteInventario> areasRaiz = areaDAO.getAreasConProductos();
        if (areasRaiz.isEmpty()) {
            System.out.println("No se encontraron áreas para mostrar en el inventario.");
        } else {
            mostrarEstructuraInventario(areasRaiz, 0);

            // PARTE 4: Mostrar totales por área antes de descuentos
            System.out.println("\n=== REPORTE DE INVENTARIO POR ÁREAS (ANTES DE DESCUENTOS) ===");
            for (ComponenteInventario areaRaiz : areasRaiz) {
                mostrarTotalesPorArea(areaRaiz, 0);
            }

            // PARTE 5: Aplicar descuentos y mostrar nuevos totales
            System.out.println("\n=== APLICANDO DESCUENTOS ===");

            // Buscar el área que acabamos de crear para aplicarle descuento
            ComponenteInventario areaPrueba = null;
            for (ComponenteInventario area : areasRaiz) {
                if (area.getNombre().equals(nuevaArea.getNombreArea())) {
                    areaPrueba = area;
                    break;
                }
            }

            if (areaPrueba != null) {
                System.out.println("Aplicando 15% de descuento al área: " + areaPrueba.getNombre());
                areaPrueba.aplicarDescuento(15);

                // Aplicar descuento a un producto específico si hay alguno
                List<ComponenteInventario> hijos = areaPrueba.getHijos();
                if (!hijos.isEmpty()) {
                    ComponenteInventario primerProducto = hijos.get(0);
                    System.out.println("Aplicando 10% de descuento adicional al producto: " + primerProducto.getNombre());
                    primerProducto.aplicarDescuento(10);
                }
            }

            // También podemos aplicar descuentos a otras áreas existentes
            if (areasRaiz.size() > 1) {
                ComponenteInventario otraArea = areasRaiz.get(0);
                if (!otraArea.getNombre().equals(nuevaArea.getNombreArea())) {
                    System.out.println("Aplicando 5% de descuento al área: " + otraArea.getNombre());
                    otraArea.aplicarDescuento(5);
                }
            }

            // PARTE 6: Mostrar totales después de descuentos
            System.out.println("\n=== REPORTE DESPUÉS DE DESCUENTOS ===");
            for (ComponenteInventario areaRaiz : areasRaiz) {
                mostrarTotalesPorArea(areaRaiz, 0);
            }

            // Calcular y mostrar total general del inventario
            double totalInventario = areasRaiz.stream()
                    .mapToDouble(ComponenteInventario::getPrecioTotal)
                    .sum();
            System.out.println("\n=======================================");
            System.out.println("TOTAL GENERAL DEL INVENTARIO: $" + String.format("%.2f", totalInventario));
        }

        // PARTE 7: Eliminar el área y verificar eliminación en cascada
        System.out.println("\n=== ELIMINANDO ÁREA Y VERIFICANDO ELIMINACIÓN EN CASCADA ===");
        System.out.println("Intentando eliminar área: " + nuevaArea.getNombreArea());

        if (areaDAO.eliminarAreaPorNombre(nuevaArea.getNombreArea())) {
            System.out.println("El área '" + nuevaArea.getNombreArea() + "' ha sido eliminada exitosamente.");

            // Verificar que los productos también fueron eliminados
            List<Producto> productosPostEliminacion = productoDAO.getAllProductos();
            boolean productosEliminados = true;

            for (Producto p : productosPostEliminacion) {
                if (p.getProductoID() == producto1.getProductoID() ||
                        p.getProductoID() == producto2.getProductoID()) {
                    productosEliminados = false;
                    System.out.println("¡Error! El producto " + p.getNombre() + " no fue eliminado.");
                }
            }

            if (productosEliminados) {
                System.out.println("Todos los productos asociados al área fueron eliminados correctamente.");
            }
        } else {
            System.out.println("Error al eliminar el área.");
        }

        // PARTE 8: Mostrar estructura final del inventario
        System.out.println("\n=== ESTRUCTURA FINAL DEL INVENTARIO ===");
        List<ComponenteInventario> areasFinales = areaDAO.getAreasConProductos();
        if (areasFinales.isEmpty()) {
            System.out.println("No hay áreas para mostrar en el inventario.");
        } else {
            mostrarEstructuraInventario(areasFinales, 0);

            // Mostrar total final
            double totalFinal = areasFinales.stream()
                    .mapToDouble(ComponenteInventario::getPrecioTotal)
                    .sum();
            System.out.println("\n=======================================");
            System.out.println("TOTAL FINAL DEL INVENTARIO: $" + String.format("%.2f", totalFinal));
        }
    }

    // Método para mostrar la estructura completa del inventario
    private static void mostrarEstructuraInventario(List<ComponenteInventario> componentes, int nivel) {
        for (ComponenteInventario componente : componentes) {
            StringBuilder indent = new StringBuilder();
            for (int i = 0; i < nivel; i++) {
                indent.append("  ");
            }
            String tipo = (componente instanceof Producto) ? "Producto" : "Área";
            System.out.println(indent + tipo + ": " + componente.getNombre() +
                    (tipo.equals("Producto") ? " - Precio: $" + String.format("%.2f", ((Producto)componente).getPrecio()) : ""));
            mostrarEstructuraInventario(componente.getHijos(), nivel + 1);
        }
    }

    // Método recursivo para mostrar totales de forma jerárquica
    private static void mostrarTotalesPorArea(ComponenteInventario componente, int nivel) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < nivel; i++) {
            indent.append("  ");
        }
        System.out.println(indent + componente.getNombre() + " - Total: $" + String.format("%.2f", componente.getPrecioTotal()));
        for (ComponenteInventario hijo : componente.getHijos()) {
            mostrarTotalesPorArea(hijo, nivel + 1);
        }
    }
}