package modelo;

import java.util.*;
import modelo.erros_adicionais.NaoHaPedrasParaSeremJogadas;
import modelo.erros_adicionais.PararAgora;
import view.TabuleiroView;

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
    public Pedra jogar(int ponta1, int ponta2) throws NaoHaPedrasParaSeremJogadas, PararAgora{
        // De alguma forma o jogador vai escolher o index da pedra que vamos retornar
        // A princípio farei com scanner, mas provalvemente, isso vai mudar
        int index = 0;
        boolean indexValido = false;
        List<Integer> pedrasPossiveis;

        pedrasPossiveis = verificaPedrasDisponiveis(ponta1, ponta2);

        // Verifica se não há pedras disponíveis, então encerra o método e retorna um erro indicando que a vez deve ser pulada
        if(pedrasPossiveis == null) {
            throw new NaoHaPedrasParaSeremJogadas();
        }

        // Recebendo o index do usuário e tratando de forma que ele só pare quando receber um valor válido
        try{
            while(true) {
                // Mostra quais são as pedras que estão disponíveis no momento
                TabuleiroView.showPedrasDisponiveis(ponta1, ponta2, this);
                index = Leitor.leitor.nextInt();

                // Se caso o player quiser parar o jogo instantâneamente
                if(index == 99) {
                    throw new PararAgora();
                }

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
                    System.out.println("Índice inválido, tente novamente!!!");
                }
            }
        } catch(PararAgora e) {
            throw e;
        } catch(Exception e) {
            return null;
        }

        return getPedra(index);
    }

}