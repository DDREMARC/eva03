// Una fila del reporte de pendientes (req. 4b): lleva el NOMBRE del cliente
// y la DESCRIPCION del equipo, no los codigos.
public class PendienteModel {
    private String nombreCliente;
    private String descripcionEquipo;
    private String fechaInicio;
    private int dias;
    private double monto;

    public PendienteModel(String nombreCliente, String descripcionEquipo,
                          String fechaInicio, int dias, double monto) {
        this.nombreCliente = nombreCliente;
        this.descripcionEquipo = descripcionEquipo;
        this.fechaInicio = fechaInicio;
        this.dias = dias;
        this.monto = monto;
    }

    public void mostrarInfo() {
        System.out.println("Cliente: " + nombreCliente);
        System.out.println("Equipo: " + descripcionEquipo);
        System.out.println("Desde: " + fechaInicio + " (" + dias + " dias)");
        System.out.println("Monto: " + monto);
    }
}
