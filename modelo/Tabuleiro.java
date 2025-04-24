package modelo;
import java.util.*;
import modelo.erros_adicionais.NaoHaPedrasParaSeremJogadas;
import modelo.erros_adicionais.PararAgora;

public class Tabuleiro {
    private final int[] pontas = new int[2];
    private final List<Pedra> dorme = new ArrayList<>();
    private final LinkedList<Pedra> pedrasTabuleiro = new LinkedList<>();
    private final Jogador[] jogadores = new Jogador[4];
    private final int qtdHumanos;
    private int passes = 0;
    private int turno;
    private int rodadas = 1;
    private boolean fim = false;

    public Tabuleiro(String[] nome, int qtdHumanos) {
        setDorme();
        // Adicionei o atributo qtdHumanos para conseguir utilizar em métodos mais a frente.
        this.qtdHumanos = qtdHumanos;

        // Fiz essa pequena modificação para permitir que haja mais jogadores humanos
        for(int i = 0; i <= 3; i++) {
            if(i < this.qtdHumanos) {
                jogadores[i] = new Humano(nome[i]);
            } else {
                jogadores[i] = new Maquina(String.format("bot %d", i+1-qtdHumanos));
            }
        }

        for(int i = 0; i < 4; i++) {
            setBaralho(jogadores[i]);
        }
    }

    // Método responsável pela criação das pedras do dorme. Caso a operação seja realizada com sucesso, retorna true; caso contrário, retorna false.
    private boolean setDorme() {
        
        if(dorme.isEmpty()) {
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
        for(int i = 0; i < 6; i++) {
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

    // Método responsável por informar ao usuário de quem é o turno.
    public int getTurno(){
        return turno;
    }

    // Método responsável por retornar as pedras que estão na mesa.
    public List<Pedra> getPedrasTabuleiro(){
        return pedrasTabuleiro;
    }

    // Método responsável por buscar a sena no baralho de um jogador.
    private boolean acharCarroca(int cabeca, List<Pedra> pedras, int indexPlayer) {
        for(Pedra pedra: pedras) {
            if(pedra.getNumCima() == cabeca && pedra.getNumBaixo() == cabeca) {
                pedrasTabuleiro.add(pedra);
                pedras.remove(pedra);
                
                System.out.printf("Jogador %s começou a partida.\n", jogadores[indexPlayer].getNome());

                turno = (indexPlayer + 1)%4;
                return true;
            }
        }
        return false;
    }


    // Método responsável por jogar a carroça de 6 na partida e, assim, começar as rodadas.
    public void iniciarPartida() {
        boolean achou = false;

        for(int i = 6; i > 1; i--) {
            for(int j = 0; j < 4; j++) {
                List<Pedra> pedras = jogadores[j].getPedras();

                achou = acharCarroca(i, pedras, j);
                
                if(achou) {
                    break;
                }
            }
            if(achou){
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
            if(jogadores[i].getPedras().isEmpty()) {
                return i;
            }
        }

        return 5;
    } 

    // Verifica se a partida chegou ao fim.
    public boolean isFim(){
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

    // Método para recolher o lado no qual o player quer jogar a própria pedra
    private boolean getDirection() {
        int decisao;

        while (true) { 
            System.out.println("Digite em qual lado você quer jogar!\n0 para esquerda - 1 para direita");
            decisao = Leitor.leitor.nextInt();
            if(1 >= decisao && decisao >= 0) {
                return decisao == 1;
            }
            System.out.println("Digite uma resposta válida!");
        }
    }

    // Método utilizado para decidir onde a pedra será jogada.
    private void decidirJogada(Jogador jogadorAtual, Pedra escolha) {
        boolean direction; // false = jogar à esquerda; true = jogar à direita

        if(jogadorAtual.checkPossivel(escolha, pontas[0]) && jogadorAtual.checkPossivel(escolha, pontas[1])) {
            if(turno < qtdHumanos) {
                direction = getDirection();
            }else {
                Random randomizer2 = new Random();
                int res = randomizer2.nextInt(1);
                direction = res == 1;
            }
        } else direction = !jogadorAtual.checkPossivel(escolha, pontas[0]); // Só depende da negação da ponta[0], uma vez que nunca ocorre o caso em que os dois são falsos nessa função.
        //direita
        if(direction) {
            if(escolha.getNumCima() == pontas[1]) {
                pedrasTabuleiro.addLast(escolha);
                System.out.printf("O jogador jogou a pedra %d %d na direita.\n", escolha.getNumCima(), escolha.getNumBaixo());
            }else {
                pedrasTabuleiro.addLast(new Pedra(escolha.getNumBaixo(), escolha.getNumCima()));
                System.out.printf("O jogador jogou a pedra %d %d na direita.\n", escolha.getNumBaixo(), escolha.getNumCima());
            }
        }else {
            if(escolha.getNumBaixo() == pontas[0]) {
                pedrasTabuleiro.addFirst(escolha);
                System.out.printf("O jogador jogou a pedra %d %d na esquerda.\n", escolha.getNumCima(), escolha.getNumBaixo());
            }else {
                pedrasTabuleiro.addFirst(new Pedra(escolha.getNumBaixo(), escolha.getNumCima()));
                System.out.printf("O jogador jogou a pedra %d %d na esquerda.\n", escolha.getNumBaixo(), escolha.getNumCima());
            }
        }

        jogadorAtual.getPedras().remove(escolha);
    }

    // Método responsável por colocar as pedras na mesa e remover do baralho do player.
    public void addTabuleiro(Jogador player) throws PararAgora{
        Pedra jogada;

        System.out.printf("Turno do jogador %s\n", jogadores[turno].getNome());

        if(isFim()){
            return;
        }

        rodadas++;

        if(turno < qtdHumanos) {
            System.out.printf("  Pedras do jogador:\n",  jogadores[turno].getNome());
            for(int i = 0; i < jogadores[turno].verificaNumPedras(); i++) {
                Pedra pedraI = jogadores[turno].getPedras().get(i);
                System.out.printf("  {%d %d}", pedraI.getNumCima(), pedraI.getNumBaixo());
            }
            System.out.println("");
        }

        try{
            jogada = player.jogar(pontas[0], pontas[1]);

            // Se caso o player quiser parar o jogo, esse erro vai subir até encerrar seu funcionamento
        } catch(PararAgora e) {
            throw e;
        } catch(NaoHaPedrasParaSeremJogadas e) {
            System.out.printf("Jogador %d não possui pedras para jogar.\nPassando o turno para o próximo jogador.\n", turno+1); // somei 1 pois o código estava retornando coisas como "Jogador 0 não tem pedras para jogar"
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