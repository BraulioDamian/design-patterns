/*
 * Concrete content generator for sales reports
 */
package Configuraciones.reportes.sales;

import Configuraciones.reportes.ReportContentGenerator;
import Configuraciones.reportes.ReportFormatter;
import ConexionDB.Conexion_DB;
import Consultas.CONSULTASDAO;
import DBObjetos.Usuario;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Concrete implementation of ReportContentGenerator for sales reports
 */
public class SalesReportContentGenerator implements ReportContentGenerator {
    private final ReportFormatter formatter;
    
    public SalesReportContentGenerator() {
        this.formatter = new SalesReportFormatter();
    }
    
    @Override
    public void generateContent(Document document, Date fechaInicio, Date fechaFin) throws DocumentException, SQLException {
        try {
            // Add charts and their descriptions
            addChartsAndDescriptions(document, fechaInicio, fechaFin);
            
            // Add employee sales data
            addEmployeeSalesData(document, fechaInicio, fechaFin);
        } catch (IOException e) {
            throw new DocumentException("Error generating report content: " + e.getMessage());
        }
    }
    
    private void addChartsAndDescriptions(Document document, Date fechaInicio, Date fechaFin) throws IOException, DocumentException, SQLException {
        // Add each section in a new page
        addSectionWithChartAndDescription(document, "Ventas por Producto", "ventas_por_producto.png", fechaInicio, fechaFin);
        document.newPage(); // New page
        
        addSectionWithChartAndDescription(document, "Ventas por Empleado", "ventas_por_empleado.png", fechaInicio, fechaFin);
        document.newPage(); // New page
        
        addSectionWithChartAndDescription(document, "Productos Menos Vendidos", "productos_menos_vendidos.png", fechaInicio, fechaFin);
        document.newPage(); // New page
    }
    
    private void addSectionWithChartAndDescription(Document document, String title, String imagePath, Date fechaInicio, Date fechaFin) throws IOException, DocumentException, SQLException {
        Font sectionTitleFont = formatter.getSectionTitleFont();
        Font contentFont = formatter.getContentFont();
        
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
        
        // Add description
        CONSULTASDAO dao = new CONSULTASDAO(Conexion_DB.getConexion());
        
        switch (title) {
            case "Ventas por Producto":
                Map<String, Integer> ventasPorProducto = dao.obtenerVentasPorProducto(new java.sql.Date(fechaInicio.getTime()), new java.sql.Date(fechaFin.getTime()));
                for (Map.Entry<String, Integer> entry : ventasPorProducto.entrySet()) {
                    document.add(new Paragraph(entry.getKey() + ": " + entry.getValue(), contentFont));
                }
                break;
            case "Ventas por Empleado":
                Map<String, Integer> ventasPorEmpleado = dao.obtenerVentasPorEmpleado(new java.sql.Date(fechaInicio.getTime()), new java.sql.Date(fechaFin.getTime()));
                for (Map.Entry<String, Integer> entry : ventasPorEmpleado.entrySet()) {
                    document.add(new Paragraph(entry.getKey() + ": " + entry.getValue(), contentFont));
                }
                break;
            case "Productos Menos Vendidos":
                Map<String, Integer> productosMenosVendidos = dao.obtenerProductosMenosVendidos(new java.sql.Date(fechaInicio.getTime()), new java.sql.Date(fechaFin.getTime()));
                for (Map.Entry<String, Integer> entry : productosMenosVendidos.entrySet()) {
                    document.add(new Paragraph(entry.getKey() + ": " + entry.getValue(), contentFont));
                }
                break;
        }
        
        document.add(new Paragraph("\n")); // Space
    }
    
    private void addEmployeeSalesData(Document document, Date fechaInicio, Date fechaFin) throws DocumentException, SQLException {
        CONSULTASDAO dao = new CONSULTASDAO(Conexion_DB.getConexion());
        List<Usuario> usuarios = dao.obtenerTodosLosUsuarios();
        
        for (Usuario usuario : usuarios) {
            addEmployeeReport(document, dao, usuario, fechaInicio, fechaFin);
        }
    }
    
    private void addEmployeeReport(Document document, CONSULTASDAO dao, Usuario usuario, Date fechaInicio, Date fechaFin) throws DocumentException, SQLException {
        Font sectionFont = formatter.getSectionTitleFont();
        Font contentFont = formatter.getContentFont();
        
        // Add date
        document.add(new Paragraph("Fecha: " + new java.util.Date(), contentFont));
        document.add(new Paragraph("\n")); // Space
        
        // Add name and role
        document.add(new Paragraph("Nombre de Empleado: " + usuario.getNombreUsuario(), sectionFont));
        document.add(new Paragraph("Cargo: " + usuario.getRol(), contentFont));
        document.add(new Paragraph("\n")); // Space
        
        // Get and add sales
        document.add(new Paragraph("Ventas", sectionFont));
        addEmployeeSales(document, dao, usuario, fechaInicio, fechaFin, contentFont);
        
        // Add total products sold
        document.add(new Paragraph("Total de productos vendidos", sectionFont));
        addEmployeeProductsSold(document, dao, usuario, fechaInicio, fechaFin, contentFont);
        
        // Add performance
        document.add(new Paragraph("Desempeño", sectionFont));
        addEmployeePerformance(document, dao, usuario, fechaInicio, fechaFin, contentFont);
    }
    
    private void addEmployeeSales(Document document, CONSULTASDAO dao, Usuario usuario, Date fechaInicio, Date fechaFin, Font contentFont) throws SQLException, DocumentException {
        Map<String, Integer> ventasDiarias = dao.obtenerVentasDiariasPorEmpleado(new java.sql.Date(fechaInicio.getTime()));
        Map<String, Integer> ventasSemanales = dao.obtenerVentasSemanalesPorEmpleado(new java.sql.Date(fechaInicio.getTime()), new java.sql.Date(fechaFin.getTime()));
        Map<String, Integer> ventasMensuales = dao.obtenerVentasMensualesPorEmpleado(new java.sql.Date(fechaInicio.getTime()), new java.sql.Date(fechaFin.getTime()));
        
        document.add(new Paragraph("\tDiarias: " + ventasDiarias.getOrDefault(usuario.getNombreUsuario(), 0), contentFont));
        document.add(new Paragraph("\tSemanales: " + ventasSemanales.getOrDefault(usuario.getNombreUsuario(), 0), contentFont));
        document.add(new Paragraph("\tMensuales: " + ventasMensuales.getOrDefault(usuario.getNombreUsuario(), 0), contentFont));
        document.add(new Paragraph("\tTotal de ventas: " + (ventasDiarias.getOrDefault(usuario.getNombreUsuario(), 0)
                + ventasSemanales.getOrDefault(usuario.getNombreUsuario(), 0)
                + ventasMensuales.getOrDefault(usuario.getNombreUsuario(), 0)), contentFont));
        document.add(new Paragraph("\n")); // Space
    }
    
    private void addEmployeeProductsSold(Document document, CONSULTASDAO dao, Usuario usuario, Date fechaInicio, Date fechaFin, Font contentFont) throws SQLException, DocumentException {
        Map<String, Integer> productosVendidosDiarios = dao.obtenerProductosVendidosDiariosPorEmpleado(new java.sql.Date(fechaInicio.getTime()));
        Map<String, Integer> productosVendidosSemanales = dao.obtenerProductosVendidosSemanalesPorEmpleado(new java.sql.Date(fechaInicio.getTime()), new java.sql.Date(fechaFin.getTime()));
        Map<String, Integer> productosVendidosMensuales = dao.obtenerProductosVendidosMensualesPorEmpleado(new java.sql.Date(fechaInicio.getTime()), new java.sql.Date(fechaFin.getTime()));
        
        document.add(new Paragraph("\tDiarios: " + productosVendidosDiarios.getOrDefault(usuario.getNombreUsuario(), 0), contentFont));
        document.add(new Paragraph("\tSemanales: " + productosVendidosSemanales.getOrDefault(usuario.getNombreUsuario(), 0), contentFont));
        document.add(new Paragraph("\tMensuales: " + productosVendidosMensuales.getOrDefault(usuario.getNombreUsuario(), 0), contentFont));
        document.add(new Paragraph("\tTotal de productos vendidos: " + (productosVendidosDiarios.getOrDefault(usuario.getNombreUsuario(), 0)
                + productosVendidosSemanales.getOrDefault(usuario.getNombreUsuario(), 0)
                + productosVendidosMensuales.getOrDefault(usuario.getNombreUsuario(), 0)), contentFont));
        document.add(new Paragraph("\n")); // Space
    }
    
    private void addEmployeePerformance(Document document, CONSULTASDAO dao, Usuario usuario, Date fechaInicio, Date fechaFin, Font contentFont) throws SQLException, DocumentException {
        Map<String, Integer> ventasDiarias = dao.obtenerVentasDiariasPorEmpleado(new java.sql.Date(fechaInicio.getTime()));
        
        int totalVentasDiarias = ventasDiarias.getOrDefault(usuario.getNombreUsuario(), 0);
        if (totalVentasDiarias > 1000) {
            document.add(new Paragraph("\tBuen desempeño: Sí", contentFont));
        } else {
            document.add(new Paragraph("\tBuen desempeño: No", contentFont));
        }
        document.add(new Paragraph("\n")); // Space
    }
}
