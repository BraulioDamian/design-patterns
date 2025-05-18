package PatronInterpreter;

import DBObjetos.Producto;
import java.util.List;

/**
 * Clase de ejemplo para demostrar el uso de las consultas combinadas
 * con el patrón Interpreter.
 */
public class EjemploConsultaCombinada {

    public static void main(String[] args) {
        // Obtener la instancia del InterpreterManager (Singleton)
        InterpreterManager manager = InterpreterManager.getInstance();
        
        // Ejemplo 1: Usar el método específico para búsqueda combinada
        System.out.println("=== Búsqueda Combinada con Método Específico ===");
        List<Producto> resultados1 = manager.busquedaCombinada(50.0, 2, "prueba (Copia)");
        mostrarResultados(resultados1);
        
        // Ejemplo 2: Usar la consulta formal con sintaxis específica
        System.out.println("\n=== Búsqueda Combinada con Sintaxis Formal ===");
        List<Producto> resultados2 = manager.ejecutarConsultaFormal("precio < 50 AND area = 2 AND nombre = \"prueba (Copia)\"");
        mostrarResultados(resultados2);
        
        // Ejemplo 3: Usar la consulta formal con otra sintaxis
        System.out.println("\n=== Búsqueda Alternativa ===");
        List<Producto> resultados3 = manager.ejecutarConsultaFormal("nombre = \"prueba (Copia)\" AND precio < 50 AND area = 2");
        mostrarResultados(resultados3);
        
        // Cerrar la conexión al finalizar
        manager.cerrarConexion();
    }
    
    /**
     * Método auxiliar para mostrar los resultados de la búsqueda
     */
    private static void mostrarResultados(List<Producto> productos) {
        if (productos.isEmpty()) {
            System.out.println("No se encontraron productos que cumplan con los criterios.");
        } else {
            System.out.println("Se encontraron " + productos.size() + " productos:");
            for (Producto p : productos) {
                System.out.println(" - " + p.getNombre() + " (Área: " + p.getNombreArea() + 
                                  ", Precio: $" + p.getPrecio() + ")");
            }
        }
    }
}
