package Controlador;

import Modelo.*;
import Vista.Interfaz;
import java.util.ArrayList;

public class Controlador {
    private Interfaz vista;
    private Entrenador entrenador1;
    private Entrenador entrenador2;
    private int indice1 = 0, indice2 = 0;
    private Batalla batalla;

    public Controlador(Interfaz vista) {
        this.vista = vista;
    }

    // Método para recibir los entrenadores desde la vista
    public void setEntrenadores(Entrenador entrenador1, Entrenador entrenador2) {
        this.entrenador1 = entrenador1;
        this.entrenador2 = entrenador2;
        this.indice1 = 0;
        this.indice2 = 0;
    }

    // Método para iniciar la batalla (puedes expandirlo según tu lógica)
    public void iniciarBatalla() {
        this.batalla = new Batalla();
        vista.iniciarBatalla();
    }

    // Recibe los nombres de los ataques seleccionados
    public void ejecutarTurno(String ataque1Nombre, String ataque2Nombre) {
        Pokemon pokemon1 = entrenador1.getEquipo()[indice1];
        Pokemon pokemon2 = entrenador2.getEquipo()[indice2];

        Ataque ataque1 = buscarAtaquePorNombre(pokemon1, ataque1Nombre);
        Ataque ataque2 = buscarAtaquePorNombre(pokemon2, ataque2Nombre);

        // Determinar el orden de ataque por velocidad
        int velocidad1 = pokemon1.getVelocidad();
        int velocidad2 = pokemon2.getVelocidad();

        Pokemon primero = velocidad1 >= velocidad2 ? pokemon1 : pokemon2;
        Pokemon segundo = primero == pokemon1 ? pokemon2 : pokemon1;
        Ataque ataquePrimero = primero == pokemon1 ? ataque1 : ataque2;
        Ataque ataqueSegundo = primero == pokemon1 ? ataque2 : ataque1;

        // Primer ataque
        int dañoPrimero = ataquePrimero.getPower();
        if (primero.getTypePokemon().isStrongAgainst(segundo.getTypePokemon())) {
            dañoPrimero += dañoPrimero * 0.3;
            vista.mostrarMensaje("¡Ventaja de tipo! El ataque de " + primero.getNamePokemon() + " es más efectivo.");
        }
        segundo.setHP((short) (segundo.getHP() - dañoPrimero));
        vista.mostrarMensaje(primero.getNamePokemon() + " usa " + ataquePrimero.getNameAtaque() + " y ataca primero.");

        batalla.getHistorialMovimientos().push(primero.getNamePokemon() + " usa " + ataquePrimero.getNameAtaque() + " y ataca primero.");

        if (segundo.getHP() <= 0) {
            vista.mostrarMensaje(segundo.getNamePokemon() + " ha sido derrotado.");
            avanzarAlSiguientePokemon(segundo);
            return;
        }

        // Segundo ataque
        int dañoSegundo = ataqueSegundo.getPower();
        if (segundo.getTypePokemon().isStrongAgainst(primero.getTypePokemon())) {
            dañoSegundo += dañoSegundo * 0.3;
            vista.mostrarMensaje("¡Ventaja de tipo! El ataque de " + segundo.getNamePokemon() + " es más efectivo.");
        }
        primero.setHP((short) (primero.getHP() - dañoSegundo));
        vista.mostrarMensaje(segundo.getNamePokemon() + " usa " + ataqueSegundo.getNameAtaque() + ".");

        batalla.getHistorialMovimientos().push(segundo.getNamePokemon() + " usa " + ataqueSegundo.getNameAtaque() + ".");

        if (primero.getHP() <= 0) {
            vista.mostrarMensaje(primero.getNamePokemon() + " ha sido derrotado.");
            avanzarAlSiguientePokemon(primero);
        } else {
            vista.mostrarMensaje(
                primero.getNamePokemon() + " tiene " + primero.getHP() + " HP restantes.\n" +
                segundo.getNamePokemon() + " tiene " + segundo.getHP() + " HP restantes."
            );
        }
        // Suponiendo que tienes acceso a la pila en Batalla:
        vista.mostrarHistorialMovimientos(new ArrayList<>(batalla.getHistorialMovimientos()));
    }

    private Ataque buscarAtaquePorNombre(Pokemon pokemon, String nombreAtaque) {
        for (Ataque ataque : pokemon.getAtaque()) {
            if (ataque.getNameAtaque().equals(nombreAtaque)) {
                return ataque;
            }
        }
        throw new IllegalArgumentException("Ataque no encontrado: " + nombreAtaque);
    }

    private void avanzarAlSiguientePokemon(Pokemon pokemonDerrotado) {
        if (pokemonDerrotado == entrenador1.getEquipo()[indice1]) {
            indice1++;
        } else {
            indice2++;
        }

        if (indice1 >= entrenador1.getEquipo().length || entrenador1.getEquipo()[indice1] == null ||
            indice2 >= entrenador2.getEquipo().length || entrenador2.getEquipo()[indice2] == null) {
            String ganador = (indice1 >= entrenador1.getEquipo().length) ? entrenador2.getNombre() : entrenador1.getNombre();
            vista.finalizarBatalla(ganador);
        } else {
            // Reinicia la batalla con los nuevos Pokémon activos
            vista.iniciarBatalla();
        }
    }

    public Entrenador getEntrenador1() {
        return entrenador1;
    }

    public Entrenador getEntrenador2() {
        return entrenador2;
    }

    public int getIndice1() {
        return indice1;
    }

    public int getIndice2() {
        return indice2;
    }
}
