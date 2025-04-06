package modelo;
import java.util.*;

import modelo.Pedra;

public class Tabuleiro {

    private int[] pontas = new int[2];
    private List<Pedra> dorme = new ArrayList<>();
    private List<Pedra> pedrasEsquerda = new ArrayList<>();
    private List<Pedra> pedrasDireita = new ArrayList<>();
    private Jogador[] jogadores = new Jogador[4];

    public Tabuleiro(String nome){
        setDorme();
        jogadores[0] = new Humano(nome);
        for(int i = 1; i <= 3; i++) {
            jogadores[i] = new Maquina(String.format("bot %d", i));
        }
    }

    // Método responsável pela criação das pedras do dorme. Caso a operação seja realizada com sucesso, retorna true; caso contrário, retorna false.
    public boolean setDorme() {
        
        if(dorme.size() == 0){
            for(char cima = 0; cima <= 6; cima++) {
                for(char baixo = 0; baixo <= 6; baixo++) {
                    if(cima >= baixo) {
                        dorme.add(new Pedra(cima, baixo));
                    }
                }
            }
        }else{
            return false;
        }

        return true;
    }

    //Método responsável por obter a lista de pedras do dorme.
    public List<Pedra> getDorme() {
        return dorme;
    }
    // Método responsável por preencher o deck de um player.
    public void setBaralho(Jogador player) {
        Random randomizer = new Random();
        for(int i = 0; i <= 6; i++) {
            int index = randomizer.nextInt(dorme.size());

            player.addPedra(dorme.get(index));
            dorme.remove(index);
        } 
    }
    // Método responsável por retornar a referência a um player.
    public Jogador getPlayer(int index) {
        Jogador retorno = null;
        try{
            retorno = jogadores[index];
        }catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Índice inválido");
        }
        return retorno;
    }
    
    // Método responsável por colocar as pedras na mesa e remover do baralho do player.
    public void addTabuleiro(Jogador player) {
        Pedra jogada = player.jogar(pontas[0], pontas[1]);

        if(player.checkPossivel(jogada, pontas[0]) && player.checkPossivel(jogada, pontas[1])) {
            // Depois implementar a decisão da esquerda ou direita;
        } else if(player.checkPossivel(jogada, pontas[0])) {
            pedrasEsquerda.add(jogada);
            player.getPedras().remove(jogada);
        } else {
            pedrasDireita.add(jogada);
            player.getPedras().remove(jogada);
        }
    }

    // Método responsável por colocar o valor das pontas do tabuleiro.
    public void setPontas(){
        int finalDireita = pedrasDireita.size() - 1;
        int finalEsquerda = pedrasEsquerda.size() - 1;

        pontas[0] = pedrasDireita.get(finalDireita).getNumCima();
        pontas[1] = pedrasEsquerda.get(finalEsquerda).getNumBaixo();
    }

    // Método responsável por retornar o array formado pelas pontas do tabuleiro.
    public int[] getPontas(){
        return pontas;
    }
}
