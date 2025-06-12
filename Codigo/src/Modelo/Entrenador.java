package Modelo;

import java.util.List;
import java.util.Collections;
import java.util.Scanner;

public class Entrenador {
    private String nombre;
    private Pokemon[] equipo;

    public void setNameTrainer(Scanner sc, List<Pokemon> availablePokemons) {
        nombre = sc.nextLine();
        equipo = new Pokemon[3];
        Collections.shuffle(availablePokemons);
        for (int i = 0; i < equipo.length; i++) {
            equipo[i] = availablePokemons.remove(0);
        }
    }

    public String getNombre() { return nombre; }
    public Pokemon[] getEquipo() { return equipo; }
}
