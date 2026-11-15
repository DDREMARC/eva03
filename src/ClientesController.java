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

    // Req. 5: devuelve el cliente o null si no existe o el documento es invalido
    public ClientesModel buscarPorDocumento(String documento) {
        if (!documentoValido(documento.trim())) {
            return null;
        }
        return db.buscarClientePorDocumento(documento.trim());
    }

    // Req. 3: busca por parte del nombre.
    // PreparedStatement evita la inyeccion, pero NO neutraliza los comodines de LIKE:
    // un "%" devolveria a todos. Por eso se rechaza el texto vacio y se escapan
    // "!", "%" y "_" con "!" (declarado con ESCAPE '!' en la consulta).
    // Devuelve null si el texto es invalido; lista vacia si no hay coincidencias.
    public ArrayList<ClientesModel> buscarPorNombre(String texto) {
        if (texto.trim().isEmpty()) {
            return null;
        }
        String limpio = texto.trim();
        limpio = limpio.replace("!", "!!");
        limpio = limpio.replace("%", "!%");
        limpio = limpio.replace("_", "!_");
        return db.buscarClientesPorNombre("%" + limpio + "%");
    }

    // Devuelve 1 = eliminado, 0 = no existe, -1 = error de base de datos,
    // -2 = documento invalido, -3 = tiene alquileres pendientes (no se borra)
    public int eliminar(String documento) {
        if (!documentoValido(documento.trim())) {
            return -2;
        }
        int pendientes = db.contarPendientesDeCliente(documento.trim());
        if (pendientes < 0) {
            return -1;
        }
        if (pendientes > 0) {
            return -3;
        }
        return db.eliminarCliente(documento.trim());
    }

    // Req. 4b: alquileres sin devolver, con nombres y descripciones
    public ArrayList<PendienteModel> listarPendientes() {
        return db.listarPendientes();
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
