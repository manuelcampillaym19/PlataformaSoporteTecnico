package gestionSoporte;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Gestor {

    private HashMap<Integer, Usuario> usuarios;

    public Gestor() {
        usuarios = archivoDatos.cargarUsuarios();
        archivoDatos.cargarSolicitudes(usuarios);

        if (!archivoDatos.existenArchivosDeDatos()) {
            cargarDatosIniciales();
            guardarDatos();
        }
    }

    public HashMap<Integer, Usuario> getUsuarios() {
        return new HashMap<>(usuarios);
    }

    public void setUsuarios(HashMap<Integer, Usuario> usuarios) {
        this.usuarios = new HashMap<>(usuarios);
    }

    // =========================================================
    // USUARIOS
    // =========================================================

    public boolean agregarUsuario(String nombre, String correo) {

        if (!correoValido(correo)) {
            return false;
        }

        Usuario usuario = new Usuario(nombre, correo);

        if (usuarios.containsKey(usuario.getId())) {
            return false;
        }

        usuarios.put(usuario.getId(), usuario);

        return true;
    }

    public Usuario buscarUsuario(int id)
            throws UsuarioNoEncontradoException {

        Usuario usuario = usuarios.get(id);

        if (usuario == null) {
            throw new UsuarioNoEncontradoException(
                    "No existe un usuario con el ID " + id
            );
        }

        return usuario;
    }

    public boolean eliminarUsuario(int id)
            throws UsuarioNoEncontradoException {

        Usuario usuario = buscarUsuario(id);

        usuarios.remove(usuario.getId());

        return true;
    }

    public boolean modificarUsuario(
            int id,
            String nombre,
            String correo)
            throws UsuarioNoEncontradoException {

        if (!correoValido(correo)) {
            return false;
        }

        Usuario usuario = buscarUsuario(id);

        usuario.setNombre(nombre);
        usuario.setCorreo(correo);

        return true;
    }

    public List<Usuario> listarUsuarios() {
        return new ArrayList<>(usuarios.values());
    }

    // =========================================================
    // VALIDACIÓN DE CORREO
    // =========================================================

    public boolean correoValido(String correo) {

        if (correo == null || correo.trim().isEmpty()) {
            return false;
        }

        return correo.matches(
                "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        );
    }

    // =========================================================
    // SOLICITUDES
    // =========================================================

    public boolean agregarSolicitud(
            int idUsuario,
            String detalle)
            throws UsuarioNoEncontradoException {

        Usuario usuario = buscarUsuario(idUsuario);

        Solicitud solicitud = new Solicitud(
                detalle,
                usuario
        );

        return solicitud != null;
    }

    public Solicitud buscarSolicitud(int idSolicitud)
            throws SolicitudNoEncontradaException {

        for (Usuario usuario : usuarios.values()) {

            for (Solicitud solicitud : usuario.getSolicitudes()) {

                if (solicitud.getIdSolicitud() == idSolicitud) {
                    return solicitud;
                }
            }
        }

        throw new SolicitudNoEncontradaException(
                "No existe una solicitud con el ID "
                + idSolicitud
        );
    }

    public boolean modificarSolicitud(
            int idSolicitud,
            String detalle)
            throws SolicitudNoEncontradaException {

        Solicitud solicitud = buscarSolicitud(idSolicitud);

        solicitud.setDetalle(detalle);

        return true;
    }

    public boolean eliminarSolicitud(int idSolicitud)
            throws SolicitudNoEncontradaException {

        Solicitud solicitud = buscarSolicitud(idSolicitud);

        Usuario usuario = solicitud.getUsuario();

        if (usuario != null) {
            usuario.eliminarSolicitud(solicitud);
        }

        return true;
    }

    public List<Solicitud> listarSolicitudes() {

        List<Solicitud> solicitudes = new ArrayList<>();

        for (Usuario usuario : usuarios.values()) {
            solicitudes.addAll(usuario.getSolicitudes());
        }

        return solicitudes;
    }

    // =========================================================
    // OPERACIONES SOBRE SOLICITUDES
    // =========================================================

    public boolean finalizarSolicitud(int idSolicitud)
            throws SolicitudNoEncontradaException {

        Solicitud solicitud = buscarSolicitud(idSolicitud);

        if (!"Pendiente".equalsIgnoreCase(
                solicitud.getEstado())) {

            return false;
        }

        solicitud.cerrar();

        return true;
    }

    public boolean registrarValoracion(
            int idSolicitud,
            int nota)
            throws SolicitudNoEncontradaException {

        Solicitud solicitud = buscarSolicitud(idSolicitud);

        return solicitud.valorar(nota);
    }

    public double calcularPromedio() {

        int suma = 0;
        int cantidad = 0;

        for (Usuario usuario : usuarios.values()) {

            for (Solicitud solicitud : usuario.getSolicitudes()) {

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

    public List<Solicitud> obtenerSolicitudesPrioritarias() {

        List<Solicitud> prioritarias = new ArrayList<>();

        for (Usuario usuario : usuarios.values()) {

            for (Solicitud solicitud : usuario.getSolicitudes()) {

                if ("Pendiente".equalsIgnoreCase(
                        solicitud.getEstado())
                        && solicitud.getTiempoAtencion() >= 180) {

                    prioritarias.add(solicitud);
                }
            }
        }

        return prioritarias;
    }

    // =========================================================
    // PERSISTENCIA
    // =========================================================

    public void guardarDatos() {

        archivoDatos.guardarUsuarios(usuarios);
        archivoDatos.guardarSolicitudes(usuarios);
    }

    // =========================================================
    // DATOS INICIALES
    // =========================================================

    private void cargarDatosIniciales() {

        Usuario juan = new Usuario(
                "Juan Perez",
                "juan.perez@gmail.com"
        );

        Usuario pedro = new Usuario(
                "Pedro Soto",
                "pedro.soto@gmail.com"
        );

        Usuario ana = new Usuario(
                "Ana Morales",
                "ana.morales@gmail.com"
        );

        usuarios.put(juan.getId(), juan);
        usuarios.put(pedro.getId(), pedro);
        usuarios.put(ana.getId(), ana);

        Solicitud solicitud1 = new Solicitud(
                "Teclado del equipo en mal estado",
                juan
        );

        Solicitud solicitud2 = new Solicitud(
                "Monitor no enciende",
                pedro
        );

        solicitud2.setEstado("Cerrada");
        solicitud2.setValoracion(4);

        Solicitud solicitud3 = new Solicitud(
                "Equipo presenta lentitud",
                ana
        );

        if (solicitud1 == null || solicitud3 == null) {
            System.out.println(
                    "Error al crear datos iniciales."
            );
        }
    }
}