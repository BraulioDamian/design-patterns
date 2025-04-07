package Factory;

import DBObjetos.Producto;
import java.time.LocalDate;


public class Main {
    
    public static void main(String[] args) {
        System.out.println("---------- Testing Factory Pattern ----------");
        
        ProductoFactory factoryPerecedero = ProductoFactory.getFactory("perecedero");
        Producto lechuga = factoryPerecedero.crearProducto("Lechuga", 1, 15.50, 50);
        System.out.println("\nProducto Perecedero (método básico):");
        System.out.println("Nombre: " + lechuga.getNombre());
        System.out.println("Precio: $" + lechuga.getPrecio());
        System.out.println("Unidades: " + lechuga.getUnidadesDisponibles());
        System.out.println("Fecha de caducidad: " + lechuga.getFechaCaducidad());
        
        ProductoPerecederoFactory factoryPerecederoExt = (ProductoPerecederoFactory) factoryPerecedero;
        LocalDate fechaCaducidad = LocalDate.now().plusDays(15);
        Producto yogurt = factoryPerecederoExt.crearProducto(
                "Yogurt", 2, 25.99, 100, 
                fechaCaducidad, "7501234567890", "Lala", "1 Litro");
        
        System.out.println("\nProducto Perecedero (método extendido):");
        System.out.println("Nombre: " + yogurt.getNombre());
        System.out.println("Precio: $" + yogurt.getPrecio());
        System.out.println("Unidades: " + yogurt.getUnidadesDisponibles());
        System.out.println("Fecha de caducidad: " + yogurt.getFechaCaducidad());
        System.out.println("Código de barras: " + yogurt.getCodigoBarras());
        System.out.println("Marca: " + yogurt.getMarca());
        System.out.println("Contenido: " + yogurt.getContenido());
        
        ProductoFactory factoryNoPerecedero = ProductoFactory.getFactory("noPerecedero");
        Producto arroz = factoryNoPerecedero.crearProducto("Arroz", 3, 32.50, 200);
        System.out.println("\nProducto No Perecedero (método básico):");
        System.out.println("Nombre: " + arroz.getNombre());
        System.out.println("Precio: $" + arroz.getPrecio());
        System.out.println("Unidades: " + arroz.getUnidadesDisponibles());
        System.out.println("Fecha de caducidad: " + arroz.getFechaCaducidad());
        
        ProductoNoPerecederoFactory factoryNoPerecederoExt = (ProductoNoPerecederoFactory) factoryNoPerecedero;
        Producto detergente = factoryNoPerecederoExt.crearProducto(
                "Detergente", 4, 89.90, 75, 
                "7509876543210", "Ariel", "5 Kg");
        
        System.out.println("\nProducto No Perecedero (método extendido):");
        System.out.println("Nombre: " + detergente.getNombre());
        System.out.println("Precio: $" + detergente.getPrecio());
        System.out.println("Unidades: " + detergente.getUnidadesDisponibles());
        System.out.println("Fecha de caducidad: " + detergente.getFechaCaducidad());
        System.out.println("Código de barras: " + detergente.getCodigoBarras());
        System.out.println("Marca: " + detergente.getMarca());
        System.out.println("Contenido: " + detergente.getContenido());
        
        try {
            ProductoFactory invalidFactory = ProductoFactory.getFactory("invalidType");
        } catch (IllegalArgumentException e) {
            System.out.println("\nError esperado: " + e.getMessage());
        }
        
        System.out.println("\n---------- Factory Pattern Test Completed ----------");
    }
}
