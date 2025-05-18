package PatronInterpreter;

import Consultas.CONSULTASDAO;
import DBObjetos.Producto;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Clase demostrativa del patrón Interpreter para consultas de productos.
 */
public class InterpreterDemo {
    
    /**
     * Ejecuta una consulta de productos utilizando el patrón Interpreter.
     * 
     * @param query La consulta en formato de texto (ej: "precio < 100 AND marca = \"Bimbo\"")
     * @return Lista de productos que cumplen con la consulta
     */
    public static List<Producto> ejecutarConsulta(String query) {
        Connection conexion = null;
        try {
            // Obtener todos los productos de la base de datos
            conexion = ConexionDB.Conexion_DB.getConexion();
            CONSULTASDAO consultasDao = new CONSULTASDAO(conexion);
            List<Producto> todosLosProductos = consultasDao.obtenerProductosConNombreArea();
            
            // Analizar la consulta y convertirla en expresiones
            Expression expression = QueryParser.parse(query);
            
            // Interpretar la consulta sobre la lista de productos
            return expression.interpret(todosLosProductos);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                    "Error al ejecutar la consulta: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return new ArrayList<>(); // Devolver lista vacía en lugar de null
        } finally {
            // Cerrar la conexión si está abierta
            if (conexion != null) {
                try {
                    if (!conexion.isClosed()) {
                        conexion.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * Ejecuta una consulta de productos usando lenguaje natural.
     * 
     * @param query La consulta en lenguaje natural (ej: "productos baratos de la marca Bimbo")
     * @return Lista de productos que cumplen con la consulta
     */
    public static List<Producto> ejecutarConsultaNatural(String query) {
        Connection conexion = null;
        try {
            // Obtener todos los productos de la base de datos
            conexion = ConexionDB.Conexion_DB.getConexion();
            CONSULTASDAO consultasDao = new CONSULTASDAO(conexion);
            List<Producto> todosLosProductos = consultasDao.obtenerProductosConNombreArea();
            
            // Analizar la consulta en lenguaje natural
            Expression expression = QueryParser.parseNaturalLanguage(query);
            
            // Interpretar la consulta sobre la lista de productos
            return expression.interpret(todosLosProductos);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                    "Error al ejecutar la consulta: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return new ArrayList<>(); // Devolver lista vacía en lugar de null
        } finally {
            // Cerrar la conexión si está abierta
            if (conexion != null) {
                try {
                    if (!conexion.isClosed()) {
                        conexion.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * Crear una expresión personalizada programáticamente y ejecutarla.
     */
    public static List<Producto> consultaAvanzada(int areaId, double precioMax, int stockMinimo) {
        Connection conexion = null;
        try {
            // Obtener todos los productos
            conexion = ConexionDB.Conexion_DB.getConexion();
            CONSULTASDAO consultasDao = new CONSULTASDAO(conexion);
            List<Producto> todosLosProductos = consultasDao.obtenerProductosConNombreArea();
            
            // Crear expresiones para cada condición
            Expression areaExpr = new AreaExpression(areaId);
            Expression precioExpr = new PrecioBajoExpression(precioMax);
            Expression stockExpr = new DisponibilidadExpression(stockMinimo - 1); // > stockMinimo-1 significa >= stockMinimo
            
            // Combinar las expresiones con AND
            Expression consultaCompleta = new AndExpression(
                    areaExpr, 
                    new AndExpression(precioExpr, stockExpr)
            );
            
            // Ejecutar la consulta
            return consultaCompleta.interpret(todosLosProductos);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                    "Error al ejecutar la consulta avanzada: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return new ArrayList<>(); // Devolver lista vacía en lugar de null
        } finally {
            // Cerrar la conexión si está abierta
            if (conexion != null) {
                try {
                    if (!conexion.isClosed()) {
                        conexion.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void main(String[] args) {
        // Este método es para pruebas
        System.out.println("Implementación del patrón Interpreter para consultas de productos");
        
        try {
            // Ejemplo de consulta formal
            String consultaFormal = "precio < 50 AND area = 2";
            List<Producto> resultadosFormal = ejecutarConsulta(consultaFormal);
            System.out.println("Resultado de la consulta formal '" + consultaFormal + "': " + 
                    resultadosFormal.size() + " productos");
            
            // Ejemplo de consulta en lenguaje natural
            String consultaNatural = "productos baratos de la marca Bimbo";
            List<Producto> resultadosNatural = ejecutarConsultaNatural(consultaNatural);
            System.out.println("Resultado de la consulta natural '" + consultaNatural + "': " + 
                    resultadosNatural.size() + " productos");
            
            // Ejemplo de consulta programática
            List<Producto> resultadosProgramaticos = consultaAvanzada(2, 100.0, 10);
            System.out.println("Resultado de la consulta programática: " + 
                    resultadosProgramaticos.size() + " productos");
            
            // Lanzar la interfaz de usuario
            javax.swing.SwingUtilities.invokeLater(() -> {
                ConsultaProductosUI ui = new ConsultaProductosUI();
                ui.setVisible(true);
            });
        } catch (Exception e) {
            System.err.println("Error en la demostración: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
