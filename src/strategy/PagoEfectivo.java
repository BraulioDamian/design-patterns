package strategy;

import DBObjetos.Producto;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Implementación de la estrategia de pago en efectivo.
 * Maneja los pagos realizados con dinero en efectivo, verificando
 * que el monto entregado sea suficiente y calculando el cambio.
 * 
 * @author Carlos
 */
public class PagoEfectivo implements EstrategiaPago {

    @Override
    public boolean procesarPago(List<Producto> productos, double total, double montoRecibido) {
        if (montoRecibido < total) {
            JOptionPane.showMessageDialog(null, 
                "El monto pagado (" + String.format("%.2f", montoRecibido) + ") es menor que el total a pagar (" + 
                String.format("%.2f", total) + ")", "Pago Insuficiente", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Aquí podrían ir validaciones adicionales o registro de la operación de pago
        return true;
    }

    @Override
    public double calcularCambio(double montoRecibido, double total) {
        return montoRecibido - total;
    }

    @Override
    public String getNombreMetodoPago() {
        return "Efectivo";
    }

    @Override
    public boolean requiereValidacion() {
        return false; // El efectivo no requiere validación adicional
    }

    @Override
    public String getInfoComprobante() {
        return "Pago en efectivo";
    }
}
