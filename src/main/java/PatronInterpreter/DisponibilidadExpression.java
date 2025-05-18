package PatronInterpreter;

import DBObjetos.Producto;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Expresión terminal para filtrar productos con disponibilidad mayor a un valor.
 */
public class DisponibilidadExpression implements Expression {
    private int cantidadMinima;
    
    public DisponibilidadExpression(int cantidadMinima) {
        this.cantidadMinima = cantidadMinima;
    }
    
    @Override
    public List<Producto> interpret(List<Producto> productos) {
        return productos.stream()
                .filter(p -> p.getUnidadesDisponibles() > cantidadMinima)
                .collect(Collectors.toList());
    }
}
