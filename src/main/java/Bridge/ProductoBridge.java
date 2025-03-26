package Bridge;

import DBObjetos.Producto;
import java.util.List;


public class ProductoBridge {
    protected ProductoPersistencia persistencia;

    public ProductoBridge(ProductoPersistencia persistencia) {
        this.persistencia = persistencia;
    }

    public boolean guardarProducto(Producto producto) {
        return persistencia.guardar(producto);
    }

    public Producto obtenerProducto(int id) {
        return persistencia.obtenerPorId(id);
    }

    public List<Producto> obtenerTodosProductos() {
        return persistencia.obtenerTodos();
    }

    public boolean actualizarProducto(Producto producto) {
        return persistencia.actualizar(producto);
    }

    public boolean eliminarProducto(int id) {
        return persistencia.eliminar(id);
    }

}
