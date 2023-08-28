import java.util.Scanner;

public class Estrutura {
    public static void main(String[] args){
        Scanner teclado = new Scanner(System.in);
        int dimensao;

        System.out.println("Digite a Dimensão da matriz: ");
        dimensao = teclado.nextInt();
        Matriz matriz = new Matriz(dimensao);
        matriz.embaralhar();
        matriz.exibir();

        matriz.cima();
        matriz.exibir();
        matriz.baixo();
        matriz.exibir();
        matriz.direita();
        matriz.exibir();
        matriz.esquerda();
        matriz.exibir();
    }
}