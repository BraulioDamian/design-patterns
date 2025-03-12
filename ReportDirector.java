/*
 * Director class for report generation
 */
package Configuraciones.reportes;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

/**
 * Director class that coordinates the report generation process
 */
public class ReportDirector {
    private final ReportFactory reportFactory;
    
    public ReportDirector(ReportFactory reportFactory) {
        this.reportFactory = reportFactory;
    }
    
    /**
     * Generates a report using the configured report factory
     * @param outputPath path where the PDF file will be saved
     * @param reportTitle title of the report
     * @param fechaInicio start date for the report data
     * @param fechaFin end date for the report data
     * @throws DocumentException if there's an error creating the PDF document
     * @throws IOException if there's an error writing to the file
     * @throws SQLException if there's an error accessing the database
     */
    public void generateReport(String outputPath, String reportTitle, Date fechaInicio, Date fechaFin) 
            throws DocumentException, IOException, SQLException {
        // Create reports directory if it doesn't exist
        File reportsDir = new File("reportes");
        if (!reportsDir.exists()) {
            reportsDir.mkdirs();
        }
        
        // Initialize chart generator
        ChartGenerator chartGenerator = reportFactory.createChartGenerator();
        chartGenerator.initialize(fechaInicio, fechaFin);
        
        // Create PDF document
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(outputPath));
        document.open();
        
        // Get report formatter
        ReportFormatter formatter = reportFactory.createReportFormatter();
        
        // Add header
        formatter.addHeader(document, reportTitle);
        
        // Generate content
        ReportContentGenerator contentGenerator = reportFactory.createReportContentGenerator();
        contentGenerator.generateContent(document, fechaInicio, fechaFin);
        
        // Add footer
        formatter.addFooter(document);
        
        // Close document
        document.close();
    }
}
