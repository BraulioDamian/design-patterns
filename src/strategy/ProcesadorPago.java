package strategy;

import DBObjetos.Producto;
import java.util.List;

/**
 * Clase contexto que utiliza la estrategia de pago seleccionada.
 * Implementa el patrón Strategy permitiendo cambiar el algoritmo
 * de pago en tiempo de ejecución.
 * 
 * @author Carlos
 */
public class ProcesadorPago {
    
    private EstrategiaPago estrategiaPago;
    
    /**
     * Constructor para el procesador de pago.
     * 
     * @param estrategiaPago Estrategia de pago inicial
     */
    public ProcesadorPago(EstrategiaPago estrategiaPago) {
        this.estrategiaPago = estrategiaPago;
    }
    
    /**
     * Cambiar la estrategia de pago en tiempo de ejecución.
     * 
     * @param estrategiaPago Nueva estrategia de pago a utilizar
     */
    public void setEstrategiaPago(EstrategiaPago estrategiaPago) {
        this.estrategiaPago = estrategiaPago;
    }
    
    /**
     * Procesa el pago utilizando la estrategia seleccionada.
     * 
     * @param productos Lista de productos a comprar
     * @param total Monto total a pagar
     * @param montoRecibido Monto entregado por el cliente
     * @return true si el pago fue exitoso, false en caso contrario
     */
    public boolean realizarPago(List<Producto> productos, double total, double montoRecibido) {
        if (estrategiaPago == null) {
            throw new IllegalStateException("No se ha definido una estrategia de pago");
        }
        
        return estrategiaPago.procesarPago(productos, total, montoRecibido);
    }
    
    /**
     * Calcula el cambio utilizando la estrategia seleccionada.
     * 
     * @param montoRecibido Monto entregado por el cliente
     * @param total Monto total de la compra
     * @return Monto a devolver como cambio
     */
    public double obtenerCambio(double montoRecibido, double total) {
        if (estrategiaPago == null) {
            throw new IllegalStateException("No se ha definido una estrategia de pago");
        }
        
        return estrategiaPago.calcularCambio(montoRecibido, total);
    }
    
    /**
     * Obtener el nombre del método de pago actual.
     * 
     * @return String con el nombre del método de pago
     */
    public String getMetodoPago() {
        if (estrategiaPago == null) {
            return "No definido";
        }
        
        return estrategiaPago.getNombreMetodoPago();
    }
    
    /**
     * Verificar si el método de pago requiere validación adicional.
     * 
     * @return true si requiere validación, false en caso contrario
     */
    public boolean requiereValidacion() {
        if (estrategiaPago == null) {
            return false;
        }
        
        return estrategiaPago.requiereValidacion();
    }
    
    /**
     * Obtener información adicional para el comprobante de pago.
     * 
     * @return String con información para el ticket
     */
    public String getInfoComprobante() {
        if (estrategiaPago == null) {
            return "Método de pago no definido";
        }
        
        return estrategiaPago.getInfoComprobante();
    }
    
    /**
     * Obtener la estrategia de pago actual.
     * 
     * @return Objeto EstrategiaPago actual
     */
    public EstrategiaPago getEstrategiaPago() {
        return estrategiaPago;
    }
}
