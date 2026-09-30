package proyecto;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Genera archivos de entrada pseudoaleatorios y coherentes para
 * probar el sistema de generación y clasificación de datos.
 *
 * @author Proyecto académico
 * @version 1.0
 */
public class GenerateInfoFiles {

    private static final String CARPETA_ENTRADA = "archivosEntrada";
    private static final Random RANDOM = new Random();

    private static final String[] NOMBRES = {
            "Carlos", "Laura", "Andres", "Maria", "Juan",
            "Diana", "Felipe", "Camila", "Sofia", "Miguel"
    };

    private static final String[] APELLIDOS = {
            "Gomez", "Rodriguez", "Martinez", "Lopez", "Perez",
            "Garcia", "Sanchez", "Torres", "Ramirez", "Castro"
    };

    /**
     * Punto de entrada del generador.
     *
     * @param args argumentos no utilizados.
     */
    public static void main(String[] args) {

        File carpeta = new File(CARPETA_ENTRADA);

        try {
            if (!carpeta.exists() && !carpeta.mkdirs()) {
                throw new IOException("No fue posible crear "
                        + CARPETA_ENTRADA);
            }

            createProductsFile(20);
            createSalesManInfoFile(10);

            for (int i = 1; i <= 10; i++) {
                long id = 10000000L + i;
                String nombre = NOMBRES[(i - 1) % NOMBRES.length];

                // Se generan dos archivos por vendedor para demostrar
                // el procesamiento de múltiples archivos por vendedor.
                createSalesMenFile(8 + RANDOM.nextInt(8), nombre, id, 1);
                createSalesMenFile(8 + RANDOM.nextInt(8), nombre, id, 2);
            }

            System.out.println("ARCHIVOS DE PRUEBA GENERADOS CORRECTAMENTE.");
            System.out.println("Ubicación: " + carpeta.getAbsolutePath());

        } catch (IOException e) {
            System.err.println("ERROR AL GENERAR LOS ARCHIVOS:");
            System.err.println(e.getMessage());
        }
    }

    /**
     * Método requerido por el enunciado.
     * Genera el primer archivo de ventas de un vendedor.
     *
     * @param randomSalesCount cantidad de líneas de venta.
     * @param name nombre del vendedor.
     * @param id documento del vendedor.
     * @throws IOException si ocurre un error de escritura.
     */
    public static void createSalesMenFile(int randomSalesCount,
                                          String name, long id) throws IOException {
        createSalesMenFile(randomSalesCount, name, id, 1);
    }

    /**
     * Sobrecarga utilizada para crear archivos adicionales del mismo vendedor.
     *
     * @param randomSalesCount cantidad de líneas de venta.
     * @param name nombre del vendedor.
     * @param id documento del vendedor.
     * @param numeroArchivo consecutivo del archivo.
     * @throws IOException si ocurre un error de escritura.
     */
    private static void createSalesMenFile(int randomSalesCount,
                                           String name, long id, int numeroArchivo) throws IOException {

        String nombreArchivo = "ventas_" + id + "_"
                + String.format("%02d", numeroArchivo) + ".txt";

        File archivo = new File(CARPETA_ENTRADA, nombreArchivo);
        BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));

        try {
            writer.write("CC;" + id);
            writer.newLine();

            for (int i = 0; i < randomSalesCount; i++) {
                long idProducto = 1 + RANDOM.nextInt(20);
                int cantidad = 1 + RANDOM.nextInt(10);

                writer.write(idProducto + ";" + cantidad);
                writer.newLine();
            }
        } finally {
            writer.close();
        }
    }

    /**
     * Método requerido por el enunciado.
     * Genera el archivo de productos.
     *
     * @param productsCount cantidad de productos.
     * @throws IOException si ocurre un error de escritura.
     */
    public static void createProductsFile(int productsCount)
            throws IOException {

        File archivo = new File(CARPETA_ENTRADA, "productos.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));

        try {
            for (int i = 1; i <= productsCount; i++) {
                int precio = 5000 + RANDOM.nextInt(95001);

                writer.write(i + ";Producto " + i + ";" + precio);
                writer.newLine();
            }
        } finally {
            writer.close();
        }
    }

    /**
     * Método requerido por el enunciado.
     * Genera la información de los vendedores.
     *
     * @param salesmanCount cantidad de vendedores.
     * @throws IOException si ocurre un error de escritura.
     */
    public static void createSalesManInfoFile(int salesmanCount)
            throws IOException {

        File archivo = new File(CARPETA_ENTRADA, "vendedores.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));

        try {
            for (int i = 1; i <= salesmanCount; i++) {
                long id = 10000000L + i;
                String nombre = NOMBRES[(i - 1) % NOMBRES.length];
                String apellido = APELLIDOS[(i - 1) % APELLIDOS.length];

                writer.write("CC;" + id + ";" + nombre + ";" + apellido);
                writer.newLine();
            }
        } finally {
            writer.close();
        }
    }
}
