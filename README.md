# Documentación del Patrón Singleton en el Código

## Objetivo del Patrón Singleton

El patrón Singleton tiene como finalidad asegurar que una clase tenga **una única instancia** y proporcionar un **punto de acceso global** a dicha instancia. En tu código, este patrón se utiliza para gestionar la sesión del usuario de forma centralizada, de modo que todas las operaciones relacionadas con la sesión se realicen a través de una única instancia de la clase **SesionManager**.

## Funcionamiento en el Código

### 1. Constructor Privado

- La clase **SesionManager** define un constructor privado (`private SesionManager() { }`). Esto evita que se puedan crear instancias adicionales desde otras clases, forzando el uso del método que controla la creación de la instancia.

### 2. Instancia Única

- Se declara un atributo estático privado:
  ```java
  private static SesionManager instance;
  ```
  Este atributo almacenará la única instancia de la clase. Si aún no se ha creado la instancia, se inicializará cuando se invoque el método `getInstance()`.

### 3. Método de Acceso Global

- El método `getInstance()` es declarado como `public static synchronized`:
  ```java
  public static synchronized SesionManager getInstance() {
      if (instance == null) {
          instance = new SesionManager();
      }
      return instance;
  }
  ```
  La sincronización garantiza que en un entorno multihilo solo se cree una única instancia, evitando condiciones de carrera.

### 4. Gestión de la Sesión

- **Inicio de Sesión (`login`):**  
  El método `login(Usuario usuario)` asigna el usuario autenticado a la variable de instancia `usuarioLogueado`:
  ```java
  public void login(Usuario usuario) {
      this.usuarioLogueado = usuario;
  }
  ```
- **Cierre de Sesión (`logout`):**  
  El método `logout()` limpia la sesión y llama a un método interno `resetSingletons()` para reiniciar otros componentes que también utilizan el patrón Singleton. Esto permite que, al cerrar la sesión, se puedan restablecer instancias relacionadas (como `MenuPrincipal`, `Venta`, etc.):
  ```java
  public void logout() {
      this.usuarioLogueado = null;
      resetSingletons();
  }
  ```
- **Obtener el Usuario Actual:**  
  El método `getUsuarioLogueado()` retorna el usuario actualmente autenticado:
  ```java
  public Usuario getUsuarioLogueado() {
      return usuarioLogueado;
  }
  ```
- **Representación de la Sesión:**  
  Se sobreescribe el método `toString()` para facilitar la depuración o el registro, mostrando el estado actual del usuario logueado.

## Integración con Otros Componentes

En el método `txtAccederActionPerformed` de la clase `LOGINN` se observa el uso del Singleton para gestionar la sesión del usuario:
- Se obtiene la instancia del `SesionManager` y se invoca el método `login(usuario)` para registrar el usuario autenticado.
- Además, se hace referencia a otros componentes (como `MenuPrincipal`) que también implementan el patrón Singleton para garantizar que solo exista una única instancia en toda la aplicación.

## Diagrama UML de la Clase SesionManager

El siguiente diagrama UML representa la estructura de la clase **SesionManager**:

```mermaid
@startuml

class Usuario

class SesionManager {
    - static SesionManager instance
    - Usuario usuarioLogueado
    - SesionManager()
    + static synchronized SesionManager getInstance()
    + void login(Usuario usuario)
    + void logout()
    + Usuario getUsuarioLogueado()
    + String toString()
    - void resetSingletons()
}

class LOGINN {
    + void txtAccederActionPerformed(ActionEvent evt)
}

SesionManager --> Usuario : usa
LOGINN --> SesionManager : invoca

@enduml

```

### Descripción del Diagrama

- **Atributos:**
  - `instance`: Atributo estático privado que almacena la única instancia de `SesionManager`.
  - `usuarioLogueado`: Almacena el usuario actualmente autenticado.
  
- **Constructor:**
  - `SesionManager()`: Constructor privado para impedir la creación de instancias desde fuera de la clase.

- **Métodos:**
  - `getInstance()`: Método estático sincronizado para obtener la instancia única.
  - `login(Usuario)`: Registra el usuario autenticado en la sesión.
  - `logout()`: Cierra la sesión del usuario y reinicia las instancias de otros Singletons asociados.
  - `getUsuarioLogueado()`: Retorna el usuario actualmente autenticado.
  - `toString()`: Retorna una representación en forma de cadena del estado de la sesión.
  - `resetSingletons()`: Método privado para reiniciar otras instancias Singleton en la aplicación al cerrar la sesión.

## Conclusión

La implementación del patrón Singleton en tu código permite mantener un control centralizado sobre la sesión del usuario, asegurando que solo exista una instancia de **SesionManager** en la aplicación. Esto facilita el manejo de la autenticación, la depuración y la coordinación de otros componentes relacionados que también utilizan este patrón.
