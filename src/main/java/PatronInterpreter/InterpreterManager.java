package PatronInterpreter;

import ConexionDB.Conexion_DB;
import Consultas.CONSULTASDAO;
import DBObjetos.Producto;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

/**
 * Clase principal que gestiona el patrón Interpreter.
 * Esta clase sirve como fachada para el sistema de interpretación de consultas.
 */
public class InterpreterManager {
    
    private static InterpreterManager instance;
    private Connection conexion;
    private CONSULTASDAO consultasDao;
    
    /**
     * Constructor privado para Singleton
     */
    private InterpreterManager() {
        try {
            conexion = Conexion_DB.getConexion();
            consultasDao = new CONSULTASDAO(conexion);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                    "Error al inicializar el Interpreter Manager: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    /**
     * Obtener la instancia única de InterpreterManager (patrón Singleton)
     */
    public static synchronized InterpreterManager getInstance() {
        if (instance == null) {
            instance = new InterpreterManager();
        }
        return instance;
    }
    
    /**
     * Ejecuta una consulta formal con sintaxis específica
     * 
     * @param query Consulta en formato específico (ej: "precio < 100 AND area = 2")
     * @return Lista de productos que cumplen la consulta
     */
    public List<Producto> ejecutarConsultaFormal(String query) {
        try {
            List<Producto> productos = consultasDao.obtenerProductosConNombreArea();
            Expression expresion = QueryParser.parse(query);
            return expresion.interpret(productos);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                    "Error al ejecutar la consulta formal: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    /**
     * Ejecuta una consulta en lenguaje natural 
     * 
     * @param query Consulta en lenguaje natural (ej: "productos baratos de la marca Bimbo")
     * @return Lista de productos que cumplen la consulta
     */
    public List<Producto> ejecutarConsultaNatural(String query) {
        try {
            List<Producto> productos = consultasDao.obtenerProductosConNombreArea();
            Expression expresion = QueryParser.parseNaturalLanguage(query);
            return expresion.interpret(productos);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                    "Error al ejecutar la consulta natural: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    /**
     * Crea una consulta programática con criterios específicos
     * 
     * @param areaId ID del área
     * @param precioMax Precio máximo
     * @param stockMinimo Stock mínimo
     * @return Lista de productos que cumplen con todos los criterios
     */
    public List<Producto> consultaAvanzada(int areaId, double precioMax, int stockMinimo) {
        try {
            List<Producto> productos = consultasDao.obtenerProductosConNombreArea();
            
            Expression areaExpr = new AreaExpression(areaId);
            Expression precioExpr = new PrecioBajoExpression(precioMax);
            Expression stockExpr = new DisponibilidadExpression(stockMinimo - 1);
            
            Expression consultaCompleta = new AndExpression(
                    areaExpr, 
                    new AndExpression(precioExpr, stockExpr)
            );
            
            return consultaCompleta.interpret(productos);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                    "Error al ejecutar la consulta avanzada: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    /**
     * Obtiene productos por marca
     * 
     * @param marca Nombre de la marca
     * @return Lista de productos de esa marca
     */
    public List<Producto> obtenerProductosPorMarca(String marca) {
        try {
            List<Producto> productos = consultasDao.obtenerProductosConNombreArea();
            Expression expresion = new MarcaExpression(marca);
            return expresion.interpret(productos);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                    "Error al buscar productos por marca: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    /**
     * Obtiene productos de un área específica
     * 
     * @param areaId ID del área
     * @return Lista de productos de esa área
     */
    public List<Producto> obtenerProductosPorArea(int areaId) {
        try {
            List<Producto> productos = consultasDao.obtenerProductosConNombreArea();
            Expression expresion = new AreaExpression(areaId);
            return expresion.interpret(productos);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                    "Error al buscar productos por área: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    /**
     * Obtiene productos por debajo de un precio máximo
     * 
     * @param precioMax Precio máximo
     * @return Lista de productos con precio menor al indicado
     */
    public List<Producto> obtenerProductosEconomicos(double precioMax) {
        try {
            List<Producto> productos = consultasDao.obtenerProductosConNombreArea();
            Expression expresion = new PrecioBajoExpression(precioMax);
            return expresion.interpret(productos);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                    "Error al buscar productos económicos: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    /**
     * Obtiene productos con stock por encima de un mínimo
     * 
     * @param stockMinimo Stock mínimo requerido
     * @return Lista de productos con stock mayor al indicado
     */
    public List<Producto> obtenerProductosDisponibles(int stockMinimo) {
        try {
            List<Producto> productos = consultasDao.obtenerProductosConNombreArea();
            Expression expresion = new DisponibilidadExpression(stockMinimo - 1);
            return expresion.interpret(productos);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                    "Error al buscar productos disponibles: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    /**
     * Ejecuta una consulta para productos de una marca específica sin filtrar por precio
     * 
     * @param marca Nombre de la marca
     * @return Lista de productos de esa marca
     */
    public List<Producto> consultaProductosPorMarca(String marca) {
        try {
            List<Producto> productos = consultasDao.obtenerProductosConNombreArea();
            Expression expresion = new MarcaExpression(marca);
            
            List<Producto> resultados = expresion.interpret(productos);
            
            // Ordenar resultados por precio (de menor a mayor)
            resultados.sort((p1, p2) -> Double.compare(p1.getPrecio(), p2.getPrecio()));
            
            return resultados;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                    "Error al buscar productos por marca: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    /**
     * Busca todos los productos en un rango de precios
     * 
     * @param precioMinimo Precio mínimo
     * @param precioMaximo Precio máximo
     * @return Lista de productos en ese rango de precios
     */
    public List<Producto> consultaProductosPorRangoPrecio(double precioMinimo, double precioMaximo) {
        try {
            List<Producto> productos = consultasDao.obtenerProductosConNombreArea();
            
            return productos.stream()
                   .filter(p -> p.getPrecio() >= precioMinimo && p.getPrecio() <= precioMaximo)
                   .sorted((p1, p2) -> Double.compare(p1.getPrecio(), p2.getPrecio()))
                   .collect(Collectors.toList());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                    "Error al buscar productos por rango de precio: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    /**
     * Ejecuta una búsqueda combinada con múltiples criterios específicos
     * 
     * @param precio Precio máximo (menor que)
     * @param areaId ID del área
     * @param nombre Nombre exacto del producto
     * @return Lista de productos que cumplen todos los criterios
     */
    public List<Producto> busquedaCombinada(double precio, int areaId, String nombre) {
        try {
            List<Producto> productos = consultasDao.obtenerProductosConNombreArea();
            
            Expression precioExpr = new PrecioBajoExpression(precio);
            Expression areaExpr = new AreaExpression(areaId);
            Expression nombreExpr = new NombreExpression(nombre, true); // Coincidencia exacta
            
            // Combinar las expresiones con AND
            Expression combinada = new AndExpression(
                precioExpr,
                new AndExpression(areaExpr, nombreExpr)
            );
            
            return combinada.interpret(productos);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                    "Error al ejecutar la búsqueda combinada: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    /**
     * Ejecuta una consulta formal compleja con sintaxis específica
     * Este método es útil para consultas como "precio < 50 AND area = 2 AND nombre = \"prueba (Copia)\""
     * 
     * @param query Consulta formal con sintaxis específica
     * @return Lista de productos que cumplen la consulta
     */
    public List<Producto> ejecutarConsultaCompleja(String query) {
        return ejecutarConsultaFormal(query);
    }
    
    /**
     * Cerrar la conexión cuando ya no se utilice el manager
     */
    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}