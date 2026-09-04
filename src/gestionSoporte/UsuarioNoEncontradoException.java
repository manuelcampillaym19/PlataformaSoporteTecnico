package gestionSoporte;

/**
 * Excepción utilizada cuando no se encuentra un usuario.
 */
public class UsuarioNoEncontradoException extends Exception {

    public UsuarioNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}