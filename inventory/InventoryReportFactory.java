/*
 * Concrete factory for inventory reports
 */
package Configuraciones.reportes.inventory;

import Configuraciones.reportes.ChartGenerator;
import Configuraciones.reportes.ReportContentGenerator;
import Configuraciones.reportes.ReportFactory;
import Configuraciones.reportes.ReportFormatter;

/**
 * Concrete factory implementation for creating inventory report components
 */
public class InventoryReportFactory implements ReportFactory {
    
    @Override
    public ChartGenerator createChartGenerator() {
        return new InventoryChartGenerator();
    }
    
    @Override
    public ReportContentGenerator createReportContentGenerator() {
        return new InventoryReportContentGenerator();
    }
    
    @Override
    public ReportFormatter createReportFormatter() {
        return new InventoryReportFormatter();
    }
}
