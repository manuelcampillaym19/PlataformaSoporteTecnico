package gestionSoporte;

import java.util.HashMap;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 * Clase principal de la plataforma de soporte.
 */
public class Sistema {

    private HashMap<Integer, Usuario> usuarios;
    private Scanner scanner;

    public Sistema() {
        usuarios = archivoDatos.cargarUsuarios();
        scanner = new Scanner(System.in);
        archivoDatos.cargarSolicitudes(usuarios);

        if (!archivoDatos.existenArchivosDeDatos()) {
            cargarDatosIniciales();
            guardarDatos();
        }
    }

    public HashMap<Integer, Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(HashMap<Integer, Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public static void main(String[] args) {
        Sistema sistema = new Sistema();
        sistema.seleccionarModo();
    }

    /**
     * Permite seleccionar la modalidad de trabajo.
     */
    public void seleccionarModo() {

        String opcion = JOptionPane.showInputDialog(
                null,
                "======================================\n"
                + "       PLATAFORMA DE SOPORTE\n"
                + "======================================\n"
                + "1. Consola\n"
                + "2. Ventana\n"
                + "======================================\n"
                + "Seleccione una modalidad:"
        );

        try {

            if ("1".equals(opcion)) {
                menuConsola();

            } else if ("2".equals(opcion)) {
                menuVentana();

            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Modalidad no válida."
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Se produjo un error: " + e.getMessage()
            );

        } finally {
            guardarDatos();
        }
    }

    // =========================================================
    // MENÚ PRINCIPAL - CONSOLA
    // =========================================================

    private void menuConsola() {

        int opcion;

        do {

            System.out.println("\n======================================");
            System.out.println("       PLATAFORMA DE SOPORTE");
            System.out.println("======================================");
            System.out.println("1. Registrar usuario");
            System.out.println("2. Consultar usuarios");
            System.out.println("3. Registrar solicitud");
            System.out.println("4. Consultar solicitudes");
            System.out.println("5. Localizar usuario");
            System.out.println("6. Dar de baja usuario");
            System.out.println("7. Modificar usuario");
            System.out.println("8. Finalizar solicitud");
            System.out.println("9. Registrar valoración");
            System.out.println("10. Localizar solicitud");
            System.out.println("11. Modificar solicitud");
            System.out.println("12. Dar de baja solicitud");
            System.out.println("13. Consultar satisfacción");
            System.out.println("14. Detectar solicitudes prioritarias");
            System.out.println("0. Salir");
            System.out.println("======================================");
            System.out.print("Opción: ");

            opcion = leerEntero();

            try {

                switch (opcion) {

                    case 1:
                        registrarUsuarioConsola();
                        break;

                    case 2:
                        consultarUsuariosConsola();
                        break;

                    case 3:
                        registrarSolicitudConsola();
                        break;

                    case 4:
                        consultarSolicitudesConsola();
                        break;

                    case 5:
                        localizarUsuarioConsola();
                        break;

                    case 6:
                        eliminarUsuarioConsola();
                        break;

                    case 7:
                        modificarUsuarioConsola();
                        break;

                    case 8:
                        finalizarSolicitudConsola();
                        break;

                    case 9:
                        registrarValoracionConsola();
                        break;

                    case 10:
                        localizarSolicitudConsola();
                        break;

                    case 11:
                        modificarSolicitudConsola();
                        break;

                    case 12:
                        eliminarSolicitudConsola();
                        break;

                    case 13:
                        consultarSatisfaccionConsola();
                        break;

                    case 14:
                        solicitudesPrioritariasConsola();
                        break;

                    case 0:
                        System.out.println(
                                "Cerrando la plataforma..."
                        );
                        break;

                    default:
                        System.out.println(
                                "Opción no válida."
                        );
                }

            } catch (UsuarioNoEncontradoException e) {

                System.out.println(e.getMessage());

            } catch (SolicitudNoEncontradaException e) {

                System.out.println(e.getMessage());

            } catch (Exception e) {

                System.out.println(
                        "No fue posible realizar la operación: "
                        + e.getMessage()
                );
            }

        } while (opcion != 0);
    }

    // =========================================================
    // USUARIOS - CONSOLA
    // =========================================================

    private void registrarUsuarioConsola() {

        System.out.println("\n--- REGISTRO DE USUARIO ---");

        System.out.print("Identificador: ");
        int id = leerEntero();

        if (usuarios.containsKey(id)) {

            System.out.println(
                    "Ya existe un usuario con ese identificador."
            );

            return;
        }

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Correo electrónico: ");
        String correo = scanner.nextLine();

        Usuario usuario = new Usuario(
                id,
                nombre,
                correo
        );

        usuarios.put(id, usuario);

        guardarDatos();

        System.out.println(
                "Usuario registrado correctamente."
        );
    }

    private void consultarUsuariosConsola() {

        System.out.println("\n--- USUARIOS REGISTRADOS ---");

        if (usuarios.isEmpty()) {

            System.out.println(
                    "No existen usuarios registrados."
            );

            return;
        }

        for (Usuario usuario : usuarios.values()) {
            usuario.mostrar();
        }
    }

    private void localizarUsuarioConsola()
            throws UsuarioNoEncontradoException {

        System.out.print(
                "Ingrese el identificador del usuario: "
        );

        int id = leerEntero();

        Usuario usuario = buscarUsuario(id);

        usuario.mostrar(true);
    }

    private void modificarUsuarioConsola()
            throws UsuarioNoEncontradoException {

        System.out.print(
                "Identificador del usuario a modificar: "
        );

        int id = leerEntero();

        Usuario usuario = buscarUsuario(id);

        System.out.print("Nuevo nombre (actual: " + usuario.getNombre() + "): ");
        String nombre = scanner.nextLine();

        System.out.print("Nuevo correo (actual: " + usuario.getCorreo() + "): ");
        String correo = scanner.nextLine();

        if (!nombre.trim().isEmpty()) {
            usuario.setNombre(nombre);
        }

        if (!correo.trim().isEmpty()) {
            usuario.setCorreo(correo);
        }

        guardarDatos();

        System.out.println(
                "Información actualizada correctamente."
        );
    }

    private void eliminarUsuarioConsola()
            throws UsuarioNoEncontradoException {

        System.out.print(
                "Identificador del usuario a eliminar: "
        );

        int id = leerEntero();

        Usuario usuario = buscarUsuario(id);

        if (!usuario.getSolicitudes().isEmpty()) {

            System.out.println(
                    "No se puede eliminar el usuario porque "
                    + "posee solicitudes asociadas."
            );

            return;
        }

        usuarios.remove(id);

        guardarDatos();

        System.out.println(
                "Usuario eliminado correctamente."
        );
    }

    // =========================================================
    // SOLICITUDES - CONSOLA
    // =========================================================

    private void registrarSolicitudConsola() {

        System.out.println("\n--- REGISTRO DE SOLICITUD ---");

        System.out.print(
                "Identificador del usuario: "
        );

        int idUsuario = leerEntero();

        try {

            Usuario usuario = buscarUsuario(idUsuario);

            System.out.print(
                    "Descripción de la solicitud: "
            );

            String detalle = scanner.nextLine();

            new Solicitud(
                    detalle,
                    usuario
            );

            guardarDatos();

            System.out.println(
                    "Solicitud registrada correctamente."
            );

        } catch (UsuarioNoEncontradoException e) {

            System.out.println(e.getMessage());
        }
    }

    private void consultarSolicitudesConsola() {

        System.out.println("\n--- SOLICITUDES DE REPARACIÓN ---");

        boolean existe = false;

        for (Usuario usuario : usuarios.values()) {
            for (Solicitud solicitud : usuario.getSolicitudes()) {

                System.out.println("\nSolicitud #" + solicitud.getIdSolicitud());
                System.out.println("Cliente: " + usuario.getNombre());
                System.out.println("Estado: " + solicitud.getEstado());
                System.out.println("Detalle: " + solicitud.getDetalle());

                if (solicitud.getValoracion() > 0) {
                    System.out.println("Valoración: " + solicitud.getValoracion() + "/5");
                } else {
                    System.out.println("Valoración: Sin valorar");
                }

                System.out.println("--------------------------------------");
                existe = true;
            }
        }

        if (!existe) {
            System.out.println("No existen solicitudes registradas.");
        }
    }

    private void localizarSolicitudConsola()
            throws SolicitudNoEncontradaException {

        System.out.print(
                "Identificador de la solicitud: "
        );

        int id = leerEntero();

        Solicitud solicitud = buscarSolicitud(id);

        solicitud.mostrar(true);
    }

    private void modificarSolicitudConsola()
            throws SolicitudNoEncontradaException {

        System.out.print(
                "Identificador de la solicitud a modificar: "
        );

        int id = leerEntero();

        Solicitud solicitud = buscarSolicitud(id);

        System.out.print(
                "Nueva descripción: "
        );

        String detalle = scanner.nextLine();

        solicitud.setDetalle(detalle);

        guardarDatos();

        System.out.println(
                "Solicitud modificada correctamente."
        );
    }

    private void eliminarSolicitudConsola()
            throws SolicitudNoEncontradaException {

        System.out.print(
                "Identificador de la solicitud a eliminar: "
        );

        int id = leerEntero();

        Solicitud solicitud = buscarSolicitud(id);

        solicitud.getUsuario()
                .getSolicitudes()
                .remove(solicitud);

        guardarDatos();

        System.out.println(
                "Solicitud eliminada correctamente."
        );
    }

    // =========================================================
    // OPERACIONES SOBRE SOLICITUDES - CONSOLA
    // =========================================================

    private void finalizarSolicitudConsola()
            throws SolicitudNoEncontradaException {

        System.out.print(
                "Identificador de la solicitud: "
        );

        int id = leerEntero();

        Solicitud solicitud = buscarSolicitud(id);

        solicitud.cerrar();

        guardarDatos();

        System.out.println(
                "La solicitud fue finalizada."
        );
    }

    private void registrarValoracionConsola()
            throws SolicitudNoEncontradaException {

        System.out.print(
                "Identificador de la solicitud: "
        );

        int id = leerEntero();

        Solicitud solicitud = buscarSolicitud(id);

        System.out.print(
                "Valoración (1 a 5): "
        );

        int nota = leerEntero();

        if (solicitud.valorar(nota)) {

            guardarDatos();

            System.out.println(
                    "Valoración registrada correctamente."
            );

        } else {

            System.out.println(
                    "No fue posible registrar la valoración. "
                    + "La solicitud debe estar cerrada y "
                    + "la nota debe estar entre 1 y 5."
            );
        }
    }

    private void consultarSatisfaccionConsola() {

        double promedio = calcularPromedio();

        System.out.println(
                "\nÍndice promedio de satisfacción: "
                + String.format("%.2f", promedio)
        );
    }

    /**
     * Detecta solicitudes pendientes que superan
     * un tiempo determinado de espera.
     */
    private void solicitudesPrioritariasConsola() {

        System.out.print(
                "Tiempo mínimo pendiente en segundos: "
        );

        int limite = leerEntero();

        mostrarSolicitudesPrioritariasConsola(limite);
    }

    private void mostrarSolicitudesPrioritariasConsola(
            int limite) {

        boolean encontrada = false;

        long ahora = System.currentTimeMillis();

        System.out.println(
                "\n--- SOLICITUDES PRIORITARIAS ---"
        );

        for (Usuario usuario : usuarios.values()) {

            for (Solicitud solicitud
                    : usuario.getSolicitudes()) {

                if ("Pendiente".equalsIgnoreCase(
                        solicitud.getEstado())) {

                    long tiempo =
                            (ahora
                            - solicitud.getMomentoCreacion())
                            / 1000;

                    if (tiempo >= limite) {

                        System.out.println("\nSolicitud #" + solicitud.getIdSolicitud());
                        System.out.println("Cliente: " + usuario.getNombre());
                        System.out.println("Estado: " + solicitud.getEstado());
                        System.out.println("Tiempo pendiente: " + tiempo + " segundos");
                        System.out.println("Detalle: " + solicitud.getDetalle());
                        System.out.println("--------------------------------------");

                        encontrada = true;
                    }
                }
            }
        }

        if (!encontrada) {

            System.out.println(
                    "No existen solicitudes que "
                    + "cumplan el criterio."
            );
        }
    }

    // =========================================================
    // MENÚ PRINCIPAL - VENTANA
    // =========================================================

    private void menuVentana() {

        String opcion;

        do {

            opcion = JOptionPane.showInputDialog(
                    null,
                    "======================================\n"
                    + "       PLATAFORMA DE SOPORTE\n"
                    + "======================================\n"
                    + "1. Registrar usuario\n"
                    + "2. Consultar usuarios\n"
                    + "3. Registrar solicitud\n"
                    + "4. Consultar solicitudes\n"
                    + "5. Localizar usuario\n"
                    + "6. Dar de baja usuario\n"
                    + "7. Modificar usuario\n"
                    + "8. Finalizar solicitud\n"
                    + "9. Registrar valoración\n"
                    + "10. Localizar solicitud\n"
                    + "11. Modificar solicitud\n"
                    + "12. Dar de baja solicitud\n"
                    + "13. Consultar satisfacción\n"
                    + "14. Detectar solicitudes prioritarias\n"
                    + "0. Salir\n"
                    + "======================================\n"
                    + "Seleccione una opción:"
            );

            if (opcion == null) {
                opcion = "0";
            }

            try {

                switch (opcion) {

                    case "1":
                        registrarUsuarioVentana();
                        break;

                    case "2":
                        consultarUsuariosVentana();
                        break;

                    case "3":
                        registrarSolicitudVentana();
                        break;

                    case "4":
                        consultarSolicitudesVentana();
                        break;

                    case "5":
                        localizarUsuarioVentana();
                        break;

                    case "6":
                        eliminarUsuarioVentana();
                        break;

                    case "7":
                        modificarUsuarioVentana();
                        break;

                    case "8":
                        finalizarSolicitudVentana();
                        break;

                    case "9":
                        registrarValoracionVentana();
                        break;

                    case "10":
                        localizarSolicitudVentana();
                        break;

                    case "11":
                        modificarSolicitudVentana();
                        break;

                    case "12":
                        eliminarSolicitudVentana();
                        break;

                    case "13":
                        consultarSatisfaccionVentana();
                        break;

                    case "14":
                        solicitudesPrioritariasVentana();
                        break;

                    case "0":
                        break;

                    default:
                        JOptionPane.showMessageDialog(
                                null,
                                "Opción no válida."
                        );
                }

            } catch (UsuarioNoEncontradoException e) {

                JOptionPane.showMessageDialog(
                        null,
                        e.getMessage()
                );

            } catch (SolicitudNoEncontradaException e) {

                JOptionPane.showMessageDialog(
                        null,
                        e.getMessage()
                );

            } catch (Exception e) {

                JOptionPane.showMessageDialog(
                        null,
                        "No fue posible realizar la operación."
                );
            }

        } while (!"0".equals(opcion));
    }

    // =========================================================
    // USUARIOS - VENTANA
    // =========================================================

    private void registrarUsuarioVentana() {

        try {

            int id = Integer.parseInt(
                    JOptionPane.showInputDialog(
                            "Identificador:"
                    )
            );

            if (usuarios.containsKey(id)) {

                JOptionPane.showMessageDialog(
                        null,
                        "Ya existe un usuario con ese identificador."
                );

                return;
            }

            String nombre =
                    JOptionPane.showInputDialog(
                            "Nombre:"
                    );

            String correo =
                    JOptionPane.showInputDialog(
                            "Correo electrónico:"
                    );

            usuarios.put(
                    id,
                    new Usuario(id, nombre, correo)
            );

            guardarDatos();

            JOptionPane.showMessageDialog(
                    null,
                    "Usuario registrado correctamente."
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Los datos ingresados no son válidos."
            );
        }
    }

    private void consultarUsuariosVentana() {

        if (usuarios.isEmpty()) {

            JOptionPane.showMessageDialog(
                    null,
                    "No existen usuarios registrados."
            );

            return;
        }

        StringBuilder texto =
                new StringBuilder();

        for (Usuario usuario : usuarios.values()) {

            texto.append("ID: ").append(usuario.getId()).append("\n")
                    .append("Nombre: ").append(usuario.getNombre()).append("\n")
                    .append("Correo: ").append(usuario.getCorreo()).append("\n")
                    .append("Solicitudes: ").append(usuario.getSolicitudes().size()).append("\n")
                    .append("--------------------------------------\n\n");
        }

        JOptionPane.showMessageDialog(
                null,
                texto.toString(),
                "Usuarios registrados",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void localizarUsuarioVentana()
            throws UsuarioNoEncontradoException {

        int id = Integer.parseInt(
                JOptionPane.showInputDialog(
                        "Identificador del usuario:"
                )
        );

        Usuario usuario = buscarUsuario(id);

        JOptionPane.showMessageDialog(
                null,
                usuario.toString(),
                "Información del usuario",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void modificarUsuarioVentana()
            throws UsuarioNoEncontradoException {

        int id = Integer.parseInt(
                JOptionPane.showInputDialog(
                        "Identificador del usuario:"
                )
        );

        Usuario usuario = buscarUsuario(id);

        String nombre =
                JOptionPane.showInputDialog(
                        "Nuevo nombre:",
                        usuario.getNombre()
                );

        String correo =
                JOptionPane.showInputDialog(
                        "Nuevo correo:",
                        usuario.getCorreo()
                );

        usuario.setNombre(nombre);
        usuario.setCorreo(correo);

        guardarDatos();

        JOptionPane.showMessageDialog(
                null,
                "Información actualizada correctamente."
        );
    }

    private void eliminarUsuarioVentana()
            throws UsuarioNoEncontradoException {

        int id = Integer.parseInt(
                JOptionPane.showInputDialog(
                        "Identificador del usuario:"
                )
        );

        Usuario usuario = buscarUsuario(id);

        if (!usuario.getSolicitudes().isEmpty()) {

            JOptionPane.showMessageDialog(
                    null,
                    "No se puede eliminar el usuario porque "
                    + "posee solicitudes asociadas."
            );

            return;
        }

        usuarios.remove(id);

        guardarDatos();

        JOptionPane.showMessageDialog(
                null,
                "Usuario eliminado correctamente."
        );
    }

    // =========================================================
    // SOLICITUDES - VENTANA
    // =========================================================

    private void registrarSolicitudVentana() {

        try {

            int idUsuario = Integer.parseInt(
                    JOptionPane.showInputDialog(
                            "Identificador del usuario:"
                    )
            );

            Usuario usuario =
                    buscarUsuario(idUsuario);

            String detalle =
                    JOptionPane.showInputDialog(
                            "Descripción de la solicitud:"
                    );

            new Solicitud(
                    detalle,
                    usuario
            );

            guardarDatos();

            JOptionPane.showMessageDialog(
                    null,
                    "Solicitud registrada correctamente."
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    null,
                    "No fue posible registrar la solicitud: "
                    + e.getMessage()
            );
        }
    }

    private void consultarSolicitudesVentana() {

        StringBuilder texto = new StringBuilder();

        for (Usuario usuario : usuarios.values()) {
            for (Solicitud solicitud : usuario.getSolicitudes()) {

                texto.append("Solicitud #").append(solicitud.getIdSolicitud()).append("\n")
                        .append("Cliente: ").append(usuario.getNombre()).append("\n")
                        .append("Estado: ").append(solicitud.getEstado()).append("\n")
                        .append("Detalle: ").append(solicitud.getDetalle()).append("\n");

                if (solicitud.getValoracion() > 0) {
                    texto.append("Valoración: ").append(solicitud.getValoracion()).append("/5\n");
                } else {
                    texto.append("Valoración: Sin valorar\n");
                }

                texto.append("--------------------------------------\n\n");
            }
        }

        if (texto.length() == 0) {
            texto.append("No existen solicitudes registradas.");
        }

        JOptionPane.showMessageDialog(
                null,
                texto.toString(),
                "Solicitudes registradas",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void localizarSolicitudVentana()
            throws SolicitudNoEncontradaException {

        int id = Integer.parseInt(
                JOptionPane.showInputDialog(
                        "Identificador de la solicitud:"
                )
        );

        Solicitud solicitud =
                buscarSolicitud(id);

        JOptionPane.showMessageDialog(
                null,
                solicitud.toString(),
                "Información de la solicitud",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void modificarSolicitudVentana()
            throws SolicitudNoEncontradaException {

        int id = Integer.parseInt(
                JOptionPane.showInputDialog(
                        "Identificador de la solicitud:"
                )
        );

        Solicitud solicitud =
                buscarSolicitud(id);

        String detalle =
                JOptionPane.showInputDialog(
                        "Nueva descripción:",
                        solicitud.getDetalle()
                );

        solicitud.setDetalle(detalle);

        guardarDatos();

        JOptionPane.showMessageDialog(
                null,
                "Solicitud modificada correctamente."
        );
    }

    private void eliminarSolicitudVentana()
            throws SolicitudNoEncontradaException {

        int id = Integer.parseInt(
                JOptionPane.showInputDialog(
                        "Identificador de la solicitud:"
                )
        );

        Solicitud solicitud =
                buscarSolicitud(id);

        solicitud.getUsuario()
                .getSolicitudes()
                .remove(solicitud);

        guardarDatos();

        JOptionPane.showMessageDialog(
                null,
                "Solicitud eliminada correctamente."
        );
    }

    // =========================================================
    // OPERACIONES - VENTANA
    // =========================================================

    private void finalizarSolicitudVentana()
            throws SolicitudNoEncontradaException {

        int id = Integer.parseInt(
                JOptionPane.showInputDialog(
                        "Identificador de la solicitud:"
                )
        );

        Solicitud solicitud =
                buscarSolicitud(id);

        solicitud.cerrar();

        guardarDatos();

        JOptionPane.showMessageDialog(
                null,
                "La solicitud fue finalizada."
        );
    }

    private void registrarValoracionVentana()
            throws SolicitudNoEncontradaException {

        int id = Integer.parseInt(
                JOptionPane.showInputDialog(
                        "Identificador de la solicitud:"
                )
        );

        Solicitud solicitud =
                buscarSolicitud(id);

        int nota = Integer.parseInt(
                JOptionPane.showInputDialog(
                        "Valoración de 1 a 5:"
                )
        );

        if (solicitud.valorar(nota)) {

            guardarDatos();

            JOptionPane.showMessageDialog(
                    null,
                    "Valoración registrada correctamente."
            );

        } else {

            JOptionPane.showMessageDialog(
                    null,
                    "No fue posible registrar la valoración."
            );
        }
    }

    private void consultarSatisfaccionVentana() {

        double promedio =
                calcularPromedio();

        JOptionPane.showMessageDialog(
                null,
                "Índice promedio de satisfacción: "
                + String.format("%.2f", promedio),
                "Satisfacción",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void solicitudesPrioritariasVentana() {

        try {

            int limite = Integer.parseInt(
                    JOptionPane.showInputDialog(
                            "Tiempo mínimo pendiente en segundos:"
                    )
            );

            mostrarSolicitudesPrioritariasVentana(limite);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Ingrese un valor numérico válido."
            );
        }
    }

    private void mostrarSolicitudesPrioritariasVentana(
            int limite) {

        StringBuilder resultado =
                new StringBuilder();

        long ahora =
                System.currentTimeMillis();

        for (Usuario usuario : usuarios.values()) {

            for (Solicitud solicitud
                    : usuario.getSolicitudes()) {

                if ("Pendiente".equalsIgnoreCase(
                        solicitud.getEstado())) {

                    long tiempo =
                            (ahora
                            - solicitud.getMomentoCreacion())
                            / 1000;

                    if (tiempo >= limite) {

                        resultado.append("Solicitud #").append(solicitud.getIdSolicitud()).append("\n")
                                .append("Cliente: ").append(usuario.getNombre()).append("\n")
                                .append("Estado: ").append(solicitud.getEstado()).append("\n")
                                .append("Tiempo pendiente: ").append(tiempo).append(" segundos\n")
                                .append("Detalle: ").append(solicitud.getDetalle()).append("\n")
                                .append("--------------------------------------\n\n");
                    }
                }
            }
        }

        if (resultado.length() == 0) {

            resultado.append(
                    "No existen solicitudes que "
                    + "cumplan el criterio."
            );
        }

        JOptionPane.showMessageDialog(
                null,
                resultado.toString(),
                "Solicitudes prioritarias",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Carga tres usuarios y solicitudes de prueba en la primera ejecución.
     */
    private void cargarDatosIniciales() {
        Usuario usuario1 = new Usuario(1, "Juan Perez", "juan@correo.com");
        Usuario usuario2 = new Usuario(2, "Pedro Soto", "pedro@correo.com");
        Usuario usuario3 = new Usuario(3, "Ana Morales", "ana@correo.com");

        usuarios.put(1, usuario1);
        usuarios.put(2, usuario2);
        usuarios.put(3, usuario3);

        Solicitud solicitud1 = new Solicitud(
                "Teclado del equipo en mal estado",
                usuario1
        );
        solicitud1.setMomentoCreacion(
                System.currentTimeMillis() - 120000
        );

        Solicitud solicitud2 = new Solicitud(
                "Monitor no enciende",
                usuario2
        );
        solicitud2.cerrar();
        solicitud2.valorar(4);
    }

    // =========================================================
    // BÚSQUEDAS
    // =========================================================

    private Usuario buscarUsuario(int id)
            throws UsuarioNoEncontradoException {

        Usuario usuario = usuarios.get(id);

        if (usuario == null) {

            throw new UsuarioNoEncontradoException(
                    "No existe un usuario con el identificador "
                    + id + "."
            );
        }

        return usuario;
    }

    private Solicitud buscarSolicitud(int id)
            throws SolicitudNoEncontradaException {

        for (Usuario usuario : usuarios.values()) {

            for (Solicitud solicitud
                    : usuario.getSolicitudes()) {

                if (solicitud.getIdSolicitud() == id) {
                    return solicitud;
                }
            }
        }

        throw new SolicitudNoEncontradaException(
                "No existe una solicitud con el identificador "
                + id + "."
        );
    }

    // =========================================================
    // SATISFACCIÓN
    // =========================================================

    private double calcularPromedio() {

        int suma = 0;
        int cantidad = 0;

        for (Usuario usuario : usuarios.values()) {

            for (Solicitud solicitud
                    : usuario.getSolicitudes()) {

                if (solicitud.getValoracion() > 0) {

                    suma += solicitud.getValoracion();
                    cantidad++;
                }
            }
        }

        if (cantidad == 0) {
            return 0;
        }

        return (double) suma / cantidad;
    }

    // =========================================================
    // UTILIDADES
    // =========================================================

    private int leerEntero() {

        while (true) {

            try {

                return Integer.parseInt(
                        scanner.nextLine()
                );

            } catch (NumberFormatException e) {

                System.out.print(
                        "Ingrese un número válido: "
                );
            }
        }
    }

    private void guardarDatos() {

        archivoDatos.guardarUsuarios(usuarios);
        archivoDatos.guardarSolicitudes(usuarios);
    }
}