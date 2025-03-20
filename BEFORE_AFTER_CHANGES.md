# Comparación entre la Implementación Original y la Versión con Patrón Memento

## Análisis de la Clase Cobro Original

### Estructura Original

La clase `Cobro.java` original presentaba las siguientes características:

1. **Funcionalidad básica**:
   * Mostraba el total a pagar
   * Permitía ingresar el monto recibido
   * Calculaba el cambio
   * Generaba un ticket en PDF
   * Enviaba el ticket por correo electrónico

2. **Limitaciones**:
   * No permitía deshacer operaciones durante el proceso de cobro
   * Si el usuario cometía un error (por ejemplo, introducir un monto incorrecto), debía reiniciar la operación
   * No mantenía un historial de las transacciones realizadas
   * No existía forma de restaurar una transacción anterior
   * Estado volátil: una vez completada o cancelada la transacción, el estado se perdía

3. **Características técnicas**:
   * Implementación monolítica con alta cohesión entre componentes
   * Mezcla de lógica de negocio y presentación
   * Sin separación clara entre el estado del objeto y sus operaciones
   * Manejo limitado de excepciones y errores de usuario

### Fragmentos de código original relevantes

```java
// Listener para calcular cambio sin posibilidad de deshacer
private void agregarListeners() {
    recibi.getDocument().addDocumentListener(new DocumentListener() {
        public void changedUpdate(DocumentEvent e) {
            calcularCambio();
        }
        public void removeUpdate(DocumentEvent e) {
            calcularCambio();
        }
        public void insertUpdate(DocumentEvent e) {
            calcularCambio();
        }
    });
}

// Cálculo de cambio - sin capacidad de revertir cambios
private void calcularCambio() {
    try {
        double montoRecibido = Double.parseDouble(recibi.getText());
        if (montoRecibido >= pre) {
            cambio = montoRecibido - pre;
            camb.setText(String.format("$%.2f", cambio));
        } else {
            camb.setText("Insuficiente");
        }
    } catch (NumberFormatException e) {
        camb.setText("");
    }
}
```

## Implementación con Patrón Memento

### Mejoras Estructurales

1. **Separación de responsabilidades**:
   * `CobroMemento`: Encapsula exclusivamente el estado del cobro
   * `CobroCaretaker`: Gestiona los mementos y proporciona navegación
   * `Cobro`: Actúa como originador y se centra en la lógica de negocio

2. **Nueva funcionalidad**:
   * Capacidad de deshacer/rehacer operaciones durante el cobro
   * Historial completo de transacciones
   * Capacidad de restaurar estados anteriores
   * Persistencia de información de transacciones entre sesiones

3. **Características técnicas mejoradas**:
   * Mejor encapsulación: el estado se extrae en objetos dedicados
   * Mayor cohesión: cada clase tiene una responsabilidad clara
   * Mejor manejo de errores y excepciones
   * Mejor testabilidad: cada componente puede probarse aisladamente
   * Mayor extensibilidad: fácil de añadir nuevas funcionalidades

### Fragmentos de código nueva implementación

```java
// En la clase Cobro.java - guardar estado
private void guardarEstado() {
    // Obtener valores actuales
    double montoRecibido = 0;
    try {
        montoRecibido = Double.parseDouble(recibi.getText());
    } catch (NumberFormatException e) {
        // Si no es un número válido, se guarda como 0
    }
    
    // Crear y guardar memento
    CobroMemento memento = new CobroMemento(
        pre,
        montoRecibido,
        cambio,
        efectivo.isSelected(),
        tarjeta.isSelected(),
        txtCorreo.getText()
    );
    
    caretaker.saveMemento(memento);
    configurarBotonesHistorial();
}

// En la clase Cobro.java - restaurar estado
private void restaurarEstado(CobroMemento memento) {
    if (memento == null) return;
    
    // Restaurar valores
    pre = memento.getTotal();
    precio.setText("$" + String.format("%.2f", pre));
    precioEnLetras = converter.convertir(pre);
    letras.setText(precioEnLetras);
    
    recibi.setText(memento.getMontoRecibido() > 0 ? 
            String.format("%.2f", memento.getMontoRecibido()) : "");
    
    cambio = memento.getCambio();
    if (cambio > 0) {
        camb.setText(String.format("$%.2f", cambio));
    } else if (memento.getMontoRecibido() < pre && memento.getMontoRecibido() > 0) {
        camb.setText("Insuficiente");
    } else {
        camb.setText("");
    }
    
    efectivo.setSelected(memento.isEfectivoSeleccionado());
    tarjeta.setSelected(memento.isTarjetaSeleccionada());
    
    txtCorreo.setText(memento.getCorreo());
    
    configurarBotonesHistorial();
}

// En la clase Cobro.java - métodos para undo/redo
private void undo() {
    CobroMemento memento = caretaker.undo();
    if (memento != null) {
        restaurarEstado(memento);
    }
}

private void redo() {
    CobroMemento memento = caretaker.redo();
    if (memento != null) {
        restaurarEstado(memento);
    }
}
```

## Tabla Comparativa

| Característica | Implementación Original | Implementación con Memento |
|----------------|-------------------------|----------------------------|
| **Deshacer/Rehacer** | No disponible | Implementado con funcionalidad completa |
| **Restauración** | No posible | Capacidad de restaurar estados anteriores |
| **Encapsulación** | Baja (estado y comportamiento mezclados) | Alta (separación clara de responsabilidades) |
| **Manejo de errores** | Básico | Mejorado con recuperación de estados |
| **Testabilidad** | Difícil (componentes acoplados) | Fácil (componentes separados) |
| **Extensibilidad** | Limitada | Alta (fácil añadir nuevas funcionalidades) |
| **Código duplicado** | Presente | Reducido mediante abstracción |
| **Mantenibilidad** | Baja | Alta |

## Impacto en el Sistema

### Ventajas Operativas

1. **Para Cajeros**:
   * Menor probabilidad de errores al permitir deshacer operaciones
   * Mejor experiencia de usuario con capacidad de revertir acciones
   * Funcionamiento más intuitivo y flexible

2. **Para Administradores**:
   * Acceso completo al historial de transacciones
   * Capacidad de revisión y auditoría de operaciones
   * Posibilidad de restaurar estados para correcciones

3. **Para el Sistema**:
   * Mayor robustez frente a errores
   * Mejor trazabilidad de operaciones
   * Arquitectura más mantenible y extensible

