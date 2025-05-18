package PatronInterpreter;

import ConexionDB.Conexion_DB;
import Consultas.CONSULTASDAO;
import DBObjetos.Area;
import DBObjetos.Producto;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Expresión terminal para filtrar productos por área.
 */
public class AreaExpression implements Expression {
    private int areaId;
    private String nombreArea;  // Añadir un campo para el nombre del área
    
    public AreaExpression(int areaId) {
        this.areaId = areaId;
        // Intentar obtener el nombre del área desde la base de datos
        try {
            Connection conexion = Conexion_DB.getConexion();
            CONSULTASDAO dao = new CONSULTASDAO(conexion);
            for (Area area : dao.obtenerAreas()) {
                if (area.getAreaID() == areaId) {
                    this.nombreArea = area.getNombreArea();
                    break;
                }
            }
            conexion.close();
        } catch (SQLException e) {
            // Si hay un error, asignar un nombre predeterminado
            this.nombreArea = "Area " + areaId;
        }
        
        // Si no se encontró el nombre del área
        if (this.nombreArea == null) {
            this.nombreArea = "Area " + areaId;
        }
    }
    
    @Override
    public List<Producto> interpret(List<Producto> productos) {
        return productos.stream()
                .filter(p -> p.getAreaID() == areaId)
                .collect(Collectors.toList());
    }
    
    @Override
    public String toString() {
        return "productos del área " + nombreArea;
    }
}