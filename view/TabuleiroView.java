package view;

import java.util.*;
import modelo.Tabuleiro;
import modelo.Pedra;

public class TabuleiroView {
    public static void listarPedrasTabuleiro(Tabuleiro tabuleiro) {
        List<Pedra> mesa = tabuleiro.getPedrasTabuleiro();
        System.out.printf("-------------------------%d° rodada-------------------------\n", tabuleiro.getRodadas());
        System.out.println("Pedras na mesa");
        for(Pedra pedraAtual: mesa) {
            System.out.printf("{%d %d} ", pedraAtual.getNumCima(), pedraAtual.getNumBaixo());
        }
        System.out.println("\n-----------------------------------------------------------");
    }
}
