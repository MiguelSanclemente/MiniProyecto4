package Modelo;
import java.util.Scanner;
import java.util.Stack;
import java.util.LinkedList;
import java.util.List;


public class Batalla {
    private Stack<String> historialMovimientos = new Stack<>();
    private LinkedList<Pokemon> ordenTurnos = new LinkedList<>();

    public void iniciarBatalla(String nombreEntrenador1, Pokemon[] equipo1, String nombreEntrenador2, Pokemon[] equipo2, Scanner sc) {
        // Se Muestras los equipos de los respectivos entrenadores
        mostrarEquipos(nombreEntrenador1, equipo1, nombreEntrenador2, equipo2);

        System.out.println("\n¡Comienza la batalla entre " + nombreEntrenador1 + " y " + nombreEntrenador2 + "!");

        int indice1 = 0, indice2 = 0; // Índices para los equipos

        // Mientras haya Pokémon disponibles en ambos equipos
        while (indice1 < equipo1.length && indice2 < equipo2.length) {
            Pokemon pokemon1 = equipo1[indice1];
            Pokemon pokemon2 = equipo2[indice2];

            System.out.println("\n" + nombreEntrenador1 + " envía a " + pokemon1.getNamePokemon() + " (HP: " + pokemon1.getHP() + ", Tipo: " + pokemon1.getTypePokemon() + ")");
            System.out.println(nombreEntrenador2 + " envía a " + pokemon2.getNamePokemon() + " (HP: " + pokemon2.getHP() + ", Tipo: " + pokemon2.getTypePokemon() + ")");

            // Determinar quién comienza basado en el Pokémon con menos HP
            boolean turnoPokemon1 = pokemon1.getHP() <= pokemon2.getHP();
            System.out.println("\nEl primer turno es para " + (turnoPokemon1 ? pokemon1.getNamePokemon() : pokemon2.getNamePokemon()) + " porque tiene menos salud.");

            // Bucle de batalla entre los dos Pokémon
            while (pokemon1.getHP() > 0 && pokemon2.getHP() > 0) {
                Pokemon atacante = turnoPokemon1 ? pokemon1 : pokemon2;
                Pokemon defensor = turnoPokemon1 ? pokemon2 : pokemon1;

                System.out.println("\nTurno de " + atacante.getNamePokemon());
                System.out.println("Elige un ataque:");
                for (int i = 0; i < atacante.getAtaque().length; i++) {
                    System.out.println((i + 1) + ". " + atacante.getAtaque()[i].getNameAtaque() + " (Potencia: " + atacante.getAtaque()[i].getPower() + ")");
                }

                int opcionAtaque;
                do {
                    System.out.print("Selecciona un ataque (1-4): ");
                    opcionAtaque = sc.nextInt();
                } while (opcionAtaque < 1 || opcionAtaque > 4);

                Ataque ataqueSeleccionado = atacante.getAtaque()[opcionAtaque - 1];
                int potenciaAtaque = ataqueSeleccionado.getPower();

                // Verificar ventaja de tipo
                if (atacante.getTypePokemon().isStrongAgainst(defensor.getTypePokemon())) {
                    potenciaAtaque += potenciaAtaque * 0.3; // Aumenta la potencia en un 30%
                    System.out.println("¡Ventaja de tipo! El ataque es más efectivo.");
                }

              
                short nuevaVida = (short) Math.max(0, defensor.getHP() - potenciaAtaque);
                defensor.setHP(nuevaVida);

                System.out.println(defensor.getNamePokemon() + " recibió " + potenciaAtaque + " de daño. Vida restante: " + defensor.getHP());

                // Verificar si el defensor ha sido derrotado
                if (defensor.getHP() <= 0) {
                    System.out.println(defensor.getNamePokemon() + " ha sido derrotado.");
                    break;
                }

                // Cambiar turno
                turnoPokemon1 = !turnoPokemon1;
            }

            // Avanzar al siguiente Pokémon del equipo derrotado
            if (pokemon1.getHP() <= 0) {
                indice1++;
            } else if (pokemon2.getHP() <= 0) {
                indice2++;
            }
        }

        // Determinar el ganador
        if (indice1 == equipo1.length) {
            System.out.println("\n¡" + nombreEntrenador2 + " gana la batalla!");
        } else {
            System.out.println("\n¡" + nombreEntrenador1 + " gana la batalla!");
        }
    }

    private void mostrarEquipos(String nombreEntrenador1, Pokemon[] equipo1, String nombreEntrenador2, Pokemon[] equipo2) {
        
        System.out.println("\nEquipos iniciales:");
        System.out.println(nombreEntrenador1 + ":");
        for (Pokemon p : equipo1) {
            System.out.println("- " + p.getNamePokemon() + " (HP: " + p.getHP() + ", Tipo: " + p.getTypePokemon() + ")");
        }

        System.out.println("\n" + nombreEntrenador2 + ":");
        for (Pokemon p : equipo2) {
            System.out.println("- " + p.getNamePokemon() + " (HP: " + p.getHP() + ", Tipo: " + p.getTypePokemon() + ")");
        }
    }
    public void registrarMovimiento(String movimiento) {
        historialMovimientos.push(movimiento);
    }
    public String ultimoMovimiento() {
        return historialMovimientos.isEmpty() ? null : historialMovimientos.peek();
    }
    public void ordenarPorVelocidad(List<Pokemon> pokemones) {
        pokemones.sort((p1, p2) -> Integer.compare(p2.getVelocidad(), p1.getVelocidad()));
        ordenTurnos.clear();
        ordenTurnos.addAll(pokemones);
    }
}


