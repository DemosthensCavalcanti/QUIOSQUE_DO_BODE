import java.util.Scanner;

public class Fachada {

    private Loja loja;

    public Fachada() {
        this.loja = new Loja();
    }

    public void iniciarAtendimento() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bem-vindo ao Quiosque do Bode!");
        System.out.println("Selecione o modo de uso:");
        System.out.println("1 - Cliente");
        System.out.println("2 - Funcionário");

        int modo = scanner.nextInt();

        if (modo == 1) {
            atendimentoCliente();
        } else if (modo == 2) {
            atendimentoFuncionario();
        } else {
            System.out.println("Modo inválido. Encerrando atendimento.");
        }
    }

    private void atendimentoCliente() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite seu nome:");
        String nomeCliente = scanner.nextLine();

        loja.iniciarPedido(nomeCliente);
        exibirMenu();

        int opcao;
        do {
            System.out.println("Selecione uma opção:");
            System.out.println("1 - Adicionar/remover produtos no pedido");
            System.out.println("2 - Cancelar pedido");
            System.out.println("3 - Fechar pedido");
            System.out.println("0 - Encerrar atendimento");

            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    adicionarRemoverProdutos();
                    break;
                case 2:
                    cancelarPedido();
                    break;
                case 3:
                    fecharPedido();
                    break;
            }

        } while (opcao != 0);
    }

    private void exibirMenu() {
        loja.exibirMenu();
    }

    private void adicionarRemoverProdutos() {
        loja.adicionarItemAoPedido();
        loja.removerItemDoPedido();
    }

    private void cancelarPedido() {
        loja.cancelarPedido();
    }

    private void fecharPedido() {
        loja.fecharPedido();
    }

    private void atendimentoFuncionario() {
        loja.atenderFuncionario();
    }

    public static void main(String[] args) {
        Fachada fachada = new Fachada();
        fachada.iniciarAtendimento();
    }
}