import busca.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// A classe Travessia implementa a interface Estado
public class Travessia implements Estado {
    final int missionariosEsquerda, canibaisEsquerda, missionariosDireita, canibaisDireita;
    char barco; // 'e' para esquerda, 'd' para direita
    String op;
    
    // Construtor da classe
    public Travessia(int missionariosEsquerda, int canibaisEsquerda, int missionariosDireita, int canibaisDireita, char barco, String op) {
        this.missionariosEsquerda = missionariosEsquerda;
        this.canibaisEsquerda = canibaisEsquerda;
        this.missionariosDireita = missionariosDireita;
        this.canibaisDireita = canibaisDireita;
        this.barco = barco;
        this.op = op;
    }

    @Override
    public String getDescricao() {
        // Retorna uma descrição do estado atual
        return "(" + missionariosEsquerda + "M, " + canibaisEsquerda + "C, " + missionariosDireita + "M, " + canibaisDireita + "C, " + barco + ")";
    }

    @Override
    public boolean ehMeta() {
        // Verifica se o estado atual é o estado meta desejado
        return missionariosEsquerda == 0 && canibaisEsquerda == 0 && missionariosDireita == 3 && canibaisDireita == 3 && barco == 'd';
    }

    @Override
    public int custo() {
        // Retorna o custo do estado (neste caso, sempre 1)
        return 1;
    }

    @Override
    public List<Estado> sucessores() {
        // Gera e retorna uma lista de estados sucessores válidos
        List<Estado> visitados = new ArrayList<>();
        levar1M(visitados);
        levar1C(visitados);
        levar1M1C(visitados);
        levar2M(visitados);
        levar2C(visitados);
        return visitados;
    }
    
    // Método privado para verificar se um estado é válido
    private boolean ehValido(Travessia estado) {
        // Verifica se o número de canibais não é maior que o número de missionários em qualquer margem.
        if (estado.missionariosEsquerda > 0 && estado.missionariosEsquerda < estado.canibaisEsquerda) {
            return false;
        }
        if (estado.missionariosDireita > 0 && estado.missionariosDireita < estado.canibaisDireita) {
            return false;
        }
        return true;
    }
    
     // Métodos privados para gerar estados sucessores válidos
    private void levar1M(List<Estado> visitados) {
        // Verifica se é possível levar 1 missionário
        if (barco == 'e' && missionariosEsquerda >= 1) {
            Travessia novo = new Travessia(missionariosEsquerda - 1, canibaisEsquerda, missionariosDireita + 1, canibaisDireita, 'd', "Levando 1 missionário para a direita");
            if (ehValido(novo)) {
                visitados.add(novo);
            }
        } else if (barco == 'd' && missionariosDireita >= 1) {
            Travessia novo = new Travessia(missionariosEsquerda + 1, canibaisEsquerda, missionariosDireita - 1, canibaisDireita, 'e', "Levando 1 missionário para a esquerda");
            if (ehValido(novo)) {
                visitados.add(novo);
            }
        }
    }

    private void levar1C(List<Estado> visitados) {
        // Verifica se é possível levar 1 canibal
        if (barco == 'e' && canibaisEsquerda >= 1) {
            Travessia novo = new Travessia(missionariosEsquerda, canibaisEsquerda - 1, missionariosDireita, canibaisDireita + 1, 'd', "Levando 1 canibal para a direita");
            if (ehValido(novo)) {
                visitados.add(novo);
            }
        } else if (barco == 'd' && canibaisDireita >= 1) {
            Travessia novo = new Travessia(missionariosEsquerda, canibaisEsquerda + 1, missionariosDireita, canibaisDireita - 1, 'e', "Levando 1 canibal para a esquerda");
            if (ehValido(novo)) {
                visitados.add(novo);
            }
        }
    }

    private void levar1M1C(List<Estado> visitados) {
        // Verifica se é possível levar 1 missionário e 1 canibal
        if (barco == 'e' && missionariosEsquerda >= 1 && canibaisEsquerda >= 1) {
            Travessia novo = new Travessia(missionariosEsquerda - 1, canibaisEsquerda - 1, missionariosDireita + 1, canibaisDireita + 1, 'd', "Levando 1 missionário e 1 canibal para a direita");
            if (ehValido(novo)) {
                visitados.add(novo);
            }
        } else if (barco == 'd' && missionariosDireita >= 1 && canibaisDireita >= 1) {
            Travessia novo = new Travessia(missionariosEsquerda + 1, canibaisEsquerda + 1, missionariosDireita - 1, canibaisDireita - 1, 'e', "Levando 1 missionário e 1 canibal para a esquerda");
            if (ehValido(novo)) {
                visitados.add(novo);
            }
        }
    }

    private void levar2M(List<Estado> visitados) {
        // Verifica se é possível levar 2 missionários
        if (barco == 'e' && missionariosEsquerda >= 2) {
            Travessia novo = new Travessia(missionariosEsquerda - 2, canibaisEsquerda, missionariosDireita + 2, canibaisDireita, 'd', "Levando 2 missionários para a direita");
            if (ehValido(novo)) {
                visitados.add(novo);
            }
        } else if (barco == 'd' && missionariosDireita >= 2) {
            Travessia novo = new Travessia(missionariosEsquerda + 2, canibaisEsquerda, missionariosDireita - 2, canibaisDireita, 'e', "Levando 2 missionários para a esquerda");
            if (ehValido(novo)) {
                visitados.add(novo);
            }
        }
    }

    private void levar2C(List<Estado> visitados) {
        // Verifica se é possível levar 2 canibais
        if (barco == 'e' && canibaisEsquerda >= 2) {
            Travessia novo = new Travessia(missionariosEsquerda, canibaisEsquerda - 2, missionariosDireita, canibaisDireita + 2, 'd', "Levando 2 canibais para a direita");
            if (ehValido(novo)) {
                visitados.add(novo);
            }
        } else if (barco == 'd' && canibaisDireita >= 2) {
            Travessia novo = new Travessia(missionariosEsquerda, canibaisEsquerda + 2, missionariosDireita, canibaisDireita - 2, 'e', "Levando 2 canibais para a esquerda");
            if (ehValido(novo)) {
                visitados.add(novo);
            }
        }
    }

     @Override
    public String toString() {
        // Retorna uma representação em string do estado, incluindo a descrição da operação realizada
        return getDescricao() + " - " + op;
    }

    public static void main(String[] args) {
        // Cria um estado inicial com 3 missionários e 3 canibais na margem esquerda
        Scanner scanner = new Scanner(System.in);

        Travessia estadoInicial = new Travessia(3, 3, 0, 0, 'e', "estado inicial");
        // exibe 2 opções para a busca
        System.out.println("Escolha o método de busca (1 para profundidade, 2 para largura):");
        String escolha = scanner.nextLine().toLowerCase();

        Nodo n = null;
        switch (escolha) {
            case "1" -> {
                System.out.println("Busca em profundidade...");
                n = new BuscaProfundidade().busca(estadoInicial);
            }
            case "2" -> {
                System.out.println("Busca em largura...");
                n = new BuscaLargura().busca(estadoInicial);
            }
            default -> System.out.println("Escolha inválida. Use 'p' para profundidade ou 'l' para largura.");
        }

        if (n == null) {
            System.out.println("Sem solução!");
        } else {
            System.out.println("Solução encontrada:");
            List<String> caminho = new ArrayList<>();
            Nodo nodoAtual = n;
            while (nodoAtual != null) {
                caminho.add(nodoAtual.getEstado().toString());
                nodoAtual = nodoAtual.getPai();
            }
            for (int i = caminho.size() - 1; i >= 0; i--) {
                System.out.println("Passo " + (caminho.size() - i) + ": " + caminho.get(i));
                System.out.println(caminho.get(i).endsWith("esquerda") ? "Esquerda" : "Direita");
                System.out.println();
            }
        }
    }
}
