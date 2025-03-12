/*
 * Interface for report content generators
 */
package Configuraciones.reportes;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

import java.sql.SQLException;
import java.util.Date;

/**
 * Interface defining report content generation capabilities
 */
public interface ReportContentGenerator {
    /**
     * Generate content for the report document
     * @param document the document to add content to
     * @param fechaInicio start date for the report
     * @param fechaFin end date for the report
     * @throws DocumentException if there's an error adding content to the document
     * @throws SQLException if there's an error accessing the database
     */
    void generateContent(Document document, Date fechaInicio, Date fechaFin) throws DocumentException, SQLException;
}
