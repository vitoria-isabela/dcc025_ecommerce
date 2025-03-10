package br.ufjf.dcc.dcc025.dcc025_ecommerce;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.*;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.excecoes.CupomInvalidoException;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.repository.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages sales operations within an e-commerce system, including adding and
 * retrieving clients and coupons, processing sales transactions, and generating
 * sales reports.
 * Implements the Singleton pattern for managing a single instance.
 * Implements the Relatorio interface for reporting capabilities.
 * 
 * @Author: Vitória Isabela de Oliveira - 202065097C
 */
public class GestorVendas implements Relatorio {
    private static GestorVendas instance;
    private final ProdutoRepositoryInterface produtoRepo;
    private final CupomRepositoryInterface cupomRepo;
    private final VendaRepositoryInterface vendaRepo;
    private final ClienteRepositoryInterface clienteRepo;
    private User usuarioLogado;

    private GestorVendas(ProdutoRepositoryInterface produtoRepo, CupomRepositoryInterface cupomRepo,
            VendaRepositoryInterface vendaRepo, ClienteRepositoryInterface clienteRepo) {
        this.produtoRepo = produtoRepo;
        this.cupomRepo = cupomRepo;
        this.vendaRepo = vendaRepo;
        this.clienteRepo = clienteRepo;
    }

    /**
     * Retrieves the single instance of GestorVendas, creating it if necessary.
     *
     * @return The singleton instance of GestorVendas.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public static GestorVendas getInstance() {
        if (instance == null) {
            instance = new GestorVendas(ProdutoRepository.getInstance(),
                    CupomRepository.getInstance(),
                    VendaRepository.getInstance(),
                    ClienteRepository.getInstance());
        }
        return instance;
    }

    /**
     * Gets the logged-in user.
     *
     * @return The logged-in user.
     */
    public User getUsuarioLogado() {
        return usuarioLogado;
    }

    /**
     * Sets the logged-in user.
     *
     * @param usuarioLogado The user to set as logged-in.
     */
    public void setUsuarioLogado(User usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    /**
     * Factory method to create a GestorVendas instance for testing purposes.
     *
     * @param produtoRepo The product repository.
     * @param cupomRepo   The coupon repository.
     * @param vendaRepo   The sale repository.
     * @param clienteRepo The client repository.
     * @return A new GestorVendas instance configured for testing.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public static GestorVendas createForTest(ProdutoRepository produtoRepo, CupomRepository cupomRepo,
            VendaRepository vendaRepo, ClienteRepository clienteRepo) {
        return new GestorVendas(produtoRepo, cupomRepo, vendaRepo, clienteRepo);
    }

    /**
     * Adds a client to the system.
     *
     * @param cliente The client to add.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public void adicionarCliente(Cliente cliente) {
        clienteRepo.salvar(cliente);
        clienteRepo.salvarEmArquivo("clientes.json");
    }

    /**
     * Searches for a client by CPF.
     *
     * @param cpf The CPF to search for.
     * @return The client with the given CPF, or null if not found.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public Cliente buscarCliente(String cpf) {
        return clienteRepo.buscarPorCpf(cpf);
    }

    /**
     * Edits a client in the system.
     *
     * @param clienteAtualizado The client with updated information.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public void editarCliente(Cliente clienteAtualizado) {
        Cliente clienteExistente = clienteRepo.buscarPorCpf(clienteAtualizado.getCpf());
        if (clienteExistente != null) {
            clienteRepo.listarTodos().remove(clienteExistente);
            clienteRepo.editar(clienteExistente);
        }
        clienteRepo.salvarEmArquivo("clientes.json");
    }

    /**
     * Deletes a client from the system.
     *
     * @param cpf The CPF of the client to delete.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public void excluirCliente(String cpf) {
        clienteRepo.excluirByCpf(cpf);
        clienteRepo.salvarEmArquivo("clientes.json");
    }

    /**
     * Adds a coupon to the system.
     *
     * @param cupom The coupon to add.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public void adicionarCupom(Cupom cupom) {
        cupomRepo.salvar(cupom);
        cupomRepo.salvarEmArquivo("cupons.json");
    }

    /**
     * Searches for a coupon by code.
     *
     * @param codigo The code to search for.
     * @return The coupon with the given code, or null if not found.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public Cupom buscarCupom(String codigo) {
        return cupomRepo.buscarPorCodigo(codigo);
    }

    /**
     * Edits a coupon in the system.
     *
     * @param cupomAtualizado The coupon with updated information.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public void editarCupom(Cupom cupomAtualizado) {
        Cupom cupomExistente = cupomRepo.buscarPorCodigo(cupomAtualizado.getCodigo());
        if (cupomExistente != null) {
            cupomRepo.editar(cupomAtualizado);
        }
        cupomRepo.salvarEmArquivo("cupons.json");
    }

    /**
     * Deletes a coupon from the system.
     *
     * @param codigo The code of the coupon to delete.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public void excluirCupom(String codigo) {
        cupomRepo.excluir(codigo);
        cupomRepo.salvarEmArquivo("cupons.json");
    }

    /**
     * Adds a product to the system.
     *
     * @param produto The product to add.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public void adicionarProduto(Produto produto) {
        produtoRepo.salvar(produto);
        produtoRepo.salvarEmArquivo("produtos.json");
    }

    /**
     * Searches for a product by code.
     *
     * @param codigo The code to search for.
     * @return The product with the given code, or null if not found.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public Produto buscarProduto(String codigo) {
        return produtoRepo.buscarPorCodigo(codigo);
    }

    /**
     * Edits a product in the system.
     *
     * @param produtoAtualizado The product with updated information.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public void editarProduto(Produto produtoAtualizado) {
        Produto produtoExistente = produtoRepo.buscarPorCodigo(produtoAtualizado.getCodigo());
        if (produtoExistente != null) {
            produtoRepo.listarTodos().remove(produtoExistente);
            produtoRepo.salvar(produtoAtualizado);
        }
        produtoRepo.salvarEmArquivo("produtos.json");
    }

    /**
     * Deletes a product from the system.
     *
     * @param codigo The code of the product to delete.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public void excluirProduto(String codigo) {
        Produto produto = produtoRepo.buscarPorCodigo(codigo);
        if (produto != null) {
            produtoRepo.listarTodos().remove(produto);
        }
        produtoRepo.salvarEmArquivo("produtos.json");
    }

    /**
     * Processes a sale transaction and saves it to the repository.
     *
     * @param venda The sale transaction to process.
     * @throws CupomInvalidoException If the applied coupon is invalid.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public void realizarVenda(Venda venda) throws CupomInvalidoException {
        venda.calcularTotal();
        vendaRepo.salvar(venda);
        vendaRepo.salvarEmArquivo("vendas.json");
    }

    /**
     * Searches for a product by name.
     *
     * @param nome The name to search for.
     * @return The product with the given name, or null if not found.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public Produto buscarProdutoPorNome(String nome) {
        List<Produto> produtos = produtoRepo.listarTodos();
        for (Produto produto : produtos) {
            if (produto.getNome().equalsIgnoreCase(nome)) {
                return produto;
            }
        }
        return null;
    }

    /**
     * Generates a sales report.
     * 
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    @Override
    public void gerarRelatorioVendas() {
        List<Venda> vendas = vendaRepo.listarTodos();
        double totalGeral = 0.0;

        System.out.println("=== Relatório de Vendas ===");
        for (Venda venda : vendas) {
            System.out.println("\nVenda ID: " + venda.getId());
            System.out.println("Data: " + venda.getData());
            System.out.println(
                    "Cliente: " + venda.getCliente().getNome() + " (CPF: " + venda.getCliente().getCpf() + ")");

            System.out.println("Produtos Vendidos:");
            for (ItemVenda item : venda.getItens()) {
                Produto produto = item.getProduto();
                System.out.printf(
                        "- %s (Quantidade: %d, Preço Unitário: R$%.2f, Total: R$%.2f)%n",
                        produto.getNome(), item.getQuantidade(), produto.getPreco(),
                        item.getQuantidade() * produto.getPreco());
            }

            if (venda.getCupom() != null) {
                System.out.println("Cupom Utilizado: " + venda.getCupom().getCodigo() + " (Desconto: "
                        + venda.getCupom().getPercentualDesconto() + "%)");
            }

            double totalVenda = venda.getTotal();
            totalGeral += totalVenda;
            System.out.printf("Total da Venda: R$%.2f%n", totalVenda);
        }

        System.out.printf("\nVendas Totais: R$%.2f%n", totalGeral);
    }

    /**
     * Lists sales filtered by a specific date.
     *
     * @param data The date to filter sales by.
     * @return A list of sales occurring on the specified date.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public List<Venda> listarVendasPorData(String data) {
        List<Venda> vendasFiltradas = new ArrayList<>();
        List<Venda> todasVendas = vendaRepo.listarTodos();

        for (Venda venda : todasVendas) {
            if (venda.getData().toString().equals(data)) {
                vendasFiltradas.add(venda);
            }
        }

        return vendasFiltradas;
    }

    /**
     * Calculates the total sales value for a specific date.
     *
     * @param data The date to calculate the total sales for.
     * @return The total sales value for the specified date.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public double calcularTotalVendas(String data) {
        List<Venda> vendasFiltradas = listarVendasPorData(data);
        double totalGeral = 0.0;

        for (Venda venda : vendasFiltradas) {
            totalGeral += venda.getTotal();
        }

        return totalGeral;
    }

    /**
     * Lists all active coupons.
     * 
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    @Override
    public void listarCuponsAtivos() {
        cupomRepo.listarAtivos()
                .forEach(c -> System.out.println(c.getCodigo() + " - " + c.getPercentualDesconto() + "%"));
    }

    /**
     * Lists all coupons.
     *
     * @return A list of all coupons.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public List<Cupom> listarCupons() {
        return cupomRepo.listarTodos();
    }

    /**
     * Lists all products.
     *
     * @return A list of all products.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public List<Produto> listarProdutos() {
        return produtoRepo.listarTodos();
    }

    /**
     * Lists all clients.
     *
     * @return A list of all clients.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public List<Cliente> listarClientes() {
        return clienteRepo.listarTodos();
    }

    /**
     * Generates a sales report for a specific date.
     *
     * @param data The date to generate the sales report for.
     * @return A string representation of the sales report for the specified date.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public String gerarRelatorioVendasPorData(LocalDate data) {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("Nome: Empresa Vitória Isabela de Oliveira\n");
        relatorio.append("CNPJ: 00.000.000/0000-00\n");
        relatorio.append("Relatório de Vendas do Dia: ").append(data).append("\n\n");

        List<Venda> vendas = vendaRepo.listarTodos();
        double totalGeral = 0.0;

        for (Venda venda : vendas) {
            if (venda.getData().equals(data)) {
                if (venda.getCliente() != null) {
                    relatorio.append("Cliente: ").append(venda.getCliente().getNome())
                            .append(" (CPF: ").append(venda.getCliente().getCpf()).append(")\n");
                }

                relatorio.append("Itens Comprados:\n");
                for (ItemVenda item : venda.getItens()) {
                    Produto produto = item.getProduto();
                    relatorio.append("- ").append(produto.getNome())
                            .append(" (Quantidade: ").append(item.getQuantidade())
                            .append(", Preço Unitário: R$").append(produto.getPreco())
                            .append(", Total: R$").append(item.getTotal()).append("\n");
                }

                double totalSemDesconto = venda.getTotalSemDesconto();
                double totalComDesconto = venda.getTotal();
                double desconto = totalSemDesconto - totalComDesconto;

                relatorio.append("Total sem Desconto: R$").append(totalSemDesconto).append("\n");
                relatorio.append("Total com Desconto: R$").append(totalComDesconto).append("\n");
                relatorio.append("Desconto Aplicado: R$").append(desconto).append("\n");

                if (venda.getCupom() != null) {
                    relatorio.append("Cupom Utilizado: ").append(venda.getCupom().getCodigo())
                            .append(" (").append(venda.getCupom().getPercentualDesconto()).append("%)\n");
                }

                relatorio.append("---\n");
                totalGeral += totalComDesconto;
            }
        }

        relatorio.append("\nVendas Totais: R$").append(totalGeral).append("\n");
        return relatorio.toString();
    }
}