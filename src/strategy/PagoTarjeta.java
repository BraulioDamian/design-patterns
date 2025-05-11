package strategy;

import DBObjetos.Producto;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Implementación de la estrategia de pago con tarjeta.
 * Maneja los pagos realizados con tarjetas de crédito o débito,
 * realizando las validaciones correspondientes.
 * 
 * @author Carlos
 */
public class PagoTarjeta implements EstrategiaPago {
    
    private String numeroTarjeta;
    private String tipoTarjeta; // "Crédito" o "Débito"
    
    /**
     * Constructor para pago con tarjeta.
     * 
     * @param numeroTarjeta Número de la tarjeta (últimos 4 dígitos)
     * @param tipoTarjeta Tipo de tarjeta (Crédito o Débito)
     */
    public PagoTarjeta(String numeroTarjeta, String tipoTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
        this.tipoTarjeta = tipoTarjeta;
    }

    @Override
    public boolean procesarPago(List<Producto> productos, double total, double montoRecibido) {
        // En el caso de tarjeta, el montoRecibido representa el monto autorizado
        if (montoRecibido < total) {
            JOptionPane.showMessageDialog(null, 
                "El monto autorizado (" + String.format("%.2f", montoRecibido) + 
                ") es menor que el total a pagar (" + String.format("%.2f", total) + ")", 
                "Autorización Insuficiente", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Simular procesamiento del pago con tarjeta
        boolean pagoAprobado = simularPagoTarjeta(total);
        
        if (!pagoAprobado) {
            JOptionPane.showMessageDialog(null, 
                "El pago con tarjeta fue rechazado. Por favor, intente con otro método de pago.", 
                "Pago Rechazado", JOptionPane.ERROR_MESSAGE);
        }
        
        return pagoAprobado;
    }
    
    /**
     * Simula el procesamiento del pago con tarjeta.
     * En un sistema real, aquí se conectaría con el gateway de pago.
     * 
     * @param monto Monto a pagar
     * @return true si el pago fue aprobado, false si fue rechazado
     */
    private boolean simularPagoTarjeta(double monto) {
        // Simulación simple: aprueba pagos menores a 10000
        return monto < 10000;
    }

    @Override
    public double calcularCambio(double montoRecibido, double total) {
        return 0.0; // No hay cambio en pagos con tarjeta
    }

    @Override
    public String getNombreMetodoPago() {
        return "Tarjeta " + tipoTarjeta;
    }

    @Override
    public boolean requiereValidacion() {
        return true; // Los pagos con tarjeta requieren validación
    }

    @Override
    public String getInfoComprobante() {
        return "Pago con tarjeta " + tipoTarjeta + "\nTerminación: " + numeroTarjeta;
    }
    
    /**
     * Obtener los últimos dígitos de la tarjeta.
     * 
     * @return String con los últimos dígitos de la tarjeta
     */
    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }
    
    /**
     * Obtener el tipo de tarjeta.
     * 
     * @return String con el tipo de tarjeta (Crédito o Débito)
     */
    public String getTipoTarjeta() {
        return tipoTarjeta;
    }
}
