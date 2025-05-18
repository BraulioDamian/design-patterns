package PatronInterpreter;

import DBObjetos.Producto;
import java.util.List;

/**
 * Interfaz base para el patrón Interpreter.
 * Todas las expresiones interpretables deben implementar esta interfaz.
 */
public interface Expression {
    /**
     * Interpreta la expresión y devuelve una lista de productos que cumplen con la condición.
     * @param productos Lista de productos sobre la que se aplica la interpretación
     * @return Lista de productos filtrados según la expresión
     */
    List<Producto> interpret(List<Producto> productos);
}
