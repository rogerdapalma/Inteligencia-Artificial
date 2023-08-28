import busca.*;

import java.util.*;

public class Puzzle implements Estado{
    public int linha;
    public int coluna;
    public int dimensao;
    public String op;
    public int matriz[][];

    public Puzzle(int dimensao, String op){
        this.dimensao = dimensao;
        this.matriz = new int[dimensao][dimensao];
        this.op = op;

        ArrayList<Integer> lista = new ArrayList<Integer>();
        for(int i = 0; i < this.dimensao*this.dimensao; i++){
            lista.add(i);
        }
        Collections.shuffle(lista);
        int posicao = 0;
        for (int i = 0; i < this.dimensao; i++){
            for(int j = 0; j < this.dimensao; j++){
                this.matriz[i][j] = lista.get(posicao);
                if(this.matriz[i][j] == 0){
                    this.linha = i;
                    this.coluna = j;
                }
                posicao++;
            }
        }
        String teste = toString();
        System.out.println(teste);
    }
    public Puzzle(int m[][], int linha, int coluna, String op){
        this.matriz = m;
        this.linha = linha;
        this.coluna = coluna;
        this.op = op;
        this.dimensao = m.length;
    }

    @Override
    public String getDescricao(){
        return "Problema do Puzzle NxN";
    }

    @Override
    public boolean ehMeta() {
        int posicao = 0;
        for (int i = 0; i < this.dimensao; i++){
            for(int j = 0; j < this.dimensao; j++){
                if(this.matriz[i][j] != posicao){
                    return false;
                }
                posicao++;
            }
        }
        return true;
    }

    @Override
    public int custo() {
        return 1;
    }

    @Override
    public List<Estado> sucessores() {
        List<Estado> visitados = new LinkedList<Estado>();
        cima(visitados);
        baixo(visitados);
        esquerda(visitados);
        direita(visitados);

        return visitados;
    }

    public int [][]clonar(int origem[][]) {
        int destino[][] = new int[origem.length][origem.length];
        for (int i = 0; i < origem.length; i++) {
            for (int j = 0; j < origem.length; j++) {
                destino[i][j] = origem[i][j];
            }
        }
        return destino;
    }
    public void cima(List<Estado> visitados ){
        if(this.linha == 0) return;

        int matrizTemporaria[][];
        matrizTemporaria = clonar(this.matriz);
        int linhaTemporaria = this.linha-1;
        int colunaTemporaria = this.coluna;

        matrizTemporaria[this.linha][this.coluna] = matrizTemporaria[linhaTemporaria][colunaTemporaria];
        matrizTemporaria[linhaTemporaria][colunaTemporaria] = 0;

        Puzzle novo = new Puzzle(matrizTemporaria,linhaTemporaria,colunaTemporaria,"Indo para cima");
        if(!visitados.contains(novo)){
            visitados.add(novo);
        }else{
            System.gc();
        }
    }

    public void baixo(List<Estado> visitados ){
        if(this.linha == this.dimensao-1) return;

        int matrizTemporaria[][];
        matrizTemporaria = clonar(this.matriz);
        int linhaTemporaria = this.linha+1;
        int colunaTemporaria = this.coluna;

        matrizTemporaria[this.linha][this.coluna] = matrizTemporaria[linhaTemporaria][colunaTemporaria];
        matrizTemporaria[linhaTemporaria][colunaTemporaria] = 0;

        Puzzle novo = new Puzzle(matrizTemporaria,linhaTemporaria,colunaTemporaria,"Indo para baixo");
        if(!visitados.contains(novo)){
            visitados.add(novo);
        }else{
            System.gc();
        }
    }

    public void esquerda(List<Estado> visitados ){
        if(this.coluna == 0) return;

        int matrizTemporaria[][];
        matrizTemporaria = clonar(this.matriz);
        int linhaTemporaria = this.linha;
        int colunaTemporaria = this.coluna-1;

        matrizTemporaria[this.linha][this.coluna] = matrizTemporaria[linhaTemporaria][colunaTemporaria];
        matrizTemporaria[linhaTemporaria][colunaTemporaria] = 0;

        Puzzle novo = new Puzzle(matrizTemporaria,linhaTemporaria,colunaTemporaria,"Indo para esquerda");
        if(!visitados.contains(novo)){
            visitados.add(novo);
        }else{
            System.gc();
        }
    }

    public void direita(List<Estado> visitados ){
        if(this.coluna == this.dimensao-1) return;

        int matrizTemporaria[][];
        matrizTemporaria = clonar(this.matriz);
        int linhaTemporaria = this.linha;
        int colunaTemporaria = this.coluna+1;

        matrizTemporaria[this.linha][this.coluna] = matrizTemporaria[linhaTemporaria][colunaTemporaria];
        matrizTemporaria[linhaTemporaria][colunaTemporaria] = 0;

        Puzzle novo = new Puzzle(matrizTemporaria,linhaTemporaria,colunaTemporaria,"Indo para direita");
        if(!visitados.contains(novo)){
            visitados.add(novo);
        }else{
            System.gc();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Puzzle) {
            Puzzle e = (Puzzle)o;
            for (int i = 0; i < this.dimensao; i++){
                for(int j =0; j< this.dimensao; j++){
                    if(e.matriz[i][j] != this.matriz[i][j]){
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        StringBuffer resposta = new StringBuffer();
        resposta.append(op+"\n");
        for (int i = 0; i < this.dimensao; i++){
            for(int j = 0; j < this.dimensao; j++){
                resposta.append(this.matriz[i][j]+" ");
            }
            resposta.append("\n");
        }
        resposta.append("POSICAO 0 = "+linha+coluna+"\n\n");
        return resposta.toString();
    }

    @Override
    public int hashCode() {
        String estado = "";

        for (int i = 0; i < this.matriz.length; i++) {
            for (int j = 0; j < this.matriz.length; j++) {
                estado = estado + this.matriz[i][j];
            }
        }

        return estado.hashCode();
    }

    public static void main(String[]args){
        try {
            Scanner teclado = new Scanner(System.in);
            int dimensao;
            System.out.println("Digite a Dimensão da matriz: ");
            dimensao = teclado.nextInt();
            Puzzle estadoInicial = new Puzzle(dimensao,"Estado Inicial");
            Nodo n = new BuscaLargura(new MostraStatusConsole()).busca(estadoInicial);
            if (n == null) {
                System.out.println("sem solucao!");
            } else {
                System.out.println("solucao:\n" + n.montaCaminho() + "\n\n");
            }
        } catch (Exception e) {
            System.out.println("erro nas dimensões");
            System.exit(403);
        }
    }
}
