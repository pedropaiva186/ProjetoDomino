package modelo;

import java.util.*;
import modelo.erros_adicionais.NaoHaPedrasParaSeremJogadas;
import modelo.erros_adicionais.PararAgora;

public abstract class Jogador implements Jogador_Interface{
    
    protected final String nomeJog;
    protected final ArrayList<Pedra> pedrasDeck;

    public Jogador(String nomeJog, ArrayList<Pedra> pedras) {
        this.nomeJog = nomeJog;
        pedrasDeck = pedras;
    }

    // Construtor no qual é necessário adicionar as pedras depois
    public Jogador(String nomeJog) {
        this.nomeJog = nomeJog;
        pedrasDeck = new ArrayList<>();
    }

    @Override
    public String getNome() {
        return nomeJog;
    }

    @Override
    public void addPedra(Pedra pedra) {
        pedrasDeck.add(pedra);
    }

    // Tenta retirar a pedra, mas caso haja um erro, ele lança o erro
    @Override
    public void retiraPedra(int index) throws IndexOutOfBoundsException{
        try {
            pedrasDeck.remove(index);
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException();
        }
    }

    // Método para retirar a pedra a partir dela mesma, e não pelo index
    @Override
    public void retiraPedra(Pedra ped) {
        pedrasDeck.remove(ped);
    }

    // retorna o número de pedras
    @Override
    public int verificaNumPedras() {
        return pedrasDeck.size();
    }
    // retorna o array de pedras.
    @Override
    public ArrayList<Pedra> getPedras() {
        return pedrasDeck;
    }

    // Faz a mesma verificação para retornar um elementro Pedra
    @Override
    public Pedra getPedra(int index) throws IndexOutOfBoundsException{
        Pedra retorno;

        try {
            retorno = pedrasDeck.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException();
        }

        return retorno;
    }

    // Serve para verificar se determinada pedra pode ser jogada dependendo da cabeça disponível no tabuleiro
    @Override
    public boolean checkPossivel(Pedra pedra, int cabeca1) {
        return pedra.getNumCima() == cabeca1 || pedra.getNumBaixo() == cabeca1;
    }
    

    // Retorna um array com o index de cada pedra que pode ser jogada
    @Override
    public List<Integer> verificaPedrasDisponiveis(int cabeca1, int cabeca2) {
        int indexPedra = 0; 
        List<Integer> pedrasDispo = new ArrayList<>();
        
        for(Pedra ped : pedrasDeck) {
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
    @Override
    public int somaPontos(){
        int soma = 0;
        for(Pedra pedra: pedrasDeck){
            soma += pedra.getNumBaixo() + pedra.getNumCima();
        }
        return soma;
    }

    @Override
    public abstract Pedra jogar(int cabeca1, int cabeca2) throws NaoHaPedrasParaSeremJogadas, PararAgora;
} 