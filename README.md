# ProyectoVentas - Entrega 1 Semana 3

## Descripcion

Proyecto Java 8 para Eclipse que implementa la clase `GenerateInfoFiles`.
La clase genera automaticamente archivos planos pseudoaleatorios que seran
utilizados como entrada por el programa principal del proyecto de procesamiento
de ventas.

## Entrega 1

Esta entrega se concentra en el diseño e implementacion de `GenerateInfoFiles`.
No se incluye todavia el procesamiento de ventas ni la generacion de reportes
finales, porque esas tareas corresponden a la siguiente etapa del proyecto.

## Archivos generados

Al ejecutar `GenerateInfoFiles`, se crean dentro de `data/`:

- `productos.txt`: ID, nombre y precio por unidad de cada producto.
- `vendedores.txt`: tipo de documento, numero de documento, nombres y apellidos.
- Un archivo `.txt` por vendedor con sus ventas.

## Requisitos

- Java 8
- Eclipse para Java Developers

## Ejecucion

1. Importar el proyecto en Eclipse.
2. Verificar que el JRE del proyecto sea Java 8.
3. Ejecutar `GenerateInfoFiles.java` como Java Application.
4. Revisar la carpeta `data` para comprobar los archivos generados.

El programa no solicita datos al usuario.

## Formatos

### Productos

`IDProducto;NombreProducto;PrecioPorUnidadProducto`

### Vendedores

`TipoDocumento;NumeroDocumento;NombresVendedor;ApellidosVendedor`

### Ventas

Primera linea:

`TipoDocumentoVendedor;NumeroDocumentoVendedor`

Lineas siguientes:

`IDProducto;CantidadProductoVendido`

## Buenas practicas aplicadas

- JavaDoc en la clase, atributos y metodos principales.
- Nombres descriptivos.
- Constantes para valores configurables.
- Manejo de excepciones de entrada/salida.
- Validacion de parametros.
- Uso de `try/finally` para garantizar el cierre de archivos.
- Datos coherentes entre productos, vendedores y archivos de ventas.
- Compatibilidad con Java 8.
