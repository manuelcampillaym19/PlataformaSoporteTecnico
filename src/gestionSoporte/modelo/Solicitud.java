package gestionSoporte.modelo;

import gestionSoporte.modelo.Usuario;
import java.util.ArrayList;

/**
 * Representa una solicitud de atención realizada por un usuario.
 * Cada solicitud puede contener varios registros de seguimiento.
 */
public class Solicitud extends ElementoSoporte {

    private static int siguienteId = 1;

    private int idSolicitud;
    private String detalle;
    private String estado;
    private long tiempoAtencion;
    private Usuario usuario;
    private long fechaCreacion;
    private long fechaCierre;
    private int valoracion;

    /**
     * Colección anidada de seguimientos asociados a la solicitud.
     */
    private ArrayList<Seguimiento> seguimientos;

    /**
     * Crea una nueva solicitud para un usuario.
     *
     * @param detalle descripción de la solicitud
     * @param usuario usuario que realiza la solicitud
     */
    public Solicitud(String detalle, Usuario usuario) {

        this.idSolicitud = siguienteId++;
        this.detalle = detalle;
        this.estado = "Pendiente";
        this.tiempoAtencion = 0;
        this.usuario = usuario;
        this.fechaCreacion = System.currentTimeMillis();
        this.fechaCierre = 0;
        this.valoracion = 0;
        this.seguimientos = new ArrayList<>();

        if (usuario != null) {
            usuario.agregarSolicitud(this);
        }
    }

    // =========================================================
    // GETTERS Y SETTERS
    // =========================================================

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {

        this.idSolicitud = idSolicitud;

        if (idSolicitud >= siguienteId) {
            siguienteId = idSolicitud + 1;
        }
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public long getTiempoAtencion() {
        return tiempoAtencion;
    }

    public void setTiempoAtencion(long tiempoAtencion) {
        this.tiempoAtencion = tiempoAtencion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    // =========================================================
    // FECHAS
    // =========================================================

    public long getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(long fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public long getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(long fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    /*
     * Estos métodos mantienen compatibilidad con archivoDatos.java,
     * que utiliza los nombres momentoCreacion y momentoCierre.
     */

    public long getMomentoCreacion() {
        return fechaCreacion;
    }

    public void setMomentoCreacion(long momentoCreacion) {
        this.fechaCreacion = momentoCreacion;
    }

    public long getMomentoCierre() {
        return fechaCierre;
    }

    public void setMomentoCierre(long momentoCierre) {
        this.fechaCierre = momentoCierre;
    }

    // =========================================================
    // VALORACIÓN
    // =========================================================

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    /**
     * Registra una valoración entre 1 y 5.
     *
     * @param valoracion valoración entregada
     * @return true si la valoración fue válida
     */
    public boolean valorar(int valoracion) {

        if (valoracion >= 1 && valoracion <= 5) {

            this.valoracion = valoracion;
            return true;
        }

        return false;
    }

    // =========================================================
    // SEGUIMIENTOS
    // =========================================================

    /**
     * Retorna una copia de la colección de seguimientos.
     *
     * @return copia de los seguimientos
     */
    public ArrayList<Seguimiento> getSeguimientos() {

        return new ArrayList<>(seguimientos);
    }

    /**
     * Reemplaza los seguimientos utilizando una copia.
     *
     * @param seguimientos nuevos seguimientos
     */
    public void setSeguimientos(
            ArrayList<Seguimiento> seguimientos) {

        if (seguimientos == null) {

            this.seguimientos =
                    new ArrayList<>();

        } else {

            this.seguimientos =
                    new ArrayList<>(seguimientos);
        }
    }

    /**
     * Agrega un seguimiento a la solicitud.
     *
     * @param seguimiento seguimiento que se desea agregar
     */
    public void agregarSeguimiento(
            Seguimiento seguimiento) {

        if (seguimiento != null) {
            seguimientos.add(seguimiento);
        }
    }

    /**
     * Elimina un seguimiento de la solicitud.
     *
     * @param seguimiento seguimiento que se desea eliminar
     */
    public void eliminarSeguimiento(
            Seguimiento seguimiento) {

        seguimientos.remove(seguimiento);
    }

    // =========================================================
    // CERRAR SOLICITUD
    // =========================================================

    /**
     * Cierra la solicitud y registra el tiempo de atención.
     */
    public void cerrar() {

        if (!"Cerrada".equalsIgnoreCase(estado)) {

            estado = "Cerrada";

            fechaCierre =
                    System.currentTimeMillis();

            tiempoAtencion =
                    (fechaCierre - fechaCreacion) / 1000;
        }
    }

    // =========================================================
    // SOBRECARGA
    // =========================================================

    /**
     * Muestra la información de la solicitud.
     */
    public void mostrar() {
        mostrar("");
    }

    /**
     * Muestra la información de la solicitud utilizando
     * un prefijo.
     *
     * @param prefijo texto que se agrega al inicio de cada línea
     */
    public void mostrar(String prefijo) {

        System.out.println(
                prefijo
                + "ID Solicitud: "
                + idSolicitud
        );

        System.out.println(
                prefijo
                + "Detalle: "
                + detalle
        );

        System.out.println(
                prefijo
                + "Estado: "
                + estado
        );

        System.out.println(
                prefijo
                + "Tiempo de atención: "
                + tiempoAtencion
                + " segundos"
        );

        System.out.println(
                prefijo
                + "Valoración: "
                + valoracion
        );

        if (usuario != null) {

            System.out.println(
                    prefijo
                    + "Usuario: "
                    + usuario.getNombre()
            );
        }

        if (!seguimientos.isEmpty()) {

            System.out.println(
                    prefijo
                    + "Seguimientos:"
            );

            for (Seguimiento seguimiento
                    : seguimientos) {

                System.out.println(
                        prefijo
                        + "  - "
                        + seguimiento
                );
            }
        }
    }

    // =========================================================
    // SOBRESCRITURA
    // =========================================================

    @Override
    public void mostrarInformacion() {
        mostrar();
    }

    // =========================================================
    // TOSTRING
    // =========================================================

    /**
     * Devuelve una representación textual completa
     * de la solicitud, incluyendo sus seguimientos.
     *
     * @return información de la solicitud
     */
    @Override
    public String toString() {

        StringBuilder texto =
                new StringBuilder();

        texto.append(
                "ID Solicitud: "
        ).append(idSolicitud);

        texto.append(
                "\nDetalle: "
        ).append(detalle);

        texto.append(
                "\nEstado: "
        ).append(estado);

        texto.append(
                "\nTiempo de atención: "
        ).append(tiempoAtencion)
                .append(" segundos");

        texto.append(
                "\nValoración: "
        ).append(valoracion);

        if (usuario != null) {

            texto.append(
                    "\nUsuario: "
            ).append(
                    usuario.getNombre()
            );
        }

        texto.append(
                "\nCantidad de seguimientos: "
        ).append(
                seguimientos.size()
        );

        if (!seguimientos.isEmpty()) {

            texto.append(
                    "\nSeguimientos:"
            );

            for (Seguimiento seguimiento
                    : seguimientos) {

                texto.append(
                        "\n- "
                ).append(
                        seguimiento
                );
            }
        }

        return texto.toString();
    }
}