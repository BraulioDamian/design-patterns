/*
 * Concrete content generator for inventory reports
 */
package Configuraciones.reportes.inventory;

import Configuraciones.reportes.ReportContentGenerator;
import Configuraciones.reportes.ReportFormatter;
import ConexionDB.Conexion_DB;
import Consultas.CONSULTASDAO;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

/**
 * Concrete implementation of ReportContentGenerator for inventory reports
 */
public class InventoryReportContentGenerator implements ReportContentGenerator {
    private final ReportFormatter formatter;
    
    public InventoryReportContentGenerator() {
        this.formatter = new InventoryReportFormatter();
    }
    
    @Override
    public void generateContent(Document document, Date fechaInicio, Date fechaFin) throws DocumentException, SQLException {
        try {
            // Add inventory charts and descriptions
            addInventoryCharts(document);
            
            // Add inventory summary
            addInventorySummary(document);
            
            // Add low stock alerts
            addLowStockAlerts(document);
            
            // Add inventory valuation
            addInventoryValuation(document);
        } catch (IOException e) {
            throw new DocumentException("Error generating inventory report content: " + e.getMessage());
        }
    }
    
    private void addInventoryCharts(Document document) throws IOException, DocumentException, SQLException {
        // Add inventory levels chart section
        addChartSection(document, "Niveles de Inventario", "inventory_levels.png");
        document.newPage(); // New page
        
        // Add low stock items chart section
        addChartSection(document, "Productos con Bajo Stock", "low_stock_items.png");
        document.newPage(); // New page
        
        // Add category distribution chart section
        addChartSection(document, "Distribución por Categorías", "category_distribution.png");
        document.newPage(); // New page
    }
    
    private void addChartSection(Document document, String title, String imagePath) throws IOException, DocumentException {
        Font sectionTitleFont = formatter.getSectionTitleFont();
        
        // Add section title
        Paragraph sectionTitle = new Paragraph(title, sectionTitleFont);
        sectionTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(sectionTitle);
        document.add(new Paragraph("\n")); // Space
        
        // Add chart
        Image image = Image.getInstance(imagePath);
        image.scaleToFit(500, 400);
        image.setAlignment(Element.ALIGN_CENTER);
        document.add(image);
        document.add(new Paragraph("\n")); // Space
    }
    
    private void addInventorySummary(Document document) throws DocumentException, SQLException {
        Font sectionTitleFont = formatter.getSectionTitleFont();
        Font contentFont = formatter.getContentFont();
        
        document.add(new Paragraph("Resumen de Inventario", sectionTitleFont));
        document.add(new Paragraph("\n")); // Space
        
        CONSULTASDAO dao = new CONSULTASDAO(Conexion_DB.getConexion());
        // Assuming these methods exist or would be implemented
        int totalItems = dao.obtenerTotalProductos();
        int totalCategories = dao.obtenerTotalCategorias();
        double inventoryValue = dao.obtenerValorInventario();
        
        // Create summary table
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(80);
        
        // Add table headers
        PdfPCell cell1 = new PdfPCell(new Paragraph("Métrica", contentFont));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Valor", contentFont));
        
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        table.addCell(cell1);
        table.addCell(cell2);
        
        // Add data rows
        addTableRow(table, "Total de productos", String.valueOf(totalItems), contentFont);
        addTableRow(table, "Total de categorías", String.valueOf(totalCategories), contentFont);
        addTableRow(table, "Valor del inventario", String.format("$%.2f", inventoryValue), contentFont);
        
        document.add(table);
        document.add(new Paragraph("\n")); // Space
    }
    
    private void addTableRow(PdfPTable table, String label, String value, Font font) {
        PdfPCell cell1 = new PdfPCell(new Paragraph(label, font));
        PdfPCell cell2 = new PdfPCell(new Paragraph(value, font));
        
        cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
        
        table.addCell(cell1);
        table.addCell(cell2);
    }
    
    private void addLowStockAlerts(Document document) throws DocumentException, SQLException {
        Font sectionTitleFont = formatter.getSectionTitleFont();
        Font contentFont = formatter.getContentFont();
        
        document.add(new Paragraph("Alertas de Bajo Stock", sectionTitleFont));
        document.add(new Paragraph("\n")); // Space
        
        CONSULTASDAO dao = new CONSULTASDAO(Conexion_DB.getConexion());
        // Assuming this method exists or would be implemented
        Map<String, Integer> lowStockItems = dao.obtenerProductosBajoStock();
        
        if (lowStockItems.isEmpty()) {
            document.add(new Paragraph("No hay productos con bajo stock.", contentFont));
        } else {
            // Create table for low stock items
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            
            // Add table headers
            PdfPCell cell1 = new PdfPCell(new Paragraph("Producto", contentFont));
            PdfPCell cell2 = new PdfPCell(new Paragraph("Stock Actual", contentFont));
            PdfPCell cell3 = new PdfPCell(new Paragraph("Stock Mínimo", contentFont));
            
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            
            // Add data rows
            for (Map.Entry<String, Integer> entry : lowStockItems.entrySet()) {
                PdfPCell productCell = new PdfPCell(new Paragraph(entry.getKey(), contentFont));
                PdfPCell stockCell = new PdfPCell(new Paragraph(entry.getValue().toString(), contentFont));
                PdfPCell minStockCell = new PdfPCell(new Paragraph("10", contentFont)); // Assuming fixed min stock for example
                
                table.addCell(productCell);
                table.addCell(stockCell);
                table.addCell(minStockCell);
            }
            
            document.add(table);
        }
        
        document.add(new Paragraph("\n")); // Space
    }
    
    private void addInventoryValuation(Document document) throws DocumentException, SQLException {
        Font sectionTitleFont = formatter.getSectionTitleFont();
        Font contentFont = formatter.getContentFont();
        
        document.add(new Paragraph("Valoración de Inventario", sectionTitleFont));
        document.add(new Paragraph("\n")); // Space
        
        CONSULTASDAO dao = new CONSULTASDAO(Conexion_DB.getConexion());
        // Assuming this method exists or would be implemented
        Map<String, Double> inventoryValuation = dao.obtenerValoracionInventario();
        
        // Create table for inventory valuation
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        
        // Add table headers
        PdfPCell cell1 = new PdfPCell(new Paragraph("Categoría", contentFont));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Valor", contentFont));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Porcentaje", contentFont));
        
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        
        // Calculate total value
        double totalValue = inventoryValuation.values().stream().mapToDouble(Double::doubleValue).sum();
        
        // Add data rows
        for (Map.Entry<String, Double> entry : inventoryValuation.entrySet()) {
            PdfPCell categoryCell = new PdfPCell(new Paragraph(entry.getKey(), contentFont));
            PdfPCell valueCell = new PdfPCell(new Paragraph(String.format("$%.2f", entry.getValue()), contentFont));
            double percentage = (entry.getValue() / totalValue) * 100;
            PdfPCell percentageCell = new PdfPCell(new Paragraph(String.format("%.1f%%", percentage), contentFont));
            
            table.addCell(categoryCell);
            table.addCell(valueCell);
            table.addCell(percentageCell);
        }
        
        document.add(table);
        document.add(new Paragraph("\n")); // Space
    }
}
