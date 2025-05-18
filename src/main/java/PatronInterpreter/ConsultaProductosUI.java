package PatronInterpreter;

import DBObjetos.Producto;
import DBObjetos.Area;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 * Interfaz de usuario para demostrar el uso del patrón Interpreter
 */
public class ConsultaProductosUI extends JFrame {
    
    private InterpreterManager interpreterManager;
    private JTextField consultaTextField;
    private JTable resultadosTable;
    private DefaultTableModel tableModel;
    private JRadioButton formalRadio, naturalRadio;
    private JComboBox<String> areasComboBox;
    private JButton buscarPorAreaBtn;
    private JPanel criteriosPanel;
    private JPanel resultadosPanel;
    private JComboBox<String> precioComboBox;
    private JComboBox<String> disponiblesComboBox;
    private List<Area> listaAreas;
    
    public ConsultaProductosUI() {
        // Inicializar el InterpretManager
        interpreterManager = InterpreterManager.getInstance();
        
        // Configurar la ventana
        setTitle("Consulta de Productos - Patrón Interpreter");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel de consulta
        JPanel queryPanel = new JPanel();
        queryPanel.setLayout(new BoxLayout(queryPanel, BoxLayout.Y_AXIS));
        
        // Título
        JLabel titleLabel = new JLabel("Búsqueda Avanzada de Productos");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        queryPanel.add(titleLabel);
        
        // Panel para seleccionar tipo de consulta
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        formalRadio = new JRadioButton("Consulta Formal", true);
        naturalRadio = new JRadioButton("Lenguaje Natural", false);
        ButtonGroup group = new ButtonGroup();
        group.add(formalRadio);
        group.add(naturalRadio);
        radioPanel.add(formalRadio);
        radioPanel.add(naturalRadio);
        queryPanel.add(radioPanel);
        
        // Ejemplos de consultas según el tipo seleccionado
        JPanel ejemplosPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel ejemplosLabel = new JLabel("Ejemplos:");
        final JLabel ejemplosFormalLabel = new JLabel("precio < 100 AND area = 1, marca = \"Bimbo\"");
        final JLabel ejemplosNaturalLabel = new JLabel("productos baratos de la marca Bimbo");
        ejemplosNaturalLabel.setVisible(false);
        
        ejemplosPanel.add(ejemplosLabel);
        ejemplosPanel.add(ejemplosFormalLabel);
        ejemplosPanel.add(ejemplosNaturalLabel);
        queryPanel.add(ejemplosPanel);
        
        // Cambiar ejemplos cuando cambia el tipo de consulta
        ActionListener radioListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejemplosFormalLabel.setVisible(formalRadio.isSelected());
                ejemplosNaturalLabel.setVisible(naturalRadio.isSelected());
                criteriosPanel.setVisible(formalRadio.isSelected());
            }
        };
        formalRadio.addActionListener(radioListener);
        naturalRadio.addActionListener(radioListener);
        
        // Panel para ingresar consulta
        JPanel inputPanel = new JPanel(new BorderLayout());
        consultaTextField = new JTextField();
        JButton consultarButton = new JButton("Consultar");
        inputPanel.add(new JLabel("Consulta: "), BorderLayout.WEST);
        inputPanel.add(consultaTextField, BorderLayout.CENTER);
        inputPanel.add(consultarButton, BorderLayout.EAST);
        queryPanel.add(inputPanel);
        
        // Panel de criterios de búsqueda (para consulta formal)
        criteriosPanel = new JPanel(new GridLayout(0, 2, 10, 5));
        criteriosPanel.setBorder(BorderFactory.createTitledBorder("Criterios de Búsqueda"));
        
        // Combobox para Áreas
        JPanel areaPanel = new JPanel(new BorderLayout());
        areaPanel.add(new JLabel("Área: "), BorderLayout.WEST);
        areasComboBox = new JComboBox<>();
        areaPanel.add(areasComboBox, BorderLayout.CENTER);
        buscarPorAreaBtn = new JButton("Buscar por Área");
        areaPanel.add(buscarPorAreaBtn, BorderLayout.EAST);
        criteriosPanel.add(areaPanel);
        
        // Cargar áreas en el combobox
        cargarAreas();
        
        // Combobox para Precio
        JPanel precioPanel = new JPanel(new BorderLayout());
        precioPanel.add(new JLabel("Precio: "), BorderLayout.WEST);
        String[] precioOpciones = {"Todos", "Menos de $50", "Menos de $100", "Menos de $200"};
        precioComboBox = new JComboBox<>(precioOpciones);
        precioPanel.add(precioComboBox, BorderLayout.CENTER);
        JButton buscarPorPrecioBtn = new JButton("Buscar por Precio");
        precioPanel.add(buscarPorPrecioBtn, BorderLayout.EAST);
        criteriosPanel.add(precioPanel);
        
        // Combobox para Disponibilidad
        JPanel dispPanel = new JPanel(new BorderLayout());
        dispPanel.add(new JLabel("Disponibilidad: "), BorderLayout.WEST);
        String[] dispOpciones = {"Todos", "Más de 10 unidades", "Más de 20 unidades", "Más de 50 unidades"};
        disponiblesComboBox = new JComboBox<>(dispOpciones);
        dispPanel.add(disponiblesComboBox, BorderLayout.CENTER);
        JButton buscarPorDispBtn = new JButton("Buscar por Disponibilidad");
        dispPanel.add(buscarPorDispBtn, BorderLayout.EAST);
        criteriosPanel.add(dispPanel);
        
        // Botón para búsqueda combinada
        JPanel combinadaPanel = new JPanel(new BorderLayout());
        JButton busquedaCombinada = new JButton("Búsqueda Combinada");
        busquedaCombinada.setToolTipText("Buscar usando todos los criterios seleccionados");
        combinadaPanel.add(busquedaCombinada, BorderLayout.CENTER);
        criteriosPanel.add(combinadaPanel);
        
        queryPanel.add(criteriosPanel);
        
        // Botones de consultas predefinidas
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton economicosButton = new JButton("Productos < $50");
        JButton disponiblesButton = new JButton("Disponibles > 10");
        JButton todosButton = new JButton("Todos los Productos");
        
        botonesPanel.add(todosButton);
        botonesPanel.add(economicosButton);
        botonesPanel.add(disponiblesButton);
        queryPanel.add(botonesPanel);
        
        // Panel de resultados
        resultadosPanel = new JPanel(new BorderLayout());
        resultadosPanel.setBorder(BorderFactory.createTitledBorder("Resultados: 0 productos encontrados"));
        
        // Configurar tabla de resultados
        tableModel = new DefaultTableModel(
                new Object[][] {},
                new String[] {"ID", "Nombre", "Descripción", "Área", "Precio", "Disponibles", "Marca"}
        );
        resultadosTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultadosTable);
        resultadosPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Añadir paneles al panel principal
        mainPanel.add(queryPanel, BorderLayout.NORTH);
        mainPanel.add(resultadosPanel, BorderLayout.CENTER);
        
        // Añadir panel principal a la ventana
        add(mainPanel);
        
        // Configurar acciones de los botones
        consultarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarConsulta();
            }
        });
        
        buscarPorAreaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int areaSeleccionada = areasComboBox.getSelectedIndex();
                if (areaSeleccionada > 0) { // 0 es "Seleccionar Área"
                    Area area = listaAreas.get(areaSeleccionada - 1);
                    List<Producto> productos = interpreterManager.obtenerProductosPorArea(area.getAreaID());
                    mostrarResultados(productos);
                    mostrarExplicacionBusqueda(productos, new AreaExpression(area.getAreaID()));
                } else {
                    JOptionPane.showMessageDialog(ConsultaProductosUI.this, 
                            "Por favor seleccione un área", 
                            "Selección requerida", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        
        buscarPorPrecioBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int indice = precioComboBox.getSelectedIndex();
                if (indice > 0) {
                    double precioLimite = 0;
                    switch (indice) {
                        case 1: precioLimite = 50.0; break;
                        case 2: precioLimite = 100.0; break;
                        case 3: precioLimite = 200.0; break;
                    }
                    List<Producto> productos = interpreterManager.obtenerProductosEconomicos(precioLimite);
                    mostrarResultados(productos);
                    mostrarExplicacionBusqueda(productos, new PrecioBajoExpression(precioLimite));
                } else {
                    try {
                        List<Producto> productos = interpreterManager.ejecutarConsultaFormal("precio < 99999");
                        mostrarResultados(productos);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(ConsultaProductosUI.this, 
                                "Error al buscar productos: " + ex.getMessage(), 
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        buscarPorDispBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int indice = disponiblesComboBox.getSelectedIndex();
                if (indice > 0) {
                    int cantidadMinima = 0;
                    switch (indice) {
                        case 1: cantidadMinima = 10; break;
                        case 2: cantidadMinima = 20; break;
                        case 3: cantidadMinima = 50; break;
                    }
                    List<Producto> productos = interpreterManager.obtenerProductosDisponibles(cantidadMinima);
                    mostrarResultados(productos);
                    mostrarExplicacionBusqueda(productos, new DisponibilidadExpression(cantidadMinima - 1));
                } else {
                    try {
                        List<Producto> productos = interpreterManager.ejecutarConsultaFormal("disponible > 0");
                        mostrarResultados(productos);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(ConsultaProductosUI.this, 
                                "Error al buscar productos: " + ex.getMessage(), 
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        busquedaCombinada.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarBusquedaCombinada();
            }
        });
        
        economicosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Producto> productos = interpreterManager.obtenerProductosEconomicos(50.0);
                mostrarResultados(productos);
                mostrarExplicacionBusqueda(productos, new PrecioBajoExpression(50.0));
            }
        });
        
        disponiblesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Producto> productos = interpreterManager.obtenerProductosDisponibles(10);
                mostrarResultados(productos);
                mostrarExplicacionBusqueda(productos, new DisponibilidadExpression(9));
            }
        });
        
        todosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Usa AllProductsExpression directamente para mostrar todos los productos
                    Consultas.CONSULTASDAO dao = new Consultas.CONSULTASDAO(ConexionDB.Conexion_DB.getConexion());
                    List<Producto> todosLosProductos = dao.obtenerProductosConNombreArea();
                    
                    // Limpiar selecciones en los combobox
                    areasComboBox.setSelectedIndex(0);
                    precioComboBox.setSelectedIndex(0);
                    disponiblesComboBox.setSelectedIndex(0);
                    
                    // Mostrar todos los productos sin filtros
                    mostrarResultados(todosLosProductos);
                    JOptionPane.showMessageDialog(ConsultaProductosUI.this, 
                            "Mostrando todos los productos (" + todosLosProductos.size() + " productos)", 
                            "Información", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(ConsultaProductosUI.this, 
                            "Error al cargar todos los productos: " + ex.getMessage(), 
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    
    /**
     * Carga las áreas desde la base de datos en el ComboBox
     */
    private void cargarAreas() {
        DefaultComboBoxModel<String> areaModel = new DefaultComboBoxModel<>();
        areaModel.addElement("Seleccionar Área");
        
        try {
            Consultas.CONSULTASDAO dao = new Consultas.CONSULTASDAO(ConexionDB.Conexion_DB.getConexion());
            listaAreas = dao.obtenerAreas();
            
            for (Area area : listaAreas) {
                areaModel.addElement(area.getNombreArea());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                    "Error al cargar las áreas: " + ex.getMessage(), 
                    "Error de conexión", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        
        areasComboBox.setModel(areaModel);
    }
    
    /**
     * Realiza una búsqueda combinada usando todos los criterios seleccionados
     */
    private void realizarBusquedaCombinada() {
        try {
            // Construir consulta según los criterios seleccionados
            StringBuilder consulta = new StringBuilder();
            
            // Área
            int areaIndex = areasComboBox.getSelectedIndex();
            if (areaIndex > 0) {
                Area area = listaAreas.get(areaIndex - 1);
                consulta.append("area = ").append(area.getAreaID());
            }
            
            // Precio
            int precioIndex = precioComboBox.getSelectedIndex();
            if (precioIndex > 0) {
                double precioLimite = 0;
                switch (precioIndex) {
                    case 1: precioLimite = 50.0; break;
                    case 2: precioLimite = 100.0; break;
                    case 3: precioLimite = 200.0; break;
                }
                
                if (consulta.length() > 0) {
                    consulta.append(" AND ");
                }
                consulta.append("precio < ").append(precioLimite);
            }
            
            // Disponibilidad
            int dispIndex = disponiblesComboBox.getSelectedIndex();
            if (dispIndex > 0) {
                int cantidadMinima = 0;
                switch (dispIndex) {
                    case 1: cantidadMinima = 10; break;
                    case 2: cantidadMinima = 20; break;
                    case 3: cantidadMinima = 50; break;
                }
                
                if (consulta.length() > 0) {
                    consulta.append(" AND ");
                }
                consulta.append("disponible > ").append(cantidadMinima);
            }
            
            // Si no hay criterios seleccionados, mostrar todos los productos
            if (consulta.length() == 0) {
                consulta.append("disponible > -1");
            }
            
            // Ejecutar consulta
            List<Producto> productos = interpreterManager.ejecutarConsultaFormal(consulta.toString());
            mostrarResultados(productos);
            Expression expr = QueryParser.parse(consulta.toString());
            mostrarExplicacionBusqueda(productos, expr);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                    "Error al realizar la búsqueda combinada: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    /**
     * Ejecuta la consulta ingresada según el tipo seleccionado
     */
    private void ejecutarConsulta() {
        String consulta = consultaTextField.getText().trim();
        if (consulta.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                    "Por favor ingrese una consulta", 
                    "Consulta vacía", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        List<Producto> resultados;
        Expression expresion;
        try {
            if (formalRadio.isSelected()) {
                expresion = QueryParser.parse(consulta);
                resultados = interpreterManager.ejecutarConsultaFormal(consulta);
            } else {
                expresion = QueryParser.parseNaturalLanguage(consulta);
                resultados = interpreterManager.ejecutarConsultaNatural(consulta);
            }
            
            mostrarResultados(resultados);
            mostrarExplicacionBusqueda(resultados, expresion);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                    "Error al ejecutar la consulta: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    /**
     * Muestra los resultados en la tabla
     */
    private void mostrarResultados(List<Producto> productos) {
        // Limpiar tabla
        tableModel.setRowCount(0);
        
        // Añadir filas con los productos
        for (Producto p : productos) {
            tableModel.addRow(new Object[] {
                p.getProductoID(),
                p.getNombre(),
                p.getDescripcion(),
                p.getNombreArea(),
                p.getPrecio(),
                p.getUnidadesDisponibles(),
                p.getMarca()
            });
        }
        
        // Actualizar el título del panel para mostrar la cantidad de resultados
        ((javax.swing.border.TitledBorder)resultadosPanel.getBorder()).setTitle("Resultados: " + 
                productos.size() + " productos encontrados");
        resultadosPanel.repaint();
    }
    
    /**
     * Muestra una descripción de los resultados para dar más transparencia sobre el algoritmo de búsqueda
     * @param productos La lista de productos resultante
     * @param expression La expresión que se utilizó para la búsqueda
     */
    private void mostrarExplicacionBusqueda(List<Producto> productos, Expression expression) {
        // No hacemos nada aquí ya que el mensaje se muestra en mostrarResultados()
    }
    
    /**
     * Método principal para probar la interfaz
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new ConsultaProductosUI().setVisible(true);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, 
                            "Error al iniciar la aplicación: " + e.getMessage(), 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        });
    }
}
