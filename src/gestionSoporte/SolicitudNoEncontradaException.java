package gestionSoporte;

/**
 * Excepción utilizada cuando no se encuentra una solicitud.
 */
public class SolicitudNoEncontradaException extends Exception {

    public SolicitudNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}