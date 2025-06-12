package Modelo;

import java.util.*;

public class Pokedex {
    private HashMap<String, Pokemon> porNombre = new HashMap<>();
    private HashMap<String, List<Pokemon>> porTipo = new HashMap<>();

    public void agregar(Pokemon p) {
        porNombre.put(p.getNamePokemon(), p);
        porTipo.computeIfAbsent(p.getTypePokemon().name(), k -> new ArrayList<>()).add(p);
    }

    public Pokemon buscarPorNombre(String nombre) {
        return porNombre.get(nombre);
    }

    public List<Pokemon> buscarPorTipo(String tipo) {
        return porTipo.getOrDefault(tipo, new ArrayList<>());
    }
}