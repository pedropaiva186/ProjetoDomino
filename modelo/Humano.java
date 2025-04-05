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

    public String getNome() {
        return nomeJog;
    }

    public void addPedra(Pedra pedra) {
        pedrasDisponiveis.add(pedra);
    }

    // Tenta retirar a pedra, mas caso haja um erro, ele lança o erro
    public void retiraPedra(int index) throws IndexOutOfBoundsException{
        try {
            pedrasDisponiveis.remove(index);
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException();
        }
    }

    // Método para retirar a pedra a partir dela mesma, e não pelo index
    public void retiraPedra(Pedra ped) {
        pedrasDisponiveis.remove(ped);
    }

    // retorna o número de pedras
    public int verificaNumPedras() {
        return pedrasDisponiveis.size();
    }
    // retorna o array de pedras.
    public ArrayList<Pedra> getPedras() {
        return pedrasDisponiveis;
    }

    // Faz a mesma verificação para retornar um elementro Pedra
    public Pedra getPedra(int index) throws IndexOutOfBoundsException{
        Pedra retorno;

        try {
            retorno = pedrasDisponiveis.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException();
        }

        return retorno;
    }


    // Serve para verificar se determinada pedra pode ser jogada dependendo das cabeças disponíveis no tabuleiro
    private boolean verificaPedraPossiveisParaJogar(Pedra pedra, int cabeca1, int cabeca2) {
        // Depois é bom alterar isso para deixar mais legível e otimizado, mas a princípio vai funcionar
        return pedra.getNumCima() == cabeca1 || pedra.getNumCima() == cabeca2 || pedra.getNumBaixo() == cabeca1 || pedra.getNumBaixo() == cabeca2;
    }

    // Retorna um array com o index de cada pedra que pode ser jogada
    protected int[] verificaPedrasDisponiveis(int cabeca1, int cabeca2) {
        int aux = 0, indexPedra = 0; 
        int[] pedrasDispo = new int[6];

        for(Pedra ped : pedrasDisponiveis) {
            if(verificaPedraPossiveisParaJogar(ped, cabeca1, cabeca2)) {
                pedrasDispo[aux] = indexPedra;
                aux++;
            }
            indexPedra++;
        }

        // Retorna null caso não haja pedras para o jogador jogar
        if(aux == 0) {
            return null;
        }

        return pedrasDispo;
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