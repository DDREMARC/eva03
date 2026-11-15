public class EquipoIluminacion extends EquipoModel {

    public EquipoIluminacion(String codigo, String descripcion, double tarifaDiaria, boolean disponible) {
        super(codigo, descripcion, tarifaDiaria, disponible, "ILUMINACION");
    }

    @Override
    public double calcularMonto(int dias) {
        // POR HACER (Angel): tarifa x dias, 10 % de descuento si pasa de 5 dias
        return 0;
    }
}
