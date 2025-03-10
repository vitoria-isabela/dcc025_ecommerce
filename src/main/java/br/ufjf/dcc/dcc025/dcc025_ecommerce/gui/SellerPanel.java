package br.ufjf.dcc.dcc025.dcc025_ecommerce.gui;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Seller;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.repository.UserRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * JPanel for managing seller accounts in the e-commerce system.
 * Allows adding, editing, and deleting seller accounts.
 */
public class SellerPanel extends JPanel {

    private JTextField nameField = new JTextField(20);
    private JTextField usernameField = new JTextField(15);
    private JPasswordField passwordField = new JPasswordField(15);
    private JTextField emailField = new JTextField(25);
    private JTable sellerTable;
    private DefaultTableModel tableModel;
    private JButton btnSalvar, btnEditar, btnExcluir, btnNovo;
    private Seller sellerSelecionadoParaEdicao = null;

    /**
     * Constructs a SellerPanel.
     */
    public SellerPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));
        initComponents();
        loadSellers();
    }

    /**
     * Initializes the components of the panel.
     */
    private void initComponents() {
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.add(new JLabel("Nome Completo:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Username:"));
        formPanel.add(usernameField);
        formPanel.add(new JLabel("Password:"));
        formPanel.add(passwordField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSalvar = new JButton("Salvar Vendedor");
        btnSalvar.addActionListener(e -> salvarVendedor());

        btnEditar = new JButton("Editar Vendedor");
        btnEditar.setEnabled(false);
        btnEditar.addActionListener(e -> editarVendedor());

        btnExcluir = new JButton("Excluir Vendedor");
        btnExcluir.setEnabled(false);
        btnExcluir.addActionListener(e -> excluirVendedor());

        btnNovo = new JButton("Novo Vendedor");
        btnNovo.addActionListener(e -> {
            limparCampos();
            sellerSelecionadoParaEdicao = null;
            btnSalvar.setText("Salvar Vendedor");
            usernameField.setEditable(true);
            sellerTable.clearSelection();
        });

        buttonPanel.add(btnNovo);
        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnEditar);
        buttonPanel.add(btnExcluir);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        tableModel = new DefaultTableModel(new Object[] { "Nome", "Username", "Email" }, 0);
        sellerTable = new JTable(tableModel);
        sellerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sellerTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && sellerTable.getSelectedRow() != -1) {
                btnEditar.setEnabled(true);
                btnExcluir.setEnabled(true);
                preencherFormularioParaEdicao();
            } else {
                btnEditar.setEnabled(false);
                btnExcluir.setEnabled(false);
                limparCampos();
                sellerSelecionadoParaEdicao = null;
                btnSalvar.setText("Salvar Vendedor");
                usernameField.setEditable(true);
            }
        });
        JScrollPane tableScrollPane = new JScrollPane(sellerTable);

        add(topPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
    }

    /**
     * Fills the form fields with the data of the selected seller from the table for
     * editing.
     */
    private void preencherFormularioParaEdicao() {
        int selectedRow = sellerTable.getSelectedRow();
        if (selectedRow == -1)
            return;

        DefaultTableModel model = (DefaultTableModel) sellerTable.getModel();
        String username = (String) model.getValueAt(selectedRow, 1);

        sellerSelecionadoParaEdicao = (Seller) UserRepository.getInstance().buscarPorUsername(username);

        if (sellerSelecionadoParaEdicao != null) {
            nameField.setText(sellerSelecionadoParaEdicao.getName());
            usernameField.setText(sellerSelecionadoParaEdicao.getUsername());
            usernameField.setEditable(false);
            emailField.setText(sellerSelecionadoParaEdicao.getEmail());
            passwordField.setText(sellerSelecionadoParaEdicao.getPassword());
            btnSalvar.setText("Atualizar Vendedor");
        }
    }

    /**
     * Saves a new seller or updates an existing one.
     */
    private void salvarVendedor() {
        String name = nameField.getText();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String email = emailField.getText();

        if (name.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Seller seller = new Seller(username, password, name, email);

        if (sellerSelecionadoParaEdicao != null) {
            sellerSelecionadoParaEdicao.setName(name);
            sellerSelecionadoParaEdicao.setUsername(username);
            sellerSelecionadoParaEdicao.setPassword(password);
            sellerSelecionadoParaEdicao.setEmail(email);
            UserRepository.getInstance().editar(sellerSelecionadoParaEdicao);
            JOptionPane.showMessageDialog(this, "Vendedor atualizado com sucesso!", "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

        } else {
            UserRepository.getInstance().salvar(seller);
            JOptionPane.showMessageDialog(this, "Vendedor salvo com sucesso!", "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        limparCampos();
        loadSellers();
        sellerSelecionadoParaEdicao = null;
        btnSalvar.setText("Salvar Vendedor");
        sellerTable.clearSelection();
        usernameField.setEditable(true);
    }

    /**
     * Edits the selected seller using information from the input fields.
     */
    public void editarVendedor() {
        if (sellerSelecionadoParaEdicao == null) {
            JOptionPane.showMessageDialog(this, "Selecione um vendedor para editar.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        salvarVendedor();
    }

    /**
     * Deletes the selected seller from the system.
     */
    public void excluirVendedor() {
        int selectedRow = sellerTable.getSelectedRow();
        if (selectedRow != -1) {
            String username = (String) sellerTable.getValueAt(selectedRow, 1);
            int confirm = JOptionPane.showConfirmDialog(this, "Deseja excluir o vendedor " + username + "?",
                    "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                UserRepository.getInstance().excluir(username);
                loadSellers();
                limparCampos();
                sellerSelecionadoParaEdicao = null;
                btnSalvar.setText("Salvar Vendedor");
                sellerTable.clearSelection();
                JOptionPane.showMessageDialog(this, "Vendedor excluído com sucesso!", "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um vendedor para excluir.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Loads the list of sellers from the repository and displays them in the table.
     */
    private void loadSellers() {
        tableModel.setRowCount(0);
        List<Seller> sellers = UserRepository.getInstance().listarTipo(Seller.class);
        for (Seller seller : sellers) {
            tableModel.addRow(new Object[] { seller.getName(), seller.getUsername(), seller.getEmail() });
        }
    }

    /**
     * Clears the values of all input fields.
     */
    private void limparCampos() {
        nameField.setText("");
        usernameField.setText("");
        passwordField.setText("");
        emailField.setText("");
        usernameField.setEditable(true);
    }

    /**
     * Main method to test the panel.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Gestão de Vendedores");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new SellerPanel());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}