package modelo;

import java.util.ArrayList;
import java.util.Scanner;
import modelo.erros_adicionais.NaoHaPedrasParaSeremJogadas;

public class Humano extends Jogador{
    // Construtor com a passagem das pedras nele próprio
    public Humano(String nomeJog, ArrayList<Pedra> pedras) {
        super(nomeJog, pedras);
    }

    // Construtor no qual é necessário adicionar as pedras depois
    public Humano(String nomeJog) {
        super(nomeJog);
    }
    
    // Esta função serve para dar ao jogador a liberdade de escolher uma pedra e retorná-la
    public Pedra jogar(int cabeca1, int cabeca2) throws NaoHaPedrasParaSeremJogadas{
        // De alguma forma o jogador vai escolher o index da pedra que vamos retornar
        // A princípio farei com scanner, mas provalvemente, isso vai mudar
        int index = 0;
        boolean indexValido = false;
        int[] pedrasPossiveis;

        pedrasPossiveis = verificaPedrasDisponiveis(cabeca1, cabeca2);

        // Verifica se não há pedras disponíveis, então encerra o método e retorna um erro indicando que a vez deve ser pulada
        if(pedrasPossiveis == null) {
            throw new NaoHaPedrasParaSeremJogadas();
        }

        // Recebendo o index do usuário e tratando de forma que ele só pare quando receber um valor válido
        try(Scanner leitor = new Scanner(System.in)) {
            while(true) {
                // Mostra quais as pedras disponíveis
                System.out.print("Pedras disponíveis:\n");
                for(int i : pedrasPossiveis) {
                    System.out.printf("%d\n", i);
                }
                System.out.print("Digite o index de uma pedra entre as disponíveis: ");
                index = leitor.nextInt();
                // Verifica se ela está entre as disponíveis
                for(int i : pedrasPossiveis) {
                    if(index == i) {
                        indexValido = true;
                        break;
                    }
                }

                if(indexValido) {
                    break;
                } else {
                    System.out.println("Número incorreto, tente novamente!!!");
                }
            }

            leitor.close();
        } catch (Exception e) {
            return null;
        }

        return getPedra(index);
    }
}