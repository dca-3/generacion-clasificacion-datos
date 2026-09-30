package proyecto;

import java.io.File;
import java.io.IOException;

/**
 * Programa principal encargado de procesar los archivos de entrada
 * y generar los dos reportes solicitados.
 *
 * @author Proyecto académico
 * @version 1.0
 */
public class main {

    private static final String CARPETA_ENTRADA = "archivosEntrada";
    private static final String CARPETA_REPORTES = "reportes";

    /**
     * Punto de entrada del programa de procesamiento.
     *
     * @param args argumentos de línea de comandos no utilizados.
     */
    public static void main(String[] args) {

        File carpetaEntrada = new File(CARPETA_ENTRADA);
        File carpetaReportes = new File(CARPETA_REPORTES);

        try {
            if (!carpetaEntrada.exists()) {
                throw new IOException("No existe la carpeta "
                        + CARPETA_ENTRADA
                        + ". Ejecute primero GenerateInfoFiles.");
            }

            if (!carpetaReportes.exists() && !carpetaReportes.mkdirs()) {
                throw new IOException("No fue posible crear la carpeta de reportes.");
            }

            ProcesadorVentas procesador = new ProcesadorVentas();

            procesador.cargarVendedores(
                    new File(carpetaEntrada, "vendedores.txt"));

            procesador.cargarProductos(
                    new File(carpetaEntrada, "productos.txt"));

            procesador.procesarArchivosVentas(carpetaEntrada);

            procesador.generarReporteVendedores(
                    new File(carpetaReportes, "reporte_vendedores.csv"));

            procesador.generarReporteProductos(
                    new File(carpetaReportes, "reporte_productos.csv"));

            System.out.println("PROCESO FINALIZADO CORRECTAMENTE.");
            System.out.println("Reportes generados en: "
                    + carpetaReportes.getAbsolutePath());

        } catch (IOException e) {
            System.err.println("ERROR DURANTE EL PROCESAMIENTO:");
            System.err.println(e.getMessage());
        }
    }
}
