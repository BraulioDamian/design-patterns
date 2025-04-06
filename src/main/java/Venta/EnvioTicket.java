/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Venta;

/**
 *
 * @author braul
 */
public class EnvioTicket {
    private ServicioEnvio servicioEnvio;
    
    public EnvioTicket(ServicioEnvio servicioEnvio){
        this.servicioEnvio=servicioEnvio;
    }
    public void enviarConArchivo(String emailDestino, String pdfPath){
        String asunto="Su Ticket de Compra";
        String contenido="Tu ticket es este. Encuentra adjunto el PDF con los detalles";
        servicioEnvio.enviar(emailDestino, asunto, contenido, pdfPath);
    }
}