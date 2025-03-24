# Patrón Adapter - Documentación

## Estructura del Patrón Adapter

Se implementó el patrón Adapter para desacoplar la lógica de envío de tickets de las implementaciones concretas (correo/WhatsApp).

### Componentes clave:

#### Interfaz ServicioEnvio
Define el contrato para enviar mensajes.

```java
public interface ServicioEnvio {
    void enviar(String destinatario, String asunto, String contenido, String rutaArchivo);
}
```

#### Adaptadores:
- **MandarCorreosAdapter**: Adapta la clase existente MandarCorreos.
- **WhatsAppAdapter**: Integra Twilio para enviar mensajes por WhatsApp.

## Implementación de los Adaptadores

### MandarCorreosAdapter.java

```java
public class MandarCorreosAdapter implements ServicioEnvio {
    private MandarCorreos mandarCorreos = new MandarCorreos();
    
    @Override
    public void enviar(String emailDestino, String asunto, String contenido, String pdfPath) {
        mandarCorreos.enviarArchivo(emailDestino, asunto, contenido, pdfPath);
    }
}
```

### WhatsAppAdapter.java (con Twilio)

```java
public class WhatsAppAdapter implements ServicioEnvio {
    private static final String ACCOUNT_SID = "ACXXXXXXXXXXXXXXXX"; // Credenciales de Twilio
    private static final String AUTH_TOKEN = "tu_auth_token";
    private static final String TWILIO_NUMBER = "whatsapp:+14155238886"; // Número Sandbox
    
    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN); // Inicialización única
    }
    
    @Override
    public void enviar(String numero, String asunto, String contenido, String rutaArchivo) {
        PhoneNumber to = new PhoneNumber("whatsapp:" + numero);
        Message.creator(to, new PhoneNumber(TWILIO_NUMBER), contenido).create();
    }
}
```

## Modificaciones en Cobro.java

- **Inyección de dependencia**: Se añadió un campo ServicioEnvio para usar cualquier adaptador.
- **Selector de método de envío**: Opcionalmente, se puede agregar un JComboBox para elegir entre correo/WhatsApp.

```java
public class Cobro extends JFrame {
    private ServicioEnvio servicioEnvio;
    
    // Constructor modificado
    public Cobro(double total, List<Producto> productos, ServicioEnvio servicioEnvio) {
        this.servicioEnvio = servicioEnvio;
        // ... (código existente)
    }
    
    // Uso del servicio en el botón "Aceptar"
    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {
        servicioEnvio.enviar(destinatario, "Ticket", "Detalles del ticket", pdfPath);
    }
}
```

## Uso en Venta.java

Al crear la ventana Cobro, se elige el adaptador deseado:

```java
// Envío por correo
ServicioEnvio servicioCorreo = new MandarCorreosAdapter();
Cobro cobroCorreo = new Cobro(total, productos, servicioCorreo);

// Envío por WhatsApp
ServicioEnvio servicioWhatsApp = new WhatsAppAdapter();
Cobro cobroWhatsApp = new Cobro(total, productos, servicioWhatsApp);
```

## Diagrama UML

![Diagrama UML del patrón Adapter](https://github.com/user-attachments/assets/9e161215-2f89-4a1d-b63a-3400eafb6740)
