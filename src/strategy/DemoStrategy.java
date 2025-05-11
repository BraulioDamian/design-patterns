package strategy;

import DBObjetos.Producto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase para demostar el funcionamiento del patrón Strategy
 * para procesar pagos con diferentes métodos.
 * 
 * @author Carlos
 */
public class DemoStrategy {
    
    /**
     * Método principal para demostrar el patrón Strategy.
     * 
     * @param args Argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        // Crear algunos productos de ejemplo
        List<Producto> productosCompra = crearProductosEjemplo();
        
        // Calcular el total de la compra
        double totalCompra = calcularTotal(productosCompra);
        
        System.out.println("== DEMOSTRACIÓN DEL PATRÓN STRATEGY PARA MÉTODOS DE PAGO ==");
        System.out.println("Productos en la compra:");
        mostrarProductos(productosCompra);
        System.out.println("\nTotal a pagar: $" + String.format("%.2f", totalCompra));
        
        // Demostrar pago en efectivo
        System.out.println("\n=== PAGO EN EFECTIVO ===");
        EstrategiaPago estrategiaEfectivo = new PagoEfectivo();
        ProcesadorPago procesador = new ProcesadorPago(estrategiaEfectivo);
        
        double montoPagado = 1200.00; // Ejemplo: el cliente paga con $1200
        System.out.println("Monto pagado: $" + String.format("%.2f", montoPagado));
        
        boolean pagoExitoso = procesador.realizarPago(productosCompra, totalCompra, montoPagado);
        if (pagoExitoso) {
            double cambio = procesador.obtenerCambio(montoPagado, totalCompra);
            System.out.println("Pago exitoso con " + procesador.getMetodoPago());
            System.out.println("Cambio a devolver: $" + String.format("%.2f", cambio));
            System.out.println("Información para comprobante: " + procesador.getInfoComprobante());
        } else {
            System.out.println("El pago en efectivo no fue exitoso.");
        }
        
        // Demostrar pago con tarjeta
        System.out.println("\n=== PAGO CON TARJETA ===");
        EstrategiaPago estrategiaTarjeta = new PagoTarjeta("4567", "Crédito");
        procesador.setEstrategiaPago(estrategiaTarjeta);
        
        montoPagado = totalCompra; // Con tarjeta, el monto exacto
        System.out.println("Monto autorizado: $" + String.format("%.2f", montoPagado));
        
        pagoExitoso = procesador.realizarPago(productosCompra, totalCompra, montoPagado);
        if (pagoExitoso) {
            System.out.println("Pago exitoso con " + procesador.getMetodoPago());
            System.out.println("Información para comprobante: " + procesador.getInfoComprobante());
        } else {
            System.out.println("El pago con tarjeta no fue exitoso.");
        }
        
        // Demostrar pago con transferencia
        System.out.println("\n=== PAGO CON TRANSFERENCIA ===");
        EstrategiaPago estrategiaTransferencia = new PagoTransferencia("TR-123456", "BBVA");
        procesador.setEstrategiaPago(estrategiaTransferencia);
        
        montoPagado = totalCompra; // Transferencia por el monto exacto
        System.out.println("Monto transferido: $" + String.format("%.2f", montoPagado));
        
        pagoExitoso = procesador.realizarPago(productosCompra, totalCompra, montoPagado);
        if (pagoExitoso) {
            System.out.println("Pago exitoso con " + procesador.getMetodoPago());
            System.out.println("Información para comprobante: " + procesador.getInfoComprobante());
        } else {
            System.out.println("El pago con transferencia no fue exitoso.");
        }
        
        // Ejemplo de pago fallido (monto insuficiente)
        System.out.println("\n=== EJEMPLO DE PAGO FALLIDO ===");
        procesador.setEstrategiaPago(estrategiaEfectivo);
        
        montoPagado = 500.00; // Monto insuficiente
        System.out.println("Monto pagado: $" + String.format("%.2f", montoPagado));
        
        pagoExitoso = procesador.realizarPago(productosCompra, totalCompra, montoPagado);
        if (pagoExitoso) {
            double cambio = procesador.obtenerCambio(montoPagado, totalCompra);
            System.out.println("Pago exitoso con " + procesador.getMetodoPago());
            System.out.println("Cambio a devolver: $" + String.format("%.2f", cambio));
        } else {
            System.out.println("El pago no fue exitoso: monto insuficiente.");
        }
    }
    
    /**
     * Crea una lista de productos de ejemplo para la demostración.
     * 
     * @return Lista de productos
     */
    private static List<Producto> crearProductosEjemplo() {
        List<Producto> productos = new ArrayList<>();
        
        // Crear algunos productos
        Producto p1 = new Producto();
        p1.setProductoID(1);
        p1.setNombre("Leche entera");
        p1.setMarca("Alpura");
        p1.setPrecio(23.50);
        p1.setCantidad(2);
        
        Producto p2 = new Producto();
        p2.setProductoID(2);
        p2.setNombre("Pan de caja");
        p2.setMarca("Bimbo");
        p2.setPrecio(35.00);
        p2.setCantidad(1);
        
        Producto p3 = new Producto();
        p3.setProductoID(3);
        p3.setNombre("Refresco cola");
        p3.setMarca("Coca-Cola");
        p3.setPrecio(18.50);
        p3.setCantidad(3);
        
        Producto p4 = new Producto();
        p4.setProductoID(4);
        p4.setNombre("Pasta dental");
        p4.setMarca("Colgate");
        p4.setPrecio(45.00);
        p4.setCantidad(1);
        
        Producto p5 = new Producto();
        p5.setProductoID(5);
        p5.setNombre("Arroz");
        p5.setMarca("Verde Valle");
        p5.setPrecio(32.00);
        p5.setCantidad(2);
        
        productos.add(p1);
        productos.add(p2);
        productos.add(p3);
        productos.add(p4);
        productos.add(p5);
        
        return productos;
    }
    
    /**
     * Calcula el total de la compra sumando los precios de los productos.
     * 
     * @param productos Lista de productos
     * @return Total de la compra
     */
    private static double calcularTotal(List<Producto> productos) {
        double total = 0.0;
        for (Producto p : productos) {
            total += p.getPrecio() * p.getCantidad();
        }
        return total;
    }
    
    /**
     * Muestra la información de los productos en consola.
     * 
     * @param productos Lista de productos a mostrar
     */
    private static void mostrarProductos(List<Producto> productos) {
        System.out.println("ID | Nombre | Marca | Precio | Cantidad | Subtotal");
        System.out.println("--------------------------------------------------");
        for (Producto p : productos) {
            double subtotal = p.getPrecio() * p.getCantidad();
            System.out.printf("%2d | %-15s | %-10s | $%6.2f | %8d | $%7.2f%n", 
                    p.getProductoID(), p.getNombre(), p.getMarca(), 
                    p.getPrecio(), p.getCantidad(), subtotal);
        }
    }
}
