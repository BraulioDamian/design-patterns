package PatronInterpreter;

import DBObjetos.Producto;
import java.util.List;

/**
 * Clase para depurar los problemas con las consultas en el patrón Interpreter.
 * Esta clase te ayudará a identificar por qué algunas consultas no están funcionando.
 */
public class InterpreterDebug {

    public static void main(String[] args) {
        // Obtener la instancia del InterpreterManager (Singleton)
        InterpreterManager manager = InterpreterManager.getInstance();
        
        System.out.println("=== DEPURACIÓN DE CONSULTAS ===");
        
        // Probar consulta formal
        probarConsulta(manager, "precio < 50 AND area = 2 AND nombre = \"prueba (Copia)\"", "Consulta Formal Combinada");
        
        // Probar lenguaje natural con las marcas en tu base de datos
        probarConsulta(manager, "productos baratos de la marca asdasd", "Consulta Natural - Marca asdasd");
        
        // Probar lenguaje natural con áreas
        probarConsulta(manager, "productos de frutas", "Consulta Natural - Área Frutas");
        
        // Probar búsqueda por nombre en lenguaje natural
        probarConsulta(manager, "productos llamados prueba", "Consulta Natural - Nombre 'prueba'");
        
        // Probar búsqueda por marca MySQLTech
        probarConsulta(manager, "productos de la marca MySQLTech", "Consulta Natural - Marca MySQLTech");
        
        // Probar búsqueda por marca MySQLTech con ajuste de precio
        probarConsulta(manager, "productos baratos de la marca MySQLTech", "Consulta Natural - Baratos MySQLTech");
        
        // Probar el método específico para buscar por marca sin filtro de precio
        System.out.println("\n=== PRUEBA: Método Específico de Búsqueda por Marca ===");
        try {
            List<Producto> resultadosMarca = manager.consultaProductosPorMarca("MySQLTech");
            if (resultadosMarca.isEmpty()) {
                System.out.println("No se encontraron productos de la marca MySQLTech");
            } else {
                System.out.println("Se encontraron " + resultadosMarca.size() + " productos de la marca MySQLTech:");
                for (Producto p : resultadosMarca) {
                    System.out.println(" - ID: " + p.getProductoID() + 
                                      ", Nombre: " + p.getNombre() + 
                                      ", Área: " + p.getNombreArea() + 
                                      ", Precio: $" + p.getPrecio() + 
                                      ", Marca: " + p.getMarca());
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR al buscar productos por marca: " + e.getMessage());
        }
        
        // Cerrar la conexión al finalizar
        manager.cerrarConexion();
    }
    
    /**
     * Método para probar consultas y mostrar información de depuración
     */
    private static void probarConsulta(InterpreterManager manager, String consulta, String descripcion) {
        System.out.println("\n=== PRUEBA: " + descripcion + " ===");
        System.out.println("Consulta: \"" + consulta + "\"");
        
        try {
            Expression expresion = QueryParser.parse(consulta);
            System.out.println("Tipo de expresión: " + expresion.getClass().getSimpleName());
            
            if (expresion instanceof AndExpression) {
                System.out.println(" -> Expresión AND detectada");
            } else if (expresion instanceof OrExpression) {
                System.out.println(" -> Expresión OR detectada");
            } else if (expresion instanceof PrecioBajoExpression) {
                System.out.println(" -> Expresión de Precio detectada");
            } else if (expresion instanceof AreaExpression) {
                System.out.println(" -> Expresión de Área detectada");
            } else if (expresion instanceof NombreExpression) {
                System.out.println(" -> Expresión de Nombre detectada");
            } else if (expresion instanceof MarcaExpression) {
                System.out.println(" -> Expresión de Marca detectada");
            } else if (expresion instanceof AllProductsExpression) {
                System.out.println(" -> No se detectó ninguna expresión, se devuelven todos los productos");
            }
            
            List<Producto> resultados = manager.ejecutarConsultaFormal(consulta);
            
            if (resultados.isEmpty()) {
                System.out.println("RESULTADO: No se encontraron productos.");
            } else {
                System.out.println("RESULTADO: Se encontraron " + resultados.size() + " productos:");
                for (Producto p : resultados) {
                    System.out.println(" - ID: " + p.getProductoID() + 
                                      ", Nombre: " + p.getNombre() + 
                                      ", Área: " + p.getNombreArea() + 
                                      ", Precio: $" + p.getPrecio() + 
                                      ", Marca: " + p.getMarca());
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("=== FIN DE PRUEBA ===");
    }
}
