import java.util.ArrayList;
import java.util.Collections;

public class Matriz {
    final int matriz[][];
    final int dimensao;
    public int linha;
    public int coluna;

    public Matriz(int dimensao){
        this.matriz = new int[dimensao][dimensao];
        this.dimensao = dimensao;
    }
    public void embaralhar(){
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
    }

    public void exibir(){
        for (int i = 0; i < this.dimensao; i++){
            for(int j = 0; j < this.dimensao; j++){
                System.out.print(this.matriz[i][j]+" ");
            }
            System.out.println("");
        }
        System.out.println("POSICAO 0 = "+linha+coluna);
    }

    public int[][] clonar(){
        int matrizTemporaria[][] = new int[this.dimensao][this.dimensao];
        for (int i = 0; i < this.dimensao; i++){
            for(int j = 0; j < this.dimensao; j++){
                matrizTemporaria[i][j] = this.matriz[i][j];
            }
        }
        return matrizTemporaria;
    }
    public void cima(){
        if(this.linha == 0) return;
        this.matriz[linha][coluna] = this.matriz[linha-1][coluna];
        this.matriz[linha-1][coluna] = 0;
        this.linha--;
    }
    public void baixo(){
        if(this.linha == this.dimensao-1) return;
        this.matriz[linha][coluna] = this.matriz[linha+1][coluna];
        this.matriz[linha+1][coluna] = 0;
        this.linha++;
    }
    public void esquerda(){
        if(this.coluna == 0) return;
        this.matriz[linha][coluna] = this.matriz[linha][coluna-1];
        this.matriz[linha][coluna-1] = 0;
        this.coluna--;
    }
    public void direita(){
        if(this.coluna == this.dimensao-1) return;
        this.matriz[linha][coluna] = this.matriz[linha][coluna+1];
        this.matriz[linha][coluna+1] = 0;
        this.coluna++;
    }
}
