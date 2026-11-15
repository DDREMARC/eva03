public class EquipoFabrica {

        public static EquipoModel crear(String tipo, String codigo, String descripcion,
                                        double tarifa, boolean disponible) {
            if (tipo.equals("CAMARA")) {
                return new EquipoCamara(codigo, descripcion, tarifa, disponible);
            } else if (tipo.equals("ILUMINACION")) {
                return new EquipoIluminacion(codigo, descripcion, tarifa, disponible);
            } else if (tipo.equals("AUDIO")) {
                return new EquipoAudio(codigo, descripcion, tarifa, disponible);
            } else if (tipo.equals("DRON")) {
                return new EquipoDron(codigo, descripcion, tarifa, disponible);
            }
            return null;
        }
}