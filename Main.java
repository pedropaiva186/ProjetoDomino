import modelo.Tabuleiro;
import modelo.Leitor;
import modelo.Pedra;
import java.util.*;

public class Main{
    public static void main(String[] args){
        Tabuleiro jogo = new Tabuleiro("Kevin");
        
        jogo.iniciarPartida();
        while (true) {
            List<Pedra> mesa = jogo.getPedrasTabuleiro();
            System.out.println("---------------------------------------------------");
            System.out.println("Pedras na mesa");
            for(Pedra pedraAtual: mesa) {
                System.out.printf("%d %d\n", pedraAtual.getNumCima(), pedraAtual.getNumBaixo());
            }
            System.out.println("---------------------------------------------------");

            jogo.addTabuleiro(jogo.getPlayer(jogo.getTurno()));

            if(jogo.getEstado() == true) {
                break;
            }
        }

        Leitor.leitor.close();
    }
}