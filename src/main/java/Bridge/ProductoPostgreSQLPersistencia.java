package Bridge;

import DBObjetos.Producto;

import java.util.ArrayList;
import java.util.List;


// Implementación concreta para PostgreSQL (ejemplo)
class ProductoPostgreSQLPersistencia implements ProductoPersistencia {
    // Implementación similar a MySQL, pero con detalles específicos de PostgreSQL
    // Se omite por brevedad, pero seguiría la misma estructura
    @Override
    public boolean guardar(Producto producto) {
        // Implementación específica para PostgreSQL
        return false;
    }

    @Override
    public Producto obtenerPorId(int id) {
        // Implementación específica para PostgreSQL
        return null;
    }

    @Override
    public List<Producto> obtenerTodos() {
        // Implementación específica para PostgreSQL
        return new ArrayList<>();
    }

    @Override
    public boolean actualizar(Producto producto) {
        // Implementación específica para PostgreSQL
        return false;
    }

    @Override
    public boolean eliminar(int id) {
        // Implementación específica para PostgreSQL
        return false;
    }
}
