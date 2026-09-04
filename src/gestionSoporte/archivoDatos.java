package gestionSoporte;

import java.io.*;
import java.util.HashMap;

/**
 * Gestiona el almacenamiento de usuarios y solicitudes.
 */
public class archivoDatos {

    private static final String ARCHIVO_USUARIOS = "usuarios.txt";
    private static final String ARCHIVO_SOLICITUDES = "solicitudes.txt";

    // GUARDAR USUARIOS
    public static void guardarUsuarios(HashMap<Integer, Usuario> usuarios) {

        try (BufferedWriter salida = new BufferedWriter(
                new FileWriter(ARCHIVO_USUARIOS))) {

            for (Usuario usuario : usuarios.values()) {

                salida.write(
                        usuario.getId() + ","
                        + limpiar(usuario.getNombre()) + ","
                        + limpiar(usuario.getCorreo())
                );

                salida.newLine();
            }

        } catch (IOException e) {
            System.out.println(
                    "No se pudieron guardar los usuarios: "
                    + e.getMessage());
        }
    }

    // CARGAR USUARIOS
    public static HashMap<Integer, Usuario> cargarUsuarios() {

        HashMap<Integer, Usuario> usuarios = new HashMap<>();
        File archivo = new File(ARCHIVO_USUARIOS);

        if (!archivo.exists()) {
            return usuarios;
        }

        try (BufferedReader entrada = new BufferedReader(
                new FileReader(archivo))) {

            String linea;

            while ((linea = entrada.readLine()) != null) {

                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] partes = linea.split(",", -1);

                if (partes.length != 3) {
                    continue;
                }

                try {

                    int id = Integer.parseInt(partes[0].trim());
                    String nombre = partes[1].trim();
                    String correo = partes[2].trim();

                    Usuario usuario =
                            new Usuario(id, nombre, correo);

                    usuarios.put(id, usuario);

                } catch (NumberFormatException e) {

                    System.out.println(
                            "Registro de usuario inválido: "
                            + linea);
                }
            }

        } catch (IOException e) {

            System.out.println(
                    "No se pudieron cargar los usuarios: "
                    + e.getMessage());
        }

        return usuarios;
    }

    // GUARDAR SOLICITUDES
    public static void guardarSolicitudes(
            HashMap<Integer, Usuario> usuarios) {

        try (BufferedWriter salida = new BufferedWriter(
                new FileWriter(ARCHIVO_SOLICITUDES))) {

            for (Usuario usuario : usuarios.values()) {

                for (Solicitud solicitud
                        : usuario.getSolicitudes()) {

                    salida.write(
                            solicitud.getIdSolicitud() + ","
                            + limpiar(solicitud.getDetalle()) + ","
                            + limpiar(solicitud.getEstado()) + ","
                            + solicitud.getTiempoAtencion() + ","
                            + usuario.getId() + ","
                            + solicitud.getValoracion() + ","
                            + solicitud.getMomentoCreacion() + ","
                            + solicitud.getMomentoCierre()
                    );

                    salida.newLine();
                }
            }

        } catch (IOException e) {

            System.out.println(
                    "No se pudieron guardar las solicitudes: "
                    + e.getMessage());
        }
    }

    // CARGAR SOLICITUDES
    public static void cargarSolicitudes(
            HashMap<Integer, Usuario> usuarios) {

        File archivo = new File(ARCHIVO_SOLICITUDES);

        if (!archivo.exists()) {
            return;
        }

        try (BufferedReader entrada = new BufferedReader(
                new FileReader(archivo))) {

            String linea;

            while ((linea = entrada.readLine()) != null) {

                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] partes = linea.split(",", -1);

                if (partes.length != 8) {
                    continue;
                }

                try {

                    int id = Integer.parseInt(partes[0].trim());
                    String detalle = partes[1].trim();
                    String estado = partes[2].trim();
                    int tiempo = Integer.parseInt(partes[3].trim());
                    int idUsuario = Integer.parseInt(partes[4].trim());
                    int valoracion = Integer.parseInt(partes[5].trim());
                    long creacion = Long.parseLong(partes[6].trim());
                    long cierre = Long.parseLong(partes[7].trim());

                    Usuario usuario = usuarios.get(idUsuario);

                    if (usuario != null) {

                        Solicitud solicitud =
                                new Solicitud(detalle, usuario);

                        solicitud.setIdSolicitud(id);
                        solicitud.setEstado(estado);
                        solicitud.setTiempoAtencion(tiempo);
                        solicitud.setValoracion(valoracion);
                        solicitud.setMomentoCreacion(creacion);
                        solicitud.setMomentoCierre(cierre);
                    }

                } catch (NumberFormatException e) {

                    System.out.println(
                            "Registro de solicitud inválido: "
                            + linea);
                }
            }

        } catch (IOException e) {

            System.out.println(
                    "No se pudieron cargar las solicitudes: "
                    + e.getMessage());
        }
    }

    // Evita problemas si alguien escribe una coma en un dato.
    private static String limpiar(String texto) {
        return texto.replace(",", " ");
    }

    /**
     * Comprueba si existen archivos de datos almacenados.
     *
     * @return true si existe al menos uno de los archivos de datos
     */
    public static boolean existenArchivosDeDatos() {

        File usuarios = new File(ARCHIVO_USUARIOS);
        File solicitudes = new File(ARCHIVO_SOLICITUDES);

        return usuarios.exists() || solicitudes.exists();
    }
}