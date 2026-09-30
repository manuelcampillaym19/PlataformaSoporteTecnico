package gestionSoporte;

/**
 * Representa una solicitud de atención realizada por un usuario.
 */
public class Solicitud extends ElementoSoporte {

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

        if (usuario != null) {
            usuario.agregarSolicitud(this);
        }
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

    public void cerrar() {
        if ("Pendiente".equalsIgnoreCase(estado)) {
            estado = "Cerrada";
            momentoCierre = System.currentTimeMillis();

            tiempoAtencion = (int) ((momentoCierre - momentoCreacion) / 1000);
        }
    }

    public boolean valorar(int nota) {
        if ("Cerrada".equalsIgnoreCase(estado)
                && nota >= 1
                && nota <= 5) {

            valoracion = nota;
            return true;
        }

        return false;
    }

    /*
     * Sobrecarga de métodos:
     * mostrar() utiliza un prefijo vacío.
     */
    public void mostrar() {
        mostrar("");
    }

    /*
     * Sobrecarga de métodos:
     * permite mostrar la solicitud utilizando un prefijo.
     */
    public void mostrar(String prefijo) {
        System.out.println(prefijo + "ID Solicitud: " + idSolicitud);
        System.out.println(prefijo + "Detalle: " + detalle);
        System.out.println(prefijo + "Estado: " + estado);
        System.out.println(prefijo + "Tiempo de atención: "
                + tiempoAtencion + " segundos");
        System.out.println(prefijo + "Valoración: " + valoracion);

        if (usuario != null) {
            System.out.println(prefijo + "Usuario: "
                    + usuario.getNombre()
                    + " (ID: " + usuario.getId() + ")");
        }
    }

    /*
     * SIA-6: sobrescritura de un método heredado.
     */
    @Override
    public void mostrarInformacion() {
        mostrar();
    }

    @Override
    public String toString() {
        String nombreUsuario;

        if (usuario != null) {
            nombreUsuario = usuario.getNombre();
        } else {
            nombreUsuario = "Sin usuario";
        }

        return "ID Solicitud: " + idSolicitud
                + "\nDetalle: " + detalle
                + "\nEstado: " + estado
                + "\nTiempo de atención: "
                + tiempoAtencion + " segundos"
                + "\nValoración: " + valoracion
                + "\nUsuario: " + nombreUsuario;
    }
}