# Implementación del Patrón Memento en el Sistema de Cobro

## Propósito

Este proyecto implementa el patrón de diseño Memento para mejorar la funcionalidad de la clase `Cobro.java` dentro del sistema de tienda de abarrotes. El patrón Memento permite:

1. **Guardar y restaurar estados** durante una transacción de cobro
2. **Implementar funcionalidad de deshacer/rehacer** durante el proceso de cobro
3. **Mantener un historial de transacciones** que puede ser consultado posteriormente
4. **Restaurar transacciones anteriores** en caso de ser necesario

## ¿Qué es el Patrón Memento?

El patrón Memento es un patrón de diseño de comportamiento que permite:

* Capturar y externalizar el estado interno de un objeto sin violar la encapsulación
* Guardar este estado para poder restaurarlo posteriormente
* Proporcionar una forma de recuperar estados previos sin exponer la implementación interna

El patrón está compuesto por tres elementos principales:
* **Originador**: El objeto cuyo estado se quiere preservar (en este caso, la clase `Cobro`)
* **Memento**: El objeto que almacena el estado interno del originador
* **Caretaker**: El objeto responsable de mantener los mementos

## Implementación

### Estructura de Clases

La implementación consta de las siguientes clases:

1. **CobroMemento.java**
   * Almacena el estado de una transacción de cobro
   * Contiene valores como: total, monto recibido, cambio, método de pago, correo, etc.

2. **CobroCaretaker.java**
   * Gestiona los estados del cobro durante una transacción activa
   * Permite operaciones de deshacer (undo) y rehacer (redo)
   * Mantiene un índice del estado actual

3. **Cobro.java (modificado)**
   * Implementa la funcionalidad para guardar y restaurar su estado
   * Incluye nuevos botones para deshacer y rehacer operaciones
   * Integra el patrón Memento con la interfaz de usuario existente

4. **HistorialTransacciones.java**
   * Implementa el patrón Singleton para mantener un historial global de transacciones
   * Almacena transacciones completadas para consulta posterior
   * Permite filtrar transacciones por período de tiempo

5. **InfoTransaccionDialog.java** y **HistorialTransaccionesDialog.java**
   * Proporcionan interfaces de usuario para interactuar con el historial de transacciones
   * Permiten ver detalles y restaurar estados anteriores

### Cambios Realizados a la Clase Original

La clase `Cobro.java` original ha sido modificada para:

1. Integrar el patrón Memento:
   * Agregar un objeto `CobroCaretaker` para gestionar los estados
   * Implementar métodos para guardar y restaurar estados
   * Añadir botones de interfaz para deshacer/rehacer

2. Mejorar la arquitectura:
   * Mejor separación de responsabilidades
   * Mayor modularidad del código
   * Uso de interfaces para la comunicación entre componentes

3. Añadir funcionalidades:
   * Registro de transacciones en el historial
   * Posibilidad de consultar transacciones pasadas
   * Restaurar estados de transacciones anteriores

## Comparación con la Versión Original

### Versión Original

La clase `Cobro.java` original:

* **Limitaciones**:
  * No permitía deshacer/rehacer operaciones durante el proceso de cobro
  * No mantenía un historial de transacciones pasadas
  * No era posible restaurar el estado de una transacción anterior
  * Carecía de una separación clara de responsabilidades

* **Funcionamiento**:
  * Funcionaba como una unidad monolítica
  * El estado del cobro solo existía durante la transacción activa
  * Una vez completada o cancelada la transacción, el estado se perdía permanentemente

### Versión Mejorada con Memento

La nueva implementación:

* **Ventajas**:
  * Permite navegación completa por el historial de cambios durante una transacción
  * Proporciona persistencia de transacciones pasadas
  * Mejora la experiencia del usuario con capacidades de deshacer/rehacer
  * Facilita la recuperación ante errores de usuario

* **Mejoras de Diseño**:
  * Separación clara de responsabilidades en clases dedicadas
  * Mayor mantenibilidad del código
  * Mejor estructura para pruebas unitarias
  * Fácil ampliación con nuevas funcionalidades

## Casos de Uso

1. **Durante una transacción activa**:
   * El cajero puede deshacer cambios al introducir montos incorrectos
   * Es posible rehacer operaciones deshechas si fue un error
   * Cada cambio en los campos se guarda automáticamente en el historial

2. **Para transacciones pasadas**:
   * El administrador puede consultar el historial de transacciones
   * Es posible filtrar transacciones por fecha
   * Se pueden ver detalles completos de cada transacción
   * En caso necesario, se puede restaurar el estado de una transacción anterior

## Instrucciones de Uso

### Interfaz de Cobro Mejorada

La interfaz de cobro ahora incluye:

* **Botón Deshacer (⟲)**: Restaura el estado anterior del cobro
* **Botón Rehacer (⟳)**: Aplica nuevamente un cambio deshecho

### Historial de Transacciones

Para acceder al historial:

1. Abrir el diálogo de historial desde la ventana principal
2. Utilizar los filtros de fecha para encontrar transacciones específicas
3. Hacer doble clic en una transacción para ver sus detalles
4. Si es necesario, usar el botón "Restaurar Estado" para recrear una transacción

## Conclusión

La implementación del patrón Memento en el sistema de cobro proporciona una mejora significativa en la experiencia del usuario y en la robustez de la aplicación. Esta nueva arquitectura no solo soluciona las limitaciones de la versión original, sino que establece una base sólida para futuras mejoras y extensiones del sistema.

El código ha sido diseñado siguiendo principios SOLID, lo que garantiza su mantenibilidad y extensibilidad a largo plazo. La clara separación de responsabilidades facilita la comprensión del código y permite a otros desarrolladores trabajar en el proyecto sin dificultad.


# Implementación del Patrón Facade

## Descripción del Proyecto

Este proyecto implementa un sistema de cobro para un Punto de Venta (POS) en Java, diseñado para procesar pagos, validar métodos de pago, calcular el cambio, generar tickets en formato PDF y enviarlos por correo electrónico. La versión actual utiliza el patrón de diseño **Facade** para encapsular la lógica compleja y mejorar la modularidad, mantenibilidad y claridad del código.

El sistema interactúa con una interfaz gráfica (Swing) y delega responsabilidades a subsistemas como la generación de PDFs (`iText`) y el envío de correos electrónicos (`EnvioTicket`).

---

## Estructura del Proyecto

- **Paquete `CobroFacade`**: Contiene la clase `PagoFacade`, que actúa como la fachada principal.
- **Clase `Cobro`**: Interfaz gráfica que interactúa con el usuario y utiliza `PagoFacade` para procesar las operaciones de cobro.
- **Subsistemas**:
  - Generación de tickets PDF (usando `iText`).
  - Envío de correos electrónicos (delegado a `EnvioTicket`).
  - Gestión de sesiones (`SesionManager`).
- **Dependencias**:
  - `iText` para la generación de PDFs.
  - `javax.swing` para la interfaz gráfica.

---

## Implementación del Patrón Facade

El patrón **Facade** se implementa mediante la clase `PagoFacade`, que proporciona una interfaz simplificada para las siguientes operaciones:
1. **Validación del método de pago** (`validarMetodoDePago`): Verifica si se seleccionó un método de pago (efectivo o tarjeta).
2. **Validación del monto** (`validarMonto`): Comprueba que el monto ingresado sea suficiente para cubrir el total.
3. **Cálculo del cambio** (`calcularCambio`): Calcula el cambio a devolver al cliente.
4. **Generación del ticket PDF** (`generarPDF`): Crea un archivo PDF con los detalles de la venta.
5. **Envío del ticket por correo** (`enviarTicketPorCorreo`): Envía el ticket al correo especificado o permite continuar sin enviarlo.

El cliente (clase `Cobro`) interactúa únicamente con `PagoFacade`, sin necesidad de conocer los detalles internos de los subsistemas.

---

## Comparación entre la Versión Anterior y la Actual

### Versión Anterior
En la versión anterior, toda la lógica estaba contenida dentro del método `btnAceptarActionPerformed` y la función `generarPDF` de la clase `Cobro`. Esto generaba los siguientes problemas:

- **Alta cohesión y acoplamiento**: La clase `Cobro` manejaba directamente la validación, el cálculo del cambio, la generación del PDF y el envío por correo, lo que la hacía dependiente de múltiples subsistemas (`iText`, `EnvioTicket`, etc.).
- **Código monolítico**: La lógica estaba mezclada con la interfaz gráfica, dificultando su reutilización y mantenimiento.
- **Falta de modularidad**: Cualquier cambio en la generación del PDF o en el envío del correo requería modificar directamente la clase `Cobro`.
- **Ejemplo de código**:
  ```java
  private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // Primero, verifica si algún método de pago ha sido seleccionado.
        if (!efectivo.isSelected() && !tarjeta.isSelected()) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un método de pago.");
            return;
        }

        // Intenta procesar el pago y generar/enviar el ticket.
        try {
            double pago = Double.parseDouble(recibi.getText());  // Intenta obtener el pago ingresado.
            if (pago < pre) {
                JOptionPane.showMessageDialog(this, "El monto pagado no es suficiente para cubrir el total de la compra.");
                return;
            }
            cambio = pago - pre;
            camb.setText("$" + String.format("%.2f", cambio));

            // Genera el ticket y obtiene la ruta del PDF generado.
            String pdfPath = generarPDF(productos, pre, pago, cambio);
            if (pdfPath != null) {
                // Verifica si se ha ingresado un correo electrónico.
                String emailDestino = txtCorreo.getText();
                if (emailDestino.isEmpty()) {
                    int opcion = JOptionPane.showConfirmDialog(this, "No ha ingresado un correo electrónico. ¿Desea continuar sin enviar el ticket por correo?", "Correo no ingresado", JOptionPane.YES_NO_OPTION);
                    if (opcion == JOptionPane.NO_OPTION) {
                        return; // Si el usuario selecciona NO, no se procede.
                    }
                } else {
                    EnvioTicket.enviarConArchivo(emailDestino, pdfPath);  // Enviar el PDF por correo
                    JOptionPane.showMessageDialog(this, "El ticket ha sido enviado correctamente a: " + emailDestino);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Error al generar el ticket PDF.");
            }
            
            // Notifica a la ventana Venta que la venta se ha completado
            if (ventaListener != null) {
                ventaListener.onVentaCompleta();
            }

            // Cierra la ventana Cobro
            dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, introduzca un monto válido en el campo 'Recibí'.");
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
      dispose();
    }//GEN-LAST:event_jButton4ActionPerformed


    
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
            
            document.add(new Paragraph(""));
            document.add(new Paragraph(""));
            
            document.add(new Paragraph("Ticket de compra",normal));
            document.add(new Paragraph("Cajero: " + usuario.getNombreCompleto()));
            
            document.add(new Paragraph(""));
            document.add(new Paragraph(""));
            
            Paragraph fechaParrafo = new Paragraph("Fecha y Hora: " + fechaHoraFormateada, normal);
            fechaParrafo.setAlignment(Element.ALIGN_RIGHT);
            document.add(fechaParrafo);            
            document.add(new Paragraph(" "));
            document.add(new Paragraph("")); 
            
            document.add(new Paragraph("RFC: VECJ880326", normal));
            document.add(new Paragraph("Régimen fiscal: 601-Ley General de Personas Morales", normal));
            document.add(new Paragraph("Emitido en: Heroica Escuela Naval Militar 917, Reforma Centro, 68050 Oaxaca de Juárez, Oax", normal));
            document.add(new Paragraph(" "));
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
            document.add(new Paragraph(" "));
            
            
            Paragraph totalParrafo = new Paragraph(String.format("Total: $%.2f", total), bold);
            totalParrafo.setAlignment(Element.ALIGN_RIGHT);
            document.add(totalParrafo);

            Paragraph pagoParrafo = new Paragraph(String.format("Pago en efectivo: $%.2f", pago), normal);
            pagoParrafo.setAlignment(Element.ALIGN_RIGHT);
            document.add(pagoParrafo);

            Paragraph cambioParrafo = new Paragraph(String.format("Cambio: $%.2f", cambio), normal);
            cambioParrafo.setAlignment(Element.ALIGN_RIGHT);
            document.add(cambioParrafo);
                        
            document.add(new Paragraph(" "));            
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            Paragraph graciasParrafo = new Paragraph("¡GRACIAS POR SU COMPRA!", bold);
            graciasParrafo.setAlignment(Element.ALIGN_CENTER);
            document.add(graciasParrafo);
            
            
            document.close();

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(new File(filePath));  // Opcional: abrir el archivo automáticamente
            }

            return filePath;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar el ticket: " + e.getMessage());
            return null;
        }
    }



### Versión Actual (con Facade)

La versión actual introduce el patrón Facade mediante la clase `PagoFacade`, resolviendo los problemas anteriores:

- **Encapsulación**: La lógica de validación, cálculo, generación de PDFs y envío de correos se traslada a `PagoFacade`, aislando los subsistemas del cliente (`Cobro`).
- **Bajo acoplamiento**: `Cobro` solo depende de `PagoFacade` y no de los subsistemas subyacentes, como `iText` o `EnvioTicket`.
- **Modularidad y reutilización**: La clase `PagoFacade` puede ser reutilizada en otros contextos del sistema POS sin modificar la interfaz gráfica.
- **Código más limpio**: La lógica del evento `btnAceptarActionPerformed` se reduce a interacciones con `PagoFacade`, mejorando la legibilidad.

**Ejemplo de código actualizado**:

```java
private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {
    if (!pagoFacade.validarMetodoDePago(efectivo.isSelected(), tarjeta.isSelected())) {
        return;
    }
    String textoRecibi = recibi.getText().trim();
    if (textoRecibi.isEmpty() || !textoRecibi.matches("\\d+(\\.\\d+)?")) {
        JOptionPane.showMessageDialog(this, "Por favor, introduzca un monto válido.");
        return;
    }
    try {
        double pago = Double.parseDouble(textoRecibi);
        if (!pagoFacade.validarMonto(pago, pre)) {
            return;
        }
        cambio = pagoFacade.calcularCambio(pago, pre);
        camb.setText("$" + String.format("%.2f", cambio));
        String pdfPath = pagoFacade.generarPDF(productos, pre, pago, cambio);
        if (pdfPath != null) {
            boolean flag = pagoFacade.enviarTicketPorCorreo(txtCorreo.getText(), pdfPath);
            if (flag) {
                ventaListener.onVentaCompleta();
                dispose();
            }
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Por favor, introduzca un monto válido.");
    }
}

```
## Conclusión

La implementación del patrón Facade en este sistema de cobro no altera el funcionamiento esencial del programa: los usuarios siguen procesando pagos, generando tickets y enviándolos por correo de la misma manera. Sin embargo, la introducción de `PagoFacade` aporta beneficios significativos, como una mejor encapsulación de la lógica, un menor acoplamiento entre componentes y una mayor modularidad. Esto resulta en un código más limpio, fácil de mantener y reutilizable, lo que facilita futuras expansiones o modificaciones sin comprometer la estabilidad del sistema. En resumen, el patrón Facade mejora la estructura del proyecto sin sacrificar su funcionalidad original, alineándose con los principios de diseño de software robusto y escalable.