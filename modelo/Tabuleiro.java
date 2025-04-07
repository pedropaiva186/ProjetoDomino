package modelo;
import java.util.*;

public class Tabuleiro {

    private int[] pontas = new int[2];
    private List<Pedra> dorme = new ArrayList<>();
    private LinkedList<Pedra> pedrasTabuleiro = new LinkedList<>();
    private Jogador[] jogadores = new Jogador[4];

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
        int index = 0;

        for(int i = 0; i < 4; i++) {
            List<Pedra> pedras = jogadores[i].getPedras();

            for(Pedra pedra: pedras) {
                if(pedra.getNumCima() == 6 && pedra.getNumBaixo() == 6) {
                    achou = true;
                    index = pedras.indexOf(pedra);
                }
            }

            if(achou) {
                pedrasTabuleiro.add(pedras.get(index));
                pedras.remove(index);
                break;
            }
        }

        setPontas();
    }

    // Método responsável por colocar as pedras na mesa e remover do baralho do player.
    public void addTabuleiro(Jogador player) {
        Pedra jogada = player.jogar(pontas[0], pontas[1]);

        //Debug
        System.out.printf("%d %d %d %d\n", jogada.getNumCima(), jogada.getNumBaixo(), pontas[0], pontas[1]);
        
        //Debug
        System.out.println("Entrando na checagem!");

        if(player.checkPossivel(jogada, pontas[0]) && player.checkPossivel(jogada, pontas[1])) {
            // Depois implementar a decisão da esquerda ou direita - Essa implementação abaixo é temporária para realizar debug;
            pedrasTabuleiro.addFirst(jogada);
            System.out.println("Adicionado! - 3");
            player.getPedras().remove(jogada);
        } else if(player.checkPossivel(jogada, pontas[0])) {
            pedrasTabuleiro.addFirst(jogada);
            //Debug
            System.out.println("Adicionado! - 1");
            player.getPedras().remove(jogada);
        } else {
            pedrasTabuleiro.addLast(jogada);
            //Debug
            System.out.println("Adicionado! - 2");
            player.getPedras().remove(jogada);
        }
        setPontas();
    }

    // Método responsável por colocar o valor das pontas do tabuleiro.
    public void setPontas(){
        pontas[0] = pedrasTabuleiro.getFirst().getNumBaixo();
        pontas[1] = pedrasTabuleiro.getLast().getNumCima();
    }

    // Método responsável por retornar o array formado pelas pontas do tabuleiro.
    public int[] getPontas(){
        return pontas;
    }

    // Método responsável por retornar as pedras que estão na mesa.
    public List<Pedra> getPedrasTabuleiro(){
        return pedrasTabuleiro;
    }
}
