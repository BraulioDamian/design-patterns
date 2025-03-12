/*
 * Concrete chart generator for inventory reports
 */
package Configuraciones.reportes.inventory;

import Configuraciones.reportes.ChartGenerator;
import ConexionDB.Conexion_DB;
import Consultas.CONSULTASDAO;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.style.Styler;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Concrete implementation of ChartGenerator for inventory reports
 */
public class InventoryChartGenerator implements ChartGenerator {

    @Override
    public void initialize(Date fechaInicio, Date fechaFin) throws IOException, SQLException {
        generateCharts(fechaInicio, fechaFin);
    }

    @Override
    public void generateCharts(Date fechaInicio, Date fechaFin) throws IOException, SQLException {
        generateInventoryLevelsChart("inventory_levels.png");
        generateLowStockItemsChart("low_stock_items.png");
        generateCategoryDistributionChart("category_distribution.png");
    }

    private String generateInventoryLevelsChart(String rutaArchivo) throws IOException, SQLException {
        Map<String, Integer> inventoryLevels;
        try {
            CONSULTASDAO dao = new CONSULTASDAO(Conexion_DB.getConexion());
            // Assuming this method exists or would be implemented
            inventoryLevels = dao.obtenerNivelesInventario();
        } catch (SQLException e) {
            throw new SQLException("Error al obtener los niveles de inventario", e);
        }

        CategoryChart chart = new CategoryChartBuilder()
                .width(800)
                .height(600)
                .title("Niveles de Inventario")
                .xAxisTitle("Producto")
                .yAxisTitle("Cantidad")
                .build();

        List<String> productos = inventoryLevels.keySet().stream().collect(Collectors.toList());
        List<Integer> cantidades = inventoryLevels.values().stream().collect(Collectors.toList());

        chart.addSeries("Cantidad", productos, cantidades);

        Path path = Paths.get(rutaArchivo);
        BitmapEncoder.saveBitmap(chart, path.toString(), BitmapEncoder.BitmapFormat.PNG);

        return rutaArchivo;
    }

    private String generateLowStockItemsChart(String rutaArchivo) throws IOException, SQLException {
        Map<String, Integer> lowStockItems;
        try {
            CONSULTASDAO dao = new CONSULTASDAO(Conexion_DB.getConexion());
            // Assuming this method exists or would be implemented
            lowStockItems = dao.obtenerProductosBajoStock();
        } catch (SQLException e) {
            throw new SQLException("Error al obtener los productos con poco stock", e);
        }

        CategoryChart chart = new CategoryChartBuilder()
                .width(800)
                .height(600)
                .title("Productos con Bajo Stock")
                .xAxisTitle("Producto")
                .yAxisTitle("Cantidad")
                .build();

        List<String> productos = lowStockItems.keySet().stream().collect(Collectors.toList());
        List<Integer> cantidades = lowStockItems.values().stream().collect(Collectors.toList());

        chart.addSeries("Cantidad", productos, cantidades);

        Path path = Paths.get(rutaArchivo);
        BitmapEncoder.saveBitmap(chart, path.toString(), BitmapEncoder.BitmapFormat.PNG);

        return rutaArchivo;
    }

    private String generateCategoryDistributionChart(String rutaArchivo) throws IOException, SQLException {
        Map<String, Integer> categoryDistribution;
        try {
            CONSULTASDAO dao = new CONSULTASDAO(Conexion_DB.getConexion());
            // Assuming this method exists or would be implemented
            categoryDistribution = dao.obtenerDistribucionCategorias();
        } catch (SQLException e) {
            throw new SQLException("Error al obtener la distribución por categorías", e);
        }

        PieChart chart = new PieChartBuilder()
                .width(800)
                .height(600)
                .title("Distribución por Categorías")
                .theme(Styler.ChartTheme.GGPlot2)
                .build();

        for (Map.Entry<String, Integer> entry : categoryDistribution.entrySet()) {
            chart.addSeries(entry.getKey(), entry.getValue());
        }

        Path path = Paths.get(rutaArchivo);
        BitmapEncoder.saveBitmap(chart, path.toString(), BitmapEncoder.BitmapFormat.PNG);

        return rutaArchivo;
    }
}
