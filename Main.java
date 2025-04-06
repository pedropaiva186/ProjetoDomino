import modelo.Tabuleiro;
import modelo.Pedra;
import java.util.*;

public class Main{
    public static void main(String[] args){
        Tabuleiro tabuleiro1 = new Tabuleiro("Kevin");
        List<Pedra> dorme = tabuleiro1.getDorme();
        tabuleiro1.setBaralho(tabuleiro1.getPlayer(0));
        
        for(int i = 0; i < dorme.size(); i++) {
            System.out.printf("%d - %d %d\n", i, (int)dorme.get(i).getNumCima(), (int) dorme.get(i).getNumBaixo());
        }
    }
}