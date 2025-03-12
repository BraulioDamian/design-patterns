/*
 * Interface for chart generators
 */
package Configuraciones.reportes;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

/**
 * Interface defining chart generation capabilities
 */
public interface ChartGenerator {
    /**
     * Initialize the chart generator
     * @param fechaInicio start date for the report
     * @param fechaFin end date for the report
     * @throws IOException if there's an error writing the chart image
     * @throws SQLException if there's an error accessing the database
     */
    void initialize(Date fechaInicio, Date fechaFin) throws IOException, SQLException;
    
    /**
     * Generate all charts for the report
     * @param fechaInicio start date for the report
     * @param fechaFin end date for the report
     * @throws IOException if there's an error writing the chart images
     * @throws SQLException if there's an error accessing the database
     */
    void generateCharts(Date fechaInicio, Date fechaFin) throws IOException, SQLException;
}
