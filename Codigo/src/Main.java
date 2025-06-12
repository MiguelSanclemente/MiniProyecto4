import Vista.Interfaz;
import Controlador.Controlador;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            Modelo.ElementPokemon.initializeData(); 
            Interfaz interfaz = new Interfaz();
            Controlador controlador = new Controlador(interfaz);
            interfaz.setControlador(controlador);

            interfaz.mostrarPantallaInicial();
            interfaz.setVisible(true);
        });
    }
}
