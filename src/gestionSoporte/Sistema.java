package gestionSoporte;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Sistema {

    private Gestor gestor;
    private JFrame ventanaPrincipal;

    public Sistema() {

        gestor = new Gestor();

        ventanaPrincipal = new JFrame(
                "Plataforma de Soporte"
        );

        ventanaPrincipal.setSize(500, 400);
        ventanaPrincipal.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );
    }

    public Gestor getGestor() {
        return gestor;
    }

    public void setGestor(Gestor gestor) {
        this.gestor = gestor;
    }

    public JFrame getVentanaPrincipal() {
        return ventanaPrincipal;
    }

    public void setVentanaPrincipal(
            JFrame ventanaPrincipal) {

        this.ventanaPrincipal = ventanaPrincipal;
    }

    // =========================================================
    // MÉTODO PRINCIPAL
    // =========================================================

    public static void main(String[] args) {

        Sistema sistema = new Sistema();

        sistema.seleccionarModo();
    }

    // =========================================================
    // SELECCIONAR MODO
    // =========================================================

    public void seleccionarModo() {

        String[] opciones = {
            "Consola",
            "Ventana",
            "Salir"
        };

        int opcion = JOptionPane.showOptionDialog(
                ventanaPrincipal,
                "¿Cómo desea utilizar el sistema?",
                "Plataforma de Soporte",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        try {

            switch (opcion) {

                case 0:
                    new Consola(gestor).iniciar();
                    break;

                case 1:
                    new Ventana(gestor).iniciar();
                    break;

                case 2:
                case JOptionPane.CLOSED_OPTION:
                    break;

                default:
                    break;
            }

        } finally {

            gestor.guardarDatos();

            ventanaPrincipal.dispose();
        }
    }
}