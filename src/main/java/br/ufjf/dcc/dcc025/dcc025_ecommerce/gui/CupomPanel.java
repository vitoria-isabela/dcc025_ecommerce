package br.ufjf.dcc.dcc025.dcc025_ecommerce.gui;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.*;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.GestorVendas;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.repository.CupomRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * JPanel representing the coupon management section of the e-commerce system
 * GUI.
 * Allows to add, edit, delete, and view available coupons.
 * 
 * @Author: Vitoria Isabela de Oliveira - 202065097C
 */
public class CupomPanel extends JPanel {
    private final JTextField txtCodigo;
    private final JTextField txtPercentual;
    private final JTextField txtCondicional;
    private final JComboBox<String> comboType;
    private JTable tabelaCupons;
    private JButton btnSalvar, btnEditar, btnExcluir, btnNovo;
    private Cupom cupomSelecionadoParaEdicao = null;

    /**
     * Constructs a CupomPanel, initializing the UI components and populating the
     * coupon table.
     */
    public CupomPanel() {
        setLayout(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));

        comboType = new JComboBox<>(new String[] { "Quantidade Limitada", "Valor Mínimo" });
        txtCodigo = new JTextField();
        txtPercentual = new JTextField();
        txtCondicional = new JTextField();
        btnSalvar = new JButton("Salvar Cupom");
        btnEditar = new JButton("Editar Cupom");
        btnExcluir = new JButton("Excluir Cupom");
        btnNovo = new JButton("Novo Cupom");

        txtCondicional.setToolTipText(
                "Quantidade Limitada: Número máximo de usos (ex: 10)\n" +
                        "Valor Mínimo: Valor mínimo da compra (ex: 100.00)");

        formPanel.add(new JLabel("Tipo:"));
        formPanel.add(comboType);
        formPanel.add(new JLabel("Código:"));
        formPanel.add(txtCodigo);
        formPanel.add(new JLabel("Percentual (%):"));
        formPanel.add(txtPercentual);
        formPanel.add(new JLabel("Condicional:"));
        formPanel.add(txtCondicional);

        add(formPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        btnSalvar.addActionListener(e -> salvarCupom());
        btnEditar.setEnabled(false);
        btnEditar.addActionListener(e -> editarCupom());
        btnExcluir.setEnabled(false);
        btnExcluir.addActionListener(e -> excluirCupom());

        btnNovo.addActionListener(e -> {
            limparCampos();
            cupomSelecionadoParaEdicao = null;
            btnSalvar.setText("Salvar Cupom");
            txtCodigo.setEditable(true);
            tabelaCupons.clearSelection();
        });

        buttonPanel.add(btnNovo);
        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnEditar);
        buttonPanel.add(btnExcluir);
        add(buttonPanel, BorderLayout.SOUTH);

        String[] colunas = { "Código", "Tipo", "Percentual", "Condicional" };
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        tabelaCupons = new JTable(model);
        tabelaCupons.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaCupons.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabelaCupons.getSelectedRow() != -1) {
                btnEditar.setEnabled(true);
                btnExcluir.setEnabled(true);
                preencherFormularioParaEdicao();
            } else {
                btnEditar.setEnabled(false);
                btnExcluir.setEnabled(false);
                limparCampos();
                cupomSelecionadoParaEdicao = null;
                btnSalvar.setText("Salvar Cupom");
            }
        });
        JScrollPane tableScrollPane = new JScrollPane(tabelaCupons);
        tableScrollPane.setPreferredSize(new Dimension(700, 150));
        add(tableScrollPane, BorderLayout.CENTER);

        atualizarTabelaCupons();
    }

    /**
     * Fills the form fields with the data of the selected coupon from the table for
     * editing.
     */
    private void preencherFormularioParaEdicao() {
        int selectedRow = tabelaCupons.getSelectedRow();
        if (selectedRow == -1)
            return;

        DefaultTableModel model = (DefaultTableModel) tabelaCupons.getModel();
        String codigo = (String) model.getValueAt(selectedRow, 0);
        String tipo = (String) model.getValueAt(selectedRow, 1);

        cupomSelecionadoParaEdicao = GestorVendas.getInstance().buscarCupom(codigo);

        if (cupomSelecionadoParaEdicao != null) {
            txtCodigo.setText(cupomSelecionadoParaEdicao.getCodigo());
            txtCodigo.setEditable(false);
            txtPercentual.setText(String.valueOf(cupomSelecionadoParaEdicao.getPercentualDesconto()));
            comboType.setSelectedItem(tipo);

            String conditionalValue = "";
            if (cupomSelecionadoParaEdicao instanceof CupomQuantidadeLimitada) {
                conditionalValue = String
                        .valueOf(((CupomQuantidadeLimitada) cupomSelecionadoParaEdicao).getMaximoUtilizacoes());
            } else if (cupomSelecionadoParaEdicao instanceof CupomValorMinimo) {
                conditionalValue = String.valueOf(((CupomValorMinimo) cupomSelecionadoParaEdicao).getValorMinimo());
            }
            txtCondicional.setText(conditionalValue);
            btnSalvar.setText("Atualizar Cupom");
        }
    }

    /**
     * Validates input fields, creates a new coupon or updates an existing one, adds
     * it to the system,
     * clears the input fields, and updates the coupon table.
     * Displays error messages for invalid inputs or exceptions.
     */
    public void salvarCupom() {
        try {
            validarCampos();
            Cupom cupom = criarCupom();

            if (cupomSelecionadoParaEdicao != null) {
                cupomSelecionadoParaEdicao.setPercentualDesconto(cupom.getPercentualDesconto());
                if (cupomSelecionadoParaEdicao instanceof CupomQuantidadeLimitada
                        && cupom instanceof CupomQuantidadeLimitada) {
                    ((CupomQuantidadeLimitada) cupomSelecionadoParaEdicao)
                            .setMaximoUtilizacoes(((CupomQuantidadeLimitada) cupom).getMaximoUtilizacoes());
                } else if (cupomSelecionadoParaEdicao instanceof CupomValorMinimo
                        && cupom instanceof CupomValorMinimo) {
                    ((CupomValorMinimo) cupomSelecionadoParaEdicao)
                            .setValorMinimo(((CupomValorMinimo) cupom).getValorMinimo());
                }
                GestorVendas.getInstance().editarCupom(cupomSelecionadoParaEdicao);
                JOptionPane.showMessageDialog(this, "Cupom atualizado com sucesso!", "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);

            } else {
                GestorVendas.getInstance().adicionarCupom(cupom);
                JOptionPane.showMessageDialog(this, "Cupom salvo com sucesso!", "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            limparCampos();
            atualizarTabelaCupons();
            cupomSelecionadoParaEdicao = null;
            btnSalvar.setText("Salvar Cupom");
            tabelaCupons.clearSelection();
            txtCodigo.setEditable(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Edits the selected coupon using information from the input fields.
     */
    public void editarCupom() {
        if (cupomSelecionadoParaEdicao == null) {
            JOptionPane.showMessageDialog(this, "Selecione um cupom para editar.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        salvarCupom();
    }

    /**
     * Deletes the selected coupon from the system.
     */
    public void excluirCupom() {
        int selectedRow = tabelaCupons.getSelectedRow();
        if (selectedRow != -1) {
            String codigo = (String) tabelaCupons.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Deseja excluir o cupom " + codigo + "?", "Confirmação",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                GestorVendas.getInstance().excluirCupom(codigo);
                atualizarTabelaCupons();
                limparCampos();
                cupomSelecionadoParaEdicao = null;
                btnSalvar.setText("Salvar Cupom");
                tabelaCupons.clearSelection();
                JOptionPane.showMessageDialog(this, "Cupom excluído com sucesso!", "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);

            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um cupom para excluir.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Validates the input fields to ensure they are not empty.
     * 
     * @throws Exception if any validation fails
     */
    private void validarCampos() throws Exception {
        if (txtCodigo.getText().isEmpty()) {
            throw new Exception("O campo Código é obrigatório!");
        }
        if (txtPercentual.getText().isEmpty()) {
            throw new Exception("O campo Percentual é obrigatório!");
        }
        if (txtCondicional.getText().isEmpty()) {
            throw new Exception("O campo Condicional é obrigatório!");
        }
    }

    /**
     * Creates a new Cupom object based on the selected coupon type.
     * 
     * @return the newly created Cupom object
     * @throws Exception if there are errors parsing numeric values or invalid
     *                   inputs
     */
    private Cupom criarCupom() throws Exception {
        String tipo = (String) comboType.getSelectedItem();
        String codigo = txtCodigo.getText();
        double percentual;

        try {
            percentual = Double.parseDouble(txtPercentual.getText());
        } catch (NumberFormatException e) {
            throw new Exception("Percentual inválido! Use números (ex: 10.0).");
        }

        if (tipo.equals("Quantidade Limitada")) {
            try {
                int max = Integer.parseInt(txtCondicional.getText());
                return new CupomQuantidadeLimitada(codigo, percentual, max);
            } catch (NumberFormatException e) {
                throw new Exception("Número máximo de usos inválido! Use números inteiros (ex: 10).");
            }
        } else {
            try {
                double minimo = Double.parseDouble(txtCondicional.getText());
                return new CupomValorMinimo(codigo, percentual, minimo);
            } catch (NumberFormatException e) {
                throw new Exception("Valor mínimo inválido! Use números (ex: 100.00).");
            }
        }
    }

    /**
     * Clears all input fields.
     */
    private void limparCampos() {
        txtCodigo.setText("");
        txtPercentual.setText("");
        txtCondicional.setText("");
        txtCodigo.setEditable(true);
    }

    /**
     * Updates the coupon table with the latest list of coupons from the repository.
     * Groups the coupons by type and sorts them by code for better organization.
     */
    private void atualizarTabelaCupons() {
        List<Cupom> cupons = CupomRepository.getInstance().listarTodos();
        DefaultTableModel model = (DefaultTableModel) tabelaCupons.getModel();
        model.setRowCount(0);

        Map<String, List<Cupom>> groupedCoupons = cupons.stream()
                .collect(Collectors.groupingBy(
                        cupom -> cupom instanceof CupomQuantidadeLimitada ? "Quantidade Limitada" : "Valor Mínimo"));

        groupedCoupons.values().forEach(list -> list.sort(Comparator.comparing(Cupom::getCodigo)));

        addCouponsByCategory(model, groupedCoupons, "Quantidade Limitada");
        addCouponsByCategory(model, groupedCoupons, "Valor Mínimo");
    }

    /**
     * Adds coupons of a specific category to the table model.
     * 
     * @param model          The DefaultTableModel of the table
     * @param groupedCoupons A map of coupons grouped by category
     * @param category       The category to be added
     */
    private void addCouponsByCategory(DefaultTableModel model, Map<String, List<Cupom>> groupedCoupons,
            String category) {
        if (groupedCoupons.containsKey(category)) {
            model.addRow(new Object[] { "", category.toUpperCase(), "", "" });

            for (Cupom cupom : groupedCoupons.get(category)) {
                String condicional = (cupom instanceof CupomQuantidadeLimitada)
                        ? "Máximo de usos: " + ((CupomQuantidadeLimitada) cupom).getMaximoUtilizacoes()
                        : "Valor mínimo: R$ " + ((CupomValorMinimo) cupom).getValorMinimo();

                model.addRow(new Object[] {
                        cupom.getCodigo(),
                        category,
                        cupom.getPercentualDesconto() + "%",
                        condicional
                });
            }
        }
    }

    /**
     * Returns the code text field.
     * 
     * @return The code text field.
     */
    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    /**
     * Returns the percent text field.
     * 
     * @return The percent text field.
     */
    public JTextField getTxtPercentual() {
        return txtPercentual;
    }

    /**
     * Returns the conditional text field.
     * 
     * @return The conditional text field.
     */
    public JTextField getTxtCondicional() {
        return txtCondicional;
    }

    /**
     * Returns the combo type.
     * 
     * @return The combo type.
     */
    public JComboBox<String> getComboType() {
        return comboType;
    }
}