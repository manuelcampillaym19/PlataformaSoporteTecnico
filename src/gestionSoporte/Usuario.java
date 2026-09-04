package gestionSoporte;

import java.util.ArrayList;

/**
 * Representa a un usuario registrado en el sistema.
 */
public class Usuario extends personaBase {

    private int id;
    private ArrayList<Solicitud> solicitudes;

    public Usuario(int id, String nombre, String correo) {
        super(nombre, correo);
        this.id = id;
        this.solicitudes = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Solicitud> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(ArrayList<Solicitud> solicitudes) {
        this.solicitudes = solicitudes;
    }

    public void agregarSolicitud(Solicitud solicitud) {
        solicitudes.add(solicitud);
    }

    // Sobrecarga de métodos
    public void mostrar() {
        System.out.println("ID: " + id);
        System.out.println("Nombre: " + getNombre());
        System.out.println("Correo: " + getCorreo());
        System.out.println("Solicitudes: " + solicitudes.size());
        System.out.println("--------------------------------------");
    }

    public void mostrar(boolean detalle) {
        if (detalle) {
            System.out.println("=== INFORMACIÓN DEL USUARIO ===");
            System.out.println("ID: " + id);
            System.out.println("Nombre: " + getNombre());
            System.out.println("Correo: " + getCorreo());
            System.out.println("Solicitudes asociadas: " + solicitudes.size());
        } else {
            mostrar();
        }
    }

    @Override
    public String toString() {
        return "ID: " + id
                + "\nNombre: " + getNombre()
                + "\nCorreo: " + getCorreo()
                + "\nSolicitudes: " + solicitudes.size();
    }
}