import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientesController {
    private DatabaseConnector db;
    // Req. 5: memoria de clientes ya consultados (clave = documento)
    private Map<String, ClientesModel> memoria = new HashMap<>();

    public ClientesController(DatabaseConnector db) {
        this.db = db;
    }

    // Req. 5: consulta un cliente por documento sin ir a la base cada vez
    public ClientesModel consultarPorDocumento(String documento) {
        if (!documentoValido(documento)) {
            return null;
        }
        if (memoria.containsKey(documento)) {
            return memoria.get(documento);
        }
        ClientesModel clientesModel = db.buscarClientePorDocumento(documento);
        if (clientesModel != null) {
            memoria.put(documento, clientesModel);
        }
        return clientesModel;
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
        int resultado = db.actualizarCliente(new ClientesModel(documento, nombre, telefono));
        if (resultado == 1) {
            memoria.remove(documento);
        }
        return resultado;
    }

    // Req. 3: busca clientes por parte del nombre
    public ArrayList<ClientesModel> buscarPorNombre(String texto) {
        if (texto.isEmpty()) {
            return new ArrayList<>();
        }
        for (int i = 0; i < texto.length(); i++) {
            if (texto.charAt(i) == '%' || texto.charAt(i) == '_') {
                return new ArrayList<>();
            }
        }
        return db.buscarClientes(texto);
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
