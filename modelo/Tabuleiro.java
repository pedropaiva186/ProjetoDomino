package modelo;
import java.util.*;

import modelo.erros_adicionais.NaoHaPedrasParaSeremJogadas;

public class Tabuleiro {

    private int[] pontas = new int[2];
    private List<Pedra> dorme = new ArrayList<>();
    private LinkedList<Pedra> pedrasTabuleiro = new LinkedList<>();
    private Jogador[] jogadores = new Jogador[4];
    private int passes = 0;
    private int turno;
    private boolean fim = false;


    public Tabuleiro(String nome){
        setDorme();

        jogadores[0] = new Humano(nome);
        for(int i = 1; i <= 3; i++) {
            jogadores[i] = new Maquina(String.format("bot %d", i));
        }

        for(int i = 0; i < 4; i++) {
            setBaralho(jogadores[i]);
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
    
    // Método responsável por jogar a carraço de 6 na partida e, assim, começar as rodadas.
    public void iniciarPartida() {
        boolean achou = false;

        for(int i = 0; i < 4; i++) {
            List<Pedra> pedras = jogadores[i].getPedras();

            for(Pedra pedra: pedras) {
                if(pedra.getNumCima() == 6 && pedra.getNumBaixo() == 6) {
                    achou = true;
                    pedrasTabuleiro.add(pedra);
                    pedras.remove(pedra);
                    turno = (i + 1)%4;
                    break;
                }
            }
            if(achou) {
                break;
            }
        }

        passes = 0;
        setPontas();
    }

    // Método responsável por colocar as pedras na mesa e remover do baralho do player.
    public void addTabuleiro(Jogador player) {
        Pedra jogada;
        int resultado = verificarFim();

        if(verificarFim() == 4) {
            // Será implementado depois.
            System.out.println("Fim da partida - Jogo trancado.");
            int vencedor = contagemPontos();
            if(vencedor == -1) {
                System.out.println("Empate!");
            } else {
                System.out.printf("Fim da partida - Player %s venceu.\n", jogadores[vencedor].getNome());
            }
            fim = true;
            return;
        } else if(verificarFim() < 4) {
            System.out.printf("Fim da partida - Player %s venceu.\n", jogadores[resultado].getNome());
            fim = true;
            return;
        }

        try{
            jogada = player.jogar(pontas[0], pontas[1]);
        } catch(NaoHaPedrasParaSeremJogadas e) {
            System.out.printf("Jogador %d não possui pedras para jogar.\nPassando o turno para o próximo jogador.\n", turno);
            turno = (turno + 1)%4;
            passes++;
            return;
        }
        
        int escolha;
        boolean direction; // false = esquerda; true = direita

        if(player.checkPossivel(jogada, pontas[0]) && player.checkPossivel(jogada, pontas[1])) {
            // Depois implementar a decisão da esquerda ou direita - Essa implementação abaixo é temporária para realizar debug;
            // Depois teremos que pensar em um jeito de aplicar isso para os bots.
            if(turno == 0) {
                while(true) {
                    System.out.println("Digite em qual lado você quer jogar!\n0 para esquerda - 1 para direita");
                    escolha = Leitor.leitor.nextInt();
                    if(escolha == 1) {
                        direction = true;
                        break;
                    } else if(escolha == 0) {
                        direction = false;
                        break;
                    }
                    System.out.println("Digite uma resposta válida!");
                }
            } else {
                Random randomizer2 = new Random();
                int res = randomizer2.nextInt(1);
                if(res == 1) {
                    direction = true;
                } else {
                    direction = false;
                }
            }
        } else if(player.checkPossivel(jogada, pontas[0])) {
            direction = false;
        } else {
            direction = true;
        }
        //direita
        if(direction) {
            if(jogada.getNumCima() == pontas[1]) {
                pedrasTabuleiro.addLast(jogada);
            } else {
                pedrasTabuleiro.addLast(new Pedra(jogada.getNumBaixo(), jogada.getNumCima()));
            }
            player.getPedras().remove(jogada);
        } else{
            if(jogada.getNumBaixo() == pontas[0]) {
                pedrasTabuleiro.addFirst(jogada);
            } else {
                pedrasTabuleiro.addFirst(new Pedra(jogada.getNumBaixo(), jogada.getNumCima()));
            }
            player.getPedras().remove(jogada);
        }

        turno = (turno + 1)%4;
        passes = 0;
        setPontas();
    }

    // Método responsável por colocar o valor das pontas do tabuleiro.
    public void setPontas(){
        pontas[0] = pedrasTabuleiro.getFirst().getNumCima();
        pontas[1] = pedrasTabuleiro.getLast().getNumBaixo();
    }

    // Método responsável por retornar o array formado pelas pontas do tabuleiro.
    public int[] getPontas(){
        return pontas;
    }

    // Método responsável por retornar as pedras que estão na mesa.
    public List<Pedra> getPedrasTabuleiro(){
        return pedrasTabuleiro;
    }

    // Método responsável por verificar se a partida de dominó já acabou, retornando 4 se for para a contagem, 5 se não for o fim e i de 0 a 3 de acordo com o vitorioso.
    public int verificarFim(){
        if(passes >= 4) {
            return 4;
        }

        for(int i = 0; i < 4; i++) {
            if(jogadores[i].getPedras().size() == 0) {
                return i;
            }
        }

        return 5;
    } 

    // Método encarregado de obter o estado do jogo.
    public boolean getEstado(){
        return fim;
    }

    // Método responsável por informar ao usuário de quem é o turno.
    public int getTurno(){
        return turno;
    }

    public int contagemPontos(){
        int[] pontos = new int[4];
        int maior = -1, index = 0, rep = 0;
        

        for(int i = 0; i < 4; i++){
            pontos[i] = jogadores[i].somaPontos();
            if(pontos[i] > maior) {
                index = i;
            }
        }

        for(int i = 0; i < 4; i++) {
            if(pontos[i] == maior) {
                rep++;
            }
        }

        if(rep > 1) {
            return -1;
        }

        return index;
    }

}
