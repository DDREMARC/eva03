import java.util.ArrayList;

public class AlquilerController {
    private DatabaseConnector db;

    public AlquilerController(DatabaseConnector db) {
        this.db = db;
    }

    public ArrayList<AlquilerModel> listar() {
        return db.listarAlquileres();
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

    public int devolver(int id) {
        String codigo = db.buscarCodigoEquipoPendiente(id);
        if (codigo == null) {
            return 0;
        }
        int resultado = db.marcarAlquilerDevuelto(id);
        if (resultado != 1) {
            return resultado;
        }
        if (db.liberarEquipo(codigo) == -1) {
            db.marcarAlquilerPendiente(id);
            return -1;
        }
        return 1;
    }

    public int revisarConsistencia() {
        ArrayList<EquipoModel> equipos = db.listarEquipos();
        ArrayList<AlquilerModel> alquileres = db.listarAlquileres();
        int corregidos = 0;

        for (int i = 0; i < equipos.size(); i++) {
            EquipoModel equipo = equipos.get(i);
            boolean tienePendiente = false;
            for (int j = 0; j < alquileres.size(); j++) {
                AlquilerModel alquiler = alquileres.get(j);
                if (!alquiler.isDevuelto() && alquiler.getCodEquipo().equals(equipo.getCodigo())) {
                    tienePendiente = true;
                }
            }

            if (tienePendiente && equipo.isDisponible()) {
                if (db.marcarEquipoFuera(equipo.getCodigo()) == 1) {
                    corregidos++;
                }
            } else if (!tienePendiente && !equipo.isDisponible()) {
                if (db.liberarEquipo(equipo.getCodigo()) == 1) {
                    corregidos++;
                }
            }
        }
        return corregidos;
    }

    public ArrayList<PendienteModel> listarPendientes() {
        return db.listarPendientes();
    }

    public ArrayList<FacturacionModel> listarFacturacion() {
        return db.listarFacturacion();
    }
}
