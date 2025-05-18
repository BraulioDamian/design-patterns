package PatronInterpreter;

import DBObjetos.Producto;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Expresión no terminal que representa la operación lógica OR entre dos expresiones.
 */
public class OrExpression implements Expression {
    private Expression expr1;
    private Expression expr2;
    
    public OrExpression(Expression expr1, Expression expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
    }
    
    @Override
    public List<Producto> interpret(List<Producto> productos) {
        List<Producto> primerResultado = expr1.interpret(productos);
        List<Producto> segundoResultado = expr2.interpret(productos);
        
        // Combinar los resultados sin duplicados
        Set<Producto> resultadoTotal = new HashSet<>(primerResultado);
        resultadoTotal.addAll(segundoResultado);
        
        return new ArrayList<>(resultadoTotal);
    }
}
