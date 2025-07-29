 package controlador;

import modelo.Cliente;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Controlador que gestiona las operaciones CRUD para clientes, incluyendo la
 * persistencia en archivo de texto.
 */
public class ControladorCliente {

    // Nombre del archivo donde se guardarán los datos
    private static final String ARCHIVO = "clientes.txt";
    // Lista en memoria para almacenar los clientes
    private List<Cliente> clientes;

    /**
     * Constructor que inicializa la lista y carga los datos del archivo.
     */
    public ControladorCliente() {
        clientes = new ArrayList<>();
        cargarClientes(); // Carga los datos al iniciar
    }

    /**
     * Carga los clientes desde el archivo de texto a la lista en memoria.
     */
    private void cargarClientes() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            // Leer línea por línea del archivo
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                // Validar que la línea tenga todos los campos
                if (datos.length == 5) {
                    // Crear cliente desde los datos del archivo
                    Cliente c = new Cliente(
                            datos[0], // dni
                            datos[1], // nombres
                            datos[2], // apellidos
                            datos[3], // telefono
                            datos[4] // direccion
                    );
                    clientes.add(c);
                }
            }
        } catch (IOException e) {
            // Si el archivo no existe, se creará al guardar el primer cliente
        }
    }

    /**
     * Guarda todos los clientes de la lista al archivo de texto.
     */
    private void guardarClientes() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO))) {
            // Escribir cada cliente como línea en el archivo
            for (Cliente c : clientes) {
                pw.println(c.toString());
            }
        } catch (IOException e) {
            mostrarError("Error al guardar clientes: " + e.getMessage());
        }
    }

    /**
     * Agrega un nuevo cliente al sistema.
     *
     * @param cliente El cliente a agregar
     */
    public void agregarCliente(Cliente cliente) {
        clientes.add(cliente);
        guardarClientes(); // Persiste los cambios
    }

    /**
     * Modifica un cliente existente en el sistema.
     *
     * @param indice Posición del cliente en la lista
     * @param cliente Cliente con los nuevos datos
     */
    public void modificarCliente(int indice, Cliente cliente) {
        if (indice >= 0 && indice < clientes.size()) {
            clientes.set(indice, cliente);
            guardarClientes(); // Persiste los cambios
        }
    }

    /**
     * Elimina un cliente del sistema.
     *
     * @param indice Posición del cliente a eliminar
     */
    public void eliminarCliente(int indice) {
        if (indice >= 0 && indice < clientes.size()) {
            clientes.remove(indice);
            guardarClientes(); // Persiste los cambios
        }
    }

    /**
     * Actualiza los datos mostrados en una tabla JTable.
     *
     * @param table La tabla a actualizar
     */
    public void actualizarTabla(javax.swing.JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Limpiar la tabla

        // Agregar cada cliente como fila en la tabla
        for (Cliente c : clientes) {
            model.addRow(new Object[]{
                c.getDni(),
                c.getNombres(),
                c.getApellidos(),
                c.getTelefono(),
                c.getDireccion()
            });
        }
    }

    /**
     * Busca un cliente por su DNI y devuelve su posición en la lista.
     *
     * @param dni DNI del cliente a buscar
     * @return Índice del cliente o -1 si no se encuentra
     */
    public int obtenerIndicePorDni(String dni) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getDni().equals(dni)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Busca un cliente por su DNI
     *
     * @param dni Documento de identidad
     * @return Cliente encontrado o null si no existe
     */
    public Cliente buscarClientePorDni(String dni) {
        for (Cliente cliente : clientes) {
            if (cliente.getDni().equals(dni)) {
                return cliente;
            }
        }
        return null;
    }

    /**
     * Obtiene la lista completa de clientes
     *
     * @return Lista de clientes (copia para proteger encapsulamiento)
     */
    public List<Cliente> getClientes() {
        return new ArrayList<>(clientes); // Retorna una copia de la lista
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
