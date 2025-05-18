package PatronInterpreter;

import DBObjetos.Producto;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Expresión terminal para filtrar productos por marca.
 */
public class MarcaExpression implements Expression {
    private String marca;
    private boolean exactMatch;
    
    /**
     * Constructor para búsqueda de marca.
     * 
     * @param marca Marca a buscar
     * @param exactMatch Si es true, busca coincidencia exacta; si es false, busca coincidencia parcial
     */
    public MarcaExpression(String marca, boolean exactMatch) {
        this.marca = marca.trim();
        this.exactMatch = exactMatch;
    }
    
    /**
     * Constructor por defecto que usa coincidencia parcial.
     * 
     * @param marca Marca a buscar
     */
    public MarcaExpression(String marca) {
        this(marca, false);
    }
    
    @Override
    public List<Producto> interpret(List<Producto> productos) {
        return productos.stream()
                .filter(p -> p.getMarca() != null && 
                           (exactMatch ? 
                               p.getMarca().equalsIgnoreCase(marca) : 
                               p.getMarca().toLowerCase().contains(marca.toLowerCase())))
                .collect(Collectors.toList());
    }
    
    @Override
    public String toString() {
        return exactMatch ? 
               "Marca es '" + marca + "'" : 
               "Marca contiene '" + marca + "'";
    }
}
