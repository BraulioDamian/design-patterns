# Patrón Builder en las Clases Producto y Usuario

## Participantes
- **Builder**: `ProductoBuilder`, `UsuarioBuilder`.
- **Productos Construidos**: `Producto`, `Usuario`.
- **Responsable**: [Tu nombre].

## Finalidad
- Simplificar la creación de objetos complejos con múltiples atributos.
- Permitir configuraciones flexibles (ejemplo: omitir `fechaCaducidad` si no aplica).

## Mejoras Clave
1. **Reducción de Errores**: Evita parámetros incorrectos en constructores largos.
2. **Código Más Limpio**: Métodos como `.precio(20.5)` son autoexplicativos.
3. **Validaciones Centralizadas**: Reglas de negocio en un solo lugar (`build()`).

## Ejemplo de Uso en el Proyecto
```java
// Crear un producto con Builder
Producto producto = Producto.builder()
    .nombre("Arroz")
    .precio(18.5)
    .marca("Sello Rojo")
    .build();

// Crear un usuario administrador
Usuario usuario = Usuario.builder()
    .nombreUsuario("braulio")
    .email("braulio@tienda.com")
    .rol(Usuario.Rol.GERENTE)
    .build();
