import java.util.ArrayList;

public class AlquilerController {
    private DatabaseConnector db;

    public AlquilerController(DatabaseConnector db) {
        this.db = db;
    }

    public ArrayList<AlquilerModel> listar() {
        return db.listarAlquileres();
    }

    public int devolver(int id, String codigo) {
        int resultado = db.marcarAlquilerDevuelto(id);
        if (resultado != 1) {
            return resultado;
        }
        db.liberarEquipo(codigo);
        return 1;
    }

    public int alquilar(String doc, String codigo, int dias, double monto) {
        int resultado = db.marcarEquipoFuera(codigo);
        if (resultado != 1) {
            return resultado;
        }
        int guardado = db.insertarAlquiler(new AlquilerModel(doc, codigo, dias, monto));
        if (guardado != 1) {
            db.liberarEquipo(codigo);
            return -1;
        }
        return 1;
    }
}
