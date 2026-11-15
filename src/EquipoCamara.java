public class EquipoCamara extends EquipoModel {

    public EquipoCamara(String codigo, String descripcion, double tarifaDiaria, boolean disponible) {
        super(codigo, descripcion, tarifaDiaria, disponible, "CAMARA");
    }

    @Override
    public double calcularMonto(int dias) {
        // POR HACER (Angel): tarifa x dias, mas seguro obligatorio del 15 %
        return 0;
    }
}
