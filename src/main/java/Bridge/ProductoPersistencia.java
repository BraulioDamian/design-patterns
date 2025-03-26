package Bridge;

import DBObjetos.Producto;
import java.util.List;

// Implementación de la interfaz de persistencia
public interface ProductoPersistencia {
    // Métodos básicos de persistencia
    boolean guardar(Producto producto);
    Producto obtenerPorId(int id);
    List<Producto> obtenerTodos();
    boolean actualizar(Producto producto);
    boolean eliminar(int id);
}

