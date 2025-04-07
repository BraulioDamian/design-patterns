package Mediator;

import DBObjetos.Inventario;
import DBObjetos.Producto;
import DBObjetos.Usuario;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * Demo gráfico del patrón mediador
 */
public class MediadorDemo extends JFrame {
    
    private static final Logger LOGGER = Logger.getLogger(MediadorDemo.class.getName());
    
    private final JTextArea logArea;
    private final ComponenteInventario componenteInventario;
    private final ComponenteVenta componenteVenta;
    private final ComponenteMenuPrincipal componenteMenu;
    private final MediadorConcreto mediador;
    
    public MediadorDemo() {
        // Configurar ventana
        setTitle("Demostración de Patrón Mediador");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Crear área de log
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.CENTER);
        
        // Crear panel de acciones
        JPanel actionPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        
        // Configurar logger
        configureLogger();
        
        // Crear mediador y componentes
        mediador = MediadorConcreto.getInstance();
        componenteInventario = new ComponenteInventario("Demo");
        componenteVenta = new ComponenteVenta("Demo");
        componenteMenu = new ComponenteMenuPrincipal();
        
        // Registrar componentes con el mediador
        mediador.registrarComponente(componenteInventario);
        mediador.registrarComponente(componenteVenta);
        mediador.registrarComponente(componenteMenu);
        
        // Añadir botones para simular eventos
        JButton btnInventario = new JButton("Simular Cambio Inventario");
        btnInventario.addActionListener((ActionEvent e) -> simularCambioInventario());
        
        JButton btnVenta = new JButton("Simular Nueva Venta");
        btnVenta.addActionListener((ActionEvent e) -> simularNuevaVenta());
        
        JButton btnNotificacion = new JButton("Enviar Notificación");
        btnNotificacion.addActionListener((ActionEvent e) -> enviarNotificacion());
        
        JButton btnNavegacion = new JButton("Simular Navegación");
        btnNavegacion.addActionListener((ActionEvent e) -> simularNavegacion());
        
        JButton btnLimpiar = new JButton("Limpiar Log");
        btnLimpiar.addActionListener((ActionEvent e) -> logArea.setText(""));
        
        JButton btnSalir = new JButton("Salir");
        btnSalir.addActionListener((ActionEvent e) -> dispose());
        
        // Añadir botones al panel
        actionPanel.add(btnInventario);
        actionPanel.add(btnVenta);
        actionPanel.add(btnNotificacion);
        actionPanel.add(btnNavegacion);
        actionPanel.add(btnLimpiar);
        actionPanel.add(btnSalir);
        
        add(actionPanel, BorderLayout.SOUTH);
        
        // Manejar cierre de ventana
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                LOGGER.info("Cerrando demo de mediador");
            }
        });
        
        // Inicializar log
        log("Demostración del patrón mediador iniciada");
        log("Componentes registrados con el mediador: Inventario, Venta, Menú Principal");
    }
    
    private void configureLogger() {
        // Crear un handler personalizado para redirigir los logs al JTextArea
        ConsoleHandler handler = new ConsoleHandler() {
            @Override
            public void publish(java.util.logging.LogRecord record) {
                SwingUtilities.invokeLater(() -> {
                    logArea.append(record.getLevel() + ": " + record.getMessage() + "\n");
                    logArea.setCaretPosition(logArea.getDocument().getLength());
                });
            }
        };
        
        // Configurar Logger raíz para mostrar todos los mensajes
        Logger rootLogger = Logger.getLogger("");
        rootLogger.setLevel(Level.INFO);
        rootLogger.addHandler(handler);
    }
    
    private void log(String message) {
        LOGGER.info(message);
    }
    
    private void simularCambioInventario() {
        log("\n--- Simulando cambio en inventario ---");
        
        try {
            // Crear inventario para la simulación
            Inventario inv = new Inventario(12345, "Producto Demo", "Marca Test", 10, 
                  "250ml", "Bebidas", 15.99);
            
            // Notificar cambio usando inventario
            componenteInventario.notificarCambioInventario(inv);
            
            // Alternativamente, podemos usar un Producto
            log("Simulando cambio usando Producto...");
            Producto producto = new Producto.ProductoBuilder()
                .productoID(1)
                .nombre("Producto Demo 2")
                .unidadesDisponibles(5)
                .precio(29.99)
                .build();
            
            componenteInventario.notificarCambioProducto(producto);
        } catch (Exception e) {
            log("Error al simular cambio de inventario: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void simularNuevaVenta() {
        log("\n--- Simulando nueva venta ---");
        
        try {
            // Simular una venta directamente
            componenteVenta.simularVenta(1, 3, 19.99);
            
            // También podemos simular vía notificación
            log("Simulando venta vía notificación...");
            Map<String, Object> datos = Map.of(
                "producto_id", 2,
                "cantidad", 1,
                "precio", 59.99,
                "total", 59.99
            );
            
            componenteVenta.recibirNotificacion("SIMULACION_VENTA", datos);
        } catch (Exception e) {
            log("Error al simular venta: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void enviarNotificacion() {
        log("\n--- Enviando notificación desde el menú principal ---");
        componenteMenu.enviarNotificacion("Esta es una notificación de prueba del mediador");
    }
    
    private void simularNavegacion() {
        log("\n--- Simulando navegación entre pantallas ---");
        String[] destinos = {"INVENTARIO", "VENTA", "USUARIOS", "CONFIGURACIONES", "GRAFICAS"};
        String destino = destinos[(int)(Math.random() * destinos.length)];
        
        log("Solicitando navegación a: " + destino);
        componenteMenu.navegarA(destino);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MediadorDemo().setVisible(true);
        });
    }
}
