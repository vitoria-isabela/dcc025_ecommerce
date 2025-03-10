package br.ufjf.dcc.dcc025.dcc025_ecommerce.gui;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.GestorVendas;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.*;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.excecoes.CupomInvalidoException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

/**
 * JPanel for managing client sales, including product selection, coupon
 * application, and sale finalization.
 * 
 * @Author: Vitoria Isabela de Oliveira - 202065097C
 */
public class ClienteVendaPanel extends JPanel {
    private final GestorVendas gestorVendas = GestorVendas.getInstance();
    private final DefaultTableModel produtosTableModel;
    private final JTable produtosTable;
    private final DefaultListModel<Cupom> cuponsListModel = new DefaultListModel<>();
    private final DefaultTableModel itensVendaTableModel;
    private final JTable itensVendaTable;
    private final JLabel totalLabel = new JLabel("Total: R$0.00");
    private final JTextField quantidadeField = new JTextField(5);
    private final JList<Cupom> cuponsList = new JList<>(cuponsListModel);
    private final JLabel totalSemDescontoLabel = new JLabel("Total sem Desconto: R$0.00");
    private final JLabel totalComDescontoLabel = new JLabel("Total com Desconto: R$0.00");
    private JComboBox<String> paymentMethodComboBox;
    private User loggedInUser;
    private List<ItemVenda> carrinho = new ArrayList<>();

    /**
     * Constructs a ClienteVendaPanel with the logged-in user.
     * 
     * @param user the logged-in user
     */
    public ClienteVendaPanel(User user) {
        setLayout(new BorderLayout(10, 10));
        this.loggedInUser = user;
        produtosTableModel = new DefaultTableModel(new Object[] { "Nome", "Valor" }, 0);
        produtosTable = new JTable(produtosTableModel);
        itensVendaTableModel = new DefaultTableModel(
                new Object[] { "Item", "Produto", "Quantidade", "Preço Unitário", "Desconto", "Total" }, 0);
        itensVendaTable = new JTable(itensVendaTableModel);
        initComponents();
        carregarDadosIniciais();
    }

    /**
     * Initializes the components of the panel.
     */
    private void initComponents() {
        JPanel produtosPanel = criarPainelProdutos();
        JPanel cuponsPanel = criarPainelCupons();
        JPanel resumoPanel = criarPainelResumo();
        JPanel acoesPanel = criarPainelAcoes();

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(produtosPanel);
        leftPanel.add(cuponsPanel);

        add(leftPanel, BorderLayout.WEST);
        add(resumoPanel, BorderLayout.CENTER);
        add(acoesPanel, BorderLayout.SOUTH);
    }

    /**
     * Creates the products panel.
     * 
     * @return the products panel
     */
    private JPanel criarPainelProdutos() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Available Products"));
        JScrollPane scrollPane = new JScrollPane(produtosTable);
        scrollPane.setPreferredSize(new Dimension(300, 400));
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel painelQuantidade = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelQuantidade.add(new JLabel("Quantity:"));
        painelQuantidade.add(quantidadeField);

        JButton btnAdicionar = new JButton("Add to Cart");
        btnAdicionar.addActionListener(e -> adicionarProdutoAVenda());

        JPanel painelBotoes = new JPanel(new BorderLayout());
        painelBotoes.add(painelQuantidade, BorderLayout.NORTH);
        painelBotoes.add(btnAdicionar, BorderLayout.SOUTH);

        panel.add(painelBotoes, BorderLayout.SOUTH);
        return panel;
    }

    /**
     * Creates the coupons panel.
     * 
     * @return the coupons panel
     */
    private JPanel criarPainelCupons() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Available Coupons"));
        JScrollPane scrollPane = new JScrollPane(cuponsList);
        scrollPane.setPreferredSize(new Dimension(200, 150));
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton btnAplicar = new JButton("Apply Coupon");
        btnAplicar.addActionListener(e -> aplicarCupom());
        panel.add(btnAplicar, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Creates the purchase summary panel.
     * 
     * @return the purchase summary panel
     */
    private JPanel criarPainelResumo() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Purchase Summary"));

        JPanel paymentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        paymentPanel.add(new JLabel("Payment Method:"));
        paymentMethodComboBox = new JComboBox<>(new String[] { "Credit Card", "Payment Slip", "Pix" });
        paymentPanel.setPreferredSize(new Dimension(300, 50));
        paymentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        paymentPanel.setMaximumSize(new Dimension(300, 50));
        paymentMethodComboBox.setPreferredSize(new Dimension(200, 25));
        paymentPanel.add(paymentMethodComboBox);

        panel.add(paymentPanel, BorderLayout.NORTH);

        JScrollPane tableScrollPane = new JScrollPane(itensVendaTable);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        JPanel painelTotais = new JPanel(new GridLayout(3, 1));
        painelTotais.add(totalSemDescontoLabel);
        painelTotais.add(totalComDescontoLabel);
        painelTotais.add(totalLabel);

        panel.add(painelTotais, BorderLayout.SOUTH);
        return panel;
    }

    /**
     * Creates the actions panel.
     * 
     * @return the actions panel
     */
    private JPanel criarPainelAcoes() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnRemover = new JButton("Remove Product");
        btnRemover.addActionListener(e -> removerProdutoDaVenda());
        JButton btnFinalizar = new JButton("Finalize Purchase");
        btnFinalizar.addActionListener(e -> finalizarCompra());
        panel.add(btnRemover);
        panel.add(btnFinalizar);
        return panel;
    }

    /**
     * Loads initial data for products and coupons.
     */
    private void carregarDadosIniciais() {
        carregarProdutos();
        carregarCupons();
    }

    /**
     * Loads products into the products table.
     */
    private void carregarProdutos() {
        produtosTableModel.setRowCount(0);
        List<Produto> produtos = gestorVendas.listarProdutos();
        for (Produto produto : produtos) {
            produtosTableModel.addRow(new Object[] { produto.getNome(), produto.getPreco() });
        }
    }

    /**
     * Loads coupons into the coupons list.
     */
    private void carregarCupons() {
        cuponsListModel.clear();
        List<Cupom> cupons = gestorVendas.listarCupons();
        for (Cupom cupom : cupons) {
            cuponsListModel.addElement(cupom);
        }
    }

    /**
     * Adds a product to the sale.
     */
    private void adicionarProdutoAVenda() {
        int selectedRow = produtosTable.getSelectedRow();
        if (selectedRow != -1) {
            String nomeProduto = (String) produtosTableModel.getValueAt(selectedRow, 0);
            Produto produto = gestorVendas.buscarProdutoPorNome(nomeProduto);
            if (produto != null) {
                try {
                    int quantidade = Integer.parseInt(quantidadeField.getText());
                    if (quantidade > 0) {
                        ItemVenda newItem = new ItemVenda(produto, quantidade);

                        boolean found = false;
                        for (int i = 0; i < carrinho.size(); i++) {
                            ItemVenda existingItem = carrinho.get(i);
                            if (existingItem.getProduto().getCodigo().equals(newItem.getProduto().getCodigo())) {
                                carrinho.set(i, new ItemVenda(existingItem.getProduto(),
                                        existingItem.getQuantidade() + quantidade));
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            carrinho.add(newItem);
                        }

                        atualizarTabelaCarrinho();
                        atualizarTotais();
                    } else {
                        JOptionPane.showMessageDialog(this, "Quantity must be greater than zero.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid quantity.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a product to add to the sale.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Removes a product from the sale.
     */
    private void removerProdutoDaVenda() {
        int selectedRow = itensVendaTable.getSelectedRow();
        if (selectedRow != -1) {
            carrinho.remove(selectedRow);
            atualizarTabelaCarrinho();
            atualizarTotais();
        } else {
            JOptionPane.showMessageDialog(this, "Select a product to remove.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Applies a selected coupon to the sale.
     */
    private void aplicarCupom() {
        Cupom cupomSelecionado = cuponsList.getSelectedValue();
        if (cupomSelecionado != null) {
            double totalSemDesconto = calcularTotalSemDesconto();
            if (cupomSelecionado.podeSerAplicado(totalSemDesconto)) {
                atualizarTabelaCarrinhoComCupom(cupomSelecionado);
            } else {
                JOptionPane.showMessageDialog(this, "The coupon cannot be applied.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a coupon to apply.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Finalizes the purchase, creating a sale transaction.
     */
    private void finalizarCompra() {
        if (itensVendaTableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Add products to the sale before finalizing.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        ClientUser loggedClientUser = (ClientUser) loggedInUser;
        Cliente clienteSelecionado = loggedClientUser.getCliente();

        if (clienteSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Error getting client data. Try logging in again.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Venda venda = new Venda();
        venda.setData(LocalDate.now());
        venda.setFormaPagamento((String) paymentMethodComboBox.getSelectedItem());
        venda.setCliente(clienteSelecionado);

        for (int i = 0; i < itensVendaTableModel.getRowCount(); i++) {
            String produtoNome = (String) itensVendaTableModel.getValueAt(i, 1);
            Produto produto = gestorVendas.buscarProdutoPorNome(produtoNome);

            if (produto != null) {
                ItemVenda itemVenda = new ItemVenda(produto, (int) itensVendaTableModel.getValueAt(i, 2));
                venda.adicionarItem(itemVenda.getProduto(), itemVenda.getQuantidade());
            }

        }

        double totalSemDesconto = venda.calcularTotalSemDesconto();
        Cupom cupomSelecionado = cuponsList.getSelectedValue();
        if (cupomSelecionado != null && cupomSelecionado.podeSerAplicado(totalSemDesconto)) {
            venda.setCupom(cupomSelecionado);
        }

        try {
            gestorVendas.realizarVenda(venda);
            JOptionPane.showMessageDialog(this, "Sale finalized successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            carrinho.clear();
            atualizarTabelaCarrinho();
            atualizarTotais();

        } catch (CupomInvalidoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Updates the cart table with the current items.
     */
    private void atualizarTabelaCarrinho() {
        itensVendaTableModel.setRowCount(0);
        int itemCount = 1;
        for (ItemVenda item : carrinho) {
            itensVendaTableModel.addRow(new Object[] {
                    itemCount++,
                    item.getProduto().getNome(),
                    item.getQuantidade(),
                    item.getProduto().getPreco(),
                    "0.00",
                    item.getProduto().getPreco() * item.getQuantidade()
            });
        }
    }

    /**
     * Updates the cart table, applying the discounts of the selected coupon.
     * 
     * @param cupom the selected coupon
     */
    private void atualizarTabelaCarrinhoComCupom(Cupom cupom) {
        itensVendaTableModel.setRowCount(0);
        int itemCount = 1;
        for (ItemVenda item : carrinho) {
            double precoUnitario = item.getProduto().getPreco();
            int quantidade = item.getQuantidade();
            double totalItemSemDesconto = precoUnitario * quantidade;
            double descontoUnitario = 0.0;

            if (cupom != null) {
                if (cupom.podeSerAplicado(totalItemSemDesconto)) {
                    descontoUnitario = truncate((cupom.getPercentualDesconto() / 100) * precoUnitario);
                }
            }

            double totalItem = (precoUnitario * quantidade) - (descontoUnitario * quantidade);
            totalItem = truncate(totalItem);

            itensVendaTableModel.addRow(new Object[] {
                    itemCount++,
                    item.getProduto().getNome(),
                    quantidade,
                    precoUnitario,
                    String.format("%.2f", descontoUnitario),
                    String.format("%.2f", totalItem)
            });

        }
        atualizarTotais();
    }

    /**
     * Truncates a double value to two decimal places.
     * 
     * @param value the value to truncate
     * @return the truncated value
     */
    private double truncate(double value) {
        return (double) ((int) (value * 100)) / 100;
    }

    /**
     * Calculates the total without discounts.
     * 
     * @return the total without discounts
     */
    private double calcularTotalSemDesconto() {
        double total = 0;
        for (ItemVenda item : carrinho) {
            total += item.getProduto().getPreco() * item.getQuantidade();
        }
        return total;
    }

    /**
     * Updates the totals labels to reflect the current state of the cart.
     */
    private void atualizarTotais() {
        double totalSemDesconto = 0.0;
        double totalComDesconto = 0.0;
        double desconto = 0.0;
        for (int i = 0; i < itensVendaTableModel.getRowCount(); i++) {
            double precoUnitario = 0.0;
            int quantidade = 0;

            precoUnitario = (double) itensVendaTableModel.getValueAt(i, 3);
            quantidade = (int) itensVendaTableModel.getValueAt(i, 2);

            totalSemDesconto += precoUnitario * quantidade;

        }
        Cupom cupomSelecionado = cuponsList.getSelectedValue();

        if (cupomSelecionado != null) {
            if (cupomSelecionado.podeSerAplicado(totalSemDesconto)) {
                double descontoTexto = truncate((cupomSelecionado.getPercentualDesconto() / 100) * totalSemDesconto);

                totalComDesconto = totalSemDesconto - descontoTexto;
                desconto = descontoTexto;
            }
        } else {
            totalComDesconto = totalSemDesconto;
            desconto = 0.0;
        }

        totalSemDescontoLabel.setText(String.format("Total without Discount: R$%.2f", totalSemDesconto));
        totalComDescontoLabel.setText(String.format("Total with Discount: R$%.2f", totalComDesconto));
        totalLabel.setText(String.format("Discount: R$%.2f", desconto));
    }

    /**
     * Returns the logged-in user.
     * 
     * @return the logged-in user
     */
    public User getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * Sets the logged-in user.
     * 
     * @param loggedInUser the user to set as logged in
     */
    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}