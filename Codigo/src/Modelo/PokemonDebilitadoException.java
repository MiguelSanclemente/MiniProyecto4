package Modelo;

public class PokemonDebilitadoException extends Exception {
    public PokemonDebilitadoException(String mensaje) {
        super(mensaje);
    }
    
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
}