package br.ufjf.dcc.dcc025.dcc025_ecommerce.gui;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.*;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.repository.ProdutoRepository;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JPanel representing the product management section of the e-commerce system GUI.
 * Allows to add, edit, delete, and view products with category-specific details.
 * @Author: Vitoria Isabela de Oliveira - 202065097C
 */
public class ProdutoPanel extends JPanel {
    private JTextField txtCodigo, txtNome, txtPreco;
    private JTextField txtGarantia, txtMarca, txtEspecificacoesTecnicas;
    private JTextField txtTamanho, txtCor, txtMaterial, txtLoteRoupa;
    private JDatePickerImpl dateValidadePicker, dateFabricacaoPicker;
    private JTextField txtLoteAlimento;
    private JComboBox<String> comboType;
    private JTable tabelaProdutos;
    private JPanel especificoPanel;
    private JButton btnSalvar, btnEditar, btnExcluir, btnNovo;
    private Produto produtoSelecionadoParaEdicao = null;
    /**
     * Constructs a ProdutoPanel using the provided ProdutoRepositoryInterface.
     * Initializes UI components, configures layout, and populates the product table.
     * @param user Logged-in user for role-based access
     */
    public ProdutoPanel(User user) {
        ProdutoRepository.getInstance();
        setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        add(scrollPane, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        Dimension fieldDimension = new Dimension(250, 25);

        comboType = new JComboBox<>(new String[]{"Eletrônico", "Roupa", "Alimento"});
        comboType.setPreferredSize(fieldDimension);
        comboType.addActionListener(e -> atualizarCamposEspecificos());

        txtCodigo = new JTextField();
        txtCodigo.setPreferredSize(fieldDimension);
        txtNome = new JTextField();
        txtNome.setPreferredSize(fieldDimension);
        txtPreco = new JTextField();
        txtPreco.setPreferredSize(fieldDimension);

        addFormField(formPanel, gbc, "Tipo:*", comboType, 0);
        addFormField(formPanel, gbc, "Código:*", txtCodigo, 1);
        addFormField(formPanel, gbc, "Nome:*", txtNome, 2);
        addFormField(formPanel, gbc, "Preço (R$):*", txtPreco, 3);

        especificoPanel = new JPanel(new CardLayout());

        JPanel eletronicoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcEletronico = new GridBagConstraints();
        gbcEletronico.insets = new Insets(5, 5, 5, 5);
        gbcEletronico.fill = GridBagConstraints.HORIZONTAL;
        gbcEletronico.weightx = 1.0;

        txtGarantia = new JTextField();
        txtGarantia.setPreferredSize(fieldDimension);
        txtMarca = new JTextField();
        txtMarca.setPreferredSize(fieldDimension);
        txtEspecificacoesTecnicas = new JTextField();
        txtEspecificacoesTecnicas.setPreferredSize(fieldDimension);

        addFormField(eletronicoPanel, gbcEletronico, "Garantia (meses):*", txtGarantia, 0);
        addFormField(eletronicoPanel, gbcEletronico, "Marca:*", txtMarca, 1);
        addFormField(eletronicoPanel, gbcEletronico, "Especificações Técnicas:*", txtEspecificacoesTecnicas, 2);

        JPanel roupaPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcRoupa = new GridBagConstraints();
        gbcRoupa.insets = new Insets(5, 5, 5, 5);
        gbcRoupa.fill = GridBagConstraints.HORIZONTAL;
        gbcRoupa.weightx = 1.0;

        txtTamanho = new JTextField();
        txtTamanho.setPreferredSize(fieldDimension);
        txtCor = new JTextField();
        txtCor.setPreferredSize(fieldDimension);
        txtMaterial = new JTextField();
        txtMaterial.setPreferredSize(fieldDimension);
        txtLoteRoupa = new JTextField();
        txtLoteRoupa.setPreferredSize(fieldDimension);

        addFormField(roupaPanel, gbcRoupa, "Tamanho:*", txtTamanho, 0);
        addFormField(roupaPanel, gbcRoupa, "Cor:*", txtCor, 1);
        addFormField(roupaPanel, gbcRoupa, "Material:*", txtMaterial, 2);
        addFormField(roupaPanel, gbcRoupa, "Lote:*", txtLoteRoupa, 3);

        JPanel alimentoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcAlimento = new GridBagConstraints();
        gbcAlimento.insets = new Insets(5, 5, 5, 5);
        gbcAlimento.fill = GridBagConstraints.HORIZONTAL;
        gbcAlimento.weightx = 1.0;

        dateValidadePicker = createDatePicker();
        dateFabricacaoPicker = createDatePicker();
        txtLoteAlimento = new JTextField();
        txtLoteAlimento.setPreferredSize(fieldDimension);

        addFormField(alimentoPanel, gbcAlimento, "Data de Validade:*", dateValidadePicker, 0);
        addFormField(alimentoPanel, gbcAlimento, "Data de Fabricação:*", dateFabricacaoPicker, 1);
        addFormField(alimentoPanel, gbcAlimento, "Lote:*", txtLoteAlimento, 2);

        especificoPanel.add(eletronicoPanel, "Eletrônico");
        especificoPanel.add(roupaPanel, "Roupa");
        especificoPanel.add(alimentoPanel, "Alimento");

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        formPanel.add(especificoPanel, gbc);

        mainPanel.add(formPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSalvar = new JButton("Salvar Produto");
        btnSalvar.addActionListener(e -> salvarProduto());
        btnEditar = new JButton("Editar Produto");
        btnEditar.setEnabled(false);
        btnEditar.addActionListener(e -> editarProduto());
        btnExcluir = new JButton("Excluir Produto");
        btnExcluir.setEnabled(false);
        btnExcluir.addActionListener(e -> excluirProduto());

        btnNovo = new JButton("Novo Produto");
        btnNovo.addActionListener(e -> {
            limparCampos();
            produtoSelecionadoParaEdicao = null;
            btnSalvar.setText("Salvar Produto");
            txtCodigo.setEditable(true);
            tabelaProdutos.clearSelection();
        });

        buttonPanel.add(btnNovo);
        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnEditar);
        buttonPanel.add(btnExcluir);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        String[] colunas = {"Código", "Nome", "Tipo", "Preço"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        tabelaProdutos = new JTable(model);
        tabelaProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaProdutos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabelaProdutos.getSelectedRow() != -1) {
                btnEditar.setEnabled(true);
                btnExcluir.setEnabled(true);
                preencherFormularioParaEdicao();

            } else {
                btnEditar.setEnabled(false);
                btnExcluir.setEnabled(false);
                limparCampos();
                produtoSelecionadoParaEdicao = null;
                btnSalvar.setText("Salvar Produto");
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(tabelaProdutos);
        tableScrollPane.setPreferredSize(new Dimension(700, 150));
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        atualizarTabelaProdutos();
        atualizarCamposEspecificos();
    }

    /**
     * Fills the form fields with the data of the selected product from the table for editing.
     */
    private void preencherFormularioParaEdicao() {
        int selectedRow = tabelaProdutos.getSelectedRow();
        if (selectedRow == -1) return;

        DefaultTableModel model = (DefaultTableModel) tabelaProdutos.getModel();
        String codigo = (String) model.getValueAt(selectedRow, 0);
        String tipo = (String) model.getValueAt(selectedRow, 2);

        produtoSelecionadoParaEdicao = ProdutoRepository.getInstance().buscarPorCodigo(codigo);

        if (produtoSelecionadoParaEdicao != null) {
            txtCodigo.setText(produtoSelecionadoParaEdicao.getCodigo());
            txtNome.setText(produtoSelecionadoParaEdicao.getNome());
            txtPreco.setText(String.valueOf(produtoSelecionadoParaEdicao.getPreco()));
            comboType.setSelectedItem(tipo);

            if (produtoSelecionadoParaEdicao instanceof Eletronico) {
                Eletronico eletronico = (Eletronico) produtoSelecionadoParaEdicao;
                txtGarantia.setText(String.valueOf(eletronico.getGarantiaMeses()));
                txtMarca.setText(eletronico.getMarca());
                txtEspecificacoesTecnicas.setText(eletronico.getEspecificacoesTecnicas());
            } else if (produtoSelecionadoParaEdicao instanceof Roupa) {
                Roupa roupa = (Roupa) produtoSelecionadoParaEdicao;
                txtTamanho.setText(roupa.getTamanho());
                txtCor.setText(roupa.getCor());
                txtMaterial.setText(roupa.getMaterial());
                txtLoteRoupa.setText(roupa.getLote());
            } else if (produtoSelecionadoParaEdicao instanceof Alimento) {
                Alimento alimento = (Alimento) produtoSelecionadoParaEdicao;

                if (alimento.getDataValidade() != null) {
                    Calendar validadeCalendar = Calendar.getInstance();
                    LocalDate validade = alimento.getDataValidade();
                    validadeCalendar.set(validade.getYear(), validade.getMonthValue() - 1, validade.getDayOfMonth());
                    ((UtilDateModel) dateValidadePicker.getModel()).setValue(validadeCalendar.getTime());
                } else {
                    ((UtilDateModel) dateValidadePicker.getModel()).setValue(null);
                }

                if (alimento.getDataFabricacao() != null) {
                    Calendar fabricacaoCalendar = Calendar.getInstance();
                    LocalDate fabricacao = alimento.getDataFabricacao();
                    fabricacaoCalendar.set(fabricacao.getYear(), fabricacao.getMonthValue() - 1, fabricacao.getDayOfMonth());
                    ((UtilDateModel) dateFabricacaoPicker.getModel()).setValue(fabricacaoCalendar.getTime());
                } else {
                    ((UtilDateModel) dateFabricacaoPicker.getModel()).setValue(null);
                }
                txtLoteAlimento.setText(alimento.getLote());
            }
            btnSalvar.setText("Atualizar Produto");
            txtCodigo.setEditable(false);
        }
    }

    /**
     * Adds a form field (label and component) to the specified panel with the provided layout constraints.
     * @param panel the JPanel to add the field to
     * @param gbc GridBagConstraints to position the field
     * @param label the text for the JLabel
     * @param field the JComponent representing the input field
     * @param row the row number for GridBagLayout
     */
    private void addFormField(JPanel panel, GridBagConstraints gbc, String label, JComponent field, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    /**
     * Creates and configures a JDatePickerImpl for date selection.
     * @return the configured JDatePickerImpl
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
     * Custom formatter for displaying and parsing dates in the JDatePickerImpl.
     */
    public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private final String datePattern = "dd-MM-yyyy";
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        /**
         * Converts a String in the specified date pattern to a Date object.
         * @param text a String to be converted
         * @return the Date object parsed from the String
         * @throws ParseException if parsing fails
         */
        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        /**
         * Converts a Date object to a String in the specified date pattern.
         * @param value the Date object to be converted
         * @return the String representation of the Date
         * @throws ParseException if formatting fails
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
     * Updates the visibility of product-specific details fields based on the selected product type.
     */
    private void atualizarCamposEspecificos() {
        CardLayout cl = (CardLayout) (especificoPanel.getLayout());
        cl.show(especificoPanel, (String) comboType.getSelectedItem());
    }

    /**
     * Attempts to save a new product with the entered details.
     * Validates input fields, creates the appropriate product object,
     * and updates the product table upon success. Shows error messages if necessary.
     */
    public void salvarProduto() {
        try {
            validarCampos();
            String tipo = (String) comboType.getSelectedItem();
            Produto produto = criarProduto(tipo);

            if (produtoSelecionadoParaEdicao != null) {
                produtoSelecionadoParaEdicao.setNome(produto.getNome());
                produtoSelecionadoParaEdicao.setPreco(produto.getPreco());
                if (produtoSelecionadoParaEdicao instanceof Eletronico && produto instanceof Eletronico) {
                    ((Eletronico) produtoSelecionadoParaEdicao).setGarantiaMeses(((Eletronico) produto).getGarantiaMeses());
                    ((Eletronico) produtoSelecionadoParaEdicao).setMarca(((Eletronico) produto).getMarca());
                    ((Eletronico) produtoSelecionadoParaEdicao).setEspecificacoesTecnicas(((Eletronico) produto).getEspecificacoesTecnicas());
                } else if (produtoSelecionadoParaEdicao instanceof Roupa && produto instanceof Roupa) {
                    ((Roupa) produtoSelecionadoParaEdicao).setTamanho(((Roupa) produto).getTamanho());
                    ((Roupa) produtoSelecionadoParaEdicao).setCor(((Roupa) produto).getCor());
                    ((Roupa) produtoSelecionadoParaEdicao).setMaterial(((Roupa) produto).getMaterial());
                    ((Roupa) produtoSelecionadoParaEdicao).setLote(((Roupa) produto).getLote());

                } else if (produtoSelecionadoParaEdicao instanceof Alimento && produto instanceof Alimento) {
                    ((Alimento) produtoSelecionadoParaEdicao).setDataFabricacao(((Alimento) produto).getDataFabricacao());
                    ((Alimento) produtoSelecionadoParaEdicao).setDataValidade(((Alimento) produto).getDataValidade());
                    ((Alimento) produtoSelecionadoParaEdicao).setLote(((Alimento) produto).getLote());
                    ((Alimento) produtoSelecionadoParaEdicao).setOrganico(((Alimento) produto).isOrganico());
                }
                ProdutoRepository.getInstance().editar(produtoSelecionadoParaEdicao);
                JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            } else {
                ProdutoRepository.getInstance().salvar(produto);
                JOptionPane.showMessageDialog(this, "Produto salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }

            limparCampos();
            atualizarTabelaProdutos();
            produtoSelecionadoParaEdicao = null;
            btnSalvar.setText("Salvar Produto");
            tabelaProdutos.clearSelection();
            txtCodigo.setEditable(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Edits the selected product using information from the input fields.
     */
    public void editarProduto() {
        if (produtoSelecionadoParaEdicao == null) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        salvarProduto();
    }

    /**
     * Deletes the selected product from the system.
     */
    public void excluirProduto() {
        int selectedRow = tabelaProdutos.getSelectedRow();
        if (selectedRow != -1) {
            String codigo = (String) tabelaProdutos.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Deseja excluir o produto " + codigo + "?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                ProdutoRepository.getInstance().excluir(codigo);
                atualizarTabelaProdutos();
                limparCampos();
                produtoSelecionadoParaEdicao = null;
                btnSalvar.setText("Salvar Produto");
                tabelaProdutos.clearSelection();
                JOptionPane.showMessageDialog(this, "Produto excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Validates all input fields, including those specific to the selected product type.
     * @throws Exception if any validation fails
     */
    public void validarCampos() throws Exception {
        if (txtCodigo.getText().isEmpty()) {
            throw new Exception("O campo Código é obrigatório!");
        }
        if (txtNome.getText().isEmpty()) {
            throw new Exception("O campo Nome é obrigatório!");
        }
        if (txtPreco.getText().isEmpty()) {
            throw new Exception("O campo Preço é obrigatório!");
        }
        String tipo = (String) comboType.getSelectedItem();
        switch (tipo) {
            case "Eletrônico":
                if (txtGarantia.getText().isEmpty() || txtMarca.getText().isEmpty() || txtEspecificacoesTecnicas.getText().isEmpty()) {
                    throw new Exception("Todos os campos específicos de Eletrônico são obrigatórios!");
                }
                break;
            case "Roupa":
                if (txtTamanho.getText().isEmpty() || txtCor.getText().isEmpty() || txtMaterial.getText().isEmpty() || txtLoteRoupa.getText().isEmpty()) {
                    throw new Exception("Todos os campos específicos de Roupa são obrigatórios!");
                }
                break;
            case "Alimento":
                if (dateValidadePicker.getModel().getValue() == null || dateFabricacaoPicker.getModel().getValue() == null || txtLoteAlimento.getText().isEmpty()) {
                    throw new Exception("Todos os campos específicos de Alimento são obrigatórios!");
                }
                break;
        }
    }

    /**
     * Creates a new Produto object of the specified type using the values from the input fields.
     * @param tipo the type of product to be created ("Eletrônico", "Roupa", or "Alimento")
     * @return the newly created Produto object
     * @throws Exception if there is an error parsing the input values or an invalid product type is specified
     */
    private Produto criarProduto(String tipo) throws Exception {
        String codigo = txtCodigo.getText();
        String nome = txtNome.getText();
        double preco;

        try {
            preco = Double.parseDouble(txtPreco.getText());
        } catch (NumberFormatException e) {
            throw new Exception("Preço inválido! Use números (ex: 29.90).");
        }

        switch (tipo) {
            case "Eletrônico":
                int garantia = Integer.parseInt(txtGarantia.getText().trim());
                String marca = txtMarca.getText().trim();
                String especificacoesTecnicas = txtEspecificacoesTecnicas.getText().trim();
                return new Eletronico(codigo, nome, preco, garantia, marca, especificacoesTecnicas);

            case "Roupa":
                String tamanho = txtTamanho.getText().trim();
                String cor = txtCor.getText().trim();
                String material = txtMaterial.getText().trim();
                String loteRoupa = txtLoteRoupa.getText().trim();
                return new Roupa(codigo, nome, preco, tamanho, cor, material, loteRoupa);

            case "Alimento":
                LocalDate validade = (dateValidadePicker.getModel().getValue() != null) ? ((Date) dateValidadePicker.getModel().getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
                LocalDate fabricacao = (dateFabricacaoPicker.getModel().getValue() != null) ? ((Date) dateFabricacaoPicker.getModel().getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
                String loteAlimento = txtLoteAlimento.getText().trim();
                return new Alimento(codigo, nome, preco, validade, fabricacao, loteAlimento, false);

            default:
                throw new Exception("Tipo de produto inválido.");
        }
    }

    /**
     * Clears the values of all input fields in the form.
     */
    private void limparCampos() {
        txtCodigo.setText("");
        txtNome.setText("");
        txtPreco.setText("");
        txtGarantia.setText("");
        txtMarca.setText("");
        txtEspecificacoesTecnicas.setText("");
        txtTamanho.setText("");
        txtCor.setText("");
        txtMaterial.setText("");
        txtLoteRoupa.setText("");
        dateValidadePicker.getModel().setValue(null);
        dateFabricacaoPicker.getModel().setValue(null);
        txtLoteAlimento.setText("");
        txtCodigo.setEditable(true);
    }

    /**
     * Updates the product table with the latest product data from the repository.
     * Groups the products by category and sorts them alphabetically for better organization.
     */
    public void atualizarTabelaProdutos() {
        List<Produto> produtos = ProdutoRepository.getInstance().listarTodos();
        DefaultTableModel model = (DefaultTableModel) tabelaProdutos.getModel();
        model.setRowCount(0);

        Map<String, List<Produto>> groupedProducts = produtos.stream()
                .collect(Collectors.groupingBy(Produto::getTipo));

        List<String> sortedCategories = new ArrayList<>(groupedProducts.keySet());
        Collections.sort(sortedCategories);

        for (String category : sortedCategories) {
            List<Produto> produtosCategoria = groupedProducts.getOrDefault(category, new ArrayList<>());

            produtosCategoria.sort(Comparator.comparing(produto -> produto.getNome().toLowerCase()));

            if (!produtosCategoria.isEmpty()) {
                model.addRow(new Object[]{category.toUpperCase(), "", "", ""});
            }

            for (Produto produto : produtosCategoria) {
                model.addRow(new Object[]{produto.getCodigo(), produto.getNome(), produto.getTipo(), produto.getPreco()});
            }
        }
    }

    /**
     * Getters for text fields and components
     */
    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JTextField getTxtNome() {
        return txtNome;
    }

    public JTextField getTxtPreco() {
        return txtPreco;
    }

    public JTextField getTxtGarantia() {
        return txtGarantia;
    }

    public JTextField getTxtMarca() {
        return txtMarca;
    }

    public JTextField getTxtEspecificacoesTecnicas() {
        return txtEspecificacoesTecnicas;
    }

    public JTextField getTxtTamanho() {
        return txtTamanho;
    }

    public JTextField getTxtCor() {
        return txtCor;
    }

    public JTextField getTxtMaterial() {
        return txtMaterial;
    }

    public JTextField getTxtLoteRoupa() {
        return txtLoteRoupa;
    }

    public JDatePickerImpl getDateValidadePicker() {
        return dateValidadePicker;
    }

    public JDatePickerImpl getDateFabricacaoPicker() {
        return dateFabricacaoPicker;
    }

    public JTextField getTxtLoteAlimento() {
        return txtLoteAlimento;
    }

    public JComboBox<String> getComboType() {
        return comboType;
    }

    public JTable getTabelaProdutos() {
        return tabelaProdutos;
    }

    /**
     * Setters for text fields and components
     */
    public void setTxtCodigo(String codigo) {
        this.txtCodigo.setText(codigo);
    }

    public void setTxtNome(String nome) {
        this.txtNome.setText(nome);
    }

    public void setTxtPreco(String preco) {
        this.txtPreco.setText(preco);
    }

    public void setTxtGarantia(String garantia) {
        this.txtGarantia.setText(garantia);
    }

    public void setTxtMarca(String marca) {
        this.txtMarca.setText(marca);
    }

    public void setTxtEspecificacoesTecnicas(String especificacoesTecnicas) {
        this.txtEspecificacoesTecnicas.setText(especificacoesTecnicas);
    }

    public void setComboType(String type) {
        this.comboType.setSelectedItem(type);
    }
}