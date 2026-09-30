package gestionSoporte;

/**
 * Clase base abstracta para las personas registradas en el sistema.
 */
public abstract class personaBase {

    private String nombre;
    private String correo;

    public personaBase(String nombre, String correo) {
        this.nombre = nombre;
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Cada tipo de persona define cómo mostrar su información.
     */
    public abstract void mostrarInformacion();

    @Override
    public String toString() {
        return "Nombre: " + nombre
                + "\nCorreo: " + correo;
    }
}