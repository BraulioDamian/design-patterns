/*
 * Abstract Factory Interface for Report Factories
 */
package Configuraciones.reportes;

import java.util.Date;

/**
 * Abstract Factory Interface for creating report-related components
 */
public interface ReportFactory {
    /**
     * Creates a chart generator for the specific report type
     * @return ChartGenerator instance
     */
    ChartGenerator createChartGenerator();
    
    /**
     * Creates a report content generator for the specific report type
     * @return ReportContentGenerator instance
     */
    ReportContentGenerator createReportContentGenerator();
    
    /**
     * Creates a report formatter for the specific report type
     * @return ReportFormatter instance
     */
    ReportFormatter createReportFormatter();
}
