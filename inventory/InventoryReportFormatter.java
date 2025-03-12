/*
 * Concrete formatter for inventory reports
 */
package Configuraciones.reportes.inventory;

import Configuraciones.reportes.ReportFormatter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;

import java.util.Date;

/**
 * Concrete implementation of ReportFormatter for inventory reports
 */
public class InventoryReportFormatter implements ReportFormatter {

    @Override
    public Font getTitleFont() {
        return new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
    }

    @Override
    public Font getSectionTitleFont() {
        return new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    }

    @Override
    public Font getContentFont() {
        return new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
    }

    @Override
    public void addHeader(Document document, String title) throws DocumentException {
        Font fontTitulo = getTitleFont();
        Paragraph titulo = new Paragraph(title, fontTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);
        
        // Add date
        Font dateFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.ITALIC);
        Paragraph date = new Paragraph("Fecha: " + new Date(), dateFont);
        date.setAlignment(Element.ALIGN_RIGHT);
        document.add(date);
        
        document.add(new Paragraph("\n")); // Space
    }

    @Override
    public void addFooter(Document document) throws DocumentException {
        document.add(new Paragraph("\n")); // Space
        Paragraph footer = new Paragraph("Reporte de Inventario - Generado automáticamente", getContentFont());
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);
    }
}
