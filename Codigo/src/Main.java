import Vista.Interfaz;
import Controlador.Controlador;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            Interfaz interfaz = new Interfaz();
            Controlador controlador = new Controlador(interfaz);
            interfaz.setControlador(controlador);
            controlador.cargarPartida();
            interfaz.setVisible(true);
        });
    }
}


