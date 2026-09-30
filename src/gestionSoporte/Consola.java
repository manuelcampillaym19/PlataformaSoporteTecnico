package gestionSoporte;

import java.util.List;
import java.util.Scanner;

public class Consola {

    private Gestor gestor;
    private Scanner scanner;

    public Consola(Gestor gestor) {
        this.gestor = gestor;
        this.scanner = new Scanner(System.in);
    }

    public Gestor getGestor() {
        return gestor;
    }

    public void setGestor(Gestor gestor) {
        this.gestor = gestor;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    // =========================================================
    // MENÚ PRINCIPAL
    // =========================================================

    public void iniciar() {

        int opcion;

        do {

            System.out.println();
            System.out.println("======================================");
            System.out.println("       PLATAFORMA DE SOPORTE");
            System.out.println("======================================");
            System.out.println("1. Gestión de usuarios");
            System.out.println("2. Gestión de solicitudes");
            System.out.println("3. Reportes y consultas");
            System.out.println("0. Salir");
            System.out.println("======================================");

            opcion = leerEntero("Opción: ");

            switch (opcion) {

                case 1:
                    menuUsuarios();
                    break;

                case 2:
                    menuSolicitudes();
                    break;

                case 3:
                    menuReportes();
                    break;

                case 0:
                    System.out.println(
                            "Saliendo del sistema..."
                    );
                    break;

                default:
                    System.out.println(
                            "Opción no válida."
                    );
            }

        } while (opcion != 0);
    }

    // =========================================================
    // MENÚ USUARIOS
    // =========================================================

    private void menuUsuarios() {

        int opcion;

        do {

            System.out.println();
            System.out.println("--------------------------------------");
            System.out.println("         GESTIÓN DE USUARIOS");
            System.out.println("--------------------------------------");
            System.out.println("1. Registrar usuario");
            System.out.println("2. Consultar usuarios");
            System.out.println("3. Localizar usuario");
            System.out.println("4. Modificar usuario");
            System.out.println("5. Dar de baja usuario");
            System.out.println("0. Volver");
            System.out.println("--------------------------------------");

            opcion = leerEntero("Opción: ");

            switch (opcion) {

                case 1:
                    registrarUsuario();
                    break;

                case 2:
                    consultarUsuarios();
                    break;

                case 3:
                    localizarUsuario();
                    break;

                case 4:
                    modificarUsuario();
                    break;

                case 5:
                    eliminarUsuario();
                    break;

                case 0:
                    break;

                default:
                    System.out.println(
                            "Opción no válida."
                    );
            }

        } while (opcion != 0);
    }

    // =========================================================
    // REGISTRAR USUARIO
    // =========================================================

    private void registrarUsuario() {

        System.out.println();
        System.out.println("--- REGISTRO DE USUARIO ---");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        String correo;

        while (true) {

            System.out.print("Correo electrónico: ");
            correo = scanner.nextLine();

            if (gestor.correoValido(correo)) {
                break;
            }

            System.out.println();
            System.out.println(
                    "Correo inválido."
            );

            System.out.println(
                    "Debe respetar el formato:"
            );

            System.out.println(
                    "usuario@dominio.extension"
            );
        }

        try {

            boolean registrado =
                    gestor.agregarUsuario(
                            nombre,
                            correo
                    );

            if (registrado) {

                Usuario ultimo =
                        obtenerUltimoUsuario();

                System.out.println();
                System.out.println(
                        "Usuario registrado correctamente."
                );

                if (ultimo != null) {

                    System.out.println(
                            "ID asignado: "
                            + ultimo.getId()
                    );
                }

            } else {

                System.out.println(
                        "No fue posible registrar el usuario."
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Error al registrar usuario: "
                    + e.getMessage()
            );
        }
    }

    // =========================================================
    // OBTENER ÚLTIMO USUARIO
    // =========================================================

    private Usuario obtenerUltimoUsuario() {

        Usuario ultimo = null;

        for (Usuario usuario
                : gestor.listarUsuarios()) {

            if (ultimo == null
                    || usuario.getId() > ultimo.getId()) {

                ultimo = usuario;
            }
        }

        return ultimo;
    }

    // =========================================================
    // CONSULTAR USUARIOS
    // =========================================================

    private void consultarUsuarios() {

        System.out.println();
        System.out.println("--- USUARIOS REGISTRADOS ---");

        List<Usuario> usuarios =
                gestor.listarUsuarios();

        if (usuarios.isEmpty()) {

            System.out.println(
                    "No existen usuarios registrados."
            );

            return;
        }

        for (Usuario usuario : usuarios) {

            System.out.println();

            usuario.mostrar();

            System.out.println(
                    "--------------------------------------"
            );
        }
    }

    // =========================================================
    // LOCALIZAR USUARIO
    // =========================================================

    private void localizarUsuario() {

        System.out.println();
        System.out.println("--- LOCALIZAR USUARIO ---");

        int id = leerEntero(
                "ID del usuario: "
        );

        try {

            Usuario usuario =
                    gestor.buscarUsuario(id);

            System.out.println();

            usuario.mostrar(true);

        } catch (UsuarioNoEncontradoException e) {

            System.out.println(
                    e.getMessage()
            );
        }
    }

    // =========================================================
    // MODIFICAR USUARIO
    // =========================================================

    private void modificarUsuario() {

        System.out.println();
        System.out.println("--- MODIFICAR USUARIO ---");

        int id = leerEntero(
                "ID del usuario: "
        );

        try {

            Usuario usuario =
                    gestor.buscarUsuario(id);

            System.out.println(
                    "Usuario encontrado: "
                    + usuario.getNombre()
            );

            System.out.print(
                    "Nuevo nombre: "
            );

            String nombre =
                    scanner.nextLine();

            String correo;

            while (true) {

                System.out.print(
                        "Nuevo correo electrónico: "
                );

                correo =
                        scanner.nextLine();

                if (gestor.correoValido(correo)) {
                    break;
                }

                System.out.println();
                System.out.println(
                        "Correo inválido."
                );

                System.out.println(
                        "Debe respetar el formato:"
                );

                System.out.println(
                        "usuario@dominio.extension"
                );
            }

            boolean modificado =
                    gestor.modificarUsuario(
                            id,
                            nombre,
                            correo
                    );

            if (modificado) {

                System.out.println(
                        "Usuario modificado correctamente."
                );

            } else {

                System.out.println(
                        "No fue posible modificar el usuario."
                );
            }

        } catch (UsuarioNoEncontradoException e) {

            System.out.println(
                    e.getMessage()
            );
        }
    }

    // =========================================================
    // ELIMINAR USUARIO
    // =========================================================

    private void eliminarUsuario() {

        System.out.println();
        System.out.println("--- DAR DE BAJA USUARIO ---");

        int id = leerEntero(
                "ID del usuario: "
        );

        try {

            Usuario usuario =
                    gestor.buscarUsuario(id);

            System.out.println(
                    "Usuario: "
                    + usuario.getNombre()
            );

            System.out.print(
                    "¿Confirma la eliminación? (s/n): "
            );

            String respuesta =
                    scanner.nextLine();

            if (respuesta.equalsIgnoreCase("s")) {

                gestor.eliminarUsuario(id);

                System.out.println(
                        "Usuario eliminado correctamente."
                );

            } else {

                System.out.println(
                        "Operación cancelada."
                );
            }

        } catch (UsuarioNoEncontradoException e) {

            System.out.println(
                    e.getMessage()
            );
        }
    }

    // =========================================================
    // MENÚ SOLICITUDES
    // =========================================================

    private void menuSolicitudes() {

        int opcion;

        do {

            System.out.println();
            System.out.println("--------------------------------------");
            System.out.println("       GESTIÓN DE SOLICITUDES");
            System.out.println("--------------------------------------");
            System.out.println("1. Registrar solicitud");
            System.out.println("2. Consultar solicitudes");
            System.out.println("3. Localizar solicitud");
            System.out.println("4. Modificar solicitud");
            System.out.println("5. Dar de baja solicitud");
            System.out.println("6. Finalizar solicitud");
            System.out.println("7. Registrar valoración");
            System.out.println("0. Volver");
            System.out.println("--------------------------------------");

            opcion = leerEntero("Opción: ");

            switch (opcion) {

                case 1:
                    registrarSolicitud();
                    break;

                case 2:
                    consultarSolicitudes();
                    break;

                case 3:
                    localizarSolicitud();
                    break;

                case 4:
                    modificarSolicitud();
                    break;

                case 5:
                    eliminarSolicitud();
                    break;

                case 6:
                    finalizarSolicitud();
                    break;

                case 7:
                    registrarValoracion();
                    break;

                case 0:
                    break;

                default:
                    System.out.println(
                            "Opción no válida."
                    );
            }

        } while (opcion != 0);
    }

    // =========================================================
    // REGISTRAR SOLICITUD
    // =========================================================

    private void registrarSolicitud() {

        System.out.println();
        System.out.println(
                "--- REGISTRO DE SOLICITUD ---"
        );

        int idUsuario = leerEntero(
                "ID del usuario: "
        );

        try {

            gestor.buscarUsuario(idUsuario);

            System.out.print(
                    "Descripción de la solicitud: "
            );

            String detalle =
                    scanner.nextLine();

            boolean registrada =
                    gestor.agregarSolicitud(
                            idUsuario,
                            detalle
                    );

            if (registrada) {

                Solicitud ultima =
                        obtenerUltimaSolicitud();

                System.out.println();
                System.out.println(
                        "Solicitud registrada correctamente."
                );

                if (ultima != null) {

                    System.out.println(
                            "ID de solicitud asignado: "
                            + ultima.getIdSolicitud()
                    );
                }

            } else {

                System.out.println(
                        "No fue posible registrar la solicitud."
                );
            }

        } catch (UsuarioNoEncontradoException e) {

            System.out.println(
                    e.getMessage()
            );
        }
    }

    // =========================================================
    // OBTENER ÚLTIMA SOLICITUD
    // =========================================================

    private Solicitud obtenerUltimaSolicitud() {

        Solicitud ultima = null;

        for (Solicitud solicitud
                : gestor.listarSolicitudes()) {

            if (ultima == null
                    || solicitud.getIdSolicitud()
                    > ultima.getIdSolicitud()) {

                ultima = solicitud;
            }
        }

        return ultima;
    }

    // =========================================================
    // CONSULTAR SOLICITUDES
    // =========================================================

    private void consultarSolicitudes() {

        System.out.println();
        System.out.println(
                "--- SOLICITUDES DE REPARACIÓN ---"
        );

        List<Solicitud> solicitudes =
                gestor.listarSolicitudes();

        if (solicitudes.isEmpty()) {

            System.out.println(
                    "No existen solicitudes registradas."
            );

            return;
        }

        for (Solicitud solicitud : solicitudes) {

            System.out.println();

            solicitud.mostrar();

            System.out.println(
                    "--------------------------------------"
            );
        }
    }

    // =========================================================
    // LOCALIZAR SOLICITUD
    // =========================================================

    private void localizarSolicitud() {

        System.out.println();
        System.out.println(
                "--- LOCALIZAR SOLICITUD ---"
        );

        int id = leerEntero(
                "ID de la solicitud: "
        );

        try {

            Solicitud solicitud =
                    gestor.buscarSolicitud(id);

            System.out.println();

            solicitud.mostrar();

        } catch (SolicitudNoEncontradaException e) {

            System.out.println(
                    e.getMessage()
            );
        }
    }

    // =========================================================
    // MODIFICAR SOLICITUD
    // =========================================================

    private void modificarSolicitud() {

        System.out.println();
        System.out.println(
                "--- MODIFICAR SOLICITUD ---"
        );

        int id = leerEntero(
                "ID de la solicitud: "
        );

        try {

            Solicitud solicitud =
                    gestor.buscarSolicitud(id);

            System.out.println(
                    "Solicitud actual: "
                    + solicitud.getDetalle()
            );

            System.out.print(
                    "Nueva descripción: "
            );

            String detalle =
                    scanner.nextLine();

            gestor.modificarSolicitud(
                    id,
                    detalle
            );

            System.out.println(
                    "Solicitud modificada correctamente."
            );

        } catch (SolicitudNoEncontradaException e) {

            System.out.println(
                    e.getMessage()
            );
        }
    }

    // =========================================================
    // ELIMINAR SOLICITUD
    // =========================================================

    private void eliminarSolicitud() {

        System.out.println();
        System.out.println(
                "--- DAR DE BAJA SOLICITUD ---"
        );

        int id = leerEntero(
                "ID de la solicitud: "
        );

        try {

            Solicitud solicitud =
                    gestor.buscarSolicitud(id);

            System.out.println(
                    "Solicitud: "
                    + solicitud.getDetalle()
            );

            System.out.print(
                    "¿Confirma la eliminación? (s/n): "
            );

            String respuesta =
                    scanner.nextLine();

            if (respuesta.equalsIgnoreCase("s")) {

                gestor.eliminarSolicitud(id);

                System.out.println(
                        "Solicitud eliminada correctamente."
                );

            } else {

                System.out.println(
                        "Operación cancelada."
                );
            }

        } catch (SolicitudNoEncontradaException e) {

            System.out.println(
                    e.getMessage()
            );
        }
    }

    // =========================================================
    // FINALIZAR SOLICITUD
    // =========================================================

    private void finalizarSolicitud() {

        System.out.println();
        System.out.println(
                "--- FINALIZAR SOLICITUD ---"
        );

        int id = leerEntero(
                "ID de la solicitud: "
        );

        try {

            boolean finalizada =
                    gestor.finalizarSolicitud(id);

            if (finalizada) {

                System.out.println(
                        "Solicitud finalizada correctamente."
                );

            } else {

                System.out.println(
                        "La solicitud ya se encuentra cerrada."
                );
            }

        } catch (SolicitudNoEncontradaException e) {

            System.out.println(
                    e.getMessage()
            );
        }
    }

    // =========================================================
    // REGISTRAR VALORACIÓN
    // =========================================================

    private void registrarValoracion() {

        System.out.println();
        System.out.println(
                "--- REGISTRAR VALORACIÓN ---"
        );

        int id = leerEntero(
                "ID de la solicitud: "
        );

        int nota = leerEntero(
                "Valoración (1 a 5): "
        );

        try {

            boolean registrada =
                    gestor.registrarValoracion(
                            id,
                            nota
                    );

            if (registrada) {

                System.out.println(
                        "Valoración registrada correctamente."
                );

            } else {

                System.out.println(
                        "No se puede registrar la valoración."
                        + " La solicitud debe estar cerrada"
                        + " y la nota debe estar entre 1 y 5."
                );
            }

        } catch (SolicitudNoEncontradaException e) {

            System.out.println(
                    e.getMessage()
            );
        }
    }

    // =========================================================
    // MENÚ REPORTES
    // =========================================================

    private void menuReportes() {

        int opcion;

        do {

            System.out.println();
            System.out.println("--------------------------------------");
            System.out.println("       REPORTES Y CONSULTAS");
            System.out.println("--------------------------------------");
            System.out.println("1. Consultar satisfacción");
            System.out.println("2. Detectar solicitudes prioritarias");
            System.out.println("0. Volver");
            System.out.println("--------------------------------------");

            opcion = leerEntero(
                    "Opción: "
            );

            switch (opcion) {

                case 1:
                    consultarSatisfaccion();
                    break;

                case 2:
                    detectarPrioritarias();
                    break;

                case 0:
                    break;

                default:
                    System.out.println(
                            "Opción no válida."
                    );
            }

        } while (opcion != 0);
    }

    // =========================================================
    // SATISFACCIÓN
    // =========================================================

    private void consultarSatisfaccion() {

        System.out.println();
        System.out.println(
                "--- SATISFACCIÓN ---"
        );

        double promedio =
                gestor.calcularPromedio();

        System.out.printf(
                "Promedio de satisfacción: %.2f%n",
                promedio
        );
    }

    // =========================================================
    // SOLICITUDES PRIORITARIAS
    // =========================================================

    private void detectarPrioritarias() {

        System.out.println();
        System.out.println(
                "--- SOLICITUDES PRIORITARIAS ---"
        );

        List<Solicitud> prioritarias =
                gestor.obtenerSolicitudesPrioritarias();

        if (prioritarias.isEmpty()) {

            System.out.println(
                    "No existen solicitudes prioritarias."
            );

            return;
        }

        for (Solicitud solicitud : prioritarias) {

            System.out.println();

            solicitud.mostrar();

            System.out.println(
                    "--------------------------------------"
            );
        }
    }

    // =========================================================
    // LECTURA DE ENTEROS
    // =========================================================

    private int leerEntero(String mensaje) {

        while (true) {

            System.out.print(mensaje);

            String entrada =
                    scanner.nextLine();

            try {

                return Integer.parseInt(entrada);

            } catch (NumberFormatException e) {

                System.out.println(
                        "Ingrese un número válido."
                );
            }
        }
    }
}