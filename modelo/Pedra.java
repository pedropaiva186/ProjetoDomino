package modelo;

public class Pedra {
    
    private final int numeroCima;
    private final int numeroBaixo;

    public Pedra(int numeroCima, int numeroBaixo) {
        this.numeroCima = numeroCima;
        this.numeroBaixo = numeroBaixo;
    }

    public int getNumCima() {
        return numeroCima;
    }

    public int getNumBaixo() {
        return numeroBaixo;
    }
}
