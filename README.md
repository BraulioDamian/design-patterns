# 🏭 Inventario Composite con DAO

## 📝 Descripción General

Esta implementación gestiona un inventario utilizando el **patrón Composite** para tratar de forma uniforme elementos individuales (productos) y compuestos (áreas). Además, se separa la lógica de acceso a datos a través de clases DAO para **Producto** y **Área**, permitiendo la persistencia en una base de datos.

## 🗂️ Tabla de Contenidos

- [Arquitectura y Actores](#-arquitectura-y-actores)
- [Diagrama UML](#-diagrama-uml)
- [Partes Importantes del Código](#-partes-importantes-del-código)
- [Uso y Ejecución](#-uso-y-ejecución)
- [Licencia](#-licencia)

## 🏗️ Arquitectura y Actores

### Patrón Composite

El patrón Composite permite tratar objetos individuales y compuestos de forma uniforme:

- **Producto** (Hoja):
  - Representa un elemento individual en el inventario
  - No puede contener otros objetos
  - Implementa todas las operaciones de la interfaz `ComponenteInventario`

- **Área** (Compuesto):
  - Puede contener productos u otras áreas
  - Forma una jerarquía de inventario
  - Implementa operaciones que se aplican recursivamente a sus componentes

### Data Access Objects (DAO)

- **ProductoDAO**:
  - Encapsula operaciones CRUD para productos
  - Gestiona la inserción, eliminación y actualización de productos
  - Maneja la conexión con la base de datos

- **AreaDAO**:
  - Gestiona operaciones CRUD para áreas
  - Soporta inserción de áreas con ID generado
  - Permite eliminación de áreas con todos sus productos asociados

## 🔍 Diagrama UML
![umlComposite](https://github.com/user-attachments/assets/3f4cc145-85d3-451c-b479-c101ebe5b9a1)


## 💻 Principales Componentes del Código

### 1. Interfaz `ComponenteInventario`

Define las operaciones comunes para productos y áreas:

```java
public interface ComponenteInventario {
    double getPrecioTotal();
    void aplicarDescuento(double porcentaje);
    void agregar(ComponenteInventario componente);
    void eliminar(ComponenteInventario componente);
    List<ComponenteInventario> getHijos();
    String getNombre();
}
```

### 2. Clase `Producto`

Implementación de un producto como elemento individual:

```java
public class Producto implements ComponenteInventario {
    @Override
    public double getPrecioTotal() {
        return precio * unidadesDisponibles;
    }

    @Override
    public void aplicarDescuento(double porcentaje) {
        this.precio *= (1 - porcentaje / 100);
    }

    // Métodos para manejo de componentes generan excepción
    @Override
    public void agregar(ComponenteInventario componente) {
        throw new UnsupportedOperationException("No se puede agregar a un producto individual");
    }
}
```

### 3. Clase `Area`

Implementación de un área como contenedor de componentes:

```java
public class Area implements ComponenteInventario {
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
}
```

## 🚀 Uso y Ejecución

### Requisitos Previos

- Java 11+
- Maven
- Base de datos configurada

### Pasos de Instalación

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/tu_usuario/inventario-composite.git
   cd inventario-composite
   ```

2. Configurar conexión de base de datos en `Conexion_DB.java`

3. Compilar y ejecutar:
   ```bash
   mvn clean install
   mvn exec:java -Dexec.mainClass="Composite.Main"
   ```

