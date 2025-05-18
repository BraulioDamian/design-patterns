package PatronInterpreter;

import DBObjetos.Producto;
import java.util.List;

/**
 * Expresión que devuelve todos los productos sin aplicar filtros.
 * Útil como caso por defecto o cuando una consulta no contiene condiciones.
 */
public class AllProductsExpression implements Expression {
    
    @Override
    public List<Producto> interpret(List<Producto> productos) {
        return productos; // Devuelve la lista sin modificar
    }
}
