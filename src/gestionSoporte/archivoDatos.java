package gestionSoporte;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class archivoDatos {

    private static final String ARCHIVO_USUARIOS = "usuarios.txt";
    private static final String ARCHIVO_SOLICITUDES = "solicitudes.txt";

    // =========================================================
    // GUARDAR USUARIOS
    // =========================================================

    public static void guardarUsuarios(HashMap<Integer, Usuario> usuarios) {

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(ARCHIVO_USUARIOS))) {

            for (Usuario usuario : usuarios.values()) {

                String nombre = limpiar(usuario.getNombre());
                String correo = limpiar(usuario.getCorreo());

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

    // =========================================================
    // CARGAR USUARIOS
    // =========================================================

    public static HashMap<Integer, Usuario> cargarUsuarios() {

        HashMap<Integer, Usuario> usuarios = new HashMap<>();

        File archivo = new File(ARCHIVO_USUARIOS);

        if (!archivo.exists()) {
            return usuarios;
        }

        try (BufferedReader br = new BufferedReader(
                new FileReader(archivo))) {

            String linea;

            while ((linea = br.readLine()) != null) {

                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] datos = linea.split(",");

                if (datos.length >= 3) {

                    int id = Integer.parseInt(datos[0].trim());
                    String nombre = datos[1].trim();
                    String correo = datos[2].trim();

                    /*
                     * El ID ahora se genera automáticamente
                     * en Usuario.
                     *
                     * Al cargar datos antiguos, primero
                     * creamos el usuario y después restauramos
                     * el ID que estaba guardado.
                     */
                    Usuario usuario = new Usuario(
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

        } catch (IOException | NumberFormatException e) {

            System.out.println(
                    "Error al cargar usuarios: "
                    + e.getMessage()
            );
        }

        return usuarios;
    }

    // =========================================================
    // GUARDAR SOLICITUDES
    // =========================================================

    public static void guardarSolicitudes(
            HashMap<Integer, Usuario> usuarios) {

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(ARCHIVO_SOLICITUDES))) {

            for (Usuario usuario : usuarios.values()) {

                for (Solicitud solicitud
                        : usuario.getSolicitudes()) {

                    bw.write(
                            solicitud.getIdSolicitud()
                            + "," + limpiar(solicitud.getDetalle())
                            + "," + limpiar(solicitud.getEstado())
                            + "," + solicitud.getTiempoAtencion()
                            + "," + usuario.getId()
                            + "," + solicitud.getValoracion()
                            + "," + solicitud.getMomentoCreacion()
                            + "," + solicitud.getMomentoCierre()
                    );

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

        File archivo = new File(ARCHIVO_SOLICITUDES);

        if (!archivo.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(
                new FileReader(archivo))) {

            String linea;

            while ((linea = br.readLine()) != null) {

                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] datos = linea.split(",");

                if (datos.length >= 8) {

                    int idSolicitud =
                            Integer.parseInt(datos[0].trim());

                    String detalle = datos[1].trim();

                    String estado = datos[2].trim();

                    int tiempoAtencion =
                            Integer.parseInt(datos[3].trim());

                    int idUsuario =
                            Integer.parseInt(datos[4].trim());

                    int valoracion =
                            Integer.parseInt(datos[5].trim());

                    long momentoCreacion =
                            Long.parseLong(datos[6].trim());

                    long momentoCierre =
                            Long.parseLong(datos[7].trim());

                    Usuario usuario = usuarios.get(idUsuario);

                    if (usuario != null) {

                        Solicitud solicitud =
                                new Solicitud(
                                        detalle,
                                        usuario
                                );

                        /*
                         * Restauramos los datos guardados.
                         */
                        solicitud.setIdSolicitud(idSolicitud);
                        solicitud.setEstado(estado);
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
                    }
                }
            }

        } catch (IOException | NumberFormatException e) {

            System.out.println(
                    "Error al cargar solicitudes: "
                    + e.getMessage()
            );
        }
    }

    // =========================================================
    // LIMPIAR TEXTO
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

        File usuarios = new File(ARCHIVO_USUARIOS);
        File solicitudes = new File(ARCHIVO_SOLICITUDES);

        return usuarios.exists() && solicitudes.exists();
    }
}