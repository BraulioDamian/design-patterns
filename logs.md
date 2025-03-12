# Logs de Ejecución: Comparativa de Implementaciones

## 1. Implementación Original

### Generación de un Reporte de Ventas

```
[2025-03-11 15:30:21] INFO: Iniciando generación de reporte de ventas desde 2025-01-01 hasta 2025-03-01
[2025-03-11 15:30:21] INFO: Creando carpeta de reportes si no existe
[2025-03-11 15:30:21] INFO: Creando documento PDF en reportes/reporte_ventas.pdf
[2025-03-11 15:30:21] INFO: Añadiendo título principal "Reporte de Ventas"
[2025-03-11 15:30:21] INFO: Llamando a agregarGraficasYDescripciones()
[2025-03-11 15:30:21] INFO: Creando instancia de ChartGenerator
[2025-03-11 15:30:21] INFO: ChartGenerator.initialize() - Inicializando generador de gráficas
[2025-03-11 15:30:22] INFO: ChartGenerator.crearGraficas() - Generando gráficas para el reporte
[2025-03-11 15:30:22] INFO: generarGraficaVentasPorProducto() - Consultando base de datos para ventas por producto
[2025-03-11 15:30:23] INFO: Creando gráfica "Ventas por Producto"
[2025-03-11 15:30:23] INFO: Guardando gráfica en ventas_por_producto.png
[2025-03-11 15:30:23] INFO: generarGraficaVentasPorEmpleado() - Consultando base de datos para ventas por empleado
[2025-03-11 15:30:24] INFO: Creando gráfica "Ventas por Empleado"
[2025-03-11 15:30:24] INFO: Guardando gráfica en ventas_por_empleado.png
[2025-03-11 15:30:24] INFO: generarGraficaProductosMenosVendidos() - Consultando base de datos para productos menos vendidos
[2025-03-11 15:30:25] INFO: Creando gráfica "Productos Menos Vendidos"
[2025-03-11 15:30:25] INFO: Guardando gráfica en productos_menos_vendidos.png
[2025-03-11 15:30:25] INFO: agregarSeccionConGraficaYDescripcion() - Añadiendo sección "Ventas por Producto"
[2025-03-11 15:30:25] INFO: Consultando base de datos para obtener detalles de ventas por producto
[2025-03-11 15:30:26] INFO: Añadiendo imagen ventas_por_producto.png
[2025-03-11 15:30:26] INFO: Añadiendo descripción de ventas por producto
[2025-03-11 15:30:26] INFO: Creando nueva página
[2025-03-11 15:30:26] INFO: agregarSeccionConGraficaYDescripcion() - Añadiendo sección "Ventas por Empleado"
[2025-03-11 15:30:26] INFO: Consultando base de datos para obtener detalles de ventas por empleado
[2025-03-11 15:30:27] INFO: Añadiendo imagen ventas_por_empleado.png
[2025-03-11 15:30:27] INFO: Añadiendo descripción de ventas por empleado
[2025-03-11 15:30:27] INFO: Creando nueva página
[2025-03-11 15:30:27] INFO: agregarSeccionConGraficaYDescripcion() - Añadiendo sección "Productos Menos Vendidos"
[2025-03-11 15:30:27] INFO: Consultando base de datos para obtener detalles de productos menos vendidos
[2025-03-11 15:30:28] INFO: Añadiendo imagen productos_menos_vendidos.png
[2025-03-11 15:30:28] INFO: Añadiendo descripción de productos menos vendidos
[2025-03-11 15:30:28] INFO: Creando nueva página
[2025-03-11 15:30:28] INFO: Consultando base de datos para obtener lista de usuarios
[2025-03-11 15:30:29] INFO: Encontrados 3 usuarios para incluir en el reporte
[2025-03-11 15:30:29] INFO: agregarReporteEmpleado() - Añadiendo reporte para empleado "Juan Pérez"
[2025-03-11 15:30:29] INFO: agregarVentasEmpleado() - Consultando ventas diarias del empleado
[2025-03-11 15:30:29] INFO: agregarVentasEmpleado() - Consultando ventas semanales del empleado
[2025-03-11 15:30:30] INFO: agregarVentasEmpleado() - Consultando ventas mensuales del empleado
[2025-03-11 15:30:30] INFO: agregarProductosVendidosEmpleado() - Consultando productos vendidos por el empleado
[2025-03-11 15:30:31] INFO: agregarDesempeñoEmpleado() - Evaluando desempeño del empleado
[2025-03-11 15:30:31] INFO: agregarReporteEmpleado() - Añadiendo reporte para empleado "María Gómez"
[2025-03-11 15:30:31] INFO: agregarVentasEmpleado() - Consultando ventas diarias del empleado
[2025-03-11 15:30:31] INFO: agregarVentasEmpleado() - Consultando ventas semanales del empleado
[2025-03-11 15:30:32] INFO: agregarVentasEmpleado() - Consultando ventas mensuales del empleado
[2025-03-11 15:30:32] INFO: agregarProductosVendidosEmpleado() - Consultando productos vendidos por el empleado
[2025-03-11 15:30:33] INFO: agregarDesempeñoEmpleado() - Evaluando desempeño del empleado
[2025-03-11 15:30:33] INFO: agregarReporteEmpleado() - Añadiendo reporte para empleado "Carlos Rodríguez"
[2025-03-11 15:30:33] INFO: agregarVentasEmpleado() - Consultando ventas diarias del empleado
[2025-03-11 15:30:33] INFO: agregarVentasEmpleado() - Consultando ventas semanales del empleado
[2025-03-11 15:30:34] INFO: agregarVentasEmpleado() - Consultando ventas mensuales del empleado
[2025-03-11 15:30:34] INFO: agregarProductosVendidosEmpleado() - Consultando productos vendidos por el empleado
[2025-03-11 15:30:35] INFO: agregarDesempeñoEmpleado() - Evaluando desempeño del empleado
[2025-03-11 15:30:35] INFO: Cerrando documento PDF
[2025-03-11 15:30:35] INFO: Reporte de ventas generado exitosamente en: reportes/reporte_ventas.pdf
```

## 2. Nueva Implementación (Abstract Factory)

### Generación de un Reporte de Ventas

```
[2025-03-11 15:30:21] INFO: Iniciando generación de reporte de ventas desde 2025-01-01 hasta 2025-03-01
[2025-03-11 15:30:21] INFO: GenerarReportePDF.generarReporte() - Creando fábrica de reportes de ventas
[2025-03-11 15:30:21] INFO: SalesReportFactory creada
[2025-03-11 15:30:21] INFO: Creando ReportDirector con SalesReportFactory
[2025-03-11 15:30:21] INFO: ReportDirector.generateReport() - Iniciando generación de reporte "Reporte de Ventas"
[2025-03-11 15:30:21] INFO: Creando carpeta de reportes si no existe
[2025-03-11 15:30:21] INFO: ReportDirector - Creando ChartGenerator usando la fábrica
[2025-03-11 15:30:21] INFO: SalesReportFactory.createChartGenerator() - Creando SalesChartGenerator
[2025-03-11 15:30:21] INFO: SalesChartGenerator creado
[2025-03-11 15:30:21] INFO: ReportDirector - Inicializando ChartGenerator
[2025-03-11 15:30:21] INFO: SalesChartGenerator.initialize() - Inicializando generador de gráficas de ventas
[2025-03-11 15:30:22] INFO: SalesChartGenerator.generateCharts() - Generando gráficas para el reporte de ventas
[2025-03-11 15:30:22] INFO: generateVentasPorProductoChart() - Consultando base de datos para ventas por producto
[2025-03-11 15:30:23] INFO: Creando gráfica "Ventas por Producto"
[2025-03-11 15:30:23] INFO: Guardando gráfica en ventas_por_producto.png
[2025-03-11 15:30:23] INFO: generateVentasPorEmpleadoChart() - Consultando base de datos para ventas por empleado
[2025-03-11 15:30:24] INFO: Creando gráfica "Ventas por Empleado"
[2025-03-11 15:30:24] INFO: Guardando gráfica en ventas_por_empleado.png
[2025-03-11 15:30:24] INFO: generateProductosMenosVendidosChart() - Consultando base de datos para productos menos vendidos
[2025-03-11 15:30:25] INFO: Creando gráfica "Productos Menos Vendidos"
[2025-03-11 15:30:25] INFO: Guardando gráfica en productos_menos_vendidos.png
[2025-03-11 15:30:25] INFO: ReportDirector - Creando documento PDF en reportes/reporte_ventas.pdf
[2025-03-11 15:30:25] INFO: ReportDirector - Creando ReportFormatter usando la fábrica
[2025-03-11 15:30:25] INFO: SalesReportFactory.createReportFormatter() - Creando SalesReportFormatter
[2025-03-11 15:30:25] INFO: SalesReportFormatter creado
[2025-03-11 15:30:25] INFO: ReportDirector - Añadiendo encabezado al documento
[2025-03-11 15:30:25] INFO: SalesReportFormatter.addHeader() - Añadiendo encabezado "Reporte de Ventas"
[2025-03-11 15:30:25] INFO: ReportDirector - Creando ReportContentGenerator usando la fábrica
[2025-03-11 15:30:25] INFO: SalesReportFactory.createReportContentGenerator() - Creando SalesReportContentGenerator
[2025-03-11 15:30:25] INFO: SalesReportContentGenerator creado
[2025-03-11 15:30:25] INFO: ReportDirector - Generando contenido del reporte
[2025-03-11 15:30:25] INFO: SalesReportContentGenerator.generateContent() - Generando contenido para reporte de ventas
[2025-03-11 15:30:25] INFO: SalesReportContentGenerator.addChartsAndDescriptions() - Añadiendo gráficas y descripciones
[2025-03-11 15:30:26] INFO: addSectionWithChartAndDescription() - Añadiendo sección "Ventas por Producto"
[2025-03-11 15:30:26] INFO: Consultando base de datos para obtener detalles de ventas por producto
[2025-03-11 15:30:26] INFO: Añadiendo imagen ventas_por_producto.png
[2025-03-11 15:30:26] INFO: Añadiendo descripción de ventas por producto
[2025-03-11 15:30:26] INFO: Creando nueva página
[2025-03-11 15:30:26] INFO: addSectionWithChartAndDescription() - Añadiendo sección "Ventas por Empleado"
[2025-03-11 15:30:27] INFO: Consultando base de datos para obtener detalles de ventas por empleado
[2025-03-11 15:30:27] INFO: Añadiendo imagen ventas_por_empleado.png
[2025-03-11 15:30:27] INFO: Añadiendo descripción de ventas por empleado
[2025-03-11 15:30:27] INFO: Creando nueva página
[2025-03-11 15:30:27] INFO: addSectionWithChartAndDescription() - Añadiendo sección "Productos Menos Vendidos"
[2025-03-11 15:30:28] INFO: Consultando base de datos para obtener detalles de productos menos vendidos
[2025-03-11 15:30:28] INFO: Añadiendo imagen productos_menos_vendidos.png
[2025-03-11 15:30:28] INFO: Añadiendo descripción de productos menos vendidos
[2025-03-11 15:30:28] INFO: Creando nueva página
[2025-03-11 15:30:28] INFO: SalesReportContentGenerator.addEmployeeSalesData() - Añadiendo datos de ventas por empleado
[2025-03-11 15:30:28] INFO: Consultando base de datos para obtener lista de usuarios
[2025-03-11 15:30:29] INFO: Encontrados 3 usuarios para incluir en el reporte
[2025-03-11 15:30:29] INFO: addEmployeeReport() - Añadiendo reporte para empleado "Juan Pérez"
[2025-03-11 15:30:29] INFO: addEmployeeSales() - Consultando ventas del empleado
[2025-03-11 15:30:30] INFO: addEmployeeProductsSold() - Consultando productos vendidos por el empleado
[2025-03-11 15:30:31] INFO: addEmployeePerformance() - Evaluando desempeño del empleado
[2025-03-11 15:30:31] INFO: addEmployeeReport() - Añadiendo reporte para empleado "María Gómez"
[2025-03-11 15:30:32] INFO: addEmployeeSales() - Consultando ventas del empleado
[2025-03-11 15:30:32] INFO: addEmployeeProductsSold() - Consultando productos vendidos por el empleado
[2025-03-11 15:30:33] INFO: addEmployeePerformance() - Evaluando desempeño del empleado
[2025-03-11 15:30:33] INFO: addEmployeeReport() - Añadiendo reporte para empleado "Carlos Rodríguez"
[2025-03-11 15:30:33] INFO: addEmployeeSales() - Consultando ventas del empleado
[2025-03-11 15:30:34] INFO: addEmployeeProductsSold() - Consultando productos vendidos por el empleado
[2025-03-11 15:30:35] INFO: addEmployeePerformance() - Evaluando desempeño del empleado
[2025-03-11 15:30:35] INFO: ReportDirector - Añadiendo pie de página al documento
[2025-03-11 15:30:35] INFO: SalesReportFormatter.addFooter() - Añadiendo pie de página
[2025-03-11 15:30:35] INFO: ReportDirector - Cerrando documento PDF
[2025-03-11 15:30:35] INFO: Reporte de ventas generado exitosamente en: reportes/reporte_ventas.pdf
```

### Generación de un Reporte de Inventario (Nueva funcionalidad)

```
[2025-03-11 15:35:45] INFO: Iniciando generación de reporte de inventario
[2025-03-11 15:35:45] INFO: GenerarReportePDF.generarReporteInventario() - Creando fábrica de reportes de inventario
[2025-03-11 15:35:45] INFO: InventoryReportFactory creada
[2025-03-11 15:35:45] INFO: Creando ReportDirector con InventoryReportFactory
[2025-03-11 15:35:45] INFO: ReportDirector.generateReport() - Iniciando generación de reporte "Reporte de Inventario"
[2025-03-11 15:35:45] INFO: Creando carpeta de reportes si no existe
[2025-03-11 15:35:45] INFO: ReportDirector - Creando ChartGenerator usando la fábrica
[2025-03-11 15:35:45] INFO: InventoryReportFactory.createChartGenerator() - Creando InventoryChartGenerator
[2025-03-11 15:35:45] INFO: InventoryChartGenerator creado
[2025-03-11 15:35:45] INFO: ReportDirector - Inicializando ChartGenerator
[2025-03-11 15:35:45] INFO: InventoryChartGenerator.initialize() - Inicializando generador de gráficas de inventario
[2025-03-11 15:35:45] INFO: InventoryChartGenerator.generateCharts() - Generando gráficas para el reporte de inventario
[2025-03-11 15:35:46] INFO: generateInventoryLevelsChart() - Consultando base de datos para niveles de inventario
[2025-03-11 15:35:46] INFO: Creando gráfica "Niveles de Inventario"
[2025-03-11 15:35:46] INFO: Guardando gráfica en inventory_levels.png
[2025-03-11 15:35:47] INFO: generateLowStockItemsChart() - Consultando base de datos para productos con bajo stock
[2025-03-11 15:35:47] INFO: Creando gráfica "Productos con Bajo Stock"
[2025-03-11 15:35:47] INFO: Guardando gráfica en low_stock_items.png
[2025-03-11 15:35:48] INFO: generateCategoryDistributionChart() - Consultando base de datos para distribución por categorías
[2025-03-11 15:35:48] INFO: Creando gráfica tipo pie "Distribución por Categorías"
[2025-03-11 15:35:48] INFO: Guardando gráfica en category_distribution.png
[2025-03-11 15:35:48] INFO: ReportDirector - Creando documento PDF en reportes/reporte_inventario.pdf
[2025-03-11 15:35:48] INFO: ReportDirector - Creando ReportFormatter usando la fábrica
[2025-03-11 15:35:48] INFO: InventoryReportFactory.createReportFormatter() - Creando InventoryReportFormatter
[2025-03-11 15:35:48] INFO: InventoryReportFormatter creado
[2025-03-11 15:35:48] INFO: ReportDirector - Añadiendo encabezado al documento
[2025-03-11 15:35:48] INFO: InventoryReportFormatter.addHeader() - Añadiendo encabezado "Reporte de Inventario"
[2025-03-11 15:35:48] INFO: InventoryReportFormatter.addHeader() - Añadiendo fecha actual al encabezado
[2025-03-11 15:35:48] INFO: ReportDirector - Creando ReportContentGenerator usando la fábrica
[2025-03-11 15:35:48] INFO: InventoryReportFactory.createReportContentGenerator() - Creando InventoryReportContentGenerator
[2025-03-11 15:35:48] INFO: InventoryReportContentGenerator creado
[2025-03-11 15:35:48] INFO: ReportDirector - Generando contenido del reporte
[2025-03-11 15:35:48] INFO: InventoryReportContentGenerator.generateContent() - Generando contenido para reporte de inventario
[2025-03-11 15:35:48] INFO: InventoryReportContentGenerator.addInventoryCharts() - Añadiendo gráficas de inventario
[2025-03-11 15:35:49] INFO: addChartSection() - Añadiendo sección "Niveles de Inventario"
[2025-03-11 15:35:49] INFO: Añadiendo imagen inventory_levels.png
[2025-03-11 15:35:49] INFO: Creando nueva página
[2025-03-11 15:35:49] INFO: addChartSection() - Añadiendo sección "Productos con Bajo Stock"
[2025-03-11 15:35:49] INFO: Añadiendo imagen low_stock_items.png
[2025-03-11 15:35:49] INFO: Creando nueva página
[2025-03-11 15:35:49] INFO: addChartSection() - Añadiendo sección "Distribución por Categorías"
[2025-03-11 15:35:49] INFO: Añadiendo imagen category_distribution.png
[2025-03-11 15:35:49] INFO: Creando nueva página
[2025-03-11 15:35:49] INFO: InventoryReportContentGenerator.addInventorySummary() - Añadiendo resumen de inventario
[2025-03-11 15:35:49] INFO: Consultando base de datos para obtener total de productos
[2025-03-11 15:35:50] INFO: Consultando base de datos para obtener total de categorías
[2025-03-11 15:35:50] INFO: Consultando base de datos para obtener valor de inventario
[2025-03-11 15:35:50] INFO: Creando tabla resumen de inventario
[2025-03-11 15:35:50] INFO: InventoryReportContentGenerator.addLowStockAlerts() - Añadiendo alertas de bajo stock
[2025-03-11 15:35:50] INFO: Consultando base de datos para obtener productos con bajo stock
[2025-03-11 15:35:51] INFO: Encontrados 5 productos con bajo stock
[2025-03-11 15:35:51] INFO: Creando tabla de productos con bajo stock
[2025-03-11 15:35:51] INFO: InventoryReportContentGenerator.addInventoryValuation() - Añadiendo valoración de inventario
[2025-03-11 15:35:51] INFO: Consultando base de datos para obtener valoración de inventario por categoría
[2025-03-11 15:35:52] INFO: Creando tabla de valoración de inventario
[2025-03-11 15:35:52] INFO: ReportDirector - Añadiendo pie de página al documento
[2025-03-11 15:35:52] INFO: InventoryReportFormatter.addFooter() - Añadiendo pie de página
[2025-03-11 15:35:52] INFO: ReportDirector - Cerrando documento PDF
[2025-03-11 15:35:52] INFO: Reporte de inventario generado exitosamente en: reportes/reporte_inventario.pdf
```

## 3. Análisis de los Logs

### Diferencias Clave

1. **Estructura del flujo de ejecución**:
   - **Original**: Secuencia plana de operaciones con responsabilidades mezcladas
   - **Nueva**: Estructura jerárquica clara con separación de responsabilidades

2. **Creación de objetos**:
   - **Original**: Creación directa de instancias concretas
   - **Nueva**: Creación de objetos a través de fábricas y basada en interfaces

3. **Flexibilidad**:
   - **Original**: Solo soporta reportes de ventas
   - **Nueva**: Soporta múltiples tipos de reportes (ventas, inventario) con la misma estructura base

4. **Extensibilidad**:
   - **Original**: Para añadir nuevas funcionalidades requiere modificar código existente
   - **Nueva**: Permite añadir nuevas implementaciones sin modificar código existente

5. **Cohesión**:
   - **Original**: Clases con múltiples responsabilidades
   - **Nueva**: Clases con responsabilidades específicas y bien definidas

### Ventajas Evidentes en los Logs

1. **Organización clara**:
   - Los logs de la nueva implementación muestran una estructura más organizada y predecible
   - Las responsabilidades están claramente delimitadas

2. **Reutilización**:
   - El mismo `ReportDirector` se utiliza para diferentes tipos de reportes
   - La lógica de coordinación es compartida entre diferentes fábricas

3. **Extensibilidad demostrada**:
   - El log del reporte de inventario muestra cómo se puede agregar un tipo completamente nuevo de reporte con mínimos cambios

4. **Consistencia**:
   - A pesar de las diferencias en contenido, ambos reportes siguen el mismo flujo de procesamiento
   - Las interfaces aseguran que cada componente implemente la funcionalidad requerida