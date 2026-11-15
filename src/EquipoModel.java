public abstract class EquipoModel implements Alquilable{
    private String codigo;
    private String descripcion;
    private double tarifaDiaria;
    private boolean disponible;
    private String tipo;

    public EquipoModel(String codigo, String descripcion, double tarifaDiaria, boolean disponible, String tipo) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.tarifaDiaria = tarifaDiaria;
        this.disponible = disponible;
        this.tipo = tipo;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getTarifaDiaria() {
        return tarifaDiaria;
    }

    public void setTarifaDiaria(double tarifaDiaria) {
        this.tarifaDiaria = tarifaDiaria;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getTipo() {
        return tipo;
    }

    public void mostrarInfo() {
        System.out.println("Codigo: " + codigo);
        System.out.println("Descripcion: " + descripcion);
        System.out.println("Tipo: " + tipo);
        System.out.println("Tarifa diaria: " + tarifaDiaria);
        System.out.println("Disponible: " + disponible);
    }
}