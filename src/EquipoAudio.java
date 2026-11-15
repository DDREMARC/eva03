public class EquipoAudio extends EquipoModel {
    public EquipoAudio(String codigo, String descripcion, double tarifaDiaria, boolean disponible) {
        super(codigo, descripcion, tarifaDiaria, disponible, "AUDIO");
    }

    @Override
    public double calcularMonto(int dias) {
        int diasCobrados = dias;
        if (dias < 2) {
            diasCobrados = 2;
        }
        return getTarifaDiaria() * diasCobrados;
    }
}
