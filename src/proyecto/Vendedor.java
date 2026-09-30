package proyecto;

/**
 * Representa la información de un vendedor y el valor total
 * recaudado por concepto de sus ventas.
 *
 * @author Proyecto académico
 * @version 1.0
 */
public class Vendedor {

    private final String tipoDocumento;
    private final long numeroDocumento;
    private final String nombres;
    private final String apellidos;
    private double totalRecaudado;

    /**
     * Construye un vendedor.
     *
     * @param tipoDocumento tipo de documento.
     * @param numeroDocumento número de documento.
     * @param nombres nombres del vendedor.
     * @param apellidos apellidos del vendedor.
     */
    public Vendedor(String tipoDocumento, long numeroDocumento,
            String nombres, String apellidos) {
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.totalRecaudado = 0.0;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public long getNumeroDocumento() {
        return numeroDocumento;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public double getTotalRecaudado() {
        return totalRecaudado;
    }

    /**
     * Acumula un nuevo valor al total recaudado.
     *
     * @param valor valor de la venta.
     */
    public void agregarRecaudo(double valor) {
        totalRecaudado += valor;
    }

    /**
     * Retorna el nombre completo.
     *
     * @return nombres y apellidos.
     */
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
}
