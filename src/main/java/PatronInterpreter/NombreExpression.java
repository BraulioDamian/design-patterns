package PatronInterpreter;

import DBObjetos.Producto;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Expresión terminal para filtrar productos por nombre.
 */
public class NombreExpression implements Expression {
    private String nombre;
    private boolean exactMatch;
    
    /**
     * Constructor para búsqueda de nombre.
     * 
     * @param nombre Nombre o parte del nombre a buscar
     * @param exactMatch Si es true, busca coincidencia exacta; si es false, busca coincidencia parcial
     */
    public NombreExpression(String nombre, boolean exactMatch) {
        this.nombre = nombre;
        this.exactMatch = exactMatch;
    }
    
    /**
     * Constructor por defecto que usa coincidencia parcial
     * 
     * @param nombre Nombre o parte del nombre a buscar
     */
    public NombreExpression(String nombre) {
        this(nombre, false);
    }
    
    @Override
    public List<Producto> interpret(List<Producto> productos) {
        return productos.stream()
                .filter(p -> p.getNombre() != null && 
                           (exactMatch ? 
                               p.getNombre().equalsIgnoreCase(nombre) : 
                               p.getNombre().toLowerCase().contains(nombre.toLowerCase())))
                .collect(Collectors.toList());
    }
    
    @Override
    public String toString() {
        return exactMatch ? 
               "Nombre es '" + nombre + "'" : 
               "Nombre contiene '" + nombre + "'";
    }
}
