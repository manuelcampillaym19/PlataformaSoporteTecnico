package gestionSoporte.modelo;

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

    /**
     * Devuelve una copia de la lista para evitar
     * modificaciones externas directas.
     */
    public ArrayList<Solicitud> getSolicitudes() {
        return new ArrayList<>(solicitudes);
    }

    public void setSolicitudes(
            ArrayList<Solicitud> solicitudes) {

        this.solicitudes =
                new ArrayList<>(solicitudes);
    }

    public void agregarSolicitud(
            Solicitud solicitud) {

        if (solicitud != null
                && !solicitudes.contains(solicitud)) {

            solicitudes.add(solicitud);
        }
    }

    public void eliminarSolicitud(
            Solicitud solicitud) {

        solicitudes.remove(solicitud);
    }

    // =========================================================
    // SOBRECARGA DE MÉTODOS
    // =========================================================

    public void mostrar() {
        mostrar("");
    }

    public void mostrar(String prefijo) {

        System.out.println(
                prefijo + "ID: " + id
        );

        System.out.println(
                prefijo + "Nombre: " + getNombre()
        );

        System.out.println(
                prefijo + "Correo: " + getCorreo()
        );

        System.out.println(
                prefijo + "Cantidad de solicitudes: "
                + solicitudes.size()
        );
    }

    // =========================================================
    // SOBRESCRITURA
    // =========================================================

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