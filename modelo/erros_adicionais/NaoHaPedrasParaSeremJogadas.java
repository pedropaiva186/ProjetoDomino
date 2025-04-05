package modelo.erros_adicionais;

public class NaoHaPedrasParaSeremJogadas extends RuntimeException{

    public NaoHaPedrasParaSeremJogadas() {
        super("O jogador passou a vez");
    }
}
