package DBObjetos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductoDemo {
/*    public static void main(String[] args) {
        System.out.println("=== VENTAJAS DEL BUILDER SOBRE SETTERS ===");
        System.out.println();

        // CASO 1: Creación de productos para diferentes vistas
        System.out.println("CASO DE USO: DIFERENTES VISTAS DEL PRODUCTO");
        System.out.println();

        // Vista de catálogo (con método tradicional)
        System.out.println("Método con Setters para Vista de Catálogo:");
        Producto catalogoTradicional = new Producto.ProductoBuilder().build();
        catalogoTradicional.setProductoID(1001);
        catalogoTradicional.setNombre("Cereal Integral");
        catalogoTradicional.setDescripcion("Cereal de maíz y trigo integral");
        catalogoTradicional.setPrecio(68.90);
        catalogoTradicional.setMarca("Kellogg's");
        catalogoTradicional.setTamañoNeto("500g");

        System.out.println("- Producto para catálogo (Setters): " + catalogoTradicional.getNombre());
        System.out.println("  * Problema: No se definieron datos de inventario, pero accidentalmente podría hacerse");
        catalogoTradicional.setUnidadesDisponibles(45); // Esto no debería ocurrir en un objeto de catálogo
        System.out.println("  * Se modificó accidentalmente: Unidades = " + catalogoTradicional.getUnidadesDisponibles());
        System.out.println();

        // Vista de catálogo (con Builder)
        System.out.println("Método con Builder para Vista de Catálogo:");
        Producto catalogoBuilder = new Producto.ProductoBuilder()
                .productoID(1001)
                .nombre("Cereal Integral")
                .descripcion("Cereal de maíz y trigo integral")
                .precio(68.90)
                .marca("Kellogg's")
                .tamañoNeto("500g")
                .build();

        System.out.println("- Producto para catálogo (Builder): " + catalogoBuilder.getNombre());
        System.out.println("  * Ventaja: El objeto se crea de una vez con solo los datos necesarios");
        System.out.println("  * Unidades disponibles (no definido): " + catalogoBuilder.getUnidadesDisponibles());
        System.out.println();

        // CASO 2: Creación de productos con propiedades obligatorias y opcionales
        System.out.println("CASO DE USO: PROPIEDADES OBLIGATORIAS VS OPCIONALES");
        System.out.println();

        // Con setters (problema)
        System.out.println("Método con Setters (problema):");
        Producto productoIncompleto = new Producto.ProductoBuilder().build();
        productoIncompleto.setNombre("Pan Integral");
        productoIncompleto.setMarca("Bimbo");
        // Olvidamos establecer el precio (propiedad obligatoria)

        System.out.println("- Producto incompleto: " + productoIncompleto.getNombre());
        System.out.println("  * Precio (olvidado): " + productoIncompleto.getPrecio() + " (valor por defecto)");
        System.out.println("  * Problema: No hay forma de asegurar que se establezcan propiedades obligatorias");
        System.out.println();

        // Con Builder (solución)
        System.out.println("Método con Builder (solución, usando comentarios):");
        try {
            // En un proyecto real, podríamos modificar la clase Producto para validar
            // propiedades obligatorias en el método build()
            Producto productoObligatorio = new Producto.ProductoBuilder()
                    .nombre("Pan Integral")
                    .marca("Bimbo")
                    // .precio(32.00) // Comentado para simular el olvido
                    .build();

            System.out.println("- Producto construido: " + productoObligatorio.getNombre());

            // Simulamos la validación que tendría lugar en un Builder mejorado
            if (productoObligatorio.getPrecio() <= 0) {
                throw new IllegalStateException("El producto debe tener un precio válido");
            }
        } catch (Exception e) {
            System.out.println("  * Simulación de error: " + e.getMessage());
            System.out.println("  * Ventaja: Un Builder bien diseñado validaría las propiedades obligatorias");
        }
        System.out.println();

        // CASO 3: Inmutabilidad
        System.out.println("CASO DE USO: INMUTABILIDAD");
        System.out.println();

        // Con setters (problema)
        System.out.println("Método con Setters (problema):");
        Producto productoMutable = new Producto.ProductoBuilder().build();
        productoMutable.setProductoID(1003);
        productoMutable.setNombre("Leche Entera");
        productoMutable.setPrecio(25.50);

        System.out.println("- Producto original: " + productoMutable.getNombre() + " ($" + productoMutable.getPrecio() + ")");

        // En alguna parte de la aplicación, alguien modifica el precio
        productoMutable.setPrecio(20.00);

        System.out.println("  * Después de cambio: " + productoMutable.getNombre() + " ($" + productoMutable.getPrecio() + ")");
        System.out.println("  * Problema: El objeto puede ser modificado en cualquier momento");
        System.out.println();

        // Con Builder (solución - simulación)
        System.out.println("Con Builder (simulación de inmutabilidad):");
        final Producto productoInmutable = new Producto.ProductoBuilder()
                .productoID(1003)
                .nombre("Leche Entera")
                .precio(25.50)
                .build();

        System.out.println("- Producto inmutable: " + productoInmutable.getNombre() + " ($" + productoInmutable.getPrecio() + ")");
        System.out.println("  * Nota: La clase actual tiene setters, pero podría ser mejorada para eliminarlos");
        System.out.println("  * Ventaja: El Builder facilita crear objetos completos y luego hacerlos inmutables");
        System.out.println();

        // CASO 4: Creación en un solo paso vs. múltiples modificaciones
        System.out.println("CASO DE USO: CREACIÓN DE OBJETOS COMPLETOS");
        System.out.println();

        System.out.println("Comparación de cantidad de líneas y claridad:");
        System.out.println("--------------------------------------------------------");
        System.out.println("Con setters (muchas líneas separadas):");
        System.out.println("  Producto p = new Producto.ProductoBuilder().build();");
        System.out.println("  p.setProductoID(1004);");
        System.out.println("  p.setNombre(\"Yogurt Natural\");");
        System.out.println("  p.setDescripcion(\"Yogurt natural sin azúcar\");");
        System.out.println("  p.setAreaID(3);");
        System.out.println("  p.setPrecio(18.90);");
        System.out.println("  p.setUnidadesDisponibles(50);");
        System.out.println("  p.setNivelReorden(15);");
        System.out.println("  // ... y así para cada propiedad");
        System.out.println();

        System.out.println("Con Builder (una sola instrucción encadenada):");
        System.out.println("  Producto p = new Producto.ProductoBuilder()");
        System.out.println("      .productoID(1004)");
        System.out.println("      .nombre(\"Yogurt Natural\")");
        System.out.println("      .descripcion(\"Yogurt natural sin azúcar\")");
        System.out.println("      .areaID(3)");
        System.out.println("      .precio(18.90)");
        System.out.println("      .unidadesDisponibles(50)");
        System.out.println("      .nivelReorden(15)");
        System.out.println("      // ... resto de propiedades");
        System.out.println("      .build();");
        System.out.println();

        System.out.println("=== RESUMEN DE VENTAJAS DEL BUILDER ===");
        System.out.println();
        System.out.println("1. ESTADO CONSISTENTE:");
        System.out.println("   - Con setters: El objeto puede estar en estado inconsistente entre llamadas");
        System.out.println("   - Con Builder: El objeto solo se crea cuando todas las propiedades están listas");
        System.out.println();

        System.out.println("2. CLARIDAD DE CÓDIGO:");
        System.out.println("   - Con setters: Múltiples líneas separadas, menos legible");
        System.out.println("   - Con Builder: Una única expresión encadenada, más clara y expresiva");
        System.out.println();

        System.out.println("3. SOPORTE PARA INMUTABILIDAD:");
        System.out.println("   - Con setters: Se requieren setters públicos, lo que impide la inmutabilidad");
        System.out.println("   - Con Builder: Permite eliminar los setters públicos, habilitando objetos inmutables");
        System.out.println();

        System.out.println("4. CONTROL DE PROPIEDADES OBLIGATORIAS:");
        System.out.println("   - Con setters: Difícil garantizar que todas las propiedades requeridas estén establecidas");
        System.out.println("   - Con Builder: El método build() puede validar que todas las propiedades necesarias existan");
        System.out.println();

        System.out.println("5. CLARIDAD DE INTENCIÓN:");
        System.out.println("   - Con setters: No es obvio cuáles propiedades son obligatorias y cuáles opcionales");
        System.out.println("   - Con Builder: Se puede diseñar para hacer evidentes las propiedades obligatorias");
    }*/
}