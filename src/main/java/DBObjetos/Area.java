package DBObjetos;

import Composite.ComponenteInventario;

import java.util.ArrayList;
import java.util.List;

public class Area implements ComponenteInventario {
    private int areaID;
    private String nombreArea;
    private String descripcion;

    private List<ComponenteInventario> componentes= new ArrayList<>();
    private Integer padreAreaID;

    // Constructor
    public Area(int areaID, String nombreArea, String descripcion) {
        this.areaID = areaID;
        this.nombreArea = nombreArea;
        this.descripcion = descripcion;
    }

    public Area() {

    }
    // Getters
    // Getters
    public int getAreaID() {
        return areaID;
    }

    public String getNombreArea() {
        return nombreArea;
    }

    public String getDescripcion() {
        return descripcion;
    }

    // Setters
    public void setAreaID(int areaID) {
        this.areaID = areaID;
    }

    public void setNombreArea(String nombre) {
        this.nombreArea = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // toString

    @Override
    public String toString() {
        return "Area{" +
                "areaID=" + areaID +
                ", nombreArea='" + nombreArea + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }





        // Getters y setters existentes...

        @Override
        public double getPrecioTotal() {
            return componentes.stream()
                    .mapToDouble(ComponenteInventario::getPrecioTotal)
                    .sum();
        }

        @Override
        public void aplicarDescuento(double porcentaje) {
            componentes.forEach(c -> c.aplicarDescuento(porcentaje));
        }

        @Override
        public void agregar(ComponenteInventario componente) {
            componentes.add(componente);
        }

        @Override
        public void eliminar(ComponenteInventario componente) {
            componentes.remove(componente);
        }

        @Override
        public List<ComponenteInventario> getHijos() {
            return componentes;
        }

        @Override
        public String getNombre() {
            return nombreArea;
        }

        // Area.java
        public Integer getPadreAreaID() {
            return padreAreaID;
        }

        public void setPadreAreaID(Integer padreAreaID) {
            this.padreAreaID = padreAreaID;
        }





}