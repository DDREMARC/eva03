public class EquipoCamara extends EquipoModel {
    private static final double SEGURO = 0.15;

    public EquipoCamara(String codigo, String descripcion, double tarifaDiaria, boolean disponible) {
        super(codigo, descripcion, tarifaDiaria, disponible, "CAMARA");
    }

    @Override
    public double calcularMonto(int dias) {
        double subtotal = getTarifaDiaria() * dias;
        return subtotal + subtotal * SEGURO;
    }
}
