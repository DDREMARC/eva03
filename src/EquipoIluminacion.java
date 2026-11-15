public class EquipoIluminacion extends EquipoModel {
    private static final double DESCUENTO = 0.10 ;
    public EquipoIluminacion(String codigo, String descripcion, double tarifaDiaria, boolean disponible) {
        super(codigo, descripcion, tarifaDiaria, disponible, "ILUMINACION");
    }

    @Override
    public double calcularMonto(int dias) {
        double subtotal = getTarifaDiaria() * dias;
        if (dias > 5) {
            return subtotal - (subtotal * DESCUENTO);
        } else {
            return subtotal;
        }
    }
}
