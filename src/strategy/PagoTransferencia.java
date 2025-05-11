package strategy;

import DBObjetos.Producto;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Implementación de la estrategia de pago mediante transferencia bancaria.
 * Maneja los pagos realizados a través de transferencias, registrando
 * la información necesaria para el seguimiento.
 * 
 * @author Carlos
 */
public class PagoTransferencia implements EstrategiaPago {
    
    private String numeroReferencia;
    private String banco;
    
    /**
     * Constructor para pago por transferencia.
     * 
     * @param numeroReferencia Número de referencia de la transferencia
     * @param banco Banco desde el que se realizó la transferencia
     */
    public PagoTransferencia(String numeroReferencia, String banco) {
        this.numeroReferencia = numeroReferencia;
        this.banco = banco;
    }

    @Override
    public boolean procesarPago(List<Producto> productos, double total, double montoRecibido) {
        if (montoRecibido < total) {
            JOptionPane.showMessageDialog(null, 
                "El monto transferido (" + String.format("%.2f", montoRecibido) + 
                ") es menor que el total a pagar (" + String.format("%.2f", total) + ")", 
                "Transferencia Insuficiente", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Verificar que el número de referencia sea válido (simulación)
        if (numeroReferencia == null || numeroReferencia.isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "Debe proporcionar un número de referencia válido para la transferencia.", 
                "Referencia Inválida", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }

    @Override
    public double calcularCambio(double montoRecibido, double total) {
        // Normalmente no hay cambio en transferencias
        // Si el monto transferido es mayor, se podría gestionar un reembolso
        return 0.0;
    }

    @Override
    public String getNombreMetodoPago() {
        return "Transferencia Bancaria";
    }

    @Override
    public boolean requiereValidacion() {
        return true; // Transferencias requieren validación
    }

    @Override
    public String getInfoComprobante() {
        return "Pago por transferencia bancaria\n" + 
               "Banco: " + banco + "\n" + 
               "Referencia: " + numeroReferencia;
    }
    
    /**
     * Obtener el número de referencia de la transferencia.
     * 
     * @return String con el número de referencia
     */
    public String getNumeroReferencia() {
        return numeroReferencia;
    }
    
    /**
     * Obtener el banco desde el que se realizó la transferencia.
     * 
     * @return String con el nombre del banco
     */
    public String getBanco() {
        return banco;
    }
}
