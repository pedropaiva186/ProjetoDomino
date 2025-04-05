package modelo;
import java.util.*;

public class Tabuleiro {
    private char[] pontas = new char[2];
    private List<Pedra> dorme = new ArrayList<>();
    private List<Pedra> pedrasEsquerda = new ArrayList<>();
    private List<Pedra> pedrasDireita = new ArrayList<>();
    private Jogador[] jogadores = new Jogador[2];

    public Tabuleiro(){
        setDorme();
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
        } else {
            return false;
        }

        return true;
    }

    //Método responsável por obter a lista de pedras do dorme.
    public List<Pedra> getDorme() {
        return dorme;
    }

    // Método responsável por colocar o valor das pontas do tabuleiro.
    public void setPontas(){
        int finalDireita = pedrasDireita.size() - 1;
        int finalEsquerda = pedrasEsquerda.size() - 1;

        pontas[0] = (char) pedrasDireita.get(finalDireita).getNumCima();
        pontas[1] = (char) pedrasEsquerda.get(finalEsquerda).getNumBaixo();
    }
}
