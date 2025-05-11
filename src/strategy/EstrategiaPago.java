package strategy;

import java.util.List;
import DBObjetos.Producto;

/**
 * Interfaz que define la estrategia para procesar pagos.
 * Esta interfaz permite implementar diferentes formas de pago
 * siguiendo el patrón Strategy.
 * 
 * @author Carlos
 */
public interface EstrategiaPago {
    
    /**
     * Procesa el pago con la estrategia específica.
     * 
     * @param productos Lista de productos que se están comprando
     * @param total Monto total a pagar
     * @param montoRecibido Monto entregado por el cliente
     * @return true si el pago fue exitoso, false en caso contrario
     */
    boolean procesarPago(List<Producto> productos, double total, double montoRecibido);
    
    /**
     * Obtiene información sobre el cambio a devolver al cliente.
     * 
     * @param montoRecibido Monto entregado por el cliente
     * @param total Monto total a pagar
     * @return Monto a devolver al cliente como cambio
     */
    double calcularCambio(double montoRecibido, double total);
    
    /**
     * Obtiene el nombre descriptivo del método de pago.
     * 
     * @return String con el nombre del método de pago
     */
    String getNombreMetodoPago();
    
    /**
     * Verifica si el pago requiere validación adicional.
     * 
     * @return true si requiere validación, false en caso contrario
     */
    boolean requiereValidacion();
    
    /**
     * Genera información adicional para el comprobante de pago.
     * 
     * @return String con información para el ticket o null si no hay información adicional
     */
    String getInfoComprobante();
}
