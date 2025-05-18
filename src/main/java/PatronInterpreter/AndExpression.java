package PatronInterpreter;

import DBObjetos.Producto;
import java.util.List;

/**
 * Expresión no terminal que representa la operación lógica AND entre dos expresiones.
 */
public class AndExpression implements Expression {
    private Expression expr1;
    private Expression expr2;
    
    public AndExpression(Expression expr1, Expression expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
    }
    
    @Override
    public List<Producto> interpret(List<Producto> productos) {
        List<Producto> primerResultado = expr1.interpret(productos);
        return expr2.interpret(primerResultado);
    }
}
