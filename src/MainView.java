import java.util.ArrayList;
import java.util.Scanner;

public class MainView {
    private Scanner sc;
    private DatabaseConnector db;
    private ClientesController clientesController;
    private EquipoController equipoController;
    private AlquilerController alquilerController;

    public MainView() {
        sc = new Scanner(System.in);
        db = new DatabaseConnector();
        clientesController = new ClientesController(db);
        equipoController = new EquipoController(db);
        alquilerController = new AlquilerController(db);
    }

    public void iniciar() {
        db.abrirConexion();

        int opcion = -1;
        while (opcion != 0) {
            System.out.println();
            System.out.println("========= EQUIPAYA =========");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Modificar cliente");
            System.out.println("4. Listar equipos");
            System.out.println("5. Alquilar equipo");
            System.out.println("6. Devolver equipo");
            System.out.println("7. Listar alquileres");
            System.out.println("8. Buscar cliente por nombre");
            System.out.println("9. Facturado por tipo de equipo");
            System.out.println("10. Alquileres pendientes de devolucion");
            System.out.println("11. Consultar cliente por documento");
            System.out.println("0. Salir");
            opcion = leerEntero("Opcion: ");

            switch (opcion) {
                case 1:
                    registrarCliente();
                    break;
                case 2:
                    listarClientes();
                    break;
                case 3:
                    modificarCliente();
                    break;
                case 4:
                    listarEquipos();
                    break;
                case 5:
                    alquilar();
                    break;
                case 6:
                    devolver();
                    break;
                case 7:
                    listarAlquileres();
                    break;
                case 8:
                    // POR HACER (Angel): req. 3
                    System.out.println("Por implementar.");
                    break;
                case 9:
                    // POR HACER (Angel): req. 4a
                    System.out.println("Por implementar.");
                    break;
                case 10:
                    // POR HACER (Angel): req. 4b
                    System.out.println("Por implementar.");
                    break;
                case 11:
                    // POR HACER (Angel): req. 5
                    System.out.println("Por implementar.");
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        }

        sc.close();
        db.cerrarConexion();
    }

    private void registrarCliente() {
        System.out.print("Documento (DNI 8 o RUC 11 digitos): ");
        String documento = sc.nextLine();
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Telefono: ");
        String telefono = sc.nextLine();

        int resultado = clientesController.registrar(documento, nombre, telefono);
        if (resultado == 1) {
            System.out.println("Cliente registrado.");
        } else if (resultado == -2) {
            System.out.println("Datos invalidos: revisa el documento y el nombre.");
        } else {
            System.out.println("No se pudo registrar (puede que ese documento ya exista).");
        }
    }

    private void listarClientes() {
        ArrayList<ClientesModel> lista = clientesController.listar();
        if (lista.isEmpty()) {
            System.out.println("No hay clientes.");
        }
        for (int i = 0; i < lista.size(); i++) {
            lista.get(i).mostrarInfo();
            System.out.println("-----");
        }
    }

    private void modificarCliente() {
        System.out.print("Documento del cliente a modificar: ");
        String documento = sc.nextLine();
        System.out.print("Nuevo nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Nuevo telefono: ");
        String telefono = sc.nextLine();

        int resultado = clientesController.actualizar(documento, nombre, telefono);
        if (resultado == 1) {
            System.out.println("Cliente actualizado.");
        } else if (resultado == 0) {
            System.out.println("No existe un cliente con ese documento.");
        } else if (resultado == -2) {
            System.out.println("Datos invalidos: revisa el documento y el nombre.");
        } else {
            System.out.println("Error en la base de datos.");
        }
    }

    private void listarEquipos() {
        ArrayList<EquipoModel> lista = equipoController.listar();
        if (lista.isEmpty()) {
            System.out.println("No hay equipos.");
        }
        for (int i = 0; i < lista.size(); i++) {
            lista.get(i).mostrarInfo();
            System.out.println("-----");
        }
    }

    private void alquilar() {
        System.out.print("Documento del cliente: ");
        String documento = sc.nextLine();
        System.out.print("Codigo del equipo: ");
        String codigo = sc.nextLine();
        int dias = leerEntero("Dias: ");
        if (dias < 1) {
            System.out.println("Los dias deben ser 1 o mas.");
            return;
        }

        EquipoModel equipo = equipoController.buscarPorCodigo(codigo);
        if (equipo == null) {
            System.out.println("No existe un equipo con ese codigo.");
            return;
        }
        double monto = equipo.calcularMonto(dias);

        int resultado = alquilerController.alquilar(documento, codigo, dias, monto);
        if (resultado == 1) {
            System.out.println("Alquiler registrado. Monto: " + monto);
        } else if (resultado == 0) {
            System.out.println("Ese equipo ya esta fuera.");
        } else {
            System.out.println("No se pudo registrar (revisa que el cliente exista).");
        }
    }

    private void devolver() {
        int id = leerEntero("ID del alquiler: ");
        System.out.print("Codigo del equipo: ");
        String codigo = sc.nextLine();

        int resultado = alquilerController.devolver(id, codigo);
        if (resultado == 1) {
            System.out.println("Devolucion registrada.");
        } else if (resultado == 0) {
            System.out.println("Ese alquiler ya estaba devuelto o no existe.");
        } else {
            System.out.println("Error en la base de datos.");
        }
    }

    private void listarAlquileres() {
        ArrayList<AlquilerModel> lista = alquilerController.listar();
        if (lista.isEmpty()) {
            System.out.println("No hay alquileres.");
        }
        for (int i = 0; i < lista.size(); i++) {
            lista.get(i).mostrarInfo();
            System.out.println("-----");
        }
    }

    // Pide un numero hasta que el usuario escriba uno valido
    private int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextInt()) {
            System.out.println("Escribe un numero.");
            sc.next();
            System.out.print(mensaje);
        }
        int numero = sc.nextInt();
        sc.nextLine();
        return numero;
    }
}
