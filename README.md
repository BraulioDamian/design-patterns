# Implementación del Patrón Factory en el Sistema de Ventas

## Objetivo
Centralizar la creación de objetos `Producto` utilizando el patrón **Factory**, mejorando la mantenibilidad y escalabilidad del código.

## Cambios Realizados

### 1. Nueva Interfaz `Producto`
Se creó una nueva interfaz llamada Producto en el paquete DBObjetos. Esta interfaz define los métodos que deben implementar todas las clases que representen un producto. Código de la Interfaz Producto:
```java
public interface Producto2 {
    int getProductoID();
    String getNombre();
    double getPrecio();
    int getCantidad();
    void setCantidad(int cantidad);
}
```

### 2. Clase `Producto` (Implementación)
Implementación de la Interfaz en la Clase Producto La clase Producto ya existente se modificó para implementar la interfaz Producto. Esto significa que la clase Producto debe proporcionar implementaciones para todos los métodos definidos en la interfaz. Código de la Clase Producto:
```java
public class Producto implements Producto2 {
    // Atributos y métodos existentes...
    // Se mantuvo la misma funcionalidad pero ahora cumple con el contrato
}
```

### 3. Factory `ProductoFactory`
Nueva clase que encapsula la lógica de creación:
```java
public class ProductoFactory {
    public static Producto crearProducto(int id, String nombre, double precio, int cantidad) {
        return new Producto(id, nombre, precio, cantidad);
    }
}
```

### 4. Modificación en `Venta`
Se reemplazó la creación directa por el uso de la Factory:
```java
// Antes
Producto p = new Producto(...);

// Ahora
Producto p = ProductoFactory.crearProducto(...);
```

## Diagrama UML
![Diagrama UML del Patrón Factory](./src/main/resources/Icons/UML%20Factory.png)  