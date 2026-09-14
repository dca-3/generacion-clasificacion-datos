import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Genera los archivos planos que sirven como entrada para el programa
 * principal del proyecto de procesamiento de ventas.
 *
 * <p>Esta clase corresponde a la Entrega 1 - Semana 3. Su responsabilidad
 * es generar de forma automatica y pseudoaleatoria los archivos de
 * productos, vendedores y ventas de cada vendedor.</p>
 *
 * <p>Los datos generados son coherentes entre si: los productos utilizados
 * en los archivos de ventas existen en productos.txt y los vendedores
 * utilizados en los archivos de ventas existen en vendedores.txt.</p>
 *
 * @author Camilo Molano
 * @version 1.0
 */
public class GenerateInfoFiles {

    /** Carpeta donde se almacenan los archivos de entrada generados. */
    private static final String DATA_FOLDER = "data";

    /** Nombre del archivo que contiene la informacion de productos. */
    private static final String PRODUCTS_FILE = "productos.txt";

    /** Nombre del archivo que contiene la informacion de vendedores. */
    private static final String SALESMEN_FILE = "vendedores.txt";

    /** Cantidad de productos a generar automaticamente. */
    private static final int PRODUCTS_COUNT = 20;

    /** Cantidad de vendedores a generar automaticamente. */
    private static final int SALESMEN_COUNT = 10;

    /** Minimo de ventas por vendedor. */
    private static final int MIN_SALES_PER_SELLER = 3;

    /** Maximo de ventas por vendedor. */
    private static final int MAX_SALES_PER_SELLER = 8;

    /** Generador de numeros pseudoaleatorios. */
    private static final Random RANDOM = new Random();

    /** Lista de identificadores de productos validos. */
    private static final List<Long> PRODUCT_IDS = new ArrayList<Long>();

    /** Lista de vendedores generados. */
    private static final List<SalesMan> SALESMEN = new ArrayList<SalesMan>();

    /** Tipos de documento validos para un vendedor. */
    private static final String[] DOCUMENT_TYPES = { "CC", "CE", "TI" };

    /**
     * Punto de entrada del programa.
     *
     * <p>No solicita informacion al usuario. Genera todos los archivos
     * necesarios para la siguiente etapa del proyecto.</p>
     *
     * @param args argumentos de linea de comandos.
     */
    public static void main(String[] args) {
        try {
            createDataFolder();
            createProductsFile(PRODUCTS_COUNT);
            createSalesManInfoFile(SALESMEN_COUNT);

            for (SalesMan salesMan : SALESMEN) {
                int randomSalesCount = MIN_SALES_PER_SELLER
                        + RANDOM.nextInt(MAX_SALES_PER_SELLER - MIN_SALES_PER_SELLER + 1);

                createSalesMenFile(
                        randomSalesCount,
                        salesMan.getName(),
                        salesMan.getId());
            }

            System.out.println("============================================");
            System.out.println("GENERACION FINALIZADA EXITOSAMENTE");
            System.out.println("Archivos creados en la carpeta: " + DATA_FOLDER);
            System.out.println("============================================");
        } catch (IOException exception) {
            System.err.println("ERROR: No fue posible generar los archivos.");
            System.err.println("Detalle: " + exception.getMessage());
        }
    }

    /**
     * Crea la carpeta de salida si no existe.
     *
     * @throws IOException si no es posible crear la carpeta.
     */
    private static void createDataFolder() throws IOException {
        File folder = new File(DATA_FOLDER);

        if (!folder.exists() && !folder.mkdirs()) {
            throw new IOException("No fue posible crear la carpeta " + DATA_FOLDER + ".");
        }
    }

    /**
     * Crea un archivo con informacion pseudoaleatoria de productos.
     *
     * <p>Formato de cada linea:</p>
     * <pre>IDProducto;NombreProducto;PrecioPorUnidadProducto</pre>
     *
     * @param productsCount cantidad de productos que se generaran.
     * @throws IOException si ocurre un error al escribir el archivo.
     * @throws IllegalArgumentException si productsCount es menor que uno.
     */
    public static void createProductsFile(int productsCount) throws IOException {
        if (productsCount < 1) {
            throw new IllegalArgumentException("La cantidad de productos debe ser mayor que cero.");
        }

        String[] productNames = {
            "Computador Portatil",
            "Mouse Inalambrico",
            "Teclado Mecanico",
            "Monitor 24 Pulgadas",
            "Disco SSD 1TB",
            "Memoria RAM 16GB",
            "Audifonos Bluetooth",
            "Webcam HD",
            "Impresora Multifuncional",
            "Tablet",
            "Smartphone",
            "Cable HDMI",
            "Adaptador USB",
            "Parlantes",
            "Router WiFi",
            "Memoria USB 64GB",
            "Cargador Universal",
            "Base Refrigerante",
            "Microfono",
            "Camara Web"
        };

        File file = new File(DATA_FOLDER + File.separator + PRODUCTS_FILE);
        PRODUCT_IDS.clear();

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        try {
            for (int i = 0; i < productsCount; i++) {
                long productId = 1001L + i;
                String productName = productNames[i % productNames.length];
                long unitPrice = (RANDOM.nextInt(96) + 5) * 10000L;

                PRODUCT_IDS.add(productId);

                writer.write(productId + ";" + productName + ";" + unitPrice);
                writer.newLine();
            }
        } finally {
            writer.close();
        }
    }

    /**
     * Crea un archivo con informacion pseudoaleatoria de vendedores.
     *
     * <p>Formato de cada linea:</p>
     * <pre>TipoDocumento;NumeroDocumento;Nombres;Apellidos</pre>
     *
     * @param salesmanCount cantidad de vendedores que se generaran.
     * @throws IOException si ocurre un error al escribir el archivo.
     * @throws IllegalArgumentException si salesmanCount es menor que uno.
     */
    public static void createSalesManInfoFile(int salesmanCount) throws IOException {
        if (salesmanCount < 1) {
            throw new IllegalArgumentException("La cantidad de vendedores debe ser mayor que cero.");
        }

        String[] firstNames = {
            "Carlos Andres", "Maria Fernanda", "Juan David", "Laura Sofia",
            "Daniel Alejandro", "Camila Andrea", "Sebastian", "Valentina",
            "Nicolas", "Santiago", "Mateo", "Juliana", "Andres Felipe", "Paula Andrea"
        };

        String[] lastNames = {
            "Rodriguez Gomez", "Lopez Martinez", "Perez Torres", "Gomez Ramirez",
            "Hernandez Castro", "Martinez Moreno", "Garcia Rojas", "Diaz Vargas",
            "Morales Sanchez", "Torres Fernandez", "Castro Leon", "Ramirez Silva",
            "Vargas Ortiz", "Moreno Ruiz"
        };

        File file = new File(DATA_FOLDER + File.separator + SALESMEN_FILE);
        SALESMEN.clear();

        Set<Long> usedDocuments = new HashSet<Long>();

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        try {
            for (int i = 0; i < salesmanCount; i++) {
                long id = generateUniqueDocument(usedDocuments);
                String documentType = DOCUMENT_TYPES[RANDOM.nextInt(DOCUMENT_TYPES.length)];
                String name = firstNames[RANDOM.nextInt(firstNames.length)];
                String lastName = lastNames[RANDOM.nextInt(lastNames.length)];

                SalesMan salesMan = new SalesMan(documentType, id, name, lastName);
                SALESMEN.add(salesMan);

                writer.write(
                        salesMan.getDocumentType() + ";"
                        + salesMan.getId() + ";"
                        + salesMan.getName() + ";"
                        + salesMan.getLastName());
                writer.newLine();
            }
        } finally {
            writer.close();
        }
    }

    /**
     * Crea el archivo plano de ventas correspondiente a un vendedor.
     *
     * <p>Formato:</p>
     * <pre>
     * TipoDocumento;NumeroDocumento
     * IDProducto;CantidadProductoVendido;
     * </pre>
     *
     * <p>Los identificadores de producto se seleccionan unicamente de los
     * productos generados por createProductsFile(), por lo que los archivos
     * son coherentes.</p>
     *
     * @param randomSalesCount cantidad de lineas de venta que se generaran.
     * @param name nombre del vendedor, usado para identificar el archivo.
     * @param id numero de documento del vendedor.
     * @throws IOException si ocurre un error al escribir el archivo.
     * @throws IllegalArgumentException si los argumentos no son validos.
     */
    public static void createSalesMenFile(int randomSalesCount, String name, long id)
            throws IOException {

        if (randomSalesCount < 1) {
            throw new IllegalArgumentException("La cantidad de ventas debe ser mayor que cero.");
        }

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del vendedor no puede estar vacio.");
        }

        if (id <= 0) {
            throw new IllegalArgumentException("El documento del vendedor debe ser positivo.");
        }

        if (PRODUCT_IDS.isEmpty()) {
            throw new IllegalStateException("Primero debe generarse el archivo de productos.");
        }

        String safeName = name.trim().replaceAll("\\s+", "_");
        File file = new File(
                DATA_FOLDER + File.separator + "vendedor_" + id + "_" + safeName + ".txt");

        String documentType = DOCUMENT_TYPES[RANDOM.nextInt(DOCUMENT_TYPES.length)];

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        try {
            writer.write(documentType + ";" + id);
            writer.newLine();

            Set<Long> usedProductIds = new HashSet<Long>();
            int numberOfSales = Math.min(randomSalesCount, PRODUCT_IDS.size());

            while (usedProductIds.size() < numberOfSales) {
                long productId = PRODUCT_IDS.get(RANDOM.nextInt(PRODUCT_IDS.size()));
                usedProductIds.add(productId);
            }

            for (Long productId : usedProductIds) {
                int quantity = RANDOM.nextInt(5) + 1;
                writer.write(productId + ";" + quantity + ";");
                writer.newLine();
            }
        } finally {
            writer.close();
        }
    }

    /**
     * Genera un numero de documento pseudoaleatorio de 7 digitos que no se
     * haya asignado previamente en la misma ejecucion.
     *
     * @param usedDocuments documentos ya asignados en esta ejecucion.
     * @return un numero de documento unico dentro de la ejecucion actual.
     */
    private static long generateUniqueDocument(Set<Long> usedDocuments) {
        long document;

        do {
            document = 1000000L + RANDOM.nextInt(9000000);
        } while (usedDocuments.contains(document));

        usedDocuments.add(document);
        return document;
    }

    /**
     * Representa los datos basicos de un vendedor.
     */
    private static class SalesMan {

        private final String documentType;
        private final long id;
        private final String name;
        private final String lastName;

        /**
         * Construye un vendedor.
         *
         * @param documentType tipo de documento.
         * @param id numero de documento.
         * @param name nombres.
         * @param lastName apellidos.
         */
        SalesMan(String documentType, long id, String name, String lastName) {
            this.documentType = documentType;
            this.id = id;
            this.name = name;
            this.lastName = lastName;
        }

        String getDocumentType() {
            return documentType;
        }

        long getId() {
            return id;
        }

        String getName() {
            return name;
        }

        String getLastName() {
            return lastName;
        }
    }
}
