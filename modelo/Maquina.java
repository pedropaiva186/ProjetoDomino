package modelo;

import java.util.*;
import java.util.Random;
import modelo.erros_adicionais.NaoHaPedrasParaSeremJogadas;

public class Maquina extends Jogador{
    
    public Maquina(String nomeJog, ArrayList<Pedra> pedras) {
        super(nomeJog, pedras);
    }

    public Maquina(String nomeJog) {
        super(nomeJog);
    }

    // Esse método basicamente replica a forma como o método de jogar pedra funciona, porém, ele aleatoriza o index da pedra jogada
    public Pedra jogar(int cabeca1, int cabeca2) throws NaoHaPedrasParaSeremJogadas{
        Random randomizer = new Random();
        List<Integer> pedrasDispo = super.verificaPedrasDisponiveis(cabeca1, cabeca2);
        int index;

        // Verificando se não há pedras disponíveis
        if(pedrasDispo == null) {
            throw new NaoHaPedrasParaSeremJogadas();
        }

        // Pegando um index aleatório dos disponíveis
        index = pedrasDispo.get(randomizer.nextInt(pedrasDispo.size()));

        // Retornando a pedra que será jogada
        return pedrasDisponiveis.get(index);
    }
}
