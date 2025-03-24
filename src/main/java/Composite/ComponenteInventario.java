package Composite;

import java.util.List;

// ComponenteInventario.java
public interface ComponenteInventario {
    double getPrecioTotal();
    void aplicarDescuento(double porcentaje);
    void agregar(ComponenteInventario componente);
    void eliminar(ComponenteInventario componente);
    List<ComponenteInventario> getHijos();
    String getNombre();
}