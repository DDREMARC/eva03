public class AlquilerModel {
    private int idAlquiler;
    private String docCliente;
    private String codEquipo;
    private String fechaInicio;
    private int dias;
    private double monto;
    private boolean devuelto;

    public AlquilerModel(int idAlquiler, String docCliente, String codEquipo, String fechaInicio, int dias, double monto, boolean devuelto) {
        this.idAlquiler = idAlquiler;
        this.docCliente = docCliente;
        this.codEquipo = codEquipo;
        this.fechaInicio = fechaInicio;
        this.dias = dias;
        this.monto = monto;
        this.devuelto = devuelto;
    }

    public AlquilerModel(String docCliente, String codEquipo, int dias, double monto) {
        this(0, docCliente, codEquipo, null, dias, monto, false);
    }

    public int getIdAlquiler() {
        return idAlquiler;
    }

    public void setIdAlquiler(int idAlquiler) {
        this.idAlquiler = idAlquiler;
    }

    public String getDocCliente() {
        return docCliente;
    }

    public String getCodEquipo() {
        return codEquipo;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public int getDias() {
        return dias;
    }

    public double getMonto() {
        return monto;
    }

    public boolean isDevuelto() {
        return devuelto;
    }

    public void setDevuelto(boolean devuelto) {
        this.devuelto = devuelto;
    }

    public void mostrarInfo(){
        System.out.println("ID: "+idAlquiler);
        System.out.println("Doc Cliente: "+docCliente);
        System.out.println("Cod Equipo: "+codEquipo);
        System.out.println("Fecha inicio: "+fechaInicio);
        System.out.println("Dias: "+dias);
        System.out.println("Monto: "+monto);
        System.out.println("Devuelto: "+devuelto);
    }
}