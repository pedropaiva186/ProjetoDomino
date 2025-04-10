package modelo.erros_adicionais;

public class PararAgora extends Exception {

    @Override
    public String getMessage() {
        return "Parar a execução do programa agora";
    }
}
