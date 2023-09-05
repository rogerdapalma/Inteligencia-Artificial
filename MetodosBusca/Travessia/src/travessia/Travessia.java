
import busca.*;
import java.util.LinkedList;
import java.util.List;

public class Travessia implements Estado {

    final char homem, alface, carneiro, lobo;
    String op;

    public Travessia(char homem, char alface, char carneiro, char lobo, String op) {
        this.homem = homem;
        this.alface = alface;
        this.carneiro = carneiro;
        this.lobo = lobo;
        this.op = op;
    }
    
    
    @Override
    public String getDescricao() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
    
    private char margemOposta(char margem){
        if(margem == 'e'){
            return 'd';
        }
        return 'e';
    }
    
    private boolean ehValido(Travessia estado){
        return !((estado.homem != estado.lobo && estado.lobo == estado.carneiro) ||
                (estado.homem != estado.carneiro && estado.carneiro == estado.alface));       
    }
    
    public void levarNada(List<Estado> visitados){
        char novaMargem = margemOposta(homem);
        
        Travessia novo = new Travessia (novaMargem, this.alface, this.carneiro, this.lobo, "Levando nada para a"+novaMargem);
        if(ehValido(novo) && !visitados.contains(novo)){
            visitados.add(novo);
        }else{
            System.gc();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Travessia) {
            Travessia e = (Travessia)o;
            return this.homem == e.homem && 
                   this.alface == e.alface &&
                    this.carneiro == e.carneiro &&
                    this.lobo == e.lobo;
        }
        return false;
    }
    
    /** 
     * retorna o hashCode do estado
     * (usado para poda, conjunto de fechados)
     */
    @Override
    public int hashCode() { 
        return (""+this.homem + this.alface + this.carneiro+this.lobo).hashCode();
    }
    
    @Override
    public String toString() {
        return "(" + this.homem + ", " + this.alface + ", " + this.carneiro + ", "+ this.lobo+ ") - "  + op + "\n";
    }
    
    public static void main(String[] a) {
        Travessia estadoInicial = new Travessia('e','e','e','e', "estado inicial");
        
        // chama busca em largura
        System.out.println("busca em ....");
        Nodo n = new BuscaLargura(new MostraStatusConsole()).busca(estadoInicial);
        if (n == null) {
            System.out.println("sem solucao!");
        } else {
            System.out.println("solucao:\n" + n.montaCaminho() + "\n\n");
        }
    }
     private void levarLobo(List<Estado> visitados) {
        char novaMargem = margemOposta(this.homem);
        
        Travessia novo = new Travessia(novaMargem,this.alface, this.carneiro, novaMargem, "Levando lobo para "+novaMargem);
        
        // Se o estado e válido e NÃO foi visitado... então adiciona.
        if(ehValido(novo) && !visitados.contains(novo)){
            visitados.add(novo);
        }else{
            System.gc();
        }
    }
     private void levarCarneiro(List<Estado> visitados) {
        char novaMargem = margemOposta(this.homem);
        
        Travessia novo = new Travessia(novaMargem,this.alface, novaMargem, this.lobo, "Levando carneiro para "+novaMargem);
        
        // Se o estado e válido e NÃO foi visitado... então adiciona.
        if(ehValido(novo) && !visitados.contains(novo)){
            visitados.add(novo);
        }else{
            System.gc();
        }
    }
      private void levarAlface(List<Estado> visitados) {
        char novaMargem = margemOposta(this.homem);
        
        Travessia novo = new Travessia(novaMargem,novaMargem, this.carneiro, this.lobo, "Levando alface para "+novaMargem);
        
        // Se o estado e válido e NÃO foi visitado... então adiciona.
        if(ehValido(novo) && !visitados.contains(novo)){
            visitados.add(novo);
        }else{
            System.gc();
        }
    }
}
