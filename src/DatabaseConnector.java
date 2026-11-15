import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnector {
    private String url = "jdbc:mysql://localhost:3306/equipaya";
    private String usuario = "root";
    private String contraseña = "";
    private Connection connection = null;

    public void abrirConexion() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, usuario, contraseña);
                System.out.println("La conexión ha sido exitosa");
            } catch (SQLException error) {
                System.out.println("Error en la base de datos: " + error.getMessage());
            }
        }
    }

    public int marcarEquipoFuera(String codigo) {
        int filasAfectadas = 0;
        try {
            String query = "UPDATE equipo SET disponible = FALSE WHERE codigo = ? AND disponible = TRUE";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, codigo);
            filasAfectadas = ps.executeUpdate();
            ps.close();
        } catch (SQLException error) {
            return -1;
        }
        return filasAfectadas;
    }

    // Alquiler
    public int insertarAlquiler(AlquilerModel alquilerModel) {
        int filasAfectadas = 0;
        try {
            String sql = "INSERT INTO alquiler (documento_cliente, codigo_equipo, dias, monto) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, alquilerModel.getDocCliente());
            ps.setString(2, alquilerModel.getCodEquipo());
            ps.setInt(3, alquilerModel.getDias());
            ps.setDouble(4, alquilerModel.getMonto());

            filasAfectadas = ps.executeUpdate();
            ps.close();
        } catch (SQLException error){
            return -1;
        }
        return filasAfectadas;
    }

    public int marcarAlquilerDevuelto(int id) {
        int filasAfectadas = 0;
        try {
            String sql = "UPDATE alquiler SET devuelto = TRUE WHERE id_alquiler = ? AND devuelto = FALSE";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            filasAfectadas = ps.executeUpdate();
            ps.close();

        } catch (SQLException error) {
            return -1;
        }
        return filasAfectadas;
    }

    public int liberarEquipo(String codigo) {
        int filasAfectadas = 0;
        try {
            String sql = "UPDATE equipo SET disponible = TRUE WHERE codigo = ? AND disponible = FALSE";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, codigo);
            filasAfectadas = ps.executeUpdate();
            ps.close();

        } catch (SQLException error) {
            return -1;
        }
        return filasAfectadas;
    }

    public ArrayList<AlquilerModel> listarAlquileres() {
        ArrayList<AlquilerModel> lista = new ArrayList<>();

        try {
            String sql = "SELECT id_alquiler, documento_cliente, codigo_equipo, fecha_inicio, dias, monto, devuelto FROM alquiler";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                AlquilerModel alquilerModel = new AlquilerModel(
                        rs.getInt("id_alquiler"),
                        rs.getString("documento_cliente"),
                        rs.getString("codigo_equipo"),
                        rs.getString("fecha_inicio"),
                        rs.getInt("dias"),
                        rs.getDouble("monto"),
                        rs.getBoolean("devuelto"));
                lista.add(alquilerModel);
            }
            rs.close();
            st.close();


        } catch (SQLException error) {
            System.out.println(error.getMessage());
            }
        return lista;
        }

    // Clientes
    public int insertarCliente(ClientesModel cliente) {
        int filasAfectadas = 0;
        try {
            String sql = "INSERT INTO cliente (documento, nombre, telefono) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, cliente.getDocumento());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getTelefono());
            filasAfectadas = ps.executeUpdate();
            ps.close();
        } catch (SQLException error) {
            return -1;
        }
        return filasAfectadas;
    }

    public int actualizarCliente(ClientesModel cliente) {
        int filasAfectadas = 0;
        try {
            String sql = "UPDATE cliente SET nombre = ?, telefono = ? WHERE documento = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getTelefono());
            ps.setString(3, cliente.getDocumento());
            filasAfectadas = ps.executeUpdate();
            ps.close();
        } catch (SQLException error) {
            return -1;
        }
        return filasAfectadas;
    }

    public ArrayList<ClientesModel> listarClientes() {
        ArrayList<ClientesModel> lista = new ArrayList<>();
        try {
            String sql = "SELECT documento, nombre, telefono FROM cliente";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                ClientesModel cliente = new ClientesModel(
                        rs.getString("documento"),
                        rs.getString("nombre"),
                        rs.getString("telefono"));
                lista.add(cliente);
            }
            rs.close();
            st.close();
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
        return lista;
    }

    // Equipos
    public ArrayList<EquipoModel> listarEquipos() {
        ArrayList<EquipoModel> lista = new ArrayList<>();
        try {
            String sql = "SELECT codigo, descripcion, tarifa_diaria, disponible, tipo FROM equipo";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String codigo = rs.getString("codigo");
                String descripcion = rs.getString("descripcion");
                double tarifa = rs.getDouble("tarifa_diaria");
                boolean disponible = rs.getBoolean("disponible");
                String tipo = rs.getString("tipo");

                // La columna tipo decide que clase de Java se crea
                EquipoModel equipo = null;
                if (tipo.equals("CAMARA")) {
                    equipo = new EquipoCamara(codigo, descripcion, tarifa, disponible);
                } else if (tipo.equals("ILUMINACION")) {
                    equipo = new EquipoIluminacion(codigo, descripcion, tarifa, disponible);
                } else if (tipo.equals("AUDIO")) {
                    equipo = new EquipoAudio(codigo, descripcion, tarifa, disponible);
                }
                if (equipo != null) {
                    lista.add(equipo);
                }
            }
            rs.close();
            st.close();
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
        return lista;
    }


    public Connection getConnection() {
        return connection;
    }

    public void cerrarConexion() {
        if (connection != null){
            try {
                connection.close();
                connection = null;
            } catch (SQLException error) {
                System.out.println("Error en la base de datos: " + error.getMessage());
            }
        }
    }



}
