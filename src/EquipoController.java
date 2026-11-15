import java.util.ArrayList;

public class EquipoController {
    private DatabaseConnector db;

    public EquipoController(DatabaseConnector db) {
        this.db = db;
    }

    public ArrayList<EquipoModel> listar() {
        return db.listarEquipos();
    }

    public EquipoModel buscarPorCodigo(String codigo) {
        return db.buscarEquipoPorCodigo(codigo);
    }
}
