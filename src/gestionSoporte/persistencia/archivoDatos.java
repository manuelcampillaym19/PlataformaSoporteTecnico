package gestionSoporte.persistencia;

import gestionSoporte.modelo.Seguimiento;
import gestionSoporte.modelo.Usuario;
import gestionSoporte.modelo.Solicitud;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class archivoDatos {

    private static final String ARCHIVO_USUARIOS = "usuarios.txt";
    private static final String ARCHIVO_SOLICITUDES = "solicitudes.txt";

    // =========================================================
    // USUARIOS
    // =========================================================

    public static void guardarUsuarios(
            HashMap<Integer, Usuario> usuarios) {

        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(ARCHIVO_USUARIOS),
                        StandardCharsets.UTF_8
                ))) {

            for (Usuario usuario : usuarios.values()) {

                String nombre =
                        limpiar(usuario.getNombre());

                String correo =
                        limpiar(usuario.getCorreo());

                bw.write(
                        usuario.getId()
                        + "," + nombre
                        + "," + correo
                );

                bw.newLine();
            }

        } catch (IOException e) {

            System.out.println(
                    "Error al guardar usuarios: "
                    + e.getMessage()
            );
        }
    }

    public static HashMap<Integer, Usuario> cargarUsuarios() {

        HashMap<Integer, Usuario> usuarios =
                new HashMap<>();

        File archivo =
                new File(ARCHIVO_USUARIOS);

        if (!archivo.exists()) {
            return usuarios;
        }

        try (BufferedReader br =
                new BufferedReader(
                        new InputStreamReader(
                        new FileInputStream(archivo),
                        StandardCharsets.UTF_8
                ))) {

            String linea;

            while ((linea = br.readLine()) != null) {

                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] datos =
                        linea.split(",");

                if (datos.length >= 3) {

                    int id =
                            Integer.parseInt(
                                    datos[0].trim()
                            );

                    String nombre =
                            datos[1].trim();

                    String correo =
                            datos[2].trim();

                    Usuario usuario =
                            new Usuario(
                                    nombre,
                                    correo
                            );

                    usuario.setId(id);

                    usuarios.put(
                            usuario.getId(),
                            usuario
                    );
                }
            }

        } catch (IOException
                | NumberFormatException e) {

            System.out.println(
                    "Error al cargar usuarios: "
                    + e.getMessage()
            );
        }

        return usuarios;
    }

    // =========================================================
    // SOLICITUDES
    // =========================================================

    public static void guardarSolicitudes(
            HashMap<Integer, Usuario> usuarios) {

        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(ARCHIVO_SOLICITUDES),
                        StandardCharsets.UTF_8
                ))) {

            for (Usuario usuario :
                    usuarios.values()) {

                for (Solicitud solicitud :
                        usuario.getSolicitudes()) {

                    /*
                     * Formato:
                     *
                     * ID,
                     * detalle,
                     * estado,
                     * tiempo,
                     * idUsuario,
                     * valoracion,
                     * momentoCreacion,
                     * momentoCierre,
                     * cantidadSeguimientos,
                     * descripcionSeguimiento,
                     * fechaSeguimiento,
                     * ...
                     */

                    StringBuilder linea =
                            new StringBuilder();

                    linea.append(
                            solicitud.getIdSolicitud()
                    );

                    linea.append(",");
                    linea.append(
                            limpiar(
                                    solicitud.getDetalle()
                            )
                    );

                    linea.append(",");
                    linea.append(
                            limpiar(
                                    solicitud.getEstado()
                            )
                    );

                    linea.append(",");
                    linea.append(
                            solicitud.getTiempoAtencion()
                    );

                    linea.append(",");
                    linea.append(
                            usuario.getId()
                    );

                    linea.append(",");
                    linea.append(
                            solicitud.getValoracion()
                    );

                    linea.append(",");
                    linea.append(
                            solicitud.getMomentoCreacion()
                    );

                    linea.append(",");
                    linea.append(
                            solicitud.getMomentoCierre()
                    );

                    // Cantidad de seguimientos
                    linea.append(",");
                    linea.append(
                            solicitud.getSeguimientos().size()
                    );

                    // Datos de cada seguimiento
                    for (Seguimiento seguimiento :
                            solicitud.getSeguimientos()) {

                        linea.append(",");
                        linea.append(
                                limpiar(
                                        seguimiento.getDescripcion()
                                )
                        );

                        linea.append(",");
                        linea.append(
                                limpiar(
                                        seguimiento.getFecha()
                                )
                        );
                    }

                    bw.write(linea.toString());
                    bw.newLine();
                }
            }

        } catch (IOException e) {

            System.out.println(
                    "Error al guardar solicitudes: "
                    + e.getMessage()
            );
        }
    }

    // =========================================================
    // CARGAR SOLICITUDES
    // =========================================================

    public static void cargarSolicitudes(
            HashMap<Integer, Usuario> usuarios) {

        File archivo =
                new File(ARCHIVO_SOLICITUDES);

        if (!archivo.exists()) {
            return;
        }

        try (BufferedReader br =
                new BufferedReader(
                        new InputStreamReader(
                        new FileInputStream(archivo),
                        StandardCharsets.UTF_8
                ))) {

            String linea;

            while ((linea = br.readLine()) != null) {

                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] datos =
                        linea.split(",");

                /*
                 * Los primeros 8 campos corresponden
                 * a la solicitud original.
                 */
                if (datos.length >= 8) {

                    int idSolicitud =
                            Integer.parseInt(
                                    datos[0].trim()
                            );

                    String detalle =
                            datos[1].trim();

                    String estado =
                            datos[2].trim();

                    int tiempoAtencion =
                            Integer.parseInt(
                                    datos[3].trim()
                            );

                    int idUsuario =
                            Integer.parseInt(
                                    datos[4].trim()
                            );

                    int valoracion =
                            Integer.parseInt(
                                    datos[5].trim()
                            );

                    long momentoCreacion =
                            Long.parseLong(
                                    datos[6].trim()
                            );

                    long momentoCierre =
                            Long.parseLong(
                                    datos[7].trim()
                            );

                    Usuario usuario =
                            usuarios.get(idUsuario);

                    if (usuario != null) {

                        Solicitud solicitud =
                                new Solicitud(
                                        detalle,
                                        usuario
                                );

                        solicitud.setIdSolicitud(
                                idSolicitud
                        );

                        solicitud.setEstado(
                                estado
                        );

                        solicitud.setTiempoAtencion(
                                tiempoAtencion
                        );

                        solicitud.setValoracion(
                                valoracion
                        );

                        solicitud.setMomentoCreacion(
                                momentoCreacion
                        );

                        solicitud.setMomentoCierre(
                                momentoCierre
                        );

                        /*
                         * Si el archivo fue generado
                         * con la nueva versión, el campo
                         * 8 indica la cantidad de seguimientos.
                         */
                        if (datos.length >= 9) {

                            int cantidadSeguimientos =
                                    Integer.parseInt(
                                            datos[8].trim()
                                    );

                            int posicion = 9;

                            for (int i = 0;
                                    i < cantidadSeguimientos;
                                    i++) {

                                if (posicion + 1
                                        < datos.length) {

                                    String descripcion =
                                            datos[posicion].trim();

                                    String fecha =
                                            datos[posicion + 1]
                                                    .trim();

                                    Seguimiento seguimiento =
                                            new Seguimiento(
                                                    descripcion,
                                                    fecha
                                            );

                                    solicitud.agregarSeguimiento(
                                            seguimiento
                                    );

                                    posicion += 2;
                                }
                            }
                        }
                    }
                }
            }

        } catch (IOException
                | NumberFormatException e) {

            System.out.println(
                    "Error al cargar solicitudes: "
                    + e.getMessage()
            );
        }
    }

    // =========================================================
    // LIMPIAR DATOS
    // =========================================================

    private static String limpiar(String texto) {

        if (texto == null) {
            return "";
        }

        return texto
                .replace(",", " ")
                .replace("\n", " ")
                .replace("\r", " ");
    }

    // =========================================================
    // COMPROBAR ARCHIVOS
    // =========================================================

    public static boolean existenArchivosDeDatos() {

        File usuarios =
                new File(ARCHIVO_USUARIOS);

        File solicitudes =
                new File(ARCHIVO_SOLICITUDES);

        return usuarios.exists()
                && solicitudes.exists();
    }
}