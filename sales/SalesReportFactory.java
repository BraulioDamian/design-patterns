/*
 * Concrete factory for sales reports
 */
package Configuraciones.reportes.sales;

import Configuraciones.reportes.ChartGenerator;
import Configuraciones.reportes.ReportContentGenerator;
import Configuraciones.reportes.ReportFactory;
import Configuraciones.reportes.ReportFormatter;

/**
 * Concrete factory implementation for creating sales report components
 */
public class SalesReportFactory implements ReportFactory {
    
    @Override
    public ChartGenerator createChartGenerator() {
        return new SalesChartGenerator();
    }
    
    @Override
    public ReportContentGenerator createReportContentGenerator() {
        return new SalesReportContentGenerator();
    }
    
    @Override
    public ReportFormatter createReportFormatter() {
        return new SalesReportFormatter();
    }
}
