/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyecto;

import vista.Login;

/**
 *
 * @author villa
 */
public class Proyecto {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        // Iniciar la aplicación mostrando el Login
        java.awt.EventQueue.invokeLater(() -> {
            Login ventanaLogin = new Login();
            ventanaLogin.setVisible(true);
            ventanaLogin.setLocationRelativeTo(null);
        }); // me sale error en esta linea 
    
    }
}
