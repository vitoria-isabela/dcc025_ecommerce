package br.ufjf.dcc.dcc025.dcc025_ecommerce.gui;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.User;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.repository.UserRepository;

import javax.swing.*;
import java.awt.*;

/**
 * JFrame for user login in the e-commerce system.
 * Allows users to log in or register as clients.
 * @Author: Vitoria Isabela de Oliveira - 202065097C
 */
public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JComboBox<String> roleComboBox;

    /**
     * Constructs a LoginFrame.
     */
    public LoginFrame() {
        super("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(350, 250));
        setLayout(new GridLayout(5, 2, 10, 10));
        initComponents();
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Initializes the components of the frame.
     */
    private void initComponents() {
        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        add(new JLabel("Role:"));
        roleComboBox = new JComboBox<>(new String[]{"Client", "Seller", "Manager"});
        add(roleComboBox);

        add(new JPanel());

        loginButton = new JButton("Login");
        loginButton.addActionListener(e -> performLogin());
        add(loginButton);

        registerButton = new JButton("Registrar");
        registerButton.addActionListener(e -> openRegistrationPanel());
        add(registerButton);

        add(new JPanel());
    }

    /**
     * Opens the client registration panel.
     */
    private void openRegistrationPanel() {
        ClientRegistrationPanel registrationPanel = new ClientRegistrationPanel();
        JFrame registrationFrame = new JFrame("Registro de Cliente");
        registrationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        registrationFrame.setContentPane(registrationPanel);
        registrationFrame.pack();
        registrationFrame.setLocationRelativeTo(null);
        registrationFrame.setVisible(true);
    }

    /**
     * Attempts to perform a login.
     */
    private void performLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String selectedRole = (String) roleComboBox.getSelectedItem();
    
        UserRepository userRepository = UserRepository.getInstance();
        User loggedInUser = userRepository.login(username, password);
    
        if (loggedInUser != null) {
            if (loggedInUser.getRole().equalsIgnoreCase(selectedRole)) {
                JOptionPane.showMessageDialog(this, "Login bem-sucedido como " + loggedInUser.getName() + " (" + loggedInUser.getRole() + ")", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                MainFrame mainFrame = new MainFrame(loggedInUser);
                mainFrame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Credenciais inválidas!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Login Falhou", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Main method to run the application.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame frame = new LoginFrame();
            frame.setVisible(true);
        });
    }
}