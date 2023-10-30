import java.util.List;

public class Combo {
    String nome;
    double preco;
    List<Produto> produtos;

    public Combo(String nome, double preco, List<Produto> produtos) {
        this.nome = nome;
        this.preco = preco;
        this.produtos = produtos;
    }
}