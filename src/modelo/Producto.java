package modelo;

/**
 * Clase que representa un producto en el sistema. Contiene los atributos
 * básicos de un producto y métodos para acceder a ellos.
 */
public class Producto {

    // Atributos de la clase
    private String codigo;
    private String nombre;
    private String categoria;
    private double precio;
    private int stock;

    /**
     * Constructor de la clase Producto.
     *
     * @param codigo Identificador único del producto
     * @param nombre Nombre descriptivo del producto
     * @param categoria Categoría a la que pertenece el producto
     * @param precio Precio de venta del producto
     * @param stock Cantidad disponible en inventario
     */
    public Producto(String codigo, String nombre, String categoria, double precio, int stock) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }

    // Métodos de acceso (Getters y Setters)
    /**
     * @return El código del producto
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo Nuevo código para el producto
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return El nombre del producto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre Nuevo nombre para el producto
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return La categoría del producto
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * @param categoria Nueva categoría para el producto
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * @return El precio del producto
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * @param precio Nuevo precio para el producto
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * @return El stock disponible del producto
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock Nuevo valor de stock para el producto
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     *
     * @return Cadena con los datos del producto separados por comas
     */
    @Override
    public String toString() {
        return codigo + "," + nombre + "," + categoria + "," + precio + "," + stock;
    }
}
