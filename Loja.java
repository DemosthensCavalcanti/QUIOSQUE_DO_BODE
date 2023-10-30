import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

public class Loja {
    private static final String SENHA_FUNCIONARIO = "senha123";
    private List<Pedido> pedidos;
    private List<String> vendasDoDia;
    private List<Integer> temposDeEspera;
    private List<Produto> menu;

    public Loja() {
        this.pedidos = new ArrayList<>();
        this.vendasDoDia = new ArrayList<>();
        this.temposDeEspera = new ArrayList<>();
        this.menu = new ArrayList<>();

        menu.add(new Produto("Suco de laranja da fruta", 4.00));
        menu.add(new Produto("Salada de frutas", 8.00));
        menu.add(new Produto("Torrada de pão de forma", 3.00));
        menu.add(new Produto("Porção de queijo", 10.00));
        menu.add(new Produto("Batata Frita", 12.00));

        List<Produto> comboBatataSuco = Arrays.asList(menu.get(4), menu.get(0));
        menu.add(new Combo("Batata frita com suco", 14.00, comboBatataSuco));

        List<Produto> comboTorradaQueijoSuco = Arrays.asList(menu.get(2), menu.get(3), menu.get(0));
        menu.add(new Combo("Torrada com queijo e suco", 15.00, comboTorradaQueijoSuco));

        List<Produto> comboTorradaSuco = Arrays.asList(menu.get(2), menu.get(0));
        menu.add(new Combo("Torrada com suco", 5.00, comboTorradaSuco));

        List<Produto> comboBatataQueijoDerretido = Arrays.asList(menu.get(4), menu.get(3));
        menu.add(new Combo("Batata com queijo derretido", 20.00, comboBatataQueijoDerretido));
    }

    public void atenderCliente() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite seu nome:");
        String nomeCliente = scanner.nextLine();
        Pedido pedido = new Pedido(nomeCliente);

        System.out.println("Iniciando pedido para " + nomeCliente);

        exibirMenu();

        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Adicionar item ao pedido");
            System.out.println("2 - Remover item do pedido");
            System.out.println("3 - Cancelar pedido");
            System.out.println("4 - Fechar pedido");
            System.out.println("0 - Voltar");

            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    adicionarItemAoPedido(pedido);
                    break;
                case 2:
                    removerItemDoPedido(pedido);
                    break;
                case 3:
                    cancelarPedido(pedido);
                    return;
                case 4:
                    fecharPedido(pedido);
                    return;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public void atenderFuncionario() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite a senha do funcionário:");
        String senha = scanner.nextLine();

        if (senha.equals(SENHA_FUNCIONARIO)) {
            System.out.println("Acesso permitido como funcionário.");
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Preparar próximo pedido");
            System.out.println("2 - Finalizar e entregar pedido");
            System.out.println("3 - Encerrar vendas do dia");
            System.out.println("4 - Relatório de estatísticas de venda do dia");
            System.out.println("5 - Relatório de tempos de espera pelos pedidos");
            System.out.println("0 - Voltar");

            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    prepararProximoPedido();
                    break;
                case 2:
                    finalizarEEntregarPedido();
                    break;
                case 3:
                    encerrarVendasDoDia();
                    break;
                case 4:
                    relatorioEstatisticasVendaDoDia();
                    break;
                case 5:
                    relatorioTemposEsperaPedidos();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } else {
            System.out.println("Senha incorreta. Acesso negado.");
        }
    }

    private void exibirMenu() {
        System.out.println("Menu de Produtos:");
        for (int i = 0; i < menu.size(); i++) {
            System.out.println((i + 1) + " - " + menu.get(i).nome + " - R$ " + menu.get(i).preco);
        }
    }

    private void adicionarItemAoPedido(Pedido pedido) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Escolha um item do menu pelo número:");
        int opcao = scanner.nextInt();

        if (opcao > 0 && opcao <= menu.size()) {
            System.out.println("Quantidade:");
            int quantidade = scanner.nextInt();

            Produto produtoEscolhido = menu.get(opcao - 1);
            pedido.adicionarItem(produtoEscolhido, quantidade);
            System.out.println(quantidade + " " + produtoEscolhido.nome + "(s) adicionado(s) ao pedido.");
        } else {
            System.out.println("Opção inválida. Tente novamente.");
        }
    }

    private void removerItemDoPedido(Pedido pedido) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Escolha um item do pedido pelo número:");
        int opcao = scanner.nextInt();

        List<ItemPedido> itens = pedido.getItens();

        if (opcao > 0 && opcao <= itens.size()) {
            ItemPedido itemRemover = itens.get(opcao - 1);
            pedido.getItens().remove(itemRemover);
            System.out.println(itemRemover.quantidade + " " + itemRemover.produto.nome + "(s) removido(s) do pedido.");
        } else {
            System.out.println("Opção inválida. Tente novamente.");
        }
    }

    private void cancelarPedido(Pedido pedido) {
        if ("ABERTO".equals(pedido.getStatus())) {
            pedido.setStatus("CANCELADO PELO CLIENTE");
            System.out.println("Pedido cancelado com sucesso.");
        } else {
            System.out.println("Não é possível cancelar um pedido que não está aberto.");
        }
    }

    private void fecharPedido(Pedido pedido) {
        if ("ABERTO".equals(pedido.getStatus())) {
            exibirItensPedido(pedido);
            exibirTotalPedido(pedido);
    
            Scanner scanner = new Scanner(System.in);
            System.out.println("Deseja aplicar algum cupom de desconto? (S/N)");
            String resposta = scanner.nextLine();
    
            if ("S".equalsIgnoreCase(resposta)) {
                aplicarCupom(pedido);
            }
    
            double totalPagar = pedido.calcularTotal();
            System.out.println("Total a pagar: R$ " + totalPagar);
    
            System.out.println("Realize o pagamento no auto-atendimento.");
    
            pedido.setStatus("AGUARDANDO PREPARO");
            pedidos.add(pedido);
    
            System.out.println("Pedido fechado com sucesso.");
        } else {
            System.out.println("Não é possível fechar um pedido que não está aberto.");
        }
    }
    
    private void exibirItensPedido(Pedido pedido) {
        System.out.println("Itens do pedido:");
        List<ItemPedido> itens = pedido.getItens();
        for (int i = 0; i < itens.size(); i++) {
            ItemPedido item = itens.get(i);
            System.out.println((i + 1) + " - " + item.quantidade + " " + item.produto.nome + " - R$ " + item.calcularSubtotal());
        }
    }
    
    private void exibirTotalPedido(Pedido pedido) {
        System.out.println("Total do pedido: R$ " + pedido.calcularTotal());
    }
    
    private void aplicarCupom(Pedido pedido) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Escolha o tipo de cupom:");
        System.out.println("1 - CupomSemDesconto");
        System.out.println("2 - CupomItemMaisBaratoGratis");
        System.out.println("3 - CupomPague3ELeve4Bode");
    
        int opcao = scanner.nextInt();
    
        Cupom cupom = null;
    
        switch (opcao) {
            case 1:
                cupom = new CupomSemDesconto();
                break;
            case 2:
                cupom = new CupomItemMaisBaratoGratis();
                break;
            case 3:
                cupom = new CupomPague3ELeve4Bode();
                break;
            default:
                System.out.println("Opção inválida. CupomSemDesconto será aplicado por padrão.");
                cupom = new CupomSemDesconto();
        }
    
        pedido.setCupom(cupom);
        System.out.println("Cupom aplicado com sucesso.");
    }

    private void prepararProximoPedido() {
        if (!pedidos.isEmpty()) {
            Pedido proximoPedido = pedidos.get(0);
    
            if ("AGUARDANDO PREPARO".equals(proximoPedido.getStatus())) {
                proximoPedido.setStatus("EM PREPARO");
                System.out.println("Pedido " + proximoPedido.getCodigo() + " em preparo.");
            } else {
                System.out.println("O próximo pedido não está aguardando preparo.");
            }
        } else {
            System.out.println("Não há pedidos na fila.");
        }
    }

    private void finalizarEEntregarPedido() {
        if (!pedidos.isEmpty()) {
            Pedido proximoPedido = pedidos.get(0);
    
            if ("EM PREPARO".equals(proximoPedido.getStatus())) {
                proximoPedido.setStatus("ENTREGUE");
                proximoPedido.setHoraEntrega(LocalDateTime.now());
                calcularTempoPreparo(proximoPedido);
    
                System.out.println("Pedido " + proximoPedido.getCodigo() + " finalizado e entregue.");
            } else {
                System.out.println("O próximo pedido não está em preparo.");
            }
        } else {
            System.out.println("Não há pedidos na fila.");
        }
    }
    
    private void calcularTempoPreparo(Pedido pedido) {
        LocalDateTime horaPedido = pedido.getHoraPedido();
        LocalDateTime horaEntrega = pedido.getHoraEntrega();
    
        long minutosPreparo = ChronoUnit.MINUTES.between(horaPedido, horaEntrega);
        System.out.println("Tempo de preparo: " + minutosPreparo + " minutos.");
    }

    private void encerrarVendasDoDia() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Deseja realmente encerrar as vendas do dia? (S/N)");
        String resposta = scanner.nextLine();
    
        if ("S".equalsIgnoreCase(resposta)) {
            gerarRelatorioVendas();
            limparPedidos();
            System.out.println("Vendas do dia encerradas.");
        } else {
            System.out.println("Vendas do dia não foram encerradas.");
        }
    }
    
    private void gerarRelatorioVendas() {
        String nomeArquivo = "Relatorio_Vendas_" + LocalDate.now() + ".txt";
        StringBuilder relatorio = new StringBuilder();
    
        for (Pedido pedido : pedidos) {
            relatorio.append("Horário da venda: ").append(pedido.getHoraPedido()).append("\n");
            relatorio.append("Itens vendidos:\n");
    
            for (ItemPedido item : pedido.getItens()) {
                relatorio.append(item.quantidade).append(" ").append(item.produto.nome).append(" - R$ ").append(item.calcularSubtotal()).append("\n");
            }
    
            relatorio.append("Preço da venda: R$ ").append(pedido.calcularTotal()).append("\n\n");
        }
    
        double totalRecebido = pedidos.stream().mapToDouble(Pedido::calcularTotal).sum();
        relatorio.append("Total recebido no dia: R$ ").append(totalRecebido).append("\n");
    
        salvarRelatorio(relatorio.toString(), nomeArquivo);
    }
    
    private void limparPedidos() {
        pedidos.clear();
    }
    
    private void salvarRelatorio(String relatorio, String nomeArquivo) {
    }

    private void relatorioEstatisticasVendaDoDia() {
        String nomeArquivo = "Relatorio_Estatisticas_Venda_" + LocalDate.now() + ".txt";
        StringBuilder relatorio = new StringBuilder();
    
        List<Produto> produtosVendidos = new ArrayList<>();
    
        for (Pedido pedido : pedidos) {
            for (ItemPedido item : pedido.getItens()) {
                Produto produto = item.produto;
                produtosVendidos.add(produto);
            }
        }
    
        Map<Produto, Long> contagemProdutos = produtosVendidos.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    
        relatorio.append("Estatísticas de venda do dia:\n");
    
        contagemProdutos.entrySet().stream()
                .sorted(Map.Entry.<Produto, Long>comparingByValue().reversed())
                .forEach(entry -> {
                    relatorio.append(entry.getKey().nome)
                            .append(" - Quantidade vendida: ")
                            .append(entry.getValue())
                            .append("\n");
                });
    
        salvarRelatorio(relatorio.toString(), nomeArquivo);
    }

    private void relatorioTemposEsperaPedidos() {
        String nomeArquivo = "Relatorio_Tempos_Espera_Pedidos_" + LocalDate.now() + ".txt";
        StringBuilder relatorio = new StringBuilder();
    
        relatorio.append("Relatório de tempos de espera pelos pedidos do dia:\n");
    
        for (Pedido pedido : pedidos) {
            if ("ENTREGUE".equals(pedido.getStatus())) {
                LocalDateTime horaPedido = pedido.getHoraPedido();
                LocalDateTime horaEntrega = pedido.getHoraEntrega();
    
                long minutosEspera = ChronoUnit.MINUTES.between(horaPedido, horaEntrega);
    
                relatorio.append("Pedido ").append(pedido.getCodigo())
                        .append(" - Tempo de espera: ").append(minutosEspera).append(" minutos\n");
            }
        }
    
        double tempoMedioEspera = calcularTempoMedioEspera();
        relatorio.append("Tempo médio de espera do dia: ").append(tempoMedioEspera).append(" minutos\n");
    
        salvarRelatorio(relatorio.toString(), nomeArquivo);
    }
    
    private double calcularTempoMedioEspera() {
        long totalMinutosEspera = pedidos.stream()
                .filter(pedido -> "ENTREGUE".equals(pedido.getStatus()))
                .mapToLong(pedido -> ChronoUnit.MINUTES.between(pedido.getHoraPedido(), pedido.getHoraEntrega()))
                .sum();
    
        long totalPedidosEntregues = pedidos.stream()
                .filter(pedido -> "ENTREGUE".equals(pedido.getStatus()))
                .count();
    
        return totalPedidosEntregues > 0 ? (double) totalMinutosEspera / totalPedidosEntregues : 0;
    }

    private void salvarRelatorio(String relatorio, String nomeArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            writer.write(relatorio);
            System.out.println("Relatório salvo com sucesso em " + nomeArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao salvar o relatório: " + e.getMessage());
        }
    }
}