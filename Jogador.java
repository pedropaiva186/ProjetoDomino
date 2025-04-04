import java.util.ArrayList;
import java.util.Scanner;

public class Jogador {

    private final String nomeJog;
    private final ArrayList<Pedra> pedrasDisponiveis;

    // Construtor com a passagem das pedras nele próprio
    public Jogador(String nomeJog, ArrayList<Pedra> pedras) {
        this.nomeJog = nomeJog;
        pedrasDisponiveis = pedras;
    }

    // Construtor no qual é necessário adicionar as pedras depois
    public Jogador(String nomeJog) {
        this.nomeJog = nomeJog;
        pedrasDisponiveis = new ArrayList<>();
    }

    public String getNome() {
        return nomeJog;
    }

    public void addPedra(Pedra pedra) {
        pedrasDisponiveis.add(pedra);
    }

    public boolean retiraPedra(int index) {
        // Verifica se o index é possível de retirar um elemento, e retorna "false" ou "true" a depender se a função funcionar ou não
        try {
            pedrasDisponiveis.remove(index);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }

        return true;
    }

    // retorna o número de pedras
    public int verificaNumPedras() {
        return pedrasDisponiveis.size();
    }

    public ArrayList<Pedra> getPedras() {
        return pedrasDisponiveis;
    }

    // Faz a mesma verificação para retornar um elementro Pedra, mas aqui se não encontrar ele retorna "null"
    public Pedra getPedra(int index) {
        Pedra retorno;

        try {
            retorno = pedrasDisponiveis.get(index);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }

        return retorno;
    }


    // Serve para verificar se determinada pedra pode ser jogada dependendo das cabeças disponíveis no tabuleiro
    private boolean verificaPedraPossiveisParaJogar(Pedra pedra, char cabeca1, char cabeca2) {
        // Depois é bom alterar isso para deixar mais legível e otimizado, mas a princípio vai funcionar
        return pedra.getNumCima() == cabeca1 || pedra.getNumCima() == cabeca2 || pedra.getNumBaixo() == cabeca1 || pedra.getNumBaixo() == cabeca2;
    }

    // Esta função serve para dar ao jogador a liberdade de escolher uma pedra e retorná-la
    // Mas provalvemente esta função vai mudar, para dar a liberdade do jogador escolher apenas as pedras disponíveis
    public Pedra jogadorJogaPedra(char cabeca1, char cabeca2) {
        // De alguma forma o jogador vai escolher o index da pedra que vamos retornar
        // A princípio farei com scanner, mas provalvemente, isso vai mudar
        int index;
        Pedra pedra;

        // Recebendo o index do usuário e tratando de forma que ele só pare quando receber um valor válido
        try(Scanner leitor = new Scanner(System.in)) {
            while(true) {
                System.out.print("Digite o index de uma pedra: ");
                index = leitor.nextInt();
                pedra = getPedra(index);

                // Verifica se a pedra foi encontrada e pode ser jogada no tabuleiro
                if(pedra != null && verificaPedraPossiveisParaJogar(pedra, cabeca1, cabeca2)) {
                    break;
                } else {
                    System.out.println("Dígito inválido, tente novamente");
                }
            }

            leitor.close();
        } catch (Exception e) {
            return null;
        }

        return pedra;
    }
}
