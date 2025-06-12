package Modelo;


public class ElementPokemon {
    private static Ataque[] fireMoves, waterMoves, grassMoves, groundMoves, electricMoves;
    private static Pokemon[] pokemon;

    public static void initializeMoves() {
        fireMoves = new Ataque[] {
            new Ataque("Lanzallamas", Pokemon.TipoAtaque.FISICO, (byte) 90),
            new Ataque("Llamarada", Pokemon.TipoAtaque.ESPACIAL, (byte) 85),
            new Ataque("Puño Fuego", Pokemon.TipoAtaque.FISICO, (byte) 75),
            new Ataque("Giro Fuego", Pokemon.TipoAtaque.ESPACIAL, (byte) 70),
            new Ataque("Nitrocarga", Pokemon.TipoAtaque.FISICO, (byte) 65)
        };
        waterMoves = new Ataque[] {
            new Ataque("Hidrobomba", Pokemon.TipoAtaque.ESPACIAL, (byte) 95),
            new Ataque("Surfer", Pokemon.TipoAtaque.ESPACIAL, (byte) 85),
            new Ataque("Acua poket", Pokemon.TipoAtaque.FISICO, (byte) 80),
            new Ataque("Pistola Agua", Pokemon.TipoAtaque.ESPACIAL, (byte) 65),
            new Ataque("Acua lluvia", Pokemon.TipoAtaque.FISICO, (byte) 75)
        };
        grassMoves = new Ataque[] {
            new Ataque("Rayo Solar", Pokemon.TipoAtaque.ESPACIAL, (byte) 95),
            new Ataque("Hoja Afilada", Pokemon.TipoAtaque.FISICO, (byte) 85),
            new Ataque("Latigazo", Pokemon.TipoAtaque.FISICO, (byte) 80),
            new Ataque("Bomba Germen", Pokemon.TipoAtaque.ESPACIAL, (byte) 75),
            new Ataque("Drenadoras", Pokemon.TipoAtaque.ESPACIAL, (byte) 70)
        };
        groundMoves = new Ataque[] {
            new Ataque("Terremoto", Pokemon.TipoAtaque.FISICO, (byte) 95),
            new Ataque("Excavar", Pokemon.TipoAtaque.FISICO, (byte) 85),
            new Ataque("Tierra Viva", Pokemon.TipoAtaque.ESPACIAL, (byte) 80),
            new Ataque("Bofetón Lodo", Pokemon.TipoAtaque.ESPACIAL, (byte) 75),
            new Ataque("Avalancha", Pokemon.TipoAtaque.FISICO, (byte) 70)
        };
        electricMoves = new Ataque[] {
            new Ataque("inpactrueno", Pokemon.TipoAtaque.FISICO, (byte) 78),
            new Ataque("Rayo", Pokemon.TipoAtaque.FISICO, (byte) 81),
            new Ataque("Puño Trueno", Pokemon.TipoAtaque.ESPACIAL, (byte) 92),
            new Ataque("Relampago", Pokemon.TipoAtaque.ESPACIAL, (byte) 89),
            new Ataque("Truenete", Pokemon.TipoAtaque.FISICO, (byte) 52)
        };
    }

    public static void initializePokemons() {
        pokemon = new Pokemon[] {
            new Pokemon("Charizard", (short) 282, Pokemon.TipoPokemon.FUEGO, new Ataque[] {fireMoves[0], fireMoves[1], fireMoves[2], fireMoves[3]}, 84, 78, 109, 85, 100),
            new Pokemon("Flareon", (short) 198, Pokemon.TipoPokemon.FUEGO, new Ataque[] {fireMoves[1], fireMoves[2], fireMoves[3], fireMoves[4]}, 130, 60, 95, 110, 65),
            new Pokemon("Blastoise", (short) 292, Pokemon.TipoPokemon.AGUA, new Ataque[] {waterMoves[0], waterMoves[1], waterMoves[2], waterMoves[3]}, 83, 100, 85, 105, 78),
            new Pokemon("Vaporeon", (short) 214, Pokemon.TipoPokemon.AGUA, new Ataque[] {waterMoves[1], waterMoves[2], waterMoves[3], waterMoves[4]}, 65, 60, 110, 95, 65),
            new Pokemon("Venasaur", (short) 271, Pokemon.TipoPokemon.PLANTA, new Ataque[] {grassMoves[0], grassMoves[1], grassMoves[2], grassMoves[3]}, 82, 83, 100, 100, 80),
            new Pokemon("Leafeon", (short) 194, Pokemon.TipoPokemon.PLANTA, new Ataque[] {grassMoves[1], grassMoves[2], grassMoves[3], grassMoves[4]}, 110, 130, 60, 65, 95),
            new Pokemon("Sandslash", (short) 324, Pokemon.TipoPokemon.TIERRA, new Ataque[] {groundMoves[0], groundMoves[1], groundMoves[2], groundMoves[3]}, 100, 110, 45, 55, 65),
            new Pokemon("Nidoking", (short) 344, Pokemon.TipoPokemon.TIERRA, new Ataque[] {groundMoves[1], groundMoves[2], groundMoves[3], groundMoves[4]}, 102, 77, 85, 75, 85),
            new Pokemon("Pikachu", (short) 176, Pokemon.TipoPokemon.ELECTRICO, new Ataque[] {electricMoves[0], electricMoves[2], electricMoves[1], electricMoves[4]}, 55, 40, 50, 50, 90),
            new Pokemon("Zapdos", (short) 274, Pokemon.TipoPokemon.ELECTRICO, new Ataque[] {electricMoves[3], electricMoves[4], electricMoves[2], electricMoves[1]}, 90, 85, 125, 90, 100)
        };
    }

    public static void initializeData() {
        initializeMoves();
        initializePokemons();
    }

    public static Pokemon[] getPokemon() { return pokemon; }

    public static void main(String[] args) {
        ElementPokemon.initializeData();

        // Mostrar los Pokémon inicializados
        for (Pokemon p : getPokemon()) {
            System.out.println("- " + p.getNamePokemon() + " (HP: " + p.getHP() + ", Tipo: " + p.getTypePokemon() + ")");
        }
    }
}
