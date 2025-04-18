package modelo;

import java.util.*;
import modelo.erros_adicionais.NaoHaPedrasParaSeremJogadas;
import modelo.erros_adicionais.PararAgora;

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

    // Serve para verificar se determinada pedra pode ser jogada dependendo da cabeça disponível no tabuleiro
    public boolean checkPossivel(Pedra pedra, int cabeca1) {
        return pedra.getNumCima() == cabeca1 || pedra.getNumBaixo() == cabeca1;
    }
    

    // Retorna um array com o index de cada pedra que pode ser jogada
    protected List<Integer> verificaPedrasDisponiveis(int cabeca1, int cabeca2) {
        int indexPedra = 0; 
        List<Integer> pedrasDispo = new ArrayList<>();
        
        for(Pedra ped : pedrasDisponiveis) {
            if(checkPossivel(ped, cabeca1) || checkPossivel(ped, cabeca2)) {
                pedrasDispo.add(indexPedra);
            }
            indexPedra++;
        }

        // Retorna null caso não haja pedras para o jogador jogar
        if(pedrasDispo.isEmpty()) {
            return null;
        }

        return pedrasDispo;
    }

    // Esta função serve para dar ao jogador a liberdade de escolher uma pedra e retorná-la

    public int somaPontos(){
        int soma = 0;
        for(Pedra pedra: pedrasDisponiveis){
            soma += pedra.getNumBaixo() + pedra.getNumCima();
        }
        return soma;
    }

    public abstract Pedra jogar(int cabeca1, int cabeca2) throws NaoHaPedrasParaSeremJogadas, PararAgora;
} 