Se implementó el patrón de diseño Factory en la clase Venta para centralizar la creación de objetos Producto. Este cambio mejora la flexibilidad y mantenibilidad del código, ya que ahora la lógica de creación de productos está encapsulada en una clase separada (ProductoFactory).

1. Cambios Realizados.
  1.1 Creación de la Interfaz Producto.
    Se creó una nueva interfaz llamada Producto en el paquete DBObjetos. Esta interfaz define los métodos que deben implementar todas las clases que representen un producto.
    Código de la Interfaz Producto:
   
     package DBObjetos;
       public interface Producto2 {
         int getProductoID();
         String getNombre();
         double getPrecio();
         int getCantidad();
         void setCantidad(int cantidad);
       }

   Explicación:
    - La interfaz Producto define los métodos que deben implementar todas las clases que representen un producto.
    - Esto asegura que cualquier clase que implemente la interfaz Producto tenga los métodos getProductoID, getNombre, getPrecio, getCantidad y setCantidad.

  1.2 Implementación de la Interfaz en la Clase Producto
    La clase Producto ya existente se modificó para implementar la interfaz Producto. Esto significa que la clase Producto debe proporcionar implementaciones para todos los métodos definidos en la interfaz.
    Código de la Clase Producto:

      package DBObjetos;

      public class Producto implements Producto2 {
        private int productoID;
        private String codigoBarras;
        private String nombre;
        private double precio;
        private int cantidad;

        public Producto(int productoID, String nombre, double precio, int cantidad) {
          this.productoID = productoID;
          this.nombre = nombre;
          this.precio = precio;
          this.cantidad = cantidad;
        }

        public Producto(int productoID, String codigoBarras, String nombre, double precio, int cantidad) {
          this.productoID = productoID;
          this.codigoBarras = codigoBarras;
          this.nombre = nombre;
          this.precio = precio;
          this.cantidad = cantidad;
        }    

        @Override
        public int getProductoID() {
          return productoID;
        }

        @Override
        public String getNombre() {
          return nombre;
        }

        @Override
        public double getPrecio() {
          return precio;
      }

        @Override
        public int getCantidad() {
          return cantidad;
        }

        @Override
        public void setCantidad(int cantidad) {
          this.cantidad = cantidad;
      }
    }

  Explicación:
    - La clase Producto ahora implementa la interfaz Producto, lo que significa que debe proporcionar implementaciones para los métodos getProductoID, getNombre, getPrecio, getCantidad y setCantidad.
    - Esto asegura que la clase Producto cumpla con el contrato definido por la interfaz.

  1.3 Uso de la Interfaz en la Factory
    La interfaz Producto se utiliza en la clase ProductoFactory para devolver objetos que implementen esta interfaz. Esto permite que la Factory sea más flexible, ya que puede devolver cualquier tipo de producto       que implemente la interfaz.
    Código de ProductoFactory:

      package Venta;

      import DBObjetos.Producto;

      public class ProductoFactory {
        public static Producto crearProducto(int productoID, String codigoBarras, String nombre, double precio, int cantidad) {
          return new Producto(productoID, codigoBarras, nombre, precio, cantidad);
        }
      }

  Explicación:
    - El método crearProducto devuelve un objeto de tipo Producto (la interfaz), pero en realidad está creando una instancia de la clase Producto.
    - Esto permite que en el futuro puedas crear otros tipos de productos (por ejemplo, ProductoDigital, ProductoFisico) que implementen la interfaz Producto, sin necesidad de modificar la Factory.

2. Ejemplo de uso
  Creación de un Producto
    Cuando el usuario selecciona un producto en la interfaz gráfica, se llama al método agregarProductoACobroYCerrarTabla. Este método usa la ProductoFactory para crear un nuevo objeto Producto (que implementa       la interfaz Producto) y agregarlo a la tabla de cobro.
  Flujo:
1. El usuario selecciona un producto.
2. El método agregarProductoACobroYCerrarTabla obtiene los detalles del producto seleccionado.
3. Usa la ProductoFactory para crear un objeto Producto (que implementa la interfaz Producto).
4. Agrega el producto a la tabla de cobro.
