package Configuraciones;

import java.util.Date;
import java.util.Map;

public class GenerarGraficaCommand implements Command {
    private ChartGenerator generador;
    private String tipoGrafica; // "producto", "empleado", etc.
    private Date fechaInicio;
    private Date fechaFin;

    public GenerarGraficaCommand(ChartGenerator generador, String tipoGrafica, Date fechaInicio, Date fechaFin) {
        this.generador = generador;
        this.tipoGrafica = tipoGrafica;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    @Override
    public void execute() throws Exception {
        switch (tipoGrafica) {
            case "producto":
                generador.generarGraficaVentasPorProducto(fechaInicio, fechaFin, "ventas_por_producto.png");
                break;
            case "empleado":
                generador.generarGraficaVentasPorEmpleado(fechaInicio, fechaFin, "ventas_por_empleado.png");
                break;
            // Agregar más casos según sea necesario
        }
    }
}