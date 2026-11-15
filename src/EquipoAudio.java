public class EquipoAudio extends EquipoModel {

    public EquipoAudio(String codigo, String descripcion, double tarifaDiaria, boolean disponible) {
        super(codigo, descripcion, tarifaDiaria, disponible, "AUDIO");
    }

    @Override
    public double calcularMonto(int dias) {
        // POR HACER (Angel): tarifa x dias, cobrando como minimo 2 dias
        return 0;
    }
}
