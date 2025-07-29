package controlador;

import modelo.Factura;
import modelo.Cliente;
import modelo.Producto;
import modelo.ItemFactura;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 * Controlador para la gestión de facturas: - Creación y manejo de facturas -
 * Generación automática de números de factura - Persistencia en archivo de
 * texto - Cálculo de totales (subtotal, IVA, total)
 */
public class ControladorFactura {

    // Archivo donde se guardarán las facturas
    private static final String ARCHIVO_FACTURAS = "facturas.txt";

    // Factura actual en proceso
    private Factura facturaActual;

    // Controladores necesarios para acceder a clientes y productos
    private final ControladorCliente ctrlCliente;
    private final ControladorProducto ctrlProducto;

    /**
     * Constructor que recibe los controladores necesarios
     *
     * @param ctrlCliente Controlador de clientes
     * @param ctrlProducto Controlador de productos
     */
    public ControladorFactura(ControladorCliente ctrlCliente, ControladorProducto ctrlProducto) {
        this.ctrlCliente = ctrlCliente;
        this.ctrlProducto = ctrlProducto;
    }

    /**
     * Crea una nueva factura para un cliente
     *
     * @param cliente El cliente asociado a la factura
     * @throws IllegalArgumentException Si el cliente es nulo
     */
    public void crearFactura(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }
        // Genera un número único para la factura
        String numeroFactura = generarNumeroFactura();
        // Crea la factura y la asigna como actual
        facturaActual = new Factura(numeroFactura, cliente);
    }

    /**
     * Genera un número de factura único con formato FAC-YYYYMMDD-XXXX
     *
     * @return Número de factura generado
     */
    private String generarNumeroFactura() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String fecha = sdf.format(new Date());
        // Obtiene el último consecutivo del día
        int consecutivo = obtenerUltimoConsecutivo(fecha);
        return "FAC-" + fecha + "-" + String.format("%04d", consecutivo);
    }

    /**
     * Obtiene el último consecutivo del día desde el archivo
     *
     * @param fecha Fecha en formato YYYYMMDD
     * @return El último número consecutivo usado + 1
     */
    private int obtenerUltimoConsecutivo(String fecha) {
        File archivo = new File(ARCHIVO_FACTURAS);
        // Si el archivo no existe, empieza desde 1
        if (!archivo.exists()) {
            return 1;
        }

        int max = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_FACTURAS))) {
            String linea;
            // Busca facturas del día actual
            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("FAC-" + fecha)) {
                    String[] partes = linea.split("-");
                    if (partes.length >= 3) {
                        try {
                            int num = Integer.parseInt(partes[2].trim());
                            max = Math.max(max, num);
                        } catch (NumberFormatException e) {
                            System.err.println("Formato de número inválido en factura: " + linea);
                        }
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al leer archivo de facturas: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return max + 1; // Retorna el siguiente consecutivo
    }

    /**
     * Agrega un ítem a la factura actual
     *
     * @param producto Producto a agregar
     * @param cantidad Cantidad del producto
     * @throws IllegalStateException Si no hay factura en proceso
     * @throws IllegalArgumentException Si el producto es nulo o cantidad
     * inválida
     */
    /**
     * Agrega un ítem a la factura actual y actualiza el stock del producto
     *
     * @param producto Producto a agregar
     * @param cantidad Cantidad del producto
     * @throws IllegalStateException Si no hay factura en proceso
     * @throws IllegalArgumentException Si el producto es nulo, cantidad
     * inválida o stock insuficiente
     */
    public void agregarItemAFactura(Producto producto, int cantidad) {
        // Validaciones básicas
        if (facturaActual == null) {
            throw new IllegalStateException("No hay factura en proceso");
        }
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser positiva");
        }
        if (cantidad > producto.getStock()) {
            throw new IllegalArgumentException("Stock insuficiente. Disponible: " + producto.getStock());
        }

        // Actualizar el stock del producto
        producto.setStock(producto.getStock() - cantidad);

        // Guardar el cambio en el archivo de productos
        ctrlProducto.guardarProductos();

        // Agregar el ítem a la factura
        facturaActual.agregarItem(producto, cantidad);
    }

    /**
     * Guarda la factura actual en el archivo con formato legible
     *
     * @throws IOException Si ocurre un error al escribir el archivo
     * @throws IllegalStateException Si no hay factura para guardar o no tiene
     * ítems
     */
    public void guardarFactura() throws IOException {
        // Validar que exista una factura con ítems
        if (facturaActual == null) {
            throw new IllegalStateException("No hay factura para guardar");
        }
        if (facturaActual.getItems().isEmpty()) {
            throw new IllegalStateException("La factura no tiene ítems");
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO_FACTURAS, true))) {
            // 1. ENCABEZADO DE LA FACTURA
            pw.println("FACTURA: " + facturaActual.getNumero());
            pw.println("Fecha: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
            pw.println("Cliente: " + facturaActual.getCliente().getNombres() + " "
                    + facturaActual.getCliente().getApellidos());
            pw.println("DNI/RUC: " + facturaActual.getCliente().getDni());
            pw.println("Teléfono: " + facturaActual.getCliente().getTelefono());
            pw.println("Dirección: " + facturaActual.getCliente().getDireccion());
            pw.println("----------------------------------------");

            // 2. DETALLE DE ÍTEMS (formato tabulado)
            pw.println("CÓDIGO\tDESCRIPCIÓN\tCANT.\tP. UNITARIO\tSUBTOTAL");
            for (ItemFactura item : facturaActual.getItems()) {
                pw.printf("%s\t%s\t%d\t%.2f\t%.2f%n",
                        item.getProducto().getCodigo(),
                        item.getProducto().getNombre(),
                        item.getCantidad(),
                        item.getProducto().getPrecio(),
                        item.getSubtotal());
            }

            // 3. TOTALES
            pw.println("----------------------------------------");
            pw.printf("SUBTOTAL: %.2f%n", facturaActual.getSubtotal());
            pw.printf("IVA (12%%): %.2f%n", facturaActual.getIva());
            pw.printf("TOTAL A PAGAR: %.2f%n", facturaActual.getTotal());
            pw.println("========================================");
            pw.println(); // Separador entre facturas
        }
    }

    // ================== MÉTODOS AUXILIARES ================== //
    /**
     * Busca un cliente por su DNI
     *
     * @param dni Documento de identidad del cliente
     * @return El cliente encontrado o null si no existe
     */
    public Cliente buscarClientePorDni(String dni) {
        return ctrlCliente.buscarClientePorDni(dni);
    }

    /**
     * Busca un producto por su código
     *
     * @param codigo Código del producto
     * @return El producto encontrado o null si no existe
     */
    public Producto buscarProductoPorCodigo(String codigo) {
        return ctrlProducto.buscarProductoPorCodigo(codigo);
    }

    /**
     * Obtiene la factura actual en proceso
     *
     * @return La factura actual o null si no hay ninguna
     */
    public Factura getFacturaActual() {
        return facturaActual;
    }
}
