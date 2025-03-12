/*
 * Interface for report formatters
 */
package Configuraciones.reportes;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;

/**
 * Interface defining report formatting capabilities
 */
public interface ReportFormatter {
    /**
     * Get title font for the report
     * @return Font instance for titles
     */
    Font getTitleFont();
    
    /**
     * Get section title font for the report
     * @return Font instance for section titles
     */
    Font getSectionTitleFont();
    
    /**
     * Get content font for the report
     * @return Font instance for content
     */
    Font getContentFont();
    
    /**
     * Add header to the document
     * @param document the document to add the header to
     * @param title the report title
     * @throws DocumentException if there's an error adding content to the document
     */
    void addHeader(Document document, String title) throws DocumentException;
    
    /**
     * Add footer to the document
     * @param document the document to add the footer to
     * @throws DocumentException if there's an error adding content to the document
     */
    void addFooter(Document document) throws DocumentException;
}
