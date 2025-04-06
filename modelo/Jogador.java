package modelo;

import java.util.ArrayList;

import modelo.erros_adicionais.NaoHaPedrasParaSeremJogadas;

public abstract class Jogador {
    
    protected final String nomeJog;
    protected final ArrayList<Pedra> pedrasDisponiveis;

    public Jogador(String nomeJog, ArrayList<Pedra> pedras) {
        this.nomeJog = nomeJog;
        pedrasDisponiveis = pedras;
    }

    // Construtor no qual é necessário adicionar as pedras depois
    public Jogador(String nomeJog) {
        this.nomeJog = nomeJog;
        pedrasDisponiveis = new ArrayList<>();
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
    public boolean checkPossivel(Pedra pedra, int cabeca1) {
        // Depois é bom alterar isso para deixar mais legível e otimizado, mas a princípio vai funcionar
        return pedra.getNumCima() == cabeca1 ||pedra.getNumBaixo() == cabeca1;
    }
    

    // Retorna um array com o index de cada pedra que pode ser jogada
    protected int[] verificaPedrasDisponiveis(int cabeca1, int cabeca2) {
        int aux = 0, indexPedra = 0; 
        int[] pedrasDispo = new int[6];

        for(Pedra ped : pedrasDisponiveis) {
            if(checkPossivel(ped, cabeca1) || checkPossivel(ped, cabeca2)) {
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
    public abstract Pedra jogar(int cabeca1, int cabeca2) throws NaoHaPedrasParaSeremJogadas;
} 