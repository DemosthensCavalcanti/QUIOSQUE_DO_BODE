import java.util.List;

public class CupomPague3ELeve4Bode implements Cupom {

    @Override
    public double aplicarDesconto(List<ItemPedido> itens) {
        long totalItens = itens.stream().mapToLong(ItemPedido::getQuantidade).sum();

        if (totalItens == 4) {
            ItemPedido itemMaisBarato = itens.stream().min((i1, i2) -> Double.compare(i1.getProduto().getPreco(), i2.getProduto().getPreco())).orElse(null);

            if (itemMaisBarato != null) {
                System.out.println("Cupom aplicado: " + this.getClass().getSimpleName());
                System.out.println("Item mais barato grátis: " + itemMaisBarato.getProduto().getNome());

                double desconto = itemMaisBarato.getProduto().getPreco();
                return desconto;
            }
        }

        return 0;
    }
}
