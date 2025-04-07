import modelo.Tabuleiro;
import modelo.Leitor;
import modelo.Pedra;
import java.util.*;

public class Main{
    public static void main(String[] args){
        Tabuleiro tabuleiro1 = new Tabuleiro("Kevin");
        
        tabuleiro1.iniciarPartida();
        tabuleiro1.addTabuleiro(tabuleiro1.getPlayer(0));
        List<Pedra> mesa = tabuleiro1.getPedrasTabuleiro();
        System.out.printf("%d %d", mesa.get(1).getNumBaixo(), mesa.get(1).getNumBaixo());
        Leitor.leitor.close();
    }
}