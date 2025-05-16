package Venta;

import java.util.List;

import Configuraciones.Command;
import DBObjetos.Producto;

public class GenerarTicketCommand implements Command {
    private Cobro cobro;
    private List<Producto> productos;
    private double total;
    private double pago;

    public GenerarTicketCommand(Cobro cobro, List<Producto> productos, double total, double pago) {
        this.cobro = cobro;
        this.productos = productos;
        this.total = total;
        this.pago = pago;
    }

    @Override
    public void execute() throws Exception {
        cobro.generarPDF(productos, total, pago, pago - total);
    }
}