package proyecto;

import java.io.*;
import java.util.*;

/**
 * Procesa los archivos de vendedores, productos y ventas.
 * Genera los reportes solicitados por el ejercicio.
 *
 * @author Proyecto académico
 * @version 1.0
 */
public class ProcesadorVentas {

    private final Map<String, Vendedor> vendedores;
    private final Map<Long, Producto> productos;
    private final Map<Long, Integer> cantidadesVendidas;

    /**
     * Inicializa las estructuras de procesamiento.
     */
    public ProcesadorVentas() {
        vendedores = new HashMap<String, Vendedor>();
        productos = new HashMap<Long, Producto>();
        cantidadesVendidas = new HashMap<Long, Integer>();
    }

    /**
     * Carga la información de vendedores.
     *
     * @param archivo archivo de vendedores.
     * @throws IOException si ocurre un error de lectura.
     */
    public void cargarVendedores(File archivo) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(archivo));

        try {
            String linea;
            int numeroLinea = 0;

            while ((linea = reader.readLine()) != null) {
                numeroLinea++;

                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] datos = linea.split(";", -1);

                if (datos.length != 4) {
                    throw new IOException("Formato inválido en vendedores.txt, línea "
                            + numeroLinea + ".");
                }

                String tipo = datos[0].trim();
                long documento = parseLong(datos[1].trim(),
                        "documento de vendedor, línea " + numeroLinea);
                String nombres = datos[2].trim();
                String apellidos = datos[3].trim();

                if (tipo.isEmpty() || nombres.isEmpty() || apellidos.isEmpty()) {
                    throw new IOException("Datos incompletos en vendedores.txt, línea "
                            + numeroLinea + ".");
                }

                String clave = generarClaveVendedor(tipo, documento);

                if (vendedores.containsKey(clave)) {
                    throw new IOException("Vendedor duplicado: " + clave);
                }

                vendedores.put(clave,
                        new Vendedor(tipo, documento, nombres, apellidos));
            }
        } finally {
            reader.close();
        }
    }

    /**
     * Carga la información de productos.
     *
     * @param archivo archivo de productos.
     * @throws IOException si ocurre un error de lectura.
     */
    public void cargarProductos(File archivo) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(archivo));

        try {
            String linea;
            int numeroLinea = 0;

            while ((linea = reader.readLine()) != null) {
                numeroLinea++;

                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] datos = linea.split(";", -1);

                if (datos.length != 3) {
                    throw new IOException("Formato inválido en productos.txt, línea "
                            + numeroLinea + ".");
                }

                long id = parseLong(datos[0].trim(),
                        "ID de producto, línea " + numeroLinea);
                String nombre = datos[1].trim();
                double precio = parseDouble(datos[2].trim(),
                        "precio del producto, línea " + numeroLinea);

                if (id <= 0) {
                    throw new IOException("El ID del producto debe ser positivo, línea "
                            + numeroLinea + ".");
                }

                if (nombre.isEmpty()) {
                    throw new IOException("El nombre del producto está vacío, línea "
                            + numeroLinea + ".");
                }

                if (precio < 0) {
                    throw new IOException("Precio negativo en productos.txt, línea "
                            + numeroLinea + ".");
                }

                if (productos.containsKey(id)) {
                    throw new IOException("Producto duplicado: " + id);
                }

                productos.put(id, new Producto(id, nombre, precio));
            }
        } finally {
            reader.close();
        }
    }

    /**
     * Procesa todos los archivos de ventas de la carpeta indicada.
     * Esto permite tener más de un archivo para el mismo vendedor.
     *
     * @param carpeta carpeta con los archivos de ventas.
     * @throws IOException si existe un archivo inválido.
     */
    public void procesarArchivosVentas(File carpeta) throws IOException {
        File[] archivos = carpeta.listFiles();

        if (archivos == null) {
            throw new IOException("No fue posible leer la carpeta de ventas.");
        }

        List<File> archivosVentas = new ArrayList<File>();

        for (File archivo : archivos) {
            if (archivo.isFile()
                    && archivo.getName().toLowerCase(Locale.US).startsWith("ventas_")
                    && archivo.getName().toLowerCase(Locale.US).endsWith(".txt")) {
                archivosVentas.add(archivo);
            }
        }

        Collections.sort(archivosVentas, new Comparator<File>() {
            @Override
            public int compare(File a, File b) {
                return a.getName().compareToIgnoreCase(b.getName());
            }
        });

        if (archivosVentas.isEmpty()) {
            throw new IOException("No se encontraron archivos de ventas.");
        }

        for (File archivo : archivosVentas) {
            procesarArchivoVenta(archivo);
        }
    }

    private void procesarArchivoVenta(File archivo) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(archivo));

        try {
            String encabezado = reader.readLine();

            if (encabezado == null) {
                throw new IOException("Archivo vacío: " + archivo.getName());
            }

            String[] datosEncabezado = encabezado.split(";", -1);

            if (datosEncabezado.length != 2) {
                throw new IOException("Encabezado inválido en " + archivo.getName());
            }

            String tipo = datosEncabezado[0].trim();
            long documento = parseLong(datosEncabezado[1].trim(),
                    "documento en " + archivo.getName());

            String clave = generarClaveVendedor(tipo, documento);

            if (!vendedores.containsKey(clave)) {
                throw new IOException("El vendedor del archivo "
                        + archivo.getName() + " no existe en vendedores.txt.");
            }

            String linea;
            int numeroLinea = 1;

            while ((linea = reader.readLine()) != null) {
                numeroLinea++;

                if (linea.trim().isEmpty()) {
                    continue;
                }

                procesarLineaVenta(linea, numeroLinea, archivo.getName(), clave);
            }
        } finally {
            reader.close();
        }
    }

    private void procesarLineaVenta(String linea, int numeroLinea,
            String nombreArchivo, String claveVendedor) throws IOException {

        String[] datos = linea.split(";", -1);

        if (datos.length != 2) {
            throw new IOException("Formato inválido en " + nombreArchivo
                    + ", línea " + numeroLinea + ".");
        }

        long idProducto = parseLong(datos[0].trim(),
                "ID de producto en " + nombreArchivo + ", línea " + numeroLinea);
        int cantidad = parseInt(datos[1].trim(),
                "cantidad en " + nombreArchivo + ", línea " + numeroLinea);

        if (idProducto <= 0) {
            throw new IOException("ID de producto inválido en " + nombreArchivo
                    + ", línea " + numeroLinea + ".");
        }

        if (cantidad <= 0) {
            throw new IOException("La cantidad debe ser mayor que cero en "
                    + nombreArchivo + ", línea " + numeroLinea + ".");
        }

        Producto producto = productos.get(idProducto);

        if (producto == null) {
            throw new IOException("Producto desconocido " + idProducto
                    + " en " + nombreArchivo + ", línea " + numeroLinea + ".");
        }

        Venta venta = new Venta(idProducto, cantidad);

        Vendedor vendedor = vendedores.get(claveVendedor);
        vendedor.agregarRecaudo(venta.getCantidad() * producto.getPrecio());

        Integer cantidadActual = cantidadesVendidas.get(idProducto);

        if (cantidadActual == null) {
            cantidadActual = 0;
        }

        cantidadesVendidas.put(idProducto,
                cantidadActual + venta.getCantidad());
    }

    /**
     * Genera el reporte de vendedores ordenado por recaudo descendente.
     *
     * @param archivo destino CSV.
     * @throws IOException si ocurre un error de escritura.
     */
    public void generarReporteVendedores(File archivo) throws IOException {
        List<Vendedor> lista = new ArrayList<Vendedor>(vendedores.values());

        Collections.sort(lista, new Comparator<Vendedor>() {
            @Override
            public int compare(Vendedor a, Vendedor b) {
                return Double.compare(b.getTotalRecaudado(),
                        a.getTotalRecaudado());
            }
        });

        BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));

        try {
            for (Vendedor vendedor : lista) {
                writer.write(String.format(Locale.US, "%.2f;%s",
                        vendedor.getTotalRecaudado(),
                        vendedor.getNombreCompleto()));
                writer.newLine();
            }
        } finally {
            writer.close();
        }
    }

    /**
     * Genera el reporte de productos ordenado por cantidad vendida.
     *
     * @param archivo destino CSV.
     * @throws IOException si ocurre un error de escritura.
     */
    public void generarReporteProductos(File archivo) throws IOException {
        List<Long> ids = new ArrayList<Long>(productos.keySet());

        Collections.sort(ids, new Comparator<Long>() {
            @Override
            public int compare(Long a, Long b) {
                int cantidadA = obtenerCantidadVendida(a);
                int cantidadB = obtenerCantidadVendida(b);

                int comparacion = Integer.compare(cantidadB, cantidadA);

                if (comparacion != 0) {
                    return comparacion;
                }

                return Long.compare(a, b);
            }
        });

        BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));

        try {
            for (Long id : ids) {
                Producto producto = productos.get(id);
                writer.write(String.format(Locale.US, "%s;%.2f",
                        producto.getNombre(), producto.getPrecio()));
                writer.newLine();
            }
        } finally {
            writer.close();
        }
    }

    private int obtenerCantidadVendida(Long idProducto) {
        Integer cantidad = cantidadesVendidas.get(idProducto);
        return cantidad == null ? 0 : cantidad;
    }

    private String generarClaveVendedor(String tipo, long documento) {
        return tipo.toUpperCase(Locale.US) + "-" + documento;
    }

    private long parseLong(String valor, String descripcion) throws IOException {
        try {
            return Long.parseLong(valor);
        } catch (NumberFormatException e) {
            throw new IOException("Valor numérico inválido para "
                    + descripcion + ": " + valor, e);
        }
    }

    private int parseInt(String valor, String descripcion) throws IOException {
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            throw new IOException("Valor numérico inválido para "
                    + descripcion + ": " + valor, e);
        }
    }

    private double parseDouble(String valor, String descripcion) throws IOException {
        try {
            return Double.parseDouble(valor);
        } catch (NumberFormatException e) {
            throw new IOException("Valor decimal inválido para "
                    + descripcion + ": " + valor, e);
        }
    }
}
