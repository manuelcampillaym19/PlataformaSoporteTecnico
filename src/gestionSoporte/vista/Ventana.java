package gestionSoporte.vista;

import gestionSoporte.excepciones.SolicitudNoEncontradaException;
import gestionSoporte.excepciones.UsuarioNoEncontradoException;
import gestionSoporte.gestor.Gestor;
import gestionSoporte.modelo.Usuario;
import gestionSoporte.modelo.Solicitud;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Ventana {

    private Gestor gestor;
    private JFrame ventanaPrincipal;

    public Ventana(Gestor gestor) {
        this.gestor = gestor;

        ventanaPrincipal = new JFrame(
                "Plataforma de Soporte"
        );

        ventanaPrincipal.setSize(500, 400);
        ventanaPrincipal.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );
    }

    public Gestor getGestor() {
        return gestor;
    }

    public void setGestor(Gestor gestor) {
        this.gestor = gestor;
    }

    public JFrame getVentanaPrincipal() {
        return ventanaPrincipal;
    }

    public void setVentanaPrincipal(
            JFrame ventanaPrincipal) {

        this.ventanaPrincipal = ventanaPrincipal;
    }

    // =========================================================
    // INICIO
    // =========================================================

    public void iniciar() {

        boolean continuar = true;

        while (continuar) {

            String[] opciones = {
                "Gestión de usuarios",
                "Gestión de solicitudes",
                "Reportes y consultas",
                "Salir"
            };

            int opcion = JOptionPane.showOptionDialog(
                    ventanaPrincipal,
                    "Seleccione una sección:",
                    "Plataforma de Soporte",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            switch (opcion) {

                case 0:
                    menuUsuarios();
                    break;

                case 1:
                    menuSolicitudes();
                    break;

                case 2:
                    menuReportes();
                    break;

                case 3:
                case JOptionPane.CLOSED_OPTION:
                    continuar = false;
                    break;

                default:
                    continuar = false;
            }
        }
    }

    // =========================================================
    // USUARIOS
    // =========================================================

    private void menuUsuarios() {

        boolean continuar = true;

        while (continuar) {

            String[] opciones = {
                "Registrar usuario",
                "Consultar usuarios",
                "Localizar usuario",
                "Modificar usuario",
                "Dar de baja usuario",
                "Volver"
            };

            int opcion = JOptionPane.showOptionDialog(
                    ventanaPrincipal,
                    "Seleccione una operación:",
                    "Gestión de Usuarios",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            switch (opcion) {

                case 0:
                    registrarUsuario();
                    break;

                case 1:
                    consultarUsuarios();
                    break;

                case 2:
                    localizarUsuario();
                    break;

                case 3:
                    modificarUsuario();
                    break;

                case 4:
                    eliminarUsuario();
                    break;

                case 5:
                case JOptionPane.CLOSED_OPTION:
                    continuar = false;
                    break;

                default:
                    continuar = false;
            }
        }
    }

    // =========================================================
    // REGISTRAR USUARIO
    // =========================================================

    private void registrarUsuario() {

        String nombre = JOptionPane.showInputDialog(
                ventanaPrincipal,
                "Ingrese el nombre del usuario:",
                "Registrar usuario",
                JOptionPane.QUESTION_MESSAGE
        );

        if (nombre == null) {
            return;
        }

        nombre = nombre.trim();

        if (nombre.isEmpty()) {

            JOptionPane.showMessageDialog(
                    ventanaPrincipal,
                    "El nombre no puede estar vacío.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        String correo;

        while (true) {

            correo = JOptionPane.showInputDialog(
                    ventanaPrincipal,
                    "Ingrese el correo electrónico:",
                    "Registrar usuario",
                    JOptionPane.QUESTION_MESSAGE
            );

            if (correo == null) {
                return;
            }

            correo = correo.trim();

            if (gestor.correoValido(correo)) {
                break;
            }

            JOptionPane.showMessageDialog(
                    ventanaPrincipal,
                    "Correo inválido.\n"
                    + "Debe respetar el formato:\n"
                    + "usuario@dominio.extension",
                    "Correo inválido",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        boolean registrado =
                gestor.agregarUsuario(
                        nombre,
                        correo
                );

        if (registrado) {

            Usuario ultimo =
                    obtenerUltimoUsuario();

            String mensaje =
                    "Usuario registrado correctamente.";

            if (ultimo != null) {

                mensaje +=
                        "\nID asignado: "
                        + ultimo.getId();
            }

            JOptionPane.showMessageDialog(
                    ventanaPrincipal,
                    mensaje,
                    "Registro exitoso",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } else {

            JOptionPane.showMessageDialog(
                    ventanaPrincipal,
                    "No fue posible registrar el usuario.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
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

        List<Usuario> usuarios =
                gestor.listarUsuarios();

        if (usuarios.isEmpty()) {

            JOptionPane.showMessageDialog(
                    ventanaPrincipal,
                    "No existen usuarios registrados.",
                    "Usuarios",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        StringBuilder texto =
                new StringBuilder();

        texto.append(
                "--- USUARIOS REGISTRADOS ---\n\n"
        );

        for (Usuario usuario : usuarios) {

            texto.append(
                    usuario.toString()
            );

            texto.append(
                    "\n--------------------------------\n"
            );
        }

        mostrarTexto(
                "Usuarios registrados",
                texto.toString()
        );
    }

    // =========================================================
    // LOCALIZAR USUARIO
    // =========================================================

    private void localizarUsuario() {

        Integer id = pedirEntero(
                "Ingrese el ID del usuario:"
        );

        if (id == null) {
            return;
        }

        try {

            Usuario usuario =
                    gestor.buscarUsuario(id);

            JOptionPane.showMessageDialog(
                    ventanaPrincipal,
                    usuario.toString(),
                    "Usuario encontrado",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (UsuarioNoEncontradoException e) {

            JOptionPane.showMessageDialog(
                    ventanaPrincipal,
                    e.getMessage(),
                    "Usuario no encontrado",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // MODIFICAR USUARIO
    // =========================================================

    private void modificarUsuario() {

        Integer id = pedirEntero(
                "Ingrese el ID del usuario:"
        );

        if (id == null) {
            return;
        }

        try {

            Usuario usuario =
                    gestor.buscarUsuario(id);

            String nombre =
                    JOptionPane.showInputDialog(
                            ventanaPrincipal,
                            "Nombre actual: "
                            + usuario.getNombre()
                            + "\n\nIngrese el nuevo nombre:",
                            "Modificar usuario",
                            JOptionPane.QUESTION_MESSAGE
                    );

            if (nombre == null) {
                return;
            }

            nombre = nombre.trim();

            if (nombre.isEmpty()) {

                JOptionPane.showMessageDialog(
                        ventanaPrincipal,
                        "El nombre no puede estar vacío.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            String correo;

            while (true) {

                correo = JOptionPane.showInputDialog(
                        ventanaPrincipal,
                        "Correo actual: "
                        + usuario.getCorreo()
                        + "\n\nIngrese el nuevo correo:",
                        "Modificar usuario",
                        JOptionPane.QUESTION_MESSAGE
                );

                if (correo == null) {
                    return;
                }

                correo = correo.trim();

                if (gestor.correoValido(correo)) {
                    break;
                }

                JOptionPane.showMessageDialog(
                        ventanaPrincipal,
                        "Correo inválido.\n"
                        + "Debe respetar el formato:\n"
                        + "usuario@dominio.extension",
                        "Correo inválido",
                        JOptionPane.ERROR_MESSAGE
                );
            }

            boolean modificado =
                    gestor.modificarUsuario(
                            id,
                            nombre,
                            correo
                    );

            if (modificado) {

                JOptionPane.showMessageDialog(
                        ventanaPrincipal,
                        "Usuario modificado correctamente.",
                        "Modificación exitosa",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } else {

                JOptionPane.showMessageDialog(
                        ventanaPrincipal,
                        "No fue posible modificar el usuario.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (UsuarioNoEncontradoException e) {

            JOptionPane.showMessageDialog(
                    ventanaPrincipal,
                    e.getMessage(),
                    "Usuario no encontrado",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // ELIMINAR USUARIO
    // =========================================================

    private void eliminarUsuario() {

        Integer id = pedirEntero(
                "Ingrese el ID del usuario:"
        );

        if (id == null) {
            return;
        }

        try {

            Usuario usuario =
                    gestor.buscarUsuario(id);

            int confirmacion =
                    JOptionPane.showConfirmDialog(
                            ventanaPrincipal,
                            "¿Desea eliminar al usuario?\n\n"
                            + usuario.getNombre()
                            + "\n"
                            + usuario.getCorreo(),
                            "Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );

            if (confirmacion
                    == JOptionPane.YES_OPTION) {

                gestor.eliminarUsuario(id);

                JOptionPane.showMessageDialog(
                        ventanaPrincipal,
                        "Usuario eliminado correctamente.",
                        "Eliminación exitosa",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

        } catch (UsuarioNoEncontradoException e) {

            JOptionPane.showMessageDialog(
                    ventanaPrincipal,
                    e.getMessage(),
                    "Usuario no encontrado",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // SOLICITUDES
    // =========================================================

    private void menuSolicitudes() {

        boolean continuar = true;

        while (continuar) {

            String[] opciones = {
                "Registrar solicitud",
                "Consultar solicitudes",
                "Localizar solicitud",
                "Modificar solicitud",
                "Dar de baja solicitud",
                "Finalizar solicitud",
                "Registrar valoración",
                "Registrar seguimiento",
                "Volver"
            };

            int opcion = JOptionPane.showOptionDialog(
                    ventanaPrincipal,
                    "Seleccione una operación:",
                    "Gestión de Solicitudes",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            switch (opcion) {

                case 0:
                    registrarSolicitud();
                    break;

                case 1:
                    consultarSolicitudes();
                    break;

                case 2:
                    localizarSolicitud();
                    break;

                case 3:
                    modificarSolicitud();
                    break;

                case 4:
                    eliminarSolicitud();
                    break;

                case 5:
                    finalizarSolicitud();
                    break;

                case 6:
                    registrarValoracion();
                    break;

                case 7:
                    registrarSeguimiento();
                    break;

                case 8:
                case JOptionPane.CLOSED_OPTION:
                    continuar = false;
                    break;

                default:
                    continuar = false;
            }
        }
    }

    // =========================================================
    // REGISTRAR SOLICITUD
    // =========================================================

    private void registrarSolicitud() {

        Integer idUsuario = pedirEntero(
                "Ingrese el ID del usuario:"
        );

        if (idUsuario == null) {
            return;
        }

        try {

            Usuario usuario =
                    gestor.buscarUsuario(idUsuario);

            String detalle =
                    JOptionPane.showInputDialog(
                            ventanaPrincipal,
                            "Usuario: "
                            + usuario.getNombre()
                            + "\n\nIngrese la descripción:",
                            "Registrar solicitud",
                            JOptionPane.QUESTION_MESSAGE
                    );

            if (detalle == null) {
                return;
            }

            detalle = detalle.trim();

            if (detalle.isEmpty()) {

                JOptionPane.showMessageDialog(
                        ventanaPrincipal,
                        "La descripción no puede estar vacía.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            boolean registrada =
                    gestor.agregarSolicitud(
                            idUsuario,
                            detalle
                    );

            if (registrada) {

                Solicitud ultima =
                        obtenerUltimaSolicitud();

                String mensaje =
                        "Solicitud registrada correctamente.";

                if (ultima != null) {

                    mensaje +=
                            "\nID de solicitud asignado: "
                            + ultima.getIdSolicitud();
                }

                JOptionPane.showMessageDialog(
                        ventanaPrincipal,
                        mensaje,
                        "Registro exitoso",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } else {

                JOptionPane.showMessageDialog(
                        ventanaPrincipal,
                        "No fue posible registrar la solicitud.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (UsuarioNoEncontradoException e) {

            JOptionPane.showMessageDialog(
                    ventanaPrincipal,
                    e.getMessage(),
                    "Usuario no encontrado",
                    JOptionPane.ERROR_MESSAGE
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

        List<Solicitud> solicitudes =
                gestor.listarSolicitudes();

        if (solicitudes.isEmpty()) {

            JOptionPane.showMessageDialog(
                    ventanaPrincipal,
                    "No existen solicitudes registradas.",
                    "Solicitudes",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        StringBuilder texto =
                new StringBuilder();

        texto.append(
                "--- SOLICITUDES REGISTRADAS ---\n\n"
        );

        for (Solicitud solicitud : solicitudes) {

            texto.append(
                    solicitud.toString()
            );

            texto.append(
                    "\n--------------------------------\n"
            );
        }

        mostrarTexto(
                "Solicitudes registradas",
                texto.toString()
        );
    }

    // =========================================================
    // LOCALIZAR SOLICITUD
    // =========================================================

    private void localizarSolicitud() {

        Integer id = pedirEntero(
                "Ingrese el ID de la solicitud:"
        );

        if (id == null) {
            return;
        }

        try {

            Solicitud solicitud =
                    gestor.buscarSolicitud(id);

            JOptionPane.showMessageDialog(
                    ventanaPrincipal,
                    solicitud.toString(),
                    "Solicitud encontrada",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (SolicitudNoEncontradaException e) {

            JOptionPane.showMessageDialog(
                    ventanaPrincipal,
                    e.getMessage(),
                    "Solicitud no encontrada",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // REGISTRAR SEGUIMIENTO
    // =========================================================

    private void registrarSeguimiento() {

        Integer id = pedirEntero(
                "Ingrese el ID de la solicitud:"
        );

        if (id == null) {
            return;
        }

        try {

            gestor.buscarSolicitud(id);

            String descripcion =
                    JOptionPane.showInputDialog(
                            ventanaPrincipal,
                            "Ingrese la descripción del seguimiento:",
                            "Registrar seguimiento",
                            JOptionPane.QUESTION_MESSAGE
                    );

            if (descripcion == null) {
                return;
            }

            descripcion = descripcion.trim();

            if (descripcion.isEmpty()) {

                JOptionPane.showMessageDialog(
                        ventanaPrincipal,
                        "La descripción no puede estar vacía.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            String fecha =
                    JOptionPane.showInputDialog(
                            ventanaPrincipal,
                            "Ingrese la fecha del seguimiento:",
                            "Registrar seguimiento",
                            JOptionPane.QUESTION_MESSAGE
                    );

            if (fecha == null) {
                return;
            }

            fecha = fecha.trim();

            if (fecha.isEmpty()) {

                JOptionPane.showMessageDialog(
                        ventanaPrincipal,
                        "La fecha no puede estar vacía.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            gestor.agregarSeguimiento(
                    id,
                    descripcion,
                    fecha
            );

            JOptionPane.showMessageDialog(
                    ventanaPrincipal,
                    "Seguimiento registrado correctamente.",
                    "Registro exitoso",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (SolicitudNoEncontradaException e) {

            JOptionPane.showMessageDialog(
                    ventanaPrincipal,
                    e.getMessage(),
                    "Solicitud no encontrada",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // MODIFICAR SOLICITUD
    // =========================================================

    private void modificarSolicitud() {

        Integer id = pedirEntero(
                "Ingrese el ID de la solicitud:"
        );

        if (id == null) {
            return;
        }

        try {

            Solicitud solicitud =
                    gestor.buscarSolicitud(id);

            String detalle =
                    JOptionPane.showInputDialog(
                            ventanaPrincipal,
                            "Descripción actual:\n"
                            + solicitud.getDetalle()
                            + "\n\nIngrese la nueva descripción:",
                            "Modificar solicitud",
                            JOptionPane.QUESTION_MESSAGE
                    );

            if (detalle == null) {
                return;
            }

            detalle = detalle.trim();

            if (detalle.isEmpty()) {

                JOptionPane.showMessageDialog(
                        ventanaPrincipal,
                        "La descripción no puede estar vacía.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            gestor.modificarSolicitud(
                    id,
                    detalle
            );

            JOptionPane.showMessageDialog(
                    ventanaPrincipal,
                    "Solicitud modificada correctamente.",
                    "Modificación exitosa",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (SolicitudNoEncontradaException e) {

            JOptionPane.showMessageDialog(
                    ventanaPrincipal,
                    e.getMessage(),
                    "Solicitud no encontrada",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // ELIMINAR SOLICITUD
    // =========================================================

    private void eliminarSolicitud() {

        Integer id = pedirEntero(
                "Ingrese el ID de la solicitud:"
        );

        if (id == null) {
            return;
        }

        try {

            Solicitud solicitud =
                    gestor.buscarSolicitud(id);

            int confirmacion =
                    JOptionPane.showConfirmDialog(
                            ventanaPrincipal,
                            "¿Desea eliminar esta solicitud?\n\n"
                            + solicitud.getDetalle(),
                            "Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );

            if (confirmacion
                    == JOptionPane.YES_OPTION) {

                gestor.eliminarSolicitud(id);

                JOptionPane.showMessageDialog(
                        ventanaPrincipal,
                        "Solicitud eliminada correctamente.",
                        "Eliminación exitosa",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

        } catch (SolicitudNoEncontradaException e) {

            JOptionPane.showMessageDialog(
                    ventanaPrincipal,
                    e.getMessage(),
                    "Solicitud no encontrada",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // FINALIZAR SOLICITUD
    // =========================================================

    private void finalizarSolicitud() {

        Integer id = pedirEntero(
                "Ingrese el ID de la solicitud:"
        );

        if (id == null) {
            return;
        }

        try {

            boolean finalizada =
                    gestor.finalizarSolicitud(id);

            if (finalizada) {

                JOptionPane.showMessageDialog(
                        ventanaPrincipal,
                        "Solicitud finalizada correctamente.",
                        "Solicitud finalizada",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } else {

                JOptionPane.showMessageDialog(
                        ventanaPrincipal,
                        "La solicitud ya se encuentra cerrada.",
                        "Información",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

        } catch (SolicitudNoEncontradaException e) {

            JOptionPane.showMessageDialog(
                    ventanaPrincipal,
                    e.getMessage(),
                    "Solicitud no encontrada",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // REGISTRAR VALORACIÓN
    // =========================================================

    private void registrarValoracion() {

        Integer id = pedirEntero(
                "Ingrese el ID de la solicitud:"
        );

        if (id == null) {
            return;
        }

        Integer nota = pedirEntero(
                "Ingrese la valoración (1 a 5):"
        );

        if (nota == null) {
            return;
        }

        try {

            boolean registrada =
                    gestor.registrarValoracion(
                            id,
                            nota
                    );

            if (registrada) {

                JOptionPane.showMessageDialog(
                        ventanaPrincipal,
                        "Valoración registrada correctamente.",
                        "Valoración",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } else {

                JOptionPane.showMessageDialog(
                        ventanaPrincipal,
                        "No se puede registrar la valoración.\n"
                        + "La solicitud debe estar cerrada "
                        + "y la nota debe estar entre 1 y 5.",
                        "Valoración inválida",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (SolicitudNoEncontradaException e) {

            JOptionPane.showMessageDialog(
                    ventanaPrincipal,
                    e.getMessage(),
                    "Solicitud no encontrada",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // REPORTES
    // =========================================================

    private void menuReportes() {

        boolean continuar = true;

        while (continuar) {

            String[] opciones = {
                "Consultar satisfacción",
                "Detectar solicitudes prioritarias",
                "Volver"
            };

            int opcion = JOptionPane.showOptionDialog(
                    ventanaPrincipal,
                    "Seleccione una consulta:",
                    "Reportes y Consultas",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            switch (opcion) {

                case 0:
                    consultarSatisfaccion();
                    break;

                case 1:
                    detectarPrioritarias();
                    break;

                case 2:
                case JOptionPane.CLOSED_OPTION:
                    continuar = false;
                    break;

                default:
                    continuar = false;
            }
        }
    }

    // =========================================================
    // SATISFACCIÓN
    // =========================================================

    private void consultarSatisfaccion() {

        double promedio =
                gestor.calcularPromedio();

        JOptionPane.showMessageDialog(
                ventanaPrincipal,
                String.format(
                        "Promedio de satisfacción: %.2f",
                        promedio
                ),
                "Satisfacción",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =========================================================
    // SOLICITUDES PRIORITARIAS
    // =========================================================

    private void detectarPrioritarias() {

        List<Solicitud> prioritarias =
                gestor.obtenerSolicitudesPrioritarias();

        if (prioritarias.isEmpty()) {

            JOptionPane.showMessageDialog(
                    ventanaPrincipal,
                    "No existen solicitudes prioritarias.",
                    "Solicitudes prioritarias",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        StringBuilder texto =
                new StringBuilder();

        texto.append(
                "--- SOLICITUDES PRIORITARIAS ---\n\n"
        );

        for (Solicitud solicitud : prioritarias) {

            texto.append(
                    solicitud.toString()
            );

            texto.append(
                    "\n--------------------------------\n"
            );
        }

        mostrarTexto(
                "Solicitudes prioritarias",
                texto.toString()
        );
    }

    // =========================================================
    // PEDIR ENTERO
    // =========================================================

    private Integer pedirEntero(String mensaje) {

        while (true) {

            String entrada =
                    JOptionPane.showInputDialog(
                            ventanaPrincipal,
                            mensaje,
                            "Entrada de datos",
                            JOptionPane.QUESTION_MESSAGE
                    );

            if (entrada == null) {
                return null;
            }

            try {

                return Integer.parseInt(
                        entrada.trim()
                );

            } catch (NumberFormatException e) {

                JOptionPane.showMessageDialog(
                        ventanaPrincipal,
                        "Debe ingresar un número válido.",
                        "Entrada inválida",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    // =========================================================
    // MOSTRAR TEXTO CON DESPLAZAMIENTO
    // =========================================================

    private void mostrarTexto(
            String titulo,
            String texto) {

        JTextArea areaTexto =
                new JTextArea(texto);

        areaTexto.setEditable(false);
        areaTexto.setLineWrap(false);
        areaTexto.setWrapStyleWord(false);

        JScrollPane scroll =
                new JScrollPane(areaTexto);

        scroll.setPreferredSize(
                new java.awt.Dimension(500, 400)
        );

        JOptionPane.showMessageDialog(
                ventanaPrincipal,
                scroll,
                titulo,
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}