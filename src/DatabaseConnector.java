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
