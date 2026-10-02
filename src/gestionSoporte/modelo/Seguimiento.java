package gestionSoporte.modelo;

/**
 * Representa un registro de seguimiento asociado a una solicitud.
 */
public class Seguimiento {

    private String descripcion;
    private String fecha;

    public Seguimiento(String descripcion, String fecha) {
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return fecha + " - " + descripcion;
    }
}