Implementación del Patrón Chain of Responsibility en el Módulo de Notificaciones
Objetivo
Refactorizar el sistema de notificaciones (correo electrónico y WhatsApp) utilizando el patrón Chain of Responsibility (CoR) para:

Desacoplar los canales de notificación.
Garantizar que solo un canal procese la solicitud (a menos que falle).
Facilitar la adición de nuevos canales en el futuro.

Cambios Realizados
1. Estructura de Archivos
Se creó un nuevo paquete Venta.notificaciones con las clases:
└── main/
    └── java/
        └── Venta/
            ├── notificaciones/
            │   ├── NotificacionHandler.java   # Handler abstracto
            │   ├── CorreoHandler.java         # Handler para correo
            │   ├── WhatsAppHandler.java       # Handler para WhatsApp
            │   └── NotificacionChain.java     # Constructor de la cadena
            └── Cobro.java                     # Cliente que usa la cadena


3. Clases Clave
NotificacionHandler.java
javaCopiarpublic abstract class NotificacionHandler {
    private NotificacionHandler next;
    
    public NotificacionHandler setNext(NotificacionHandler next) {
        this.next = next;
        return next;
    }

    public void handleRequest(String destinatario, String asunto, String contenido, String archivo) {
        if (next != null) next.handleRequest(destinatario, asunto, contenido, archivo);
    }
}
CorreoHandler.java y WhatsAppHandler.java

Lógica: Intentan enviar la notificación. Si fallan, pasan al siguiente handler.
Cambio crítico: No llaman a super.handleRequest() si tienen éxito.

NotificacionChain.java
javaCopiarpublic class NotificacionChain {
    private NotificacionHandler chain;

    public NotificacionChain() {
        buildChain();
    }

    private void buildChain() {
        this.chain = new CorreoHandler();
        chain.setNext(new WhatsAppHandler()); // Encadenamiento
    }

    public void enviarNotificacion(String destinatario, String asunto, String contenido, String archivo) {
        chain.handleRequest(destinatario, asunto, contenido, archivo);
    }
}
3. Modificaciones en Cobro.java

Se eliminó la inyección directa de ServicioEnvio.
Se integró la cadena de notificaciones:

javaCopiarNotificacionChain notificacionChain = new NotificacionChain();
notificacionChain.enviarNotificacion(emailDestino, "Su Ticket", "Detalles", pdfPath);
UML 
![Screenshot 2025-04-06 174800](https://github.com/user-attachments/assets/63b52f7c-80ba-4cc9-b1c1-636ad7b1e023)

Beneficios Obtenidos

Flexibilidad: Añadir un nuevo canal (ej: SMS) requiere solo crear un nuevo Handler.
Mantenibilidad: Cada handler tiene una única responsabilidad.
Tolerancia a fallos: Si un canal falla, se intenta el siguiente automáticamente.

Pruebas Realizadas
EscenarioResultado EsperadoCorreo exitosoSolo envía correo. No ejecuta WhatsApp.Correo fallaIntenta WhatsApp. Si funciona, se detiene.Ambos fallanEjecuta FallbackHandler (si existe).
