/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyectofinal;

import java.util.Locale;
import java.util.Scanner;

public class ProyectoFinal {
    // CONSTANTES
    static final int MAX_PRODUCTOS = 100;
    static final int MAX_CLIENTES = 100;
    static final int MAX_VENTAS = 100;
    
    // CATEGORÍAS DE PRODUCTOS DEPORTIVOS
    static final String[] CATEGORIAS = {
        "Fútbol", "Baloncesto", "Tenis", 
        "Natación", "Ciclismo", "Running", 
        "Gimnasio", "Otros"
    };

    // VARIABLES PARA PRODUCTOS
    static String[] codigosProductos = new String[MAX_PRODUCTOS];
    static String[] nombresProductos = new String[MAX_PRODUCTOS];
    static String[] categoriasProductos = new String[MAX_PRODUCTOS];
    static double[] preciosProductos = new double[MAX_PRODUCTOS];
    static int[] stocksProductos = new int[MAX_PRODUCTOS];
    static int totalProductos = 0;

    // VARIABLES PARA CLIENTES
    static String[] dniClientes = new String[MAX_CLIENTES];
    static String[] nombresClientes = new String[MAX_CLIENTES];
    static String[] apellidosClientes = new String[MAX_CLIENTES];
    static String[] telefonosClientes = new String[MAX_CLIENTES];
    static String[] direccionesClientes = new String[MAX_CLIENTES];
    static int totalClientes = 0;

    // VARIABLES PARA VENTAS
    static String[] ventaDNICliente = new String[MAX_VENTAS];
    static String[] ventaProductos = new String[MAX_VENTAS];
    static double[] ventaTotales = new double[MAX_VENTAS];
    static int totalVentas = 0;

    static Scanner entrada = new Scanner(System.in).useLocale(Locale.US);

    public static void main(String[] args) {
        boolean sesionIniciada = iniciarSesion();
        
        if (sesionIniciada) {
            mostrarMenuPrincipal();
        }
        
        entrada.close();
        System.out.println("\n¡Gracias por usar nuestro sistema!");
    }

    static boolean iniciarSesion() {
        System.out.println("================================");
        System.out.println("       INICIO DE SESIÓN        ");
        System.out.println("================================");
        
        int intentos = 0;
        final int MAX_INTENTOS = 3;
        
        while (intentos < MAX_INTENTOS) {
            System.out.print("Usuario: ");
            String usuario = entrada.nextLine();
            
            System.out.print("Contraseña: ");
            String clave = entrada.nextLine();
            
            if (usuario.equals("admin") && clave.equals("admin123")) {
                System.out.println("\n¡Bienvenido al sistema!");
                return true;
            } else {
                intentos++;
                System.out.println("\nCredenciales incorrectas. Intentos restantes: " + (MAX_INTENTOS - intentos));
            }
        }
        
        System.out.println("\nDemasiados intentos fallidos. Sistema bloqueado.");
        return false;
    }

    static void mostrarMenuPrincipal() {
        int opcion;
        
        do {
            System.out.println("\n================================");
            System.out.println("        MENÚ PRINCIPAL         ");
            System.out.println("================================");
            System.out.println("1. Gestión de Productos");
            System.out.println("2. Gestión de Clientes");
            System.out.println("3. Realizar Venta");
            System.out.println("4. Ver Reportes");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            
            opcion = entrada.nextInt();
            entrada.nextLine(); // Limpiar el buffer
            
            switch(opcion) {
                case 1:
                    gestionProductos();
                    break;
                case 2:
                    gestionClientes();
                    break;
                case 3:
                    realizarVenta();
                    break;
                case 4:
                    verReportes();
                    break;
                case 5:
                    System.out.println("\nSaliendo del sistema...");
                    break;
                default:
                    System.out.println("\nOpción no válida. Intente nuevamente.");
            }
        } while (opcion != 5);
    }

    static void gestionProductos() {
        int opcion;
        
        do {
            System.out.println("\n================================");
            System.out.println("      GESTIÓN DE PRODUCTOS     ");
            System.out.println("================================");
            System.out.println("1. Agregar Producto");
            System.out.println("2. Ver Todos los Productos");
            System.out.println("3. Ver Productos por Categoría");
            System.out.println("4. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            
            opcion = entrada.nextInt();
            entrada.nextLine(); // Limpiar el buffer
            
            switch(opcion) {
                case 1:
                    agregarProducto();
                    break;
                case 2:
                    mostrarProductos();
                    break;
                case 3:
                    mostrarProductosPorCategoria();
                    break;
                case 4:
                    System.out.println("\nVolviendo al menú principal...");
                    break;
                default:
                    System.out.println("\nOpción no válida. Intente nuevamente.");
            }
        } while (opcion != 4);
    }

    static void agregarProducto() {
        if (totalProductos >= MAX_PRODUCTOS) {
            System.out.println("\nNo se pueden agregar más productos. Límite alcanzado.");
            return;
        }
        
        System.out.println("\n================================");
        System.out.println("       NUEVO PRODUCTO          ");
        System.out.println("================================");
        
        System.out.print("Código del producto: ");
        codigosProductos[totalProductos] = entrada.nextLine();
        
        System.out.print("Nombre del producto: ");
        nombresProductos[totalProductos] = entrada.nextLine();
        
        System.out.println("\nCategorías disponibles:");
        for (int i = 0; i < CATEGORIAS.length; i++) {
            System.out.println((i+1) + ". " + CATEGORIAS[i]);
        }
        System.out.print("Seleccione categoría (1-" + CATEGORIAS.length + "): ");
        int opcionCategoria = entrada.nextInt();
        entrada.nextLine(); // Limpiar el buffer
        categoriasProductos[totalProductos] = CATEGORIAS[opcionCategoria-1];
        
        System.out.print("Precio: $");
        preciosProductos[totalProductos] = entrada.nextDouble();
        entrada.nextLine(); // Limpiar el buffer
        
        System.out.print("Cantidad en stock: ");
        stocksProductos[totalProductos] = entrada.nextInt();
        entrada.nextLine(); // Limpiar el buffer
        
        totalProductos++;
        System.out.println("\n¡Producto agregado con éxito!");
    }

    static void mostrarProductos() {
        if (totalProductos == 0) {
            System.out.println("\nNo hay productos registrados.");
            return;
        }
        
        System.out.println("\n======================================================================");
        System.out.println("                      LISTA DE PRODUCTOS                       ");
        System.out.println("======================================================================");
        System.out.printf("%-10s %-20s %-15s %-10s %-10s\n", "CÓDIGO", "NOMBRE", "CATEGORÍA", "PRECIO", "STOCK");
        System.out.println("----------------------------------------------------------------------");
        
        for (int i = 0; i < totalProductos; i++) {
            System.out.printf("%-10s %-20s %-15s $%-9.2f %-10d\n",
                codigosProductos[i],
                nombresProductos[i],
                categoriasProductos[i],
                preciosProductos[i],
                stocksProductos[i]);
        }
        
        System.out.println("======================================================================");
    }

    static void mostrarProductosPorCategoria() {
        if (totalProductos == 0) {
            System.out.println("\nNo hay productos registrados.");
            return;
        }
        
        System.out.println("\nCategorías disponibles:");
        for (int i = 0; i < CATEGORIAS.length; i++) {
            System.out.println((i+1) + ". " + CATEGORIAS[i]);
        }
        System.out.print("Seleccione categoría a mostrar (1-" + CATEGORIAS.length + "): ");
        int opcionCategoria = entrada.nextInt();
        entrada.nextLine(); // Limpiar el buffer
        String categoriaSeleccionada = CATEGORIAS[opcionCategoria-1];
        
        System.out.println("\n==================================================");
        System.out.println("    PRODUCTOS DE CATEGORÍA: " + categoriaSeleccionada.toUpperCase());
        System.out.println("==================================================");
        System.out.printf("%-10s %-20s %-10s %-10s\n", "CÓDIGO", "NOMBRE", "PRECIO", "STOCK");
        System.out.println("--------------------------------------------------");
        
        boolean hayProductos = false;
        for (int i = 0; i < totalProductos; i++) {
            if (categoriasProductos[i].equals(categoriaSeleccionada)) {
                System.out.printf("%-10s %-20s $%-9.2f %-10d\n",
                    codigosProductos[i],
                    nombresProductos[i],
                    preciosProductos[i],
                    stocksProductos[i]);
                hayProductos = true;
            }
        }
        
        if (!hayProductos) {
            System.out.println("No hay productos en esta categoría.");
        }
        
        System.out.println("==================================================");
    }

    static void gestionClientes() {
        int opcion;
        
        do {
            System.out.println("\n================================");
            System.out.println("      GESTIÓN DE CLIENTES      ");
            System.out.println("================================");
            System.out.println("1. Registrar Cliente");
            System.out.println("2. Ver Clientes");
            System.out.println("3. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            
            opcion = entrada.nextInt();
            entrada.nextLine(); // Limpiar el buffer
            
            switch(opcion) {
                case 1:
                    registrarCliente();
                    break;
                case 2:
                    mostrarClientes();
                    break;
                case 3:
                    System.out.println("\nVolviendo al menú principal...");
                    break;
                default:
                    System.out.println("\nOpción no válida. Intente nuevamente.");
            }
        } while (opcion != 3);
    }

    static void registrarCliente() {
        if (totalClientes >= MAX_CLIENTES) {
            System.out.println("\nNo se pueden registrar más clientes. Límite alcanzado.");
            return;
        }
        
        System.out.println("\n================================");
        System.out.println("      REGISTRAR CLIENTE       ");
        System.out.println("================================");
        
        System.out.print("DNI: ");
        dniClientes[totalClientes] = entrada.nextLine();
        
        System.out.print("Nombres: ");
        nombresClientes[totalClientes] = entrada.nextLine();
        
        System.out.print("Apellidos: ");
        apellidosClientes[totalClientes] = entrada.nextLine();
        
        System.out.print("Teléfono: ");
        telefonosClientes[totalClientes] = entrada.nextLine();
        
        System.out.print("Dirección: ");
        direccionesClientes[totalClientes] = entrada.nextLine();
        
        totalClientes++;
        System.out.println("\n¡Cliente registrado con éxito!");
    }

    static void mostrarClientes() {
        if (totalClientes == 0) {
            System.out.println("\nNo hay clientes registrados.");
            return;
        }
        
        System.out.println("\n=================================================================================");
        System.out.println("                            LISTA DE CLIENTES                            ");
        System.out.println("=================================================================================");
        System.out.printf("%-10s %-20s %-20s %-15s %-20s\n", "DNI", "NOMBRES", "APELLIDOS", "TELÉFONO", "DIRECCIÓN");
        System.out.println("---------------------------------------------------------------------------------");
        
        for (int i = 0; i < totalClientes; i++) {
            System.out.printf("%-10s %-20s %-20s %-15s %-20s\n",
                dniClientes[i],
                nombresClientes[i],
                apellidosClientes[i],
                telefonosClientes[i],
                direccionesClientes[i]);
        }
        
        System.out.println("=================================================================================");
    }

    static void realizarVenta() {
        if (totalProductos == 0 || totalClientes == 0) {
            System.out.println("\nNo hay suficientes datos para realizar una venta.");
            System.out.println("Necesita tener al menos 1 producto y 1 cliente registrado.");
            return;
        }
        
        System.out.println("\n================================");
        System.out.println("         NUEVA VENTA          ");
        System.out.println("================================");
        
        mostrarClientes();
        System.out.print("\nIngrese el DNI del cliente: ");
        String dniCliente = entrada.nextLine();
        
        int indiceCliente = buscarCliente(dniCliente);
        if (indiceCliente == -1) {
            System.out.println("\nCliente no encontrado. Regístrelo primero.");
            return;
        }
        
        StringBuilder productosVenta = new StringBuilder();
        double totalVenta = 0;
        boolean continuar = true;
        
        while (continuar) {
            mostrarProductos();
            
            System.out.print("\nIngrese el código del producto: ");
            String codigoProducto = entrada.nextLine();
            
            int indiceProducto = buscarProducto(codigoProducto);
            if (indiceProducto == -1) {
                System.out.println("\nProducto no encontrado.");
                continue;
            }
            
            System.out.print("Ingrese la cantidad a vender: ");
            int cantidad = entrada.nextInt();
            entrada.nextLine(); // Limpiar el buffer
            
            if (cantidad > stocksProductos[indiceProducto]) {
                System.out.println("\nNo hay suficiente stock. Stock disponible: " + stocksProductos[indiceProducto]);
                continue;
            }
            
            stocksProductos[indiceProducto] -= cantidad;
            double subtotal = preciosProductos[indiceProducto] * cantidad;
            totalVenta += subtotal;
            
            productosVenta.append(String.format("%s x%d ($%.2f) | ",
                nombresProductos[indiceProducto],
                cantidad,
                subtotal));
            
            System.out.print("\n¿Desea agregar otro producto? (s/n): ");
            String respuesta = entrada.nextLine();
            continuar = respuesta.equalsIgnoreCase("s");
        }
        
        ventaDNICliente[totalVentas] = dniCliente;
        ventaProductos[totalVentas] = productosVenta.toString();
        ventaTotales[totalVentas] = totalVenta;
        totalVentas++;
        
        System.out.println("\n================================");
        System.out.println("          FACTURA              ");
        System.out.println("================================");
        System.out.println("Cliente: " + nombresClientes[indiceCliente] + " " + apellidosClientes[indiceCliente]);
        System.out.println("DNI: " + dniCliente);
        System.out.println("\nPRODUCTOS:");
        System.out.println(productosVenta.toString());
        System.out.println("\nTOTAL A PAGAR: $" + String.format("%.2f", totalVenta));
        System.out.println("\n¡Venta registrada con éxito!");
    }

    static void verReportes() {
        System.out.println("\n================================");
        System.out.println("          REPORTES             ");
        System.out.println("================================");
        System.out.println("1. Ventas realizadas");
        System.out.println("2. Productos con bajo stock (menos de 5 unidades)");
        System.out.println("3. Volver al Menú Principal");
        System.out.print("Seleccione una opción: ");
        
        int opcion = entrada.nextInt();
        entrada.nextLine(); // Limpiar el buffer
        
        switch(opcion) {
            case 1:
                mostrarVentas();
                break;
            case 2:
                mostrarBajoStock();
                break;
            case 3:
                System.out.println("\nVolviendo al menú principal...");
                break;
            default:
                System.out.println("\nOpción no válida.");
        }
    }

    static void mostrarVentas() {
        if (totalVentas == 0) {
            System.out.println("\nNo hay ventas registradas.");
            return;
        }
        
        System.out.println("\n==================================================================================================");
        System.out.println("                                  HISTORIAL DE VENTAS                                   ");
        System.out.println("==================================================================================================");
        System.out.printf("%-20s %-50s %-10s\n", "CLIENTE", "PRODUCTOS", "TOTAL");
        System.out.println("--------------------------------------------------------------------------------------------------");
        
        for (int i = 0; i < totalVentas; i++) {
            int indiceCliente = buscarCliente(ventaDNICliente[i]);
            String nombreCliente = nombresClientes[indiceCliente] + " " + apellidosClientes[indiceCliente];
            
            String productos = ventaProductos[i].length() > 50 ?
                              ventaProductos[i].substring(0, 47) + "..." :
                              ventaProductos[i];
            
            System.out.printf("%-20s %-50s $%-10.2f\n",
                nombreCliente,
                productos,
                ventaTotales[i]);
        }
        
        System.out.println("==================================================================================================");
        
        double totalGeneral = 0;
        for (double total : ventaTotales) {
            totalGeneral += total;
        }
        System.out.printf("\nTOTAL GENERAL DE VENTAS: $%.2f\n", totalGeneral);
    }

    static void mostrarBajoStock() {
        System.out.println("\n================================================");
        System.out.println("       PRODUCTOS CON STOCK BAJO (<5 UNIDADES)  ");
        System.out.println("================================================");
        System.out.printf("%-10s %-20s %-10s\n", "CÓDIGO", "NOMBRE", "STOCK");
        System.out.println("------------------------------------------------");
        
        boolean hayBajoStock = false;
        for (int i = 0; i < totalProductos; i++) {
            if (stocksProductos[i] < 5) {
                System.out.printf("%-10s %-20s %-10d\n",
                    codigosProductos[i],
                    nombresProductos[i],
                    stocksProductos[i]);
                hayBajoStock = true;
            }
        }
        
        if (!hayBajoStock) {
            System.out.println("No hay productos con stock bajo.");
        }
        
        System.out.println("================================================");
    }

    static int buscarCliente(String dni) {
        for (int i = 0; i < totalClientes; i++) {
            if (dniClientes[i].equals(dni)) {
                return i;
            }
        }
        return -1;
    }

    static int buscarProducto(String codigo) {
        for (int i = 0; i < totalProductos; i++) {
            if (codigosProductos[i].equals(codigo)) {
                return i;
            }
        }
        return -1;
    }
}