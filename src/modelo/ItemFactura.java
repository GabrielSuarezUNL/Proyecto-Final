package modelo;

/**
 * Modelo que representa un ítem dentro de una factura. Relaciona un producto
 * con su cantidad vendida y subtotal.
 */
public class ItemFactura {

    private Producto producto;  // Producto vendido
    private int cantidad;      // Cantidad comprada
    private double subtotal;   // Precio unitario * cantidad

    // Constructor
    public ItemFactura(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.subtotal = producto.getPrecio() * cantidad;
    }

    // Getters
    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    // No hay setters para evitar modificaciones inconsistentes
}
