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
