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
    private int rodadas = 0;
    private boolean fim = false;

    public Tabuleiro(String nome) {
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
    private boolean setDorme() {
        
        if(dorme.size() == 0) {
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
    private void setBaralho(Jogador player) {
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
    
    public int getRodadas() {
        return rodadas;
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

    // Método encarregado de obter o estado do jogo.
    public boolean getEstado(){
        return fim;
    }

    // Método responsável por informar ao usuário de quem é o turno.
    public int getTurno(){
        return turno;
    }

    // Método responsável por retornar as pedras que estão na mesa.
    public List<Pedra> getPedrasTabuleiro(){
        return pedrasTabuleiro;
    }

    // Método responsável por buscar a sena no baralho de um jogador.
    private boolean acharSena(List<Pedra> pedras, int indexPlayer) {
        for(Pedra pedra: pedras) {
            if(pedra.getNumCima() == 6 && pedra.getNumBaixo() == 6) {
                pedrasTabuleiro.add(pedra);
                pedras.remove(pedra);
                
                turno = (indexPlayer + 1)%4;
                return true;
            }
        }
        return false;
    }


    // Método responsável por jogar a carraço de 6 na partida e, assim, começar as rodadas.
    public void iniciarPartida() {
        boolean achou = false;

        for(int i = 0; i < 4; i++) {
            List<Pedra> pedras = jogadores[i].getPedras();

            achou = acharSena(pedras, i);

            if(achou) {
                break;
            }
        }

        passes = 0;
        rodadas++;
        setPontas();
    }

    // Método responsável por fazer a contagem de pontos dos jogadores.
    private int contagemPontos(){
        int[] pontos = new int[4];
        int menor = 100, index = 0, repet = 0;
        
        for(int i = 0; i < 4; i++){
            pontos[i] = jogadores[i].somaPontos();
            if(pontos[i] < menor) {
                index = i;
            }
        }

        for(int i = 0; i < 4; i++) {
            if(pontos[i] == menor) {
                repet++;
            }
        }

        if(repet > 1) {
            return -1;
        }

        return index;
    }

    // Método responsável por verificar se a partida de dominó já acabou, retornando 4 se for para a contagem, 5 se não for o fim e i de 0 a 3 de acordo com o vitorioso.
    private int verificarResultado(){
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

    // Verifica se a partida chegou ao fim.
    private boolean isFim(){
        int resultado = verificarResultado();

        if(resultado == 4) {
            System.out.println("Fim da partida - Jogo trancado.");
            int vencedor = contagemPontos();
            if(vencedor == -1) {
                System.out.println("Empate!");
            } else {
                System.out.printf("Fim da partida - Player %s venceu.\n", jogadores[vencedor].getNome());
            }
            fim = true;
        } else if(resultado < 4) {
            System.out.printf("Fim da partida - Player %s venceu.\n", jogadores[resultado].getNome());
            fim = true;
        }

        return fim;
    }

    // Método utilizado para decidir onde a pedra será jogada.
    private void decidirJogada(Jogador jogadorAtual, Pedra escolha) {
        int decisao;
        boolean direction; // false = jogar à esquerda; true = jogar à direita

        if(jogadorAtual.checkPossivel(escolha, pontas[0]) && jogadorAtual.checkPossivel(escolha, pontas[1])) {
            if(turno == 0) {
                while(true) {
                    System.out.println("Digite em qual lado você quer jogar!\n0 para esquerda - 1 para direita");
                    decisao = Leitor.leitor.nextInt();
                    if(decisao == 1) {
                        direction = true;
                        break;
                    } else if(decisao == 0) {
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
        } else if(jogadorAtual.checkPossivel(escolha, pontas[0])) {
            direction = false;
        } else {
            direction = true;
        }
        //direita
        if(direction) {
            if(escolha.getNumCima() == pontas[1]) {
                pedrasTabuleiro.addLast(escolha);
                System.out.printf("O jogador jogou a pedra %d %d na direita.\n", escolha.getNumCima(), escolha.getNumBaixo());
            } else {
                pedrasTabuleiro.addLast(new Pedra(escolha.getNumBaixo(), escolha.getNumCima()));
                System.out.printf("O jogador jogou a pedra %d %d na direita.\n", escolha.getNumBaixo(), escolha.getNumCima());
            }
        } else{
            if(escolha.getNumBaixo() == pontas[0]) {
                pedrasTabuleiro.addFirst(escolha);
                System.out.printf("O jogador jogou a pedra %d %d na esquerda.\n", escolha.getNumCima(), escolha.getNumBaixo());
            } else {
                pedrasTabuleiro.addFirst(new Pedra(escolha.getNumBaixo(), escolha.getNumCima()));
                System.out.printf("O jogador jogou a pedra %d %d na esquerda.\n", escolha.getNumBaixo(), escolha.getNumCima());
            }
        }

        jogadorAtual.getPedras().remove(escolha);
    }

    // Método responsável por colocar as pedras na mesa e remover do baralho do player.
    public void addTabuleiro(Jogador player) {
        Pedra jogada;

        System.out.printf("Turno de %s\n", jogadores[turno].getNome());

        if(isFim()){
            return;
        }

        rodadas++;

        if(turno == 0) {
            System.out.printf("Número de pedras do jogador: %d\n", jogadores[0].verificaNumPedras());
        }

        try{
            jogada = player.jogar(pontas[0], pontas[1]);
        } catch(NaoHaPedrasParaSeremJogadas e) {
            System.out.printf("Jogador %d não possui pedras para jogar.\nPassando o turno para o próximo jogador.\n", turno);
            turno = (turno + 1)%4;
            passes++;
            return;
        }

        decidirJogada(player, jogada);

        turno = (turno + 1)%4;
        passes = 0;
        setPontas();
    }
}