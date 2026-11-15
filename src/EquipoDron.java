public class EquipoDron extends EquipoModel{
    public EquipoDron(String codigo, String descripcion, double tarifa, boolean disponible) {
        super(codigo, descripcion, tarifa, disponible, "DRON");
    }

    @Override
    public double calcularMonto(int dias) {
        return getTarifaDiaria() * dias;
    }
}
