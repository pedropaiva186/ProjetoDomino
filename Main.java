import java.util.*;
import java.util.concurrent.TimeUnit;

import modelo.Leitor;
import modelo.Pedra;
import modelo.Tabuleiro;

public class Main{
    public static void main(String[] args){
        try (Leitor.leitor) {
            Tabuleiro jogo = new Tabuleiro("Kevin");
            
            jogo.iniciarPartida();
            while (true) {
                try{
                    TimeUnit.SECONDS.sleep(2);
                } catch(InterruptedException e) {
                    ;
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