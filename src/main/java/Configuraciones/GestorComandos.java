// src/main/java/Configuraciones/GestorComandos.java
package Configuraciones;

public class GestorComandos {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void ejecutarComando() throws Exception {
        if (command != null) {
            command.execute();
        }
    }
}