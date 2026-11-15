import java.util.ArrayList;

public class EquipoController {
    private DatabaseConnector db;

    public EquipoController(DatabaseConnector db) {
        this.db = db;
    }

    public ArrayList<EquipoModel> listar() {
        return db.listarEquipos();
    }

    // Devuelve el equipo con ese codigo, o null si no existe
    public EquipoModel buscarPorCodigo(String codigo) {
        ArrayList<EquipoModel> lista = db.listarEquipos();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCodigo().equals(codigo)) {
                return lista.get(i);
            }
        }
        return null;
    }
}
