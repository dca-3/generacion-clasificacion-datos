package proyecto;

/**
 * Representa un producto disponible para la venta.
 *
 * @author Proyecto académico
 * @version 1.0
 */
public class Producto {

    private final long id;
    private final String nombre;
    private final double precio;

    /**
     * Construye un producto.
     *
     * @param id identificador del producto.
     * @param nombre nombre del producto.
     * @param precio precio unitario.
     */
    public Producto(long id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }
}
