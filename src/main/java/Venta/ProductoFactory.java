package Venta;

import DBObjetos.Producto;

public class ProductoFactory {
    public static Producto crearProducto(int productoID, String codigoBarras, String nombre, double precio, int cantidad) {
        return new Producto(productoID, codigoBarras, nombre, precio, cantidad);
    }
}