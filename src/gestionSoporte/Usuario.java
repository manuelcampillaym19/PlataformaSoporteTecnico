package gestionSoporte;

import java.util.ArrayList;

/**
 * Representa a un usuario registrado en el sistema.
 */
public class Usuario extends personaBase {

    private static int siguienteId = 1;

    private int id;
    private ArrayList<Solicitud> solicitudes;

    public Usuario(String nombre, String correo) {
        super(nombre, correo);
        this.id = siguienteId++;
        this.solicitudes = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;

        if (id >= siguienteId) {
            siguienteId = id + 1;
        }
    }

    public ArrayList<Solicitud> getSolicitudes() {
        return new ArrayList<>(solicitudes);
    }

    public void setSolicitudes(ArrayList<Solicitud> solicitudes) {
        this.solicitudes = new ArrayList<>(solicitudes);
    }

    public void agregarSolicitud(Solicitud solicitud) {
        if (solicitud != null && !solicitudes.contains(solicitud)) {
            solicitudes.add(solicitud);
        }
    }

    public void eliminarSolicitud(Solicitud solicitud) {
        solicitudes.remove(solicitud);
    }

    /*
     * Sobrecarga de métodos.
     */
    public void mostrar() {
        System.out.println("ID: " + id);
        System.out.println("Nombre: " + getNombre());
        System.out.println("Correo: " + getCorreo());
        System.out.println("Cantidad de solicitudes: "
                + solicitudes.size());
    }

    /*
     * Sobrecarga de métodos.
     */
    public void mostrar(boolean detalle) {
        mostrar();

        if (detalle) {
            for (Solicitud solicitud : solicitudes) {
                solicitud.mostrar("  ");
            }
        }
    }

    /*
     * SIA-6: sobrescritura de un método heredado
     * desde personaBase.
     */
    @Override
    public void mostrarInformacion() {
        mostrar();
    }

    @Override
    public String toString() {
        return "ID: " + id
                + "\nNombre: " + getNombre()
                + "\nCorreo: " + getCorreo()
                + "\nCantidad de solicitudes: "
                + solicitudes.size();
    }
}