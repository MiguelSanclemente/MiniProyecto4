package Modelo;

import java.util.List;
import java.util.Collections;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class Entrenador {
    private String nombre;
    private Pokemon[] equipo;

    public void setNameTrainer(Scanner sc, List<Pokemon> availablePokemons) {
        nombre = sc.nextLine();
        equipo = new Pokemon[3];
        Collections.shuffle(availablePokemons);
        for (int i = 0; i < equipo.length; i++) {
            equipo[i] = availablePokemons.remove(0);
            Ataque[] ataques = equipo[i].getAtaque();
            for (int j = 0; j < ataques.length; j++) {
                if (ataques[j] == null) {
                    System.out.println("ADVERTENCIA: " + equipo[i].getNamePokemon() + " tiene ataque null en la posición " + j);
                }
            }
        }
    }

    public String getNombre() { return nombre; }
    public Pokemon[] getEquipo() { return equipo; }

    public void guardarEstado(String archivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            writer.write(nombre);
            writer.newLine();
            for (Pokemon p : equipo) {
                writer.write(p.getNamePokemon() + "," + p.getHP() + "," + p.getTypePokemon());
                Ataque[] ataques = p.getAtaque();
                for (int i = 0; i < 4; i++) {
                    if (ataques != null && ataques.length > i && ataques[i] != null) {
                        writer.write("," + ataques[i].getNameAtaque());
                    } else {
                        writer.write(",null");
                    }
                }
                writer.newLine();
            }
        }
    }

    public void cargarEstado(String archivo) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            nombre = reader.readLine();
            equipo = new Pokemon[3];
            String linea;
            int idx = 0;
            while ((linea = reader.readLine()) != null && idx < equipo.length) {
                String[] partes = linea.split(",");
                if (partes.length < 3) continue; // Línea mal formada, saltar

                String nombrePokemon = partes[0];
                short hp = Short.parseShort(partes[1]);
                Pokemon.TipoPokemon tipo = Pokemon.TipoPokemon.valueOf(partes[2]);
                Ataque[] ataques = new Ataque[4];

                for (int i = 0; i < 4; i++) {
                    String nombreAtaque = (partes.length > 3 + i) ? partes[3 + i] : "null";
                    if (!"null".equals(nombreAtaque)) {
                        Pokemon ref = ElementPokemon.getPokedex().buscarPorNombre(nombrePokemon);
                        if (ref != null) {
                            boolean encontrado = false;
                            for (Ataque a : ref.getAtaque()) {
                                if (a != null && a.getNameAtaque().equals(nombreAtaque)) {
                                    ataques[i] = a;
                                    encontrado = true;
                                    break;
                                }
                            }
                            if (!encontrado) {
                                System.out.println("ADVERTENCIA: Ataque '" + nombreAtaque + "' no encontrado para " + nombrePokemon);
                            }
                        } else {
                            System.out.println("ADVERTENCIA: Pokémon '" + nombrePokemon + "' no encontrado en la pokedex.");
                        }
                    }
                }
                equipo[idx++] = new Pokemon(nombrePokemon, hp, tipo, ataques, 0, 0, 0, 0, 0);
            }
            // Rellenar con Pokémon vacíos si faltan
            while (idx < equipo.length) {
                equipo[idx++] = new Pokemon("VACIO", (short)0, Pokemon.TipoPokemon.AGUA, new Ataque[4], 0, 0, 0, 0, 0);
            }
        }
    }
}
