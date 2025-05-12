package Venta;

import DBObjetos.Producto;
import java.util.List;

import Configuraciones.Command;
import Consultas.CONSULTASDAO;

public class ProcesarVentaCommand implements Command {
    private CONSULTASDAO dao;
    private int usuarioId;
    private List<Producto> productos;

    public ProcesarVentaCommand(CONSULTASDAO dao, int usuarioId, List<Producto> productos) {
        this.dao = dao;
        this.usuarioId = usuarioId;
        this.productos = productos;
    }

    @Override
    public void execute() throws Exception {
        dao.completarVenta(usuarioId, productos);
    }
}