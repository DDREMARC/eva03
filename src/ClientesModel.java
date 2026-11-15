public class ClientesModel {
    private String documento;
    private String nombre;
    private String telefono;

    public ClientesModel(String documento, String nombre, String telefono) {
        this.documento = documento;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void mostrarInfo() {
        System.out.println("Documento: " + documento);
        System.out.println("Nombre: " + nombre);
        System.out.println("Telefono: " + telefono);
    }
}
