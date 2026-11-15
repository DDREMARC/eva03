public class PendienteModel {
    private String nombreCliente;
    private String descripcionEquipo;
    private int dias;
    private double monto;

    public PendienteModel(String nombreCliente, String descripcionEquipo, int dias, double monto) {
        this.nombreCliente = nombreCliente;
        this.descripcionEquipo = descripcionEquipo;
        this.dias = dias;
        this.monto = monto;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getDescripcionEquipo() {
        return descripcionEquipo;
    }

    public int getDias() {
        return dias;
    }

    public double getMonto() {
        return monto;
    }

    public void mostrarInfo() {
        System.out.println("Cliente: " + nombreCliente);
        System.out.println("Equipo: " + descripcionEquipo);
        System.out.println("Dias: " + dias);
        System.out.println("Monto: " + monto);
    }
}
