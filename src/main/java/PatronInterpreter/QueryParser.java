package PatronInterpreter;

import ConexionDB.Conexion_DB;
import Consultas.CONSULTASDAO;
import DBObjetos.Area;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parseador de consultas que convierte texto en expresiones interpretables.
 */
public class QueryParser {
    // Patrones para reconocer distintas partes de la consulta formal
    private static final Pattern PRECIO_PATTERN = Pattern.compile("precio\\s*<\\s*(\\d+(\\.\\d+)?)");
    private static final Pattern AREA_PATTERN = Pattern.compile("area\\s*=\\s*(\\d+)");
    private static final Pattern DISPONIBILIDAD_PATTERN = Pattern.compile("disponible\\s*>\\s*(\\d+)");
    private static final Pattern MARCA_PATTERN = Pattern.compile("marca\\s*=\\s*\"([^\"]+)\"");
    private static final Pattern NOMBRE_PATTERN = Pattern.compile("nombre\\s*=\\s*\"([^\"]+)\"");
    
    // Patrones adicionales para casos más flexibles
    private static final Pattern PRECIO_FLEXIBLE_PATTERN = Pattern.compile("precio\\s*menor\\s*(a|que)\\s*(\\d+(\\.\\d+)?)");
    private static final Pattern MARCA_SIN_COMILLAS_PATTERN = Pattern.compile("marca\\s*=\\s*([^\\s]+)");
    private static final Pattern NOMBRE_SIN_COMILLAS_PATTERN = Pattern.compile("nombre\\s*=\\s*([^\\s]+)");
    
    // Almacena las áreas para que coincidan los nombres con los IDs
    private static Map<String, Integer> areasMap = null;
    
    /**
     * Inicializa el mapa de áreas desde la base de datos
     */
    private static void initAreas() {
        if (areasMap == null) {
            areasMap = new HashMap<>();
            try {
                Connection conexion = Conexion_DB.getConexion();
                CONSULTASDAO dao = new CONSULTASDAO(conexion);
                List<Area> areas = dao.obtenerAreas();
                
                // Llenar el mapa con los nombres de áreas y sus IDs
                for (Area area : areas) {
                    areasMap.put(area.getNombreArea().toLowerCase(), area.getAreaID());
                    // También añadir versiones singulares/plurales comunes
                    if (area.getNombreArea().toLowerCase().endsWith("s")) {
                        // Si termina en 's', añadir versión sin 's' (ejemplo: bebidas -> bebida)
                        String singular = area.getNombreArea().toLowerCase().substring(0, area.getNombreArea().length() - 1);
                        areasMap.put(singular, area.getAreaID());
                    } else {
                        // Si no termina en 's', añadir versión con 's' (ejemplo: bebida -> bebidas)
                        String plural = area.getNombreArea().toLowerCase() + "s";
                        areasMap.put(plural, area.getAreaID());
                    }
                }
                
                conexion.close();
            } catch (SQLException e) {
                System.err.println("Error al inicializar áreas: " + e.getMessage());
                // Crear un mapa por defecto si falla la conexión
                areasMap.put("bebidas", 1);
                areasMap.put("bebida", 1);
                areasMap.put("alimentos", 2);
                areasMap.put("alimento", 2);
                areasMap.put("limpieza", 3);
            }
        }
    }
    
    /**
     * Parsea una consulta en texto y devuelve la expresión correspondiente.
     * 
     * @param query Texto de la consulta (ej: "precio < 50 AND area = 2")
     * @return Expression que representa la consulta
     */
    public static Expression parse(String query) {
        try {
            query = query.toLowerCase().trim();
            
            // Si la consulta contiene AND u OR, separarla y procesarla
            if (query.contains(" and ")) {
                String[] parts = query.split(" and ", 2);
                return new AndExpression(parse(parts[0]), parse(parts[1]));
            } else if (query.contains(" or ")) {
                String[] parts = query.split(" or ", 2);
                return new OrExpression(parse(parts[0]), parse(parts[1]));
            }
            
            // Procesar expresiones simples
            Matcher precieMatcher = PRECIO_PATTERN.matcher(query);
            if (precieMatcher.find()) {
                double precio = Double.parseDouble(precieMatcher.group(1));
                return new PrecioBajoExpression(precio);
            }
            
            // Patrón alternativo para precio
            Matcher precioFlexMatcher = PRECIO_FLEXIBLE_PATTERN.matcher(query);
            if (precioFlexMatcher.find()) {
                double precio = Double.parseDouble(precioFlexMatcher.group(2));
                return new PrecioBajoExpression(precio);
            }
            
            Matcher areaMatcher = AREA_PATTERN.matcher(query);
            if (areaMatcher.find()) {
                int areaId = Integer.parseInt(areaMatcher.group(1));
                return new AreaExpression(areaId);
            }
            
            Matcher dispMatcher = DISPONIBILIDAD_PATTERN.matcher(query);
            if (dispMatcher.find()) {
                int cantidad = Integer.parseInt(dispMatcher.group(1));
                return new DisponibilidadExpression(cantidad);
            }
            
            Matcher marcaMatcher = MARCA_PATTERN.matcher(query);
            if (marcaMatcher.find()) {
                String marca = marcaMatcher.group(1);
                return new MarcaExpression(marca);
            }
            
            // Verificar si es una consulta por nombre con comillas
            Matcher nombreMatcher = NOMBRE_PATTERN.matcher(query);
            if (nombreMatcher.find()) {
                String nombre = nombreMatcher.group(1);
                return new NombreExpression(nombre, true); // Coincidencia exacta
            }
            
            // Patrón alternativo para nombre sin comillas
            Matcher nombreSinComillasMatcher = NOMBRE_SIN_COMILLAS_PATTERN.matcher(query);
            if (nombreSinComillasMatcher.find()) {
                String nombre = nombreSinComillasMatcher.group(1);
                return new NombreExpression(nombre, true); // Coincidencia exacta
            }
            
            // Patrón alternativo para marca sin comillas
            Matcher marcaSinComillasMatcher = MARCA_SIN_COMILLAS_PATTERN.matcher(query);
            if (marcaSinComillasMatcher.find()) {
                String marca = marcaSinComillasMatcher.group(1);
                return new MarcaExpression(marca);
            }
            
            // Si no encuentra ningún patrón conocido, intentamos interpretar como lenguaje natural
            try {
                return parseNaturalLanguage(query);
            } catch (Exception e) {
                throw new IllegalArgumentException("No se pudo interpretar la consulta: " + query);
            }
        } catch (Exception e) {
            // Si ocurre cualquier error, devolvemos AllProductsExpression para no interrumpir el flujo
            System.err.println("Error al parsear consulta: " + e.getMessage());
            return new AllProductsExpression();
        }
    }
    
    /**
     * Parsea una consulta en lenguaje natural más amigable.
     * 
     * @param query Consulta en lenguaje natural (ej: "productos baratos de la marca Bimbo")
     * @return Expression que representa la consulta
     */
    public static Expression parseNaturalLanguage(String query) {
        query = query.toLowerCase().trim();
        
        // Inicializar áreas si es necesario
        initAreas();
        
        Map<String, Expression> expressions = new HashMap<>();
        
        // Detectar patrones de lenguaje natural para precio
        if (query.contains("barato") || query.contains("económico") || query.contains("economico")) {
            // Detectar si la consulta menciona "MySQLTech" para ajustar el umbral
            if (query.toLowerCase().contains("mysqltech")) {
                expressions.put("precio", new PrecioBajoExpression(350.0)); // Umbral más alto para MySQLTech
            } else {
                expressions.put("precio", new PrecioBajoExpression(50.0)); // Define tu umbral de "barato"
            }
        } else if (query.contains("muy barato") || query.contains("super barato") || query.contains("súper barato")) {
            expressions.put("precio", new PrecioBajoExpression(25.0)); // Umbral más bajo para "muy barato"
        }
        
        // Detectar patrones de lenguaje natural para área
        // Añadir "frutas" al mapa si no existe ya
        if (!areasMap.containsKey("frutas")) {
            areasMap.put("frutas", 2); // Suponiendo que el ID 2 es para Frutas
        }
        if (!areasMap.containsKey("fruta")) {
            areasMap.put("fruta", 2);
        }

        for (Map.Entry<String, Integer> entry : areasMap.entrySet()) {
            String areaName = entry.getKey();
            if (query.contains(areaName)) {
                expressions.put("area", new AreaExpression(entry.getValue()));
                break;
            }
        }
        
        // Detectar patrones para disponibilidad
        if (query.contains("disponible") || query.contains("en stock") || query.contains("hay")) {
            expressions.put("disponibilidad", new DisponibilidadExpression(0)); // Mayor a 0 unidades
        } else if (query.contains("mucha existencia") || query.contains("abundante")) {
            expressions.put("disponibilidad", new DisponibilidadExpression(20)); // Mayor a 20 unidades
        }
        
        // Detectar marca - patrón mejorado para incluir "de la marca"
        Pattern marcaPattern = Pattern.compile(
            "marca\\s+\"?([^\"]+)\"?|" +  // marca X o marca "X"
            "de\\s+(la\\s+)?marca\\s+\"?([^\"]+)\"?|" +  // de marca X, de la marca X
            "de\\s+\"?([^\"]+)\"?"  // de X
        );
        Matcher marcaMatcher = marcaPattern.matcher(query);
        if (marcaMatcher.find()) {
            String marca = null;
            for (int i = 1; i <= marcaMatcher.groupCount(); i++) {
                if (marcaMatcher.group(i) != null && !marcaMatcher.group(i).equals("la")) {
                    marca = marcaMatcher.group(i);
                    break;
                }
            }
            if (marca != null) {
                expressions.put("marca", new MarcaExpression(marca));
            }
        }
        
        // Si menciona una marca específica directamente
        String[] marcasComunes = {"bimbo", "coca", "pepsi", "lala", "alpura", "sabritas", "gamesa", "nestle", "nestlé", "asdasd", "mysqltech"};
        for (String marca : marcasComunes) {
            if (query.contains(marca)) {
                expressions.put("marca", new MarcaExpression(marca));
                break;
            }
        }
        
        // Detectar nombre del producto
        if (query.contains("producto ") || query.contains(" producto") || query.contains("productos llamados") || 
            query.contains("llamado ") || query.contains(" llamados") || query.contains("nombre")) {
            
            // Intentar extraer el nombre del producto - mejorado para capturar palabras con espacios y caracteres especiales
            Pattern nombrePattern = Pattern.compile(
                "(?:producto(?:s)?\\s+(?:llamado(?:s)?\\s+)?\"([^\"]+)\"|nombre\\s+\"([^\"]+)\"|llamado(?:s)?\\s+\"([^\"]+)\")");
            Matcher nombreMatcher = nombrePattern.matcher(query);
            if (nombreMatcher.find()) {
                String nombre = nombreMatcher.group(1);
                if (nombre == null) nombre = nombreMatcher.group(2);
                if (nombre == null) nombre = nombreMatcher.group(3);
                
                if (nombre != null) {
                    expressions.put("nombre", new NombreExpression(nombre, true)); // Buscar coincidencia exacta
                }
            } else {
                // Intentar capturar nombre sin comillas (solo palabras simples)
                Pattern nombreSimplePattern = Pattern.compile(
                    "(?:producto(?:s)?\\s+(?:llamado(?:s)?\\s+)?(\\w+)|nombre\\s+(\\w+)|llamado(?:s)?\\s+(\\w+))");
                Matcher nombreSimpleMatcher = nombreSimplePattern.matcher(query);
                if (nombreSimpleMatcher.find()) {
                    String nombre = nombreSimpleMatcher.group(1);
                    if (nombre == null) nombre = nombreSimpleMatcher.group(2);
                    if (nombre == null) nombre = nombreSimpleMatcher.group(3);
                    
                    if (nombre != null) {
                        expressions.put("nombre", new NombreExpression(nombre, false)); // Buscar coincidencia parcial
                    }
                }
            }
        }
        
        // Combinar expresiones con AND
        Expression resultExpr = null;
        for (Expression expr : expressions.values()) {
            if (resultExpr == null) {
                resultExpr = expr;
            } else {
                resultExpr = new AndExpression(resultExpr, expr);
            }
        }
        
        return resultExpr != null ? resultExpr : new AllProductsExpression();
    }
}