package gestionSoporte;

/**
 * Representa una solicitud de reparación realizada para un usuario.
 */
public class Solicitud {

    private static int siguienteId = 1;

    private int idSolicitud;
    private String detalle;
    private String estado;
    private int tiempoAtencion;
    private Usuario usuario;
    private long momentoCreacion;
    private long momentoCierre;
    private int valoracion;

    public Solicitud(String detalle, Usuario usuario) {
        this.idSolicitud = siguienteId++;
        this.detalle = detalle;
        this.estado = "Pendiente";
        this.tiempoAtencion = 0;
        this.usuario = usuario;
        this.momentoCreacion = System.currentTimeMillis();
        this.momentoCierre = 0;
        this.valoracion = 0;

        usuario.agregarSolicitud(this);
    }

    public static int getSiguienteId() {
        return siguienteId;
    }

    public static void setSiguienteId(int siguienteId) {
        Solicitud.siguienteId = siguienteId;
    }

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

    public int getTiempoAtencion() {
        return tiempoAtencion;
    }

    public void setTiempoAtencion(int tiempoAtencion) {
        this.tiempoAtencion = tiempoAtencion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public long getMomentoCreacion() {
        return momentoCreacion;
    }

    public void setMomentoCreacion(long momentoCreacion) {
        this.momentoCreacion = momentoCreacion;
    }

    public long getMomentoCierre() {
        return momentoCierre;
    }

    public void setMomentoCierre(long momentoCierre) {
        this.momentoCierre = momentoCierre;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    /**
     * Finaliza la solicitud y calcula el tiempo de atención.
     */
    public void cerrar() {

        if ("Pendiente".equalsIgnoreCase(estado)) {

            estado = "Cerrada";

            momentoCierre = System.currentTimeMillis();

            tiempoAtencion = (int) (
                    (momentoCierre - momentoCreacion) / 1000
            );
        }
    }

    /**
     * Registra una valoración entre 1 y 5.
     *
     * @param nota valoración entregada
     * @return true si la valoración fue registrada correctamente
     */
    public boolean valorar(int nota) {

        if (!"Cerrada".equalsIgnoreCase(estado)) {
            return false;
        }

        if (nota < 1 || nota > 5) {
            return false;
        }

        valoracion = nota;

        return true;
    }

    // Sobrecarga de métodos

    public void mostrar() {

        System.out.println(
                "Solicitud #" + idSolicitud
        );

        System.out.println(
                "Cliente: " + usuario.getNombre()
        );

        System.out.println(
                "Estado: " + estado
        );

        System.out.println(
                "Detalle: " + detalle
        );

        if (valoracion > 0) {
            System.out.println(
                    "Valoración: " + valoracion + "/5"
            );
        } else {
            System.out.println(
                    "Valoración: Sin valorar"
            );
        }

        System.out.println(
                "--------------------------------------"
        );
    }

    public void mostrar(String prefijo) {

        System.out.println(prefijo);

        mostrar();
    }

    public void mostrar(boolean detalleMostrar) {

        if (detalleMostrar) {

            System.out.println(
                    "=== INFORMACIÓN DE LA SOLICITUD ==="
            );

            System.out.println(
                    "ID: " + idSolicitud
            );

            System.out.println(
                    "Cliente: " + usuario.getNombre()
            );

            System.out.println(
                    "Detalle: " + detalle
            );

            System.out.println(
                    "Estado: " + estado
            );

            System.out.println(
                    "Tiempo de atención: "
                    + tiempoAtencion
                    + " segundos"
            );

            if (valoracion > 0) {
                System.out.println(
                        "Valoración: "
                        + valoracion
                        + "/5"
                );
            } else {
                System.out.println(
                        "Valoración: Sin valorar"
                );
            }

        } else {

            mostrar();
        }
    }

    @Override
    public String toString() {

        String texto = "Solicitud #"
                + idSolicitud
                + "\nCliente: "
                + usuario.getNombre()
                + "\nEstado: "
                + estado
                + "\nDetalle: "
                + detalle;

        if (valoracion > 0) {

            texto += "\nValoración: "
                    + valoracion
                    + "/5";

        } else {

            texto += "\nValoración: Sin valorar";
        }

        return texto;
    }
}