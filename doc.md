# Abstract Factory Pattern Documentation

## Introducción

El patrón Abstract Factory (Fábrica Abstracta) es un patrón de diseño creacional que proporciona una interfaz para crear familias de objetos relacionados o dependientes sin especificar sus clases concretas. En otras palabras, este patrón permite la creación de objetos donde la decisión sobre qué tipo concreto de objeto crear se delega a subclases específicas.

## Diagrama UML



## Implementación en el Proyecto

En este proyecto, el patrón Abstract Factory se utiliza para crear diferentes tipos de productos (perecederos y no perecederos) de manera uniforme a través de una interfaz común.

### Componentes Principales

1. **ProductoFactory (Abstract Factory)**: 
   - Define una interfaz abstracta para crear productos.
   - Proporciona un método de fábrica estático `getFactory` que devuelve la fábrica concreta adecuada.
   - Declara el método abstracto `crearProducto` que deben implementar las fábricas concretas.

2. **ProductoPerecederoFactory (Concrete Factory)**:
   - Implementa `crearProducto` para crear productos perecederos con una fecha de caducidad.
   - Incluye un método extendido que permite especificar más detalles específicos.

3. **ProductoNoPerecederoFactory (Concrete Factory)**:
   - Implementa `crearProducto` para crear productos no perecederos sin fecha de caducidad.
   - Incluye un método extendido para detalles adicionales.

4. **Producto (Product)**:
   - Representa el producto creado por las fábricas.
   - Utiliza el patrón Builder para una construcción flexible.

### Flujo de Trabajo

1. El cliente solicita una fábrica específica usando `ProductoFactory.getFactory("tipoProducto")`.
2. La fábrica concreta se utiliza para crear instancias de productos con características específicas.
3. Los productos se crean con configuraciones diferentes según el tipo de fábrica.

## Ventajas del Patrón Abstract Factory en este Proyecto

### 1. Centralización de la Creación de Objetos

La creación de productos está centralizada en las clases de fábrica, lo que permite controlar el proceso de creación desde un solo punto. Esto facilita la gestión y mantenimiento del código, especialmente cuando se trata de un sistema de inventario donde pueden existir diversos tipos de productos.

### 2. Encapsulamiento de la Lógica de Creación

La lógica para crear diferentes tipos de productos está encapsulada en sus respectivas fábricas. Por ejemplo, la lógica para asignar una fecha de caducidad predeterminada a productos perecederos está contenida dentro de `ProductoPerecederoFactory`, lo que facilita la modificación de esta lógica sin afectar al resto del sistema.

### 3. Flexibilidad y Extensibilidad

El diseño permite agregar fácilmente nuevos tipos de productos sin modificar el código existente. Si en el futuro se necesita un nuevo tipo de producto (por ejemplo, "ProductoRefrigerado"), solo se necesita:
- Crear una nueva subclase de `ProductoFactory`
- Actualizar el método `getFactory` para incluir el nuevo tipo

### 4. Consistencia en la Creación de Objetos

Garantiza que los productos creados sigan siempre un patrón consistente. Los productos perecederos siempre tendrán una fecha de caducidad, mientras que los no perecederos nunca la tendrán, evitando errores de configuración.

### 5. Facilita el Testing

La separación clara de responsabilidades facilita las pruebas unitarias. Se pueden probar las fábricas de manera aislada, como se muestra en la clase `Main` creada para este propósito.

### 6. Integración con el Patrón Builder

El proyecto combina eficazmente Abstract Factory con el patrón Builder, lo que proporciona una flexibilidad adicional en la configuración de los objetos producidos.

## Utilidad en el Contexto del Proyecto

En un sistema de gestión de inventario (que parece ser el objetivo de este proyecto), la aplicación del patrón Abstract Factory es particularmente útil por las siguientes razones:

### 1. Diversidad de Productos

Un sistema de inventario maneja diferentes tipos de productos con características distintas. Los productos perecederos requieren seguimiento de fechas de caducidad, mientras que los no perecederos tienen otras consideraciones. El patrón facilita la gestión de esta diversidad.

### 2. Reglas de Negocio Específicas

Cada tipo de producto puede tener reglas de negocio específicas. Por ejemplo, los productos perecederos automáticamente reciben una fecha de caducidad predeterminada. Estas reglas se encapsulan en las fábricas concretas.

### 3. Mantenimiento Simplificado

Cuando las reglas de negocio cambian (por ejemplo, si cambia el período predeterminado de caducidad), solo es necesario modificar la fábrica concreta correspondiente, sin afectar al resto del sistema.

### 4. Interfaz Unificada

Proporciona una interfaz unificada para la creación de productos, lo que simplifica el código cliente. El código que necesita crear productos no necesita conocer los detalles específicos de cada tipo.

## Conclusión

La implementación del patrón Abstract Factory en este proyecto proporciona una solución elegante y extensible para la creación de diferentes tipos de productos. Combinado con el patrón Builder, ofrece una gran flexibilidad mientras mantiene la simplicidad en el uso.

Este diseño facilita la evolución del sistema a medida que surgen nuevos requisitos, permitiendo que el código sea más mantenible y menos propenso a errores relacionados con la creación y configuración de objetos.
