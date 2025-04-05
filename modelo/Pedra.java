package modelo;

public class Pedra {
    
    private final char numeroCima;
    private final char numeroBaixo;

    public Pedra(char numeroCima, char numeroBaixo) {
        this.numeroCima = numeroCima;
        this.numeroBaixo = numeroBaixo;
    }

    public char getNumCima() {
        return numeroCima;
    }

    public char getNumBaixo() {
        return numeroBaixo;
    }

}
