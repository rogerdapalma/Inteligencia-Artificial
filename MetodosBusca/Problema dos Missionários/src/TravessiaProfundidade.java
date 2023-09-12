import busca.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class TravessiaProfundidade implements Estado {

    final char homem, alface, carneiro, lobo;
    String op;

    public TravessiaProfundidade(char homem, char alface, char carneiro, char lobo, String op) {
        this.homem = homem;
        this.alface = alface;
        this.carneiro = carneiro;
        this.lobo = lobo;
        this.op = op;
    }

    @Override
    public String getDescricao() {
        return "(" + this.homem + ", " + this.alface + ", " + this.carneiro + ", " + this.lobo + ")";
    }

    @Override
    public boolean ehMeta() {
        return homem == 'd' && alface == 'd' && lobo == 'd' && carneiro == 'd';
    }

    @Override
    public int custo() {
        return 1;
    }

    @Override
    public List<Estado> sucessores() {
        List<Estado> visitados = new LinkedList<>();
        levarNada(visitados);
        levarAlface(visitados);
        levarCarneiro(visitados);
        levarLobo(visitados);
        return visitados;
    }

    private char margemOposta(char margem) {
        if (margem == 'e') {
            return 'd';
        }
        return 'e';
    }

    private boolean ehValido(TravessiaProfundidade estado) {
        return !((estado.homem != estado.lobo && estado.lobo == estado.carneiro) ||
                (estado.homem != estado.carneiro && estado.carneiro == estado.alface));
    }

    public void levarNada(List<Estado> visitados) {
        char novaMargem = margemOposta(homem);

        TravessiaProfundidade novo = new TravessiaProfundidade(novaMargem, this.alface, this.carneiro, this.lobo,
                "Levando nada para a " + novaMargem);
        if (ehValido(novo) && !visitados.contains(novo)) {
            visitados.add(novo);
        } else {
            System.gc();
        }
    }

    private void levarLobo(List<Estado> visitados) {
        char novaMargem = margemOposta(this.homem);

        TravessiaProfundidade novo = new TravessiaProfundidade(novaMargem, this.alface, this.carneiro, novaMargem,
                "Levando lobo para " + novaMargem);

        if (ehValido(novo) && !visitados.contains(novo)) {
            visitados.add(novo);
        } else {
            System.gc();
        }
    }

    private void levarCarneiro(List<Estado> visitados) {
        char novaMargem = margemOposta(this.homem);

        TravessiaProfundidade novo = new TravessiaProfundidade(novaMargem, this.alface, novaMargem, this.lobo,
                "Levando carneiro para " + novaMargem);

        if (ehValido(novo) && !visitados.contains(novo)) {
            visitados.add(novo);
        } else {
            System.gc();
        }
    }

    private void levarAlface(List<Estado> visitados) {
        char novaMargem = margemOposta(this.homem);

        TravessiaProfundidade novo = new TravessiaProfundidade(novaMargem, novaMargem, this.carneiro, this.lobo,
                "Levando alface para " + novaMargem);

        if (ehValido(novo) && !visitados.contains(novo)) {
            visitados.add(novo);
        } else {
            System.gc();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof TravessiaProfundidade) {
            TravessiaProfundidade e = (TravessiaProfundidade) o;
            return this.homem == e.homem && this.alface == e.alface && this.carneiro == e.carneiro
                    && this.lobo == e.lobo;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ("" + this.homem + this.alface + this.carneiro + this.lobo).hashCode();
    }

    @Override
    public String toString() {
        return "(" + this.homem + ", " + this.alface + ", " + this.carneiro + ", " + this.lobo + ") - " + op + "\n";
    }

     public static void main(String[] a) {
        TravessiaProfundidade estadoInicial = new TravessiaProfundidade('e', 'e', 'e', 'e', "estado inicial");

        // chama busca em profundidade
        System.out.println("busca em profundidade....");
        Stack<Nodo> pilha = new Stack<>();
        pilha.push(new Nodo(estadoInicial, null)); // Correção aqui
        Nodo n = buscaProfundidade(pilha);
        if (n == null) {
            System.out.println("sem solução!");
        } else {
            System.out.println("solução:\n" + n.montaCaminho() + "\n\n");
        }
    }

    private static Nodo buscaProfundidade(Stack<Nodo> pilha) {
        while (!pilha.isEmpty()) {
            Nodo n = pilha.pop();
            Estado estado = n.getEstado();
            if (estado.ehMeta()) {
                return n;
            }
            List<Estado> sucessores = estado.sucessores();
            for (Estado s : sucessores) {
                pilha.push(new Nodo(s, n)); // Correção aqui
            }
        }
        return null;
    }

}
