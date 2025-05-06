package DBObjetos;

import java.time.LocalDateTime;

public interface IVenta {
    /**
     * Método para realizar una venta.
     * @param usuarioID El ID del usuario que realiza la venta.
     * @param fechaVenta La fecha en la que se realiza la venta.
     * @param precioTotal El precio total de la venta.
     */
    void realizarVenta(int usuarioID, LocalDateTime fechaVenta, double precioTotal);
}
