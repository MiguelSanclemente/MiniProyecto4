package Modelo;

public class Ataque {
    private String nameAtaque;
    private Pokemon.TipoAtaque tipoAtaque;
    private byte power;

    public Ataque(String nameAtaque, Pokemon.TipoAtaque tipoAtaque, byte power) {
        this.nameAtaque = nameAtaque;
        this.tipoAtaque = tipoAtaque;
        this.power = power;
    }

    public String getNameAtaque() { return nameAtaque; }
    public Pokemon.TipoAtaque getTipoAtaque() { return tipoAtaque; }
    public byte getPower() { return power; }
}
