# EquipaYa: aviso de Angel para Leo y Dan

Fecha: 2026-11-14

Mi parte (alquileres, req. 1 y 2) ya compila y funciona con un menú temporal. Aquí va lo que necesitan saber para conectarse. Si algo no cuadra con lo que escribieron, avísenme antes de cambiar nada.

## 1. Qué ya está hecho

- `equipaya.sql`: las 3 tablas con datos de prueba.
- `AlquilerModel`: ficha del alquiler.
- `DatabaseConnector`: abrir y cerrar conexión, más 6 métodos de alquileres.
- `AlquilerController`: `alquilar`, `devolver` y `listar`.
- `TempMenu`: menú temporal mío para probar. Se borra cuando exista `MainView`. No lo usen.

## 2. Nombres que usé (confírmenlos o díganme qué cambiar)

| Qué | Nombre |
|---|---|
| Base de datos | `equipaya` |
| Tabla de clientes | `cliente` (PK `documento`, texto) |
| Tabla de equipos | `equipo` (PK `codigo`) |
| Tabla de alquileres | `alquiler` (PK `id_alquiler`) |
| Columna de disponibilidad | `disponible` (TRUE = libre) |
| Columna de devolución | `devuelto` (FALSE = sigue afuera) |
| Valores de `equipo.tipo` | `CAMARA`, `ILUMINACION`, `AUDIO` |
| Paquetes | por ahora todo está en `src/` sin paquetes |

Columnas de `alquiler`: `id_alquiler`, `documento_cliente`, `codigo_equipo`, `fecha_inicio`, `dias`, `monto`, `devuelto`.

## 3. Cómo se usa mi parte

- Hay **una sola** instancia de `DatabaseConnector`. Se crea una vez, se llama `abrirConexion()` y se pasa por constructor a los controladores. Nadie crea la suya.
- `AlquilerController` recibe el conector por constructor.
- Los métodos devuelven un número y la vista decide el mensaje:

| Resultado | Significa |
|---|---|
| `1` | salió bien |
| `0` | no cambió nada (equipo ya estaba fuera, o alquiler ya devuelto) |
| `-1` | falló la base de datos |

- Firmas:
  - `int alquilar(String doc, String codigo, int dias, double monto)`
  - `int devolver(int id, String codigo)`
  - `ArrayList<AlquilerModel> listar()`

## 4. Para Leo

1. **`EquipoCamara.java` línea 11 no compila.** `tarifaPorDia` está declarada dos veces (llega como parámetro y se vuelve a declarar adentro), y el método es `void`. Esto frena a todo el proyecto en IntelliJ.
2. **El monto lo necesito calculado.** `alquilar` recibe `monto` ya listo. Necesito un método tuyo que, dado un equipo y los días, devuelva el monto en `double` según las reglas de cobro. Dime cómo se llama y de dónde saco el equipo.
3. El seguro es 15 % del subtotal, no `+ 0.15`. Los `dias` son de cada alquiler, no del equipo. Usa `double` y no `float`.
4. `EquipoAudio` y `EquipoIluminacion` siguen vacíos y todavía no extienden `EquipoModel`.
5. Cuando tengas `MainView`, mi `TempMenu` se borra y tú unes el submenú de alquileres.

## 5. Para Dan

1. `ClientesModel` y `ClientesController` están vacíos. El documento va como texto (`String`), no `int`, porque un DNI puede empezar con cero y el RUC tiene 11 dígitos.
2. Sus consultas con SQL van **dentro de `DatabaseConnector`**, porque es la única clase que ejecuta SQL. Hagan `git pull` antes de tocarlo, agreguen sus métodos al final y avísenme, para no pisarnos en ese archivo.
3. Req. 3: en `LIKE`, `%` y `_` son comodines. Validen el texto en Java antes de armar el parámetro (vacío, `%`, `_`).
4. Req. 4b: pendientes = `devuelto = FALSE`. Hace falta un `JOIN` entre `alquiler`, `cliente` y `equipo` para traer el nombre y la descripción.
5. Req. 5: la PK `documento` ya tiene índice. Usen la misma conexión abierta, no una nueva por consulta.
6. Antes de eliminar un cliente con alquileres pendientes, avisar.

## 6. Cómo trabajar para no pisarnos

- `git pull` antes de empezar. Commits chicos. Cada quien toca solo sus archivos.
- No subir `.idea`, `out` ni `TempMenu`.
- Para probar solo mi parte, yo dejé vacíos de forma local `EquipoModel` y `EquipoCamara` (no los subo). Su versión en el repo no cambia.

## 7. Pendiente entre los tres

- Confirmar la tabla de nombres de arriba.
- Justificación escrita de a), b) y cada requerimiento.
- Una captura por requerimiento.
- Un solo archivo de texto con todo el código al final.
