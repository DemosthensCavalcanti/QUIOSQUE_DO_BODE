import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pedido {
    private static int proximoCodigo = 1;

    private int codigo;
    private String cliente;
    private List<ItemPedido> itens;
    private Cupom cupom;
    private Date horario;
    private String status;

    public Pedido(String cliente) {
        this.codigo = proximoCodigo++;
        this.cliente = cliente;
        this.itens = new ArrayList<>();
        this.horario = new Date();
        this.status = "ABERTO";
    }

    public void adicionarItem(Produto produto, int quantidade) {
        itens.add(new ItemPedido(produto, quantidade));
    }

    public void adicionarCombo(Combo combo, int quantidade) {
        for (Produto produto : combo.produtos) {
            adicionarItem(produto, quantidade);
        }
    }

    public double calcularTotal() {
        double total = 0;
        for (ItemPedido item : itens) {
            total += item.calcularSubtotal();
        }

        if (cupom != null) {
            total = cupom.aplicarDesconto(total);
        }

        return total;
    }

    public void setCupom(Cupom cupom) {
        this.cupom = cupom;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getCliente() {
        return cliente;
    }

    public Date getHorario() {
        return horario;
    }

    public String getStatus() {
        return status;
    }

    public List<ItemPedido> getItens() {
        return new ArrayList<>(itens);
    }
}