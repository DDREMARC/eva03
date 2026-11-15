import java.util.ArrayList;

public class ClientesController {
    private DatabaseConnector db;

    public ClientesController(DatabaseConnector db) {
        this.db = db;
    }

    public ArrayList<ClientesModel> listar() {
        return db.listarClientes();
    }

    // Devuelve 1 = registrado, -1 = error de base de datos (ej. documento repetido),
    // -2 = datos invalidos
    public int registrar(String documento, String nombre, String telefono) {
        if (!documentoValido(documento) || nombre.isEmpty()) {
            return -2;
        }
        return db.insertarCliente(new ClientesModel(documento, nombre, telefono));
    }

    // Devuelve 1 = actualizado, 0 = no existe ese documento, -1 = error, -2 = datos invalidos
    public int actualizar(String documento, String nombre, String telefono) {
        if (!documentoValido(documento) || nombre.isEmpty()) {
            return -2;
        }
        return db.actualizarCliente(new ClientesModel(documento, nombre, telefono));
    }

    // DNI de 8 digitos o RUC de 11, solo numeros
    private boolean documentoValido(String documento) {
        if (documento.length() != 8 && documento.length() != 11) {
            return false;
        }
        for (int i = 0; i < documento.length(); i++) {
            if (!Character.isDigit(documento.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
