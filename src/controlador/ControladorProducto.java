package controlador;

import modelo.Producto;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Controlador que gestiona las operaciones CRUD para productos,
 * incluyendo la persistencia en archivo de texto.
 */
public class ControladorProducto {
    // Constante con el nombre del archivo de persistencia
    private static final String ARCHIVO = "productos.txt";
    
    // Lista que mantiene los productos en memoria
    private List<Producto> productos;

    /**
     * Constructor del controlador.
     * Inicializa la lista de productos y carga los datos del archivo.
     */
    public ControladorProducto() {
        productos = new ArrayList<>();
        cargarProductos();
    }

    /**
     * Carga los productos desde el archivo de texto a la lista en memoria.
     * Si el archivo no existe, se creará cuando se guarde el primer producto.
     */
    private void cargarProductos() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            // Leer línea por línea del archivo
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                // Validar que la línea tenga todos los campos necesarios
                if (datos.length == 5) {
                    // Crear producto desde los datos del archivo
                    Producto p = new Producto(
                        datos[0], // código
                        datos[1], // nombre
                        datos[2], // categoría
                        Double.parseDouble(datos[3]), // precio
                        Integer.parseInt(datos[4]) // stock
                    );
                    productos.add(p);
                }
            }
        } catch (IOException e) {
            // El archivo no existe aún, se ignorará el error
        }
    }

    /**
     * Guarda todos los productos de la lista al archivo de texto.
     * Sobrescribe el archivo completo con los datos actuales.
     */
    private void guardarProductos() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO))) {
            // Escribir cada producto como línea en el archivo
            for (Producto p : productos) {
                pw.println(p.toString());
            }
        } catch (IOException e) {
            mostrarError("Error al guardar los productos: " + e.getMessage());
        }
    }

    // Operaciones CRUD
    
    /**
     * Agrega un nuevo producto al sistema.
     * 
     * @param producto El producto a agregar
     */
    public void agregarProducto(Producto producto) {
        productos.add(producto);
        guardarProductos();
    }

    /**
     * Modifica un producto existente en el sistema.
     * 
     * @param indice Posición del producto en la lista
     * @param producto Producto con los nuevos datos
     */
    public void modificarProducto(int indice, Producto producto) {
        if (indice >= 0 && indice < productos.size()) {
            productos.set(indice, producto);
            guardarProductos();
        }
    }

    /**
     * Elimina un producto del sistema.
     * 
     * @param indice Posición del producto a eliminar
     */
    public void eliminarProducto(int indice) {
        if (indice >= 0 && indice < productos.size()) {
            productos.remove(indice);
            guardarProductos();
        }
    }

    /**
     * @return Lista de todos los productos (copia para evitar modificaciones externas)
     */
    public List<Producto> getProductos() {
        return new ArrayList<>(productos);
    }

    
    /**
     * Actualiza la tabla según el filtro de stock bajo.
     * @param table Tabla a actualizar
     * @param soloStockBajo True para mostrar solo productos con stock < 10
     */
    public void actualizarTabla(javax.swing.JTable table, boolean soloStockBajo) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Limpiar la tabla
        
        for (Producto p : productos) {
            // Mostrar todos o solo los con stock bajo según el filtro
            if (!soloStockBajo || p.getStock() < 10) {
                model.addRow(new Object[]{
                    p.getCodigo(),
                    p.getNombre(),
                    p.getCategoria(),
                    p.getPrecio(),
                    p.getStock()
                });
            }
        }
    }

    /**
     * Busca un producto por su código y devuelve su posición en la lista.
     * 
     * @param codigo Código del producto a buscar
     * @return Índice del producto o -1 si no se encuentra
     */
    public int obtenerIndicePorCodigo(String codigo) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo().equals(codigo)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Muestra un mensaje de error en un diálogo.
     * 
     * @param mensaje Texto del error a mostrar
     */
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}