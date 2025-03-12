# Patrón Abstract Factory - Sistema de Generación de Reportes

Este directorio contiene la implementación del patrón Abstract Factory para la generación de reportes en el proyecto de la Tienda de Abarrotes.

## Descripción del Patrón

El patrón Abstract Factory proporciona una interfaz para crear familias de objetos relacionados o dependientes sin especificar sus clases concretas. En esta implementación, lo utilizamos para crear diferentes tipos de reportes (ventas, inventario) con componentes consistentes.

## Estructura

### Interfaces (Productos Abstractos)
- `ChartGenerator`: Define la interfaz para crear gráficos para los reportes
- `ReportContentGenerator`: Define la interfaz para generar el contenido del reporte
- `ReportFormatter`: Define la interfaz para formatear los reportes

### Abstract Factory
- `ReportFactory`: La interfaz abstracta de la fábrica que declara métodos para crear componentes de reporte

### Fábricas Concretas
- `SalesReportFactory`: Crea componentes para reportes de ventas
- `InventoryReportFactory`: Crea componentes para reportes de inventario

### Director
- `ReportDirector`: Coordina el proceso de generación de reportes utilizando la fábrica abstracta

## Uso

Para generar un reporte:

```java
// Crear una fábrica de reportes
ReportFactory factory = new SalesReportFactory();

// Crear un director de reportes con la fábrica
ReportDirector director = new ReportDirector(factory);

// Generar un reporte
director.generateReport("output.pdf", "Título del Reporte", fechaInicio, fechaFin);
```

Alternativamente, utilizar la interfaz simplificada en `GenerarReportePDF`:

```java
GenerarReportePDF generador = new GenerarReportePDF();

// Generar un reporte de ventas
generador.generarReporte(fechaInicio, fechaFin);

// Generar un reporte de inventario
generador.generarReporteInventario();
```

## Ventajas

1. **Consistencia**: Asegura que todos los componentes de una familia de reportes trabajen juntos correctamente
2. **Aislamiento**: Las implementaciones concretas están aisladas del código cliente
3. **Extensibilidad**: Se pueden agregar nuevos tipos de reportes creando nuevas fábricas concretas
4. **Flexibilidad**: El código cliente trabaja con interfaces, no con clases concretas

## Adicional

Este sistema de generación de reportes permite la creación de reportes personalizados, facilitando la adaptación a diferentes necesidades del negocio. Además, el uso del patrón Abstract Factory promueve un diseño modular y mantenible, lo que simplifica la incorporación de nuevas funcionalidades y la realización de cambios en el sistema.

## Beneficios del Patrón Abstract Factory

- **Flexibilidad**: Permite cambiar fácilmente la familia de productos utilizados en tiempo de ejecución.
- **Reutilización de código**: Promueve la reutilización de componentes comunes entre diferentes tipos de reportes.
- **Bajo acoplamiento**: Reduce la dependencia entre las clases concretas y el código cliente.

## Extensión del Sistema

Para agregar un nuevo tipo de reporte:

1.  Crear una nueva fábrica concreta que implemente la interfaz `ReportFactory`.
2.  Crear las clases concretas para `ChartGenerator`, `ReportContentGenerator` y `ReportFormatter` específicas para el nuevo tipo de reporte.
3.  Modificar la clase `GenerarReportePDF` para incluir la opción de generar el nuevo tipo de reporte.


## Diagrama de clases (simplificado)

![Diagrama de clases](./uml.png)