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
                for (Ataque a : p.getAtaque()) {
                    writer.write("," + a.getNameAtaque());
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
                String nombrePokemon = partes[0];
                short hp = Short.parseShort(partes[1]);
                Pokemon.TipoPokemon tipo = Pokemon.TipoPokemon.valueOf(partes[2]);
                Ataque[] ataques = new Ataque[4];
                for (int i = 0; i < 4; i++) {
                    String nombreAtaque = partes[3 + i];
                    // Busca el ataque en la pokedex global
                    Pokemon ref = ElementPokemon.getPokedex().buscarPorNombre(nombrePokemon);
                    if (ref != null) {
                        for (Ataque a : ref.getAtaque()) {
                            if (a.getNameAtaque().equals(nombreAtaque)) {
                                ataques[i] = a;
                                break;
                            }
                        }
                    }
                }
                equipo[idx++] = new Pokemon(nombrePokemon, hp, tipo, ataques, 0, 0, 0, 0, 0); // Puedes mejorar stats si lo deseas
            }
        }
    }
}
