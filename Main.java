import java.util.*;
import java.util.concurrent.TimeUnit;
import modelo.Leitor;
import modelo.Pedra;
import modelo.Tabuleiro;
import modelo.erros_adicionais.PararAgora;

public class Main{
    public static void main(String[] args) throws InterruptedException{
        int qtdJogadores;
        try (Leitor.leitor) {
             

            /*Permite que o usuário informe a quantidade de jogadores(limitado a no máximo 4).
             * Optei por essa alteração por acreditar que torna o programa mais user friendly
             */
            while(true) {
                System.out.print("Informe a quantidade de jogadores: ");
                qtdJogadores = Leitor.leitor.nextInt();
                if(qtdJogadores <= 4) {
                    break;
                }
                System.out.println("Quantidade de jogadores humanos inválida, o limite de jogadores humanos é de 0-4.");
            }

            String[] nomes = new String[qtdJogadores];
            Leitor.leitor.nextLine(); //limpa buffer;

            /*Laço para que o nome dos jogadores seja informado pelo usuário. 
              Cada nome é alocado numa posição do array nomes de maneira sequencail*/
            for(int i = 0; i < qtdJogadores; i++){
                System.out.printf("Informe o nome do jogador n° %d: ", i+1);
                nomes[i] = Leitor.leitor.nextLine();
            }

            Tabuleiro jogo = new Tabuleiro(nomes, qtdJogadores);
            
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
                
                try {
                    jogo.addTabuleiro(jogo.getPlayer(jogo.getTurno()));
                } catch (PararAgora e) {
                    break;
                }
                
                if(jogo.getEstado() == true) {
                    break;
                }
            }
        }
    }
}