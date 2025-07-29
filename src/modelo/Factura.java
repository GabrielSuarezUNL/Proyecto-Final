package modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Modelo que representa una factura en el sistema. Contiene información del
 * cliente, items, fechas y totales.
 */
public class Factura {

    private String numero;          // Número único de factura (Ej: FAC-20231115-0001)
    private Date fecha;             // Fecha de emisión
    private Cliente cliente;        // Cliente asociado
    private List<ItemFactura> items;// Lista de productos facturados
    private double subtotal;        // Suma de subtotales de items
    private double iva;             // IVA calculado (12%)
    private double total;           // Subtotal + IVA

    // Constructor
    public Factura(String numero, Cliente cliente) {
        this.numero = numero;
        this.fecha = new Date(); // Fecha actual
        this.cliente = cliente;
        this.items = new ArrayList<>();
        this.subtotal = 0;
        this.iva = 0;
        this.total = 0;
    }

    /**
     * Agrega un producto a la factura como un ítem.
     *
     * @param producto Producto a agregar.
     * @param cantidad Cantidad vendida.
     */
    public void agregarItem(Producto producto, int cantidad) {
        ItemFactura item = new ItemFactura(producto, cantidad);
        items.add(item);
        calcularTotales(); // Actualiza subtotal, IVA y total
    }

    /**
     * Calcula automáticamente los totales (subtotal, IVA y total).
     */
    private void calcularTotales() {
        subtotal = 0;
        for (ItemFactura item : items) {
            subtotal += item.getSubtotal();
        }
        iva = subtotal * 0.12; // IVA del 12%
        total = subtotal + iva;
    }

    // Getters (no hay setters para mantener integridad)
    public String getNumero() {
        return numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<ItemFactura> getItems() {
        return items;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getIva() {
        return iva;
    }

    public double getTotal() {
        return total;
    }
}
