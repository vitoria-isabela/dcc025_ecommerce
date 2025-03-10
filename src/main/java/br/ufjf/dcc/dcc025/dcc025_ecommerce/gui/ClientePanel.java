package br.ufjf.dcc.dcc025.dcc025_ecommerce.gui;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.GestorVendas;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Cliente;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Endereco;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.table.DefaultTableModel;

/**
 * JPanel representing the client management section of the e-commerce system GUI.
 * Allows to add, edit, delete, and view clients with their information.
 * @Author: Vitoria Isabela de Oliveira - 202065097C
 */
public class ClientePanel extends JPanel {
    private GestorVendas gestorVendas;
    private final JTextField cpfField = new JTextField(15);
    private final JTextField nomeField = new JTextField(20);
    private final JTextField emailField = new JTextField(25);
    private final JTextField telefoneField = new JTextField(15);
    private final JComboBox<String> tipoTelefoneCombo = new JComboBox<>(new String[]{"Celular", "Fixo"});
    private final JTextField ruaField = new JTextField(30);
    private final JTextField numeroField = new JTextField(10);
    private final JTextField complementoField = new JTextField(20);
    private final JTextField cidadeField = new JTextField(20);
    private final JTextField estadoField = new JTextField(20);
    private final JTextField cepField = new JTextField(10);
    private JTable tabelaClientes;
    private JButton btnSalvar, btnEditar, btnExcluir, btnNovo;
    private Cliente clienteSelecionadoParaEdicao = null;

    /**
     * Constructs a ClientePanel using the singleton instance of GestorVendas.
     */
    public ClientePanel() {
        this(GestorVendas.getInstance());
    }

    /**
     * Constructs a ClientePanel with the provided GestorVendas instance.
     * Initializes components and updates the client list.
     * @param gestorVendas the GestorVendas instance to use
     */
    public ClientePanel(GestorVendas gestorVendas) {
        this.gestorVendas = gestorVendas;
        setLayout(new BorderLayout(10, 10));
        inicializarComponentes();
        atualizarListaClientes();
    }

    /**
     * Returns the CPF field.
     * @return cpfField - CPF do cliente
     */
    public JTextField getCpfField() { return cpfField; }

    /**
     * Returns the Name field.
     * @return nomeField - Nome do cliente
     */
    public JTextField getNomeField() { return nomeField; }

    /**
     * Returns the e-mail field.
     * @return emailField - E-mail do cliente
     */
    public JTextField getEmailField() { return emailField; }

    /**
     * Returns the telephone field.
     * @return telefoneField - Telefone do cliente
     */
    public JTextField getTelefoneField() { return telefoneField; }

    /**
     * Returns the telephone's type combo.
     * @return tipoTelefoneCombo - Tipo de telefone
     */
    public JComboBox<String> getTipoTelefoneCombo() { return tipoTelefoneCombo; }

    /**
     * Returns the Address street field.
     * @return ruaField - Rua do endereço
     */
    public JTextField getRuaField() { return ruaField; }

    /**
     * Returns the Address number field.
     * @return numeroField - Número do endereço
     */
    public JTextField getNumeroField() { return numeroField; }

    /**
     * Returns the Address complement field.
     * @return complementoField - Complemento do endereço
     */
    public JTextField getComplementoField() { return complementoField; }

    /**
     * Returns the Address city field.
     * @return cidadeField - Cidade do endereço
     */
    public JTextField getCidadeField() { return cidadeField; }

    /**
     * Returns the Address state field.
     * @return estadoField - Estado do endereço
     */
    public JTextField getEstadoField() { return estadoField; }

    /**
     * Returns the Address zip-code field.
     * @return cepField - CEP do endereço
     */
    public JTextField getCepField() { return cepField; }

    /**
     * Initializes and organizes UI components for the client panel.
     */
    private void inicializarComponentes() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        cpfField.setToolTipText("Client CPF (required)");
        nomeField.setToolTipText("Client Name (required)");
        emailField.setToolTipText("Client Email (required)");
        telefoneField.setToolTipText("Client Phone (required)");
        ruaField.setToolTipText("Address Street (required)");
        numeroField.setToolTipText("Address Number (required)");
        cidadeField.setToolTipText("Address City (required)");
        estadoField.setToolTipText("Address State (required)");
        cepField.setToolTipText("Address ZIP Code (required)");

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("CPF*:"), gbc);
        gbc.gridx = 1;
        formPanel.add(cpfField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Name*:"), gbc);
        gbc.gridx = 1;
        formPanel.add(nomeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Email*:"), gbc);
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Phone Type:"), gbc);
        gbc.gridx = 1;
        formPanel.add(tipoTelefoneCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Phone*:"), gbc);
        gbc.gridx = 1;
        formPanel.add(telefoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("Street*:"), gbc);
        gbc.gridx = 1;
        formPanel.add(ruaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(new JLabel("Number*:"), gbc);
        gbc.gridx = 1;
        formPanel.add(numeroField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(new JLabel("Complement:"), gbc);
        gbc.gridx = 1;
        formPanel.add(complementoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        formPanel.add(new JLabel("City*:"), gbc);
        gbc.gridx = 1;
        formPanel.add(cidadeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        formPanel.add(new JLabel("State*:"), gbc);
        gbc.gridx = 1;
        formPanel.add(estadoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        formPanel.add(new JLabel("ZIP Code*:"), gbc);
        gbc.gridx = 1;
        formPanel.add(cepField, gbc);

        add(formPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSalvar = new JButton("Save Client");
        btnSalvar.addActionListener(e -> salvarCliente());
        btnEditar = new JButton("Edit Client");
        btnEditar.setEnabled(false);
        btnEditar.addActionListener(e -> editarCliente());
        btnExcluir = new JButton("Delete Client");
        btnExcluir.setEnabled(false);
        btnExcluir.addActionListener(e -> excluirCliente());

        btnNovo = new JButton("New Client");
        btnNovo.addActionListener(e -> {
            limparCampos();
            clienteSelecionadoParaEdicao = null;
            btnSalvar.setText("Save Client");
            cpfField.setEditable(true);
            tabelaClientes.clearSelection();
        });
        buttonPanel.add(btnNovo);
        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnEditar);
        buttonPanel.add(btnExcluir);

        add(buttonPanel, BorderLayout.SOUTH);

        String[] colunas = {"CPF", "Name", "Email", "Phone", "Address"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        tabelaClientes = new JTable(model);
        tabelaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaClientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabelaClientes.getSelectedRow() != -1) {
                btnEditar.setEnabled(true);
                btnExcluir.setEnabled(true);
                preencherFormularioParaEdicao();
            } else {
                btnEditar.setEnabled(false);
                btnExcluir.setEnabled(false);
                limparCampos();
                clienteSelecionadoParaEdicao = null;
                btnSalvar.setText("Save Client");
                cpfField.setEditable(true);
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(tabelaClientes);
        tableScrollPane.setPreferredSize(new Dimension(700, 200));
        add(tableScrollPane, BorderLayout.CENTER);
    }

    /**
     * Fills the form fields with the data of the selected client from the table for editing.
     */
    private void preencherFormularioParaEdicao() {
        int selectedRow = tabelaClientes.getSelectedRow();
        if (selectedRow == -1) return;

        DefaultTableModel model = (DefaultTableModel) tabelaClientes.getModel();
        String cpf = (String) model.getValueAt(selectedRow, 0);

        clienteSelecionadoParaEdicao = gestorVendas.buscarCliente(cpf);
        if (clienteSelecionadoParaEdicao != null) {
            cpfField.setText(clienteSelecionadoParaEdicao.getCpf());
            cpfField.setEditable(false);
            nomeField.setText(clienteSelecionadoParaEdicao.getNome());
            emailField.setText(clienteSelecionadoParaEdicao.getEmail());
            telefoneField.setText(clienteSelecionadoParaEdicao.getTelefone());
            tipoTelefoneCombo.setSelectedItem(clienteSelecionadoParaEdicao.getTelefone().startsWith("(") && clienteSelecionadoParaEdicao.getTelefone().length() == 14 ? "Celular" : "Fixo");

            Endereco endereco = clienteSelecionadoParaEdicao.getEndereco();
            ruaField.setText(endereco.getRua());
            numeroField.setText(endereco.getNumero());
            complementoField.setText(endereco.getComplemento());
            cidadeField.setText(endereco.getCidade());
            estadoField.setText(endereco.getEstado());
            cepField.setText(endereco.getCep());
            btnSalvar.setText("Update Client");
        }
    }

    /**
     * Validates input fields, creates a new client object or updates an existing one, and adds it to the system.
     * Clears the input fields and updates the client list after successful addition.
     */
    public void salvarCliente() {
        try {
            validarCampos();
            String cpf = cpfField.getText();

            if (clienteSelecionadoParaEdicao == null) {
                cpf = formatCpf(cpf);
            }
            String nome = nomeField.getText();
            String email = emailField.getText();
            String telefone = formatTelefone(telefoneField.getText(), (String) tipoTelefoneCombo.getSelectedItem());
            Endereco endereco = new Endereco(
                    ruaField.getText(),
                    numeroField.getText(),
                    complementoField.getText(),
                    cidadeField.getText(),
                    estadoField.getText(),
                    cepField.getText()
            );
            Cliente cliente = new Cliente(cpf, nome, email, telefone, endereco);

            if (clienteSelecionadoParaEdicao != null) {
                clienteSelecionadoParaEdicao.setNome(nome);
                clienteSelecionadoParaEdicao.setEmail(email);
                clienteSelecionadoParaEdicao.setTelefone(telefone);
                clienteSelecionadoParaEdicao.setEndereco(endereco);
                gestorVendas.editarCliente(clienteSelecionadoParaEdicao);
                JOptionPane.showMessageDialog(this, "Client updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            } else {
                gestorVendas.adicionarCliente(cliente);
                JOptionPane.showMessageDialog(this, "Client registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }

            limparCampos();
            atualizarListaClientes();
            clienteSelecionadoParaEdicao = null;
            btnSalvar.setText("Save Client");
            tabelaClientes.clearSelection();
            cpfField.setEditable(true);

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Edits the selected client using information from the input fields.
     */
    public void editarCliente() {
        if (clienteSelecionadoParaEdicao == null) {
            JOptionPane.showMessageDialog(this, "Select a client to edit.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        salvarCliente();
    }

    /**
     * Deletes the selected client from the system.
     */
    public void excluirCliente() {
        int selectedRow = tabelaClientes.getSelectedRow();
        if (selectedRow != -1) {
            String cpf = (String) tabelaClientes.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Do you want to delete client " + cpf + "?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                gestorVendas.excluirCliente(cpf);
                atualizarListaClientes();
                limparCampos();
                clienteSelecionadoParaEdicao = null;
                btnSalvar.setText("Save Client");
                tabelaClientes.clearSelection();
                JOptionPane.showMessageDialog(this, "Client deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a client to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Validates the input fields for valid CPF, email, and phone number, and minimum name length.
     * @throws IllegalArgumentException if any validation fails
     */
    private void validarCampos() {
        if (nomeField.getText().length() < 3) {
            throw new IllegalArgumentException("Name must be at least 3 characters long.");
        }
        if (!isValidCpf(cpfField.getText())) {
            throw new IllegalArgumentException("Invalid CPF.");
        }
        if (!isValidEmail(emailField.getText())) {
            throw new IllegalArgumentException("Invalid Email.");
        }
        if (!isValidTelefone(telefoneField.getText(), (String) tipoTelefoneCombo.getSelectedItem())) {
            throw new IllegalArgumentException("Invalid Phone.");
        }
        if (ruaField.getText().isEmpty() ){
            throw new IllegalArgumentException("Street is required.");
        }
        if (numeroField.getText().isEmpty()){
            throw new IllegalArgumentException("Number is required.");
        }
        if (cidadeField.getText().isEmpty()){
            throw new IllegalArgumentException("City is required.");
        }
        if (estadoField.getText().isEmpty()){
            throw new IllegalArgumentException("State is required.");
        }
        if (cepField.getText().isEmpty()){
            throw new IllegalArgumentException("ZIP Code is required.");
        }
    }

    /**
     * Returns true if the input CPF is in correct Pattern, otherwise returns false.
     * @param cpf
     * @return Boolean
     */
    private boolean isValidCpf(String cpf) {
        return cpf.matches("\\d{11}") || cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}");
    }

    /**
     * Returns true if the input e-mail is in correct Pattern, otherwise returns false.
     * @param email
     * @return Boolean
     */
    private boolean isValidEmail(String email) {
        String emailPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return Pattern.matches(emailPattern, email);
    }

    /**
     * Returns true if the input phone is in correct Pattern, otherwise returns false.
     * @param telefone
     * @param tipo
     * @return Boolean
     */
    private boolean isValidTelefone(String telefone, String tipo) {
        telefone = telefone.replaceAll("\\D", "");
        return (tipo.equals("Celular") && telefone.length() == 11) || (tipo.equals("Fixo") && telefone.length() == 10);
    }

    /**
     * Returns CPF with the Pattern set in the application.
     * @param cpf
     * @return String
     */
    private String formatCpf(String cpf) {
        String digitsOnly = cpf.replaceAll("\\D", "");
        if (digitsOnly.length() != 11) {
            throw new IllegalArgumentException("CPF must have 11 digits.");
        }
        return digitsOnly.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    /**
     * Returns telephone with the Pattern set in the application, according with the type (cellphone or landline).
     * @param telefone
     * @param tipo
     * @return String
     */
    private String formatTelefone(String telefone, String tipo) {
        String digitsOnly = telefone.replaceAll("\\D", "");
        if (tipo.equals("Celular")) {
            return digitsOnly.replaceAll("(\\d{2})(\\d{5})(\\d{4})", "($1)$2-$3");
        } else {
            return digitsOnly.replaceAll("(\\d{2})(\\d{4})(\\d{4})", "($1)$2-$3");
        }
    }

    /**
     * Clears all input fields.
     */
    private void limparCampos() {
        cpfField.setText("");
        nomeField.setText("");
        emailField.setText("");
        telefoneField.setText("");
        ruaField.setText("");
        numeroField.setText("");
        complementoField.setText("");
        cidadeField.setText("");
        estadoField.setText("");
        cepField.setText("");
    }

    /**
     * Updates the client list table with data from GestorVendas.
     */
    private void atualizarListaClientes() {
        List<Cliente> clientes = gestorVendas.listarClientes();

        clientes.sort(Comparator.comparing(cliente -> cliente.getNome().toLowerCase()));

        DefaultTableModel model = (DefaultTableModel) tabelaClientes.getModel();
        model.setRowCount(0);

        for (Cliente cliente : clientes) {
            Endereco endereco = cliente.getEndereco();
            String enderecoCompleto = endereco.getRua() + ", " + endereco.getNumero() +
                    (endereco.getComplemento().isEmpty() ? "" : ", " + endereco.getComplemento()) +
                    ", " + endereco.getCidade() + ", " + endereco.getEstado() + " - " + endereco.getCep();
            model.addRow(new Object[]{cliente.getCpf(), cliente.getNome(), cliente.getEmail(), cliente.getTelefone(), enderecoCompleto});
        }
    }
}