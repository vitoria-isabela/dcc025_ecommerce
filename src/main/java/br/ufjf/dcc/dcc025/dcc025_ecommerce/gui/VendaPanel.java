package br.ufjf.dcc.dcc025.dcc025_ecommerce.gui;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.GestorVendas;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.*;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.excecoes.CupomInvalidoException;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

/**
 * JPanel representing the sales panel of the e-commerce system GUI.
 * Allows creating new sales, adding products, and applying coupons.
 * 
 * @Author: Vitória Isabela de Oliveira - 202065097C
 */
public class VendaPanel extends JPanel {
    private final GestorVendas gestorVendas = GestorVendas.getInstance();
    private final DefaultTableModel produtosTableModel;
    private final JTable produtosTable;
    private final DefaultListModel<Cupom> cuponsListModel = new DefaultListModel<>();
    private final DefaultTableModel itensVendaTableModel;
    private final JTable itensVendaTable;
    private final JLabel totalLabel = new JLabel("Total: R$0.00");
    private final JComboBox<Cliente> clienteComboBox = new JComboBox<>();
    private final JTextField quantidadeField = new JTextField(5);
    private final JDatePickerImpl dataPicker;
    private final JList<Cupom> cuponsList = new JList<>(cuponsListModel);
    private final JLabel totalSemDescontoLabel = new JLabel("Total sem Desconto: R$0.00");
    private final JLabel totalComDescontoLabel = new JLabel("Total com Desconto: R$0.00");
    private JComboBox<String> paymentMethodComboBox;

    /**
     * Constructs a VendaPanel.
     * 
     * @Author: Vitória Isabela de Oliveira
     */
    public VendaPanel() {
        setLayout(new BorderLayout(10, 10));
        dataPicker = createDatePicker();
        produtosTableModel = new DefaultTableModel(new Object[] { "Nome", "Valor" }, 0);
        produtosTable = new JTable(produtosTableModel);

        produtosTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        produtosTable.getColumnModel().getColumn(1).setPreferredWidth(50);

        produtosTable.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (value != null && (value.toString().equals("ALIMENTO") || value.toString().equals("ELETRÔNICO")
                        || value.toString().equals("ROUPA"))) {
                    setFont(getFont().deriveFont(Font.BOLD));
                } else {
                    setFont(getFont().deriveFont(Font.PLAIN));
                }
                return this;
            }
        });

        itensVendaTableModel = new DefaultTableModel(
                new Object[] { "Item", "Produto", "Quantidade", "Preço Unitário", "Desconto", "Total" }, 0);
        itensVendaTable = new JTable(itensVendaTableModel);
        itensVendaTable.setFillsViewportHeight(true);
        inicializarComponentes();
        carregarDadosIniciais();
    }

    /**
     * Sets the visibility of the panel.
     * 
     * @param visible true to make the panel visible, false to hide it
     * @Author: Vitória Isabela de Oliveira
     */
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            carregarDadosIniciais();
        }
    }

    /**
     * Creates and configures a JDatePickerImpl for date selection.
     * 
     * @return the configured JDatePickerImpl
     * @Author: Vitória Isabela de Oliveira
     */
    private JDatePickerImpl createDatePicker() {
        UtilDateModel model = new UtilDateModel();
        model.setSelected(true);

        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
        return new JDatePickerImpl(datePanel, new DateLabelFormatter());
    }

    /**
     * Custom formatter to handle the display and parsing of dates in the
     * JDatePicker.
     * 
     * @Author: Vitória Isabela de Oliveira
     */
    public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private final String datePattern = "dd-MM-yyyy";
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        /**
         * Parses the input text into an object representing the date.
         * 
         * @param text a string to parse
         * @return the parsed date object
         * @throws ParseException if parsing fails
         * @Author: Vitória Isabela de Oliveira
         */
        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        /**
         * Formats the provided value (a date) into a string representation.
         * 
         * @param value the date object to format
         * @return the formatted date string
         * @throws ParseException if formatting fails
         * @Author: Vitória Isabela de Oliveira
         */
        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }
    }

    /**
     * Initializes the components of the panel.
     * 
     * @Author: Vitória Isabela de Oliveira
     */
    private void inicializarComponentes() {
        JPanel painelCliente = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelCliente.add(new JLabel("Cliente:"));

        clienteComboBox.setPreferredSize(new Dimension(300, 25));

        clienteComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                if (value instanceof Cliente) {
                    Cliente cliente = (Cliente) value;
                    setText(cliente.getNome());
                } else {
                    setText("Selecione o Cliente");
                }
                return this;
            }
        });

        painelCliente.add(clienteComboBox);
        JPanel painelData = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelData.add(new JLabel("Data:"));
        painelData.add(dataPicker);

        JPanel produtosPanel = criarPainelProdutos();
        JPanel cuponsPanel = criarPainelCupons();

        cuponsList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof CupomHeader) {
                    setFont(getFont().deriveFont(Font.BOLD));
                } else {
                    setFont(getFont().deriveFont(Font.PLAIN));
                }
                return this;
            }
        });

        JPanel resumoPanel = criarPainelResumo(painelCliente);
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
     * @Author: Vitória Isabela de Oliveira
     */
    private JPanel criarPainelProdutos() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Produtos Disponíveis"));
        JScrollPane scrollPane = new JScrollPane(produtosTable);
        scrollPane.setPreferredSize(new Dimension(300, 400));
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel painelQuantidade = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelQuantidade.add(new JLabel("Quantidade:"));
        painelQuantidade.add(quantidadeField);

        JButton btnAdicionar = new JButton("Adicionar à Venda");
        btnAdicionar.addActionListener(e -> adicionarProdutoAVenda());

        JPanel painelBotoes = new JPanel(new BorderLayout());
        painelBotoes.add(painelQuantidade, BorderLayout.NORTH);
        painelBotoes.add(btnAdicionar, BorderLayout.SOUTH);

        panel.add(painelBotoes, BorderLayout.SOUTH);
        return panel;
    }

    /**
     * Loads the products on the table.
     * 
     * @Author: Vitória Isabela de Oliveira
     */
    private void carregarProdutos() {
        produtosTableModel.setRowCount(0);
        List<Produto> produtos = gestorVendas.listarProdutos();

        Map<String, List<Produto>> produtosPorTipo = produtos.stream()
                .collect(Collectors.groupingBy(Produto::getTipo));

        List<String> tiposOrdenados = new ArrayList<>(produtosPorTipo.keySet());
        Collections.sort(tiposOrdenados);

        for (String tipo : tiposOrdenados) {
            produtosTableModel.addRow(new Object[] { tipo.toUpperCase(), "" });

            List<Produto> produtosDoTipo = produtosPorTipo.get(tipo);
            produtosDoTipo.sort(Comparator.comparing(Produto::getNome));

            for (Produto produto : produtosDoTipo) {
                produtosTableModel.addRow(new Object[] { produto.getNome(), produto.getPreco() });
            }
        }
    }

    /**
     * Creates the coupons panel.
     * 
     * @return the coupons panel
     * @Author: Vitória Isabela de Oliveira
     */
    private JPanel criarPainelCupons() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Cupons Disponíveis"));
        JScrollPane scrollPane = new JScrollPane(cuponsList);
        scrollPane.setPreferredSize(new Dimension(200, 150));
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton btnAplicar = new JButton("Aplicar Cupom");
        btnAplicar.addActionListener(e -> aplicarCupom());
        panel.add(btnAplicar, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Creates the purchase summary panel.
     * 
     * @param painelCliente the customer panel
     * @return the purchase summary panel
     * @Author: Vitória Isabela de Oliveira
     */
    private JPanel criarPainelResumo(JPanel painelCliente) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Resumo da Venda"));

        JPanel painelData = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelData.add(new JLabel("Data:"));
        painelData.add(dataPicker);

        JPanel painelSuperior = new JPanel();
        painelSuperior.setLayout(new BoxLayout(painelSuperior, BoxLayout.Y_AXIS));
        painelSuperior.add(painelCliente);
        painelSuperior.add(painelData);

        painelCliente.setPreferredSize(new Dimension(400, 30));
        painelCliente.setMaximumSize(new Dimension(400, 30));

        JPanel painelPagamento = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelPagamento.add(new JLabel("Forma de Pagamento:"));
        paymentMethodComboBox = new JComboBox<>(new String[] { "Cartão de Crédito", "Boleto", "Pix" });
        painelPagamento.add(paymentMethodComboBox);
        painelSuperior.add(painelPagamento);

        panel.add(painelSuperior, BorderLayout.NORTH);

        JScrollPane tableScrollPane = new JScrollPane(itensVendaTable);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        JPanel painelTotais = new JPanel(new GridLayout(3, 1));
        painelTotais.add(totalSemDescontoLabel);
        painelTotais.add(totalComDescontoLabel);
        painelTotais.add(totalLabel);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnRemover = new JButton("Remover Produto");
        btnRemover.addActionListener(e -> removerProdutoDaVenda());
        painelBotoes.add(btnRemover);

        JPanel painelInferior = new JPanel(new BorderLayout());
        painelInferior.add(painelTotais, BorderLayout.CENTER);
        painelInferior.add(painelBotoes, BorderLayout.SOUTH);

        panel.add(painelInferior, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Creates the actions panel.
     * 
     * @return the actions panel
     * @Author: Vitória Isabela de Oliveira
     */
    private JPanel criarPainelAcoes() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnFinalizar = new JButton("Finalizar Venda");
        btnFinalizar.addActionListener(e -> finalizarVenda());
        panel.add(btnFinalizar);
        return panel;
    }

    /**
     * Loads initial data for products, coupons and customers.
     * 
     * @Author: Vitória Isabela de Oliveira
     */
    private void carregarDadosIniciais() {
        carregarProdutos();
        carregarCupons();
        carregarClientes();
    }

    /**
     * Loads the coupons on the list.
     * 
     * @Author: Vitória Isabela de Oliveira
     */
    private void carregarCupons() {
        cuponsListModel.clear();
        List<Cupom> cupons = gestorVendas.listarCupons();

        Map<String, List<Cupom>> groupedCoupons = cupons.stream()
                .collect(Collectors.groupingBy(
                        cupom -> cupom instanceof CupomQuantidadeLimitada ? "Quantidade Limitada" : "Valor Mínimo"));

        groupedCoupons.values().forEach(list -> list.sort(Comparator.comparing(Cupom::getCodigo)));

        addCouponsByCategory(groupedCoupons, "Quantidade Limitada");
        addCouponsByCategory(groupedCoupons, "Valor Mínimo");
    }

    /**
     * Adds coupons by category.
     * 
     * @Author: Vitória Isabela de Oliveira
     */
    private void addCouponsByCategory(Map<String, List<Cupom>> groupedCoupons, String category) {
        if (groupedCoupons.containsKey(category)) {
            cuponsListModel.addElement(new CupomHeader(category.toUpperCase()));
            for (Cupom cupom : groupedCoupons.get(category)) {
                cuponsListModel.addElement(cupom);
            }
        }
    }

    /**
     * Load the customers on combo box.
     * 
     * @Author: Vitória Isabela de Oliveira
     */
    private void carregarClientes() {
        clienteComboBox.removeAllItems();
        clienteComboBox.addItem(null);
        List<Cliente> clientes = gestorVendas.listarClientes();
        clientes.sort(Comparator.comparing(Cliente::getNome));
        for (Cliente cliente : clientes) {
            clienteComboBox.addItem(cliente);
        }
    }

    /**
     * Adds a selected product to the sale with the specified quantity and applies
     * any active coupon discount.
     * 
     * @Author: Vitória Isabela de Oliveira
     */
    public void adicionarProdutoAVenda() {
        int selectedRow = produtosTable.getSelectedRow();
        if (selectedRow != -1) {
            if (produtosTableModel.getValueAt(selectedRow, 1) instanceof String) {
                JOptionPane.showMessageDialog(this, "Selecione um produto válido.", "Aviso",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Produto produtoSelecionado = gestorVendas
                    .buscarProdutoPorNome((String) produtosTableModel.getValueAt(selectedRow, 0));
            if (produtoSelecionado != null) {
                try {
                    int quantidade = Integer.parseInt(quantidadeField.getText());
                    if (quantidade > 0) {
                        double precoUnitario = produtoSelecionado.getPreco();
                        double descontoUnitario = 0.0;

                        Cupom cupomSelecionado = cuponsList.getSelectedValue();
                        if (cupomSelecionado != null) {
                            double totalItemSemDesconto = precoUnitario * quantidade;
                            if (cupomSelecionado.podeSerAplicado(totalItemSemDesconto)) {
                                descontoUnitario = truncate(
                                        (cupomSelecionado.getPercentualDesconto() / 100) * precoUnitario);
                            }
                        }

                        double totalItem = (precoUnitario * quantidade) - (descontoUnitario * quantidade);
                        totalItem = truncate(totalItem);

                        int itemCount = itensVendaTableModel.getRowCount() + 1;
                        itensVendaTableModel.addRow(new Object[] {
                                itemCount,
                                produtoSelecionado.getNome(),
                                quantidade,
                                precoUnitario,
                                String.format("%.2f", descontoUnitario),
                                String.format("%.2f", totalItem)
                        });
                        this.atualizarTotaisIndividuais();

                    } else {
                        JOptionPane.showMessageDialog(this, "A quantidade deve ser maior que zero.", "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Quantidade inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto para adicionar à venda.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Truncates a double to two decimal places.
     * 
     * @param value The value to be truncated.
     * @return a double
     * @Author: Vitória Isabela de Oliveira
     */
    private double truncate(double value) {
        return (double) ((int) (value * 100)) / 100;
    }

    /**
     * Updates all totals.
     * 
     * @Author: Vitória Isabela de Oliveira
     */
    private void atualizarTotaisIndividuais() {
        double totalSemDesconto = 0.0;
        double totalComDesconto = 0.0;

        for (int i = 0; i < itensVendaTableModel.getRowCount(); i++) {
            double quantidade = (int) itensVendaTableModel.getValueAt(i, 2);
            double precoUnitario = (double) itensVendaTableModel.getValueAt(i, 3);
            double descontoUnitario = Double.parseDouble((String) itensVendaTableModel.getValueAt(i, 4));

            double totalItemSemDesconto = precoUnitario * quantidade;
            double totalItemComDesconto = totalItemSemDesconto - (descontoUnitario * quantidade);

            totalSemDesconto += totalItemSemDesconto;
            totalComDesconto += totalItemComDesconto;
        }

        totalSemDescontoLabel.setText(String.format("Total sem Desconto: R$%.2f", totalSemDesconto));
        totalComDescontoLabel.setText(String.format("Total com Desconto: R$%.2f", totalComDesconto));
        totalLabel.setText(String.format("Desconto: R$%.2f", totalSemDesconto - totalComDesconto));
    }

    /**
     * Applies the selected coupon to the current sale, updating item discounts and
     * totals.
     * 
     * @Author: Vitória Isabela de Oliveira
     */
    public void aplicarCupom() {
        Cupom cupomSelecionado = cuponsList.getSelectedValue();
        if (cupomSelecionado != null) {
            double totalSemDesconto = calcularTotalSemDesconto();
            if (cupomSelecionado.podeSerAplicado(totalSemDesconto)) {
                for (int i = 0; i < itensVendaTableModel.getRowCount(); i++) {
                    double precoUnitario = (double) itensVendaTableModel.getValueAt(i, 3);
                    int quantidade = (int) itensVendaTableModel.getValueAt(i, 2);

                    double descontoUnitario = truncate(
                            (cupomSelecionado.getPercentualDesconto() / 100) * precoUnitario);
                    double totalItemComDesconto = (precoUnitario * quantidade) - (descontoUnitario * quantidade);
                    totalItemComDesconto = truncate(totalItemComDesconto);

                    itensVendaTableModel.setValueAt(String.format("%.2f", descontoUnitario), i, 4);
                    itensVendaTableModel.setValueAt(String.format("%.2f", totalItemComDesconto), i, 5);
                }
                atualizarTotaisIndividuais();
            } else {
                if (cupomSelecionado instanceof CupomValorMinimo) {
                    JOptionPane.showMessageDialog(this,
                            "O valor mínimo de compra não foi atingido para aplicar este cupom.",
                            "Erro ao Aplicar Cupom",
                            JOptionPane.ERROR_MESSAGE);
                } else if (cupomSelecionado instanceof CupomQuantidadeLimitada) {
                    JOptionPane.showMessageDialog(this,
                            "O limite de utilizações para este cupom foi ultrapassado.",
                            "Erro ao Aplicar Cupom",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "O cupom não pode ser aplicado.",
                            "Erro ao Aplicar Cupom",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um cupom para aplicar.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Removes a product from the list of items sale.
     * 
     * @Author: Vitória Isabela de Oliveira
     */
    private void removerProdutoDaVenda() {
        int selectedRow = itensVendaTable.getSelectedRow();
        if (selectedRow != -1) {
            itensVendaTableModel.removeRow(selectedRow);
            atualizarTotal();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto para remover.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Calculates all product without discount.
     * 
     * @return a double value
     * @Author: Vitória Isabela de Oliveira
     */
    private double calcularTotalSemDesconto() {
        double total = 0.0;
        for (int i = 0; i < itensVendaTableModel.getRowCount(); i++) {
            String totalString = (String) itensVendaTableModel.getValueAt(i, 5);
            double totalValue = Double.parseDouble(totalString);
            total += totalValue;
        }
        return total;
    }

    /**
     * Finalize the sale.
     * 
     * @Author: Vitória Isabela de Oliveira
     */
    public void finalizarVenda() {
        if (itensVendaTableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Adicione produtos à venda antes de finalizar.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Cliente clienteSelecionado = (Cliente) clienteComboBox.getSelectedItem();

        if (clienteSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente antes de finalizar a venda.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        Date selectedDate = (Date) dataPicker.getModel().getValue();

        if (selectedDate == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma data válida.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalDate dataSelecionada = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Venda venda = new Venda();
        venda.setData(dataSelecionada);
        venda.setFormaPagamento((String) paymentMethodComboBox.getSelectedItem());

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

        venda.setCliente(clienteSelecionado);

        try {
            gestorVendas.realizarVenda(venda);
            JOptionPane.showMessageDialog(this, "Venda finalizada com sucesso!", "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
            itensVendaTableModel.setRowCount(0);
            atualizarTotal();
        } catch (CupomInvalidoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Updates the labels to reflect the current totals and discount.
     * 
     * @Author: Vitória Isabela de Oliveira
     */
    private void atualizarTotal() {
        double totalSemDesconto = calcularTotalSemDesconto();
        double totalComDesconto = totalSemDesconto;

        Cupom cupomSelecionado = cuponsList.getSelectedValue();
        if (cupomSelecionado != null && cupomSelecionado.podeSerAplicado(totalSemDesconto)) {
            totalComDesconto = cupomSelecionado.aplicarDesconto(totalSemDesconto);
        }

        totalSemDescontoLabel.setText(String.format("Total sem Desconto: R$%.2f", totalSemDesconto));
        totalComDescontoLabel.setText(String.format("Total com Desconto: R$%.2f", totalComDesconto));
        totalLabel.setText(String.format("Desconto: R$%.2f", totalSemDesconto - totalComDesconto));
    }

    /**
     * Getters para teste
     */
    public DefaultTableModel getProdutosTableModel() {
        return produtosTableModel;
    }

    public DefaultListModel<Cupom> getCuponsListModel() {
        return cuponsListModel;
    }

    public DefaultTableModel getItensVendaTableModel() {
        return itensVendaTableModel;
    }

    public JTable getItensVendaTable() {
        return itensVendaTable;
    }

    public JLabel getTotalLabel() {
        return totalLabel;
    }

    public JComboBox<Cliente> getClienteComboBox() {
        return clienteComboBox;
    }

    public JTextField getQuantidadeField() {
        return quantidadeField;
    }

    public JLabel getTotalSemDescontoLabel() {
        return totalSemDescontoLabel;
    }

    public JLabel getTotalComDescontoLabel() {
        return totalComDescontoLabel;
    }

    public JDatePickerImpl getDataPicker() {
        return dataPicker;
    }
}