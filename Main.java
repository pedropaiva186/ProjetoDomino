import java.util.*;
import java.util.concurrent.TimeUnit;
import modelo.Leitor;
import modelo.Pedra;
import modelo.Tabuleiro;

public class Main{
    public static void main(String[] args) throws InterruptedException{
        try (Leitor.leitor) {
            // Aqui inserimos os nomes dos jogadores, essa alteração vai permitir uma quantidade alterável de
            // jogadores
            String[] nomes = {"Kevin", "Pedro"};
            Tabuleiro jogo = new Tabuleiro(nomes, 2);
            
            jogo.iniciarPartida();
            while(true) {
                try{
                    TimeUnit.SECONDS.sleep(2);
                } catch(InterruptedException e) {
                    throw e;
                }

                List<Pedra> mesa = jogo.getPedrasTabuleiro();
                System.out.printf("-------------------------%d° rodada-------------------------\n", jogo.getRodadas());
                System.out.println("Pedras na mesa");
                for(Pedra pedraAtual: mesa) {
                    System.out.printf("{%d %d}\n", pedraAtual.getNumCima(), pedraAtual.getNumBaixo());
                }
                System.out.println("-----------------------------------------------------------");
                
                jogo.addTabuleiro(jogo.getPlayer(jogo.getTurno()));
                
                if(jogo.getEstado() == true) {
                    break;
                }
            }
        }
    }
}