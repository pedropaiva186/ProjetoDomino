package view;

import java.util.*;
import modelo.Tabuleiro;
import modelo.Jogador;
import modelo.Pedra;

public class TabuleiroView {
    public static void listarPedrasTabuleiro(Tabuleiro tabuleiro) {
        List<Pedra> mesa = tabuleiro.getPedrasTabuleiro();
        System.out.printf("-------------------------%d° rodada-------------------------\n", tabuleiro.getRodadas());
        System.out.println("Pedras na mesa: ");
        for(Pedra pedraAtual: mesa) {
            System.out.printf("{%d %d} ", pedraAtual.getNumCima(), pedraAtual.getNumBaixo());
        }
        System.out.println("\n-----------------------------------------------------------");
    }

    public static void showPedrasDisponiveis(int cabeca1, int cabeca2, Jogador jog) {
        List<Integer> indicesPossiveis = jog.verificaPedrasDisponiveis(cabeca1, cabeca2);
        List<Pedra> pedrasDisp = jog.getPedras();

        System.out.println("----------------------------------------------");
        System.out.print("Pedras disponíveis:\n");
        for(int i : indicesPossiveis) {
            System.out.printf("%d - {%d %d} | ", i, pedrasDisp.get(i).getNumCima(), pedrasDisp.get(i).getNumBaixo());
        }
        System.out.println("\n----------------------------------------------");
        System.out.print("Digite o index de uma pedra entre as disponíveis ou digite '99' para encerrar: ");
    }
}
