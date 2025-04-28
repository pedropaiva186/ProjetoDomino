package modelo;

import java.util.ArrayList;
import java.util.List;
import modelo.erros_adicionais.NaoHaPedrasParaSeremJogadas;
import modelo.erros_adicionais.PararAgora;

public interface  Jogador_Interface {
    public String getNome();
    public void addPedra(Pedra pedra);
    public void retiraPedra(int index);
    public void retiraPedra(Pedra ped);
    public int verificaNumPedras();
    public ArrayList<Pedra> getPedras();
    public Pedra getPedra(int index) throws IndexOutOfBoundsException;
    public boolean checkPossivel(Pedra pedra, int cabeca1);
    public List<Integer> verificaPedrasDisponiveis(int cabeca1, int cabeca2);
    public int somaPontos();
    public abstract Pedra jogar(int cabeca1, int cabeca2) throws NaoHaPedrasParaSeremJogadas, PararAgora;
}
