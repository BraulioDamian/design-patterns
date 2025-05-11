package strategy;

import DBObjetos.Producto;
import DBObjetos.Usuario;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;
import login.SesionManager;
import Venta.NumeroEnPalabras;
import Venta.EnvioTicket;

/**
 * Versión mejorada de la clase Cobro que utiliza el patrón Strategy.
 * Esta clase es un ejemplo de cómo se podría adaptar la clase Cobro
 * existente para utilizar el patrón Strategy.
 * 
 * @author Carlos
 */
public class CobroConStrategy {
    
    private double total; // Total a pagar
    private NumeroEnPalabras converter = new NumeroEnPalabras();
    private String totalEnLetras;
    private List<Producto> productos;  // Lista de productos para generar el ticket
    private ProcesadorPago procesadorPago;
    
    /**
     * Constructor para el cobro utilizando el patrón Strategy.
     * 
     * @param total Total a pagar
     * @param productos Lista de productos a cobrar
     */
    public CobroConStrategy(double total, List<Producto> productos) {
        this.total = total;
        this.productos = productos;
        this.totalEnLetras = converter.convertir(total);
        
        // Por defecto, inicializamos con la estrategia de pago en efectivo
        procesadorPago = new ProcesadorPago(new PagoEfectivo());
    }
    
    /**
     * Cambia la estrategia de pago a efectivo.
     */
    public void seleccionarPagoEfectivo() {
        procesadorPago.setEstrategiaPago(new PagoEfectivo());
        System.out.println("Método de pago cambiado a Efectivo");
    }
    
    /**
     * Cambia la estrategia de pago a tarjeta.
     * 
     * @param numeroTarjeta Últimos 4 dígitos de la tarjeta
     * @param tipoTarjeta Tipo de tarjeta (Crédito o Débito)
     */
    public void seleccionarPagoTarjeta(String numeroTarjeta, String tipoTarjeta) {
        procesadorPago.setEstrategiaPago(new PagoTarjeta(numeroTarjeta, tipoTarjeta));
        System.out.println("Método de pago cambiado a Tarjeta " + tipoTarjeta);
    }
    
    /**
     * Cambia la estrategia de pago a transferencia.
     * 
     * @param numeroReferencia Número de referencia de la transferencia
     * @param banco Banco desde el que se realizó la transferencia
     */
    public void seleccionarPagoTransferencia(String numeroReferencia, String banco) {
        procesadorPago.setEstrategiaPago(new PagoTransferencia(numeroReferencia, banco));
        System.out.println("Método de pago cambiado a Transferencia");
    }
    
    /**
     * Procesa el pago utilizando la estrategia seleccionada.
     * 
     * @param montoRecibido Monto entregado por el cliente
     * @return true si el pago fue exitoso, false en caso contrario
     */
    public boolean procesarPago(double montoRecibido) {
        boolean exitoso = procesadorPago.realizarPago(productos, total, montoRecibido);
        
        if (exitoso) {
            double cambio = procesadorPago.obtenerCambio(montoRecibido, total);
            System.out.println("Pago exitoso con " + procesadorPago.getMetodoPago());
            System.out.println("Total pagado: $" + String.format("%.2f", total));
            System.out.println("Cambio a devolver: $" + String.format("%.2f", cambio));
            
            // Generar el ticket
            String pdfPath = generarPDF(productos, total, montoRecibido, cambio);
            
            // Aquí podríamos añadir lógica para enviar el ticket por correo
            // o mostrarlo en pantalla
            if (pdfPath != null) {
                try {
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().open(new File(pdfPath));
                    }
                } catch (Exception e) {
                    System.err.println("Error al abrir el PDF: " + e.getMessage());
                }
            }
        } else {
            System.out.println("Pago fallido con " + procesadorPago.getMetodoPago());
        }
        
        return exitoso;
    }
    
    /**
     * Envía el ticket por correo electrónico.
     * 
     * @param correoDestino Dirección de correo electrónico del cliente
     * @param rutaPDF Ruta al archivo PDF del ticket
     * @return true si el envío fue exitoso, false en caso contrario
     */
    public boolean enviarTicketPorCorreo(String correoDestino, String rutaPDF) {
        try {
            EnvioTicket.enviarConArchivo(correoDestino, rutaPDF);
            System.out.println("Ticket enviado a " + correoDestino);
            return true;
        } catch (Exception e) {
            System.err.println("Error al enviar el ticket: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Genera el PDF del ticket.
     * 
     * @param productosSeleccionados Lista de productos comprados
     * @param total Total de la compra
     * @param pago Monto pagado por el cliente
     * @param cambio Cambio a devolver al cliente
     * @return Ruta al archivo PDF generado
     */
    private String generarPDF(List<Producto> productosSeleccionados, double total, double pago, double cambio) {
        Document document = new Document();
        String fileName = "Ticket_" + System.currentTimeMillis() + ".pdf";  // Nombre del archivo con marca de tiempo
        String filePath = "./tickets/" + fileName;  // Guardar en un directorio específico
        LocalDateTime fechaActual= LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fechaHoraFormateada = fechaActual.format(formato);

        // Asegúrate de que el directorio tickets existe o créalo
        new File("./tickets").mkdirs();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            
            Usuario usuario = SesionManager.getInstance().getUsuarioLogueado();

            Font bold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font normal = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Font gigante = FontFactory.getFont(FontFactory.HELVETICA,16);
            
            Paragraph Titulo = new Paragraph("ABARROTES DON LUIS", gigante);
            Titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(Titulo);   
            
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            
            document.add(new Paragraph("Ticket de compra",normal));
            document.add(new Paragraph("Cajero: " + usuario.getNombreCompleto()));
            
            document.add(new Paragraph(" "));
            
            Paragraph fechaParrafo = new Paragraph("Fecha y Hora: " + fechaHoraFormateada, normal);
            fechaParrafo.setAlignment(Element.ALIGN_RIGHT);
            document.add(fechaParrafo);            
            document.add(new Paragraph(" "));
            
            document.add(new Paragraph("RFC: VECJ880326", normal));
            document.add(new Paragraph("Régimen fiscal: 601-Ley General de Personas Morales", normal));
            document.add(new Paragraph("Emitido en: Heroica Escuela Naval Militar 917, Reforma Centro, 68050 Oaxaca de Juárez, Oax", normal));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(new float[]{1, 2, 1, 1, 1});
            table.setWidthPercentage(100);
            String[] headers = {"Código", "Producto", "Unidades", "Precio Uni.", "Importe"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Paragraph(header, bold));
                cell.setBorder(PdfPCell.NO_BORDER);
                table.addCell(cell);
            }

            for (Producto producto : productosSeleccionados) {
                PdfPCell cell = new PdfPCell(new Paragraph(String.valueOf(producto.getProductoID()), normal));
                cell.setBorder(PdfPCell.NO_BORDER);
                table.addCell(cell);
                cell = new PdfPCell(new Paragraph(producto.getNombre(), normal));
                cell.setBorder(PdfPCell.NO_BORDER);
                table.addCell(cell);
                cell = new PdfPCell(new Paragraph(String.valueOf(producto.getCantidad()), normal));
                cell.setBorder(PdfPCell.NO_BORDER);
                table.addCell(cell);
                cell = new PdfPCell(new Paragraph(String.format("$%.2f", producto.getPrecio()), normal));
                cell.setBorder(PdfPCell.NO_BORDER);
                table.addCell(cell);
                cell = new PdfPCell(new Paragraph(String.format("$%.2f", producto.getPrecio() * producto.getCantidad()), normal));
                cell.setBorder(PdfPCell.NO_BORDER);
                table.addCell(cell);
            }

            document.add(table);
            
            
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            
            Paragraph totalParrafo = new Paragraph(String.format("Total: $%.2f", total), bold);
            totalParrafo.setAlignment(Element.ALIGN_RIGHT);
            document.add(totalParrafo);

            // Añadir información específica del método de pago
            Paragraph metodoPagoParrafo = new Paragraph("Método de pago: " + procesadorPago.getMetodoPago(), normal);
            metodoPagoParrafo.setAlignment(Element.ALIGN_RIGHT);
            document.add(metodoPagoParrafo);
            
            String infoComprobante = procesadorPago.getInfoComprobante();
            if (infoComprobante != null && !infoComprobante.isEmpty()) {
                // Si es pago en efectivo, mostramos el pago y cambio
                if (procesadorPago.getEstrategiaPago() instanceof PagoEfectivo) {
                    Paragraph pagoParrafo = new Paragraph(String.format("Pago en efectivo: $%.2f", pago), normal);
                    pagoParrafo.setAlignment(Element.ALIGN_RIGHT);
                    document.add(pagoParrafo);
                    
                    Paragraph cambioParrafo = new Paragraph(String.format("Cambio: $%.2f", cambio), normal);
                    cambioParrafo.setAlignment(Element.ALIGN_RIGHT);
                    document.add(cambioParrafo);
                } else {
                    // Para otros métodos de pago, mostramos la información específica
                    Paragraph infoParrafo = new Paragraph(infoComprobante, normal);
                    infoParrafo.setAlignment(Element.ALIGN_RIGHT);
                    document.add(infoParrafo);
                }
            }
                        
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            Paragraph graciasParrafo = new Paragraph("¡GRACIAS POR SU COMPRA!", bold);
            graciasParrafo.setAlignment(Element.ALIGN_CENTER);
            document.add(graciasParrafo);
            
            document.close();
            
            return filePath;
        } catch (Exception e) {
            System.err.println("Error al generar el ticket: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Método principal para demostrar la funcionalidad.
     * 
     * @param args Argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        // Crear un objeto demo con productos y total
        List<Producto> productosDemo = DemoStrategy.crearProductosEjemplo();
        double totalDemo = 1000.00;
        
        CobroConStrategy cobro = new CobroConStrategy(totalDemo, productosDemo);
        
        // Ejemplo de pago en efectivo
        cobro.seleccionarPagoEfectivo();
        cobro.procesarPago(1200.00);
        
        // Ejemplo de pago con tarjeta
        cobro.seleccionarPagoTarjeta("1234", "Crédito");
        cobro.procesarPago(totalDemo);
        
        // Ejemplo de pago con transferencia
        cobro.seleccionarPagoTransferencia("TR-789456", "BBVA");
        cobro.procesarPago(totalDemo);
    }
    
    /**
     * Método auxiliar para crear productos de ejemplo.
     * 
     * @return Lista de productos para demostración
     */
    private static List<Producto> crearProductosEjemplo() {
        return DemoStrategy.crearProductosEjemplo();
    }
}
