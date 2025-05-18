package PatronInterpreter;

import DBObjetos.Producto;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Expresión terminal para filtrar productos con precio menor a un valor específico.
 */
public class PrecioBajoExpression implements Expression {
    private double precioLimite;
    
    public PrecioBajoExpression(double precioLimite) {
        this.precioLimite = precioLimite;
    }
    
    @Override
    public List<Producto> interpret(List<Producto> productos) {
        return productos.stream()
                .filter(p -> p.getPrecio() < precioLimite)
                .collect(Collectors.toList());
    }
}
