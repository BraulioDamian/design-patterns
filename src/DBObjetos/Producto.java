/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DBObjetos;

/**
 *
 * @author braul
 */
import java.time.LocalDate;

public class Producto {
    private int productoID;
    private String nombre;
    private String descripcion;
    private int areaID;
    private double precio;
    private int unidadesDisponibles;
    private int nivelReorden;
    private LocalDate fechaCaducidad;
    private String codigoBarras;
    private String tamañoNeto;
    private String marca;
    private String contenido;
    private String nombreArea; // Nuevo campo para el nombre del área
    private int cantidad;  // Campo adicional para la cantidad

    // Constructor
    private Producto(){}

    public static ProductoBuilder builder(){
        return new ProductoBuilder();
    }
    
    // Getters

        public String getNombreArea() {
        return nombreArea;
    }

    // Setter para nombreArea
    public void setNombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
    }
    
public int getProductoID() {
    return productoID;
}

public String getNombre() {
    return nombre;
}

public String getDescripcion() {
    return descripcion;
}

public int getAreaID() {
    return areaID;
}

public double getPrecio() {
    return precio;
}

public int getUnidadesDisponibles() {
    return unidadesDisponibles;
}

public int getNivelReorden() {
    return nivelReorden;
}

public LocalDate getFechaCaducidad() {
    return fechaCaducidad;
}

public String getCodigoBarras() {
    return codigoBarras;
}

public String getTamañoNeto() {
    return tamañoNeto;
}

public String getMarca() {
    return marca;
}

public String getContenido() {
    return contenido;
}


    public int getCantidad() { 
        return cantidad; 
    }
    
// Setters
public void setProductoID(int productoID) {
    this.productoID = productoID;
}

public void setNombre(String nombre) {
    this.nombre = nombre;
}

public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
}

public void setAreaID(int areaID) {
    this.areaID = areaID;
}

public void setPrecio(double precio) {
    this.precio = precio;
}

public void setUnidadesDisponibles(int unidadesDisponibles) {
    this.unidadesDisponibles = unidadesDisponibles;
}

public void setNivelReorden(int nivelReorden) {
    this.nivelReorden = nivelReorden;
}

public void setFechaCaducidad(LocalDate fechaCaducidad) {
    this.fechaCaducidad = fechaCaducidad;
}

public void setCodigoBarras(String codigoBarras) {
    this.codigoBarras = codigoBarras;
}

public void setTamañoNeto(String tamañoNeto) {
    this.tamañoNeto = tamañoNeto;
}

public void setMarca(String marca) {
    this.marca = marca;
}

public void setContenido(String contenido) {
    this.contenido = contenido;
}

    public void setCantidad(int cantidad) { 
        this.cantidad = cantidad; 
    }


    // toString
    @Override
    public String toString() {
        return "Producto{" +
                "productoID=" + productoID +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", areaID=" + areaID +
                ", precio=" + precio +
                ", unidadesDisponibles=" + unidadesDisponibles +
                ", nivelReorden=" + nivelReorden +
                ", fechaCaducidad=" + fechaCaducidad +
                ", codigoBarras='" + codigoBarras + '\'' +
                ", tamañoNeto='" + tamañoNeto + '\'' +
                ", marca='" + marca + '\'' +
                ", contenido='" + contenido + '\'' +
                ", nombreArea='" + nombreArea + '\'' +
                ", cantidad=" + cantidad +
                '}';
    }
     // Clase Builder interna
    public static class ProductoBuilder {
        private Producto producto = new Producto();

        public ProductoBuilder productoID(int productoID) {
            producto.setProductoID(productoID);
            return this;
        }

        public ProductoBuilder nombre(String nombre) {
            producto.setNombre(nombre);
            return this;
        }

        public ProductoBuilder precio(double precio) {
            producto.setPrecio(precio);
            return this;
        }

        public ProductoBuilder marca(String marca) {
            producto.setMarca(marca);
            return this;
        }

        // Métodos para TODOS los demás atributos...
        public ProductoBuilder descripcion(String descripcion) {
            producto.setDescripcion(descripcion);
            return this;
        }

        public ProductoBuilder fechaCaducidad(LocalDate fecha) {
            producto.setFechaCaducidad(fecha);
            return this;
        }
        public ProductoBuilder areaID(int areaID){
            producto.setAreaID(areaID);
            return this;
        }
        public ProductoBuilder unidadesDisponibles(int unidadesDisponibles){
            producto.setUnidadesDisponibles(unidadesDisponibles);
            return this;
        }
        public ProductoBuilder nivelReorden(int nivelReorden){
            producto.setNivelReorden(nivelReorden);
            return this;
        }
       
        public ProductoBuilder codigoBarras(String codigoBarras){
            producto.setCodigoBarras(codigoBarras);
            return this;
        }
        public ProductoBuilder tamañoNeto(String tamañoNeto){
            producto.setTamañoNeto(tamañoNeto);
            return this;
        }
        public ProductoBuilder contenido (String contenido){
            producto.setContenido(contenido);
            return this;
        }
        public ProductoBuilder nombreArea(String nombreArea){
            producto.setNombreArea(nombreArea);
            return this;
        }
        public ProductoBuilder cantidad( int cantidad){
            producto.setCantidad(cantidad);
            return this;
        }
        public Producto build() {
            // Validaciones básicas (personaliza según necesidades)
            if (producto.getNombre() == null || producto.getPrecio() <= 0) {
                throw new IllegalArgumentException("Nombre y precio son obligatorios");
            }
            return producto;
        }
    }
}

