import modelo.Tabuleiro;
import modelo.Pedra;
import java.util.*;

public class Main{
    public static void main(String[] args){
        Tabuleiro tabuleiro1 = new Tabuleiro();
        List<Pedra> dorme = tabuleiro1.getDorme();

        for(int i = 0; i < dorme.size(); i++) {
            System.out.printf("%d %d\n", (int)dorme.get(i).getNumCima(), (int) dorme.get(i).getNumBaixo());
        }

        for(Pedra pedra: dorme) {
            System.out.printf("%d %d\n", (int)pedra.getNumCima(), (int) pedra.getNumBaixo());
        }
    }
}