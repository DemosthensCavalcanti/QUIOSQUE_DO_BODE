import java.util.List;

public class CupomItemMaisBaratoGratis implements Cupom {

    @Override
    public double aplicarDesconto(List<ItemPedido> itens) {
        if (itens.size() >= 5) {
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
