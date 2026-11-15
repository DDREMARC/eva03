public class EquipoDron extends EquipoModel {
    public EquipoDron(String codigo, String descripcion, double tarifaDiaria, boolean disponible) {
        super(codigo, descripcion, tarifaDiaria, disponible, "DRON");
    }

    @Override
    public double calcularMonto(int dias) {
        return getTarifaDiaria() * dias;
    }
}
