package br.ufjf.dcc.dcc025.dcc025_ecommerce.gui;

import javax.swing.*;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.ClientUser;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Cliente;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Endereco;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.repository.ClienteRepository;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.repository.UserRepository;

import java.awt.*;
import java.util.regex.Pattern;

/**
 * JPanel for client registration in the e-commerce system.
 * Allows new clients to register by providing their details and creating a user
 * account.
 * 
 * @Author: Vitoria Isabela de Oliveira - 202065097C
 */
public class ClientRegistrationPanel extends JPanel {

    private JTextField usernameField = new JTextField(15);
    private JPasswordField passwordField = new JPasswordField(15);
    private JTextField cpfField = new JTextField(15);
    private JTextField nomeField = new JTextField(20);
    private JTextField emailField = new JTextField(25);
    private JTextField telefoneField = new JTextField(15);
    private JComboBox<String> tipoTelefoneCombo = new JComboBox<>(new String[] { "Celular", "Fixo" });
    private JTextField ruaField = new JTextField(30);
    private JTextField numeroField = new JTextField(10);
    private JTextField complementoField = new JTextField(20);
    private JTextField cidadeField = new JTextField(20);
    private JTextField estadoField = new JTextField(20);
    private JTextField cepField = new JTextField(10);

    private JButton btnRegister;

    /**
     * Constructs a ClientRegistrationPanel.
     */
    public ClientRegistrationPanel() {
        setLayout(new BorderLayout(10, 10));
        initComponents();
    }

    /**
     * Initializes the components of the panel.
     */
    private void initComponents() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Username*:"), gbc);
        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Password*:"), gbc);
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("CPF*:"), gbc);
        gbc.gridx = 1;
        formPanel.add(cpfField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Nome*:"), gbc);
        gbc.gridx = 1;
        formPanel.add(nomeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Email*:"), gbc);
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("Tipo Telefone:"), gbc);
        gbc.gridx = 1;
        formPanel.add(tipoTelefoneCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(new JLabel("Telefone*:"), gbc);
        gbc.gridx = 1;
        formPanel.add(telefoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(new JLabel("Rua*:"), gbc);
        gbc.gridx = 1;
        formPanel.add(ruaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        formPanel.add(new JLabel("Número*:"), gbc);
        gbc.gridx = 1;
        formPanel.add(numeroField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        formPanel.add(new JLabel("Complemento:"), gbc);
        gbc.gridx = 1;
        formPanel.add(complementoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        formPanel.add(new JLabel("Cidade*:"), gbc);
        gbc.gridx = 1;
        formPanel.add(cidadeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 11;
        formPanel.add(new JLabel("Estado*:"), gbc);
        gbc.gridx = 1;
        formPanel.add(estadoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 12;
        formPanel.add(new JLabel("CEP*:"), gbc);
        gbc.gridx = 1;
        formPanel.add(cepField, gbc);

        add(formPanel, BorderLayout.CENTER);

        btnRegister = new JButton("Registrar");
        btnRegister.addActionListener(e -> registerClient());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnRegister);
        add(buttonPanel, BorderLayout.SOUTH);

    }

    /**
     * Registers a new client, validating the input and creating a new user account.
     */
    private void registerClient() {
        try {
            validarCampos();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            String cpf = formatCpf(cpfField.getText());
            String nome = nomeField.getText();
            String email = emailField.getText();
            String telefone = formatTelefone(telefoneField.getText(), (String) tipoTelefoneCombo.getSelectedItem());
            Endereco endereco = new Endereco(
                    ruaField.getText(),
                    numeroField.getText(),
                    complementoField.getText(),
                    cidadeField.getText(),
                    estadoField.getText(),
                    cepField.getText());

            Cliente cliente = new Cliente(cpf, nome, email, telefone, endereco);
            ClientUser clientUser = new ClientUser(username, password, nome, email, cliente);

            ClienteRepository.getInstance().salvar(cliente);
            UserRepository userRepository = UserRepository.getInstance();

            try {
                userRepository.salvar(clientUser);
                userRepository.salvarEmArquivo("users.json");
                ClienteRepository.getInstance().salvarEmArquivo("clientes.json");
                JOptionPane.showMessageDialog(this, "Registro bem-sucedido!", "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                ((JFrame) SwingUtilities.getWindowAncestor(this)).dispose();

            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Validates the input fields.
     * 
     * @throws IllegalArgumentException if any field is invalid
     */
    private void validarCampos() {
        if (usernameField.getText().isEmpty()) {
            throw new IllegalArgumentException("Username é obrigatório.");
        }
        if (new String(passwordField.getPassword()).isEmpty()) {
            throw new IllegalArgumentException("Password é obrigatório.");
        }
        if (nomeField.getText().length() < 3) {
            throw new IllegalArgumentException("O nome deve ter pelo menos 3 caracteres.");
        }
        if (!isValidCpf(cpfField.getText())) {
            throw new IllegalArgumentException("CPF inválido.");
        }
        if (!isValidEmail(emailField.getText())) {
            throw new IllegalArgumentException("Email inválido.");
        }
        if (!isValidTelefone(telefoneField.getText(), (String) tipoTelefoneCombo.getSelectedItem())) {
            throw new IllegalArgumentException("Telefone inválido.");
        }
        if (ruaField.getText().isEmpty()) {
            throw new IllegalArgumentException("Rua é obrigatório.");
        }
        if (numeroField.getText().isEmpty()) {
            throw new IllegalArgumentException("Número é obrigatório.");
        }
        if (cidadeField.getText().isEmpty()) {
            throw new IllegalArgumentException("Cidade é obrigatório.");
        }
        if (estadoField.getText().isEmpty()) {
            throw new IllegalArgumentException("Estado é obrigatório.");
        }
        if (cepField.getText().isEmpty()) {
            throw new IllegalArgumentException("CEP é obrigatório.");
        }
    }

    /**
     * Checks if a CPF is valid.
     * 
     * @param cpf the CPF to validate
     * @return true if the CPF is valid, false otherwise
     */
    private boolean isValidCpf(String cpf) {
        return cpf.matches("\\d{11}");
    }

    /**
     * Checks if an email is valid.
     * 
     * @param email the email to validate
     * @return true if the email is valid, false otherwise
     */
    private boolean isValidEmail(String email) {
        String emailPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return Pattern.matches(emailPattern, email);
    }

    /**
     * Checks if a telephone number is valid.
     * 
     * @param telefone the telephone number to validate
     * @param tipo     the type of telephone (Celular or Fixo)
     * @return true if the telephone number is valid, false otherwise
     */
    private boolean isValidTelefone(String telefone, String tipo) {
        telefone = telefone.replaceAll("\\D", "");
        return (tipo.equals("Celular") && telefone.length() == 11) || (tipo.equals("Fixo") && telefone.length() == 10);
    }

    /**
     * Formats a CPF.
     * 
     * @param cpf the CPF to format
     * @return the formatted CPF
     * @throws IllegalArgumentException if the CPF is not 11 digits long
     */
    private String formatCpf(String cpf) {
        String digitsOnly = cpf.replaceAll("\\D", "");
        if (digitsOnly.length() != 11) {
            throw new IllegalArgumentException("CPF deve ter 11 dígitos.");
        }
        return digitsOnly.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    /**
     * Formats a telephone number.
     * 
     * @param telefone the telephone number to format
     * @param tipo     the type of telephone (Celular or Fixo)
     * @return the formatted telephone number
     */
    private String formatTelefone(String telefone, String tipo) {
        String digitsOnly = telefone.replaceAll("\\D", "");
        if (tipo.equals("Celular")) {
            return digitsOnly.replaceAll("(\\d{2})(\\d{5})(\\d{4})", "($1)$2-$3");
        } else {
            return digitsOnly.replaceAll("(\\d{2})(\\d{4})(\\d{4})", "($1)$2-$3");
        }
    }
}