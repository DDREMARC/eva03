public class FacturacionModel {
    private String tipo;
    private double total;

    public FacturacionModel(String tipo, double total) {
        this.tipo = tipo;
        this.total = total;
    }

    public String getTipo() {
        return tipo;
    }

    public double getTotal() {
        return total;
    }

    public void mostrarInfoFacturacion() {
        System.out.println("Tipo: " + tipo);
        System.out.println("Total facturado: " + total);
    }
}
