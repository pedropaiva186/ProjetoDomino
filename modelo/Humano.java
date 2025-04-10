package modelo;

import java.util.*;
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
    @Override
    public Pedra jogar(int cabeca1, int cabeca2) throws NaoHaPedrasParaSeremJogadas{
        // De alguma forma o jogador vai escolher o index da pedra que vamos retornar
        // A princípio farei com scanner, mas provalvemente, isso vai mudar
        int index = 0;
        boolean indexValido = false;
        List<Integer> pedrasPossiveis;

        pedrasPossiveis = verificaPedrasDisponiveis(cabeca1, cabeca2);

        // Verifica se não há pedras disponíveis, então encerra o método e retorna um erro indicando que a vez deve ser pulada
        if(pedrasPossiveis == null) {
            throw new NaoHaPedrasParaSeremJogadas();
        }

        // Recebendo o index do usuário e tratando de forma que ele só pare quando receber um valor válido
        try{
            while(true) {
                // Mostra quais são as pedras que estão disponíveis no momento.
                showPedrasDisponiveis(pedrasPossiveis);
                index = Leitor.leitor.nextInt();
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

        } catch (Exception e) {
            return null;
        }

        // Debug
        System.out.println("Pedra retornada com sucesso!");

        return getPedra(index);
    }

    private void showPedrasDisponiveis(List<Integer> pedrasPossiveis) {
        System.out.println("----------------------------------------------");
        System.out.print("Pedras disponíveis:\n");
        for(int i : pedrasPossiveis) {
            System.out.printf("%d - {%d %d}\n", i, pedrasDisponiveis.get(i).getNumCima(), pedrasDisponiveis.get(i).getNumBaixo());
        }
        System.out.println("----------------------------------------------");
        System.out.print("Digite o index de uma pedra entre as disponíveis: ");
    }
}