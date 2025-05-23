import java.util.InputMismatchException;
import java.util.concurrent.TimeUnit;

import modelo.Leitor;
import modelo.Tabuleiro;
import modelo.erros_adicionais.PararAgora;
import view.TabuleiroView;

public class Main{
    public static void limparTerminal() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) throws InterruptedException{
        int qtdJogadores;
        try (Leitor.leitor) {
             

            /*Permite que o usuário informe a quantidade de jogadores(limitado a no máximo 4).
             * Optei por essa alteração por acreditar que torna o programa mais user friendly
             */
            while(true) {
                System.out.print("Informe a quantidade de jogadores: ");
                try{
                    qtdJogadores = Leitor.leitor.nextInt();
                }catch(InputMismatchException e) {
                    System.out.println("Quantidade de jogadores fornecida inválida, por favor digite um valor de 0-4.");
                    Leitor.leitor.nextLine();
                    continue;
                }   

                if(qtdJogadores <= 4) {
                    break;
                }
                System.out.println("Quantidade de jogadores humanos inválida, o limite de jogadores humanos é de 0-4.");
            }

            String[] nomes = new String[qtdJogadores];
            Leitor.leitor.nextLine(); //limpa buffer;

            /*Laço para que o nome dos jogadores seja informado pelo usuário. 
              Cada nome é alocado numa posição do array nomes de maneira sequencial*/
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
                limparTerminal();
                TabuleiroView.listarPedrasTabuleiro(jogo);
                
                try {
                    jogo.addTabuleiro(jogo.getPlayer(jogo.getTurno()));
                } catch (PararAgora e) {
                    break;
                }

                if(jogo.isFim()) {
                    break;
                }
            }

            System.out.println("Fim do programa, espero que você tenha se divertido!");
        }
    }
}