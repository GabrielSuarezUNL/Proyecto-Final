package modelo;

/**
 * Clase que representa un cliente en el sistema. Contiene los datos personales
 * del cliente y métodos para acceder a ellos.
 */
public class Cliente {

    // Atributos de la clase
    private String dni;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String direccion;

    /**
     * Constructor para crear un nuevo cliente.
     */
    public Cliente(String dni, String nombres, String apellidos, String telefono, String direccion) {
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    // Métodos de acceso (Getters y Setters)
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Convierte el objeto Cliente a formato CSV para almacenamiento.
     *
     * @return Cadena con los datos separados por comas
     */
    @Override
    public String toString() {
        return dni + "," + nombres + "," + apellidos + "," + telefono + "," + direccion;
    }
}
