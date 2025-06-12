package Modelo;

import java.util.Arrays;
public class Pokemon {
    private String namePokemon;
    private short hp;
    private TipoPokemon typePokemon;
    private Ataque[] ataque = new Ataque[4];

    // Estadísticas
    private int defensa;
    private int ataqueEspecial;
    private int defensaEspecial;
    private int velocidad;

  
    public enum TipoAtaque {
        FISICO, ESPACIAL;
    }

    public enum TipoPokemon {
        FUEGO(new String[]{"PLANTA"}),
        AGUA(new String[]{"FUEGO"}),
        TIERRA(new String[]{"ELECTRICO"}),
        PLANTA(new String[]{"AGUA", "TIERRA"}),
        ELECTRICO(new String[]{"AGUA"});

        private final String[] strongAgainst;

        TipoPokemon(String[] strongAgainst) {
            this.strongAgainst = strongAgainst;
        }

        public boolean isStrongAgainst(TipoPokemon other) {
            return Arrays.asList(strongAgainst).contains(other.name());
        }
    }

    public Pokemon() {}

    public Pokemon(String namePokemon, short hp, TipoPokemon typePokemon, Ataque[] ataques, int ataque, int defensa, int ataqueEspecial, int defensaEspecial, int velocidad) {
        this.namePokemon = namePokemon;
        this.hp = hp;
        this.typePokemon = typePokemon;
        this.ataque = ataques;
        this.defensa = defensa;
        this.ataqueEspecial = ataqueEspecial;
        this.defensaEspecial = defensaEspecial;
        this.velocidad = velocidad;
    }

    public String getNamePokemon() { return namePokemon; }
    public void setNamePokemon(String namePokemon) { this.namePokemon = namePokemon; }
    public short getHP() { return hp; }
    public void setHP(short HP) { this.hp = HP; }
    public TipoPokemon getTypePokemon() { return typePokemon; }
    public void setTypePokemon(TipoPokemon typePokemon) { this.typePokemon = typePokemon; }
    public Ataque[] getAtaque() { return ataque; }
    public int getDefensa() { return defensa; }
    public int getAtaqueEspecial() { return ataqueEspecial; }
    public int getDefensaEspecial() { return defensaEspecial; }
    public int getVelocidad() { return velocidad; }

    public void usarAtaque(String nombreAtaque) throws PokemonDebilitadoException, AtaqueNoDisponibleException {
        if (this.hp <= 0) {
            throw new PokemonDebilitadoException("El Pokémon está debilitado y no puede atacar.");
        }
        Ataque ataque = buscarAtaquePorNombre(nombreAtaque);
        if (ataque == null) {
            throw new AtaqueNoDisponibleException("El ataque no está disponible para este Pokémon.");
        }
        // Lógica para usar el ataque...
    }

    private Ataque buscarAtaquePorNombre(String nombreAtaque) {
        for (Ataque a : ataque) {
            if (a != null && a.getNameAtaque().equals(nombreAtaque)) {
                return a;
            }
        }
        return null;
    }
}
