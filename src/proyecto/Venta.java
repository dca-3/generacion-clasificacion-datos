package proyecto;

/**
 * Representa una línea de venta compuesta por un producto y
 * la cantidad vendida.
 *
 * @author Proyecto académico
 * @version 1.0
 */
public class Venta {

    private final long idProducto;
    private final int cantidad;

    /**
     * Construye una venta.
     *
     * @param idProducto identificador del producto.
     * @param cantidad cantidad vendida.
     */
    public Venta(long idProducto, int cantidad) {
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }

    public long getIdProducto() {
        return idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }
}
