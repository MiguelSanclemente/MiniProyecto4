package Controlador;

import Modelo.*;
import Vista.Interfaz;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;

public class Controlador {
    private Interfaz vista;
    private Entrenador entrenador1;
    private Entrenador entrenador2;
    private int indice1 = 0, indice2 = 0;
    private Batalla batalla;
    private JTextArea historialArea; // Agregado aquí

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
        // Mostrar historial vacío o con mensaje inicial
        vista.mostrarHistorialMovimientos(new ArrayList<>(batalla.getHistorialMovimientos()));
        
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
        String entrenadorDerrotado = (pokemonDerrotado == entrenador1.getEquipo()[indice1]) ? entrenador1.getNombre() : entrenador2.getNombre();

        // Registrar en el historial que el Pokémon fue derrotado
        batalla.getHistorialMovimientos().push(
            entrenadorDerrotado + ": " + pokemonDerrotado.getNamePokemon() + " ha sido derrotado."
        );

        if (pokemonDerrotado == entrenador1.getEquipo()[indice1]) {
            indice1++;
            // Si hay otro Pokémon, registrar que entra uno nuevo
            if (indice1 < entrenador1.getEquipo().length && entrenador1.getEquipo()[indice1] != null) {
                batalla.getHistorialMovimientos().push(
                    entrenador1.getNombre() + " envía a " + entrenador1.getEquipo()[indice1].getNamePokemon() + " al combate."
                );
            }
        } else {
            indice2++;
            if (indice2 < entrenador2.getEquipo().length && entrenador2.getEquipo()[indice2] != null) {
                batalla.getHistorialMovimientos().push(
                    entrenador2.getNombre() + " envía a " + entrenador2.getEquipo()[indice2].getNamePokemon() + " al combate."
                );
            }
        }

        vista.mostrarHistorialMovimientos(new ArrayList<>(batalla.getHistorialMovimientos()));

        if (indice1 >= entrenador1.getEquipo().length || entrenador1.getEquipo()[indice1] == null ||
            indice2 >= entrenador2.getEquipo().length || entrenador2.getEquipo()[indice2] == null) {
            String ganador = (indice1 >= entrenador1.getEquipo().length) ? entrenador2.getNombre() : entrenador1.getNombre();
            vista.finalizarBatalla(ganador);
        } else {
            vista.iniciarBatalla();
        }
    }

    public void guardarPartida() {
        System.out.println("DEBUG: Se llamó a guardarPartida()");
        try {
            if (entrenador1 != null) entrenador1.guardarEstado("entrenador1.txt");
            if (entrenador2 != null) entrenador2.guardarEstado("entrenador2.txt");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("batalla.txt"))) {
                writer.write(indice1 + "," + indice2);
                writer.newLine();
                if (batalla != null) {
                    for (String mov : batalla.getHistorialMovimientos()) {
                        writer.write(mov);
                        writer.newLine();
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error al guardar la partida: " + e.getMessage());
        }
    }

    public boolean cargarPartida() {
        try {
            java.io.File f1 = new java.io.File("entrenador1.txt");
            java.io.File f2 = new java.io.File("entrenador2.txt");
            java.io.File f3 = new java.io.File("batalla.txt");
            if (f1.length() == 0 || f2.length() == 0 || f3.length() == 0) {
                return false; // Archivos vacíos, no cargar
            }

            Entrenador e1 = new Entrenador();
            Entrenador e2 = new Entrenador();
            e1.cargarEstado("entrenador1.txt");
            e2.cargarEstado("entrenador2.txt");
            setEntrenadores(e1, e2);

            batalla = new Batalla();
            try (BufferedReader reader = new BufferedReader(new FileReader("batalla.txt"))) {
                String linea = reader.readLine();
                if (linea != null) {
                    String[] indices = linea.split(",");
                    indice1 = Integer.parseInt(indices[0]);
                    indice2 = Integer.parseInt(indices[1]);
                }
                String mov;
                while ((mov = reader.readLine()) != null) {
                    batalla.getHistorialMovimientos().push(mov);
                }
            }
            // Avanza si el Pokémon activo está debilitado
            while (indice1 < entrenador1.getEquipo().length && entrenador1.getEquipo()[indice1].getHP() <= 0) {
                indice1++;
            }
            while (indice2 < entrenador2.getEquipo().length && entrenador2.getEquipo()[indice2].getHP() <= 0) {
                indice2++;
            }
            vista.iniciarBatalla();
            vista.mostrarHistorialMovimientos(new ArrayList<>(batalla.getHistorialMovimientos()));
            return true;
        } catch (Exception e) {
            System.err.println("No se pudo cargar la partida anterior: " + e.getMessage());
            return false;
        }
    }

    public void borrarArchivosGuardado() {
        try {
            new java.io.File("entrenador1.txt").delete();
            new java.io.File("entrenador2.txt").delete();
            new java.io.File("batalla.txt").delete();
        } catch (Exception e) {
            System.err.println("Error al borrar archivos de guardado: " + e.getMessage());
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
