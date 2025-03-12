/*
 * Concrete formatter for sales reports
 */
package Configuraciones.reportes.sales;

import Configuraciones.reportes.ReportFormatter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;

import java.util.Date;

/**
 * Concrete implementation of ReportFormatter for sales reports
 */
public class SalesReportFormatter implements ReportFormatter {

    @Override
    public Font getTitleFont() {
        return new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    }

    @Override
    public Font getSectionTitleFont() {
        return new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
    }

    @Override
    public Font getContentFont() {
        return new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
    }

    @Override
    public void addHeader(Document document, String title) throws DocumentException {
        Font fontTitulo = getTitleFont();
        Paragraph titulo = new Paragraph(title, fontTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);
        document.add(new Paragraph("\n")); // Espacio
    }

    @Override
    public void addFooter(Document document) throws DocumentException {
        document.add(new Paragraph("\n")); // Espacio
        Paragraph footer = new Paragraph("Reporte generado el " + new Date(), getContentFont());
        footer.setAlignment(Element.ALIGN_RIGHT);
        document.add(footer);
    }
}
