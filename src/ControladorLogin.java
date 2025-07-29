package controlador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import modelo.Usuario;

public class ControladorLogin {
    private static final String ARCHIVO_USUARIOS = "usuario.txt";

    public Usuario validarUsuario(String nombre, String clave) {
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_USUARIOS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 2 && datos[0].equals(nombre) && datos[1].equals(clave)) {
                    return new Usuario(datos[0], datos[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // Si no coincide
    }
}